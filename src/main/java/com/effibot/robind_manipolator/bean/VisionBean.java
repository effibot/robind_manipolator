package com.effibot.robind_manipolator.bean;

import javafx.beans.property.*;

import java.io.Serializable;

public class VisionBean  implements Serializable {
    private DoubleProperty objArea = new SimpleDoubleProperty();
    private DoubleProperty objPerim = new SimpleDoubleProperty();
    private StringProperty objShape = new SimpleStringProperty();
    private DoubleProperty objOrient = new SimpleDoubleProperty();
    private byte[] objImage;

    public double getObjArea() {
        return objArea.get();
    }

    public DoubleProperty objAreaProperty() {
        return objArea;
    }

    public void setObjArea(double objArea) {
        this.objArea.set(objArea);
    }

    public double getObjPerim() {
        return objPerim.get();
    }

    public DoubleProperty objPerimProperty() {
        return objPerim;
    }

    public void setObjPerim(double objPerim) {
        this.objPerim.set(objPerim);
    }

    public String getObjShape() {
        return objShape.get();
    }

    public StringProperty objShapeProperty() {
        return objShape;
    }

    public void setObjShape(String objShape) {
        this.objShape.set(objShape);
    }

    public double getObjOrient() {
        return objOrient.get();
    }

    public DoubleProperty objOrientProperty() {
        return objOrient;
    }

    public void setObjOrient(double objOrient) {
        this.objOrient.set(objOrient);
    }

    public byte[] getObjImage() {
        return objImage;
    }

    public void setObjImage(byte[] objImage) {
        this.objImage = objImage;
    }
}
