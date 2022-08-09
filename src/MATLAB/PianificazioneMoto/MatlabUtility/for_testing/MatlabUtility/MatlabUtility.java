/*
 * MATLAB Compiler: 8.4 (R2022a)
 * Date: Tue Aug  9 12:18:36 2022
 * Arguments: 
 * "-B""macro_default""-W""java:MatlabUtility,MatlabUtility""-T""link:lib""-d""C:\\Users\\loren\\OneDrive\\Desktop\\robind_manipolator\\src\\MATLAB\\PianificazioneMoto\\MatlabUtility\\for_testing""class{MatlabUtility:C:\\Users\\loren\\OneDrive\\Desktop\\robind_manipolator\\src\\MATLAB\\PianificazioneMoto\\mapGeneration.m,C:\\Users\\loren\\OneDrive\\Desktop\\robind_manipolator\\src\\MATLAB\\IK\\newtongrad.m,C:\\Users\\loren\\OneDrive\\Desktop\\robind_manipolator\\src\\MATLAB\\PianificazioneMoto\\path_generator.m,C:\\Users\\loren\\OneDrive\\Desktop\\robind_manipolator\\src\\MATLAB\\PianificazioneMoto\\runsimulation.m,C:\\Users\\loren\\OneDrive\\Desktop\\robind_manipolator\\src\\MATLAB\\Visione\\visione.m}"
 */

package MatlabUtility;

import com.mathworks.toolbox.javabuilder.*;
import com.mathworks.toolbox.javabuilder.internal.*;
import java.util.*;
import java.io.Serializable;

/**
 * The <code>MatlabUtility</code> class provides a Java interface to MATLAB functions. 
 * The interface is compiled from the following files:
 * <pre>
 *  C:\\Users\\loren\\OneDrive\\Desktop\\robind_manipolator\\src\\MATLAB\\PianificazioneMoto\\mapGeneration.m
 *  C:\\Users\\loren\\OneDrive\\Desktop\\robind_manipolator\\src\\MATLAB\\IK\\newtongrad.m
 *  C:\\Users\\loren\\OneDrive\\Desktop\\robind_manipolator\\src\\MATLAB\\PianificazioneMoto\\path_generator.m
 *  C:\\Users\\loren\\OneDrive\\Desktop\\robind_manipolator\\src\\MATLAB\\PianificazioneMoto\\runsimulation.m
 *  C:\\Users\\loren\\OneDrive\\Desktop\\robind_manipolator\\src\\MATLAB\\Visione\\visione.m
 * </pre>
 * The {@link #dispose} method <b>must</b> be called on a <code>MatlabUtility</code> 
 * instance when it is no longer needed to ensure that native resources allocated by this 
 * class are properly freed.
 * @version 0.0
 */
public class MatlabUtility extends MWComponentInstance<MatlabUtility> implements Serializable
{
    /**
     * Tracks all instances of this class to ensure their dispose method is
     * called on shutdown.
     */
    private static final Set<Disposable> sInstances = new HashSet<Disposable>();

    /**
     * Maintains information used in calling the <code>mapGeneration</code> MATLAB 
     *function.
     */
    private static final MWFunctionSignature sMapGenerationSignature =
        new MWFunctionSignature(/* max outputs = */ 2,
                                /* has varargout = */ false,
                                /* function name = */ "mapGeneration",
                                /* max inputs = */ 2,
                                /* has varargin = */ false);
    /**
     * Maintains information used in calling the <code>newtongrad</code> MATLAB function.
     */
    private static final MWFunctionSignature sNewtongradSignature =
        new MWFunctionSignature(/* max outputs = */ 1,
                                /* has varargout = */ false,
                                /* function name = */ "newtongrad",
                                /* max inputs = */ 6,
                                /* has varargin = */ false);
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
     * Maintains information used in calling the <code>runsimulation</code> MATLAB 
     *function.
     */
    private static final MWFunctionSignature sRunsimulationSignature =
        new MWFunctionSignature(/* max outputs = */ 4,
                                /* has varargout = */ false,
                                /* function name = */ "runsimulation",
                                /* max inputs = */ 2,
                                /* has varargin = */ false);
    /**
     * Maintains information used in calling the <code>visione</code> MATLAB function.
     */
    private static final MWFunctionSignature sVisioneSignature =
        new MWFunctionSignature(/* max outputs = */ 4,
                                /* has varargout = */ false,
                                /* function name = */ "visione",
                                /* max inputs = */ 1,
                                /* has varargin = */ false);

