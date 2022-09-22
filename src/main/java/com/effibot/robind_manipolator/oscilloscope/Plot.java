package com.effibot.robind_manipolator.oscilloscope;

import com.effibot.robind_manipolator.processing.P3DMap;
import com.effibot.robind_manipolator.processing.ProcessingBase;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableListValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import processing.core.PApplet;
public class Plot {
    private static int titleHeight = 30;
    private int referenceLineColor;
    private int currentLineColor;
    private  int gridColor;
    private int gridHeight;
    private final ProcessingBase processingBase;
    private final String title;
    private final int xPos;
    private final int yPos;
    private final int width;
    private final int height;
    private int titleBg;
    private int titleColor;
    private int gridLineColor;
    private int spacing = 25;
    private static int textSize = 18 ;
    private processing.core.PGraphics pGraphics;
//    FXCollections.observableList(
//            new ArrayList<>(List .<Double[]>of(new Double[]{0d, 0d}))
//            )
    private final ListProperty<Float> currentValProperty = new SimpleListProperty<>(
            FXCollections.observableList(new ArrayList<>(0)));
    private final ListProperty<Float> referenceValProperty = new SimpleListProperty<>(
            FXCollections.observableList(new ArrayList<>(0)));
    private ObservableList<Float> currentVal;
    private ObservableList<Float> referenceVal;
    private int canvasColor;
    private boolean visible = false;

    public Plot(ProcessingBase processingBase, String title, int xPos, int yPos, int width, int height) {
        // Plot Title
        this.title = title;
        //Left top corner point
        this.xPos = xPos;
        this.yPos = yPos;
        //Width and Heigth canvas
        this.width = width;
        this.height = height;
        this.processingBase = processingBase;
        titleBg = processingBase.color(214,182,75);
        gridLineColor = processingBase.color(50,50,50);
        gridColor = processingBase.color(255, 255, 204);
        canvasColor = processingBase.color(0,0,0);
        currentLineColor = processingBase.color(0,0,0);
        referenceLineColor = processingBase.color(255,0,0);
        setGridHeight();
        pGraphics = processingBase.createGraphics(width,height);
        System.out.println(pGraphics);
    }

    public void setTitleBackground(int r, int g, int b){
        this.titleBg = processingBase.color(r,g,b);

    }
    public void setTitleColor(int r, int g, int b){
        this.titleColor = processingBase.color(r,g,b);

    }
    public void setGridBackgroundColor(int r, int g, int b){
        this.gridLineColor = processingBase.color(r,g,b);

    }

    public String getTitle() {
        return title;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSpacing() {
        return spacing;
    }

    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }

    public void setCurrentBinding(ListProperty<Float> xVal) {
        xVal.bind(xVal);

    }

    public void setReferenceBinding(ListProperty<Float> yVal) {
        yVal.bind(yVal);
    }

    public static int getTitleHeight() {
        return titleHeight;
    }

    public static void setTitleHeight(int titleHeight) {
        Plot.titleHeight = titleHeight;
    }

    public static int getTextSize() {
        return textSize;
    }

    public static void setTextSize(int textSize) {
        Plot.textSize = textSize;
    }

    public int getCanvasColor() {
        return canvasColor;
    }

    public void setCanvasColor(int canvasColor) {
        this.canvasColor = canvasColor;
    }

    public void setGridHeight(){
        this.gridHeight = height-titleHeight;
    }

    public float getGridHeight(){
        return gridHeight;
    }

    public int getGridColor() {
        return gridColor;
    }

    public void setGridColor(int gridColor) {
        this.gridColor = gridColor;
    }

    public int getReferenceLineColor() {
        return referenceLineColor;
    }

    public void setReferenceLineColor(int referenceLineColor) {
        this.referenceLineColor = referenceLineColor;
    }

    public int getCurrentLineColor() {
        return currentLineColor;
    }

    public void setCurrentLineColor(int currentLineColor) {
        this.currentLineColor = currentLineColor;
    }

