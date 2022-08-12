import com.mathworks.toolbox.javabuilder.*;
import MatlabUtility.MatlabUtility;

/**
 *
 * Sample driver code that is integrated with a compiled MATLAB function
 * generated by MATLAB Compiler SDK.
 *
 * Refer to the MATLAB Compiler SDK documentation for more
 * information.
 *
 * @see com.mathworks.toolbox.javabuilder.MWArray
 *
 */
public class path_generatorSample1 {

	private static MatlabUtility matlabutilityInstance;

	private static void setup() throws MWException {
		matlabutilityInstance = new MatlabUtility();
	}

	/**
	 * Sample code for {@link MatlabUtility#path_generator(int, Object...)}.
	 */
	public static void path_generatorExample() {
		MWArray startIdIn = null;
		MWArray shapeposIn = null;
		MWArray methodIn = null;
		MWNumericArray pOut = null;
		MWNumericArray dpOut = null;
		MWNumericArray ddpOut = null;
		Object[] results = null;
		try {
			double startIdInData = 19.0;
			startIdIn = new MWNumericArray(startIdInData, MWClassID.DOUBLE);
			double[] shapeposInData = {700.0, 600.0};
			shapeposIn = new MWNumericArray(shapeposInData, MWClassID.DOUBLE);
			String methodInData = "paraboloic";
			methodIn = new MWCharArray(methodInData);
			results = matlabutilityInstance.path_generator(3, startIdIn, shapeposIn, methodIn);
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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Dispose of native resources
			MWArray.disposeArray(startIdIn);
			MWArray.disposeArray(shapeposIn);
			MWArray.disposeArray(methodIn);
			MWArray.disposeArray(results);
		}
	}

	public static void main(String[] args) {
		try {
			setup();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		try {
			path_generatorExample();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		} finally {
			// Dispose of native resources
			matlabutilityInstance.dispose();
		}
	}

}