package sait.mms.managers;

import sait.mms.problemdomain.*;

import java.io.*;
import java.util.*;

/**
 * Class description: This class read, write, and generate random movies based
 * on the data in movie.txt and display the result.
 *
 * @author Waylon Mao (weilong.mao@edu.sait.ca)
 */
public class MovieManagementSystem {

	/**
	 * Used to get input from the keyboard.
	 * 
	 * @Fields keyboard
	 */
	private Scanner keyboard = new Scanner(System.in);

	/**
	 * Hold list of movies in memory.
	 * 
	 * @Fields movies
	 */
	private ArrayList<Movie> movies = new ArrayList<>();

	/**
	 * Location of text file (relative to the project folder).
	 * 
	 * @Fields TEXT_FILE
	 */
	public static final String TEXT_FILE = "res/movies.txt";

	/**
	 * 
	 * Initializes the newly created MovieManagementSystem
	 * 
	 * @throws FileNotFoundException
	 */
	public MovieManagementSystem() throws FileNotFoundException {

	}

	/**
	 * Display the menu, show options to user. There is a logical structure with a
	 * loop, if-else and checking inputs. There are 4 options: 1 Add New Movie and
	 * Save 2 Generate List of Movies Released in a Year 3 Generate List of Random
	 * Movies 4 Save & Exit If user input other number, it would show error message;
	 * 
	 * @throws FileNotFoundException
	 */
	public void displayMenu() throws FileNotFoundException {
		// A string to temporarily stores the input string.
		String input = "";
		int duration;
		int year;
		String title;
		// The menu will be running until the user chooses 4 to exit.
		while (!input.equals("4")) {
			// Load movies from a TXT file to ArrayList.
			this.loadMovies();
			System.out.println("\nMovie Management system");
			System.out.println("1\tAdd New Movie and Save");
			System.out.println("2\tGenerate List of Movies Released in a Year");
			System.out.println("3\tGenerate List of Random Movies");
			System.out.println("4\tSave & Exit\n");
			System.out.print("Enter an option: ");
			input = keyboard.nextLine();

			// The if-else nested structure judges user input and access other functions.
			if (input.equals("1")) {
				System.out.print("\nEnter duration: ");
				input = keyboard.nextLine();
				duration = toDigitals(input);

				System.out.print("Enter movie title: ");
				title = keyboard.nextLine();

				System.out.print("Enter year: ");
				input = keyboard.nextLine();
				year = toDigitals(input);
				System.out.println("Saving movies...");
				this.addMovie(duration, title, year);

			} else if (input.equals("2")) {
				System.out.print("\nEnter in year: ");
				input = keyboard.nextLine();
				year = toDigitals(input);
				if (year == 0) {
					System.out.print("Error: The movie year should be any number greater than zero.\n\n");
				} else {
					generateMoviesInYear(year);
				}

			} else if (input.equals("3")) {
				System.out.print("\nEnter number of movies: ");
				input = keyboard.nextLine();
				int number = toDigitals(input);
				if (number == 0) {
					System.out.print("Error: The number of movies should be any number greater than zero.\n\n");
				} else {
					generateRandomMovies(number);
				}

			} else if (input.equals("4")) {
				writeMoviesToFile();
				System.out.print("Goodbye!\n");
			} else {
				System.out.println("\nInvalid option! Please try again.\n");
			}
		}

	}

	/**
	 * 
	 * Adds a movie to the array list.
	 * 
	 * @param duration Duration of movie.
	 * @param title    Movie title.
	 * @param year     Year movie was released.
	 * @throws FileNotFoundException
	 */
	public void addMovie(int duration, String title, int year) throws FileNotFoundException {
		// A boolean variable that stores whether there is an error.
		boolean error = false;
		if (duration <= 0) {
			System.out.print("Error: The movie duration should be any number greater than zero.\n\n");
			error = true;
		}
		if (title.equals("")) {
			System.out.print("Error: The movie title should have at least one character.\n\n");
			error = true;
		}
		if (year <= 0) {
			System.out.print("Error: The movie year should be any number greater than zero.\n\n");
			error = true;
		}
		if (!error) {
			movies.add(new Movie(duration, title, year));
			System.out.print("Added movie to the data file.\n\n");
		}

	}

	/**
	 * 
	 * Generates and displays all movies from a year as well as the total duration.
	 * 
	 * @param year The year of movies to display.
	 */
	public void generateMoviesInYear(int year) {
		int totalDuration = 0;
		System.out.println("\nMovie List");
		System.out.println("Duration Year\tTitle");
		for (Movie movie : movies) {
			if (movie.getYear() == year) {
				System.out.println(movie.toString());
				totalDuration += movie.getDuration();
			}
		}
		System.out.printf("\nTotal duration: %d minutes\n", totalDuration);

	}

	/**
	 * 
	 * Generates and displays a list of random movies as well as the total duration.
	 * 
	 * @param number The number of movies to display.
	 */
	public void generateRandomMovies(int number) {
		int totalDuration = 0;
		Collections.shuffle(movies);
		System.out.println("\nMovie List");
		System.out.println("Duration Year\tTitle");
		for (int i = 0; i < number; i++) {
			System.out.println(movies.get(i).toString());
			totalDuration += movies.get(i).getDuration();
		}
		System.out.printf("\nTotal duration: %d minutes\n", totalDuration);
	}

	/**
	 * 
	 * Load movies from text file into array list.
	 * 
	 * @throws FileNotFoundException Thrown if the text file does not exist.
	 */
	public void loadMovies() throws FileNotFoundException {
		File txtFile = new File(TEXT_FILE);
		Scanner inFile = new Scanner(txtFile);
		// To avoid duplicate the ArrayList movies in this class, use this newMovies to
		// storage the movies objects.
		ArrayList<Movie> newMovies = new ArrayList<>();
		inFile.useDelimiter("\r\n|,");
		while (inFile.hasNext()) {
			int duration = inFile.nextInt();
			String title = inFile.next();
			int year = inFile.nextInt();
			newMovies.add(new Movie(duration, title, year));
		}
		// Close Scanner.
		inFile.close();
		// Replace(Reference) movies by newMovies.
		movies = newMovies;
	}

	/**
	 * 
	 * Save movies from objects in memory to hard drive.
	 * 
	 * @throws FileNotFoundException Thrown if the text file does not exist.
	 */
	public void writeMoviesToFile() throws FileNotFoundException {
		File textFile = new File(TEXT_FILE);

		PrintWriter writer = new PrintWriter(textFile);
		System.out.print("\nSaving movies... \n");

		for (Movie movie : movies) {
			writer.println(movie.formatForFile());
		}
		writer.close();
		System.out.print("Movie list saved successfully!\n");
	}

	/**
	 * This method check if the string contains only numbers. If the user enters a
	 * non-number, the input string will be treated as 0.
	 * 
	 * @param string The string user input.
	 * @return Integer Transfer string to integer and treat non-number as 0.
	 */
	public int toDigitals(String string) {
		// If the input string is empty or has non-numbers, the integer will be
		// default 0.
		int digitals = 0;
		if (!string.isEmpty() && string.matches("[0-9]+")) {
			digitals = Integer.parseInt(string);
		}
		return digitals;
	}
}
