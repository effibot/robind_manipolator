package com.effibot.robind_manipolator;

import com.effibot.robind_manipolator.Processing.P2DMap;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
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
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(  ((primScreenBounds.getWidth() - stage.getWidth()) / 2) -600 );
        stage.setY(  ((primScreenBounds.getHeight() - stage.getHeight()) / 2)   );
        stage.show();
        SceneController controller = fxmlLoader.getController();
        controller.setSketch(sketch);
        sketch.registerObserver(controller);
        controller.setJavafxApp(this);
        sketch.setJavaFX(controller);
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