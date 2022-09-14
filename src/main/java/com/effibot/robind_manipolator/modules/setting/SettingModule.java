package com.effibot.robind_manipolator.modules.setting;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.validators.DoubleRangeValidator;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import com.dlsc.workbenchfx.Workbench;
import com.dlsc.workbenchfx.model.WorkbenchDialog;
import com.dlsc.workbenchfx.model.WorkbenchModule;
import com.effibot.robind_manipolator.bean.IntroBean;
import com.effibot.robind_manipolator.bean.SettingBean;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SplitPane;
import javafx.scene.image.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class SettingModule extends WorkbenchModule implements PropertyChangeListener {
    private IntroBean introBean;
    private final SettingController settingController;
    Workbench wb = this.getWorkbench();
    private static final int HEIGHT = 1024;
    private static final int WIDTH = 1024;
    private ByteBuffer bufferBW = ByteBuffer.allocateDirect(4 * WIDTH * HEIGHT);
    private ByteBuffer bufferAnimation = ByteBuffer.allocateDirect(4 * WIDTH * HEIGHT);
    private PixelBuffer<ByteBuffer> pixelBufferBW = new PixelBuffer<>(WIDTH, HEIGHT, bufferBW, PixelFormat.getByteBgraPreInstance());

    private PixelBuffer<ByteBuffer> pixelBufferAnimation = new PixelBuffer<>(WIDTH, HEIGHT, bufferAnimation, PixelFormat.getByteBgraPreInstance());
    private WritableImage imgBW = new WritableImage(pixelBufferBW);
    private WritableImage imgAnimation = new WritableImage(pixelBufferAnimation);
    private ImageView basicMap;
    private ImageView map;

    private VBox vb;

    private FormRenderer form;
    private final ObjectProperty<Double> id = new SimpleObjectProperty<>();
    private final ObjectProperty<String> shape = new SimpleObjectProperty<>();
    private final ObjectProperty<String> selectedMethod = new SimpleObjectProperty<>();
    private final SettingBean settingBean;
    private final ListProperty<String> shapeName = new SimpleListProperty<>(
            FXCollections.observableArrayList(
                    Arrays.asList("Sfera", "Cono", "Cubo")
            ));
    private final ListProperty<String> methods = new SimpleListProperty<>(
            FXCollections.observableArrayList(
                    Arrays.asList("Paraboloic", "Quintic", "Cubic")
            ));

    public SettingModule(SettingBean settingBean) {
        super("Impostazioni", MaterialDesign.MDI_SETTINGS);
        this.settingBean = settingBean;
        this.settingBean.addPropertyChangeListener(this);
        settingController = new SettingController(this, this.settingBean);

    }

    private FormRenderer setupForm() {
        // Construct control form
        /* Id e Forma */
        Field shapeField = Field.ofSingleSelectionType(shapeName, shape)
                .label("Forma")
                .required(true)
                .tooltip("Forma da raggiungere");
        shape.bindBidirectional(settingBean.selectedShapeProperty());
        shape.addListener(change -> settingController.setIdByShape(shape.get()));
        Field idField = Field.ofSingleSelectionType(settingBean.idListProperty(), id)
                .label("Start ID")
                .required(true)
                .tooltip("ID di un nodo Verde");
        id.bindBidirectional(settingBean.selectedIdProperty());
        /* Interpolante */
        Field interpField = Field.ofSingleSelectionType(methods, selectedMethod)
                .label("Metodo").required(true)
                .tooltip("Metodo di interpolazione del percorso");
        selectedMethod.bindBidirectional(settingBean.selectedMethodProperty());
        /* Roll Pitch Yaw */
        Field rollField = Field.ofDoubleType(0.0)
                .label("Roll").required("Specificare un valore")
                .validate(DoubleRangeValidator.between(0.0d, 360.0d, "Valore non ammesso"));

        Field pitchField = Field.ofDoubleType(0.0)
                .label("Pitch").required("Specificare un valore")
                .validate(DoubleRangeValidator.between(0.0d, 360.0d, "Valore non ammesso"));

        Field yawField = Field.ofDoubleType(0.0)
                .label("Yaw").required("Specificare un valore")
                .validate(DoubleRangeValidator.between(0.0d, 360.0d, "Valore non ammesso"));
        Form controlForm = Form.of(Group.of(shapeField, idField, interpField, rollField, yawField, pitchField));
        return new FormRenderer(controlForm);
    }

    private void setSquareSizes(Pane n, double size, double anchor) {
        n.setPrefHeight(size);
        n.setPrefWidth(size);
        n.setMinWidth(size);
        n.setMinHeight(size);
        n.setMaxWidth(size);
        n.setMaxHeight(size);
        AnchorPane.setBottomAnchor(n, anchor);
        AnchorPane.setTopAnchor(n, anchor);
        AnchorPane.setLeftAnchor(n, anchor);
        AnchorPane.setRightAnchor(n, anchor);
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
        form = setupForm();
        form.setDisable(true);
        form.setPadding(Insets.EMPTY);
        // Start Button
        Button start = new Button("Start");
        settingController.onStartAction(start);
        // Back Button
        Button back = new Button("Indietro");
        // Bottom Hbox for start and back
        HBox btmBox = new HBox();
        btmBox.getChildren().addAll(back, start);
        btmBox.setAlignment(Pos.CENTER);
        btmBox.setSpacing(160);
        vb.getChildren().addAll(form, btmBox);
        // container
        AnchorPane anchorVB = new AnchorPane(vb);
        setSquareSizes(vb, 360, 0);

        // Bottom Spllit -> BW UI
        basicMap = new ImageView();
        basicMap.setFitHeight(360);
        basicMap.setFitWidth(360);
        basicMap.setImage(imgBW);
        AnchorPane anchorBW = new AnchorPane(basicMap);
        setSquareSizes(anchorBW, 360, 0);
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
        map = new ImageView();
        map.setImage(imgAnimation);
        map.setPreserveRatio(true);
        AnchorPane rightPane = new AnchorPane(map);
        setSquareSizes(rightPane, 720, 0);
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
                bufferAnimation.put(0, settingBean.getAnimation());
                Platform.runLater(() -> pixelBufferAnimation.updateBuffer(b -> null));
            }

            case "BW" -> {
                bufferBW.clear();
                bufferBW.put(0, settingBean.getRaw());
                Platform.runLater(() -> pixelBufferBW.updateBuffer(b -> null));
            }
            case "ERROR_STID" -> errorStartIdRecovery();
            case "FINISH" -> {
                form.setDisable(false);
            }
            case "OBSUPDATE" -> System.out.println("DIOPORCO2");
            default -> System.out.println("Not Mapped Case.");

        }

    }

    private void errorStartIdRecovery() {
        Platform.runLater(() -> wb.showDialog(WorkbenchDialog.builder(
                        "Il rover non necessita di spostarsi",
                        "Continuare (OK) la simulazione o selezionare un altro ID (CANCEL).",
                        ButtonType.OK, ButtonType.CANCEL).blocking(true)
                .onResult(buttonType -> {
                    switch (buttonType.getText()) {
                        case "OK" -> {
                        }
                        case "CANCEL" -> {
                        }
                    }
                }).build()
        ));
    }

    private void clearAnimationBuffer() {
        bufferBW = ByteBuffer.allocateDirect(4 * WIDTH * HEIGHT);
        bufferAnimation = ByteBuffer.allocateDirect(4 * WIDTH * HEIGHT);
        pixelBufferBW = new PixelBuffer<>(WIDTH, HEIGHT, bufferBW, PixelFormat.getByteBgraPreInstance());
        pixelBufferAnimation = new PixelBuffer<>(WIDTH, HEIGHT, bufferAnimation, PixelFormat.getByteBgraPreInstance());
        imgBW = new WritableImage(pixelBufferBW);
        imgAnimation = new WritableImage(pixelBufferAnimation);
        map.setImage(imgAnimation);
        basicMap.setImage(imgBW);
    }


    public VBox getVb() {
        return vb;
    }

    public void setVb(VBox vb) {
        this.vb = vb;
    }
}