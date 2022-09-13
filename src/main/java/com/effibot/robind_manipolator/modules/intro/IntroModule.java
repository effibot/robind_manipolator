package com.effibot.robind_manipolator.modules.intro;

import com.dlsc.workbenchfx.Workbench;
import com.dlsc.workbenchfx.model.WorkbenchModule;
import com.dlsc.workbenchfx.view.controls.MultilineLabel;
import com.effibot.robind_manipolator.Processing.ProcessingBase;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
public class IntroModule extends WorkbenchModule {
    private IntroController introController;
    private final ProcessingBase sketch;
    private Workbench wb;
    public IntroModule(ProcessingBase sketch) {
        super("Intro", MaterialDesign.MDI_ACCOUNT);
        this.sketch = sketch;
        introController= new IntroController();
    }

    @Override
    public Node activate() {
        wb=this.getWorkbench();
        Label mlb = new MultilineLabel("""
                Benvenuti nella schermata di controllo dell'application
                 Premi continua per procedere con gli ostacoli selezionati o annulla per riposizione.
                Andrea Efficace, Lorenzo Rossi, Martina Liberti""");
        mlb.setTextAlignment(TextAlignment.CENTER);
        mlb.setAlignment(Pos.CENTER);
        Button cbtn = new Button("Continua");
        Button abtn = new Button("Annulla");
        HBox hb = new HBox();
        hb.getChildren().addAll(cbtn, abtn);
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(100d);
        VBox vbx = new VBox(mlb, hb);
        vbx.setAlignment(Pos.CENTER);
        vbx.setSpacing(50d);
        introController.onContinueAction(cbtn,wb,sketch);
        introController.onRedoAction(abtn,sketch);

        return vbx;
    }
}