package com.effibot.robind_manipolator;

import com.effibot.robind_manipolator.Processing.P3DMap;
import com.effibot.robind_manipolator.Processing.Robot;
import com.effibot.robind_manipolator.TCP.GameState;
import com.effibot.robind_manipolator.TCP.Lock;
import com.effibot.robind_manipolator.TCP.TCPFacade;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class Controller implements Runnable {
    private static Controller instance = null;
    private P3DMap map;
    private Robot bot;
    private GameState bean;
    private int state = 0;
    private final TCPFacade tcp;
    private final BlockingQueue<HashMap<String, Object>> queue;
    PropertyChangeSupport changes = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }

    public void notifyPropertyChange(String propertyName, Object oldValue, Object newValue) {
        /*
         * Just a wrapper for the fire property change method.
         */
        changes.firePropertyChange(propertyName, oldValue, newValue);
    }

    private static final Lock lock = new Lock();

    private Controller() {
        bean = GameState.getInstance();
        tcp = TCPFacade.getInstance();
        queue = new LinkedBlockingQueue<>(1);
        tcp.setQueue(queue);
        addPropertyChangeListener(tcp);
        this.state = -1;
    }

    public static synchronized Controller getInstance() {
        if (instance == null)
            instance = new Controller();
        return instance;
    }

    public synchronized int getStateCase() {
        return state;
    }

    public synchronized void setState(int state) {
        this.state = state;
    }

    private void makeMap() throws InterruptedException {
        this.state = -1;
        HashMap<String, Object> pkt = new HashMap<>();
        pkt.put("PROC", "MAP");
        pkt.put("DIM", new double[]{1024.0, 1024.0});
        pkt.put("OBSLIST", bean.getObslist());
        notifyPropertyChange("SEND", null, pkt);
        notifyPropertyChange("RECEIVE", false, true);
        boolean finish = false;
        while (!finish) {
            // set green id
            pkt = queue.take();
            System.out.println("taking");
            String key = (String) (pkt.keySet().toArray())[0];
            switch (key) {
                case "ID" -> bean.setGreenId((double[]) pkt.get("ID"));
                case "BW" -> {
                    Utils.stream2img((byte[]) pkt.get("BW"));
                    bean.notifyPropertyChange("BW", false, true);
                    Utils.closeStreamEnc();
                }
                case "SHAPE" -> bean.setObslist((double[][]) pkt.get("SHAPE"));
                case "ANIMATION" -> Utils.stream2img((byte[]) pkt.get(key));
                case "OBS" -> bean.setObslist((double[][]) pkt.get("OBS"));
            }
            if ((Double) pkt.get("FINISH") == 1.0) {
                Utils.closeStreamEnc();
                bean.notifyPropertyChange("ANIMATION", false, true);
                finish = true;
            }
        }
    }


    @Override
    public void run() {
        while (true) {
            try {
               switch (state){
                   case 0 -> makeMap();
                   case 1 -> makePath();
                   // state  == -1
                   default -> {
                       synchronized (lock) {
                           lock.wait();
                       }
                   }
               }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
//
//                                // while !finished
//                                // make geometric path
//                                // function ->  // ask for simulation
//                                // set bean
//                                // release
//                                // acquire
//                                // start IK -> function
//                                // start vision -> function
//

    }

    private void makePath() throws InterruptedException {
        this.state = -1;
        // make new packet
        HashMap<String, Object> pkt = new HashMap<>();
        pkt.put("PROC", "PATH");
        pkt.put("START", bean.getStartId());
        pkt.put("END", bean.getShapepos());
        pkt.put("METHOD", bean.getMethod());
        notifyPropertyChange("SEND", null, pkt);
        notifyPropertyChange("RECEIVE", false, true);
        boolean finish = false;
        while (!finish) {
            // set green id
            pkt = queue.take();
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
                finish = true;
            }
        }
    }

    public Lock getLock() {
        return lock;
    }
}