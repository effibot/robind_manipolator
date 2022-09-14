package com.effibot.robind_manipolator.modules.setting;

import com.effibot.robind_manipolator.Utils;
import com.effibot.robind_manipolator.bean.SettingBean;
import com.effibot.robind_manipolator.tcp.TCPFacade;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import org.apache.commons.lang.ArrayUtils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SettingController {
    private final SettingBean sb;
    private final SettingModule sm;
    private final BlockingQueue<LinkedHashMap<String, Object>> queue;
    TCPFacade tcp = TCPFacade.getInstance();
    PropertyChangeSupport changes = new PropertyChangeSupport(this);

    public void notifyPropertyChange(String propertyName, Object oldValue, Object newValue) {
        /*
         * Just a wrapper for the fire property change method.
         */
        changes.firePropertyChange(propertyName, oldValue, newValue);
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }

    private final Thread t;

    public SettingController(SettingModule sm, SettingBean sb) {
        this.sm = sm;
        this.sb = sb;
        addPropertyChangeListener(tcp);
        this.queue = tcp.getQueue();
        t = new Thread(()->{

        });
        t.start();
    }

    public void setIdByShape(String shape) {
        ListProperty<Double> list = new SimpleListProperty<>();
        ArrayList<double[]> shapesID = sb.getShapeIdList();
        switch (shape) {
            case "Sfera" -> list.setValue(arrayToObsListProp(shapesID.get(0)));
            case "Cono" -> list.setValue(arrayToObsListProp(shapesID.get(1)));
            case "Cubo" -> list.setValue(arrayToObsListProp(shapesID.get(2)));
        }
        sb.setIdList(list);
    }

    private ObservableList<Double> arrayToObsListProp(double[] array) {
        return FXCollections.observableList(
                Arrays.asList(
                        ArrayUtils.toObject(
                                array
                        )
                )
        );
    }

    public void onStartAction(Button start) {
        
    }

    private void makePath() throws InterruptedException {
        // make new packet
        LinkedHashMap<String, Object> pkt = new LinkedHashMap<>();
        pkt.put("PROC", "PATH");
        pkt.put("START", sb.getSelectedId());
        pkt.put("END", sb.shapeToPos());
        pkt.put("METHOD", sb.getSelectedMethod());
        notifyPropertyChange("SEND", null, pkt);
        notifyPropertyChange("RECEIVE", false, true);
        boolean finish = false;
        while (!finish) {
            // set green id
            pkt = queue.take();
            String key = (String) (pkt.keySet().toArray())[0];
            switch (key) {
//                case "Q" -> sb.setGq((double[][]) pkt.get(key));
//                case "dQ" -> sb.setGdq((double[][]) pkt.get(key));
//                case "ddQ" -> sb.setGddq((double[][]) pkt.get(key));
                case "PATHIDS" -> sb.setPathLabel((double[]) pkt.get(key));
                case "ANIMATION" -> sb.setAnimation((byte[]) Utils.decompress((byte[]) pkt.get(key)));
                default -> {
                    System.out.println("Caso error non mappato");
                    finish = true;
                }
            }
            if ((Double) pkt.get("FINISH") == 1.0) {
                finish = true;

            }
        }
    }
}