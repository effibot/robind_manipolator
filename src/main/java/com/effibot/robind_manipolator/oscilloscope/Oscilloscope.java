package com.effibot.robind_manipolator.oscilloscope;

import com.effibot.robind_manipolator.bean.RobotBean;
import com.effibot.robind_manipolator.processing.ProcessingBase;
import javafx.beans.property.ListProperty;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class Oscilloscope implements PropertyChangeListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(Oscilloscope.class.getName());
    private static ProcessingBase processingBase;
    private static Oscilloscope instance;
    private static RobotBean rb;
    private static final LinkedHashMap<String, Plot> plotMap = new LinkedHashMap<>();

    private Oscilloscope() {
    }

    public void addPlot(String title, int xPos, int yPos, int width, int height) {
        plotMap.put(title, new Plot(processingBase, title, xPos, yPos, width, height));
    }

    public void setRefBinding(String title, ListProperty<Float> refVal) {
        plotMap.get(title).referenceValPropertyProperty().bind(refVal);
    }

    public void setCurrBinding(String title, ListProperty<Float> currVal) {
        plotMap.get(title).currentValPropertyProperty().bind(currVal);
    }

    public void plotVisible(String name, boolean val) {
        plotMap.get(name).setVisible(val);

    }
    private static final List<String> ROVER_NAMES_PLOT = Arrays.asList("X","Y","VX","VY","AX","AY");

    public void setAllRoverVisible(boolean visible){
        for (String k : ROVER_NAMES_PLOT) {
                plotMap.get(k).setVisible(visible);
        }
    }

    public void drawRoverOscilloscope() {
        Plot plt;
        for (String k : ROVER_NAMES_PLOT) {
            plt = plotMap.get(k);
            if(plt.isVisible()) plt.drawPlot();
        }
    }
    public void drawIKOscilloscope() {
        Plot plt;
        for (String k : IK_NAMES_PLOT) {
            plt = plotMap.get(k);
            if(plt.isVisible()) plt.drawPlot();
        }
    }

    public ProcessingBase getProcessingBase() {
        return processingBase;
    }

    public static void setProcessingBase(ProcessingBase processingBase) {
        Oscilloscope.processingBase = processingBase;
    }

    public static synchronized Oscilloscope getInstance() {
        if (instance == null) {
            instance = new Oscilloscope();
        }
        return instance;
    }

    public RobotBean getRb() {
        return rb;
    }

    public static void setRb(RobotBean rb) {
        Oscilloscope.rb = rb;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("PLOT".equals(evt.getPropertyName())) {//                Double[] qRoverPlot = rb.getqRover().get(plotMap.get("X").getCurrentVal().size());
//                plotMap.get("X").setCurrentValProperty(Float.valueOf(qRoverPlot[0].toString()));
//                plotMap.get("Y").setCurrentValProperty(Float.valueOf(qRoverPlot[1].toString()));
//                Double[] qGRoverPlot = rb.getqGRover().get(plotMap.get("X").getReferenceVal().size());
//                plotMap.get("X").setReferenceValProperty(Float.valueOf(qGRoverPlot[0].toString()));
//                plotMap.get("Y").setReferenceValProperty(Float.valueOf(qGRoverPlot[1].toString()));
//                Double[] dqRoverPlot = rb.getDqRover().get(plotMap.get("X").getReferenceVal().size());
            try {
                addPoint("X", "getqRover", false, 0);
                addPoint("Y", "getqRover", false, 1);
                addPoint("X", "getqGRover", true, 0);
                addPoint("Y", "getqGRover", true, 1);
                addPoint("VX", "getDqRover", false, 0);
                addPoint("VY", "getDqRover", false, 1);
                addPoint("VX", "getDqGRover", true, 0);
                addPoint("VY", "getDqGRover", true, 1);
                addPoint("AX", "getDdqRover", false, 0);
                addPoint("AY", "getDdqRover", false, 1);
                addPoint("AX", "getDdqGRover", true, 0);
                addPoint("AY", "getDdqGRover", true, 1);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    private void addPoint(String plotName, String beanMethod, boolean reference, int index) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = RobotBean.class.getMethod(beanMethod);
        int size;
        Method plotMethod;
        Method valMethod;
        if(reference){
            plotMethod = Plot.class.getMethod("getReferenceVal");
            valMethod = Plot.class.getMethod("setReferenceValProperty", Float.class);
        } else {
            plotMethod = Plot.class.getMethod("getCurrentVal");
            valMethod = Plot.class.getMethod("setCurrentValProperty", Float.class);
        }
        size = ((ObservableList<?>)plotMethod.invoke(plotMap.get(plotName))).size();
        int maxsize = ((List<?>)m.invoke(rb)).size();
        if(size<maxsize) {
            Double[] values = (Double[]) ((List<?>) m.invoke(rb)).get(size);
            valMethod.invoke(plotMap.get(plotName), Float.valueOf(values[index].toString()));
        }
    }

//    private void addPoint(String plotName, String beanMethod,int qindex)throws NoSuchMethodException, InvocationTargetException, IllegalAccessException{
//        Method m = RobotBean.class.getMethod(beanMethod);
//        Method plotMethod = Plot.class.getMethod("getReferenceVal");
//        Method valMethod = Plot.class.getMethod("setReferenceValProperty", Float.class);
//
//
//
//    }
    private static final List<String> IK_NAMES_PLOT = Arrays.asList("Q1","Q2","Q3","Q4","Q5","Q6","E");

    public void setQNewtonVisible(boolean visible, int qSelection) {
//        String[] keys = plotMap.keySet().toArray(new String[0]);
        String toShow = "Q"+(qSelection+1);
        LOGGER.info(toShow);
        for (String k : IK_NAMES_PLOT) {
                plotMap.get(k).setVisible(false);
        }
        Plot plt = plotMap.get(toShow);
        if(plt!=null) plt.setVisible(visible);
        plt = plotMap.get("E");
        if(plt!=null) plt.setVisible(visible);

    }



    public void setPlotProperty(String title,String property,String lineType, float value){
        Plot plt = plotMap.get(title);
        if(plt!=null) {
            switch (property){
                case "strokeline"->{
                    switch (lineType){
                        case "reference"->plt.setReferenceLineStroke(value);
                        case "current"->plt.setCurrentLineStroke(value);
                        default-> LOGGER.info("Stroke not valid");
                    }
                }
                case "titlecolor"-> plt.setTitleColor((int)value);
            }
        }
    }

}