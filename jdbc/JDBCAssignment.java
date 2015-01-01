import java.sql.*;
import java.io.*;

/**
 * Database assignment for CSCI 131 that allows a user to use
 * a command line utility to update, change, and enter new assignment
 * grades for a class. The user is also able to add new students.
 *
 * @author Pash DeVore <Pash.DeVore@heartland.edu>
 * @version 1.0
 * @since 2013-12-04
 */

public class JDBCAssignment{
	public static final void main(String[] args){
		TeacherHelper();
	}


	/**
	 * Runs a command line utility for adding, updating, and seeing assignment grades for students.
	 *
	 * Has four different selections. These include adding students, updating grades for assignments,
	 * and adding and entering assignment grades for the entire class.
	 */
	private static final void TeacherHelper(){
	    try{
	    	Class.forName("com.mysql.jdbc.Driver").newInstance();
		}catch(ClassNotFoundException cnfe){
			System.out.println("Unable to load database driver. Class not found.");
			System.exit(0);
		}catch(InstantiationException ie){
		}catch(IllegalAccessException iae){}
		finally{

	    Connection conn = null;
	    try{
	    	conn = DriverManager.getConnection("jdbc:mysql://techsql/pash?user=mdevore2&password=0223526");
	    	DatabaseMetaData dbMetaData = conn.getMetaData();
	    	ResultSet resultSet = null;
	    	Statement statement = null;

				//COLUMNS IN grades TABLE --- ID,ASSIGNMENT,GRADE
				//COLUMNS IN students TABLE --- ID,FIRST_NAME,LAST_NAME
				//
				//METADATA --- TABLE_CAT, TABLE_SCHEM, TABLE_NAME, COLUMN_NAME, DATA_TYPE, TYPE_NAME, COLUMN_SIZE, BUFFER_LENGTH,
				//DECIMAL_DIGITS, NUMBER_PREC_RADIX, NULLABLE, SQL_DATA_TYPE, SQL_DATETIME_SUB, CHAR_OCTET_LENGTH, ORIGINAL_POSITION,
				//IS_NULLABLE, SCOPE_CATALOG, SCOPE_SCHEMA, SCOPE_TABLE, SOURCE_DATA_TYPE, IS_GENERATEDCOLUMN

			while(true){
	    		KeyboardReader kr = new KeyboardReader();
	    		System.out.println("Select one of the following by number");
	    		System.out.println("---------------------------------------");
	    		System.out.println("1) Create a new student");
	    		System.out.println("2) Add grades for a new assignment");
	    		System.out.println("3) Change a grade for an assignment");
	    		System.out.println("4) See all grades for an assignment");
	    		System.out.println("5) Exit");
	    		int s = kr.readInt();

				switch(s){
					case 1:
						System.out.print("Please enter student's FIRST NAME: ");
						String first = kr.readLine();
						System.out.print("Please enter student's LAST NAME: ");
						String last = kr.readLine();
						statement = conn.createStatement();
						statement.execute("INSERT INTO students (FIRST_NAME, LAST_NAME) VALUES ('" + first + "','" + last + "')");
						statement = null;
						break;
					case 2:
						System.out.print("Please enter the ASSIGNMENT NUMBER for the new assignment: ");
						int assignment = kr.readInt();
						statement = conn.createStatement();
						resultSet = statement.executeQuery("SELECT * FROM students");
						while(resultSet.next()){
							//get method params must be in numerical order!
							int id = resultSet.getInt(1);
							last = resultSet.getString(2);
							first = resultSet.getString(3);


							System.out.print("Please enter " + first + " " + last + "'s grade for Assigment " + assignment + ": ");
							String grade = kr.readLine();

							Statement insert = conn.createStatement();
							insert.executeUpdate("INSERT INTO grades (ID,ASSIGNMENT,GRADE) VALUES ('" + id + "','" + assignment + "','" + grade + "')");
						}
						resultSet.close();
						resultSet = null;
						statement = null;
						break;
					case 3:
						System.out.println("Please enter the ASSIGNMENT NUMBER you wish to change: ");
						assignment = kr.readInt();
						System.out.println("Please enter the student's FIRST NAME: ");
						first = kr.readLine();
						System.out.println("Please enter the student's LAST NAME: ");
						last = kr.readLine();
						System.out.println("Please enter the new GRADE to be given: ");
						String grade = kr.readLine();

						statement = conn.createStatement();
						resultSet = statement.executeQuery("SELECT ID FROM students WHERE FIRST_NAME='" + first + "' AND LAST_NAME='" + last + "'");
						while(resultSet.next()){
							int id = resultSet.getInt(1);

							Statement update = conn.createStatement();
							update.executeUpdate("UPDATE grades SET GRADE='" + grade + "' WHERE ID='" + id + "' AND ASSIGNMENT='" + assignment + "'");
						}
						resultSet.close();
						resultSet = null;
						statement = null;
						break;
					case 4:
						System.out.println("Please enter the ASSIGNMENT NUMBER which you wish to see all grades for: ");
						assignment = kr.readInt();

						statement = conn.createStatement();
						resultSet = statement.executeQuery("SELECT * FROM grades WHERE ASSIGNMENT='" + assignment + "'");
						while(resultSet.next()){
							//get method params must be in numerical order!
							int id = resultSet.getInt(1);
							grade = resultSet.getString(3);

							Statement select = conn.createStatement();
							ResultSet query = select.executeQuery("SELECT FIRST_NAME,LAST_NAME FROM students WHERE ID='" + id + "'");
							first="";last="";
							while(query.next()){
								//get method params must be in numerical order!
								first = query.getString(1);
								last = query.getString(2);
							}
							query.close();
							query = null;

							System.out.println("Student: " + first + " " + last);
							System.out.println("Assignment: " + assignment);
							System.out.println("Grade: " + grade + "\n");
						}
						resultSet.close();
						resultSet = null;
						statement = null;
						break;
					case 5:
						System.exit(0);
						break;
					default:
						break;
				}
			}
	    		//Statement statement = conn.createStatement();
	    		//ResultSet resultSet = statement.executeQuery(s);

				//ResultSetMetaData rsMetaData = resultSet.getMetaData();
				//int columnCount = rsMetaData.getColumnCount();

				//while(resultSet.next()){
				//		for(int i = 1; i <= columnCount; i++){
				//			if(rsMetaData.getColumnClassName(i).equals("java.lang.String")){
				//				System.out.println(resultSet.getString(rsMetaData.getColumnLabel(i)));
				//			}
				//			else if(rsMetaData.getColumnClassName(i).equals("java.lang.Integer")){
				//				System.out.println(resultSet.getInt(rsMetaData.getColumnLabel(i)));
				//			}
				//		}
				//	}
	    		//resultSet.close();
	    		//resultSet = null;
		}catch(SQLException sqle){
			sqle.printStackTrace(System.err);
			}finally{
				try{
					conn.close();
				}catch(Exception e){}
			}
		}
	}
}