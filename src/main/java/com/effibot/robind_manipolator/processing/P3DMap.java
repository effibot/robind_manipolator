package com.effibot.robind_manipolator.processing;

import com.effibot.robind_manipolator.bean.RobotBean;
import javafx.beans.property.ListProperty;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import peasy.PeasyCam;
import processing.core.PConstants;
import processing.core.PShape;
import processing.opengl.PGL;
import processing.opengl.PGraphics3D;
import processing.opengl.PJOGL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import com.effibot.robind_manipolator.oscilloscope.Oscilloscope;
public class P3DMap extends ProcessingBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(P3DMap.class.getName());

    private static final String CURRENT_STRING = "current";
    private List<Obstacle> obsList;
    private static final int NX = 2;
    private static final int NY = 1;
    private final int mapH;
    private final int bgColor = color(51, 102, 102);
    private final int[] shapeColor = new int[]{color(246, 182, 41), color(205, 117, 149), color(88, 238, 255)};
    private final Semaphore[] next;
    private static final float SHAPE_DIAMETER = 40;

    private final ArrayList<processing.core.PShape> pShapeArrayList = new ArrayList<>();

    private Robot r;
    private final PeasyCam[] cameras = new PeasyCam[NX * NY];
    private final Reference[] frame;
    private final RobotBean rb;
    private int qSelection = 0;// Joint selection (for interactive controls).

    private int charOffset = (6) + 48;
    private Robot r1;
    private Float[] symPos = new Float[]{0f, 0f, 0f};

    private static final int LEFT_MARGIN = 2;
    private static final int UP_MARGIN = 2;
    private static final int PLOT_HEIGHT = 175;
    private static final int PLOT_WIDTH = 220;
    private static final int NEWTON_PLOT_WIDTH = 650;
    private static final int NEWTON_PLOT_HEIGHT = 150;
    private processing.core.PImage textureMap;
    private static final String REFERENCE_STRING = "reference";
    private static final String STROKE_LINE = "strokeline";
    private static final String TITLE_COLOR = "titlecolor";
    private double[] selectedShapePos;
    private double[] robotPos;


    public P3DMap(RobotBean rb, Semaphore[] next) {
        super();
        this.rb = rb;
        size = 1024;
        mapH = 20;
        frame = new Reference[]{new Reference(this), new Reference(this)};
        observers = new ArrayList<>();
        this.padding = 5;
        this.next = next;
        Oscilloscope.getInstance().setProcessingBase(this);
        Oscilloscope.getInstance().setRb(this.rb);
        rb.addPropertyChangeListener(Oscilloscope.getInstance());

    }

    public void setObs() {
        obsList = rb.getObsList();
    }

    private processing.core.PShape sphereShape() {
        float radius = SHAPE_DIAMETER / 2;
        PShape sphere = createShape(SPHERE, radius);
        sphere.setFill(shapeColor[0]);
        return sphere;
    }

    private processing.core.PShape coneShape() {
        float radius = SHAPE_DIAMETER / 2;
        processing.core.PShape ps = createShape();
        ps.beginShape(TRIANGLE_STRIP);
        ps.noStroke();
        for (int angle = 0; angle <= 360; angle += 20) {
            ps.vertex(radius * cos(radians(angle)), radius * sin(radians(angle)), 0);
            ps.vertex(0, 0, SHAPE_DIAMETER);
        }
        ps.endShape();
        return ps;
    }

    private processing.core.PShape cubeShape() {
        PShape cubeShape = createShape(BOX, SHAPE_DIAMETER);
        cubeShape.setFill(shapeColor[2]);
        return cubeShape;
    }


    @Override
    public void draw() {
//        frameRate(100);
        setGLGraphicsViewport(0, 0, width, height);
        background(153, 204, 153);
        // Aggiungo degli effetti di luce direzionale
        directionalLight(126F, 126F, 126F, -1, 1, (float) -0.7);
        // Aggiungo degli effetti di luce ambientale
        ambientLight(200, 200, 200);
        // setup bg
        for (int cameraIdx = 0; cameraIdx < cameras.length; cameraIdx++) {
            pushStyle();
            pushMatrix();
            switch (cameraIdx) {
                case 0 -> draw3DMap(setupScene(cameras[cameraIdx], cameraIdx));
                case 1 -> draw3DRobot(setupScene(cameras[cameraIdx], cameraIdx));
                default -> LOGGER.warn("DRAW3DMAP/ROBOT not mapped");
            }
            popMatrix();
            popStyle();
        }
    }

    @Override
    public void settings() {
        int width3D = 1366;
        int height3D = 768;
        size(width3D, height3D, P3D);
        pixelDensity(1);
        smooth(8);

    }

    static float dx = 1f;


    int dy = 0;
    int dz = 0;
    private LinkedBlockingQueue<Float[]> symQueue;
    private boolean showSpace = false;
    private float[] qFinal;

    @Override
    public void setup() {
        super.setup();
        r = new Robot(this, rb, true);
        symQueue = setupSimulationQueue();
        // setup inverse kin vars
        robotPos = ArrayUtils.toPrimitive(rb.getqRover().get(rb.getqRover().size() - 1));
        LOGGER.info("ROBOT POS:{}",robotPos);
        selectedShapePos = rb.getSelectedShape();
        if (selectedShapePos != null) {
            targetPosRel = new float[]{((float) selectedShapePos[1]-(float)robotPos[1] ) ,
                    ((float) selectedShapePos[2] -(float)robotPos[0]) ,
                    (float) selectedShapePos[3] + 20};
        }
        LOGGER.info("RELPOS:{}",targetPosRel);
        r1 = new Robot(this, rb, false);
        selectedShape = selectShape();
        qFinal = r1.getQ();
        // continue the setup
        surface.setTitle("Mappa 3D");
        rectMode(CENTER);
        //setGradient(0, 0, width, height, c1, c2, Y_AXIS);
        int tilex = floor((float) (width - padding) / NX);
        int tiley = floor((float) (height - padding) / NY);

        // viewport offset ... corrected gap due to floor()
        int offx = (width - (tilex * NX - padding)) / 2;
        int offy = (height - (tiley * NY - padding)) / 2;

        // viewport dimension
        int cw = tilex - padding;
        int ch = tiley - padding;

        // create new viewport for each camera
        for (int y = 0; y < NY; y++) {
            for (int x = 0; x < NX; x++) {
                int id = y * NX + x;
                int cx = offx + x * tilex;
                int cy = offy + y * tiley;
                if (x == 0) cameras[id] = new PeasyCam(this, 1500);
                else cameras[id] = new PeasyCam(this, 500);
                cameras[id].setViewport(cx, cy, cw, ch); // this is the key of this whole demo
            }
        }
        pShapeArrayList.add(sphereShape());
        pShapeArrayList.add(coneShape());
        pShapeArrayList.add(cubeShape());
        initializeRoverPlot();
        File file = new File("src/main/resources/com/effibot/robind_manipolator/img/textureImg.png");
        textureMap = loadImage(file.getAbsolutePath());


    }

    public void initializeRoverPlot() {
        // Rover Plots
        Oscilloscope.getInstance().addPlot("X", LEFT_MARGIN, UP_MARGIN, PLOT_WIDTH, PLOT_HEIGHT);
        Oscilloscope.getInstance().addPlot("Y", LEFT_MARGIN, 2 * UP_MARGIN + PLOT_HEIGHT, PLOT_WIDTH, PLOT_HEIGHT);
        Oscilloscope.getInstance().addPlot("VX", 2 * LEFT_MARGIN + PLOT_WIDTH, UP_MARGIN, PLOT_WIDTH, PLOT_HEIGHT);
        Oscilloscope.getInstance().addPlot("VY", 2 * LEFT_MARGIN + PLOT_WIDTH, 2 * UP_MARGIN + PLOT_HEIGHT, PLOT_WIDTH, PLOT_HEIGHT);
        Oscilloscope.getInstance().addPlot("AX", 3 * LEFT_MARGIN + 2 * PLOT_WIDTH, UP_MARGIN, PLOT_WIDTH, PLOT_HEIGHT);
        Oscilloscope.getInstance().addPlot("AY", 3 * LEFT_MARGIN + 2 * PLOT_WIDTH, 2 * UP_MARGIN + PLOT_HEIGHT, PLOT_WIDTH, PLOT_HEIGHT);

        Oscilloscope.getInstance().addPlot("Q1", 2 * LEFT_MARGIN + padding, 2 * UP_MARGIN + NEWTON_PLOT_HEIGHT, NEWTON_PLOT_WIDTH, NEWTON_PLOT_HEIGHT);
        Oscilloscope.getInstance().addPlot("Q2", 2 * LEFT_MARGIN + padding, 2 * UP_MARGIN + NEWTON_PLOT_HEIGHT, NEWTON_PLOT_WIDTH, NEWTON_PLOT_HEIGHT);
        Oscilloscope.getInstance().addPlot("Q3", 2 * LEFT_MARGIN + padding, 2 * UP_MARGIN + NEWTON_PLOT_HEIGHT, NEWTON_PLOT_WIDTH, NEWTON_PLOT_HEIGHT);
        Oscilloscope.getInstance().addPlot("Q4", 2 * LEFT_MARGIN + padding, 2 * UP_MARGIN + NEWTON_PLOT_HEIGHT, NEWTON_PLOT_WIDTH, NEWTON_PLOT_HEIGHT);
        Oscilloscope.getInstance().addPlot("Q5", 2 * LEFT_MARGIN + padding, 2 * UP_MARGIN + NEWTON_PLOT_HEIGHT, NEWTON_PLOT_WIDTH, NEWTON_PLOT_HEIGHT);
        Oscilloscope.getInstance().addPlot("Q6", 2 * LEFT_MARGIN + padding, 2 * UP_MARGIN + NEWTON_PLOT_HEIGHT, NEWTON_PLOT_WIDTH, NEWTON_PLOT_HEIGHT);
        Oscilloscope.getInstance().addPlot("E", 2 * LEFT_MARGIN + padding, UP_MARGIN, NEWTON_PLOT_WIDTH, NEWTON_PLOT_HEIGHT);

        Oscilloscope.getInstance().setPlotProperty("X", STROKE_LINE, CURRENT_STRING, 3);
        Oscilloscope.getInstance().setPlotProperty("Y", STROKE_LINE, CURRENT_STRING, 3);
        Oscilloscope.getInstance().setPlotProperty("VX", STROKE_LINE, CURRENT_STRING, 3);
        Oscilloscope.getInstance().setPlotProperty("VY", STROKE_LINE, CURRENT_STRING, 3);
        Oscilloscope.getInstance().setPlotProperty("AX", STROKE_LINE, CURRENT_STRING, 3);
        Oscilloscope.getInstance().setPlotProperty("AY", STROKE_LINE, CURRENT_STRING, 3);

        Oscilloscope.getInstance().setPlotProperty("Q1", STROKE_LINE, REFERENCE_STRING, 2);
        Oscilloscope.getInstance().setPlotProperty("Q2", STROKE_LINE, REFERENCE_STRING, 2);
        Oscilloscope.getInstance().setPlotProperty("Q2", STROKE_LINE, REFERENCE_STRING, 2);
        Oscilloscope.getInstance().setPlotProperty("Q3", STROKE_LINE, REFERENCE_STRING, 2);
        Oscilloscope.getInstance().setPlotProperty("Q4", STROKE_LINE, REFERENCE_STRING, 2);
        Oscilloscope.getInstance().setPlotProperty("Q5", STROKE_LINE, REFERENCE_STRING, 2);
        Oscilloscope.getInstance().setPlotProperty("Q6", STROKE_LINE, REFERENCE_STRING, 2);
        Oscilloscope.getInstance().setPlotProperty("E", STROKE_LINE, REFERENCE_STRING, 2);


    }

    private LinkedBlockingQueue<Float[]> setupSimulationQueue() {
//         Setup Bindings
        ListProperty<Double[]> qRoverObs = rb.qRoverProperty();
        LinkedBlockingQueue<Float[]> queue = new LinkedBlockingQueue<>();
        Float[] pos;
        for (Double[] value : qRoverObs.get()) {
            pos = new Float[]{Float.valueOf(value[0].toString()),
                    Float.valueOf(value[1].toString())};
            try {
                queue.put(pos);
            } catch (InterruptedException e) {
                LOGGER.error("SymQueue put failed");
                Thread.currentThread().interrupt();
            }
        }
        return queue;
    }


    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    int elbow = -1;

    @Override
    public void keyPressed() {
        if (key == 'A' || key == 'a') {
            dx = dx + 1f;
        } else if (key == 's' || key == 'S') {
            dy = dy + 1;
        } else if (key == 'd' || key == 'D') {
            dz = dz + 1;
        } else if (key == 'z') {
            dx = dx - 1f;
        } else if (key == 'x') {
            dy = dy - 1;
        } else if (key == 'c') {
            dz -= 1;
        } else if (key == 'K' || key == 'k') {
            Oscilloscope.getInstance().setAllRoverVisible(true);
        } else if (key == 'W' || key == 'w') {
            Oscilloscope.getInstance().setAllRoverVisible(false);
        } else if (key == 'M' || key == 'm') {
            Oscilloscope.getInstance().setQNewtonVisible(false, qSelection);
        } else if (key == 'F' || key == 'f') {
            setShowSpace(!showSpace);
            setIK(!isIK);
        } else if (key == 'G' || key == 'g') {
            elbow += 1;
            LOGGER.info("Elbow {}", elbow % 7);
            qFinal = r1.inverseKinematics(targetPosRel[0], targetPosRel[1], targetPosRel[2],
                    rb.getRoll(), rb.getPitch(), rb.getYaw(), elbow % 7);
        }
        if (key == 'o' || key == 'O') {
            P3DMap.dx += 0.1;
            r1.getShapeList().get(6).scale(P3DMap.dx);
        }
        if (key == 'p' || key == 'P') {
            P3DMap.dx -= 0.1;
            r1.getShapeList().get(6).scale(P3DMap.dx);

        }
        println("""
                [dx, dy, dz] = {%f, %d, %d}
                """.formatted(dx, dy, dz));

        // Select joint to control (with some ASCII magic).
        if ((key >= '1') && (key <= (char) charOffset)) {
            qSelection = key - 48 - 1;  // It's an array index.
            Oscilloscope.getInstance().setQNewtonVisible(true, qSelection);

        }

    }

    public PeasyCam setupScene(PeasyCam cam, int id) {
        //!! Begin camera setup
        int[] viewport = cam.getViewport();
        int w = viewport[2];
        int h = viewport[3];
        int x = viewport[0];
        int y = viewport[1];
        int yInv = height - y - h; // inverted y-axis
        // scissors-test and viewport transformation
        setGLGraphicsViewport(x, yInv, w, h);
        // set camera state like spinning a globe
        cam.setYawRotationMode();
        cam.setRightDragHandler(null);
        cam.setCenterDragHandler(null);
        switch (id) {
            case 0 -> {
                cam.setMinimumDistance(1000);
                cam.setMaximumDistance(1700);
            }
            case 1 -> {
                cam.setMinimumDistance(300);
                cam.setMaximumDistance(1000);
            }
            default -> LOGGER.warn("Id not mapped");

        }
        cam.feed();
        // projection - using camera viewport
        perspective(60 * PI / 180, w / (float) h, 1, 5000);
        // clear background (scissors makes sure we only clear the region we own)
//        background(51, 102, 153);
        background(bgColor);
        // Scalo su -y per ottenere un S.d.R. destrorso e ruoto per settare
        // Z > 0 su, X > 0 a destra, Y > 0 in avanti
        scale(1, -1, 1);
        rotateX(-PI / 2);
        rotateZ(-PI / 2);
        if (id == 0) rotateY(PI / 12);

        //!! End camera setup
        return cam;
    }

    public void draw3DMap(PeasyCam cam) {
        int zeroH = -300;
        // scene objects
        pushMatrix();
        translate(0, 0, zeroH);  // translate to a nicer vertical position
//        rotateY(PI/2);
        // draw the floor
        fill(207, 216, 220);
        box(size, size, mapH);
        translate(0, 0, mapH / 2f);
        beginShape();
        texture(textureMap);
        vertex(-512, -512, 0, 0);
        vertex(-512, 512, 1024, 0);
        vertex(512, 512, 1024, 1024);
        vertex(512, -512, 0, 1024);
        endShape();
        // elevate everything to the top of the floor
        translate(0, 0, mapH / 2.0f);
        // origin of R3
        stroke(0);
        strokeWeight(1);
        fill(100);
        // draw obstacles
        for (Obstacle obs : obsList) {
            pushMatrix();
            translate(obs.getYc() - size / 2.0f, obs.getXc() - size / 2.0f, obs.getZc());
            PShape pShape;
            if (obs.getXc() == rb.getShapePos()[0][2] && obs.getYc() == rb.getShapePos()[0][1]) {
                fill(224, 224, 224);
                pushMatrix();
                translate(0, 0, obs.getH()-mapH/2f);
                pShape = pShapeArrayList.get(0);
                shape(pShape);
                popMatrix();
            } else if (obs.getXc() == rb.getShapePos()[1][2] && obs.getYc() == rb.getShapePos()[1][1]) {
                fill(224, 224, 224);
                pushMatrix();
                translate(0, 0, obs.getH()-mapH/2f-SHAPE_DIAMETER/2f);
                pShape = pShapeArrayList.get(1);
                pShape.setFill(shapeColor[1]);
                shape(pShape);
                popMatrix();
            } else if (obs.getXc() == rb.getShapePos()[2][2] && obs.getYc() == rb.getShapePos()[2][1]) {
                fill(224, 224, 224);
                pushMatrix();
                translate(0, 0, obs.getH()-mapH/2f);
                pShape = pShapeArrayList.get(2);
                shape(pShape);
                popMatrix();
            } else
                fill(224, 224, 224);


            box(obs.getR(), obs.getR(), obs.getH());

            popMatrix();
        }

//        translate(0, 0, dz + 9.5f);

        pushMatrix();
        if (!symQueue.isEmpty()) {
            thread("simulinkModel");
        } else {
            try {
                next[0].release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//        else {
//            symEnd = true;
//            thread("inverseKinematic");
//        }
        translate(symPos[1] - 512, symPos[0] - 512, -5.5f);
        r.drawLink();

        popMatrix();
        popMatrix();

        // screen-aligned 2D HUD
        cam.beginHUD();

        Oscilloscope.getInstance().drawRoverOscilloscope();
        cam.endHUD();
    }

    private boolean isIK = false;
    private float[] targetPosRel;
    float k = (float) 0.01;

    public void draw3DRobot(PeasyCam cam) {
        translate(0, 0, -200);
        drawTarget();
        r1.setTable(r1.qProp(qFinal, k));
        r1.drawLink();
        r1.showSpace(showSpace);
        cam.beginHUD();
        Oscilloscope.getInstance().drawIKOscilloscope();
        cam.endHUD();
    }

    //    public void drawIK(){
//      oscilloscope.drawIKOscilloscope();
//
//    }
    private PShape selectedShape;

    private void drawTarget() {
        pushMatrix();
        // set reference frame
        double[] posBc = rb.getShapeBc();
        translate((float)posBc[0]-(float)robotPos[1],
                (float)posBc[1]-(float)robotPos[0],
                (float)selectedShapePos[3]+20);
//        LOGGER.info("POSITION SHAPE:{}",new float[]{(float)posBc[1]-(float)robotPos[1],(float)posBc[0]-(float)robotPos[0]});
        // draw sphere
        stroke(255, 0, 0);
        strokeWeight(2);
        noFill();
        shape(selectedShape);
        noStroke();
        // draw reference
        makeOrient();
        frame[1].show(true);
        popMatrix();
    }

    private PShape selectShape() {
        switch ((int) selectedShapePos[0]) {
            case 0 -> {
                return sphereShape();
            }
            case 1 -> {
                return coneShape();
            }
            case 2 -> {
                return cubeShape();
            }
            default -> {
                return new PShape(PConstants.SPHERE);
            }
        }
    }

    private void makeOrient() {
        rotateZ(radians(rb.getRoll()));
        rotateY(radians(rb.getPitch()));
        rotateX(radians(rb.getYaw()));
    }

    public void setGLGraphicsViewport(int x, int y, int w, int h) {
        PGraphics3D pg = (PGraphics3D) this.g;
        PJOGL pgl = (PJOGL) pg.beginPGL();
        pg.endPGL();
        pgl.enable(PGL.SCISSOR_TEST);
        pgl.scissor(x, y, w, h);
        pgl.viewport(x, y, w, h);
    }

    public void simulinkModel() {
        try {

            if (!symQueue.isEmpty()) {
                symPos = symQueue.take();
                rb.notifyPropertyChange("PLOT", false, true);
            }
        } catch (InterruptedException e) {
            LOGGER.error("Taking Queue Error", e);
            Thread.currentThread().interrupt();
        }

    }

    public boolean isShowSpace() {
        return showSpace;
    }

    public void setShowSpace(boolean showSpace) {
        this.showSpace = showSpace;
    }

    public boolean isIK() {
        return isIK;
    }

    public void setIK(boolean IK) {
        isIK = IK;
    }
}