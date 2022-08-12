package com.effibot.robind_manipolator.matlab;

import com.effibot.robind_manipolator.Utils;
import com.mathworks.engine.MatlabEngine;

import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class Matlab extends MatlabEngine {
    private static Matlab matlab = null;
    private static final Future<MatlabEngine> engine = MatlabEngine.startMatlabAsync();
    private static MatlabEngine eng;



    private static info info = null;
    private static path path = null;
    private static SysOut sysout = null;
    private static GeomProps geomprops = null;
    private static Ik ik = null;
    private static Utils util = new Utils();


    public Matlab(){
       /* Default Constructor */
    }

    public void mapgeneration(double[][] obslist, double[] dim) throws ExecutionException, InterruptedException {

            Object[] results = eng.feval(5,"mapGeneration",obslist,dim);
            double[] idlist =  (double[]) results[0];
            double[][] shapepos =(double[][]) results[1];

            byte[][][] mapimg=(byte[][][]) results[2];
            BufferedImage bw = util.createImage(mapimg);

            byte[][][][] mapwork=(byte[][][][]) results[3];
            BufferedImage[] mapwk =   new BufferedImage[mapwork.length];
            for(int i = 0; i<mapwork.length;i++){
                byte[][][] tmp = mapwork[i];
                mapwk[i] = util.createImage(tmp);
            }
            byte[][][] mapgraph=(byte[][][]) results[4];
            BufferedImage graph = util.createImage(mapgraph);
            setInfo(new info(idlist,shapepos,bw,mapwk,graph));


    }

    public void pathgeneration(int startid, double[] obspos, String method) throws ExecutionException, InterruptedException {

            Object[] results = eng.feval(4,"path_generator",startid,obspos,method);
            double[][] qr =  (double[][]) results[0];
            double[][] dqr =(double[][]) results[1];
            double[][] ddqr =(double[][]) results[2];
            byte[][][][] mapsim=(byte[][][][]) results[3];
            BufferedImage[] mapsimimg =   new BufferedImage[mapsim.length];
            for(int i = 0; i<mapsim.length;i++){
                byte[][][] tmp = mapsim[i];
                mapsimimg[i] = util.createImage(tmp);
            }
            setPath(new path(qr,dqr,ddqr,mapsimimg));


    }

    public void runsimulation(int m, int alpha) throws ExecutionException, InterruptedException {
            Object[] results = eng.feval(4,"runsimulation",m,alpha);
            double[][] qr =  (double[][]) results[0];
            double[][] dqr =(double[][]) results[1];
            double[][] ddqr =(double[][]) results[2];
            double[][] e =(double[][]) results[3];
//            byte[][][][] mapPID=(byte[][][][]) results[4];
//            BufferedImage[] mappid =   new BufferedImage[mapPID.length];
//            for(int i = 0; i<mapPID.length;i++){
//                byte[][][] tmp = mapPID[i];
//                mappid[i] = util.createImage(tmp);
//            }
//            setSysout(new sysout(qr,dqr,ddqr,e,mappid));
            setSysout(new SysOut(qr,dqr,ddqr,e));

            eng.close();


    }
    public  info getInfo() {
        return info;
    }

    public static void setInfo(info info) {
        Matlab.info = info;
    }

    public  path getPath() {
        return path;
    }

    public static void setPath(path path) {
        Matlab.path = path;
    }

    public SysOut getSysout() {
        return sysout;
    }

    public static void setSysout(SysOut sysout) {
        Matlab.sysout = sysout;
    }

    public static GeomProps getGeomprops() {
        return geomprops;
    }

    public static void setGeomprops(GeomProps geomprops) {
        Matlab.geomprops = geomprops;
    }

    public static Ik getIk() {
        return ik;
    }

    public static void setIk(Ik ik) {
        Matlab.ik = ik;
    }

    public static MatlabEngine getEngine(){
        if(engine == null){
           eng= Matlab.getEngine();
        }
        return eng;
    }
    public  static  synchronized Matlab getInstance()  {
        if (matlab ==null){
            matlab =new Matlab();
            try {
                eng = engine.get();
            } catch (InterruptedException|ExecutionException e) {
                Thread.currentThread().interrupt();
            }

        }
        return matlab;
    }

}
