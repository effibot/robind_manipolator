package com.effibot.robind_manipolator.bean;

import com.effibot.robind_manipolator.processing.Obstacle;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.lang.ArrayUtils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static processing.core.PConstants.PI;

public class RobotBean implements Serializable {
    // Robot Joint Variables [q1,...,q6]

    private final transient ListProperty<Float> q = new SimpleListProperty<>(FXCollections.observableArrayList(
            Arrays.asList(
                    ArrayUtils.toObject(
                            new float[]{0f, -PI / 2, PI / 2, 0f, 0f, 0f}
                    ))
    ));
    // Rover Position [x][y]
    private final transient ListProperty<Double[]> qRover = new SimpleListProperty<>(FXCollections.observableArrayList());
    // Rover Velocity [dx][dy]
    private final transient ListProperty<Double[]> dqRover = new SimpleListProperty<>(FXCollections.observableArrayList());
    // Rover Acceleration [ddx][ddy]
    private final transient ListProperty<Double[]> ddqRover = new SimpleListProperty<>(FXCollections.observableArrayList());
    // Rover Error [e_x][e_y][e_dx][e_dy]
    private final transient ListProperty<Double[]> errors = new SimpleListProperty<>(FXCollections.observableArrayList());
    // map image's stream
    private byte[] animation;
    private final transient PropertyChangeSupport changes = new PropertyChangeSupport(this);
    private ArrayList<Obstacle> obsList;

    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
    }

    public void notifyPropertyChange(String propertyName, Object oldValue, Object newValue) {
        changes.firePropertyChange(propertyName, oldValue, newValue);
    }

    public void setRoverPos(double[][] roverPos) {
        this.qRover.set(adaptToPropertyList(roverPos));
    }

    public void setRoverVel(double[][] roverVel) {
        this.dqRover.set(adaptToPropertyList(roverVel));

    }
    public void setRoverAcc(double[][] roverAcc) {
        this.ddqRover.set(adaptToPropertyList(roverAcc));
    }
    public void setError(double[][] error) {
        this.errors.set(adaptToPropertyList(error));
    }
    private ListProperty<Double[]> adaptToPropertyList(double[][] values) {
        ListProperty<Double[]> adapted = new SimpleListProperty<>(FXCollections.observableArrayList());
        int i = 0;
        for (double[] val : values) {
            adapted.set(i, ArrayUtils.toObject(val));
            i++;
        }
        return adapted;
    }


    public void setAnimation(byte[] writableImage){
        this.animation = writableImage;
        changes.firePropertyChange("ANIMATION",false,true);
    }
    // PUMA
    public ObservableList<Float> getQ() {
        return q.get();
    }

    public ListProperty<Float> qProperty() {
        return q;
    }

    public void setQ(ObservableList<Float> q) {
        this.q.set(q);
    }
    // ROVER
    public ObservableList<Double[]> getqRover() {
        return qRover.get();
    }

    public ListProperty<Double[]> qRoverProperty() {
        return qRover;
    }

    public void setqRover(ObservableList<Double[]> qRover) {
        this.qRover.set(qRover);
    }

    public ObservableList<Double[]> getDqRover() {
        return dqRover.get();
    }

    public ListProperty<Double[]> dqRoverProperty() {
        return dqRover;
    }

    public void setDqRover(ObservableList<Double[]> dqRover) {
        this.dqRover.set(dqRover);
    }

    public ObservableList<Double[]> getDdqRover() {
        return ddqRover.get();
    }

    public ListProperty<Double[]> ddqRoverProperty() {
        return ddqRover;
    }

    public void setDdqRover(ObservableList<Double[]> ddqRover) {
        this.ddqRover.set(ddqRover);
    }

    public ObservableList<Double[]> getErrors() {
        return errors.get();
    }

    public ListProperty<Double[]> errorsProperty() {
        return errors;
    }

    public void setErrors(ObservableList<Double[]> errors) {
        this.errors.set(errors);
    }

    public void setObsList(ArrayList<Obstacle> obstacleList) {
        this.obsList = obstacleList;
    }
    public List<Obstacle> getObsList(){
        return obsList;
    }
}
