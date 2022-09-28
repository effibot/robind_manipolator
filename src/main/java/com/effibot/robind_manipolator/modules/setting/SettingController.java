package com.effibot.robind_manipolator.modules.setting;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.DoubleField;

import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.workbenchfx.Workbench;
import com.dlsc.workbenchfx.view.controls.ToolbarItem;
import com.effibot.robind_manipolator.Utils;
import com.effibot.robind_manipolator.bean.RobotBean;
import com.effibot.robind_manipolator.bean.SettingBean;
import com.effibot.robind_manipolator.modules.intro.IntroModule;
import com.effibot.robind_manipolator.processing.P3DMap;
import com.effibot.robind_manipolator.tcp.TCPFacade;
import com.jfoenix.controls.JFXButton;
import com.jogamp.newt.opengl.GLWindow;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

public class SettingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SettingController.class.getName());
    private Form controlForm;
    private P3DMap p3d;
    private final SettingBean sb;
    private final SettingModule sm;
    private final BlockingQueue<LinkedHashMap<String, Object>> queue;
    private static final String RECEIVE = "RECEIVE";
    private final Workbench wb;
    private final RobotBean rb;
    TCPFacade tcp = TCPFacade.getInstance();
    PropertyChangeSupport changes = new PropertyChangeSupport(this);
    
    private final Semaphore[] sequence = {new Semaphore(1),new Semaphore(0)};
    private final Semaphore[] next = {new Semaphore(0),new Semaphore(0)};

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

    public SettingController(SettingModule sm, SettingBean sb, RobotBean robotBean, Workbench wb) {
        this.sm = sm;
        this.sb = sb;
        this.rb = robotBean;
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
        ListProperty<Integer> list = new SimpleListProperty<>();
        ArrayList<Integer[]> shapesID = (ArrayList<Integer[]>) sb.getShapeIdList();
        switch (shape) {
            case "Sfera" -> list.setValue(arrayToObsListProp(ArrayUtils.toPrimitive(shapesID.get(0))));
            case "Cono" -> list.setValue(arrayToObsListProp(ArrayUtils.toPrimitive(shapesID.get(1))));
            case "Cubo" -> list.setValue(arrayToObsListProp(ArrayUtils.toPrimitive(shapesID.get(2))));
            default -> LOGGER.warn("Forma non esistente");
        }
        sb.setIdList(list);
    }

    private ObservableList<Integer> arrayToObsListProp(int[] array) {
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
            File fl = new File("src/main/resources/com/effibot/robind_manipolator/img/textureImg.png");
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(sm.getMap(),null),"png",fl);
            } catch (IOException e) {
                LOGGER.error("Unable to make Texture",e);
                Thread.currentThread().interrupt();
            }
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
        rb.setSelectedShape(sb.shapeToPos());
        pkt.put("METHOD", sb.getSelectedMethod());
        notifyPropertyChange("SEND", null, pkt);
        notifyPropertyChange(RECEIVE, false, true);
        boolean finish = false;
        while (!finish) {
            // set green id
            pkt = queue.take();
            String key = (String) (pkt.keySet().toArray())[0];
            switch (key) {
                case "Q" -> rb.setqGRover((double[][]) pkt.get(key));
                case "dQ" -> rb.setDqGRover((double[][]) pkt.get(key));
                case "ddQ" -> rb.setDdqGRover((double[][]) pkt.get(key));
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
            sb.setRoll(((DoubleField)controlForm.getFields().get(3)).getValue());
            sb.setPitch(((DoubleField)controlForm.getFields().get(4)).getValue());
            sb.setYaw(((DoubleField)controlForm.getFields().get(5)).getValue());
            rb.setSelectedOrient(sb.getRoll(),sb.getPitch(),sb.getYaw());
            Thread simulationThread = makeSimulation();
            simulationThread.start();
            try {
                sequence[1].acquire();
                rb.setShapePos(sb.getShapeList());
                p3d = new P3DMap(rb.getObsList(), rb, next);
                p3d.run(p3d.getClass().getSimpleName());
                next[0].acquire();
                Thread inverseThread = inverseKinematics();
                inverseThread.start();
//                next[1].acquire();
            } catch (InterruptedException e) {
                LOGGER.error("Interruption",e);
                Thread.currentThread().interrupt();
            }




        });
    }

    private Thread makeSimulation() {
        return new Thread(()->{
            try {
                sequence[0].acquire();
                // make new packet
                LinkedHashMap<String, Object> pkt = new LinkedHashMap<>();
                pkt.put("PROC","SYM");
                pkt.put("M",50);
                pkt.put("ALPHA",500);
                notifyPropertyChange("SEND", null, pkt);
                notifyPropertyChange(RECEIVE, false, true);
                boolean finish = false;
                while (!finish) {
                    // set green id
                    pkt = queue.take();
                    String key = (String) (pkt.keySet().toArray())[0];
                    LOGGER.info("Getting Rover Infos:");
                    if ("ROVER".equals(key)) {
                        rb.setRoverPos((double[][]) pkt.get("Qs"));
                        LOGGER.info("rover pos setted");
                        rb.setRoverVel((double[][]) pkt.get("dQs"));
                        rb.setRoverAcc((double[][]) pkt.get("ddQs"));
                        rb.setError((double[][]) pkt.get("E"));
                    } else {
                        finish = true;
                    }
                }

            } catch (InterruptedException e) {
                LOGGER.info("Interrupting 3D Thread",e);
                Thread.currentThread().interrupt();
            }finally {
                LOGGER.info("Finally Simulink, releasing IK");
                sequence[1].release();
            }
        });
    }

    private Thread inverseKinematics() {
        return new Thread(()->{
            try{
                // make new packet
                LinkedHashMap<String, Object> pkt = new LinkedHashMap<>();
                pkt.put("PROC","IK");
                pkt.put("ROLL",sb.getRoll());
                pkt.put("PITCH",sb.getPitch());
                pkt.put("YAW",sb.getYaw());
                notifyPropertyChange("SEND", null, pkt);
                notifyPropertyChange(RECEIVE, false, true);
                boolean finish = false;
                while (!finish) {
                    // set green id
                        pkt = queue.take();

                    String key = (String) (pkt.keySet().toArray())[0];
                    switch (key) {
                        case "Q" ->{
//                            LOGGER.info("Receiving Q IK");
                            String qString = ArrayUtils.toString(pkt.get(key));
                            qString =qString.substring(1,qString.length()-1);
                            ObservableList<Float> qJoint = FXCollections.observableArrayList(
                                    Arrays.stream(qString.split(",")).map(Float::valueOf).toArray(Float[]::new)
                            );
                            rb.setQ(qJoint);
                            rb.getQ1Newton().add(qJoint.get(0));
                            rb.getQ2Newton().add(qJoint.get(1));
                            rb.getQ3Newton().add(qJoint.get(2));
                            rb.getQ4Newton().add(qJoint.get(3));
                            rb.getQ5Newton().add(qJoint.get(4));
                            rb.getQ6Newton().add(qJoint.get(5));

                        }
                        case "ENEWTON"->{
                            rb.getErrorNewton().add( ((Double) pkt.get(key)).floatValue());
                        }
                        case "FINISH"->finish = true;
                        default -> LOGGER.warn("IK not mapped case:{}",key);
                    }
                }
            }catch (InterruptedException e){
                LOGGER.error("Inverse Kinemetics acquire error",e);
                Thread.currentThread().interrupt();
            }finally {
                LOGGER.info("Finally IK");

            }
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

    public void closeProcessing() {
        if(p3d!=null){
            p3d.noLoop();
            GLWindow pane = (GLWindow)p3d.getSurface().getNative();
            pane.destroy();
        }
    }


    public Form getControlForm() {
        return controlForm;
    }
    public void setControlForm(Form cf){
        this.controlForm = cf;
    }
}