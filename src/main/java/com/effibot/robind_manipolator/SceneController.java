package com.effibot.robind_manipolator;
import com.effibot.robind_manipolator.Processing.ProcessingBase;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class SceneController implements Initializable {
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
    private Label obsNumber;

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

    @FXML
    public void onContinueButtonClick() {
        // load images
        Image basicMapImage = new Image(String.valueOf(getClass().getResource("img/mappaPre.jpeg")),
                256d,256d,true,true);
        basicMap.setImage(basicMapImage);
        Image postProcMapImage = new Image(String.valueOf(getClass().getResource("img/mappaPost.jpeg")),
                512d,512d,true,true);
        // disable setup tab and select info tab
        map.setImage(postProcMapImage);
        setupTab.setClosable(true);
        setupTab.setDisable(true);
        tabPane.getSelectionModel().select(infoTab);
    }
    @FXML
    public void onDismissButtonClick() {
        //TODO: implementa funzionalità annulla
    }

    @FXML
    public void onObjectButtonAction(ActionEvent actionEvent) {
        //TODO: implementa azione basata sull'oggetto chiamante
        // in base al bottone che chiama il metodo, carica l'oggetto

        String id = ((Node)actionEvent.getSource()).getId();
        switch (id) {
            case "cube":
                System.out.println(id);
                break;
            case "cone":
                System.out.println(id);
                break;
            case "sphere":
                System.out.println(id);
                break;
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sphereBtn.setId("sphere");
        cubeBtn.setId("cube");
        coneBtn.setId("cone");

        // blocco dei divisori degli splitpane
        SplitPane.Divider mainDivider = mainView.getDividers().get(0);
        double mainDividerPosition = mainDivider.getPosition();
        mainDivider.positionProperty().addListener((observable, oldValue, newValue) -> mainDivider.setPosition(mainDividerPosition));
        SplitPane.Divider topDivider = topSplit.getDividers().get(0);
        double topDividerPosition = topDivider.getPosition();
        topDivider.positionProperty().addListener((observable, oldValue, newValue) -> topDivider.setPosition(topDividerPosition));

        // Setup delle label di info
        obsNumber.setText("");
        pathLabel.setText("");
        infoTab.setClosable(true);
        infoTab.setDisable(true);
    }
    // Processing 2D setup
    public ProcessingBase sketch;
    Application app;
    public void setJavafxApp(Application jfxApp) {
        app = jfxApp;
    }
    public void setSketch(ProcessingBase sketch) { this.sketch = sketch; }

}
