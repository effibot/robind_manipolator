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
import com.jogamp.newt.opengl.GLWindow;
import javafx.scene.control.Button;
import processing.core.PGraphics;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.BlockingQueue;

public class IntroController {
    private final IntroModule introModule;
    private final ProcessingBase sketch;
    TCPFacade tcp = TCPFacade.getInstance();
    private final BlockingQueue<LinkedHashMap<String, Object>> queue;

    private int state = 0;
    private IntroBean introBean;
    PropertyChangeSupport changes = new PropertyChangeSupport(this);
    private Thread t=null;

    private static final Lock lock = new Lock();
    private SettingBean settingBean;

    public IntroController(Workbench workbench, IntroModule introModule, ProcessingBase sketch) {
        this.introModule = introModule;
        this.queue = tcp.getQueue();
        this.sketch = sketch;
        this.sketch.run(this.sketch.getClass().getSimpleName());

        addPropertyChangeListener(tcp);

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

        btn.setOnAction(event -> {
            introBean = new IntroBean();
            this.settingBean = new SettingBean();

            SettingModule sm = new SettingModule(settingBean,wb);
            introBean.setObsList(Utils.obs2List(((P2DMap) pb).getObstacleList()));
//            notifyPropertyChange("RESET",false,true);

            if(t!=null)
                t.interrupt();
            else{
                t = getNewThread();
                t.start();
            }
            synchronized (lock){
       
                lock.lock();
                System.out.println("I'm unlocking and notifying");
                lock.notifyAll();
            }
            GLWindow pane = (GLWindow)sketch.getSurface().getNative();
            pane.destroy();
            introModule.close();
            wb.getModules().remove(introModule);
            wb.getModules().add(sm);
            wb.openModule(sm);



        });
    }

    private Thread getNewThread() {
        return new Thread(()->{
            synchronized (lock){
                try {
//                    lock.lock();
                    while (!lock.isLocked()) {
                        System.out.println("I'm waiting");
                        lock.wait();
                    }
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }

                System.out.println("Running MakeMap");

                makeMap();
            }
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
                        settingBean.setFinish(true);
                    }
                }
//            if ((Double) pkt.get("FINISH") == 1.0) {
//                bean.notifyPropertyChange(ANIMATION, false, true);
//                finish = true;
            }
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}