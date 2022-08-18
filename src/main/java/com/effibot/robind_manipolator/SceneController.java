package com.effibot.robind_manipolator;
import com.effibot.robind_manipolator.matlab.Matlab;
import com.effibot.robind_manipolator.matlab.info;
import com.effibot.robind_manipolator.Processing.*;
import com.effibot.robind_manipolator.Processing.Observer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.util.converter.FloatStringConverter;
import javafx.scene.control.TextFormatter;
import org.controlsfx.control.SegmentedButton;
import org.controlsfx.control.textfield.CustomTextField;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.function.UnaryOperator;

public class SceneController implements Initializable, Observer {
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
    private static  Matlab matlabInstance= Matlab.getInstance();
    private static List<String> greenId= new ArrayList<>();
    private static final Utils util= new Utils();

    @FXML
    public void onContinueButtonClick() throws ExecutionException, InterruptedException {
//        ArrayList<Obstacle> dummy = new ArrayList<>();
//        dummy.add(new Obstacle(sketch,2*(40-10),2*(40-10),50,120,100,0));
//        ((P2DMap)sketch).setObstacleList(dummy);
        if (obsList != null) {
            double[][] obslist = util.obs2List(obsList);
            double[] dim = {1024.0,1024.0};
            long st1=System.currentTimeMillis();
            matlabInstance.mapgeneration(obslist,dim);
            long end1 = System.currentTimeMillis();
            System.out.println("Elapsed Time in milli seconds: "+ (end1-st1));
            DecimalFormat format = new DecimalFormat("0.#");

            info ids = matlabInstance.getInfo();
            while (ids==null){ids = matlabInstance.getInfo();}
            for (Double id : ids.gids()){

                greenId.add(format.format(id));
            }
            startPos.getItems().addAll(greenId);   //? replace gids with the sequence list from the map

            // load images
//            Image basicMapImage = new Image("file:mapgenerationimg/generated/bw.png",
//                    256d, 256d, true, true);
            basicMap.setImage(SwingFXUtils.toFXImage(ids.bw(),null));

            ArrayList<Image> imgs = (ArrayList<Image>) util.makeImage(ids.mapwk());
            // disable setup tab and select info tab
            map.setImage(imgs.get(0));
            Timeline timeLine = new Timeline();
            Collection<KeyFrame> frames = timeLine.getKeyFrames();
            Duration frameGap = Duration.millis(150);
            Duration frameTime = Duration.ZERO;
            int sz = imgs.size();
            for (int i = 0;i<sz;i++) {
                frameTime = frameTime.add(frameGap);
                Image imgi = imgs.get(i);
                frames.add(new KeyFrame(frameTime, e -> map.setImage(imgi)));
            }
            timeLine.setCycleCount(1);
            timeLine.setOnFinished(finish->map.setImage(SwingFXUtils.toFXImage(matlabInstance.getInfo().graph(),null)));
            timeLine.play();

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
            //TODO: implements popup to specify at leas one obstacle
        }
    }

    @FXML
    public void onCancelButtonClick() {
        obsList.remove(obsList.size() - 1);
        ((P2DMap) sketch).setObstacleList((ArrayList<Obstacle>) obsList);
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
    public void onStartAction(ActionEvent actionEvent) throws ExecutionException, InterruptedException {
        float roll = Float.parseFloat(rollField.getText());
        float pitch = Float.parseFloat(pitchField.getText());
        float yaw = Float.parseFloat(yawField.getText());
//        boolean condition = sequence.isEmpty() ||
//                rollField.getText().isEmpty() || pitchField.getText().isEmpty() || rollField.getText().isEmpty();
        double startid = Double.parseDouble(startPos.getValue());
        double selectedShape = Double.valueOf(shapeGroup.getSelectedToggle().getUserData().toString()).intValue();
        String method = (String) radioGroup.getSelectedToggle().getUserData();
//        if (!condition) {
            double[][] shapepositions = matlabInstance.getInfo().shapepos();
            double[] obspos = {shapepositions[(int)selectedShape][1],shapepositions[(int)selectedShape][2]};
            matlabInstance.pathgeneration((int) startid,obspos ,method);
            // load images
            basicMap.setImage(SwingFXUtils.toFXImage(matlabInstance.getInfo().graph(),null));
            ArrayList<Image> imgs =(ArrayList<Image>)  util.makeImage(matlabInstance.getPath().mapsimimg());
            map.setImage(imgs.get(0));
            Timeline timeLine = new Timeline();
            Collection<KeyFrame> frames = timeLine.getKeyFrames();
            Duration frameGap = Duration.millis(150);
            Duration frameTime = Duration.ZERO;
            int sz = imgs.size();
            for (int i = 0;i<sz;i++) {
                frameTime = frameTime.add(frameGap);
                Image imgi = imgs.get(i);
                frames.add(new KeyFrame(frameTime, e -> map.setImage(imgi)));
            }
            timeLine.setCycleCount(1);
            timeLine.setOnFinished(finish-> {
                try {
                    setUpStage();
                } catch (ExecutionException | InterruptedException e) {

                    Thread.currentThread().interrupt();
                }
            });
            timeLine.play();


//        }


    }

    private void setUpStage() throws ExecutionException, InterruptedException {
            // open 3D map
            P3DMap sketchmap = new P3DMap(obsList);
            SceneController.setSketch(sketchmap);
            sketchmap.setJavaFX(this);
            double[][] pos = matlabInstance.getPath().q();
            sketchmap.setInitPos(pos[0]);
            sketch.run(sketch.getClass().getSimpleName());
            ((Main)app).setSketch(sketchmap);
            matlabInstance.runsimulation(10,200);
            sketchmap.setsysout(matlabInstance.getSysout());

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
    }


    // Processing 2D setup
    private static ProcessingBase sketch;
    Application app;


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
    private void mySetFormatter(CustomTextField txtField) {
        // Create new text filter
        UnaryOperator<TextFormatter.Change> floatFilter = change -> {
            String newText = change.getControlNewText();
            // if proposed change results in a valid value, return change as-is:
            if (newText.matches("-?(\\d{0,7}([\\.]\\d{0,4}))?")) {
                return change;
            } else if ("-".equals(change.getText()) ) {

                // if user types or pastes a "-" in middle of current text,
                // toggle sign of value:

                if (change.getControlText().startsWith("-")) {
                    // if we currently start with a "-", remove first character:
                    change.setText("");
                    change.setRange(0, 1);
                    // since we're deleting a character instead of adding one,
                    // the caret position needs to move back one, instead of
                    // moving forward one, so we modify the proposed change to
                    // move the caret two places earlier than the proposed change:
                    change.setCaretPosition(change.getCaretPosition()-2);
                    change.setAnchor(change.getAnchor()-2);
                } else {
                    // otherwise just insert at the beginning of the text:
                    change.setRange(0, 0);
                }
                return change ;
            }
            // invalid change, veto it by returning null:
            return null;
        };
        txtField.setTextFormatter(
                new TextFormatter<>(new FloatStringConverter(), 0.0f, floatFilter));
    }
}
