import java.util.*;
import java.lang.*;
import java.io.*;
import java.nio.file.*;
import java.net.*;

/**
 * FileBrowser allows a user to traverse the directory structure of all the drives
 * available to a computer using a command line interface.
 *
 * @author  Pash DeVore <mdevore2@my.heartland.edu>
 * @version 1.0
 * @since   2013-11-10
 */

public class FileBrowser implements Runnable{

	/**
	 * Used to exit the run method.
	 */
	private static boolean canRun;
	/**
	 * Collections of type 'File' used for determining
	 * if the current directory's files/directories have changed.
	 */
	private static Collection<File> updatingFiles;
	private static Collection<File> updatingDirectories;
	/**
	 * Keeps track of user's current location.
	 */
	protected java.io.File currentDirectory;
	/**
	 * Used to regulate which file structures are visible.
	 */
	protected java.lang.String currentMask;
	/**
	 * Collections of type 'File' used for storing the roots of a system,
	 * the current directory directories, and the current directory files.
	 */
	private Collection<File> roots;
	private Collection<File> directories;
	private Collection<File> files;

	public final static void main(String args[]){
		FileBrowser fb = new FileBrowser();
		new Thread(fb).start();
		while(true){
			fb.printAll();
			if(fb.getUserInput()){
				canRun = false;
				break;
			}
		}
	}



    /**
     * Creates a new instance of FileBrowser, setting the current directory  
     * to the user's home directory and the current file mask to '.*' 
     * (all files will be displayed).
     */
	public FileBrowser(){
		currentDirectory = new File(System.getProperty("user.home"));
		currentMask = ".*";
	}
	
	
	
	/**
	 * Attempts to change to a user specified directory. If the passed in directory
	 * name does not exist, the user is informed and the directory does not change.
	 * Otherwise, the new directory becomes the current directory.
	 *
	 * @param  newDir       A String referring to the new directory to which the user 
	 *                      wishes to move.
	 * @return java.io.File The updated current directory.                
	 */
	public final java.io.File changeDirectoryDown(java.lang.String newDir){
		File newDirectory = null;
		try{
			newDirectory = new File(currentDirectory, newDir);
			if(newDirectory.exists()){
				currentDirectory = newDirectory;
				return currentDirectory;
			}
		}catch(NullPointerException npe){}
		return currentDirectory;
	}
	
	
	
	/**
	 * Attempts to change the current directory to the current directory's parent. 
	 * If the current directory does not have a parent then the directory remains
	 * unchanged.
	 *
	 * @return java.io.File The updated current directory
	 */
	public final java.io.File changeDirectoryUp(){
		if(currentDirectory.getParentFile() != null){
			currentDirectory = currentDirectory.getParentFile();
			return currentDirectory;
		}
		return currentDirectory;
	}



	/**
	 * Attempts to change the drive to the passed in drive name String. If the passed in
	 * value does not match a drive name, nothing will happen. Otherwise, the
	 * currentDirectory is updated and set to the new drive root.
	 *
	 * @param  newDriveName A String referring to the new drive where the current 
	 *                      directory is to be set.
	 * @return boolean
	 */
	public final boolean changeDrive(java.lang.String newDriveName){
		File[] roots = null;
		try{
			roots = (File[])getRoots().toArray();
			
			for(File f : roots){
				if(newDriveName.equals(f.toString())){
					currentDirectory = new File(newDriveName);
					return false;
				}
			}
		}catch(NullPointerException npe){}
		return true;
	}
	
	
	
	/**
	 * Returns the current directory.
	 *
	 * @return java.io.File The currently active directory.
	 */
	public final java.io.File getCurrentDirectory(){
		return currentDirectory;
	}
	
	
	
	/**
	 * Returns a <b>sorted</b> Collection containing all subdirectories of the current
	 * directory.
	 *
	 * @return java.util.Collection A <b>sorted</b> Collection.
	 */
	public final java.util.Collection getDirectories(){
		File[] files = null;
		ArrayList<File> directoriesCollection = null;
		try{
			files = currentDirectory.listFiles();
			directoriesCollection = new ArrayList<File>();
			for(File f : files){
				if(f.isDirectory()){
					directoriesCollection.add(f);
				}
			}
		}catch(SecurityException se){}
		return directoriesCollection;
	}
	
	
	
	/**
	 * Returns a <b>sorted</b> Collection of files in the current directory.
	 *
	 * @return java.util.Collection A <b>sorted</b> Collection.
	 */
	public final java.util.Collection getFiles(){
		if(!currentMask.equals(".*")){
			return getFiles(currentMask);
		}
		List<File> filesCollection = null;
		try{
			filesCollection = Arrays.asList(currentDirectory.listFiles());
		}catch(SecurityException se){}
		return filesCollection;
	}
	
	
	
