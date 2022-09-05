package com.effibot.robind_manipolator;
import com.effibot.robind_manipolator.TCP.GameState;
import com.effibot.robind_manipolator.TCP.TCPFacade;
import com.effibot.robind_manipolator.Processing.*;
import com.effibot.robind_manipolator.Processing.Observer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextFormatter;
import org.apache.commons.lang.ArrayUtils;
import org.controlsfx.control.SegmentedButton;
import org.controlsfx.control.textfield.CustomTextField;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.effibot.robind_manipolator.Utils.mySetFormatter;

public class SceneController implements Initializable, Observer, PropertyChangeListener {
    private static ProcessingBase sketch;
    Application app;

    @FXML
    public SegmentedButton segButtonBar;
    @FXML
    public ComboBox<String> startPos;
    @FXML
    private Button cancel;
    @FXML
    private Tab controlTab;
    @FXML
    private Button startBtn;
    @FXML
    private Label objName;
    @FXML
    private Button bckCtrlBtn;
    @FXML
    private Button bckStpBtn;
    @FXML
    private CustomTextField rollField;
    @FXML
    private CustomTextField pitchField;
    @FXML
    private CustomTextField yawField;
    @FXML
    private Button back;
    @FXML
    private ImageView basicMap;
    @FXML
    private AnchorPane bottomAnchor;
    @FXML
    private ToggleButton coneBtn;
    @FXML
    private Button cont;
    @FXML
    private ToggleButton cubeBtn;
    @FXML
    private AnchorPane leftAnchor;
    @FXML
    private SplitPane mainView;
    @FXML
    private ImageView map;
    @FXML
    private AnchorPane paneOne;
    @FXML
    private Label pathLabel;
    @FXML
    private AnchorPane rightAnchor;
    @FXML
    private ToggleButton sphereBtn;
    @FXML
    private AnchorPane topAnchor;
    @FXML
    private SplitPane topSplit;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab setupTab;
    @FXML
    private Tab infoTab;
    @FXML
    private List<Obstacle> obsList;
    @FXML
    private RadioButton paraRadio;
    @FXML
    private RadioButton quinticRadio;
    @FXML
    private RadioButton cubicRadio;
    private ToggleGroup radioGroup;
    private ToggleGroup shapeGroup;
    private ArrayList<Integer> sequence;
    private static List<String> greenId = new ArrayList<>();
    private static final Utils util = new Utils();
    private static GameState gm;
    private static TCPFacade tcp;
    private Controller ctrl;
    private int[] statecase= new int[1];
    private Cases cases;
    private ReentrantLock guiLock;
    private Condition start;

    @FXML
    public void onContinueButtonClick() {
        if (obsList != null) {
            gm.setObslist(util.obs2List(obsList));
//            synchronized (cases) {
//                cases.setCases(1);
//                cases.notify();
//                System.out.println("FX-CONTROLLER NOTIFY");
//
//            }
            guiLock.lock();
            gm.setCase(1);
            start.signalAll();
            guiLock.unlock();
            setupTab.setClosable(true);
            setupTab.setDisable(true);
            tabPane.getSelectionModel().select(controlTab);
            controlTab.setDisable(false);
            // close 2D map
            sketch.removeObserver(this);
            sketch.noLoop();
            sketch.stop();
            sketch.exit();

        } else {
            //TODO: implements popup to specify at least one obstacle
        }
    }


    @FXML
    public void onCancelButtonClick() {
        if (!obsList.isEmpty()) {
            obsList.remove(obsList.size() - 1);
            ((P2DMap) sketch).setObstacleList((ArrayList<Obstacle>) obsList);
        }
    }

    @FXML
    public void onObjectButtonAction(ActionEvent actionEvent) {
        //TODO: implementa azione basata sull'oggetto chiamante
        // in base al bottone che chiama il metodo, carica l'oggetto
        String id = ((Node) actionEvent.getSource()).getId();
        switch (id) {
            case "sphere":
                if (sequence.isEmpty() || !sequence.contains(0)) {
                    sequence.add(0);
                } else {
                    sequence.removeIf(t -> t == 0);
                }
                break;
            case "cone":
                if (sequence.isEmpty() || !sequence.contains(1)) {
                    sequence.add(1);
                } else {
                    sequence.removeIf(t -> t == 1);
                }
                break;
            case "cube":
                if (sequence.isEmpty() || !sequence.contains(2)) {
                    sequence.add(2);
                } else {
                    sequence.removeIf(t -> t == 2);
                }
                break;
            default:
                break;
        }
    }

