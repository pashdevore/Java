import java.io.*;
import java.lang.*;
import java.util.*;

/**
 * CA (cellular automaton) class gets user input for variables that will determine the
 * survival rate of cells within a grid system. The grid is determined for every 
 * generation and according to the user will save data to a file every X generations.  
 *
 * @author  Pash DeVore <mdevore2@my.heartland.edu>
 * @version 1.0
 * @since   2013-09-30
 */

public class CA{

	/**
	 * KeyboardReader for getting user input.
	 */
	 private static KeyboardReader kr;
	 
	/**
	 * 2d boolean array used for cellular automatons(CAs) survival data.
	 */
	private boolean[][] grid;

	/**
	 * Minimum neighbors that each cell must have to survive into the next generation.
	 */
	private int minNeighbors;

	/**
	 * Maximum neighbors that each cell can have to survive into the next generation.
	 */
	private int maxNeighbors;

	/**
	 * Current generation variable necessary to determine when grid data should be 
	 * saved.
	 */
	private int currentGeneration;

	/**
	 * Maximum number of generations in which grid data should be determined.
	 */
	private int maxGenerations;

	/**
	 * Modulus value used to determine when to save data.
	 */
	private int modulus;

	/**
	 * File to which data from CA methods is saved.
	 */
	private File saveFile = null;
	
	
	/**
	 * Constructor for CA objects which instantiates all necessary class variables.
	 *
	 * @throws IllegalArgumentException If parameter is of conflicting type, fewer 
	 * 			                        paramaters are used,or more parameters than 
	 *									necessary are passed to the constructor.
	 *                                  Other reasons can be seen in the below
	 *									description of parameters of the constructor. 
	 *				    
	 * @param  gridSize                 Size of grid which must be between 5 and 
	 *				    				50(inclusive) or an exception is thrown.
	 * @param  initProbability	        The probability value which must be between 0 
	 *                                  and 1(exclusive) or an exception is thrown. 
	 * @param  minN			            The minimum neighbors which must be between 1 
	 *				    				and 4(inclusive) or an exception is thrown.
	 * @param  maxN			    	    The maximum neighbors which must be between 2 
	 *  				    			and 8(inclusive) or an exception is thrown.
	 * @param  maxGens		    		The maximum number of generations that the 
	 *				    				program will run through to determine the 
	 *									surviving cells.  
	 * @param  mod			    		The modulus value which must be less than 
	 *				    				maxGens(inclusive) or an exception is thrown.
	 * @param  fileName		    		The file name to which the determined generations 
	 *				    				will be saved. This must be a valid file name. 
	 */
	 
	public CA(int gridSize, double initProbability, int minN, int maxN, int maxGens, int mod, String fileName) throws IllegalArgumentException{
		
		if(gridSize < 5 || gridSize > 50) throw new IllegalArgumentException();
		if(initProbability < 0.0 || initProbability >= 1.0) throw new IllegalArgumentException();
		if(minN < 1 || minN > 4) throw new IllegalArgumentException();
		if(maxN < 2 || maxN > 8) throw new IllegalArgumentException();
		if(mod > maxGens) throw new IllegalArgumentException();
		
		modulus = mod;
		minNeighbors = minN;
		maxNeighbors = maxN;
		maxGenerations = maxGens;
		currentGeneration = 0;
		
		grid = new boolean[gridSize][gridSize];
		saveFile = new File(fileName);
		setupInitialState(initProbability);
	}

	/**
	 * Determines survival and death of cells into the next generation.
 	 * <p>
	 * Each randomized boolean value within the grid is evaluated(true values are alive). 
	 * This is done by looking at immediate neighbors (wrapped cells in the 2d array
	 * are also considered neighbors) and evaluating whether or not they are alive or 
	 * dead. If the cell being evaluated has at least the minimum number of neighbors
	 * and less than the maximum number of neighbors, it will survive. 
	 * 
	 * @return boolean Whether or not you have reached the maximum generation value.
	 */
	
