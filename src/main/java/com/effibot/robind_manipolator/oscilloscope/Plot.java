package com.effibot.robind_manipolator.oscilloscope;

import com.effibot.robind_manipolator.bean.RobotBean;
import com.effibot.robind_manipolator.processing.ProcessingBase;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

import processing.core.PApplet;
import processing.core.PGraphics;

public class Plot {
    private final List<Float> minArray;
    private final List<Float> maxArray;
    private  int titleHeight = 30;
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
    private  int textSize = 18 ;
    private final processing.core.PGraphics pGraphics;

    private final  ListProperty<Float> currentValProperty = new SimpleListProperty<>(
            FXCollections.observableList(new ArrayList<>(0)));
    private final  ListProperty<Float> referenceValProperty = new SimpleListProperty<>(
            FXCollections.observableList(new ArrayList<>(0)));;
    private int canvasColor;
    private boolean visible = false;
    private float currentLineStroke=0.5f;
    private float referenceLineStroke=1f;

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
        currentLineColor = processingBase.color(26, 255, 26);
        referenceLineColor = processingBase.color(204, 0, 0);
        setGridHeight();
        pGraphics = processingBase.createGraphics(width,height);

        minArray = Collections.synchronizedList(this.referenceValProperty.get());
        maxArray = Collections.synchronizedList(this.referenceValProperty.get());


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

    public  int getTitleHeight() {
        return titleHeight;
    }

    public  void setTitleHeight(int titleHeight) {
        this.titleHeight = titleHeight;
    }

    public  int getTextSize() {
        return textSize;
    }

    public  void setTextSize(int textSize) {
        this.textSize = textSize;
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
    public void onlyRef(){
        if (visible ) {
            pGraphics.beginDraw();
            pGraphics.stroke(canvasColor);
            drawBackground();


            pGraphics.beginShape();
            pGraphics.stroke(0);
            pGraphics.strokeWeight(referenceLineStroke);
            pGraphics.stroke(referenceLineColor);
            scaleValue(referenceValProperty, referenceValProperty.get().stream().min(Float::compare).get(),
                    referenceValProperty.get().stream().max(Float::compare).get());
            pGraphics.endShape();

            pGraphics.endDraw();
        }
    }

    public void drawRefCurr() {

        if (visible) {
            pGraphics.beginDraw();
            pGraphics.stroke(canvasColor);
            drawBackground();

            List<Float> minVal = Arrays.asList(
                    Collections.min(currentValProperty.get()),
                    Collections.min(referenceValProperty.get()));
            List<Float> maxVal = Arrays.asList(
                    Collections.max(currentValProperty.get()),
                    Collections.max(referenceValProperty.get()));
            Float minValf = Collections.min(minVal);
            Float maxValf = Collections.max(maxVal);

            pGraphics.beginShape();
            pGraphics.stroke(0);
            pGraphics.strokeWeight(0.5f);
            float scaling = PApplet.map(0,
                    minValf, maxValf,
                    0, gridHeight - 20f);
            pGraphics.line(0, scaling + titleHeight, width, scaling + titleHeight);
            pGraphics.endShape();

            pGraphics.beginShape();
            pGraphics.strokeWeight(currentLineStroke);
            pGraphics.stroke(currentLineColor);
            scaleValue(currentValProperty, minValf, maxValf);
            pGraphics.endShape();


            pGraphics.beginShape();
            pGraphics.stroke(0);
            pGraphics.strokeWeight(referenceLineStroke);
            pGraphics.stroke(referenceLineColor);
            scaleValue(referenceValProperty, minValf, maxValf);
            pGraphics.endShape();

            pGraphics.endDraw();

            processingBase.image(pGraphics, xPos, yPos);
        }
    }


    private void scaleValue(ListProperty<Float> valProperty,float min,float max) {
        if(!valProperty.isEmpty() && !valProperty.get().isEmpty()) {
            float time = 0.0f;
            float step = 1/100f;
            for (int i = 0; i < valProperty.get().size(); i++) {
                    float scaledYValue = PApplet.map(valProperty.get().get(i), min,max, 0,gridHeight-20f);
                    float scaledXValue = PApplet.map(time, 0, RobotBean.getMaxPoint()/100f, 0, width);
                    pGraphics.fill(0,0,0,0);
                    pGraphics.vertex(scaledXValue, scaledYValue+titleHeight);
                    pGraphics.noFill();
                    time += step;


                }


        }
    }


//    private synchronized ArrayList<Float> yBound(ref,curr){
//        ArrayList<Float> allPlotValue = currentValProperty.get();
//        allPlotValue.addAll(referenceValProperty.get());
//        return new ArrayList<>(Arrays.asList(Collections.max(allPlotValue),Collections.min(allPlotValue)));
//    }


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
        pGraphics.strokeWeight(0.2f);
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



    public ObservableList<Float> getReferenceVal() {
        return referenceValProperty.get();
    }


    public boolean isVisible() {
        return this.visible;
    }

    public float getCurrentLineStroke() {
        return currentLineStroke;
    }

    public void setRefPoint(Float val){
        this.referenceValProperty.add(val);
    }

    public void setCurrentLineStroke(float currentLineStroke) {
        this.currentLineStroke = currentLineStroke;
    }

    public float getReferenceLineStroke() {
        return referenceLineStroke;
    }

    public void setReferenceLineStroke(float referenceLineStroke) {
        this.referenceLineStroke = referenceLineStroke;
    }

    public void setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
    }

    public ProcessingBase getProcessingBase() {
        return processingBase;
    }

    public int getTitleBg() {
        return titleBg;
    }

    public void setTitleBg(int titleBg) {
        this.titleBg = titleBg;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    public int getGridLineColor() {
        return gridLineColor;
    }

    public void setGridLineColor(int gridLineColor) {
        this.gridLineColor = gridLineColor;
    }

    public PGraphics getpGraphics() {
        return pGraphics;
    }
}