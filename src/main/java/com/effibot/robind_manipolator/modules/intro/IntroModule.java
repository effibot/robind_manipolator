package com.effibot.robind_manipolator.modules.intro;

import com.dlsc.workbenchfx.Workbench;
import com.dlsc.workbenchfx.model.WorkbenchModule;
import com.dlsc.workbenchfx.view.controls.MultilineLabel;
import com.effibot.robind_manipolator.processing.Observer;
import com.effibot.robind_manipolator.processing.P2DMap;
import com.effibot.robind_manipolator.processing.ProcessingBase;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.TextAlignment;
import org.kordamp.ikonli.materialdesign.MaterialDesign;


public class IntroModule extends WorkbenchModule implements Observer {
    private final ProcessingBase sketch;

    private JFXButton  cbtn;
    private JFXButton  abtn;
    public IntroModule() {
        super("Intro", MaterialDesign.MDI_ACCOUNT);

            this.sketch = new P2DMap();
            sketch.registerObserver(this);

    }

    @Override
    public Node activate() {
        Workbench wb = this.getWorkbench();
        IntroController introController = new IntroController(this, this.sketch, wb);

        Label mlb = new MultilineLabel("""
                 Premere continua per procedere con la generazione della mappa.
                 Premere annulla per riposizionare gli ostacoli.
                 Chiudere la tab per ritornare alla schermata principale.
               """);
        mlb.setTextFill(Color.rgb(33,33,33));
        mlb.setFont(Font.font("Verdana", FontPosture.REGULAR,16));
        mlb.setTextAlignment(TextAlignment.CENTER);
        mlb.setAlignment(Pos.CENTER);
        cbtn = new JFXButton("Continua");
        cbtn.setPrefSize(120,50);
        cbtn.setFont(Font.font("Verdana",FontPosture.REGULAR,16));
        cbtn.setDisable(true);
        abtn = new JFXButton("Annulla");
        abtn.setPrefSize(120,50);
        abtn.setFont(Font.font("Verdana",FontPosture.REGULAR,16));
        abtn.setDisable(true);
        HBox hb = new HBox();
        hb.getChildren().addAll(cbtn, abtn);
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(100d);
        VBox vbx = new VBox();
        vbx.setAlignment(Pos.CENTER);
        vbx.setSpacing(50d);
        vbx.setBackground(new Background(new BackgroundFill(Color.rgb(115,194,194,0.7d),
                new CornerRadii(20d),new Insets(275,275,275,275))));
        vbx.getChildren().addAll(mlb,hb);
        introController.onContinueAction(cbtn, wb,sketch);
        introController.onRedoAction(abtn,sketch);
        introController.setOnHoverInfo(wb.getToolbarControlsRight().get(0));
        return vbx;
    }

    @Override
    public void update(Object object) {
        cbtn.setDisable(((P2DMap) object).getObstacleList().isEmpty());
        abtn.setDisable(((P2DMap) object).getObstacleList().isEmpty());
    }


}