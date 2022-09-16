package com.effibot.robind_manipolator.modules.intro;

import com.dlsc.workbenchfx.Workbench;
import com.dlsc.workbenchfx.model.WorkbenchModule;
import com.dlsc.workbenchfx.view.controls.MultilineLabel;
import com.effibot.robind_manipolator.processing.Observer;
import com.effibot.robind_manipolator.processing.P2DMap;
import com.effibot.robind_manipolator.processing.ProcessingBase;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import com.jfoenix.controls.JFXButton;


public class IntroModule extends WorkbenchModule implements Observer {
    private final ProcessingBase sketch;

    private final IntroController introController;
    private JFXButton  cbtn;
    private JFXButton  abtn;
    public IntroModule() {
        super("Intro", MaterialDesign.MDI_ACCOUNT);

            this.sketch = new P2DMap();
            introController = new IntroController( this, this.sketch);
            sketch.registerObserver(this);

    }

    @Override
    public Node activate() {
        Workbench wb = this.getWorkbench();

        Label mlb = new MultilineLabel("""
                Benvenuti nella schermata di controllo dell'application
                 Premi continua per procedere con gli ostacoli selezionati o annulla per riposizione.
                Andrea Efficace, Lorenzo Rossi, Martina Liberti""");
        mlb.setTextAlignment(TextAlignment.CENTER);
        mlb.setAlignment(Pos.CENTER);
        cbtn = new JFXButton("Continua");
        cbtn.setDisable(true);
        abtn = new JFXButton("Annulla");
        abtn.setDisable(true);
        HBox hb = new HBox();
        hb.getChildren().addAll(cbtn, abtn);
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(100d);
        VBox vbx = new VBox(mlb,hb);
        vbx.setAlignment(Pos.CENTER);
        vbx.setSpacing(50d);
        introController.onContinueAction(cbtn, wb,sketch);
        introController.onRedoAction(abtn,sketch);

        return vbx;
    }

    @Override
    public void update(Object object) {
        cbtn.setDisable(((P2DMap) object).getObstacleList().isEmpty());
        abtn.setDisable(((P2DMap) object).getObstacleList().isEmpty());
    }

}