	/**
	 * Returns a <b>sorted</b> Collection of files in the current directory with the 
	 * passed in file type mask. If the user has not specified a file mask, the '.*' mask
	 * will be used and all files will be returned.
	 *
	 * @param fileTypeMask A String referring to the file structure to be masked.
	 */
	public final java.util.Collection getFiles(java.lang.String fileTypeMask){
			File[] files = null;
			List<File> filesCollection = new LinkedList<File>();
			try{
				files = currentDirectory.listFiles();
				for(File f : files){
					if(!f.toString().endsWith(fileTypeMask)){
						filesCollection.add(f);
					}
				}
			}catch(SecurityException se){}
			return filesCollection;
	}
	
	
	
	/**
	 * Returns a <b>sorted</b> Collection of the root drives.
	 *
	 * @return java.util.Collection A <b>sorted</b> Collection.
	 */
	public final java.util.Collection getRoots(){
		List<File> rootsCollection = null;
		try{
			rootsCollection = Arrays.asList(File.listRoots());
		}catch(SecurityException se){}
		return rootsCollection;
	}



	/**
	 * Run method which keeps track of any changes to the directory
	 * the user is currently in.
	 */
	public final void run(){
		canRun = true;
		while(true){
			try{
				updatingDirectories = getDirectories();
				updatingFiles = getFiles();
				Thread.sleep(1000);
			}catch(InterruptedException ie){}
			finally{
				if((updatingDirectories.equals(getDirectories()) == false) || 
				(updatingFiles.equals(getFiles()) == false)) {
					printAll(); 
				}
				if(!canRun)break;
			}
		}
	}



	/**
	 * Prints out information about the user's current directory. Lists the 
	 * files and directories in regards to the user's current location in 
	 * the file system. Files also show read, write, and execution rights.
	 * Finally, the user is given a list of choices to pick from for future
	 * action.
	 */
	private final void printAll(){
		try{
			roots = getRoots();
			directories = getDirectories();
			files = getFiles();
			System.out.println("\n\n");
			System.out.println("=======================================================");
			System.out.println("YOUR CURRENT DIRECTORY: " + getCurrentDirectory());
			System.out.println("=======================================================");
			for(File f : directories){
				if(f.isDirectory()){
					String print = f.getName();
					while(print.length() < 30) print += " ";
					print = print + "<DIR>";
					System.out.println(print);
				}
			}
			for(File f: files){
				if(f.isFile()){
					String readable = f.canRead() ? "Read " : "---- ";
					String writable = f.canWrite() ? "Write " : "----- ";
					String executable = f.canExecute() ? "Execute" : "-------";
					String print = f.getName();
					while(print.length() < 30) print += " ";
					print = print + readable + writable + executable;
					System.out.println(print);
				}
			}
			System.out.println("=====================================================\n");
			System.out.println("1)  Change The File Mask");
			System.out.println("2)  Change To Subdirectory");
			System.out.println("3)  Change To Parent Directory");
			System.out.println("4)  Change Drive");
			System.out.println("5)  Exit");
		}catch(SecurityException se){}
	}
	
	
	
	/*
	 * Follow up to the printAll() method. Gets user input 
	 * for the selected choice and reacts accordingly. Handles
	 * improper input and exceptions.
	 */
	private final boolean getUserInput(){
		try{
			String choice = KeyboardReader.readLine();
			if(choice.equals("1")){
				//change file mask
				System.out.println("Type in the new file extension mask or press enter " + 
				"to clear it");
				String mask = KeyboardReader.readLine();
				if(mask.equals("")){
					currentMask = ".*";
					System.out.println("ALL FILE EXTENSIONS CURRENTLY VALID");
					return false;
				}
				else if(mask.length() <= 5 && mask.charAt(0) == '.'){
					currentMask = mask;
					return false;
				}
				System.out.println("INCORRECT FORMAT. MUST BE OF FORM '.XXXX' WHERE X " +
				 "REPRESENTS A CHARACTER");
				return false;
			}
			else if(choice.equals("2")){
				//change to subdirectory
				System.out.println("Enter the name of the new directory");
				String newDir = KeyboardReader.readLine();
				for(File f : directories){
					if((f.getName()).equalsIgnoreCase(newDir)){
						changeDirectoryDown(f.getName());
						updatingDirectories = getDirectories();
						updatingFiles = getFiles();
						return false;
					}
				}
				System.out.println("NO SUCH DIRECTORY EXISTS");
				return false;
			}
			else if(choice.equals("3")){
				//change to parent directory
				changeDirectoryUp();
				updatingDirectories = getDirectories();
				updatingFiles = getFiles();
				return false;
			}
			else if(choice.equals("4")){
				//change drive
				for(File f : roots){
					System.out.println(f.toString());
				}
				System.out.println("Type in the name of the drive to use");
				String newRoot = KeyboardReader.readLine();
				for(File f : roots){
					if((f.toString()).equalsIgnoreCase(newRoot)){
						changeDrive(f.toString());
						updatingDirectories = getDirectories();
						updatingFiles = getFiles();
						return false;			
					}
				}
				System.out.println("NO SUCH DRIVE EXISTS");
				return false;
			}
			else if(choice.equals("5")){
				//exit
				return true;
			}
		}catch(SecurityException se){}
		return false;
	}
}