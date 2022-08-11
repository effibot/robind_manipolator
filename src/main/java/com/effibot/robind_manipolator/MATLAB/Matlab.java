package com.effibot.robind_manipolator.MATLAB;

import com.effibot.robind_manipolator.Utils;
import com.mathworks.engine.MatlabEngine;

import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class Matlab extends MatlabEngine {
    private static Matlab matlab = null;
    private static Future<MatlabEngine> engine = null;
    private static MatlabEngine eng=null;

    private static info info = null;
    private static path path = null;
    private static sysout sysout = null;
    private static geomprops geomprops = null;
    private static ik ik = null;
    private static Utils util = new Utils();
//    private Matlab() throws MWException {
//        matlabUtility = new MatlabUtility();
//
//    }
//
//
//
//    public void mapgeneration(double[][] obs, double[] dim) {
//        MWArray obsIn = null;
//        MWArray dimIn = null;
//        MWNumericArray gidOut = null;
//        MWNumericArray shapeposOut = null;
//        Object[] results = null;
//        try {
//            obsIn = new MWNumericArray(obs, MWClassID.DOUBLE);
//            dimIn = new MWNumericArray(dim, MWClassID.DOUBLE);
//            results = matlabUtility.mapGeneration(2, obsIn, dimIn);
//            if (results[0] instanceof MWNumericArray) {
//                gidOut = (MWNumericArray) results[0];
//            }
//            if (results[1] instanceof MWNumericArray) {
//                shapeposOut = (MWNumericArray) results[1];
//            }
//            System.out.println(gidOut);
//            System.out.println(shapeposOut);
//            Object shapemat = shapeposOut.toDoubleArray();
//            setInfo(new info( gidOut.getDoubleData(),(double[][]) shapemat));
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            // Dispose of native resources
//            disposeResources(new MWArray[]{obsIn, dimIn});
//            MWArray.disposeArray(results);
//        }
//    }
//
//    public void pathgeneration(double startId,double[] shapepos, String method){
//        MWArray startIdIn = null;
//        MWArray shapeposIn = null;
//        MWArray methodIn = null;
//        MWNumericArray pOut = null;
//        MWNumericArray dpOut = null;
//        MWNumericArray ddpOut = null;
//        Object[] results = null;
//        try {
//            startIdIn = new MWNumericArray(startId, MWClassID.DOUBLE);
//            shapeposIn = new MWNumericArray(shapepos, MWClassID.DOUBLE);
//            methodIn = new MWCharArray(method);
//            results = matlabUtility.path_generator(3, startIdIn, shapeposIn, methodIn);
//            if (results[0] instanceof MWNumericArray) {
//                pOut = (MWNumericArray) results[0];
//            }
//            if (results[1] instanceof MWNumericArray) {
//                dpOut = (MWNumericArray) results[1];
//            }
//            if (results[2] instanceof MWNumericArray) {
//                ddpOut = (MWNumericArray) results[2];
//            }
//            System.out.println(pOut);
//            System.out.println(dpOut);
//            System.out.println(ddpOut);
//            setPath(new path((double[][]) pOut.toDoubleArray(),(double[][]) dpOut.toDoubleArray(),(double[][]) ddpOut.toDoubleArray()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            // Dispose of native resources
//            disposeResources(new MWArray[]{startIdIn,shapeposIn,methodIn});
//            MWArray.disposeArray(results);
//        }
//    }
//
//    public void runsimulation(double M, double alpha) throws MWException {
//        MWArray MIn = null;
//        MWArray alphaIn = null;
//        MWNumericArray qrOut = null;
//        MWNumericArray dqrOut = null;
//        MWNumericArray ddqrOut = null;
//        MWNumericArray eOut = null;
//        Object[] results = null;
//        try {
//            MIn = new MWNumericArray(M, MWClassID.DOUBLE);
//            alphaIn = new MWNumericArray(alpha, MWClassID.DOUBLE);
//            results = matlabUtility.runsimulation(4, MIn, alphaIn);
//            if (results[0] instanceof MWNumericArray) {
//                qrOut = (MWNumericArray) results[0];
//            }
//            if (results[1] instanceof MWNumericArray) {
//                dqrOut = (MWNumericArray) results[1];
//            }
//            if (results[2] instanceof MWNumericArray) {
//                ddqrOut = (MWNumericArray) results[2];
//            }
//            if (results[3] instanceof MWNumericArray) {
//                eOut = (MWNumericArray) results[3];
//            }
//            System.out.println(qrOut);
//            System.out.println(dqrOut);
//            System.out.println(ddqrOut);
//            System.out.println(eOut);
//            setSysout(new sysout((double[]) qrOut.toDoubleArray(),(double[]) dqrOut.toDoubleArray(),
//                    (double[]) ddqrOut.toDoubleArray(),(double[]) eOut.toDoubleArray()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            // Dispose of native resources
//            disposeResources(new MWArray[]{MIn,alphaIn});
//            MWArray.disposeArray(results);
//        }
//    }
//
//    public void vision(String filename){
//        MWArray filenameIn = null;
//        MWNumericArray objAreaOut = null;
//        MWNumericArray objPerimOut = null;
//        MWNumericArray objShapeOut = null;
//        MWNumericArray angOut = null;
//        Object[] results = null;
//        try {
//            filenameIn = new MWCharArray(filename);
//            results = matlabUtility.visione(4, filenameIn);
//            if (results[0] instanceof MWNumericArray) {
//                objAreaOut = (MWNumericArray) results[0];
//            }
//            if (results[1] instanceof MWNumericArray) {
//                objPerimOut = (MWNumericArray) results[1];
//            }
//            if (results[2] instanceof MWNumericArray) {
//                objShapeOut = (MWNumericArray) results[2];
//            }
//            if (results[3] instanceof MWNumericArray) {
//                angOut = (MWNumericArray) results[3];
//            }
//            System.out.println(objAreaOut);
//            System.out.println(objPerimOut);
//            System.out.println(objShapeOut);
//            System.out.println(angOut);
//            setGeomprops(new geomprops(objAreaOut.getDouble(),objPerimOut.getDouble(),objShapeOut.getDouble(), angOut.getDouble()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            // Dispose of native resources
//            disposeResources(new MWArray[]{filenameIn});
//            MWArray.disposeArray(results);
//        }
//    }
//
//    public void inverse_kinematics(double xdes, double ydes,double zdes,double roll,double pitch,double yaw){
//        MWArray x = null;
//        MWArray y = null;
//        MWArray z = null;
//        MWArray r = null;
//        MWArray p = null;
//        MWArray yy = null;
//        MWNumericArray qikOut = null;
//        Object[] results = null;
//        try {
//            x = new MWNumericArray(xdes, MWClassID.DOUBLE);
//            y = new MWNumericArray(ydes, MWClassID.DOUBLE);
//            z = new MWNumericArray(zdes, MWClassID.DOUBLE);
//            r = new MWNumericArray(roll, MWClassID.DOUBLE);
//            p = new MWNumericArray(pitch, MWClassID.DOUBLE);
//            y = new MWNumericArray(yaw, MWClassID.DOUBLE);
//
//            results = matlabUtility.newtongrad(1,x,y,z,r,p,yy);
//            if (results[0] instanceof MWNumericArray) {
//                qikOut = (MWNumericArray) results[0];
//            }
//            System.out.println(qikOut);
//            setIk( new ik(qikOut.getDoubleData()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            // Dispose of native resources
//            disposeResources(new MWArray[]{x,y,z,r,p,yy});
//            MWArray.disposeArray(results);
//        }
//    }
//
//    public static void disposeResources(MWArray[] mw){
//        int sz = mw.length;
//        for(int i=0;i<sz;i++) {
//            MWArray.disposeArray(mw[i]);
//        }
//    }
//
//    public void disposeMatlab(){
//        matlabUtility.dispose();
//    }
//
//


    public Matlab(){
        this.engine =MatlabEngine.startMatlabAsync();
        try {
            this.eng = engine.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private void setEngPath(){

        String rootpath = null;
        try {
            rootpath = eng.feval("genpath","./src");
            String checkpath = eng.feval("addpath",rootpath);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void mapgeneration(double[][] obslist, double[] dim) {
        try {
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
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

    public void pathgeneration(int startid, double[] obspos, String method) {
        try {
            this.getEngine();
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

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void runsimulation(int M, int alpha) {
        try {
            this.getEngine();
            Object[] results = eng.feval(4,"runsimulation",M,alpha);
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
            setSysout(new sysout(qr,dqr,ddqr,e));

            eng.close();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
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

    public  sysout getSysout() {
        return sysout;
    }

    public static void setSysout(sysout sysout) {
        Matlab.sysout = sysout;
    }

    public static geomprops getGeomprops() {
        return geomprops;
    }

    public static void setGeomprops(geomprops geomprops) {
        Matlab.geomprops = geomprops;
    }

    public static ik getIk() {
        return ik;
    }

    public static void setIk(ik ik) {
        Matlab.ik = ik;
    }

    public MatlabEngine getEngine(){
        if(engine == null){
           eng= matlab.getEngine();
        }
        return eng;
    }
    public  static  synchronized Matlab getInstance()  {
        if (matlab ==null){
            matlab =new Matlab();
        }
        return matlab;
    }

}
