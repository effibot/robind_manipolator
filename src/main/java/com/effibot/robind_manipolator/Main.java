package com.effibot.robind_manipolator;

import com.dlsc.workbenchfx.Workbench;
import com.dlsc.workbenchfx.view.controls.ToolbarItem;
import com.effibot.robind_manipolator.modules.manual.WelcomeModule;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

            WelcomeModule mn = new WelcomeModule();
            Workbench wb = Workbench.builder(
                            mn
                    ).modulesPerPage(5)
                    .build();
            FontIcon fi = new FontIcon(MaterialDesign.MDI_COMMENT_QUESTION_OUTLINE);
            fi.setIconSize(50);
            ToolbarItem tb = new ToolbarItem("",fi);
            tb.setMaxSize(50,50);
            tb.setMinSize(50,50);
            tb.setPrefSize(50,50);

            wb.getToolbarControlsRight().add(tb);
            wb.getStylesheets().add(Objects.requireNonNull(getClass().getResource("ui.css")).toExternalForm());
            Scene myScene = new Scene(wb);
            stage.setTitle("Robind Manipolator");
            stage.setScene(myScene);
            stage.setWidth(1094.5);
            stage.setHeight(830.5);
            stage.setResizable(false);
            stage.show();

    }


    public static void main(String[] args) {
        launch();
    }


}