package com.effibot.robind_manipolator.processing;


import java.util.ArrayList;

public class P2DMap extends ProcessingBase {
    private final int mapColor = color(102, 102, 102);
    private int targetColor;
    private static final float TARGET_ALPHA = 80f;
    private static final float TARGET_SIZE = 32.0f;

    private final int colorPass = color(0, 255, 0, TARGET_ALPHA);
    private final int colorFail = color(255, 0, 0, TARGET_ALPHA);
    private ArrayList<Obstacle> obsList;

    public P2DMap(){
        observers = new ArrayList<>();
        size = 512;
    }

    @Override
    public void settings() {
        size(size + 2 * padding, size + 2 * padding, P2D);
        pixelDensity(1);
    }



    @Override
    public void setup() {
        super.setup();
        settings();
        surface.setTitle("Posizionamento Ostacoli");
        rectMode(CENTER);
        obsList = new ArrayList<>();
    }
    @Override
    public void exitActual(){
        surface.setVisible(false);
    }
    @Override
    public void draw() {
        // background
        setGradient(0, 0, width, height, c1, c2, Y_AXIS);
        fill(mapColor);
        stroke(0);
        pushMatrix();
        translate(width / 2.0f, height / 2.0f);
        rect(0, 0, size, size);
        popMatrix();
        noFill();
        noStroke();
        target();
        drawObstacles2D();
    }



    private void targetColorSelect() {
        // ObsList is empty> checks only if mouse is inside the box
        if (checkConstrains(mouseX + TARGET_SIZE / 2, mouseY + TARGET_SIZE / 2, padding, padding, size, size) &&
                checkConstrains(mouseX + TARGET_SIZE / 2, mouseY - TARGET_SIZE / 2, padding, padding, size, size) &&
                checkConstrains(mouseX - TARGET_SIZE / 2, mouseY + TARGET_SIZE / 2, padding, padding, size, size) &&
                checkConstrains(mouseX - TARGET_SIZE / 2, mouseY - TARGET_SIZE / 2, padding, padding, size, size)) {
            targetColor = colorPass;
        } else {
            targetColor = colorFail;
        }
    }

    private void target() {
        pushMatrix();
        translate(mouseX, mouseY);
        targetColorSelect();
        fill(targetColor);
        noStroke();
        rect(0, 0, TARGET_SIZE, TARGET_SIZE);
        noFill();
        popMatrix();
    }

    public void mouseClicked() {
        int targetX = 2 * (mouseX - padding);
        int targetY = 2 * (mouseY - padding);
        if (targetColor == colorPass) {
            if (!obsList.isEmpty()) {
                boolean isIn = false;
                int currSize = obsList.size() - 1;
                for (int i = 0; i <= currSize; i++) {
                    Obstacle obs = obsList.get(i);
                    if (checkConstrains(mouseX, mouseY, obs.getXc() / 2 + padding - obs.getR() / 2,
                            obs.getYc() / 2 + padding - obs.getR() / 2, obs.getR(), obs.getR())) {
                        isIn = true;
                    }
                }
                if (!isIn)
                    addObstacle(targetX, targetY);
            } else {
                addObstacle(targetX, targetY);
            }

        }
    }

    private void drawObstacles2D() {
        if (!obsList.isEmpty()) {
            for (Obstacle obs : obsList) {
                obs.drawObstacle2D();
            }
        }
    }

    private void addObstacle(int targetX, int targetY) {
        int id = obsList.size();
        final int obsHeight = 200;
        Obstacle obs = new Obstacle(this, targetX, targetY,
                obsHeight/2.0f, 2 * TARGET_SIZE, obsHeight, id);
        obsList.add(obs);
        notifyObservers();
    }

    private boolean checkConstrains(float px, float py, float rx, float ry, float rw, float rh) {
        // is the point inside the rectangle's bounds?
        return px >= rx &&          // right of the left edge AND
                px <= rx + rw &&    // left of the right edge AND
                py >= ry &&         // below the top AND
                py <= ry + rh;      // above the bottom
    }

    public ArrayList<Obstacle> getObstacleList() {
        return obsList;
    }
    // set method to let FX controller remove the last added obstacle to the list.
    public void setObstacleList(ArrayList<Obstacle> obsList) {
        this.obsList = obsList;
        notifyObservers();
    }
    @Override
    public void notifyObservers() {
        for(Observer observer : observers){
            observer.update(this);
        }
    }
}