    @FXML
    public void onStartAction(ActionEvent actionEvent) {
        float roll = Float.parseFloat(rollField.getText());
        float pitch = Float.parseFloat(pitchField.getText());
        float yaw = Float.parseFloat(yawField.getText());
        boolean condition = sequence.isEmpty() ||
                rollField.getText().isEmpty() || pitchField.getText().isEmpty() || rollField.getText().isEmpty();
        double startid = Double.parseDouble(startPos.getValue());
        double selectedShape = Double.valueOf(shapeGroup.getSelectedToggle().getUserData().toString()).intValue();
        String method = (String) radioGroup.getSelectedToggle().getUserData();
        if (!condition) {
            gm.setRoll(roll);
            gm.setPitch(pitch);
            gm.setYaw(yaw);
            double[][] obs = gm.getObslist();
            gm.setShapepos(new double[]{obs[(int) selectedShape][0], obs[(int) selectedShape][1]});
            gm.setStartId(startid);
            gm.setMethod(method);
            guiLock.lock();
            gm.setCase(2);
            start.signalAll();
            guiLock.unlock();
//            double[][] obsshapes = gm.getObslist();
//            double[] shapeposition = obsshapes[(int) selectedShape];
//            HashMap<String, Object> msg = new HashMap<>();
//            msg.put("PROC", "PATH");
//            msg.put("START", startid);
//            msg.put("END", shapeposition);
//            msg.put("METHOD", method);
//            msg.put("FINISH", 1);
//            ArrayList<HashMap> rec = tcp.sendMsg(msg);
//
//            tcp.flushBuffer();
//            gm.setGq((double[][]) rec.get(0).get("Q"));
//            gm.setGdq((double[][]) rec.get(0).get("dQ"));
//            gm.setGddq((double[][]) rec.get(0).get("ddQ"));
//            gm.setSelectedShape(selectedShape);
//            gm.setPitch(pitch);
//            gm.setRoll(roll);
//            gm.setYaw(yaw);
//            gm.setShapepos(shapeposition);
//            gm.setXdes(shapeposition[1]);
//            gm.setYdes(shapeposition[0]);
//            gm.setZdes(80);

//            // load images
//            basicMap.setImage(SwingFXUtils.toFXImage(matlabInstance.getInfo().graph(),null));
//            ArrayList<Image> imgs =(ArrayList<Image>)  util.makeImage(matlabInstance.getPath().mapsimimg());
//            map.setImage(imgs.get(0));
//            Timeline timeLine = new Timeline();
//            Collection<KeyFrame> frames = timeLine.getKeyFrames();
//            Duration frameGap = Duration.millis(150);
//            Duration frameTime = Duration.ZERO;
//            int sz = imgs.size();
//            for (int i = 0;i<sz;i++) {
//                frameTime = frameTime.add(frameGap);
//                Image imgi = imgs.get(i);
//                frames.add(new KeyFrame(frameTime, e -> map.setImage(imgi)));
//            }
//            timeLine.setCycleCount(1);
//            timeLine.setOnFinished(finish-> {
//            try {
//                setUpStage();
//            } catch (ExecutionException | InterruptedException e) {
//
//                Thread.currentThread().interrupt();
//            }
//            });
//            timeLine.play();
//

        }


    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sphereBtn.setId("sphere");
        sphereBtn.setUserData(0);
        cubeBtn.setId("cube");
        cubeBtn.setUserData(1);
        coneBtn.setId("cone");
        coneBtn.setUserData(2);
        shapeGroup = new ToggleGroup();
        sphereBtn.setToggleGroup(shapeGroup);
        cubeBtn.setToggleGroup(shapeGroup);
        coneBtn.setToggleGroup(shapeGroup);
        // blocco dei divisori degli split pane
        SplitPane.Divider mainDivider = mainView.getDividers().get(0);
        double mainDividerPosition = mainDivider.getPosition();
        mainDivider.positionProperty().addListener((observable, oldValue, newValue) -> mainDivider.setPosition(mainDividerPosition));
        SplitPane.Divider topDivider = topSplit.getDividers().get(0);
        double topDividerPosition = topDivider.getPosition();
        topDivider.positionProperty().addListener((observable, oldValue, newValue) -> topDivider.setPosition(topDividerPosition));

        // Setup delle label d'info
        objName.setText("");
        pathLabel.setText("");
        infoTab.setClosable(true);
        infoTab.setDisable(true);
        // setup label control
        controlTab.setClosable(true);
        controlTab.setDisable(true);
        startPos.setVisibleRowCount(5);
        startPos.getEditor().setTextFormatter(new TextFormatter<>(c -> {
            if (c.getControlNewText().matches("\\d*") && greenId.contains(c.getText()))
                return c;
            else
                return null;
        }
        ));
        // setup interpolation method
        radioGroup = new ToggleGroup();
        paraRadio.setToggleGroup(radioGroup);
        paraRadio.setUserData("paraboloic");
        quinticRadio.setToggleGroup(radioGroup);
        quinticRadio.setUserData("quintic");
        cubicRadio.setToggleGroup(radioGroup);
        cubicRadio.setUserData("cubic");
        radioGroup.selectToggle(paraRadio); // default value
        //startPos.getItems().addAll(/*TODO: get list of green cells from the map and add to the choicebox*/)
        // Setup RPY default value
        rollField.setText("");
        mySetFormatter(rollField);
        pitchField.setText("");
        mySetFormatter(pitchField);
        yawField.setText("");
        mySetFormatter(yawField);
        // init sequence
        sequence = new ArrayList<>();
        startBtn.setDisable(false);
        // get singletons' instances
        gm = GameState.getInstance();
        gm.addPropertyChangeListener(this);
        tcp = TCPFacade.getInstance();
        ctrl = Controller.getInstance();
        this.cases = new Cases(0);
        ctrl.setCases(cases);
        guiLock = new ReentrantLock();
        start = guiLock.newCondition();

        ReentrantLock sendLock = new ReentrantLock();
        ReentrantLock recLock = new ReentrantLock();
        Condition empty = sendLock.newCondition();
        Condition full = sendLock.newCondition();
        Condition produced = recLock.newCondition();
        Condition consumed = recLock.newCondition();
        ConcurrentLinkedQueue<HashMap<String, Object>> queueSend = new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<HashMap<String,Object>> queueRec = new ConcurrentLinkedQueue<>();
        ctrl.setLock(sendLock,recLock,empty,full,queueSend,queueRec,produced,consumed,guiLock,start);
        tcp.setLock(sendLock,recLock,empty,full,queueSend,queueRec,produced,consumed);
        ctrl.start();
        tcp.start();
    }




