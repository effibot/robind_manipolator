package com.effibot.robind_manipolator.Processing;

import processing.core.PGraphics;

public class Oscilloscope {
    private float time_step = (float) (1 / 6.0);
    private PGraphics pg;
    private PGraphics oscilloscope_pg;
    private ProcessingBase p3d;
    private int pgheight;
    private int pgwidth;
    private int nLines;
    private float endscreen = pgwidth / time_step;


    public Oscilloscope(ProcessingBase p3d,int pgwidth, int pgheight, int nLines){
        this.p3d=p3d;
        this.pg = this.p3d.createGraphics((int) pgwidth, (int) pgheight);
        this.oscilloscope_pg = this.p3d.createGraphics((int) pgwidth, (int) pgheight);
        init_oscilloscope(nLines);
        this.pgheight=pgheight;
        this.pgwidth=pgwidth;
    }

    void draw_oscilloscope(float[] oldQ,float[] q, float[] oldq, float[] qReal, float[][] rover )
    {

        float oldq1REF=oldQ[0];
        float oldq2REF=oldQ[1];
        float oldq3REF=oldQ[2];
        float oldq4REF=oldQ[3];
        float oldq5REF=oldQ[4];
        float oldq6REF=oldQ[5];

        float q1 = q[0];
        float q2 = q[1];
        float q3 = q[2];
        float q4 = q[3];
        float q5 = q[4];
        float q6 = q[5];

        float oldq1=oldq[0];
        float oldq2=oldq[1];
        float oldq3=oldq[2];
        float oldq4=oldq[3];
        float oldq5=oldq[4];
        float oldq6=oldq[5];

        float q1Real=qReal[0];
        float q2Real=qReal[1];
        float q3Real=qReal[2];
        float q4Real=qReal[3];
        float q5Real=qReal[4];
        float q6Real=qReal[5];

        float oldxDes= rover[0][0];
        float xDesUniciclo = rover[0][1];
        float oldx = rover[0][2];
        float xUniciclo = rover[0][3];

        float oldyDes= rover[1][0];
        float yDesUniciclo = rover[1][1];
        float oldy = rover[1][2];
        float yUniciclo = rover[1][3];

        oscilloscope_pg.beginDraw();

        oscilloscope_plot(oldq1REF, q1, oldq1, q1Real, 0, 1);
        oscilloscope_plot(oldq2REF, q2, oldq2, q2Real, 1, 1);
        oscilloscope_plot(oldq3REF, q3, oldq3, q3Real, 2, 1);
        oscilloscope_plot(oldq4REF, q4, oldq4, q4Real, 3, 1);
        oscilloscope_plot(oldq5REF, q5, oldq5, q5Real, 4, 1);
        oscilloscope_plot(oldq6REF, q6, oldq6, q6Real, 5, 1);
        oscilloscope_plot(oldxDes, xDesUniciclo, oldx, xUniciclo, 6, 0.01f);
        oscilloscope_plot(oldyDes, yDesUniciclo, oldy, yUniciclo, 7, 0.01f);

        if (this.p3d.frameCount % endscreen == 0)
            init_oscilloscope(nLines);

        oscilloscope_pg.endDraw();
        this.p3d.image(oscilloscope_pg, this.p3d.width-220, 210);

        oldq1REF = q1;
        oldq2REF = q2;
        oldq3REF = q3;

        oldq1 = q1Real;
        oldq2 = q2Real;
        oldq3 = q3Real;

        oldx = xUniciclo;
        oldy = yUniciclo;

        oldxDes = xDesUniciclo;
        oldyDes = yDesUniciclo;
    }

    void oscilloscope_plot(float oldREF, float actualREF, float old, float actual,
                           int i, float r_scale)
    {
        oldREF *= r_scale;
        actualREF *= r_scale;
        old *= r_scale;
        actual *= r_scale;

        oscilloscope_pg.stroke(126);
        oscilloscope_pg.line(this.pgwidth - time_step * ((this.p3d.frameCount - 1) % endscreen),
                pgheight/(nLines+1) * (i+1) - oldREF * 10,
                pgwidth - time_step * (this.p3d.frameCount % endscreen),
                pgheight/(nLines+1) * (i+1) - actualREF * 10);

        oscilloscope_pg.stroke(0);
        oscilloscope_pg.line(pgwidth - time_step * ((this.p3d.frameCount - 1) % endscreen),
                pgheight/(nLines+1) * (i+1) - old * 10,
                pgwidth - time_step * (this.p3d.frameCount % endscreen),
                pgheight/(nLines+1) * (i+1) - actual * 10);
    }
    void init_oscilloscope(int no_lines)
    {
        oscilloscope_pg.beginDraw();
        oscilloscope_pg.background(255, 255, 255);
        oscilloscope_pg.stroke(200);
        oscilloscope_pg.fill(0, 0, 0);
        for (int i = 0; i < no_lines; ++i) {
            oscilloscope_pg.line(0, pgheight/(no_lines+1) * (i+1),
                    pgwidth, pgheight/(no_lines+1) * (i+1));
            oscilloscope_pg.line(pgwidth/(no_lines+1) * (i+1), 0,
                    pgwidth/(no_lines+1) * (i+1), pgheight);
        }
        oscilloscope_pg.endDraw();
    }

}
