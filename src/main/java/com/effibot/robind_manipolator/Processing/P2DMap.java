package com.effibot.robind_manipolator.Processing;

import com.effibot.robind_manipolator.SceneController;
import processing.core.PApplet;

import java.util.ArrayList;

public class P2DMap extends ProcessingBase {
    private final int padding = 10;
    private final int mapColor = color(102, 102, 102);
    private final int size = 512;
    private int targetColor;
    private final float targetAlpha = 80f;
    private final float targetSize = 60.0f;

    private final int colorPass = color(0, 255, 0, targetAlpha);
    private final int colorFail = color(255, 0, 0, targetAlpha);
    private ArrayList<Obstacle> obsList;

    @Override
    public void settings() {
        size(size + 2 * padding, size + 2 * padding, P2D);
        pixelDensity(1);
    }

    @Override
    public void setup() {
        super.setup();
        surface.setTitle("Posizionamento Ostacoli");
        rectMode(CENTER);
        obsList = new ArrayList<>();
    }

    public void run() {
        String[] processingArgs = {"main.java.com.effibot.robind_manipolator.Processing.P2DMap"};
        PApplet.runSketch(processingArgs, this);
    }

    public void setJavaFX(SceneController controller) {
        this.controller = controller;
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

    public int getPadding() {
        return padding;
    }

    private void targetColorSelect() {
        // ObsList is empty> checks only if mouse is inside the box
        if (checkConstrains(mouseX + targetSize / 2, mouseY + targetSize / 2, padding, padding, size, size) &&
                checkConstrains(mouseX + targetSize / 2, mouseY - targetSize / 2, padding, padding, size, size) &&
                checkConstrains(mouseX - targetSize / 2, mouseY + targetSize / 2, padding, padding, size, size) &&
                checkConstrains(mouseX - targetSize / 2, mouseY - targetSize / 2, padding, padding, size, size)) {
            targetColor = colorPass;
        } else {
            targetColor = colorFail;
        }
    }

    void target() {
        pushMatrix();
        translate(mouseX, mouseY);
        targetColorSelect();
        fill(targetColor);
        noStroke();
        rect(0, 0, targetSize, targetSize);
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

    public void drawObstacles2D() {
        if (!obsList.isEmpty()) {
            for (Obstacle obs : obsList) {
                obs.drawObstacle2D();
            }
        }
    }

    public void addObstacle(int targetX, int targetY) {
        int id = obsList.size();
        Obstacle obs = new Obstacle(this, targetX, targetY, 0, 2 * targetSize, 100, id);
        obsList.add(obs);
    }

    boolean checkConstrains(float px, float py, float rx, float ry, float rw, float rh) {
        // is the point inside the rectangle's bounds?
        return px >= rx &&          // right of the left edge AND
                px <= rx + rw &&    // left of the right edge AND
                py >= ry &&         // below the top AND
                py <= ry + rh;      // above the bottom
    }
}