    public void setJavafxApp(Application jfxApp) {
        app = jfxApp;
    }

    public static void setSketch(ProcessingBase sketch) {
        SceneController.sketch = sketch;
    }

    @Override
    public void update(Object object) {
        this.obsList = ((P2DMap) object).getObstacleList();
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        switch (propertyName) {
            case "ID" -> {
                // set id in the combobox
                for (double id : gm.getGreenId()) greenId.add(String.valueOf((int) id));
                startPos.getItems().addAll(greenId);
            }
            case "ANIMATION" -> {
                // start image generation and set image components
//                File gifFile = new File();
                Image gifImage = new Image("file:"+util.getGifPath());
                map.setImage(gifImage);
            }
            case "BW" -> {
                // set BW image
                Image basicMapImage = new Image("file:"+util.getGifPath());
                basicMap.setImage(basicMapImage);
                map.setImage(basicMapImage);
            }
        }
    }
    private void setUpStage() throws ExecutionException, InterruptedException {


//        HashMap<String, Object> msg = new HashMap<>();
//        msg.put("PROC", "SYM");
//        msg.put("M", 10);
//        msg.put("ALPHA", 200);
//        ArrayList<HashMap> rec = tcp.sendMsg(msg);
//        tcp.flushBuffer();
//        gm.setSq((double[][]) rec.get(0).get("Q"));
//        gm.setSdq((double[][]) rec.get(0).get("dQ"));
//        gm.setSddq((double[][]) rec.get(0).get("ddQ"));
//        gm.setE((double[][]) rec.get(0).get("E"));
//        // open 3D map
//        P3DMap sketchmap = new P3DMap(obsList);
//
//        SceneController.setSketch(sketchmap);
//        sketchmap.setJavaFX(this);
//        sketch.run(sketch.getClass().getSimpleName());
//        ((Main) app).setSketch(sketchmap);
//            BufferedImage[] oldpath = matlabInstance.getPath().mapsimimg();
//            basicMap.setImage(SwingFXUtils.toFXImage(oldpath[oldpath.length],null));
//            ArrayList<Image> imgs = util.makeImage(matlabInstance.getSysout().mappid());
//            map.setImage(imgs.get(0));
//            Timeline timeLine = new Timeline();
//            Collection<KeyFrame> frames = timeLine.getKeyFrames();
//            Duration frameGap = Duration.millis(150);
//            Duration frameTime = Duration.ZERO;
//            int sz = imgs.size();
//            for (int i = 0;i<sz;i++) {
//                frameTime = frameTime.add(frameGap);
//                Image imgi = imgs.get(i);
//                frames.add(new KeyFrame(frameTime, e -> map.setImage(imgi)));
//            }
//            timeLine.setCycleCount(1);
//    //        timeLine.setOnFinished((finish)-> setUpStage(finish));
//            timeLine.play();

    }
}