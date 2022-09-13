package com.effibot.robind_manipolator;

//import com.effibot.robind_manipolator.Processing.Observer;
import com.effibot.robind_manipolator.Processing.Obstacle;
import com.effibot.robind_manipolator.Processing.P2DMap;
import com.effibot.robind_manipolator.Processing.ProcessingBase;
import com.effibot.robind_manipolator.tcp.GameState;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.*;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelBuffer;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;
import javafx.util.converter.FloatStringConverter;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.dialog.CommandLinksDialog;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.function.UnaryOperator;
import org.controlsfx.dialog.CommandLinksDialog.CommandLinksButtonType;


public class SceneController {
//    @FXML
//    public ComboBox<String> startPos;
//    @FXML
//    private Button cancel;
//    @FXML
//    private Tab controlTab;
//    @FXML
//    private Button startBtn;
//    @FXML
//    private Label objName;
//    @FXML
//    private Button bckCtrlBtn;
//    @FXML
//    private Button bckStpBtn;
//    @FXML
//    private CustomTextField rollField;
//    @FXML
//    private CustomTextField pitchField;
//    @FXML
//    private CustomTextField yawField;
//    @FXML
//    private Button back;
//    @FXML
//    private ImageView basicMap;
//    @FXML
//    private AnchorPane bottomAnchor;
//    @FXML
//    private ToggleButton coneBtn;
//    @FXML
//    private Button cont;
//    @FXML
//    private ToggleButton cubeBtn;
//    @FXML
//    private AnchorPane leftAnchor;
//    @FXML
//    private SplitPane mainView;
//    @FXML
//    private ImageView map;
//    @FXML
//    private AnchorPane paneOne;
//    @FXML
//    private Label pathLabel;
//    @FXML
//    private AnchorPane rightAnchor;
//    @FXML
//    private ToggleButton sphereBtn;
//    @FXML
//    private AnchorPane topAnchor;
//    @FXML
//    private SplitPane topSplit;
//    @FXML
//    private TabPane tabPane;
//    @FXML
//    private Tab setupTab;
//    @FXML
//    private Tab infoTab;
//    @FXML
//    private List<Obstacle> obsList;
//    @FXML
//    private RadioButton paraRadio;
//    @FXML
//    private RadioButton quinticRadio;
//    @FXML
//    private RadioButton cubicRadio;
//    private ToggleGroup radioGroup;
//    private ToggleGroup shapeGroup;
//    private ArrayList<Integer> sequence;
//    private static final List<String> greenId = new ArrayList<>();
//    private static final Utils util = new Utils();
//    private GameState gm;
//    private Controller ctrl;
//
//
//    @FXML
//    public void onContinueButtonClick() {
////        ArrayList<Obstacle> dummy = new ArrayList<>();
////        dummy.add(new Obstacle(sketch,2*(40-10),2*(40-10),50,120,100,0));
////        ((P2DMap)sketch).setObstacleList(dummy);
//        if (obsList.size() >= 3) {
//            gm.setObsList(util.obs2List(obsList));
//            ctrl.setState(0);
//            sketch2D.pause();
//            synchronized (ctrl.getLock()) {
//                ctrl.getLock().notifyAll();
//                System.out.println("Scene controller notify");
//            }
//            setupTab.setClosable(true);
//            setupTab.setDisable(true);
//            tabPane.getSelectionModel().select(controlTab);
//            controlTab.setDisable(false);
//            // close 2D map
////            sketch.noLoop();
////            sketch.stop();
////            sketch.exit();
//        } else {
//            Platform.runLater(() ->{
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setHeaderText("Numero di Ostacoli Insufficienti");
//                alert.setContentText("Inserire almeno tre ostacoli sulla mappa");
//                alert.initStyle(StageStyle.UNDECORATED);
//                alert.showAndWait();
//            });
//        }
//    }
//
//    @FXML
//    public void onCancelButtonClick() {
//        if (!obsList.isEmpty()) {
//            obsList.remove(obsList.size() - 1);
//            ((P2DMap) sketch2D).setObstacleList((ArrayList<Obstacle>) obsList);
//        }
//    }
//
//    @FXML
//    public void onObjectButtonAction(ActionEvent actionEvent) {
//        //TODO: implementa azione basata sull'oggetto chiamante
//        // in base al bottone che chiama il metodo, carica l'oggetto
//        String id = ((Node) actionEvent.getSource()).getId();
//        switch (id) {
//            case "sphere":
//                if (sequence.isEmpty() || !sequence.contains(0)) {
//                    sequence.add(0);
//                } else {
//                    sequence.removeIf(t -> t == 0);
//                }
//                break;
//            case "cone":
//                if (sequence.isEmpty() || !sequence.contains(1)) {
//                    sequence.add(1);
//                } else {
//                    sequence.removeIf(t -> t == 1);
//                }
//                break;
//            case "cube":
//                if (sequence.isEmpty() || !sequence.contains(2)) {
//                    sequence.add(2);
//                } else {
//                    sequence.removeIf(t -> t == 2);
//                }
//                break;
//            default:
//                break;
//        }
//    }
//
//    @FXML
//    public void onStartAction() {
////        startBtn.setDisable(true);
//        float roll = Float.parseFloat(rollField.getText());
//        float pitch = Float.parseFloat(pitchField.getText());
//        float yaw = Float.parseFloat(yawField.getText());
//        boolean condition = sequence.isEmpty() ||
//                rollField.getText().isEmpty() || pitchField.getText().isEmpty();
//        double startid = Double.parseDouble(startPos.getValue());
//        double selectedShape = Double.valueOf(shapeGroup.getSelectedToggle().getUserData().toString()).intValue();
//        String method = (String) radioGroup.getSelectedToggle().getUserData();
//        if (!condition) {
//            gm.setRoll(roll);
//            gm.setPitch(pitch);
//            gm.setYaw(yaw);
//            double[][] obs = gm.getObsList();
//            gm.setShapePos(new double[]{obs[(int) selectedShape][1], obs[(int) selectedShape][2]});
//            gm.setStartId(startid);
//            gm.setMethod(method);
//            ctrl.setState(1);
//            synchronized (ctrl.getLock()) {
//                ctrl.getLock().notifyAll();
//            }
//            switchTab("Info");
//            objName.setText("");
//            // set pathlabel
//
////            double[][] obsshapes = gm.getObslist();
////            double[] shapeposition = obsshapes[(int) selectedShape];
////            HashMap<String, Object> msg = new HashMap<>();
////            msg.put("PROC", "PATH");
////            msg.put("START", startid);
////            msg.put("END", shapeposition);
////            msg.put("METHOD", method);
////            msg.put("FINISH", 1);
////            ArrayList<HashMap> rec = tcp.sendMsg(msg);
////
////            tcp.flushBuffer();
////            gm.setGq((double[][]) rec.get(0).get("Q"));
////            gm.setGdq((double[][]) rec.get(0).get("dQ"));
////            gm.setGddq((double[][]) rec.get(0).get("ddQ"));
////            gm.setSelectedShape(selectedShape);
////            gm.setPitch(pitch);
////            gm.setRoll(roll);
////            gm.setYaw(yaw);
////            gm.setShapepos(shapeposition);
////            gm.setXdes(shapeposition[1]);
////            gm.setYdes(shapeposition[0]);
////            gm.setZdes(80);
//
//
//        }
//
//
//    }
//
//    private void setUpStage() throws ExecutionException, InterruptedException {
//
//
////        HashMap<String, Object> msg = new HashMap<>();
////        msg.put("PROC", "SYM");
////        msg.put("M", 10);
////        msg.put("ALPHA", 200);
////        ArrayList<HashMap> rec = tcp.sendMsg(msg);
////        tcp.flushBuffer();
////        gm.setSq((double[][]) rec.get(0).get("Q"));
////        gm.setSdq((double[][]) rec.get(0).get("dQ"));
////        gm.setSddq((double[][]) rec.get(0).get("ddQ"));
////        gm.setE((double[][]) rec.get(0).get("E"));
////        // open 3D map
////        P3DMap sketchmap = new P3DMap(obsList);
////
////        SceneController.setSketch(sketchmap);
////        sketchmap.setJavaFX(this);
////        sketch.run(sketch.getClass().getSimpleName());
////        ((Main) app).setSketch(sketchmap);
////            BufferedImage[] oldpath = matlabInstance.getPath().mapsimimg();
////            basicMap.setImage(SwingFXUtils.toFXImage(oldpath[oldpath.length],null));
////            ArrayList<Image> imgs = util.makeImage(matlabInstance.getSysout().mappid());
////            map.setImage(imgs.get(0));
////            Timeline timeLine = new Timeline();
////            Collection<KeyFrame> frames = timeLine.getKeyFrames();
////            Duration frameGap = Duration.millis(150);
////            Duration frameTime = Duration.ZERO;
////            int sz = imgs.size();
////            for (int i = 0;i<sz;i++) {
////                frameTime = frameTime.add(frameGap);
////                Image imgi = imgs.get(i);
////                frames.add(new KeyFrame(frameTime, e -> map.setImage(imgi)));
////            }
////            timeLine.setCycleCount(1);
////    //        timeLine.setOnFinished((finish)-> setUpStage(finish));
////            timeLine.play();
//
//    }
//
//    private static final int HEIGHT = 1024;
//    private static final int WIDTH = 1024;
//    private ByteBuffer bufferBW = ByteBuffer.allocateDirect(4 * WIDTH * HEIGHT);
//    private ByteBuffer bufferAnimation = ByteBuffer.allocateDirect(4 * WIDTH * HEIGHT);
//    private PixelBuffer<ByteBuffer> pixelBufferBW = new PixelBuffer<>(WIDTH, HEIGHT, bufferBW, PixelFormat.getByteBgraPreInstance());
//
//    private PixelBuffer<ByteBuffer> pixelBufferAnimation = new PixelBuffer<>(WIDTH, HEIGHT, bufferAnimation, PixelFormat.getByteBgraPreInstance());
//    private WritableImage imgBW = new WritableImage(pixelBufferBW);
//    private WritableImage imgAnimation = new WritableImage(pixelBufferAnimation);
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        obsList = new ArrayList<>();
//        sphereBtn.setId("sphere");
//        sphereBtn.setUserData(0);
//        cubeBtn.setId("cube");
//        cubeBtn.setUserData(1);
//        coneBtn.setId("cone");
//        coneBtn.setUserData(2);
//        shapeGroup = new ToggleGroup();
//        sphereBtn.setToggleGroup(shapeGroup);
//        cubeBtn.setToggleGroup(shapeGroup);
//        coneBtn.setToggleGroup(shapeGroup);
//        // blocco dei divisori degli split pane
//        SplitPane.Divider mainDivider = mainView.getDividers().get(0);
//        double mainDividerPosition = mainDivider.getPosition();
//        mainDivider.positionProperty().addListener((observable, oldValue, newValue) -> mainDivider.setPosition(mainDividerPosition));
//        SplitPane.Divider topDivider = topSplit.getDividers().get(0);
//        double topDividerPosition = topDivider.getPosition();
//        topDivider.positionProperty().addListener((observable, oldValue, newValue) -> topDivider.setPosition(topDividerPosition));
//        // Setup delle label d'info
//        objName.setText("");
//        pathLabel.setText("");
//        switchTab("Setup");
//        startPos.setVisibleRowCount(5);
//        startPos.getEditor().setTextFormatter(new TextFormatter<>(c -> {
//            if (c.getControlNewText().matches("\\d*") && greenId.contains(c.getText()))
//                return c;
//            else
//                return null;
//        }
//        ));
//        // setup interpolation method
//        radioGroup = new ToggleGroup();
//        paraRadio.setToggleGroup(radioGroup);
//        paraRadio.setUserData("paraboloic");
//        quinticRadio.setToggleGroup(radioGroup);
//        quinticRadio.setUserData("quintic");
//        cubicRadio.setToggleGroup(radioGroup);
//        cubicRadio.setUserData("cubic");
//        radioGroup.selectToggle(paraRadio); // default value
//        // Setup RPY default value
//        rollField.setText("");
//        mySetFormatter(rollField);
//        pitchField.setText("");
//        mySetFormatter(pitchField);
//        yawField.setText("");
//        mySetFormatter(yawField);
//        // Setup Images
//        basicMap.setImage(imgBW);
//        map.setImage(imgAnimation);
//
//        // init sequence
//        sequence = new ArrayList<>();
//        startBtn.setDisable(false);
//        // get singletons' instances
//        gm = GameState.getInstance();
//        gm.addPropertyChangeListener(this);
//        ctrl = Controller.getInstance();
//        Thread crtlThread = new Thread(ctrl);
//        crtlThread.start();
//        // add event filter to info-labels
//
//    }
//
//
//    // Processing 2D setup
//    private static ProcessingBase sketch2D;
//    private Application app;
//
//
//    public void setJavafxApp(Application jfxApp) {
//        app = jfxApp;
//    }
//
//    public static void setSketch(ProcessingBase sketch) {
//        SceneController.sketch2D = sketch;
//    }
//
//    @Override
//    public void update(Object object) {
//        this.obsList = ((P2DMap) object).getObstacleList();
//    }
//
//    private void mySetFormatter(CustomTextField txtField) {
//        // Create new text filter
//        UnaryOperator<TextFormatter.Change> floatFilter = change -> {
//            String newText = change.getControlNewText();
//            // if proposed change results in a valid value, return change as-is:
//            if (newText.matches("-?(\\d{0,7}([\\.]\\d{0,4}))?")) {
//                return change;
//            } else if ("-".equals(change.getText())) {
//
//                // if user types or pastes a "-" in middle of current text,
//                // toggle sign of value:
//
//                if (change.getControlText().startsWith("-")) {
//                    // if we currently start with a "-", remove first character:
//                    change.setText("");
//                    change.setRange(0, 1);
//                    // since we're deleting a character instead of adding one,
//                    // the caret position needs to move back one, instead of
//                    // moving forward one, so we modify the proposed change to
//                    // move the caret two places earlier than the proposed change:
//                    change.setCaretPosition(change.getCaretPosition() - 2);
//                    change.setAnchor(change.getAnchor() - 2);
//                } else {
//                    // otherwise just insert at the beginning of the text:
//                    change.setRange(0, 0);
//                }
//                return change;
//            }
//            // invalid change, veto it by returning null:
//            return null;
//        };
//        txtField.setTextFormatter(
//                new TextFormatter<>(new FloatStringConverter(), 0.0f, floatFilter));
//    }
//
//    @Override
//    public void propertyChange(PropertyChangeEvent evt) {
//        String propertyName = evt.getPropertyName();
//        switch (propertyName) {
//            case "ID" -> {
//                // set id in the combobox
//                for (double id : gm.getGreenId()) greenId.add(String.valueOf((int) id));
//                startPos.getItems().addAll(greenId);
//                startPos.getItems().sorted();
//            }
//            case "ANIMATION" -> {
//                bufferAnimation.clear();
//                bufferAnimation.put(0, gm.getAnimation());
//                Platform.runLater(() -> pixelBufferAnimation.updateBuffer(b -> null));
//            }
//
//            case "BW" -> {
//                bufferBW.clear();
//                bufferBW.put(0, gm.getRaw());
//                Platform.runLater(() -> pixelBufferBW.updateBuffer(b -> null));
//            }
//            case "ERROR_STID" -> errorStartIdRecovery();
//            case "PATHLABEL" -> Platform.runLater(()-> pathLabel.setText((String) evt.getNewValue()));
//
//            default -> System.out.println("Not Mapped Case.");
//        }
//    }
//
//
//    private void errorStartIdRecovery() {
//        Platform.runLater(() -> {
//            List<CommandLinksButtonType> links = Arrays.asList(
//                    new CommandLinksButtonType("Continue",
//                            "Punto iniziale e finale del percorso coincidenti. Procedere con la simulazione.", false),
//                    new CommandLinksButtonType("ChangeId",
//                            "Selezione un altro nodo verde da cui partire.", true)
//            );
//            CommandLinksDialog dlg = new CommandLinksDialog(links);
//            dlg.setOnCloseRequest(event -> {
//                        String result = dlg.getResult().getText();
//                        switch (result) {
//                            case "Continue" -> {
//                                tabPane.getSelectionModel().select(infoTab);
//                                controlTab.setClosable(true);
//                                controlTab.setDisable(true);{*
//                                //TODO: start thread to make magic UwU
//                            }
//                            case "ChangeId" ->
//                                // remove selected id from list
//                                startPos.getItems().remove(startPos.getSelectionModel().getSelectedIndex());
//
//                        }
//                    }
//            );
//            dlg.setTitle("Il rover non necessità di spostarsi.");
//            dlg.getDialogPane().setContentText("Seleziona azione da eseguire");
//            dlg.showAndWait();
//        });
//    }
//
//    private void resetP2D(){
//        sketch2D.resume();
//
//        sketch2D.setup();
//        greenId.clear();
//        startPos.getSelectionModel().clearSelection();
//        startPos.getItems().clear();
//        gm.setGreenId(new double[]{});
//    }
//
//    private void switchTab(String tabName){
//        for (Tab t : tabPane.getTabs()){
//            if(t.getText().equals(tabName)){
//                t.setClosable(false);
//                t.setDisable(false);
//                tabPane.getSelectionModel().select(t);
//            }else {
//                t.setDisable(true);
//                t.setClosable(true);
//            }
//        }
//    }
//
//    private void clearAnimationBuffer(){
//        bufferBW = ByteBuffer.allocateDirect(4 * WIDTH * HEIGHT);
//        bufferAnimation = ByteBuffer.allocateDirect(4 * WIDTH * HEIGHT);
//        pixelBufferBW = new PixelBuffer<>(WIDTH, HEIGHT, bufferBW, PixelFormat.getByteBgraPreInstance());
//        pixelBufferAnimation = new PixelBuffer<>(WIDTH, HEIGHT, bufferAnimation, PixelFormat.getByteBgraPreInstance());
//        imgBW = new WritableImage(pixelBufferBW);
//        imgAnimation = new WritableImage(pixelBufferAnimation);
//        map.setImage(imgAnimation);
//        basicMap.setImage(imgBW);
//    }
//    private void clearInfoTab(){
//        objName.setText("");
//        pathLabel.setText("");
//    }
//    private void clearControlTab(){
//        shapeGroup.selectToggle(null);
//        radioGroup.selectToggle(paraRadio);
//        rollField.setText("0.0");
//        yawField.setText("0.0");
//        pitchField.setText("0.0");
//    }
//    @FXML
//    public void onBackControlAction() {
//        // clear info tab
//        clearInfoTab();
//        // reset control tab
//        clearControlTab();
//        // switch to the control tab
//        switchTab("Control");
//    }
//    @FXML
//    public void onBackSetupAction() {
//        // reset p2d map
//        resetP2D();
//        // clear control tab
//        clearControlTab();
//        // clear images
//        clearAnimationBuffer();
//        // switch to the setup tab
//        switchTab("Setup");
//    }
}