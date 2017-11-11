import java.util.*;
import java.lang.*;

/**
 * @author  Pash DeVore
 *
 * @version 1.0
 * @since   2013-09-08
 */

public class RecursionAssignment {
	static Class userClass;
	
	/**
	 * Recursive function that prints out the class hierarchy of the user's input. 
	 * The hierarchy is printed from the root class 'java.lang.Object' to the user input.
	 *
	 * @param c class Class object determined from user input
	 */
	public static void classStructureRecursiveF(Class c) {
		if(c.getSuperclass() == null) {
			System.out.println(c.getName());
			return;
		}
		else {
			classStructureRecursiveF(c.getSuperclass());
			System.out.println(c.getName());
		}
	}
	
	/**
	 * Recursive function that prints out the class hierarchy of the user's input.
	 * The hierarchy is printed from the user inputed class to the root class
	 * 'java.lang.object'.
	 *
	 * @param c class Class object determined from user input
	 */
	public static void classStructureRecursiveR(Class c) {
		if(c.getSuperclass() == null) {
			System.out.println(c.getName());
			return;
		} else {
			System.out.println(c.getName());
			classStructureRecursiveR(c.getSuperclass());
		}
	}
	
	/**
	 * Iterative function that prints out the class hierarchy of the user's input. 
	 * The hierarchy is printed from the root class 'java.lang.Object' to the user input.
	 *
	 * @param c class Class object determined from user input
	 */
	public static void classStructureIterativeF(Class c) {
		String concatClass = "";
		while(c.getSuperclass() != null) {
			concatClass = c.getName().toString() + "\n" + concatClass;
			c = c.getSuperclass();
		}
		concatClass = c.getName().toString() + "\n" + concatClass;
		System.out.print(concatClass);
	}
	
	/**
	 * Iterative function that prints out the class hierarchy of the user's input.
	 * The hierarchy is printed from the user inputed class to the root class
	 * 'java.lang.object'.
	 *
	 * @param c class Class object determined from user input
	 */
	public static void classStructureIterativeR(Class c) {
		while(c.getSuperclass() != null) {
			System.out.println(c.getName());
			c = c.getSuperclass();
		}
		System.out.println(c.getName());
	}
	
	/**
	 * Main method which gets user input for the class to be traversed. Uses the 
	 * forName method which requires the use of a try/catch statement. Prints out 
	 * the traversed classes in both the forward and reverse directions using both
	 * recursive and iterative methods.
	 *
	 * @param args the command-line arguments passed to the compiler at run time 
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("What class' hierarchy would you like to see?\nClass: ");
		
		String userInput = in.nextLine();
		
		try{
			userClass = Class.forName(userInput);
		}
		catch(ClassNotFoundException cnfe){
			System.out.println("This is not a class...");
		}
		System.out.println("");
		classStructureRecursiveF(userClass);
		System.out.println("");
		classStructureRecursiveR(userClass);
		System.out.println("");
		classStructureIterativeF(userClass);
		System.out.println("");
		classStructureIterativeR(userClass);
	}
}
