package com.effibot.robind_manipolator.Processing;

import peasy.PeasyCam;
import processing.opengl.PGL;
import processing.opengl.PGraphics3D;
import processing.opengl.PJOGL;

import java.util.List;

public class P3DMap extends ProcessingBase{
    private final List<Obstacle> obsList;
    private final int NX = 2;
    private final int NY = 1;
    private final int mapH;
    private final int bgColor = color(51, 102, 153);

    private final PeasyCam[] cameras = new PeasyCam[NX * NY];
    private final Reference frame;
    public P3DMap(List<Obstacle> obsList){
        this.obsList = obsList;
        size = 1024;
        mapH = 20;
        frame = new Reference(this);
    }

    @Override
    public void draw() {
        setGLGraphicsViewport(0, 0, width, height);
        background(bgColor);

        //setGradient(0, 0, width, height, c1, c2, Y_AXIS);

        // setup bg
        for (int i = 0; i < cameras.length; i++) {
            pushStyle();
            pushMatrix();
            switch (i) {
                case 0 -> drawScene(setupScene(cameras[0]));
                case 1 -> {
                    setupScene(cameras[1]);
                    box(50);
                }
            }
            popMatrix();
            popStyle();
        }
    }

    @Override
    public void settings() {
        int WIDTH3D = 1366;
        int HEIGHT3D = 768;
        size(WIDTH3D, HEIGHT3D,P3D);
        pixelDensity(1);
        smooth(8);
    }

    int dx = 0;
    int dy = 0;
    int dz = 0;

    @Override
    public void setup() {
        super.setup();
        surface.setTitle("Mappa 3D");
        rectMode(CENTER);
        //setGradient(0, 0, width, height, c1, c2, Y_AXIS);
        int tilex = floor((width) / NX);
        int tiley = floor((height) / NY);

        // viewport offset ... corrected gap due to floor()
        int offx = (width  - (tilex * NX)) / 2;
        int offy = (height - (tiley * NY)) / 2;

        // viewport dimension
        int cw = tilex - padding;
        int ch = tiley - padding;

        // create new viewport for each camera
        for(int y = 0; y < NY; y++){
            for(int x = 0; x < NX; x++){
                int id = y * NX + x;
                int cx = offx + x * tilex;
                int cy = offy + y * tiley;
                cameras[id] = new PeasyCam(this, 1500);
                cameras[id].setViewport(cx, cy, cw, ch); // this is the key of this whole demo
            }
        }

    }
    @Override
    public void notifyObservers() {
        // TODO: decide if this method should be a dummy implementation
    }
    @Override
    public void keyPressed(){
        if (key == 'A' || key == 'a') {
            dx = dx +10;
        } else if(key == 's' || key == 'S'){
            dy = dy + 10;
        } else if (key == 'd' || key == 'D'){
            dz = dz +10;
        } else if(key == 'Z'){
            dx = dz-10;
        } else if (key == 'x'){
            dy = dy-10;
        } else if (key == 'c'){
            dz -=10;
        }
        println("""
                [dx, dy, dz] = {%d, %d, %d}
                """.formatted(dx, dy, dz));
    }
    public PeasyCam setupScene(PeasyCam cam) {
        //!! Begin camera setup
        int[] viewport = cam.getViewport();
        int w = viewport[2];
        int h = viewport[3];
        int x = viewport[0];
        int y = viewport[1];
        int y_inv =  height - y - h; // inverted y-axis
        // scissors-test and viewport transformation
        setGLGraphicsViewport(x, y_inv, w, h);
        // set camera state like spinning a globe
        cam.setYawRotationMode();
        cam.setRightDragHandler(null);
        cam.setCenterDragHandler(null);
        cam.setMinimumDistance(1600);
        cam.setMaximumDistance(1900);
        cam.feed();
        // projection - using camera viewport
        perspective(60 * PI/180, w/(float)h, 1, 5000);
        // clear background (scissors makes sure we only clear the region we own)
        background(51,102,153);
        // Ruoto il mondo per settare
        // Z > 0 su, X > 0 a destra, Y > 0 in avanti
        rotateX(PI/2);
        // Aggiungo degli effetti di luce direzionale
        directionalLight(126F, 126F, 126F, (float) -1, (float) -1, (float) -0.7);
        // Aggiungo degli effetti di luce ambientale
        ambientLight(200, 200, 200);
        //!! End camera setup
        return cam;
    }
    public void drawScene(PeasyCam cam){
        int zeroH = -540;

        // scene objects
        pushMatrix();
        translate(0,0, zeroH);  // translate to a nicer vertical position
        // draw the floor
        fill(200);
        box(size,size,mapH);
        // elevate everything to the top of the floor
        translate(0,0,mapH/2.0f);
        // origin of R3
        frame.show(true);
        stroke(0);
        strokeWeight(1);
        fill(100);
        // draw obstacles
        for(Obstacle obs : obsList){
            pushMatrix();
            translate(obs.getXc()-size/2.0f,obs.getYc()-size/2.0f,obs.getZc());
            box(obs.getR(),obs.getR(), obs.getH());
            popMatrix();
        }

        translate(dx-100,dy+200,dz+150);
        fill(140,100,20);
        box(96,96,300);
        popMatrix();
//        Robot r = new Robot(this);

        // screen-aligned 2D HUD
        cam.beginHUD();

        cam.endHUD();
    }

    void setGLGraphicsViewport(int x, int y, int w, int h){
        PGraphics3D pg = (PGraphics3D) this.g;
        PJOGL pgl = (PJOGL) pg.beginPGL();  pg.endPGL();

        pgl.enable(PGL.SCISSOR_TEST);
        pgl.scissor (x,y,w,h);
        pgl.viewport(x,y,w,h);
    }

}
