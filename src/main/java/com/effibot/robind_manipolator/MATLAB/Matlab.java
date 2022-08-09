package com.effibot.robind_manipolator.MATLAB;

import MatlabUtility.MatlabUtility;
import com.mathworks.toolbox.javabuilder.*;

public class Matlab extends MatlabUtility {
    private static MatlabUtility matlabUtility = null;

    private Matlab() throws MWException {
        matlabUtility = new MatlabUtility();
    }

    private double[][] mapgeneration(double[][] obs, double[] dim) {
        MWArray obsIn = null;
        MWArray dimIn = null;
        MWNumericArray gidOut = null;
        MWNumericArray shapeposOut = null;
        Object[] results = null;
        double[][] id_shape= new double[2][];
        try {
            obsIn = new MWNumericArray(obs, MWClassID.DOUBLE);
            dimIn = new MWNumericArray(dim, MWClassID.DOUBLE);
            results = matlabUtility.mapGeneration(2, obsIn, dimIn);
            if (results[0] instanceof MWNumericArray) {
                gidOut = (MWNumericArray) results[0];
            }
            if (results[1] instanceof MWNumericArray) {
                shapeposOut = (MWNumericArray) results[1];
            }
            System.out.println(gidOut);
            System.out.println(shapeposOut);
            id_shape[0] = gidOut.getDoubleData();
            id_shape[1] = shapeposOut.getDoubleData();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Dispose of native resources
            disposeResources(new MWArray[]{obsIn, dimIn});
            MWArray.disposeArray(results);
        }
        return id_shape;
    }

    private path pathgeneration(double startId,double[] shapepos, String method){
        MWArray startIdIn = null;
        MWArray shapeposIn = null;
        MWArray methodIn = null;
        MWNumericArray pOut = null;
        MWNumericArray dpOut = null;
        MWNumericArray ddpOut = null;
        Object[] results = null;
        try {
            startIdIn = new MWNumericArray(startId, MWClassID.DOUBLE);
            shapeposIn = new MWNumericArray(shapepos, MWClassID.DOUBLE);
            methodIn = new MWCharArray(method);
            results = matlabUtility.path_generator(3, startIdIn, shapeposIn, methodIn);
            if (results[0] instanceof MWNumericArray) {
                pOut = (MWNumericArray) results[0];
            }
            if (results[1] instanceof MWNumericArray) {
                dpOut = (MWNumericArray) results[1];
            }
            if (results[2] instanceof MWNumericArray) {
                ddpOut = (MWNumericArray) results[2];
            }
            System.out.println(pOut);
            System.out.println(dpOut);
            System.out.println(ddpOut);
            path res = new path(pOut.getDoubleData(),dpOut.getDoubleData(),ddpOut.getDoubleData());
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Dispose of native resources
            disposeResources(new MWArray[]{startIdIn,shapeposIn,methodIn});
            MWArray.disposeArray(results);
        }
        return null;
    }

    private sysout runsimulation(double M, double alpha){
        MWArray MIn = null;
        MWArray alphaIn = null;
        MWNumericArray qrOut = null;
        MWNumericArray dqrOut = null;
        MWNumericArray ddqrOut = null;
        MWNumericArray eOut = null;
        Object[] results = null;
        try {
            MIn = new MWNumericArray(M, MWClassID.DOUBLE);
            alphaIn = new MWNumericArray(alpha, MWClassID.DOUBLE);
            results = matlabUtility.runsimulation(4, MIn, alphaIn);
            if (results[0] instanceof MWNumericArray) {
                qrOut = (MWNumericArray) results[0];
            }
            if (results[1] instanceof MWNumericArray) {
                dqrOut = (MWNumericArray) results[1];
            }
            if (results[2] instanceof MWNumericArray) {
                ddqrOut = (MWNumericArray) results[2];
            }
            if (results[3] instanceof MWNumericArray) {
                eOut = (MWNumericArray) results[3];
            }
            System.out.println(qrOut);
            System.out.println(dqrOut);
            System.out.println(ddqrOut);
            System.out.println(eOut);
            sysout res = new sysout(qrOut.getDoubleData(), dqrOut.getDoubleData(), ddqrOut.getDoubleData(), eOut.getDoubleData());
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Dispose of native resources
            disposeResources(new MWArray[]{MIn,alphaIn});
            MWArray.disposeArray(results);
        }
        return  null;
    }

    private geomprops vision(String filename){
    return  null;
    }


    private static void disposeResources(MWArray[] mw){
        int sz = mw.length;
        for(int i=0;i<sz;i++) {
            MWArray.disposeArray(mw[i]);
        }
    }

    public void disposeMatlab(){
        matlabUtility.dispose();
    }

    public  static  synchronized Matlab getInstance() throws MWException {
        if (matlabUtility==null){
            matlabUtility=new Matlab();
        }
        return (Matlab) matlabUtility;
    }
}
