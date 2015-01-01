import java.util.*;
import java.lang.*;
import java.io.*;

/**
 * WordCountDriver is the driver for the WordCount class. It will go through a text file
 * and determine how many times each word is used and display the information to the
 * user.
 *
 * @author  Pash DeVore <mdevore2@my.heartland.edu>
 * @version 1.0
 * @since   2013-10-31
 */

public class WordCountDriver{

	/**
	 * Main class method used to get user input for absolute filepath of file to be read, 
	 * search through the files words, and sort them. All words and word counts are then
	 * displayed on the screen.
	 *
	 * @param args not used
	 */
	public static final void main(String[] args){
		System.out.print("Please enter the absolute path to the file you wish to search and sort: ");
		String filePath = KeyboardReader.readLine();
		long start = System.currentTimeMillis();
		sortAndSearchFile(filePath);
		long end = System.currentTimeMillis();
		System.out.println("Time it took to look through Homer's Odyssey: " + (end - start) + "milliseconds.");
	}

	/**
	 * Looks at a text file and stores all instances of words in an array along with
	 * the number of times each word is present throughout the file.
	 *
	 * @param fileName the absolute path to the text file
	 */
	private static void sortAndSearchFile(String fileName){
		File f = null;
		Reader r = null;
		StreamTokenizer st = null;
		WordCount[] wcArray = null;

		try{
			f = new File(fileName);
			r = new BufferedReader(new FileReader(f));
			st = new StreamTokenizer(r);
			wcArray = new WordCount[1];
			wcArray[0] = new WordCount("");
		}catch(IllegalArgumentException iae){iae.printStackTrace();}
		catch(NullPointerException npe){npe.printStackTrace();}
		catch(FileNotFoundException fnfe){
			System.out.println("The file path " + fileName + " does not exist.");
		}finally{
			try{

				st.whitespaceChars(' ', '@');
				st.ordinaryChar('\'');
				while(st.ttype != st.TT_EOF){
					st.nextToken();

					if(st.sval != null){
						int bs = binarySearch(wcArray,st.sval.toString());
						if(bs == -1){
							WordCount[] copy = new WordCount[wcArray.length+1];
							System.arraycopy(wcArray,0,copy,0,wcArray.length);
							copy[wcArray.length] = new WordCount(st.sval.toString());
							insertionSort(copy);
							wcArray=copy;
						}
						else{
							wcArray[bs].increaseCount();
						}
					}
				}
				for(int i = 1; i < wcArray.length; i++){
					System.out.println(wcArray[i].toString());
				}
			}catch(IOException e){
				e.printStackTrace();
			}finally{
				try{
					r.close();
				}catch(IOException e){
					System.out.println("There was an error when closing your file.");
				}finally{

				}
			}
		}
	}
	
	/**
	 * Uses binary search to look through a WordCount array for a WordCount object 
	 * with a word variable matching the string parameter.
	 *
	 * @param array     the WordCount array to be searched
	 * @param searchVal the string value being compared
	 * @return int 
	 */
	private static int binarySearch(WordCount[] array, String searchVal){
		int startIndex = 0;
		int endIndex = array.length-1;
		int index;

		while(endIndex >= startIndex){
			//if startIndex and endIndex are equal, we either have or haven't
			//found the matching value
			if(startIndex == endIndex){
				index = startIndex;
				if(array[index].compareTo(searchVal) != 0)return -1;
				else{
					return index;
				}
			}
			else{
				index = ((endIndex-startIndex)/2) + startIndex;
				if(array[index].compareTo(searchVal)>0){
					//search lower half of array
					endIndex = index - 1;
				}
				else if(array[index].compareTo(searchVal)<0){
					//search upper half of array
					startIndex = index + 1;
				}
				else{
					//we found the value
					return index;
				}
			}
		}
		return -1;
	}
	
	/**
	 * Sort method of type insertion sort used for proper ordering of a WordCount array.
	 *
	 * @param wc the WordCount array to be sorted
	 */
	private static void insertionSort(WordCount[] wc){
		int sortedIndex = 0;
		int unsortedIndex = 1;
		WordCount temp;

		do{
			temp = wc[unsortedIndex];

			if(temp.compareTo(wc[sortedIndex].getWord()) < 0){
				while(temp.compareTo(wc[sortedIndex].getWord()) < 0){
					wc[sortedIndex+1] = wc[sortedIndex];
					wc[sortedIndex] = temp;
					sortedIndex--;
				}
				wc[sortedIndex+1] = temp;
				unsortedIndex++;
				sortedIndex = unsortedIndex - 1;
			}
			else if(temp.compareTo(wc[sortedIndex].getWord()) > 0){
							unsortedIndex++;
							sortedIndex = unsortedIndex-1;
			}
		}
		while(unsortedIndex != wc.length);
	}
}