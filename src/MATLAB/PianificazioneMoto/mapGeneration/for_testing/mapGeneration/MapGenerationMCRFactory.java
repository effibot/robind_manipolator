/*
 * MATLAB Compiler: 8.4 (R2022a)
 * Date: Mon Aug  8 11:25:44 2022
 * Arguments: 
 * "-B""macro_default""-W""java:mapGeneration,Map""-T""link:lib""-d""C:\\Users\\loren\\OneDrive\\Desktop\\robind_manipolator\\src\\MATLAB\\PianificazioneMoto\\mapGeneration\\for_testing""class{Map:C:\\Users\\loren\\OneDrive\\Desktop\\robind_manipolator\\src\\MATLAB\\PianificazioneMoto\\mapGeneration.m}""class{Path:C:\\Users\\loren\\OneDrive\\Desktop\\robind_manipolator\\src\\MATLAB\\PianificazioneMoto\\path_generator.m}""class{Simulate:C:\\Users\\loren\\OneDrive\\Desktop\\robind_manipolator\\src\\MATLAB\\PianificazioneMoto\\runsimulation.m}"
 */

package mapGeneration;

import com.mathworks.toolbox.javabuilder.*;
import com.mathworks.toolbox.javabuilder.internal.*;
import java.io.Serializable;
/**
 * <i>INTERNAL USE ONLY</i>
 */
public class MapGenerationMCRFactory implements Serializable 
{
    /** Component's uuid */
    private static final String sComponentId = "mapGeneratio_3af7ec76-2c5b-476f-ae3b-b94ee72f19cf";
    
    /** Component name */
    private static final String sComponentName = "mapGeneration";
    
   
    /** Pointer to default component options */
    private static final MWComponentOptions sDefaultComponentOptions = 
        new MWComponentOptions(
            MWCtfExtractLocation.EXTRACT_TO_CACHE, 
            new MWCtfClassLoaderSource(MapGenerationMCRFactory.class)
        );
    
    
    private MapGenerationMCRFactory()
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
            MapGenerationMCRFactory.class, 
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
