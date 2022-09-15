package com.effibot.robind_manipolator.processing;


import java.util.ArrayList;

import static processing.core.PApplet.ceil;
import static processing.core.PConstants.PI;

class Plot2D {

    /* 2D plot class parameters. */
    private int titleBackground ;  // Title tile background color.
    private int titleHeight = 35;                         // Title tile height.
    private int titleColor ;            // Title text color.
    private int titleSize = 16;                           // Title text size (pixels).
    private int gridColor ;          // Grid lines color.
    private int gridSpacing = 25;                         // Spacing between grid lines (pixels).

    /* Plot attributes. */
    private int xMin;         // X axis minimum value.
    private int xMax;         // X axis maximum value.
    private int yMin;         // Y axis minimum value.
    private int yMax;         // Y axis maximum value.
    private int xPos;         // X plot window position.
    private int yPos;         // Y plot window position.
    private int pWidth;       // Plot width.
    private int pHeight;      // Plot height (without title tile).
    private String pltTitle;  // Plot title.
    private int backColor;  // Background color.
    private int scaleFact;    // Scale factor for variable to plot.
    private P3DMap p3d;

    public Plot2D(P3DMap p3DMap) {
        this.p3d = p3DMap;
        this.titleBackground = p3d.color(214, 182, 75);
        this.titleColor = p3d.color(0, 0, 0);
        this.gridColor = p3d.color(80, 80, 80);

    }

    public ArrayList<Plot2D> initializePlot(P3DMap p3DMap) {

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
        this.xMin = x1;
        this.xMax = x2;
        this.yMin = y1;
        this.yMax = y2;
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
        p3d.textAlign(p3d.CENTER, p3d.CENTER);
        p3d.textSize(this.titleSize);
        // Draw title tile.
        p3d.fill(this.titleBackground);
        p3d.rect(this.xPos, this.yPos-160, this.pWidth + 2, this.titleHeight);

        // Draw plot tile.
        p3d.fill(this.backColor);
        p3d.rect(this.xPos, this.yPos + this.titleHeight-92, this.pWidth + 2, this.pHeight/2 + 2);

        p3d.fill(this.titleColor);
        p3d.text(this.pltTitle,  this.pWidth/2,  this.titleHeight/2);
    }
    /* Draw XY grid. */
    public void drawGrid() {
        p3d.strokeWeight(1);
        p3d.stroke(this.gridColor);
        // Draw vertical lines.
        int xOff = this.gridSpacing;
        while (xOff < this.pWidth) {
            p3d.line(  10+xOff,  this.titleHeight , 10+xOff, this.yPos+this.titleHeight);
            xOff += this.gridSpacing;
        }
        // Draw horizontal lines.
        int yOff = this.titleHeight + this.gridSpacing;
        while ((yOff - this.titleHeight) < this.pHeight/2) {
            p3d.line(10,  yOff, 10+ this.pWidth,  yOff);
            yOff += this.gridSpacing;
        }
    }

    /* Add a new line to the plot. */
    public void addLine(float[] points, int nPoints, int lineColor) {
        p3d.noFill();
        p3d.strokeWeight(2);
        p3d.stroke(lineColor);
        p3d.beginShape();
        for (int i = 0; i < nPoints; i++) {
            this.p3d.vertex(this.xPos + i, this.titleHeight + (this.pHeight - (this.scaleFact * points[i])));
        }
        p3d.endShape();
    }

}