    public void drawPlot(){
        if(visible) {
            pGraphics.beginDraw();
            pGraphics.stroke(canvasColor);
            drawBackground();
            // Drawing Points
            currentVal = currentValProperty.get();
            referenceVal = referenceValProperty.get();

            pGraphics.strokeWeight(2);
            pGraphics.stroke(currentLineColor);
            pGraphics.beginShape();
            drawCurrentPoint();
            pGraphics.endShape();

            pGraphics.stroke(referenceLineColor);
            pGraphics.beginShape();
            drawReferencePoint();
            pGraphics.endShape();

            pGraphics.endDraw();

            processingBase.image(pGraphics, xPos, yPos);
        }
    }

    private void drawReferencePoint() {
        scaleValue(referenceValProperty);
    }

    private void scaleValue(ListProperty<Float> referenceValProperty) {
        if(!referenceValProperty.isEmpty()) {
            if (!referenceValProperty.get().isEmpty()) {
                for (int i = 0; i < referenceVal.size(); i++) {
                    float scaledYValue = PApplet.map(referenceVal.get(i), Collections.min(currentVal)-gridHeight/2f, Collections.max(currentVal)+gridHeight/2f, -gridHeight / 2f, gridHeight / 2f);
                    float scaledXValue = PApplet.map(i, 0, i+1, 0, width);
                    pGraphics.point(scaledXValue, scaledYValue+gridHeight/2f);
                }
            }
        }
    }

    private void drawCurrentPoint() {
        scaleValue(currentValProperty);
    }


    private void drawBackground(){
        // Text Title size
        pGraphics.textSize(textSize);
        //Title Header
        pGraphics.fill(titleBg);
        pGraphics.rect(0,0,width, titleHeight);
        pGraphics.noFill();
        // Writing Title
        pGraphics.fill(titleColor);
        pGraphics.text(title,width*0.5f, titleHeight *0.75f);
        pGraphics.noFill();
        // Drawing grid
        //Grid Canvas
        pGraphics.fill(gridColor);
        pGraphics.rect(0,titleHeight,width,gridHeight);
        //Line grid property
        pGraphics.strokeWeight(1);
        pGraphics.stroke(gridLineColor);
        //Vertical Line
        for ( int i = 0 ; i < width ; i+=spacing) {
            pGraphics.line(i,titleHeight,i,height);
        }
        //Horizontal Line
        for ( int j = titleHeight ; j < height ; j+=spacing) {
            pGraphics.line(0,j,width,j);
        }
    }

    public void setVisible(boolean val) {
        visible = val;
    }

    public ObservableList<Float> getCurrentValProperty() {
        return currentValProperty.get();
    }

    public ListProperty<Float> currentValPropertyProperty() {
        return currentValProperty;
    }

    public void setCurrentValProperty(ObservableList<Float> currentValProperty) {
        this.currentValProperty.set(currentValProperty);
    }
    public void setCurrentValProperty(Float currentVal) {
        this.currentValProperty.add(currentVal);
    }

    public ObservableList<Float> getReferenceValProperty() {
        return referenceValProperty.get();
    }

    public ListProperty<Float> referenceValPropertyProperty() {
        return referenceValProperty;
    }

    public void setReferenceValProperty(ObservableList<Float> referenceValProperty) {
        this.referenceValProperty.set(referenceValProperty);
    }
    public void setReferenceValProperty(Float currentVal) {
        this.referenceValProperty.add(currentVal);
    }

    public ObservableList<Float> getCurrentVal() {
        return currentValProperty.get();
    }

    public void setCurrentVal(ObservableList<Float> currentVal) {
        this.currentVal = currentVal;
    }

    public ObservableList<Float> getReferenceVal() {
        return referenceValProperty.get();
    }

    public void setReferenceVal(ObservableList<Float> referenceVal) {
        this.referenceVal = referenceVal;
    }
}