package com.effibot.robind_manipolator;

import com.effibot.robind_manipolator.tcp.GameState;
import com.effibot.robind_manipolator.tcp.Lock;
import com.effibot.robind_manipolator.tcp.TCPFacade;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Controller implements Runnable {
    @Override
    public void run() {

    }
//    private static Controller instance = null;
//
//    private final GameState bean;
//    private int state = 0;
//    private final BlockingQueue<LinkedHashMap<String, Object>> queue;
//    PropertyChangeSupport changes = new PropertyChangeSupport(this);
//
//    private static final String ANIMATION ="ANIMATION";
//
//    public void addPropertyChangeListener(PropertyChangeListener l) {
//        changes.addPropertyChangeListener(l);
//    }
//
//    public void notifyPropertyChange(String propertyName, Object oldValue, Object newValue) {
//        /*
//         * Just a wrapper for the fire property change method.
//         */
//        changes.firePropertyChange(propertyName, oldValue, newValue);
//    }
//
//    private static final Lock lock = new Lock();
//
//    private Controller() {
//        bean = GameState.getInstance();
//        TCPFacade tcp = TCPFacade.getInstance();
//        queue = new LinkedBlockingQueue<>(1);
//        tcp.setQueue(queue);
//        addPropertyChangeListener(tcp);
//        this.state = -1;
//    }
//
//    public static synchronized Controller getInstance() {
//        if (instance == null)
//            instance = new Controller();
//        return instance;
//    }
//
//    public synchronized int getStateCase() {
//        return state;
//    }
//
//    public synchronized void setState(int state) {
//        this.state = state;
//    }
//
//    private void makeMap() throws InterruptedException {
//        this.state = -1;
//        HashMap<String, Object> pkt = new HashMap<>();
//        pkt.put("PROC", "MAP");
//        pkt.put("DIM", new double[]{1024.0, 1024.0});
//        pkt.put("OBSLIST", bean.getObsList());
//        notifyPropertyChange("SEND", null, pkt);
//        notifyPropertyChange("RECEIVE", false, true);
//        boolean finish = false;
//        while (!finish) {
//            // set green id
//            pkt = queue.take();
//            String key = (String) (pkt.keySet().toArray())[0];
//            switch (key) {
//                case "ID" -> bean.setGreenId((double[]) pkt.get(key));
//                case "BW" -> bean.setRaw((byte[]) Utils.decompress((byte[]) pkt.get(key)));
//                case "SHAPE" -> bean.setObsList((double[][]) pkt.get(key));
//                case ANIMATION ->bean.setAnimation((byte[]) Utils.decompress((byte[]) pkt.get(key)));
//                case "SHAPEIDS" -> {
//                    //TODO: hasmap con tre chiavi sfera cono cubo
//                    System.out.println("RICE");
//                }
////                case "Qs"-> bean.setSq((double[][]) pkt.get(key));
////                case "dQs"-> bean.setSdq((double[][]) pkt.get(key));
////                case "ddQs"-> bean.setSddq((double[][]) pkt.get(key));
////                case "E"-> bean.setE((double[][]) pkt.get(key));
//                case "FINISH" -> {
//                    finish = true;
//                }
//            }
////            if ((Double) pkt.get("FINISH") == 1.0) {
////                bean.notifyPropertyChange(ANIMATION, false, true);
////                finish = true;
////            }
//        }
//    }
//
//
//    @Override
//    public void run() {
//        while (!Thread.currentThread().isInterrupted()) {
//            try {
//               switch (state){
//                   case 0 -> makeMap();
//                   case 1 -> makePath();
//                   case 2 -> simulate();
//                   case 3 -> ik();
//                   case 4 -> vision();
//                   // state  == -1
//                   default -> {
//                       synchronized (lock) {
//                           lock.wait();
//                           System.out.println("Controller wait");
//                       }
//                   }
//               }
//            } catch (InterruptedException e) {
//                break;
//            }
//        }
////
////                                // while !finished
////                                // make geometric path
////                                // function ->  // ask for simulation
////                                // set bean
////                                // release
////                                // acquire
////                                // start IK -> function
////                                // start vision -> function
////
//
//    }
//
//    private void vision() throws InterruptedException{
//        this.state = -1;
//        HashMap<String,Object> pkt = new HashMap<>();
//        pkt.put("PROC","VIS");
//        pkt.put("SHAPE",bean.getSelectedShape());
//        notifyPropertyChange("SEND", null, pkt);
//        notifyPropertyChange("RECEIVE", false, true);
//        boolean finish = false;
//        while (!finish) {
//            // set green id
//            pkt = queue.take();
//            String key = (String) (pkt.keySet().toArray())[0];
//            switch (key) {
//                case "AREA" -> bean.setArea((double) pkt.get(key));
//                case "PERIM" ->bean.setPerim((double) pkt.get(key));
//                case "FORMA" ->bean.setForma((String) pkt.get(key));
//                case "ORIENT" ->bean.setOrient((double) pkt.get(key));
//                case "BW" ->bean.setRaw((byte[]) Utils.decompress((byte[]) pkt.get(key)));
//
//                default -> finish=true;
//            }
//            if ((Double) pkt.get("FINISH") == 1.0) {
//                finish = true;
//
//            }
//        }
//    }
//
//    private void ik()  throws InterruptedException{
//        this.state = -1;
//
//        HashMap<String,Object> pkt = new HashMap<>();
//        pkt.put("PROC","IK");
//        pkt.put("X",bean.getXdes());
//        pkt.put("Y",bean.getYdes());
//        pkt.put("Z",bean.getZdes());
//        pkt.put("ROLL",bean.getRoll());
//        pkt.put("PITCH",bean.getPitch());
//        pkt.put("YAW",bean.getYaw());
//        notifyPropertyChange("SEND", null, pkt);
//        notifyPropertyChange("RECEIVE", false, true);
//        boolean finish = false;
//        while (!finish) {
//            // set green id
//            pkt = queue.take();
//            String key = (String) (pkt.keySet().toArray())[0];
//            switch (key) {
//                case "Q" -> bean.setQIK((double[]) pkt.get(key));
//                case "ENEWTON" ->bean.setEnewton((double[]) pkt.get(key));
//                default -> finish=true;
//            }
//            if ((Double) pkt.get("FINISH") == 1.0) {
//                finish = true;
//
//            }
//        }
//    }
//
//    private void simulate()  throws InterruptedException{
//        this.state = -1;
//
//        HashMap<String,Object> pkt = new HashMap<>();
//        pkt.put("PROC","SYM");
//        pkt.put("M",5);
//        pkt.put("ALPHA",300);
//        notifyPropertyChange("SEND", null, pkt);
//        notifyPropertyChange("RECEIVE", false, true);
//        boolean finish = false;
//        while (!finish) {
//            // set green id
//            pkt = queue.take();
//            String key = (String) (pkt.keySet().toArray())[0];
//            switch (key) {
//                case "Qs" -> bean.setGq((double[][]) pkt.get(key));
//                case "dQs" -> bean.setGdq((double[][]) pkt.get(key));
//                case "ddQs" -> bean.setGddq((double[][]) pkt.get(key));
//                case "E" -> bean.setE((double[][]) pkt.get(key));
//                case ANIMATION -> bean.setAnimation((byte[]) Utils.decompress((byte[]) pkt.get(key)));
//                case "ERROR"-> {
//                    bean.setShapeAvailable(true);
//                    finish = true;
//                }
//                default -> finish=true;
//            }
//            if ((Double) pkt.get("FINISH") == 1.0) {
//                finish = true;
//
//            }
//        }
//    }
//
//    private void makePath() throws InterruptedException {
//        this.state = -1;
//        // make new packet
//        HashMap<String, Object> pkt = new HashMap<>();
//        pkt.put("PROC", "PATH");
//        pkt.put("START", bean.getStartId());
//        pkt.put("END", bean.getShapePos());
//        pkt.put("METHOD", bean.getMethod());
//        notifyPropertyChange("SEND", null, pkt);
//        notifyPropertyChange("RECEIVE", false, true);
//        boolean finish = false;
//        while (!finish) {
//            // set green id
//            pkt = queue.take();
//            String key = (String) (pkt.keySet().toArray())[0];
//            switch (key) {
//                case "Q" -> bean.setGq((double[][]) pkt.get(key));
//                case "dQ" -> bean.setGdq((double[][]) pkt.get(key));
//                case "ddQ" -> bean.setGddq((double[][]) pkt.get(key));
//                case "PATHIDS" -> bean.setPathLabel((double[]) pkt.get(key));
//                case ANIMATION -> bean.setAnimation((byte[]) Utils.decompress((byte[]) pkt.get(key)));
//                case "ERROR"-> {
//                    bean.setShapeAvailable(true);
//                    finish = true;
//                }
//                default -> finish=true;
//            }
//            if ((Double) pkt.get("FINISH") == 1.0) {
//                finish = true;
//
//            }
//        }
//    }
//
//    public Lock getLock() {
//        return lock;
//    }
}