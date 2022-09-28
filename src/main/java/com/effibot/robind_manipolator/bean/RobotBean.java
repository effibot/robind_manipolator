package com.effibot.robind_manipolator.bean;

import com.effibot.robind_manipolator.processing.Obstacle;

import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.PixelBuffer;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import org.apache.commons.lang.ArrayUtils;
import javafx.embed.swing.*;
import javax.imageio.ImageIO;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static processing.core.PConstants.PI;

public class RobotBean implements Serializable {
    private double[] selectedShape;
    private double roll;
    private double pitch;
    private double yaw;

    private final ListProperty<Float> errorNewton = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>(0)));
    private static int MAXPOINT=0;
    public double[][] getShapePos() {
        return shapePos;
    }

    public void setShapePos(double[][] shapePos) {
        this.shapePos = shapePos;
    }

    // Robot Joint Variables [q1,...,q6]
    // shape ids
    private double[][] shapePos;
    private final transient ByteBuffer bufferTexture = ByteBuffer.allocateDirect(4 * 1024 * 1024);
    private final transient PixelBuffer<ByteBuffer> pixelBufferTexture = new PixelBuffer<>(1024, 1024, bufferTexture, PixelFormat.getByteBgraPreInstance());
    private transient final WritableImage imgTexture = new WritableImage(pixelBufferTexture);
    private final transient ListProperty<Float> q = new SimpleListProperty<>(FXCollections.observableArrayList(
            Arrays.asList(
                    ArrayUtils.toObject(
                            new float[]{0f, -PI / 2, PI / 2, 0f, 0f, 0f}
                    ))
    ));
    // Rover Position [x][y]
    private final transient ListProperty<Double[]> qRover = new SimpleListProperty<>(
            FXCollections.observableList(new ArrayList<>(0)));
    private final transient ListProperty<Double[]> qGRover = new SimpleListProperty<>(
            FXCollections.observableList(new ArrayList<>(0)));
    // Rover Velocity [dx][dy]
    private final transient ListProperty<Double[]> dqRover = new SimpleListProperty<>(
            FXCollections.observableList(new ArrayList<>(0)));
    private final transient ListProperty<Double[]> dqGRover = new SimpleListProperty<>(
            FXCollections.observableList(new ArrayList<>(0)));
    // Rover Acceleration [ddx][ddy]
    private final transient ListProperty<Double[]> ddqRover = new SimpleListProperty<>(
            FXCollections.observableList(new ArrayList<>(0)));
    private final transient ListProperty<Double[]> ddqGRover = new SimpleListProperty<>(
            FXCollections.observableList(new ArrayList<>(0)));
    // Rover Error [e_x][e_y][e_dx][e_dy]
    private final transient ListProperty<Double[]> errors = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>(1)));

    private final transient ListProperty<Float> q1Newton = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>(0)));
    private final transient ListProperty<Float> q2Newton = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>(0)));
    private final transient ListProperty<Float> q3Newton =new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>(0)));
    private final transient ListProperty<Float> q4Newton =new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>(0)));
    private final transient ListProperty<Float> q5Newton =new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>(0)));
    private final transient ListProperty<Float> q6Newton =new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>(0)));

    // map image's stream
    private byte[] animation;
    private final transient PropertyChangeSupport changes = new PropertyChangeSupport(this);
    private transient ArrayList<Obstacle> obsList;

    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
    }

    public void notifyPropertyChange(String propertyName, Object oldValue, Object newValue) {
        changes.firePropertyChange(propertyName, oldValue, newValue);
    }
    public static int getMaxPoint(){
        return RobotBean.MAXPOINT;
    }

    public void setRoverPos(double[][] roverPos) {
        RobotBean.MAXPOINT=roverPos.length;
        this.qRover.addAll(adaptToPropertyList(roverPos));
    }

    public void setRoverVel(double[][] roverVel) {
        this.dqRover.addAll(adaptToPropertyList(roverVel));

    }
    public void setRoverAcc(double[][] roverAcc) {
        this.ddqRover.addAll(adaptToPropertyList(roverAcc));
    }
    public void setError(double[][] error) {
        this.errors.addAll(adaptToPropertyList(error));
    }
    private ListProperty<Double[]> adaptToPropertyList(double[][] values) {
        ListProperty<Double[]> adapted = new SimpleListProperty<>(FXCollections.observableArrayList());
        for (double[] val : values) {
            adapted.add(ArrayUtils.toObject(val));

        }
        return adapted;
    }

    public byte[] getAnimation(){return this.animation;}

    public void setAnimation(byte[] writableImage){
        this.animation = writableImage;
        changes.firePropertyChange("ANIMATION",false,this);
    }
    // PUMA
    public ObservableList<Float> getQ() {
        return q.get();
    }

    public ListProperty<Float> qProperty() {
        return q;
    }

    public synchronized void setQ(ObservableList<Float> q) {
        this.q.set(q);
    }
    // ROVER
    public ObservableList<Double[]> getqRover() {
        return this.qRover.get();
    }

    public ListProperty<Double[]> qRoverProperty() {
        return qRover;
    }

    public void setqRover(ObservableList<Double[]> qRover) {
        this.qRover.addAll(qRover);
    }

    public ObservableList<Double[]> getDqRover() {
        return dqRover.get();
    }

    public ListProperty<Double[]> dqRoverProperty() {
        return dqRover;
    }

    public void setDqRover(ObservableList<Double[]> dqRover) {
        this.dqRover.addAll(dqRover);
    }

    public ObservableList<Double[]> getDdqRover() {
        return ddqRover.get();
    }

    public ListProperty<Double[]> ddqRoverProperty() {
        return ddqRover;
    }

    public void setDdqRover(ObservableList<Double[]> ddqRover) {
        this.ddqRover.addAll(ddqRover);
    }

    public ObservableList<Double[]> getErrors() {
        return errors.get();
    }

    public ListProperty<Double[]> errorsProperty() {
        return errors;
    }

    public void setErrors(ObservableList<Double[]> errors) {
        this.errors.addAll(errors);
    }

    public void setObsList(List<Obstacle> obstacleList) {
        this.obsList = (ArrayList<Obstacle>) obstacleList;
    }
    public List<Obstacle> getObsList(){
        return obsList;
    }

    public ObservableList<Double[]> getqGRover() {
        return qGRover.get();
    }

    public ListProperty<Double[]> qGRoverProperty() {
        return qGRover;
    }

    public void setqGRover(double[][] qGRover) {
        this.qGRover.addAll(adaptToPropertyList(qGRover));
    }

    public ObservableList<Double[]> getDqGRover() {
        return dqGRover.get();
    }

    public ListProperty<Double[]> dqGRoverProperty() {
        return dqGRover;
    }

    public void setDqGRover(double[][] dqGRover) {
        this.dqGRover.addAll(adaptToPropertyList(dqGRover));
    }

    public ObservableList<Double[]> getDdqGRover() {
        return ddqGRover.get();
    }

    public ListProperty<Double[]> ddqGRoverProperty() {
        return ddqGRover;
    }

    public void setDdqGRover(double[][] ddqGRover) {
        this.ddqGRover.addAll(adaptToPropertyList(ddqGRover));
    }

    private final int[] texturePixels=new int[1024*1024*4];




    public void setSelectedShape(double[] shapeToPos) {
        this.selectedShape = shapeToPos;
    }

    public double[] getSelectedShape() {
        return selectedShape;
    }

    public void setSelectedOrient(double roll, double pitch, double yaw) {
        this.roll = roll;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public float getRoll() {
        return (float) this.roll;
    }
    public float getPitch() {
        return (float) this.pitch;
    }
    public float getYaw() {
        return (float) this.yaw;
    }



    public ObservableList<Float> getErrorNewton() {
        return errorNewton.get();
    }

    public ListProperty<Float> errorNewtonProperty() {
        return errorNewton;
    }

    public void setErrorNewton(ObservableList<Float> errorNewton) {
        this.errorNewton.set(errorNewton);
    }

    public ObservableList<Float> getQ1Newton() {
        return q1Newton.get();
    }

    public ListProperty<Float> q1NewtonProperty() {
        return q1Newton;
    }

    public void setQ1Newton(ObservableList<Float> q1Newton) {
        this.q1Newton.set(q1Newton);
    }

    public ObservableList<Float> getQ2Newton() {
        return q2Newton.get();
    }

    public ListProperty<Float> q2NewtonProperty() {
        return q2Newton;
    }

    public void setQ2Newton(ObservableList<Float> q2Newton) {
        this.q2Newton.set(q2Newton);
    }

    public ObservableList<Float> getQ3Newton() {
        return q3Newton.get();
    }

    public ListProperty<Float> q3NewtonProperty() {
        return q3Newton;
    }

    public void setQ3Newton(ObservableList<Float> q3Newton) {
        this.q3Newton.set(q3Newton);
    }

    public ObservableList<Float> getQ4Newton() {
        return q4Newton.get();
    }

    public ListProperty<Float> q4NewtonProperty() {
        return q4Newton;
    }

    public void setQ4Newton(ObservableList<Float> q4Newton) {
        this.q4Newton.set(q4Newton);
    }

    public ObservableList<Float> getQ5Newton() {
        return q5Newton.get();
    }

    public ListProperty<Float> q5NewtonProperty() {
        return q5Newton;
    }

    public void setQ5Newton(ObservableList<Float> q5Newton) {
        this.q5Newton.set(q5Newton);
    }

    public ObservableList<Float> getQ6Newton() {
        return q6Newton.get();
    }

    public ListProperty<Float> q6NewtonProperty() {
        return q6Newton;
    }

    public void setQ6Newton(ObservableList<Float> q6Newton) {
        this.q6Newton.set(q6Newton);
    }


}