package com.effibot.robind_manipolator.bean;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.lang.ArrayUtils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class IntroBean implements Serializable {
    private final PropertyChangeSupport changes= new PropertyChangeSupport(this);


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

    private transient double[][] obsList;


    public double[][] getObsList() {
        return obsList;
    }

    public void setObsList(double[][] obsList) {
        notifyPropertyChange("OBSUPDATE",this.obsList,obsList);
        this.obsList = obsList;
    }

   ////




}