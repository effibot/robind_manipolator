/*
 * MATLAB Compiler: 8.4 (R2022a)
 * Date: Mon Aug  8 11:25:44 2022
 * Arguments: 
 * "-B""macro_default""-W""java:mapGeneration,Map""-T""link:lib""-d""C:\\Users\\loren\\OneDrive\\Desktop\\robind_manipolator\\src\\MATLAB\\PianificazioneMoto\\mapGeneration\\for_testing""class{Map:C:\\Users\\loren\\OneDrive\\Desktop\\robind_manipolator\\src\\MATLAB\\PianificazioneMoto\\mapGeneration.m}""class{Path:C:\\Users\\loren\\OneDrive\\Desktop\\robind_manipolator\\src\\MATLAB\\PianificazioneMoto\\path_generator.m}""class{Simulate:C:\\Users\\loren\\OneDrive\\Desktop\\robind_manipolator\\src\\MATLAB\\PianificazioneMoto\\runsimulation.m}"
 */

package mapGeneration;

import com.mathworks.toolbox.javabuilder.*;
import com.mathworks.toolbox.javabuilder.internal.*;
import java.util.*;
import java.io.Serializable;

/**
 * The <code>Path</code> class provides a Java interface to MATLAB functions. 
 * The interface is compiled from the following files:
 * <pre>
 *  C:\\Users\\loren\\OneDrive\\Desktop\\robind_manipolator\\src\\MATLAB\\PianificazioneMoto\\path_generator.m
 * </pre>
 * The {@link #dispose} method <b>must</b> be called on a <code>Path</code> instance when 
 * it is no longer needed to ensure that native resources allocated by this class are 
 * properly freed.
 * @version 0.0
 */
public class Path extends MWComponentInstance<Path> implements Serializable
{
    /**
     * Tracks all instances of this class to ensure their dispose method is
     * called on shutdown.
     */
    private static final Set<Disposable> sInstances = new HashSet<Disposable>();

    /**
     * Maintains information used in calling the <code>path_generator</code> MATLAB 
     *function.
     */
    private static final MWFunctionSignature sPath_generatorSignature =
        new MWFunctionSignature(/* max outputs = */ 3,
                                /* has varargout = */ false,
                                /* function name = */ "path_generator",
                                /* max inputs = */ 3,
                                /* has varargin = */ false);

    /**
     * Shared initialization implementation - private
     * @throws MWException An error has occurred during the function call.
     */
    private Path (final MWMCR mcr) throws MWException
    {
        super(mcr);
        // add this to sInstances
        synchronized(Path.class) {
            sInstances.add(this);
        }
    }

    /**
     * Constructs a new instance of the <code>Path</code> class.
     * @throws MWException An error has occurred during the function call.
     */
    public Path() throws MWException
    {
        this(MapGenerationMCRFactory.newInstance());
    }
    
    private static MWComponentOptions getPathToComponentOptions(String path)
    {
        MWComponentOptions options = new MWComponentOptions(new MWCtfExtractLocation(path),
                                                            new MWCtfDirectorySource(path));
        return options;
    }
    
    /**
     * @deprecated Please use the constructor {@link #Path(MWComponentOptions componentOptions)}.
     * The <code>com.mathworks.toolbox.javabuilder.MWComponentOptions</code> class provides an API to set the
     * path to the component.
     * @param pathToComponent Path to component directory.
     * @throws MWException An error has occurred during the function call.
     */
    @Deprecated
    public Path(String pathToComponent) throws MWException
    {
        this(MapGenerationMCRFactory.newInstance(getPathToComponentOptions(pathToComponent)));
    }
    
    /**
     * Constructs a new instance of the <code>Path</code> class. Use this constructor to 
     * specify the options required to instantiate this component.  The options will be 
     * specific to the instance of this component being created.
     * @param componentOptions Options specific to the component.
     * @throws MWException An error has occurred during the function call.
     */
    public Path(MWComponentOptions componentOptions) throws MWException
    {
        this(MapGenerationMCRFactory.newInstance(componentOptions));
    }
    
