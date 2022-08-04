package com.effibot.robind_manipolator.Processing;

import com.effibot.robind_manipolator.SceneController;
import processing.core.PApplet;
import processing.core.PSurface;

import java.util.List;

public abstract class ProcessingBase extends PApplet implements Subject{
    protected SceneController controller;
    protected List<Observer> observers;
    protected int padding = 10;
    protected int size;


    @Override
    public void setup() {
    }
    @Override
    public abstract void draw();
    public void run(String className){
        String[] processingArgs = {"main.java.com.effibot.robind_manipolator.Processing."+className};
        PApplet.runSketch(processingArgs, this);
    }
    @Override
    public abstract void settings();

    protected int Y_AXIS = 1;
    protected int X_AXIS = 2;
    protected int c1 = color(153,204,204);
    protected int c2 = color(57,104,124);
    // bg utils
    protected void setGradient(int x, int y, float w, float h, int c1, int c2, int axis) {

        noFill();

        if (axis == Y_AXIS) {  // Top to bottom gradient
            for (int i = y; i <= y + h; i++) {
                float inter = map(i, y, y + h, 0, 1);
                int c = lerpColor(c1, c2, inter);
                stroke(c);
                line(x, i, x + w, i);
            }
        } else if (axis == X_AXIS) {  // Left to right gradient
            for (int i = x; i <= x + w; i++) {
                float inter = map(i, x, x + w, 0, 1);
                int c = lerpColor(c1, c2, inter);
                stroke(c);
                line(i, y, i, y + h);
            }
        }
    }
    public int getPadding() {
        return padding;
    }
    // Observer Pattern implementation to notify the FX controller when new obstacles are added to the map
    // We added to the base class for further development.
    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public abstract void notifyObservers();

    public void setJavaFX(SceneController controller) {
        this.controller = controller;
    }

    public PSurface getSurface() {
        return this.surface;
    }
}
