package com.effibot.robind_manipolator.modules.setting;

import com.effibot.robind_manipolator.bean.SettingBean;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class SettingController {
    private final SettingBean sb;
    private final SettingModule sm;
    public SettingController(SettingModule sm, SettingBean sb){
        this.sm = sm;
        this.sb = sb;
    }

    public void setIdByShape(String shape){
        ListProperty<Double> list = new SimpleListProperty<>();
        ArrayList<double[]> shapesID = sb.getShapeIdList();
        switch (shape){
            case "Sfera"-> list.setValue(arrayToObsListProp(shapesID.get(0)));
            case "Cono"-> list.setValue(arrayToObsListProp(shapesID.get(1)));
            case "Cubo"-> list.setValue(arrayToObsListProp(shapesID.get(2)));
        }
        sb.setIdList(list);
    }
    private ObservableList<Double> arrayToObsListProp(double[] array){
        return FXCollections.observableList(
                Arrays.asList(
                        ArrayUtils.toObject(
                                array
                        )
                )
        );
    }
}