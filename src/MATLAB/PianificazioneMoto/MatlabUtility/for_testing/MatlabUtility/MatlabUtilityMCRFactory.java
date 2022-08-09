/*
 * MATLAB Compiler: 8.4 (R2022a)
 * Date: Tue Aug  9 12:50:24 2022
 * Arguments: 
 * "-B""macro_default""-W""java:MatlabUtility,MatlabUtility""-T""link:lib""-d""C:\\Users\\loren\\OneDrive\\Desktop\\robind_manipolator\\src\\MATLAB\\PianificazioneMoto\\MatlabUtility\\for_testing""class{MatlabUtility:C:\\Users\\loren\\OneDrive\\Desktop\\robind_manipolator\\src\\MATLAB\\PianificazioneMoto\\mapGeneration.m,C:\\Users\\loren\\OneDrive\\Desktop\\robind_manipolator\\src\\MATLAB\\IK\\newtongrad.m,C:\\Users\\loren\\OneDrive\\Desktop\\robind_manipolator\\src\\MATLAB\\PianificazioneMoto\\path_generator.m,C:\\Users\\loren\\OneDrive\\Desktop\\robind_manipolator\\src\\MATLAB\\PianificazioneMoto\\runsimulation.m,C:\\Users\\loren\\OneDrive\\Desktop\\robind_manipolator\\src\\MATLAB\\Visione\\visione.m}"
 */

package MatlabUtility;

import com.mathworks.toolbox.javabuilder.*;
import com.mathworks.toolbox.javabuilder.internal.*;
import java.io.Serializable;
/**
 * <i>INTERNAL USE ONLY</i>
 */
public class MatlabUtilityMCRFactory implements Serializable 
{
    /** Component's uuid */
    private static final String sComponentId = "MatlabUtilit_d20a9775-b385-4ec7-8536-19641e67967b";
    
    /** Component name */
    private static final String sComponentName = "MatlabUtility";
    
   
    /** Pointer to default component options */
    private static final MWComponentOptions sDefaultComponentOptions = 
        new MWComponentOptions(
            MWCtfExtractLocation.EXTRACT_TO_CACHE, 
            new MWCtfClassLoaderSource(MatlabUtilityMCRFactory.class)
        );
    
    
    private MatlabUtilityMCRFactory()
    {
        // Never called.
    }
    
    public static MWMCR newInstance(MWComponentOptions componentOptions) throws MWException
    {
        if (null == componentOptions.getCtfSource()) {
            componentOptions = new MWComponentOptions(componentOptions);
            componentOptions.setCtfSource(sDefaultComponentOptions.getCtfSource());
        }
        return MWMCR.newInstance(
            componentOptions, 
            MatlabUtilityMCRFactory.class, 
            sComponentName, 
            sComponentId,
            new int[]{9,12,0}
        );
    }
    
    public static MWMCR newInstance() throws MWException
    {
        return newInstance(sDefaultComponentOptions);
    }
}
