package com.effibot.robind_manipolator;
import com.effibot.robind_manipolator.Processing.*;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import org.controlsfx.control.SegmentedButton;
import org.controlsfx.control.textfield.CustomTextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
public class SceneController implements Initializable, Observer {
    @FXML
    public SegmentedButton segButtonBar;
    @FXML
    private Button cancel;
    @FXML
    private Tab controlTab;
    @FXML
    private ComboBox method;
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
    private AnchorPane paneTwo;
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
    private final ToggleGroup group = new ToggleGroup();

    private final ObservableList<String> methods = FXCollections.observableArrayList("Parabolic", "Quintic", "Cubic");
    private float roll, pitch, yaw;
    private ArrayList<Integer> sequence;

    @FXML
    public void onContinueButtonClick() {
//        ArrayList<Obstacle> dummy = new ArrayList<>();
//        dummy.add(new Obstacle(sketch,2*(40-10),2*(40-10),50,120,100,0));
//        ((P2DMap)sketch).setObstacleList(dummy);
        if (obsList != null) {
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
        System.out.println(sequence);
    }

    @FXML
    public void onStartAction(ActionEvent actionEvent) {
        String interp = ((String) method.getValue()).toLowerCase();
        roll = Integer.parseInt(rollField.getText());
        pitch = Integer.parseInt(pitchField.getText());
        yaw = Integer.parseInt(yawField.getText());
        boolean condition = sequence.isEmpty() || method.getSelectionModel().isEmpty() ||
                rollField.getText().isEmpty() || pitchField.getText().isEmpty() || rollField.getText().isEmpty();
        if (condition) {

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
        method.getItems().addAll("Parabolic", "Quintic", "Cubic");
        method.setVisibleRowCount(3);
        method.setPromptText("Metodo");
        // Setup RPY default value
        rollField.setText(String.valueOf(0));
        addTextListener(rollField);
        pitchField.setText(String.valueOf(0));
        addTextListener(pitchField);
        yawField.setText(String.valueOf(0));
        addTextListener(yawField);
        // init sequence
        sequence = new ArrayList<>();

        startBtn.setDisable(false);
    }

    private void addTextListener(CustomTextField txtField) {
        txtField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                        rollField.setText(oldValue);
                    }
//                    if (newValue.equals(String.valueOf(0)) || oldValue.equals(String.valueOf(0)))
//                        rollField.setText(oldValue + "." + newValue);
                    //TODO doppio zero va trimmato e mezzo a singolo 0
                    else if (newValue.equals("."))
                        rollField.setText("0" + newValue);
                }
        );
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

    private List<Obstacle> obsList;

    @Override
    public void update(Object object) {
        this.obsList = ((P2DMap) object).getObstacleList();
    }
}
