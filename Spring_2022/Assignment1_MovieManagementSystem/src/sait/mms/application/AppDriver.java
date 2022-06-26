package sait.mms.application;

import java.io.*;

import sait.mms.managers.*;

/**
 * Class description: Entry class into program.
 *
 * @author Waylon Mao (weilong.mao@edu.sait.ca)
 * @version Jun 9, 2022
 */
public class AppDriver {

	/**
	 * Entry method into a program.
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// Uncomment so JAR file can be generated.
		MovieManagementSystem mms = new MovieManagementSystem();
		mms.displayMenu();
	}

}
