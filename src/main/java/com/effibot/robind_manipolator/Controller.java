package com.effibot.robind_manipolator;

import com.effibot.robind_manipolator.Processing.P3DMap;
import com.effibot.robind_manipolator.Processing.Robot;
import com.effibot.robind_manipolator.TCP.GameState;
import com.effibot.robind_manipolator.TCP.Lock;
import com.effibot.robind_manipolator.TCP.TCPFacade;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class Controller implements Runnable{
    private static Controller instance = null;
    private final Thread tcpThread;
    private P3DMap map;
    private Robot bot;
    private static Semaphore[] semaphore ;
    private GameState bean;
    private int state = 0;
    private final TCPFacade tcp;
    private final BlockingQueue<HashMap<String, Object>> queue;

    private  final HashMap<String, Object> msg = new HashMap<>();
    private Thread thread;
    private static  Lock lock;

    public static Lock getLock() {
        return lock;
    }

    public static void setLock(Lock lock) {
        Controller.lock = lock;
    }

    private Controller() {
        this.semaphore = new Semaphore[]{new Semaphore(1, true),
                new Semaphore(0, true), new Semaphore(0, true)};
        bean = GameState.getInstance();
        tcp = TCPFacade.getInstance();
        tcpThread = new Thread(tcp);
        queue = new LinkedBlockingQueue<>();
        tcp.setQueue(queue);
        lock = new Lock();

    }

    public static synchronized Controller getInstance() {
        if(instance == null)
            instance = new Controller();
        return instance;
    }
    public synchronized int getStateCase() {
        return state;
    }

    public synchronized void setState(int state) {
        this.state = state;
    }

    @Override
    public void run() {
        try {
                while (true) {
//                    semaphore[1].acquire();
                    synchronized (lock) {
                        if(!lock.isLock()){
                        switch ((state)) {
                            case 0:
                                // get data obstacle from bean
                                // open matlab connection and get map
                                msg.put("PROC", "MAP");
                                msg.put("DIM", new double[]{1024.0, 1024.0});
                                msg.put("OBSLIST", bean.getObslist());
                                tcp.setToSend(msg);
                                tcpThread.start();
                                while (!msg.isEmpty()) {
                                    // set green id
                                    HashMap<String, Object> pkt = queue.take();
                                    String key = (String) (pkt.keySet().toArray())[0];
                                    switch (key) {
                                        case "ID" -> bean.setGreenId((double[]) pkt.get("ID"));
                                        case "BW" -> {
                                            Utils.stream2img((byte[]) pkt.get("BW"));
                                            bean.notifyPropertyChange("BW", false, true);
                                            Utils.closeStreamEnc();
                                        }
//                                case "GRAPH" -> beam.setGraph
                                        case "SHAPE" -> bean.setObslist((double[][]) pkt.get("SHAPE"));
                                        case "ANIMATION" -> Utils.stream2img((byte[]) pkt.get(key));
                                        case "OBS" -> bean.setObslist((double[][]) pkt.get("OBS"));
                                    }
                                    if ((Double) pkt.get("FINISH") == 1.0) {
                                        msg.clear();
                                        Utils.closeStreamEnc();
                                        bean.notifyPropertyChange("ANIMATION", false, true);

                                    }

                                }
                                semaphore[1].release();
                            case 1:
                                // get data form bean
                                // open matlab connection and get map
                                msg.put("PROC", "PATH");
                                msg.put("START", bean.getStartId());
                                msg.put("END", bean.getShapepos());
                                msg.put("METHOD", bean.getMethod());
                                tcp.setToSend(msg);
                                while (true) {
                                    // set green id
                                    HashMap<String, Object> pkt = queue.take();
                                    String key = (String) (pkt.keySet().toArray())[0];
                                    switch (key) {
                                        case "Q" -> bean.setGq((double[][]) pkt.get(key));
                                        case "dQ" -> bean.setGdq((double[][]) pkt.get(key));
                                        case "ddQ" -> bean.setGddq((double[][]) pkt.get(key));
                                        case "ANIMATION" -> Utils.stream2img((byte[]) pkt.get(key));
                                    }
                                    if ((Double) pkt.get("FINISH") == 1.0) {

                                        Utils.closeStreamEnc();
                                        bean.notifyPropertyChange("ANIMATION", false, true);
                                    }
                                }


                                // while !finished
                                // make geometric path
                                // function ->  // ask for simulation
                                // set bean
                                // release
                                // acquire
                                // start IK -> function
                                // start vision -> function


                        }
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


    }

    private void mapGeneration() {

    }

    public Semaphore[] getSemaphore() {
        return this.semaphore;
    }


    public void setThread(Thread crtlThread) {
        this.thread = crtlThread;
    }

    public void setSemaphore(Semaphore[] semaphore) {
        this.semaphore=semaphore;
    }
}