    /** Frees native resources associated with this object */
    public void dispose()
    {
        try {
            super.dispose();
        } finally {
            synchronized(Path.class) {
                sInstances.remove(this);
            }
        }
    }
    
    /**
     * Calls dispose method for each outstanding instance of this class.
     */
    public static void disposeAllInstances()
    {
        synchronized(Path.class) {
            for (Disposable i : sInstances) i.dispose();
            sInstances.clear();
        }
    }

    /**
     * Provides the interface for calling the <code>path_generator</code> MATLAB function 
     * where the first argument, an instance of List, receives the output of the MATLAB function and
     * the second argument, also an instance of List, provides the input to the MATLAB function.
     * @param lhs List in which to return outputs. Number of outputs (nargout) is
     * determined by allocated size of this List. Outputs are returned as
     * sub-classes of <code>com.mathworks.toolbox.javabuilder.MWArray</code>.
     * Each output array should be freed by calling its <code>dispose()</code>
     * method.
     *
     * @param rhs List containing inputs. Number of inputs (nargin) is determined
     * by the allocated size of this List. Input arguments may be passed as
     * sub-classes of <code>com.mathworks.toolbox.javabuilder.MWArray</code>, or
     * as arrays of any supported Java type. Arguments passed as Java types are
     * converted to MATLAB arrays according to default conversion rules.
     * @throws MWException An error has occurred during the function call.
     */
    public void path_generator(List lhs, List rhs) throws MWException
    {
        fMCR.invoke(lhs, rhs, sPath_generatorSignature);
    }

    /**
     * Provides the interface for calling the <code>path_generator</code> MATLAB function 
     * where the first argument, an Object array, receives the output of the MATLAB function and
     * the second argument, also an Object array, provides the input to the MATLAB function.
     * @param lhs array in which to return outputs. Number of outputs (nargout)
     * is determined by allocated size of this array. Outputs are returned as
     * sub-classes of <code>com.mathworks.toolbox.javabuilder.MWArray</code>.
     * Each output array should be freed by calling its <code>dispose()</code>
     * method.
     *
     * @param rhs array containing inputs. Number of inputs (nargin) is
     * determined by the allocated size of this array. Input arguments may be
     * passed as sub-classes of
     * <code>com.mathworks.toolbox.javabuilder.MWArray</code>, or as arrays of
     * any supported Java type. Arguments passed as Java types are converted to
     * MATLAB arrays according to default conversion rules.
     * @throws MWException An error has occurred during the function call.
     */
    public void path_generator(Object[] lhs, Object[] rhs) throws MWException
    {
        fMCR.invoke(Arrays.asList(lhs), Arrays.asList(rhs), sPath_generatorSignature);
    }

    /**
     * Provides the standard interface for calling the <code>path_generator</code> MATLAB function with 
     * 3 comma-separated input arguments.
     * Input arguments may be passed as sub-classes of
     * <code>com.mathworks.toolbox.javabuilder.MWArray</code>, or as arrays of
     * any supported Java type. Arguments passed as Java types are converted to
     * MATLAB arrays according to default conversion rules.
     *
     * @param nargout Number of outputs to return.
     * @param rhs The inputs to the MATLAB function.
     * @return Array of length nargout containing the function outputs. Outputs
     * are returned as sub-classes of
     * <code>com.mathworks.toolbox.javabuilder.MWArray</code>. Each output array
     * should be freed by calling its <code>dispose()</code> method.
     * @throws MWException An error has occurred during the function call.
     */
    public Object[] path_generator(int nargout, Object... rhs) throws MWException
    {
        Object[] lhs = new Object[nargout];
        fMCR.invoke(Arrays.asList(lhs), 
                    MWMCR.getRhsCompat(rhs, sPath_generatorSignature), 
                    sPath_generatorSignature);
        return lhs;
    }
}
