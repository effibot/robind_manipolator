package com.effibot.robind_manipolator;
import com.effibot.robind_manipolator.Processing.P2DMap;
import com.effibot.robind_manipolator.Processing.ProcessingBase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import mapGeneration.Map;
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
    private static Map m;

    private static void setup() throws MWException {
        m = new Map();
    }
    public static void main(String[] args) {
        try {
            setup();
            int [][] obs = {{100,100,30},{800,800,100}};
            MWArray obsin =new MWNumericArray(obs, MWClassID.DOUBLE);;
            int[] dim ={1024,1024};
            MWArray dimin = new MWNumericArray(dim, MWClassID.DOUBLE);;
            Object[] result = m.mapGeneration(2,obsin,dimin);
            MWNumericArray gidOut = null;
            MWNumericArray shapeposOut = null;
            if (result[0] instanceof MWNumericArray) {
                gidOut = (MWNumericArray) result[0];
            }
            if (result[1] instanceof MWNumericArray) {
                shapeposOut = (MWNumericArray) result[1];
            }
            System.out.println(gidOut);
            System.out.println(shapeposOut);
        } catch (MWException e) {
            throw new RuntimeException(e);
        }

        launch();
    }
}