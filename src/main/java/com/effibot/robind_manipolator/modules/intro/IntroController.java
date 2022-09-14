package com.effibot.robind_manipolator.modules.intro;

import com.dlsc.workbenchfx.Workbench;
import com.effibot.robind_manipolator.Processing.Obstacle;
import com.effibot.robind_manipolator.Processing.P2DMap;
import com.effibot.robind_manipolator.Processing.ProcessingBase;
import com.effibot.robind_manipolator.Utils;
import com.effibot.robind_manipolator.bean.IntroBean;
import com.effibot.robind_manipolator.bean.SettingBean;
import com.effibot.robind_manipolator.modules.setting.SettingModule;
import com.effibot.robind_manipolator.tcp.Lock;
import com.effibot.robind_manipolator.tcp.TCPFacade;
import javafx.scene.control.Button;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class IntroController {
    TCPFacade tcp = TCPFacade.getInstance();
    private final BlockingQueue<LinkedHashMap<String, Object>> queue;

    private int state = 0;
    private IntroBean introBean;
    private SettingBean settingBean = new SettingBean();
    PropertyChangeSupport changes = new PropertyChangeSupport(this);
    private final Thread t;

    private static final Lock lock = new Lock();
    public IntroController() {
        this.queue = tcp.getQueue();
        addPropertyChangeListener(tcp);
        t = new Thread(()->{
            synchronized (lock){
                try {
                    lock.lock();
                    while(lock.isLocked()){
                        System.out.println("I'm waiting");
                        lock.wait();}
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Running MakeMap");

            makeMap();
        });
        t.start();
    }

    public void notifyPropertyChange(String propertyName, Object oldValue, Object newValue) {
        /*
         * Just a wrapper for the fire property change method.
         */
        changes.firePropertyChange(propertyName, oldValue, newValue);
    }
    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }




    public void onRedoAction(Button btn, ProcessingBase pb) {
        btn.setOnMouseClicked(event -> {

            ArrayList<Obstacle> obsList = ((P2DMap) pb).getObstacleList();
            if (!obsList.isEmpty()) {
                obsList.remove(obsList.size() - 1);
                ((P2DMap) pb).setObstacleList(obsList);
            }
        });
    }

    public void onContinueAction(Button btn, Workbench wb, ProcessingBase pb) {

        btn.setOnMouseClicked(event -> {
            introBean = new IntroBean();
            SettingModule sm = new SettingModule(settingBean);
            introBean.setObsList(Utils.obs2List(((P2DMap) pb).getObstacleList()));
            synchronized (lock){
                lock.unlock();
                System.out.println("I'm unlocking and notifying");
                lock.notifyAll();
            }
            wb.getModules().addAll(sm);
            wb.openModule(wb.getModules().get(1));

        });
    }

    public void makeMap() {
        try {
            LinkedHashMap<String, Object> pkt = new LinkedHashMap<>();
            pkt.put("PROC", "MAP");
            pkt.put("DIM", new double[]{1024.0, 1024.0});
            pkt.put("OBSLIST", introBean.getObsList());
            notifyPropertyChange("SEND", null, pkt);
            notifyPropertyChange("RECEIVE", false, true);
            boolean finish = false;
            while (!finish) {
                // set green id

                pkt = queue.take();

                String key = (String) (pkt.keySet().toArray())[0];
                switch (key) {
                    case "ID" -> settingBean.setGreenId((double[]) pkt.get(key));
                    case "BW" -> settingBean.setRaw((byte[]) Utils.decompress((byte[]) pkt.get(key)));
                    case "SHAPE" -> settingBean.setShapeList((double[][]) pkt.get(key));
                    case "ANIMATION" -> settingBean.setAnimation((byte[]) Utils.decompress((byte[]) pkt.get(key)));
                    case "SHAPEIDS" -> {
                        ArrayList<double[]> greenIdShape = new ArrayList<>();
                        greenIdShape.add((double[]) pkt.get("SFERA"));
                        greenIdShape.add((double[]) pkt.get("CONO"));
                        greenIdShape.add((double[]) pkt.get("CUBO"));
                        settingBean.setShapeIdList(greenIdShape);
                    }
                    case "FINISH" -> {
                        finish = true;
                        introBean.setFinish(finish);
                    }
                }
//            if ((Double) pkt.get("FINISH") == 1.0) {
//                bean.notifyPropertyChange(ANIMATION, false, true);
//                finish = true;
            }
        } catch (InterruptedException e) {
             Thread.currentThread().interrupt();
        }

    }
}