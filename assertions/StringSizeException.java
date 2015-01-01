import java.lang.*;

/**
 * @author  Pash DeVore <pashdevore@gmail.com>
 * @version 1.0
 * @since   2013-09-04
 */

public class StringSizeException extends Exception {
	
	/**
	 * The main method necessary for StringSizeException to compile.
	 *
	 * @param args the command-line arguments passed to the main method at run time
	 */
	public static void main(String[] args){}

	/**
	 * General constructor which calls the super class constructor.
	 */
	public StringSizeException(){
		super();
	}
	
	/**
	 * Constructor which includes a string argument for better describing why the 
	 * exception was thrown. The constructor calls upon the super class constructor
	 * for its implementation.
	 *
	 * @param message the string message to better describe why the exception was thrown 
	 */
	public StringSizeException(String message){
		super(message);
	}
	
	/**
	 * Constructor which includes a string and throwable argument to best inform the 
	 * caller of the reason for the thrown exception. The constructor calls upon the super
	 * class constructor for its implementation.
	 *
	 * @param message the string message to better describe why the exception was thrown
	 * @param cause   the throwable describing the reason for the throwing of the 
	 *                exception
	 */
	public StringSizeException(String message, Throwable cause){
		super(message,cause);
	}
	
	/**
	 * Constructor which includes a throwable argument for better describing why the
	 * exception was thrown. The constructor calls upon the super class constructor for
	 * its implementation.
	 *
	 * @param cause the throwable describing the reason for the throwing of the exception 
	 */
	public StringSizeException(Throwable cause){
		super(cause);
	}
}