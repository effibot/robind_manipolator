package com.effibot.robind_manipolator;

import com.dlsc.workbenchfx.Workbench;
import com.effibot.robind_manipolator.modules.manual.WelcomeModule;
import com.effibot.robind_manipolator.processing.P2DMap;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        WelcomeModule mn = new WelcomeModule();
        Workbench wb = Workbench.builder(
                mn
        ).modulesPerPage(5)
                .build();
        wb.getStylesheets().add(Objects.requireNonNull(getClass().getResource("ui.css")).toExternalForm());

        Scene myScene = new Scene(wb);
        stage.setTitle("Robind Manipolator");
        stage.setScene(myScene);
        stage.setWidth(1095);
        stage.setHeight(801.5);
        stage.setResizable(false);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }


}