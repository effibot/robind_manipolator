package com.effibot.robind_manipolator.Processing;

import javafx.application.Platform;
import processing.core.PApplet;

import java.util.ArrayList;

public class P2DMap extends ProcessingBase {
    private final int padding = 10;
    private final int mapColor = color(102,102,102);
    private final int size = 512;
    private int targetColor;
    private float targetAlpha = 80f;
    private float targetR = 30.0f;
    private boolean click = false;
    private ArrayList<Obstacle> obsList = new ArrayList<>();
    @Override
    public void settings() {
        size(size+2*padding, size+2*padding, P2D);
        pixelDensity(1);

    }
    @Override
    public void setup() {
        super.setup();
        surface.setTitle("Posizionamento Ostacoli");
    }
    public void run(){
        String[] processingArgs = {"main.java.com.effibot.robind_manipolator.Processing.P2DMap"};
        PApplet.runSketch(processingArgs, this);
    }
    @Override
    public void setJavaFX(FXController controller) {
        /*TODO: define controller for this class and implement it*/
    }
    @Override
    public void draw() {
        // background
        setGradient(0, 0, width, height, c1, c2, Y_AXIS);
        fill(mapColor);
        stroke(0);
        rect(padding, padding,size,size);
        noFill();
        noStroke();
//        translate(width/2.0f, height/2.0f);
        // obstacle positioning
        fill(255);
        target();
        stroke(0);
        line(0,mouseY,width,mouseY);
        line(mouseX,0,mouseX,height);
        drawObstacles2D();

    }
    public int getPadding() {
        return padding;
    }
    private boolean isDrawable(){
        return  (mouseX + targetR <= size+padding) && (mouseX - targetR >= padding) &&
                (mouseY + targetR <= size+padding) && (mouseY - targetR >= padding);
    }
    private void targetColorSelect(float targetAlpha){
        if(isDrawable()) {
            targetColor =  color(0,255,0,targetAlpha);
        } else {
            targetColor =  color(255,0,0,targetAlpha);
        }
    }
    void target(){
        int targetX = 2*(mouseX-padding);
        int targetY = 2*(mouseY-padding);
        pushMatrix();
        translate(mouseX,mouseY);
        targetColorSelect(targetAlpha);
        fill(targetColor);
        noStroke();
        circle(0,0,2*targetR);
        popMatrix();
        if (click && isDrawable()) {
            click = false;
            int id = obsList.size();
            Obstacle obs = new Obstacle(this, targetX, targetY,0,targetR,100,id);
            obsList.add(obs);
            fill(targetColor);
            pushMatrix();
            translate(mouseX,mouseY);
            circle(0,0,2*targetR);
            popMatrix();
        }
    }
    public void mouseClicked(){
        if(isDrawable()) click = true;
    }
    public void drawObstacles2D(){
        if (!obsList.isEmpty()){
            for (Obstacle obs : obsList)
                obs.drawObstacle2D();
        }
    }
}
