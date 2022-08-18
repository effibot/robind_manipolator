package com.effibot.robind_manipolator;
import com.effibot.robind_manipolator.matlab.Matlab;
import com.effibot.robind_manipolator.Processing.P2DMap;
import com.effibot.robind_manipolator.Processing.ProcessingBase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    private ProcessingBase sketch;
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
        stage.setTitle("Find Object Manipulator");
        stage.setScene(scene);
        stage.setResizable(false);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(  ((primScreenBounds.getWidth() - stage.getWidth()) / 2) -600 );
        stage.setY(  ((primScreenBounds.getHeight() - stage.getHeight()) / 2)   );
        stage.show();
        SceneController controller = fxmlLoader.getController();
        SceneController.setSketch(sketch);
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
        Matlab.getInstance().getEngine();
        launch();
    }
}