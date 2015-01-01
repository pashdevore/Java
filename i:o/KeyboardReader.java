import java.io.*;
import java.lang.*;
import java.util.*;

/**
 * KeyboardReader class for use in reading data from the keyboard and use in the
 * remainder of CSCI 131 assignments.
 *
 * @author Pash DeVore
 * @version 1.0
 * @since 2013-10-03
 */

public class KeyboardReader{

	/**
	 * BufferedReader used to read system input for use in class methods.
	 */
	private static BufferedReader in = null;

	/**
	 * Main method for testing of the KeyboardReader class.
	 *
	 * @param args not used
	 */
	public static void main(String[] args){
		System.out.println("Ready for data?");
		System.out.println(isDataAvailable());
		
		System.out.print("Please enter a String: ");
		System.out.println(readLine());

		System.out.print("Please enter an integer: ");
		System.out.println(readInt());

		System.out.print("Please enter a boolean: ");
		System.out.println(readBoolean());

		System.out.print("Please enter a byte: ");
		System.out.println(readByte());

		System.out.print("Please enter a character: ");
		System.out.println(readChar());

		System.out.print("Please enter a double: ");
		System.out.println(readDouble());

		System.out.print("Please enter a float: ");
		System.out.println(readFloat());

		System.out.print("Please enter a long: ");
		System.out.println(readLong());

		System.out.print("Please enter a short: ");
		System.out.println(readShort());
	}
	
	/**
	 * Determines whether data from the keyboard is waiting to be read.
	 *
	 * @return boolean 
	 */
	public static boolean isDataAvailable(){
		if(System.console() != null)return true;
		return false;
	}

	/**
	 * Reads a line of text from the keyboard. Re-prompts for data on invalid entry.
	 *
	 * @return String
	 */
	public static java.lang.String readLine(){
		do{
			try{
				in = new BufferedReader(new InputStreamReader(System.in));
				return in.readLine();
			}catch(IOException e){}
		}while(true);
	}

	/**
	 * Reads an integer value from the keyboard. Re-prompts for data on invalid entry and
	 * notifies user of accepted values.
	 *
	 * @return int
	 */
	public static int readInt(){
		do{
			try{
				in = new BufferedReader(new InputStreamReader(System.in));
				return Integer.parseInt(in.readLine());
			}catch(IOException e){}
			catch(NumberFormatException nfe){
				System.out.println("Accepted values: " + Integer.MIN_VALUE + " to " + Integer.MAX_VALUE);		
			}
		}while(true);
	}

	/**
	 * Reads a boolean from the keyboard. Re-prompts for data on invalid entry and
	 * notifies user of accepted values.
	 *
	 * @return boolean
	 */
	public static boolean readBoolean(){
		do{
			try{
				in = new BufferedReader(new InputStreamReader(System.in));
				return Boolean.parseBoolean(in.readLine());
			}catch(IOException e){
				System.out.println("Accepted values: TRUE or FALSE");
			}
		}while(true);
	}

	/**
	 * Reads a byte from the keyboard. Re-prompts for data on invalid entry and
	 * notifies user of accepted values.
	 *
	 * @return byte
	 */
	public static byte readByte(){
		do{
			try{
				in = new BufferedReader(new InputStreamReader(System.in));
				return Byte.parseByte(in.readLine());
			}catch(IOException e){
				System.out.println("Accepted values: " + Byte.MIN_VALUE + " to " + Byte.MAX_VALUE);
			}catch(NumberFormatException nfe){
				System.out.println("Accepted values: " + Byte.MIN_VALUE + " to " + Byte.MAX_VALUE);
			}
		}while(true);
	}

	/**
	 * Reads a char from the keyboard. Re-prompts for data on invalid entry.
	 *
	 * @return char
	 */
	public static char readChar(){
		do{
			try{
				in = new BufferedReader(new InputStreamReader(System.in));
				return in.readLine().charAt(0);
			}catch(IOException e){}
			catch(IndexOutOfBoundsException ioobe){
				System.out.print("Please enter a character: ");
			}
		}while(true);
	}
	
	/**
	 * Reads a double from the keyboard. Re-prompts for data on invalid entry and
	 * notifies user of accepted values.
	 *
	 * @return double
	 */
	public static double readDouble(){
		do{
			try{
				in = new BufferedReader(new InputStreamReader(System.in));
				return Double.parseDouble(in.readLine());
			}catch(IOException e){
				System.out.println("Accepted values: " + Double.MIN_VALUE + " to " + Double.MAX_VALUE);
			}catch(NullPointerException npe){
				System.out.println("Accepted values: " + Double.MIN_VALUE + " to " + Double.MAX_VALUE);
			}catch(NumberFormatException nfe){
				System.out.println("Accepted values: " + Double.MIN_VALUE + " to " + Double.MAX_VALUE);
			}
		}while(true);
	}
	
	/**
	 * Reads a float from the keyboard. Re-prompts for data on invalid entry and
	 * notifies user of accepted values.
	 *
	 * @return float
	 */
	public static float readFloat(){
		do{
			try{
				in = new BufferedReader(new InputStreamReader(System.in));
				return Float.parseFloat(in.readLine());
			}catch(IOException e){
				System.out.println("Accepted values: " + Float.MIN_VALUE + " to " + Float.MAX_VALUE);
			}catch(NumberFormatException nfe){
				System.out.println("Accepted values: " + Float.MIN_VALUE + " to " + Float.MAX_VALUE);
			}
		}while(true);
	}
	
	/**
	 * Reads a long from the keyboard. Re-prompts for data on invalid entry and
	 * notifies user of accepted values.
	 *
	 * @return long
	 */
	public static long readLong(){
		do{
			try{
				in = new BufferedReader(new InputStreamReader(System.in));
				return Long.parseLong(in.readLine());
			}catch(IOException e){
				System.out.println("Accepted values: " + Long.MIN_VALUE + " to " + Long.MAX_VALUE);
			}catch(NumberFormatException nfe){
				System.out.println("Accepted values: " + Long.MIN_VALUE + " to " + Long.MAX_VALUE);
			}
		}while(true);
	}

	/**
	 * Reads a short from the keyboard. Re-prompts for data on invalid entry and
	 * notifies user of accepted values.
	 *
	 * @return short
	 */
	public static short readShort(){
		do{
			try{
				in = new BufferedReader(new InputStreamReader(System.in));
				return Short.parseShort(in.readLine());
			}catch(IOException e){
				System.out.println("Accepted values: " + Short.MIN_VALUE + " to " + Short.MAX_VALUE);
			}catch(NumberFormatException nfe){
				System.out.println("Accepted values: " + Short.MIN_VALUE + " to " + Short.MAX_VALUE);
			}
		}while(true);
	}
}