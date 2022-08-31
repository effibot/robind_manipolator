package com.effibot.robind_manipolator.Processing;

import com.effibot.robind_manipolator.TCP.GameState;
import com.effibot.robind_manipolator.TCP.TCPFacade;
import peasy.PeasyCam;
import processing.opengl.PGL;
import processing.opengl.PGraphics3D;
import processing.opengl.PJOGL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class P3DMap extends ProcessingBase{
    private final List<Obstacle> obsList;
    private final int NX = 2;
    private final int NY = 1;
    private final int mapH;
    private final int bgColor = color(51,102,102);
    private static double[][] ps;
    private final GameState gm;
    private final double[][] points;

    private Robot r;
    private final PeasyCam[] cameras = new PeasyCam[NX * NY];
    private Reference frame;

    private int qSelection = 0;                                // Joint selection (for interactive controls).

    private int i = (6) + 48;
    private boolean showPlots=false;

    private ArrayList<Plot2D> plots = new ArrayList<>();
    private final TCPFacade tcp = TCPFacade.getInstance();


    private  int simIdx = 0;
    private int task = 0;
    public P3DMap(List<Obstacle> obsList) {
        this.obsList = obsList;
        size = 1024;
        mapH = 20;
        frame = new Reference(this);
        observers = new ArrayList<>();
        this.padding = 5;
        this.gm = GameState.getInstance();
        this.points = gm.getSq();

    }


    @Override
    public void draw() {
        setGLGraphicsViewport(0, 0, width, height);
        background(153,204,153);
        // Aggiungo degli effetti di luce direzionale
        directionalLight(126F, 126F, 126F, (float) -1, (float) 1, (float) -0.7);
        // Aggiungo degli effetti di luce ambientale
        ambientLight(200, 200, 200);
        // setup bg
        for (int i = 0; i < cameras.length; i++) {
            pushStyle();
            pushMatrix();
            switch (i) {
                case 0 -> draw3Dmap(setupScene(cameras[i],i));
                case 1 -> draw3Drobot(setupScene(cameras[i],i));
            }
            popMatrix();
            popStyle();
        }
    }

    @Override
    public void settings() {
        int WIDTH3D = 1366;
        int HEIGHT3D = 768;
        size(WIDTH3D, HEIGHT3D, P3D);
        pixelDensity(1);
        smooth(8);
    }

    float dx = 0;
    int dy = 0;
    int dz = 0;


    @Override
    public void setup() {
        super.setup();
        surface.setTitle("Mappa 3D");
        rectMode(CENTER);
        r = Robot.getInstance(this);
        //setGradient(0, 0, width, height, c1, c2, Y_AXIS);
        int tilex = floor((width-padding) / NX);
        int tiley = floor((height-padding) / NY);

        // viewport offset ... corrected gap due to floor()
        int offx = (width - (tilex * NX-padding)) / 2;
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
        int y_inv = height - y - h; // inverted y-axis
        // scissors-test and viewport transformation
        setGLGraphicsViewport(x, y_inv, w, h);
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
            translate(obs.getXc() - size / 2.0f, obs.getYc() - size / 2.0f, obs.getZc());
            box(obs.getR(), obs.getR(), obs.getH());
            popMatrix();
        }
            translate((float) 0, (float) 0, dz+9.5f);

        pushMatrix();

        if(simIdx<points.length-1 & task == 0) {
            simIdx+=1;
        }else if(simIdx == points.length-1 && task<2){
            task = task+1;
        }
        if(task<2) {
            HashMap<String, Object> msg = new HashMap<>();
            switch (task) {
                case 1:
                    msg.put("PROC","IK");
                    msg.put("X",gm.getXdes()- ps[ps.length-1][1]);
                    msg.put("Y",gm.getYdes()- ps[ps.length-1][0]);
                    msg.put("Z",gm.getZdes());
                    msg.put("ROLL",gm.getRoll());
                    msg.put("PITCH",gm.getPitch());
                    msg.put("YAW",gm.getYaw());
                    tcp.sendMsg(msg);
                    task +=1;
                    break;
                case 2:
                    msg.put("PROC", "VIS");
                    msg.put("SHAPE", gm.getSelectedShape());
                    ArrayList<HashMap> res = tcp.sendMsg(msg);
                    task +=1;
                    break;
            }
        }
        translate((float)points[simIdx][0]-512,(float)points[simIdx][1]-512,-9.5f);

        r.drawLink();
        popMatrix();
        popMatrix();

        // screen-aligned 2D HUD
        cam.beginHUD();

        cam.endHUD();
    }
    public void draw3Drobot(PeasyCam cam){
        translate(0,0,-100);


        r.drawLink();
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

    public void setPosition(){
        this.ps = GameState.getInstance().getSq();

    }


}