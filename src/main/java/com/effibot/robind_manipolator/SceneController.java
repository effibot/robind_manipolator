package com.effibot.robind_manipolator;
import com.effibot.robind_manipolator.MATLAB.Matlab;
import com.effibot.robind_manipolator.MATLAB.info;
import com.effibot.robind_manipolator.Processing.*;
import com.effibot.robind_manipolator.Processing.Observer;
import com.mathworks.toolbox.javabuilder.MWException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.scene.control.TextFormatter;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.SegmentedButton;
import org.controlsfx.control.textfield.CustomTextField;

import java.net.URL;
import java.util.*;
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
    private Tab setupTab, infoTab;
    @FXML
    private List<Obstacle> obsList;
    @FXML
    private RadioButton paraRadio, quinticRadio, cubicRadio;
    private ToggleGroup radioGroup;
    private final ObservableList<String> methods = FXCollections.observableArrayList("Parabolic", "Quintic", "Cubic");
    private float roll, pitch, yaw;
    private ArrayList<Integer> sequence;
    private Vector<Integer> startPosition;
    private static  Matlab matlabInstance;
    @FXML
    public void onContinueButtonClick() throws MWException {
//        ArrayList<Obstacle> dummy = new ArrayList<>();
//        dummy.add(new Obstacle(sketch,2*(40-10),2*(40-10),50,120,100,0));
//        ((P2DMap)sketch).setObstacleList(dummy);
        if (obsList != null) {
            matlabInstance=Matlab.getInstance();
            double[][] obslist = matlabInstance.Obs2List(obsList);
            double[] dim = {1024.0,1024.0};
            info res = matlabInstance.mapgeneration(obslist,dim);
            // load images
            Image basicMapImage = new Image(String.valueOf(getClass().getResource("img/mappaPre.jpeg")),
                    256d, 256d, true, true);
            basicMap.setImage(basicMapImage);
            Image postProcMapImage = new Image(String.valueOf(getClass().getResource("img/mappaPost.jpeg")),
                    512d, 512d, true, true);
            // disable setup tab and select info tab
            map.setImage(postProcMapImage);
            setupTab.setClosable(true);
            setupTab.setDisable(true);
            tabPane.getSelectionModel().select(controlTab);
            controlTab.setDisable(false);
            // close 2D map
            sketch.removeObserver(this);
            sketch.noLoop();
            sketch.stop();
            sketch.exit();
            // open 3D map
            sketch = new P3DMap(obsList);
            this.setSketch(sketch);
            sketch.setJavaFX(this);
            sketch.run(sketch.getClass().getSimpleName());
            ((Main)app).setSketch(sketch);
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
        }
    }
    @FXML
    public void onStartAction(ActionEvent actionEvent) {
        roll = Float.parseFloat(rollField.getText());
        pitch = Float.parseFloat(pitchField.getText());
        yaw = Float.parseFloat(yawField.getText());
        boolean condition = sequence.isEmpty() ||
                rollField.getText().isEmpty() || pitchField.getText().isEmpty() || rollField.getText().isEmpty();
        if (condition) {
//            TODO> implementare il salto a matlab
            System.out.println("Start");
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sphereBtn.setId("sphere");
        cubeBtn.setId("cube");
        coneBtn.setId("cone");

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
        // setup robot start position combobox

        //! this is for dummy usage
        List<String> greenId = randomSequence(10);
        final ObservableList<String> gids = FXCollections.observableArrayList(greenId);
        //! -----------------------------
        startPos.getItems().addAll(gids);   //? replace gids with the sequence list from the map
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
        quinticRadio.setToggleGroup(radioGroup);
        cubicRadio.setToggleGroup(radioGroup);
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
    private List<String> randomSequence(int n){
        ArrayList<String> list = new ArrayList<>(n);
        Random random = new Random();

        for (int i = 0; i < n; i++)
        {
            list.add(String.valueOf(random.nextInt(1000)));
        }
        return list;
    }

    // Processing 2D setup
    public ProcessingBase sketch;
    Application app;


    public void setJavafxApp(Application jfxApp) {
        app = jfxApp;
    }

    public void setSketch(ProcessingBase sketch) {
        this.sketch = sketch;
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
