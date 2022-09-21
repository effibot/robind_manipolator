package com.effibot.robind_manipolator.processing;

import grafica.*;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;

class Plot2D extends GPlot{

    ListProperty<Double> xvalues = new SimpleListProperty<>();
    ListProperty<Double> yvaluesT = new SimpleListProperty<>();
    ListProperty<Double> yvaluesR = new SimpleListProperty<>();
    private final GPointsArray points;
    public Plot2D(P3DMap p3DMap,int x, int y) {
        super(p3DMap);
        this.setPos(x,y);
        points = new GPointsArray();

    }

    public void setXLabel(String name){
        this.getXAxis().setAxisLabelText(name);
    }
    public void setYLabel(String name){
        this.getYAxis().setAxisLabelText(name);

    }

    public void setTitle(String title){
        this.setTitleText(title);
    }

    public void setupBindings(ListProperty<Double> x, ListProperty<Double> yT,ListProperty<Double> yR){
        xvalues.bind(x);
        yvaluesT.bind(yT);
        yvaluesR.bind(yR);

    }
    void draw(){
        this.getPointsRef();
        this.beginDraw();
        this.drawBackground();
        this.drawXAxis();
        this.drawYAxis();
        this.getMainLayer().drawPoints();
        this.setDim(400,100);
        this.setVerticalAxesNTicks(0);
        this.setHorizontalAxesTicksSeparation(1f);
        this.setPointSize(2);
//        this.getLayer("surface").drawFilledContour(GPlot.HORIZONTAL, 0);
        this.endDraw();

    }

}