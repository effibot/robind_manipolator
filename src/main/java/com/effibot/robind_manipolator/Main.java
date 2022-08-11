package com.effibot.robind_manipolator;
import com.effibot.robind_manipolator.MATLAB.Matlab;
import com.effibot.robind_manipolator.Processing.P2DMap;
import com.effibot.robind_manipolator.Processing.ProcessingBase;
import com.mathworks.engine.MatlabEngine;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import com.mathworks.toolbox.javabuilder.*;

import java.io.IOException;
import java.util.List;

public class Main extends Application {
    private ProcessingBase sketch;
    private static final String addr="127.0.0.1";
    private static final int portRx = 12345;
    private static final int portTx = 12346;
    public void setSketch(ProcessingBase sketch){
        this.sketch = sketch;
    }
    public ProcessingBase getSketch(){ return  this.sketch; }
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
        sketch.run(sketch.getClass().getSimpleName());
    }
    @Override
    public void stop(){
        sketch.noLoop();
        sketch.stop();
        sketch.exit();
    }


    public static void main(String[] args) {
//        try {
//            Matlab.getInstance();
//        } catch (MWException e) {
//            throw new RuntimeException(e);
//        }
        MatlabEngine eng = Matlab.getInstance().getEngine();
        launch();
    }
}