	public boolean createNextGeneration(){
		boolean[][] nextGrid = new boolean[grid.length][grid.length];

		for(int i = 0; i < nextGrid.length; i++){
			for(int j = 0; j < nextGrid.length; j++){
				if(shouldBeAlive(i,j)){
					nextGrid[i][j] = true;
				}
				else{
					nextGrid[i][j] = false;
				}
			}
		}
		grid = nextGrid;
		currentGeneration++;
		
		if(currentGeneration%modulus == 0){
			//write to file
			save();
		}
		
		if(currentGeneration <= maxGenerations) return true;
		return false;
	}

	/**
	 * Saves the data in a 2d boolean array for the current generation.
	 * <p>
	 * Upon determination of whether the user input for file name exists, is readable, and 	
	 * is writable, the method loops through the grid data and saves an interpretable 
	 * image.
	 */
	 
	public void save(){
		PrintWriter writer = null;
		if(saveFile.exists() && saveFile.canWrite() && saveFile.canRead()){
				try{
					writer = new PrintWriter(new BufferedOutputStream(new FileOutputStream(saveFile,true)));
					String currentGen = Integer.toString(currentGeneration);
					writer.append(currentGen);
					writer.append("\n\n");
					
					for(int i = 0; i < grid.length; i++){
						for(int j = 0; j < grid.length; j++){
							if(grid[i][j]){
								writer.append("| * |");
								if(j==grid.length-1)writer.append("\n");
							}
							else{
								writer.append("|   |");
								if(j==grid.length-1)writer.append("\n");
							}
						}
					}
				}catch(FileNotFoundException fnfe){
					System.out.println("File for output not found:" + saveFile);
				}finally{
					try{
						writer.close();
					}catch(Exception e){
						System.out.println("Warning! File was not properly closed.");
					}
				}
			}
	}

	/**
	 * Sets up the initial grid of cellular automatons based upon the user
	 * input for initial probability.
	 * 
	 * @param initProb The initial probability used to set up the initial grid. The value
	 *				   must be between 0 and 1(exclusive). A smaller value results in
	 *				   better chance for living cells.
	 */

	private void setupInitialState(double initProb){
		Random random = new Random();
		
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid.length; j++){
				if(random.nextDouble() >= initProb){
					grid[i][j] = true;
				}
				else{
					grid[i][j] = false;
				}
			}
		}
	}

	/**
	 * Determines whether or not a cell will survive into the next generation.
	 * 
	 * @param i        The row of the grid we are inspecting.
	 * @param j        The column of the grid we are inspecting.
	 * @return boolean Whether or not the cell will be alive in the next generation.
	 */

	private boolean shouldBeAlive(int i, int j){
		int nNeighbors = 0;
		
		if(i+1 < grid.length){
			if(grid[i+1][j])nNeighbors++;
		}else{ // at the end of the column
			if(grid[0][j])nNeighbors++;
		}
		
		if(i-1 > -1){
			if(grid[i-1][j])nNeighbors++;
		}else{ // at the beginning of a column
			if(grid[grid.length-1][j])nNeighbors++;
		}
		
		if(j+1 < grid.length){
			if(grid[i][j+1])nNeighbors++;
		}else{ // at the end of a row
			if(grid[i][0])nNeighbors++;
		}
		
		if(j-1 > -1){
			if(grid[i][j-1])nNeighbors++;
		}else{ // at the beginning of a row
			if(grid[i][grid.length-1])nNeighbors++;
		}
		
		if(nNeighbors > maxNeighbors || nNeighbors < minNeighbors) return false;
		else{
			return true;
		}
	}

	/**
	 * Main method used for getting user input for the values used in the CA constructor.
	 */
	
	public static void main(String[] args){
		kr = new KeyboardReader();
		
		System.out.print("Grid size: ");
		int gs = kr.readInt();
		
		System.out.print("Initial probability: ");
		double ip = kr.readDouble();
		
		System.out.print("Minimum neighbors: ");
		int minN = kr.readInt();
		
		System.out.print("Maximum neighbors: ");
		int maxN = kr.readInt();
		
		System.out.print("Maximum generations: ");
		int mg = kr.readInt();
		
		System.out.print("Modulus: ");
		int mod = kr.readInt();
		
		System.out.print("File name: ");
		String file = kr.readLine();
		
		CA userInput = new CA(gs,ip,minN,maxN,mg,mod,file);
		
		while(userInput.createNextGeneration());
	}
}