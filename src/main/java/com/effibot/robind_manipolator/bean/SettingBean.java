package com.effibot.robind_manipolator.bean;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SettingBean implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(SettingBean.class.getName());
    private final transient  ListProperty<Double> greenId = new SimpleListProperty<>(FXCollections.observableArrayList(1.0d));
    private final transient ListProperty<Double> idList = new SimpleListProperty<>(FXCollections.observableArrayList(1.0d));

    private final transient ObjectProperty<Double> selectedId = new SimpleObjectProperty<>();

    private final transient ObjectProperty<String> selectedShape = new SimpleObjectProperty<>();

    private final transient ObjectProperty<String> selectedMethod = new SimpleObjectProperty<>();

    private final transient PropertyChangeSupport changes= new PropertyChangeSupport(this);

    private final transient ListProperty<String> pathLabels = new SimpleListProperty<>(FXCollections.observableArrayList());

    private final transient DoubleProperty roll = new SimpleDoubleProperty();
    private final transient DoubleProperty pitch = new SimpleDoubleProperty();
    private final transient DoubleProperty yaw = new SimpleDoubleProperty();

    private byte[] rawImg;
    private double[][] shapeList;

    private  byte[] animation;
    private ArrayList<double[]> shapeIdList;
    private boolean finish = false;
    private double[] selectedShapePos;

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

    public double[] adaptToDouble(){
         ObservableList<Double> values = getIdList();
         double[] result = new double[values.size()];
         for(int i = 0; i < values.size(); i++){
             result[i] = values.get(i);
         }
         return result;
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
        animation = writableImage;
        changes.firePropertyChange("ANIMATION",false,this);
    }

    public byte[] getAnimation(){return this.animation;}

    public void setShapeIdList(List<double[]> greenIdShape) {
        this.shapeIdList = (ArrayList<double[]>)greenIdShape;
    }

    public List<double[]> getShapeIdList(){return shapeIdList;}
    public ObservableList<Double> getIdList() {
        return idList.get();
    }

    public ListProperty<Double> idListProperty() {
        return idList;
    }
    public void setIdList(ObservableList<Double> idList) {
        this.idList.set(idList);
    }
    public Double getSelectedId() {
        return selectedId.get();
    }

    public ObjectProperty<Double> selectedIdProperty() {
        return selectedId;
    }

    public void setSelectedId(Double selectedId) {
        this.selectedId.set(selectedId);
    }

    public String getSelectedShape() {
        return selectedShape.get();
    }

    public ObjectProperty<String> selectedShapeProperty() {
        return selectedShape;
    }

    public void setSelectedShape(String selectedShape) {
        this.selectedShape.set(selectedShape);
    }

    public double[] shapeToPos(){
        int select = -1;
        switch(getSelectedShape()){
            case "Sfera"-> select=0;
            case "Cono" -> select=1;
            case "Cubo" -> select=2;
            default -> LOGGER.warn("ShapeToPos shape not found");
        }
        this.selectedShapePos = new double[]{getShapeList()[select][1],getShapeList()[select][2]};
        return this.selectedShapePos;
    }

    public String getSelectedMethod() {
        return selectedMethod.get();
    }
    public ObjectProperty<String> selectedMethodProperty() {
        return selectedMethod;
    }

    public void setSelectedMethod(String selectedMethod) {
        this.selectedMethod.set(selectedMethod);
    }

    public void setShapeAvailable(boolean b) {
        notifyPropertyChange("ERROR",false,true);
    }

    public void setPathLabel(double[] ids) {
        setPathLabels(FXCollections.observableArrayList(Arrays.toString(ids)));
    }

    public ObservableList<String> getPathLabels() {
        return pathLabels.get();
    }

    public ListProperty<String> pathLabelsProperty() {
        return pathLabels;
    }

    public void setPathLabels(ObservableList<String> pathLabels) {
        this.pathLabels.set(pathLabels);
    }
    public void setFinish(boolean finish) {
        notifyPropertyChange("FINISH",this.finish, finish);
        this.finish = finish;
    }

    public boolean getFinish(){
        return finish;
    }

    public double getRoll() {
        return roll.get();
    }

    public DoubleProperty rollProperty() {
        return roll;
    }

    public void setRoll(double roll) {
        this.roll.set(roll);
    }

    public double getPitch() {
        return pitch.get();
    }

    public DoubleProperty pitchProperty() {
        return pitch;
    }

    public void setPitch(double pitch) {
        this.pitch.set(pitch);
    }

    public double getYaw() {
        return yaw.get();
    }

    public DoubleProperty yawProperty() {
        return yaw;
    }

    public void setYaw(double yaw) {
        this.yaw.set(yaw);
    }
}