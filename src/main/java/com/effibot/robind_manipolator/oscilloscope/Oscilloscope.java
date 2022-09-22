package com.effibot.robind_manipolator.oscilloscope;

import com.effibot.robind_manipolator.bean.RobotBean;
import com.effibot.robind_manipolator.processing.ProcessingBase;
import javafx.beans.property.ListProperty;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;

public class Oscilloscope implements PropertyChangeListener {
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
        Plot plt = plotMap.get(title);
        plt.setReferenceBinding(refVal);
    }

    public void setCurrBinding(String title, ListProperty<Float> currVal) {
        Plot plt = plotMap.get(title);
        plt.setCurrentBinding(currVal);
    }

    public void plotVisible(String name, boolean val) {
        plotMap.get(name).setVisible(val);

    }
    public void setAllPlotVisible(boolean visible){
        String[] keys = plotMap.keySet().toArray(new String[0]);
        for (String k : keys) {
            plotMap.get(k).setVisible(visible);
        }
    }

    public static void drawOscilloscope() {
        String[] keys = plotMap.keySet().toArray(new String[0]);
        for (String k : keys) {
            plotMap.get(k).drawPlot();
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
        switch (evt.getPropertyName()) {
            case "PLOT" -> {
//                Double[] qRoverPlot = rb.getqRover().get(plotMap.get("X").getCurrentVal().size());
//                plotMap.get("X").setCurrentValProperty(Float.valueOf(qRoverPlot[0].toString()));
//                plotMap.get("Y").setCurrentValProperty(Float.valueOf(qRoverPlot[1].toString()));
//                Double[] qGRoverPlot = rb.getqGRover().get(plotMap.get("X").getReferenceVal().size());
//                plotMap.get("X").setReferenceValProperty(Float.valueOf(qGRoverPlot[0].toString()));
//                plotMap.get("Y").setReferenceValProperty(Float.valueOf(qGRoverPlot[1].toString()));
//                Double[] dqRoverPlot = rb.getDqRover().get(plotMap.get("X").getReferenceVal().size());
                try {
                    addPoint("X","getqRover",false, 0);
                    addPoint("Y","getqRover",false, 1);
                    addPoint("X","getqGRover",true, 0);
                    addPoint("Y","getqGRover",true, 1);
                    addPoint("VX","getDqRover",false, 0);
                    addPoint("VY","getDqRover",false, 1);
                    addPoint("VX","getDqGRover",true, 0);
                    addPoint("VY","getDqGRover",true, 1);
                    addPoint("AX","getDdqRover",false, 0);
                    addPoint("AY","getDdqRover",false, 1);
                    addPoint("AX","getDdqGRover",true, 0);
                    addPoint("AY","getDdqGRover",true, 1);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            case "PLOTIK" -> {

            }
            default -> {//fake
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
}