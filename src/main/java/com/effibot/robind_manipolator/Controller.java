package com.effibot.robind_manipolator;

import com.effibot.robind_manipolator.Processing.P3DMap;
import com.effibot.robind_manipolator.Processing.Robot;
import com.effibot.robind_manipolator.TCP.GameState;
import com.effibot.robind_manipolator.TCP.TCPFacade;

import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Controller extends Thread {
    private static Controller instance = null;


    private P3DMap map;
    private Robot bot;
    private GameState bean;
    private final TCPFacade tcp;
    private SceneController viewController;
    private HashMap<String,Object> toSend  = null;
    private Cases cases ;

    private ReentrantLock lock1;
    private Condition empty;
    private Condition full;
    private ConcurrentLinkedQueue<HashMap<String, Object>> queueSend;
    private boolean stop = false;
    private Condition produced;
    private Condition consumed;
    private ReentrantLock lock2;
    private ConcurrentLinkedQueue<HashMap<String, Object>> queueReceive;
    private ReentrantLock guiLock;
    private Condition start;

    public synchronized HashMap<String, Object> getToSend() {
        return toSend;
    }

    public synchronized void setToSend(HashMap<String, Object> toSend) {
        this.toSend = toSend;
    }

    private Controller() {
        bean = GameState.getInstance();
        tcp = TCPFacade.getInstance();
    }

    public static synchronized Controller getInstance() {
        if (instance == null)
            instance = new Controller();
        return instance;
    }

    public void setLock(ReentrantLock sendLock, ReentrantLock recLock,
                        Condition empty, Condition full,
                        ConcurrentLinkedQueue<HashMap<String, Object>> queue,
                        ConcurrentLinkedQueue<HashMap<String, Object>> queueRec, Condition produced,
                        Condition consumed,
                        ReentrantLock guiLock, Condition start){
        this.guiLock = guiLock;
        this.start = start;
        this.lock1 = sendLock;
        this.lock2 = recLock;
        this.empty = empty;
        this.full = full;
        this.queueSend = queue;
        this.queueReceive = queueRec;
        this.produced = produced;
        this.consumed = consumed;

    }

    public synchronized HashMap<String, Object> setTosend(int i) {
        HashMap<String, Object> msg = new HashMap<>();
        switch (i) {
            case 1:
                msg.put("PROC", "MAP");
                msg.put("DIM", new double[]{1024.0, 1024.0});
                msg.put("OBSLIST", bean.getObslist());
                break;
            case 2:
                msg.put("PROC", "PATH");
                msg.put("START", bean.getStartId());
                msg.put("END", bean.getShapepos());
                msg.put("METHOD", bean.getMethod());
                break;
        }
        return msg;

    }

    @Override
    public synchronized void run() {
        try {
            int id;
            HashMap<String, Object> msg;
            while (!isInterrupted()) {
                bean.setCase(0);
//                GUI SYNCHRO
                guiLock.lock();
                while (bean.getCase() == 0) {
                    System.out.println("WAIT CASE");
                    start.await();
                }
                id = bean.getCase();

                guiLock.unlock();

/////////////////////
//              SEND SYNCHRO
                lock1.lock();
                while (!queueSend.isEmpty()) {
                    System.out.println("Controller wait");
                    empty.await();
                }
                msg = setTosend(id);
                queueSend.offer(msg);
                System.out.println("Controller full signal");
                full.signalAll();
                lock1.unlock();


/////////////////////
//              RECEIVE SYNCHRO
                stop = bean.getStop();
                while (!stop) {
                    stop = bean.getStop();

                    lock2.lock();
                    while (queueReceive.isEmpty()) {
                        System.out.println("CONTROLLER WAIT");
                        produced.await();
                    }
                    managePacket(queueReceive.remove(), id);
                    System.out.println("Controller READ");
                    consumed.signalAll();
                    System.out.println("CONtROLLER UNLOCK");

                if(bean.getStop()) {
                    queueReceive.clear();
                    consumed.signalAll();
                    stop = bean.getStop();

                }
                    lock2.unlock();

                }

            }
        } catch (Exception e){
        throw new RuntimeException(e);
    }
    }

    public void managePacket(HashMap<String,Object> msg,int id) {
        switch (id) {
            case 1:
                String key = (String) (msg.keySet().toArray())[0];
                switch (key) {
                    case "ID" -> bean.setGreenId((double[]) msg.get("ID"));
                    case "BW" -> {
                        Utils.stream2img((byte[]) msg.get("BW"));
                        bean.notifyPropertyChange("BW", false, true);
                        Utils.closeStreamEnc();
                    }
                    case "SHAPE" -> bean.setObslist((double[][]) msg.get("SHAPE"));
                    case "ANIMATION" -> Utils.stream2img((byte[]) msg.get(key));
                    case "OBS" -> bean.setObslist((double[][]) msg.get("OBS"));
                }
                if ((Double) msg.get("FINISH") == 1.0) {
                    bean.setStop(true);
                    Utils.closeStreamEnc();
                    bean.notifyPropertyChange("ANIMATION", false, true);
                }
                break;
            case 2:
                System.out.println("TAKING");

                key = (String) (msg.keySet().toArray())[0];
                switch (key) {
                    case "Q" -> bean.setGq((double[][]) msg.get(key));
                    case "dQ" -> bean.setGdq((double[][]) msg.get(key));
                    case "ddQ" -> bean.setGddq((double[][]) msg.get(key));
                    case "ANIMATION" -> Utils.stream2img((byte[]) msg.get(key));
                }
                if ((Double) msg.get("FINISH") == 1.0) {
                    stop = true;
                    bean.setStop(stop);
                    Utils.closeStreamEnc();
                    bean.notifyPropertyChange("ANIMATION", false, true);
                }
                break;
        }
    }


    public void setCases(Cases cases) {
            this.cases = cases;
    }
}