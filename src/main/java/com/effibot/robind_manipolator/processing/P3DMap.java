package com.effibot.robind_manipolator.processing;

import com.effibot.robind_manipolator.bean.RobotBean;
import javafx.beans.property.ListProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import peasy.PeasyCam;
import processing.opengl.PGL;
import processing.opengl.PGraphics3D;
import processing.opengl.PJOGL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class P3DMap extends ProcessingBase{
    private static final Logger LOGGER = LoggerFactory.getLogger(P3DMap.class.getName());
    private final List<Obstacle> obsList;
    private static final int NX = 2;
    private static final int NY = 1;
    private final int mapH;
    private final int bgColor = color(51,102,102);
    private final int[] shapeColor = new int[]{color(246,182,41), color(205,117,149), color(88, 238, 255)};
    private final Semaphore[] next;

    private Robot r;
    private final PeasyCam[] cameras = new PeasyCam[NX * NY];
    private final Reference frame;
    private final RobotBean rb;
    private int qSelection = 0;                                // Joint selection (for interactive controls).

    private int i = (6) + 48;
    private boolean showPlots=false;

    private List<Plot2D> plots = new ArrayList<>();
    private Robot r1;
    private Float[] symPos = new Float[]{0f,0f};
    private boolean symEnd = false;
    private boolean inkEnd = false;
    public P3DMap(List<Obstacle> obsList, RobotBean rb, Semaphore[] next) {
        super();
        this.obsList = obsList;
        this.rb = rb;
        size = 1024;
        mapH = 20;
        frame = new Reference(this);
        observers = new ArrayList<>();
        this.padding = 5;
        this.next = next;
    }


    @Override
    public void draw() {
        setGLGraphicsViewport(0, 0, width, height);
        background(153,204,153);
        // Aggiungo degli effetti di luce direzionale
        directionalLight(126F, 126F, 126F, -1, 1, (float) -0.7);
        // Aggiungo degli effetti di luce ambientale
        ambientLight(200, 200, 200);
        // setup bg
        for (int cameraIdx = 0; cameraIdx < cameras.length; cameraIdx++) {
            pushStyle();
            pushMatrix();
            switch (cameraIdx) {
                case 0 -> draw3Dmap(setupScene(cameras[cameraIdx],cameraIdx));
                case 1 -> draw3Drobot(setupScene(cameras[cameraIdx],cameraIdx));
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

    float dx = 0;
    int dy = 0;
    int dz = 0;
    private LinkedBlockingQueue<Float[]> symQueue;

    @Override
    public void setup() {
        super.setup();
        r = new Robot(this,rb);
        symQueue=setupSimulationQueue();
        r1 = new Robot(this,rb);
//        r1 = setup3DRobot();
//        r1 = new Robot(this,rb,sequence,false);
        surface.setTitle("Mappa 3D");
        rectMode(CENTER);
        //setGradient(0, 0, width, height, c1, c2, Y_AXIS);
        int tilex = floor((float)(width-padding) / NX);
        int tiley = floor((float)(height-padding) / NY);

        // viewport offset ... corrected gap due to floor()
        int offx = (width - (tilex * NX -padding)) / 2;
        int offy = (height - (tiley * NY-padding)) / 2;

        // viewport dimension
        int cw = tilex - padding;
        int ch = tiley - padding;

        // create new viewport for each camera
        for (int y = 0; y < NY; y++) {
            for (int x = 0; x < NX; x++) {
                int id = y * NX + x;
                int cx = offx + x * tilex;
                int cy = offy + y * tiley;
                if(x == 0) cameras[id] = new PeasyCam(this, 1500);
                else cameras[id] = new PeasyCam(this, 500);
                cameras[id].setViewport(cx, cy, cw, ch); // this is the key of this whole demo
            }
        }

        plots = new Plot2D(this).initializePlot(this);
    }

    private LinkedBlockingQueue<Float[]> setupSimulationQueue() {
//         Setup Bindings
        ListProperty<Double[]> qRoverObs = rb.qRoverProperty();
        LinkedBlockingQueue<Float[]> queue = new LinkedBlockingQueue<>();
        Float[] pos;
        for(Double[] value : qRoverObs.get()) {
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
        }else if (key =='K'|| key == 'k'){
            showPlots=true;
        }else if (key =='W'|| key == 'w'){
            showPlots=false;
        }
        println("""
                [dx, dy, dz] = {%f, %d, %d}
                """.formatted(dx, dy, dz));
//        if (key == '1') {
//            r.setJoint(0,0.2f);
//        }
//        if (key == '2') {
//            r.setJoint(1,0.2f);
//        }
//        if (key == '3') {
//            r.setJoint(2,0.2f);
//        }
//        if (key == '4') {
//            r.setJoint(3,0.2f);
//        }
//        if (key == '5') {
//            r.setJoint(4,0.2f);
//        }
//        if (key == '6') {
//            r.setJoint(5,0.2f);
//        }
        // Select joint to control (with some ASCII magic).
        if ((key >= '1') && (key <= (char) i)) {
            qSelection = key - 48 - 1;  // It's an array index.
        }

    }

    public PeasyCam setupScene(PeasyCam cam,int id) {
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
//        cam.setYawRotationMode();
        cam.setRightDragHandler(null);
        cam.setCenterDragHandler(null);
        switch(id){
            case 0 -> {
                cam.setMinimumDistance(1000);
                cam.setMaximumDistance(1700);
            }
            case 1 ->{
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
        scale(1,-1,1);
        rotateX(-PI / 2);
        rotateZ(-PI/2);
        //!! End camera setup
        return cam;
    }
    double[][] dh;

    public void draw3Dmap(PeasyCam cam) {
        int zeroH = -300;
        // scene objects
        pushMatrix();
        translate(0, 0, zeroH);  // translate to a nicer vertical position
        // draw the floor
        fill(200);
        box(size, size, mapH);
        // elevate everything to the top of the floor
        translate(0, 0, mapH / 2.0f);
        // origin of R3
        frame.show(true);
        stroke(0);
        strokeWeight(1);
        fill(100);
        // draw obstacles
        for (Obstacle obs : obsList) {
            pushMatrix();
            translate(obs.getYc() - size / 2.0f, obs.getXc() - size / 2.0f, obs.getZc());
            if (obs.getXc() == rb.getShapePos()[0][2] && obs.getYc() == rb.getShapePos()[0][1])
                fill(shapeColor[0]);
            else if (obs.getXc() == rb.getShapePos()[1][2] && obs.getYc() == rb.getShapePos()[1][1])
                fill(shapeColor[1]);
            else if (obs.getXc() == rb.getShapePos()[2][2] && obs.getYc() == rb.getShapePos()[2][1])
                fill(shapeColor[2]);
            else
                fill(100);

            box(obs.getR(), obs.getR(), obs.getH());

            popMatrix();
        }

            translate(0, 0, dz+9.5f);

        pushMatrix();

        if(!symQueue.isEmpty()){
            thread("simulinkModel");
        } else {
            try {
              next[0].release();
              dh = r.dhValue(r.getDhTable());
              System.out.format("[%f,%f,%f,%f]\n[%f,%f,%f,%f]\n[%f,%f,%f,%f]\n\n",
                      dh[0][0],dh[0][1],dh[0][2],dh[0][3],
                      dh[1][0],dh[1][1],dh[1][2],dh[1][3],
                      dh[2][0],dh[2][1],dh[2][2],dh[2][3]);
            } catch (Exception e){
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

        cam.endHUD();
    }
    public void draw3Drobot(PeasyCam cam){
        translate(0,0,-100);


        r1.drawLink();
        int nPoints = 300;
        cam.beginHUD();
        if (showPlots) {
            this.plots.get(qSelection).drawCanvas();
            this.plots.get(qSelection).drawGrid();
//            this.plots.get(qSelection).addLine(qrs_p[qSelection], nPoints, 255);
//            this.plots.get(qSelection).addLine(qs_p[qSelection], nPoints, 16711680);
        }


        cam.endHUD();
    }
    public void setGLGraphicsViewport(int x, int y, int w, int h) {
        PGraphics3D pg = (PGraphics3D) this.g;
        PJOGL pgl = (PJOGL) pg.beginPGL();
        pg.endPGL();
        pgl.enable(PGL.SCISSOR_TEST);
        pgl.scissor(x, y, w, h);
        pgl.viewport(x, y, w, h);
    }

    public void simulinkModel(){
        try {
            if(!symQueue.isEmpty()) {
                symPos = symQueue.take();
            }
        } catch (InterruptedException e) {
            LOGGER.error("Taking Queue Error",e);
            Thread.currentThread().interrupt();
        }

    }



}