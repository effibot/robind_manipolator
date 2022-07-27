package com.effibot.robind_manipolator;

import com.effibot.robind_manipolator.Processing.P2DMap;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public P2DMap sketch;
    @Override
    public void init() throws Exception {
        sketch = new P2DMap();
    }
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("scene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 516, 768);
        stage.setTitle("Find Object Manipolator");
        stage.setScene(scene);
        stage.setResizable(false);
//        stage.show();
        sketch.run();

    }
    @Override
    public void stop(){
        sketch.noLoop();
        sketch.stop();
        sketch.exit();
    }
    public static void main(String[] args) {
        launch();
    }
}