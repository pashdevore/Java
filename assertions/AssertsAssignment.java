import java.lang.Exception;

/**
 * @author  Pash DeVore <pashdevore@gmail.com>
 * @version 1.0
 * @since   2013-09-04
 */

public class AssertsAssignment {
	static String checkLength;
	
	static {
		checkLength = "Hello world?";
	}
	
	/**
	 * The main method to test the validateStringWithAnIfCondition and
	 * validateStringWhileEmployingAnAssert methods. 
	 *
	 * @param args the command-line arguments passed to the main method at run time
	 */
	
	public static void main(String[] args){
		validateStringWhileEmployingAnAssert(checkLength);
		try{
			validateStringWithAnIfCondition(checkLength);
		}catch(StringSizeException sse){
			sse.printStackTrace(System.err);
		}
		finally{}
	}
	
	/**
	 * Returns a boolean value indicating whether or not a string
	 * is of the required length. 
	 *
	 * @throws StringSizeException if an invalid string length exception occurs 
	 * @param  arg                 the string value tested for valid length
	 * @return                     the boolean indicating whether the string is of a valid 
	 *                             length
	 */
	public static boolean validateStringWithAnIfCondition(String arg) throws StringSizeException{
		if(arg.length() < 1 || arg.length() > 10){
			throw new StringSizeException("Your string length: " + arg.length() + "\nRequired length of 1-10 characters");
		}
		else{
			return true;
		}
	}
	
	/**
	 * Returns a boolean value indicating whether or not a string 
	 * is of the required length. Will always return 'true' unless assertions
	 * are enabled during compilation. When enabled, false values throw the string 
	 * parameter's length value to the AssertionError constructor where it is used in a 
	 * resultant printed message.
	 *
	 * @param  arg the string value tested for valid length
	 * @return     the boolean indicating whether the string is of a valid length
	 */
	public static boolean validateStringWhileEmployingAnAssert(String arg){
		assert(arg.length() > 0 && arg.length() < 11): "String length: " + arg.length();
		return true;
	}
}

