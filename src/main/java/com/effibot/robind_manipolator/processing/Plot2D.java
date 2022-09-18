package com.effibot.robind_manipolator.processing;


import java.util.ArrayList;
import java.util.List;

import processing.core.PConstants;

import static processing.core.PApplet.ceil;
import static processing.core.PConstants.PI;

class Plot2D {

    /* 2D plot class parameters. */
    private final int titleBackground ;  // Title tile background color.
    private static final int TITLE_HEIGHT = 35;                         // Title tile height.
    private final int titleColor ;            // Title text color.
    private final int gridColor ;          // Grid lines color.

    private int xPos;         // X plot window position.
    private int yPos;         // Y plot window position.
    private int pWidth;       // Plot width.
    private int pHeight;      // Plot height (without title tile).
    private String pltTitle;  // Plot title.
    private int backColor;  // Background color.
    private int scaleFact;    // Scale factor for variable to plot.
    private final P3DMap p3d;

    public Plot2D(P3DMap p3DMap) {
        this.p3d = p3DMap;
        this.titleBackground = p3d.color(214, 182, 75);
        this.titleColor = p3d.color(0, 0, 0);
        this.gridColor = p3d.color(80, 80, 80);

    }

    public List<Plot2D> initializePlot(P3DMap p3DMap) {

        ArrayList<Plot2D> plots = new ArrayList<>();

        int nPoints = 300;          // Number of points to show for each plot.
        int[][] qLims = {{0, ceil(2 * PI)}, {0, ceil(2 * PI)}, {0, ceil(2 * PI)}, {0, ceil(2 * PI)}, {0, ceil(2 * PI)}, {0, ceil(2 * PI)}};
        int angScale = 50;          // Scale factor for angular joint variables.
        int[] qScales = {angScale, angScale, angScale, angScale, angScale, angScale};

        for (int i = 0; i < 6; i++) {
            Plot2D ploti = new Plot2D(p3DMap);
            plots.add(ploti);
            plots.get(i).setXYLims(0, nPoints, qLims[i][0], qLims[i][1], qScales[i]);
            plots.get(i).setPos(160, 180);
            plots.get(i).setBackground(255);
            plots.get(i).setTitle("Joint Variable " + (i + 1));

        }
        return plots;
    }

    /* Set plot dimensions. */
    public void setXYLims(int x1, int x2, int y1, int y2, int scale) {
        /* Plot attributes. */
        // X axis minimum value.
        // X axis maximum value.
        // Y axis minimum value.
        // Y axis maximum value.
        this.pWidth = x2 - x1;
        this.pHeight = (y2 - y1) * scale;
        this.scaleFact = scale;
    }

    /* Set plot position (upper left corner). */
    public void setPos(int x, int y) {
        this.xPos = x;
        this.yPos = y;
    }

    /* Set plot title. */
    public void setTitle(String title) {
        this.pltTitle = title;
    }

    /* Set plot background color. */
    public void setBackground(int bckColor) {
        this.backColor = bckColor;
    }

    /* Draw plot canvas. */
    /* Draw plot canvas. */
    public void drawCanvas() {
        p3d.strokeWeight(2);
        p3d.stroke(0, 0, 0);
        p3d.textAlign(PConstants.CENTER, PConstants.CENTER);
        // Title text size (pixels).
        int titleSize = 16;
        p3d.textSize(titleSize);
        // Draw title tile.
        p3d.fill(this.titleBackground);
        p3d.rect(this.xPos, this.yPos-160f, this.pWidth + 2f, TITLE_HEIGHT);

        // Draw plot tile.
        p3d.fill(this.backColor);
        p3d.rect(this.xPos, this.yPos + TITLE_HEIGHT -92f, this.pWidth + 2f, this.pHeight/2f + 2);

        p3d.fill(this.titleColor);
        p3d.text(this.pltTitle,  this.pWidth/2f, Plot2D.TITLE_HEIGHT /2f);
    }
    /* Draw XY grid. */
    public void drawGrid() {
        p3d.strokeWeight(1);
        p3d.stroke(this.gridColor);
        // Draw vertical lines.
        // Spacing between grid lines (pixels).
        int gridSpacing = 25;
        int xOff = gridSpacing;
        while (xOff < this.pWidth) {
            p3d.line(  10f+xOff,  TITLE_HEIGHT, 10f+xOff, (float)this.yPos+ TITLE_HEIGHT);
            xOff += gridSpacing;
        }
        // Draw horizontal lines.
        int yOff = TITLE_HEIGHT + gridSpacing;
        while ((yOff - TITLE_HEIGHT) < this.pHeight/2) {
            p3d.line(10f,  yOff, 10f+ this.pWidth,  yOff);
            yOff += gridSpacing;
        }
    }

    /* Add a new line to the plot. */
    public void addLine(float[] points, int nPoints, int lineColor) {
        p3d.noFill();
        p3d.strokeWeight(2);
        p3d.stroke(lineColor);
        p3d.beginShape();
        for (int i = 0; i < nPoints; i++) {
            this.p3d.vertex((float)this.xPos + i, Plot2D.TITLE_HEIGHT + (this.pHeight - (this.scaleFact * points[i])));
        }
        p3d.endShape();
    }

}