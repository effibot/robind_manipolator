package com.effibot.robind_manipolator.modules.setting;

import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.model.validators.DoubleRangeValidator;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import com.dlsc.workbenchfx.Workbench;
import com.dlsc.workbenchfx.model.WorkbenchModule;
import com.effibot.robind_manipolator.bean.RobotBean;
import com.effibot.robind_manipolator.bean.SettingBean;
import com.effibot.robind_manipolator.tcp.TCPFacade;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.image.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

public class SettingModule extends WorkbenchModule implements PropertyChangeListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(SettingModule.class.getName());
    private final SettingController settingController;
    private static final int HEIGHT = 1024;
    private static final int WIDTH = 1024;
    private final Workbench wb;
    private final ByteBuffer bufferBW = ByteBuffer.allocateDirect(4 * WIDTH * HEIGHT);
    private final ByteBuffer bufferAnimation = ByteBuffer.allocateDirect(4 * WIDTH * HEIGHT);
    private final PixelBuffer<ByteBuffer> pixelBufferBW = new PixelBuffer<>(WIDTH, HEIGHT, bufferBW, PixelFormat.getByteBgraPreInstance());

    private final PixelBuffer<ByteBuffer> pixelBufferAnimation = new PixelBuffer<>(WIDTH, HEIGHT, bufferAnimation, PixelFormat.getByteBgraPreInstance());
    private final WritableImage imgBW = new WritableImage(pixelBufferBW);
    private final WritableImage imgAnimation = new WritableImage(pixelBufferAnimation);

    private VBox vb;

    private final ObjectProperty<Integer> id = new SimpleObjectProperty<>();
    private final ObjectProperty<String> shape = new SimpleObjectProperty<>();
    private final ObjectProperty<String> selectedMethod = new SimpleObjectProperty<>();
    private Form controlForm;


    private final SettingBean settingBean;
    private RobotBean robotBean;
    private final ListProperty<String> shapeName = new SimpleListProperty<>(
            FXCollections.observableArrayList(
                    List.of()
            ));
    private final ListProperty<String> methods = new SimpleListProperty<>(
            FXCollections.observableArrayList(
                    Arrays.asList("Paraboloic", "Quintic", "Cubic")
            ));
    private final DoubleProperty pitchValue = new SimpleDoubleProperty(0.1);
    private final DoubleProperty rollValue = new SimpleDoubleProperty(0);

    private final DoubleProperty yawValue = new SimpleDoubleProperty(0);
    public SettingModule(SettingBean settingBean, RobotBean robotBean, Workbench wb) {
        super("Impostazioni", MaterialDesign.MDI_SETTINGS);
        this.settingBean = settingBean;
        this.settingBean.addPropertyChangeListener(this);
        this.robotBean = robotBean;
        this.robotBean.addPropertyChangeListener(this);
        this.wb = wb;
        settingController = new SettingController(this, this.settingBean, this.robotBean,this.wb);
        setShapeName();

    }
    private void setShapeName() {
        int len = robotBean.getObsList().size();
        switch (len){
            case 1 -> shapeName.add("Sfera");
            case 2 -> shapeName.addAll("Sfera", "Cono");
            default -> shapeName.addAll("Sfera", "Cono" , "Cubo");
        }
    }

    private FormRenderer setupForm() {
        // Construct control form
        /* Id e Forma */
        Field<SingleSelectionField<String>> shapeField = Field.ofSingleSelectionType(shapeName, shape)
                .label("Forma")
                .required(true)
                .tooltip("Forma da raggiungere");
        shape.bindBidirectional(settingBean.selectedShapeProperty());
        shape.addListener(change -> settingController.setIdByShape(shape.get()));
        Field<SingleSelectionField<Integer>> idField = Field.ofSingleSelectionType(settingBean.idListProperty(), id)
                .label("Start ID")
                .required(true)
                .tooltip("ID di un nodo Verde");
        id.bindBidirectional(settingBean.selectedIdProperty());
        /* Interpolante */
        Field<SingleSelectionField<String>> interpField = Field.ofSingleSelectionType(methods, selectedMethod)
                .label("Metodo").required(true)
                .tooltip("Metodo di interpolazione del percorso");
        selectedMethod.bindBidirectional(settingBean.selectedMethodProperty());
        /* Roll Pitch Yaw */
        String valueNonCompliant = "Valore non ammesso";
        String specifyValue = "Specificare un valore";
        Field<DoubleField> rollField = Field.ofDoubleType(rollValue)
                .label("Roll").required(specifyValue)
                .validate(DoubleRangeValidator.between(0.0d, 360.0d, valueNonCompliant));

        Field<DoubleField> pitchField = Field.ofDoubleType(pitchValue)
                .label("Pitch").required(specifyValue)
                .validate(DoubleRangeValidator.between(0.1d, 360.0d, valueNonCompliant));


        Field<DoubleField> yawField = Field.ofDoubleType(yawValue)
                .label("Yaw").required(specifyValue)
                .validate(DoubleRangeValidator.between(0.0d, 360.0d, valueNonCompliant));
        controlForm = Form.of(Group.of(shapeField, idField, interpField, rollField, pitchField, yawField));
        settingController.setControlForm(controlForm);
        return new FormRenderer(controlForm);
    }

    private void setSquareSizes(Pane n, double size) {
        n.setPrefHeight(size);
        n.setPrefWidth(size);
        n.setMinWidth(size);
        n.setMinHeight(size);
        n.setMaxWidth(size);
        n.setMaxHeight(size);
        AnchorPane.setBottomAnchor(n, (double) 0);
        AnchorPane.setTopAnchor(n, (double) 0);
        AnchorPane.setLeftAnchor(n, (double) 0);
        AnchorPane.setRightAnchor(n, (double) 0);
    }

    @Override
    public Node activate() {
        // parent node for this module
        HBox hb = new HBox();
        //----------------------------//
        // Left Panel UI
        SplitPane sp = new SplitPane();
        // Upper split -> Form
        vb = new VBox();
        vb.setDisable(true);
        FormRenderer form = setupForm();
        form.setPadding(Insets.EMPTY);
        // Start Button
        JFXButton start2D = new JFXButton("Start 2D");
        settingController.onStart2DAction(start2D);
        // Start 3D Button
        JFXButton start3d = new JFXButton("Start 3D");
        settingController.onStart3DAction(start3d);
        // Back Button
        JFXButton back = new JFXButton("Indietro");
        settingController.onBackAction(back);
        settingController.setOnHoverInfo(wb.getToolbarControlsRight().get(0));
        // Bottom Hbox for start and back
        HBox btmBox = new HBox();
        btmBox.getChildren().addAll(back, start2D,start3d);
        btmBox.setAlignment(Pos.CENTER);
        btmBox.setSpacing(50);
        vb.getChildren().addAll(form, btmBox);
        // container
        AnchorPane anchorVB = new AnchorPane(vb);
        setSquareSizes(vb, 360);

        // Bottom Spllit -> BW UI
        ImageView basicMap = new ImageView();
        basicMap.setFitHeight(360);
        basicMap.setFitWidth(360);
        basicMap.setImage(imgBW);
        AnchorPane anchorBW = new AnchorPane(basicMap);
        setSquareSizes(anchorBW, 360);
        basicMap.setPreserveRatio(true);
        basicMap.fitHeightProperty().bind(anchorBW.heightProperty());
        basicMap.fitWidthProperty().bind(anchorBW.widthProperty());

        // Left Panel graphic settings
        sp.getItems().addAll(anchorVB, anchorBW);
        sp.setOrientation(Orientation.VERTICAL);
        sp.setMaxSize(360, 720);
        sp.setMinSize(360, 720);
        sp.setPrefSize(360, 720);

        // Right Side -> Colored Map
        // Animation Img
        ImageView map = new ImageView();
        map.setImage(imgAnimation);
        map.setPreserveRatio(true);
        AnchorPane rightPane = new AnchorPane(map);
        setSquareSizes(rightPane, 720);
        map.fitHeightProperty().bind(rightPane.heightProperty());
        map.fitWidthProperty().bind(rightPane.widthProperty());
        hb.setSpacing(0);
        hb.getChildren().addAll(sp, rightPane);
        hb.setAlignment(Pos.TOP_LEFT);
        return hb;

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "ANIMATION" -> {
                bufferAnimation.clear();
                byte[] animation = new byte[0];
                if(evt.getNewValue() instanceof RobotBean)
                     animation = robotBean.getAnimation();
                else if (evt.getNewValue() instanceof SettingBean) {
                    animation = settingBean.getAnimation();
                }
                bufferAnimation.put(0, animation);
                Platform.runLater(() -> pixelBufferAnimation.updateBuffer(b -> null));
            }

            case "BW" -> {
                bufferBW.clear();
                bufferBW.put(0, settingBean.getRaw());
                Platform.runLater(() -> pixelBufferBW.updateBuffer(b -> null));
            }
            case "FINISH" -> {
                vb.setDisable(false);

            }
            case "OBSUPDATE" -> LOGGER.info("OBS UPDATE");
            case "PLOT"->{}
            default -> LOGGER.warn("Not Mapped Case.");

        }

    }


    public VBox getVb() {
        return vb;
    }

    public void setVb(VBox vb) {
        this.vb = vb;
    }
    @Override
    public boolean destroy(){
        TCPFacade.getInstance().resetSocket();
        wb.getModules().remove(this);
        settingController.closeProcessing();
        return true;
    }

    public WritableImage getMap() {
        return this.imgAnimation;
    }
}