package com.effibot.robind_manipolator.modules.intro;

import com.dlsc.workbenchfx.Workbench;
import com.dlsc.workbenchfx.model.WorkbenchModule;
import com.dlsc.workbenchfx.view.controls.MultilineLabel;
import com.effibot.robind_manipolator.Main;
import com.effibot.robind_manipolator.Processing.P2DMap;
import com.effibot.robind_manipolator.Processing.ProcessingBase;
import com.jogamp.newt.NewtFactory;
import com.jogamp.newt.Screen;
import com.jogamp.newt.awt.NewtCanvasAWT;
import com.jogamp.newt.javafx.NewtCanvasJFX;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.newt.swt.NewtCanvasSWT;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.Animator;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

import javax.swing.*;
import java.awt.*;

public class IntroModule extends WorkbenchModule {
    private  ProcessingBase sketch;

    private IntroController introController;

    public IntroModule() {
        super("Intro", MaterialDesign.MDI_ACCOUNT);

            this.sketch = new P2DMap();
            introController = new IntroController(this.getWorkbench(), this, this.sketch);

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
        Button cbtn = new Button("Continua");
        Button abtn = new Button("Annulla");
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
}