    /**
     * Shared initialization implementation - private
     * @throws MWException An error has occurred during the function call.
     */
    private MatlabUtility (final MWMCR mcr) throws MWException
    {
        super(mcr);
        // add this to sInstances
        synchronized(MatlabUtility.class) {
            sInstances.add(this);
        }
    }

    /**
     * Constructs a new instance of the <code>MatlabUtility</code> class.
     * @throws MWException An error has occurred during the function call.
     */
    public MatlabUtility() throws MWException
    {
        this(MatlabUtilityMCRFactory.newInstance());
    }
    
    private static MWComponentOptions getPathToComponentOptions(String path)
    {
        MWComponentOptions options = new MWComponentOptions(new MWCtfExtractLocation(path),
                                                            new MWCtfDirectorySource(path));
        return options;
    }
    
    /**
     * @deprecated Please use the constructor {@link #MatlabUtility(MWComponentOptions componentOptions)}.
     * The <code>com.mathworks.toolbox.javabuilder.MWComponentOptions</code> class provides an API to set the
     * path to the component.
     * @param pathToComponent Path to component directory.
     * @throws MWException An error has occurred during the function call.
     */
    @Deprecated
    public MatlabUtility(String pathToComponent) throws MWException
    {
        this(MatlabUtilityMCRFactory.newInstance(getPathToComponentOptions(pathToComponent)));
    }
    
    /**
     * Constructs a new instance of the <code>MatlabUtility</code> class. Use this 
     * constructor to specify the options required to instantiate this component.  The 
     * options will be specific to the instance of this component being created.
     * @param componentOptions Options specific to the component.
     * @throws MWException An error has occurred during the function call.
     */
    public MatlabUtility(MWComponentOptions componentOptions) throws MWException
    {
        this(MatlabUtilityMCRFactory.newInstance(componentOptions));
    }
    
    /** Frees native resources associated with this object */
    public void dispose()
    {
        try {
            super.dispose();
        } finally {
            synchronized(MatlabUtility.class) {
                sInstances.remove(this);
            }
        }
    }
    
    /**
     * Calls dispose method for each outstanding instance of this class.
     */
    public static void disposeAllInstances()
    {
        synchronized(MatlabUtility.class) {
            for (Disposable i : sInstances) i.dispose();
            sInstances.clear();
        }
    }

