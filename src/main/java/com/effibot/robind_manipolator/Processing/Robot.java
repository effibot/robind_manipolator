package com.effibot.robind_manipolator.Processing;

import com.effibot.robind_manipolator.Main;
import processing.core.PShape;
import java.util.ArrayList;
import static processing.core.PConstants.PI;

public class Robot {
    private float[] q = new float[6];
    private final float L1 = 40.0f;
    private final float L2 = 62.0f;
    //private final float L3 = 0.0f;
    private final float L4 = 60.0f;
    //private final float L5 = 0.0f;
    private final float L6 = 12.0f;
    private final float a1 = -PI / 2;
    //private final float a2 = 0.0f;
    private final float a3 = -PI / 2;
    private final float a4 = PI / 2;
    private final float a5 = -PI / 2;
    private final float offset1 = 29.0f;
    private ArrayList<PShape> shapeList = new ArrayList<>();
    private ArrayList<Reference> referenceList = new ArrayList<>();
    private String path = "src/main/resources/com/effibot/robind_manipolator/models/";
    private float scaleFactor;
    private final ProcessingBase p3d;

    public Robot(ProcessingBase p3d) {
        this.p3d = p3d;
        for (int i = 0; i <=  6; i++) {
            // load link_i
            String str = String.valueOf(Main.class.getResource("models/r" + i + "c.obj"));
//            this.shapeList.add(loadLink(path + "r" + i + "c.obj"));
            this.shapeList.add(loadLink(str));
            // assign q_i
            if (i <= 5) this.q[i] = 0.0f;
        }
        this.p3d.shape(shapeList.get(0));
    }
    private void dh(float theta, float d, float alpha, float a) {
        p3d.rotateZ(theta);
        p3d.translate(0,0,d);
        p3d.rotateX(alpha);
        p3d.translate(a,0,0);
    }
    private PShape loadLink(String objectName) {
        PShape shape = p3d.loadShape(objectName);
        if (objectName.equals(path + "r0c.obj")) {
            scaleFactor = 0.3f;
        } else {
            scaleFactor = 1.2f;
        }
        shape.scale(scaleFactor);
        return shape;
    }
}