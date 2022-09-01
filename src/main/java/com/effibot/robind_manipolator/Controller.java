package com.effibot.robind_manipolator;

import com.effibot.robind_manipolator.Processing.P3DMap;
import com.effibot.robind_manipolator.Processing.Robot;
import com.effibot.robind_manipolator.TCP.GameState;
import com.effibot.robind_manipolator.TCP.TCPFacade;

import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class Controller implements Runnable {
    private static Controller instance = null;
    private P3DMap map;
    private Robot bot;
    private Semaphore semaphore;
    private GameState bean;
    private int state = 0;
    private final TCPFacade tcp;
    private final BlockingQueue<HashMap<String, Object>> queue;


    private Controller() {
        semaphore = new Semaphore(0, true);
        bean = GameState.getInstance();
        tcp = TCPFacade.getInstance();
        queue = new LinkedBlockingQueue<>();
        tcp.setQueue(queue);
    }

    public static synchronized Controller getInstance() {
        if(instance == null)
            instance = new Controller();
        return instance;
    }

    public synchronized int getState() {
        return state;
    }

    public synchronized void setState(int state) {
        this.state = state;
    }

    @Override
    public synchronized void run() {
        while (true) {
            try {
                semaphore.acquire();
                switch (state) {
                    case 0:
                        // get data obstacle from bean
                        HashMap<String, Object> msg = new HashMap<>();
                        // open matlab connection and get map
                        msg.put("PROC", "MAP");
                        msg.put("DIM", new double[]{1024.0, 1024.0});
                        msg.put("OBSLIST", bean.getObslist());
                        tcp.setToSend(msg);
                        tcp.run();
                        while (true) {
                            // set green id
                            HashMap<String, Object> pkt = queue.take();
                            String key = (String) (pkt.keySet().toArray())[0];
                            switch (key) {
                                case "ID" -> bean.setGreenId((double[]) pkt.get("ID"));
                                case "BW" -> {
                                    Utils.stream2img((byte[]) pkt.get("BW"));
                                    bean.notifyPropertyChange("BW",false ,true);
                                    Utils.closeStreamEnc();
                                }
//                                case "GRAPH" -> beam.setGraph
                                case "SHAPE" -> bean.setObslist((double[][]) pkt.get("SHAPE"));
                                case "ANIMATION" -> Utils.stream2img((byte[]) pkt.get(key));
                            }
                            if ((Double) pkt.get("FINISH") == 1.0) {
                                Utils.closeStreamEnc();
                                bean.notifyPropertyChange("ANIMATION",false, true);
                                break;
                            }
                        }
                        // release -> setup to control
                    case 1:
                        // get data form bean
                        // while !finished
                        // make geometric path
                        // function ->  // ask for simulation
                        // set bean
                        // release
                        // acquire
                        // start IK -> function
                        // start vision -> function
                        break;

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private void mapGeneration() {

    }

    public Semaphore getSemaphore() {
        return this.semaphore;
    }
}
