package com.effibot.robind_manipolator.Processing;

import processing.core.PApplet;

public abstract class ProcessingBase extends PApplet implements FXController{
    public FXController controller;
    @Override
    public void setup() {
        smooth();
    }
    @Override
    public abstract void draw();
    @Override
    public abstract void settings();
    public int Y_AXIS = 1;
    public int X_AXIS = 2;
    int c1 = color(153,204,204);
    int c2 = color(57,104,124);
    // bg utils
    void setGradient(int x, int y, float w, float h, int c1, int c2, int axis) {

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
}
