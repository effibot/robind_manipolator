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
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SplitPane;
import javafx.scene.image.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

public class SettingModule extends WorkbenchModule implements PropertyChangeListener {
    private IntroBean introBean;
    private SettingController settingController;
    Workbench wb = this.getWorkbench();
    private static final int HEIGHT = 1024;
    private static final int WIDTH = 1024;
    private ByteBuffer bufferBW = ByteBuffer.allocateDirect(4 * WIDTH * HEIGHT);
    private ByteBuffer bufferAnimation = ByteBuffer.allocateDirect(4 * WIDTH * HEIGHT);
    private PixelBuffer<ByteBuffer> pixelBufferBW = new PixelBuffer<>(WIDTH, HEIGHT, bufferBW, PixelFormat.getByteBgraPreInstance());

    private PixelBuffer<ByteBuffer> pixelBufferAnimation = new PixelBuffer<>(WIDTH, HEIGHT, bufferAnimation, PixelFormat.getByteBgraPreInstance());
    private WritableImage imgBW = new WritableImage(pixelBufferBW);
    private WritableImage imgAnimation = new WritableImage(pixelBufferAnimation);
    private  ImageView basicMap;
    private ImageView map;
    private VBox vb;
    private ObjectProperty<Double> id = new SimpleObjectProperty<>();
    private final ObjectProperty<String> shape = new SimpleObjectProperty<>();
    private SettingBean settingBean;
    private final ListProperty<String> shapeName = new SimpleListProperty<>(
            FXCollections.observableArrayList(
                    Arrays.asList("Sfera", "Cono", "Cubo")
            ));

    public SettingModule(SettingBean settingBean) {
        super("Impostazioni", MaterialDesign.MDI_SETTINGS);
        this.settingBean = settingBean;
        this.settingBean.addPropertyChangeListener(this);
        settingController = new SettingController(this, this.settingBean);

    }

    private FormRenderer setupForm(){
        // Construct control form
        Field shapeField = Field.ofSingleSelectionType(shapeName,shape)
                .label("Forma")
                .required(true)
                .tooltip("Forma da raggiungere")
                ;
        shape.addListener(change ->{
            settingController.setIdByShape(shape.get());
        });
        /* Id e Forma */
//         Field.ofSingleSelectionType(introBean.getGreenId(),id)

        Field idField = Field.ofSingleSelectionType(settingBean.idListProperty(),id)
                .label("Start ID")
                .required(true)
                .tooltip("ID di un nodo Verde")
                ;
        id.addListener(changeId->{
            System.out.println("ID:"+id.get());
            System.out.println("shape:"+shape.get());
        });
        /* Interpolante */
        ArrayList<Object> interpList = new ArrayList<>();
        Field interpField = Field.ofSingleSelectionType(interpList)
                .label("Metodo").required(true)
                .tooltip("Metodo di interpolazione del percorso")
                ;
        /* Roll Pitch Yaw */
        Field rollField = Field.ofDoubleType(0.0)
                .label("Roll").required("Specificare un valore")
                .validate(DoubleRangeValidator.between(0.0d,360.0d,"Valore non ammesso"))
                ;

        Field pitchField = Field.ofDoubleType(0.0)
                .label("Pitch").required("Specificare un valore")
                .validate(DoubleRangeValidator.between(0.0d,360.0d,"Valore non ammesso"))
                ;

        Field yawField = Field.ofDoubleType(0.0)
                .label("Yaw").required("Specificare un valore")
                .validate(DoubleRangeValidator.between(0.0d,360.0d,"Valore non ammesso"))
                ;

        idField.getRenderer().addEventFilter(ActionEvent.ACTION, actionEvent -> {

        });




        Form controlForm = Form.of(Group.of(shapeField, idField, interpField, rollField, yawField, pitchField));
        return new FormRenderer(controlForm);
    }

    @Override
    public Node activate() {

        HBox hb = new HBox();
//        Left Panel UI
        SplitPane sp = new SplitPane();

        vb = new VBox();
        vb.setMinWidth(360);
        vb.setMinHeight(360);
        vb.setMaxWidth(360);
        vb.setMaxHeight(360);

//        BW UI
        basicMap = new ImageView();
        basicMap.setFitHeight(360);
        basicMap.setFitWidth(360);
        basicMap.setImage(imgBW);
        AnchorPane anchorBW = new AnchorPane();
        anchorBW.setPrefHeight(360);
        anchorBW.setPrefWidth(360);
        anchorBW.setMinWidth(360);
        anchorBW.setMinHeight(360);
        anchorBW.setMaxWidth(360);
        anchorBW.setMaxHeight(360);
        AnchorPane.setTopAnchor(anchorBW,0d);
        AnchorPane.setLeftAnchor(anchorBW,0d);
        AnchorPane.setBottomAnchor(anchorBW,0d);
        AnchorPane.setRightAnchor(anchorBW,0d);
        basicMap.setPreserveRatio(true);
        basicMap.fitHeightProperty().bind(anchorBW.heightProperty());
        basicMap.fitWidthProperty().bind(anchorBW.widthProperty());
        anchorBW.getChildren().add(basicMap);
        vb.getChildren().add(setupForm());
//        Left Panel graphic settings
        sp.getItems().addAll(vb, anchorBW);
        sp.setOrientation(Orientation.VERTICAL);

        sp.setMaxSize(360,720);
        sp.setMinSize(360,720);
        sp.setPrefSize(360,720);

        AnchorPane hBoxAnim = new AnchorPane();
        hBoxAnim.setPrefHeight(720);
        hBoxAnim.setPrefWidth(720);
        hBoxAnim.setMinWidth(720);
        hBoxAnim.setMinHeight(720);
        hBoxAnim.setMaxWidth(720);
        hBoxAnim.setMaxHeight(720);
        AnchorPane.setTopAnchor(hBoxAnim,0d);
        AnchorPane.setLeftAnchor(hBoxAnim,0d);
        AnchorPane.setBottomAnchor(hBoxAnim,0d);
        AnchorPane.setRightAnchor(hBoxAnim,0d);

        // Animation Img
        map = new ImageView();
        map.setImage(imgAnimation);
        map.setPreserveRatio(true);
        map.fitHeightProperty().bind(hBoxAnim.heightProperty());
        map.fitWidthProperty().bind(hBoxAnim.widthProperty());

        hBoxAnim.getChildren().add(map);
        hb.setSpacing(0);
        hb.getChildren().addAll(sp, hBoxAnim);
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
            case "FINISH"-> System.out.println("DIOPORCO");
            case "OBSUPDATE"-> System.out.println("DIOPORCO2");
            default -> System.out.println("Not Mapped Case.");

        }

    }

        private void errorStartIdRecovery() {
        Platform.runLater(() -> wb.showDialog(WorkbenchDialog.builder(
                "Il rover non necessita di spostarsi",
                "Continuare (OK) la simulazione o selezionare un altro ID (CANCEL).",
                ButtonType.OK,ButtonType.CANCEL).blocking(true)
                .onResult(buttonType -> {
                    switch(buttonType.getText()){
                        case "OK"->{}
                            case "CANCEL"->{}
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

}