package com.effibot.robind_manipolator.bean;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.lang.ArrayUtils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;

public class SettingBean implements Serializable {
    private ListProperty<Double> greenId = new SimpleListProperty<>(FXCollections.observableArrayList(1.0d));
    private ListProperty<Double> idList = new SimpleListProperty<>(FXCollections.observableArrayList(1.0d));

    private final PropertyChangeSupport changes= new PropertyChangeSupport(this);
    private byte[] rawImg;
    private double[][] shapeList;
    private byte[] animation;
    private ArrayList<double[]> shapeIdList;


    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
    }
    public void notifyPropertyChange(String propertyName, Object oldValue, Object newValue){
        /*
         * Just a wrapper for the fire property change method.
         */
        changes.firePropertyChange(propertyName, oldValue, newValue);
    }
    public void setGreenId(double[] greenId) {
        ObservableList<Double> values = FXCollections.observableArrayList(ArrayUtils.toObject(greenId));
        this.greenId.setValue(values);
    }
    public ListProperty<Double> getGreenId() {
        return greenId;
    }
    public void setRaw(byte[] imgEnanched) {
        this.rawImg = imgEnanched;
        changes.firePropertyChange("BW",false,true);
    }

    public byte[] getRaw(){return this.rawImg;}

    public double[][] getShapeList() {
        return shapeList;
    }

    public void setShapeList(double[][] shapeList) {
        this.shapeList = shapeList;
    }
    public void setAnimation(byte[] writableImage){
        this.animation = writableImage;
        changes.firePropertyChange("ANIMATION",false,true);
    }

    public byte[] getAnimation(){return this.animation;}
    public void setShapeIdList(ArrayList<double[]> greenIdShape) {
        this.shapeIdList = greenIdShape;
    }
    public ArrayList<double[]> getShapeIdList(){return shapeIdList;}

    public ObservableList<Double> getIdList() {
        return idList.get();
    }

    public ListProperty<Double> idListProperty() {
        return idList;
    }

    public void setIdList(ObservableList<Double> idList) {
        this.idList.set(idList);
    }
}