import java.util.*;
import java.lang.*;

/**
 * WordCount class used for storing a word and its count. Intended to be used
 * with words in a text file.
 *
 * @author  Pash DeVore <mdevore2@my.heartland.edu>
 * @version 1.0
 * @since   2013-10-31
 */
 
public class WordCount implements Comparable<String>{
	
	/**
	 * Class variable to store an instance of a word.
	 */
	private final String word;
	
	/**
	 * Class variable to store the number of instances of the word.
	 */
	private int wordCount;

	/**
	 * Public constructor which sets the word to the string parameter and the wordCount
	 * initially to 1.
	 *
	 * @param word sets the word class variable
	 */
	public WordCount(String word){
		this.word = word;
		this.wordCount = 1;
	}
	
	/**
	 * Public method to increase the wordCount class variable by one.
	 */
	public void increaseCount(){
		wordCount = wordCount + 1;
	}

	/**
	 * Public getter method for the word class variable.
	 *
	 * @return String
	 */
	public String getWord(){
		return this.word;
	}

	/**
	 * Public getter method for the wordCount class variable.
	 *
	 * @return int
	 */
	public int getCount(){
		return this.wordCount;
	}

	/**
	 * Overridden toString method which returns a string displaying the word
	 * and count for the word.
	 *
	 * @return String
	 */
	@Override public String toString(){
		return word + " (" + wordCount + ")";
	}

	/**
	 * Implementation of the compareTo method for the class.
	 *
	 * @param  s a string to which a class object is to be compared
	 * @return int
	 */
	public int compareTo(String s){
		return this.word.compareTo(s);
	}

	/**
	 * Overridden equals method.
	 * 
	 * @param o an object of type Object
	 * @return boolean
	 */
	@Override public boolean equals(Object o){
		if(o == this)return true;
		if(!(o instanceof WordCount))return false;
		WordCount wc = (WordCount)o;
		return wc.word == word && wc.wordCount == wordCount;
	}
	
	/**
	 * Overridden hashCode method.
	 *
	 * @return int
	 */
	@Override public int hashCode(){
		int result = 17;
		result *= word.hashCode();
		result = 31 * result + wordCount;
		return result;
	}
}