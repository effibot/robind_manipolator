package com.effibot.robind_manipolator.modules.setting;

import com.dlsc.workbenchfx.Workbench;
import com.dlsc.workbenchfx.view.controls.ToolbarItem;
import com.effibot.robind_manipolator.Utils;
import com.effibot.robind_manipolator.bean.SettingBean;
import com.effibot.robind_manipolator.modules.intro.IntroModule;
import com.effibot.robind_manipolator.tcp.TCPFacade;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.concurrent.BlockingQueue;

public class SettingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SettingController.class.getName());
    private final SettingBean sb;
    private final SettingModule sm;
    private final BlockingQueue<LinkedHashMap<String, Object>> queue;
    private final Workbench wb;
    TCPFacade tcp = TCPFacade.getInstance();
    PropertyChangeSupport changes = new PropertyChangeSupport(this);

    private static final String WIKICONTENT = """
            Selezionare la forma e l'ID da cui far partire il rover, il metodo di
             interpolazione della generazione del cammino e gli angoli
            di roll,pitch e yaw da utilizzare nella cinematica inversa per avvicinare
            il PUMA alla forma e procedere all'identificazione dell'oggetto.
            Nel caso in cui si voglia cambiare i parametri, modificare le scelte e
            premere il bottono Start 2D; altrimenti, per continuare premere Start 3D.
            """;
    public void notifyPropertyChange(String propertyName, Object oldValue, Object newValue) {
        /*
         * Just a wrapper for the fire property change method.
         */
        changes.firePropertyChange(propertyName, oldValue, newValue);
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }

    private Thread t;

    public SettingController(SettingModule sm, SettingBean sb, Workbench wb) {
        this.sm = sm;
        this.sb = sb;
        this.wb = wb;
        addPropertyChangeListener(tcp);
        this.queue = tcp.getQueue();
    }

    private Thread getNew2DThread() {
        return new Thread(()->{
            try {
                    makePath();
            } catch (InterruptedException e) {
                LOGGER.info("Interrupting 2D Thread",e);
                Thread.currentThread().interrupt();
            }
        });
    }

    public void setIdByShape(String shape) {
        ListProperty<Double> list = new SimpleListProperty<>();
        ArrayList<double[]> shapesID = (ArrayList<double[]>) sb.getShapeIdList();
        switch (shape) {
            case "Sfera" -> list.setValue(arrayToObsListProp(shapesID.get(0)));
            case "Cono" -> list.setValue(arrayToObsListProp(shapesID.get(1)));
            case "Cubo" -> list.setValue(arrayToObsListProp(shapesID.get(2)));
            default -> LOGGER.warn("Forma non esistente");
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

    public void onStart2DAction(JFXButton start) {
        start.setOnAction(event -> {
            t = getNew2DThread();
            t.start();
            sm.getVb().setDisable(true);
        });
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
                case "FINISH" ->{
                        finish = true;
                        sm.getVb().setDisable(false);
                }
                default -> LOGGER.warn("Pacchetto non mappato.");
            }
        }
    }


    public void onBackAction(JFXButton back) {
        back.setOnMouseClicked(evt->{
            IntroModule im = new IntroModule();
            sm.close();
            wb.getModules().remove(sm);
            wb.getModules().add(im);
            wb.openModule(im);
        });
    }

    public void onStart3DAction(JFXButton start3d) {
        start3d.setOnAction(event->{
            t = getNew3DThread();
            t.start();
        });
    }

    private Thread getNew3DThread() {
        return new Thread(()->{
            // TODO: 1. starto processing3d. 2. bean 3d. 3. robot getter from bean

        });
    }

    public void setOnHoverInfo(ToolbarItem toolbarItem) {
        toolbarItem.setOnClick(evt->
            wb.showInformationDialog(
                    "Wiki "+sm.getName(),
                    WIKICONTENT,
                    buttonType ->{}
            )
        );

    }
}