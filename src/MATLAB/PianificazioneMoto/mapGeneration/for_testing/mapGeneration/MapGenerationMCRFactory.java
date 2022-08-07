/*
 * MATLAB Compiler: 8.4 (R2022a)
 * Date: Sun Aug  7 23:05:38 2022
 * Arguments: 
 * "-B""macro_default""-W""java:mapGeneration,Map""-T""link:lib""-d""C:\\Users\\loren\\OneDrive\\Desktop\\robind_manipolator\\src\\MATLAB\\PianificazioneMoto\\mapGeneration\\for_testing""class{Map:C:\\Users\\loren\\OneDrive\\Desktop\\robind_manipolator\\src\\MATLAB\\PianificazioneMoto\\mapGeneration.m}"
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
    private static final String sComponentId = "mapGeneratio_212f69e9-1445-43ad-9faa-210615b7ba74";
    
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