    /**
     * Provides the interface for calling the <code>mapGeneration</code> MATLAB function 
     * where the first argument, an instance of List, receives the output of the MATLAB function and
     * the second argument, also an instance of List, provides the input to the MATLAB function.
     * <p>
     * Description as provided by the author of the MATLAB function:
     * </p>
     * <pre>
     * {@literal
	 * % addpath(genpath('.\\'));
	 * }
     * </pre>
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
    public void mapGeneration(List lhs, List rhs) throws MWException
    {
        fMCR.invoke(lhs, rhs, sMapGenerationSignature);
    }

    /**
     * Provides the interface for calling the <code>mapGeneration</code> MATLAB function 
     * where the first argument, an Object array, receives the output of the MATLAB function and
     * the second argument, also an Object array, provides the input to the MATLAB function.
     * <p>
     * Description as provided by the author of the MATLAB function:
     * </p>
     * <pre>
     * {@literal
	 * % addpath(genpath('.\\'));
	 * }
	 * </pre>
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
    public void mapGeneration(Object[] lhs, Object[] rhs) throws MWException
    {
        fMCR.invoke(Arrays.asList(lhs), Arrays.asList(rhs), sMapGenerationSignature);
    }

    /**
     * Provides the standard interface for calling the <code>mapGeneration</code> MATLAB function with 
     * 2 comma-separated input arguments.
     * Input arguments may be passed as sub-classes of
     * <code>com.mathworks.toolbox.javabuilder.MWArray</code>, or as arrays of
     * any supported Java type. Arguments passed as Java types are converted to
     * MATLAB arrays according to default conversion rules.
     *
     * <p>
     * Description as provided by the author of the MATLAB function:
     * </p>
     * <pre>
     * {@literal
	 * % addpath(genpath('.\\'));
	 * }
     * </pre>
     * @param nargout Number of outputs to return.
     * @param rhs The inputs to the MATLAB function.
     * @return Array of length nargout containing the function outputs. Outputs
     * are returned as sub-classes of
     * <code>com.mathworks.toolbox.javabuilder.MWArray</code>. Each output array
     * should be freed by calling its <code>dispose()</code> method.
     * @throws MWException An error has occurred during the function call.
     */
    public Object[] mapGeneration(int nargout, Object... rhs) throws MWException
    {
        Object[] lhs = new Object[nargout];
        fMCR.invoke(Arrays.asList(lhs), 
                    MWMCR.getRhsCompat(rhs, sMapGenerationSignature), 
                    sMapGenerationSignature);
        return lhs;
    }
    /**
     * Provides the interface for calling the <code>newtongrad</code> MATLAB function 
     * where the first argument, an instance of List, receives the output of the MATLAB function and
     * the second argument, also an instance of List, provides the input to the MATLAB function.
     * <p>
     * Description as provided by the author of the MATLAB function:
     * </p>
     * <pre>
     * {@literal
	 * %% Inverse Functions
	 * }
     * </pre>
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
    public void newtongrad(List lhs, List rhs) throws MWException
    {
        fMCR.invoke(lhs, rhs, sNewtongradSignature);
    }

    /**
     * Provides the interface for calling the <code>newtongrad</code> MATLAB function 
     * where the first argument, an Object array, receives the output of the MATLAB function and
     * the second argument, also an Object array, provides the input to the MATLAB function.
     * <p>
     * Description as provided by the author of the MATLAB function:
     * </p>
     * <pre>
     * {@literal
	 * %% Inverse Functions
	 * }
	 * </pre>
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
    public void newtongrad(Object[] lhs, Object[] rhs) throws MWException
    {
        fMCR.invoke(Arrays.asList(lhs), Arrays.asList(rhs), sNewtongradSignature);
    }

    /**
     * Provides the standard interface for calling the <code>newtongrad</code> MATLAB function with 
     * 6 comma-separated input arguments.
     * Input arguments may be passed as sub-classes of
     * <code>com.mathworks.toolbox.javabuilder.MWArray</code>, or as arrays of
     * any supported Java type. Arguments passed as Java types are converted to
     * MATLAB arrays according to default conversion rules.
     *
     * <p>
     * Description as provided by the author of the MATLAB function:
     * </p>
     * <pre>
     * {@literal
	 * %% Inverse Functions
	 * }
     * </pre>
     * @param nargout Number of outputs to return.
     * @param rhs The inputs to the MATLAB function.
     * @return Array of length nargout containing the function outputs. Outputs
     * are returned as sub-classes of
     * <code>com.mathworks.toolbox.javabuilder.MWArray</code>. Each output array
     * should be freed by calling its <code>dispose()</code> method.
     * @throws MWException An error has occurred during the function call.
     */
    public Object[] newtongrad(int nargout, Object... rhs) throws MWException
    {
        Object[] lhs = new Object[nargout];
        fMCR.invoke(Arrays.asList(lhs), 
                    MWMCR.getRhsCompat(rhs, sNewtongradSignature), 
                    sNewtongradSignature);
        return lhs;
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
    /**
     * Provides the interface for calling the <code>runsimulation</code> MATLAB function 
     * where the first argument, an instance of List, receives the output of the MATLAB function and
     * the second argument, also an instance of List, provides the input to the MATLAB function.
     * <p>
     * Description as provided by the author of the MATLAB function:
     * </p>
     * <pre>
     * {@literal
	 * % time = 0:step:(size(p,1)-1)/1000;
	 * }
     * </pre>
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
    public void runsimulation(List lhs, List rhs) throws MWException
    {
        fMCR.invoke(lhs, rhs, sRunsimulationSignature);
    }

    /**
     * Provides the interface for calling the <code>runsimulation</code> MATLAB function 
     * where the first argument, an Object array, receives the output of the MATLAB function and
     * the second argument, also an Object array, provides the input to the MATLAB function.
     * <p>
     * Description as provided by the author of the MATLAB function:
     * </p>
     * <pre>
     * {@literal
	 * % time = 0:step:(size(p,1)-1)/1000;
	 * }
	 * </pre>
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
    public void runsimulation(Object[] lhs, Object[] rhs) throws MWException
    {
        fMCR.invoke(Arrays.asList(lhs), Arrays.asList(rhs), sRunsimulationSignature);
    }

    /**
     * Provides the standard interface for calling the <code>runsimulation</code> MATLAB function with 
     * 2 comma-separated input arguments.
     * Input arguments may be passed as sub-classes of
     * <code>com.mathworks.toolbox.javabuilder.MWArray</code>, or as arrays of
     * any supported Java type. Arguments passed as Java types are converted to
     * MATLAB arrays according to default conversion rules.
     *
     * <p>
     * Description as provided by the author of the MATLAB function:
     * </p>
     * <pre>
     * {@literal
	 * % time = 0:step:(size(p,1)-1)/1000;
	 * }
     * </pre>
     * @param nargout Number of outputs to return.
     * @param rhs The inputs to the MATLAB function.
     * @return Array of length nargout containing the function outputs. Outputs
     * are returned as sub-classes of
     * <code>com.mathworks.toolbox.javabuilder.MWArray</code>. Each output array
     * should be freed by calling its <code>dispose()</code> method.
     * @throws MWException An error has occurred during the function call.
     */
    public Object[] runsimulation(int nargout, Object... rhs) throws MWException
    {
        Object[] lhs = new Object[nargout];
        fMCR.invoke(Arrays.asList(lhs), 
                    MWMCR.getRhsCompat(rhs, sRunsimulationSignature), 
                    sRunsimulationSignature);
        return lhs;
    }
    /**
     * Provides the interface for calling the <code>visione</code> MATLAB function 
     * where the first argument, an instance of List, receives the output of the MATLAB function and
     * the second argument, also an instance of List, provides the input to the MATLAB function.
     * <p>
     * Description as provided by the author of the MATLAB function:
     * </p>
     * <pre>
     * {@literal
	 * % addpath(genpath('./MATLAB/Visione/'))
     * % addpath('MATLAB/PianificazioneMoto/')
	 * }
     * </pre>
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
    public void visione(List lhs, List rhs) throws MWException
    {
        fMCR.invoke(lhs, rhs, sVisioneSignature);
    }

    /**
     * Provides the interface for calling the <code>visione</code> MATLAB function 
     * where the first argument, an Object array, receives the output of the MATLAB function and
     * the second argument, also an Object array, provides the input to the MATLAB function.
     * <p>
     * Description as provided by the author of the MATLAB function:
     * </p>
     * <pre>
     * {@literal
	 * % addpath(genpath('./MATLAB/Visione/'))
     * % addpath('MATLAB/PianificazioneMoto/')
	 * }
	 * </pre>
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
    public void visione(Object[] lhs, Object[] rhs) throws MWException
    {
        fMCR.invoke(Arrays.asList(lhs), Arrays.asList(rhs), sVisioneSignature);
    }

    /**
     * Provides the standard interface for calling the <code>visione</code> MATLAB function with 
     * 1 input argument.
     * Input arguments may be passed as sub-classes of
     * <code>com.mathworks.toolbox.javabuilder.MWArray</code>, or as arrays of
     * any supported Java type. Arguments passed as Java types are converted to
     * MATLAB arrays according to default conversion rules.
     *
     * <p>
     * Description as provided by the author of the MATLAB function:
     * </p>
     * <pre>
     * {@literal
	 * % addpath(genpath('./MATLAB/Visione/'))
     * % addpath('MATLAB/PianificazioneMoto/')
	 * }
     * </pre>
     * @param nargout Number of outputs to return.
     * @param rhs The inputs to the MATLAB function.
     * @return Array of length nargout containing the function outputs. Outputs
     * are returned as sub-classes of
     * <code>com.mathworks.toolbox.javabuilder.MWArray</code>. Each output array
     * should be freed by calling its <code>dispose()</code> method.
     * @throws MWException An error has occurred during the function call.
     */
    public Object[] visione(int nargout, Object... rhs) throws MWException
    {
        Object[] lhs = new Object[nargout];
        fMCR.invoke(Arrays.asList(lhs), 
                    MWMCR.getRhsCompat(rhs, sVisioneSignature), 
                    sVisioneSignature);
        return lhs;
    }
}
