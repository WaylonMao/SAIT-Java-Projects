package sait.mms.problemdomain;

/**
 * Class description: The Movie class represents a movie record. There are 3
 * parameters: integer duration, string title and integer year.
 *
 * @author Waylon Mao (weilong.mao@edu.sait.ca)
 *
 */
public class Movie {
	/**
	 * The duration of the Movie.
	 * 
	 * @Fields duration
	 */
	private int duration;
	/**
	 * The title of the Movie.
	 * 
	 * @Fields title
	 */
	private String title;
	/**
	 * The year of the Movie.
	 * 
	 * @Fields year
	 */
	private int year;

	/**
	 * 
	 * Initializes the newly created Movie. Non-default or user-defined constructor.
	 * 
	 * @param newDuration The duration of the new Movie.
	 * @param newTitle    The title of the new Movie.
	 * @param newYear     The year of the new Movie.
	 */
	public Movie(int newDuration, String newTitle, int newYear) {
		this.duration = newDuration;
		this.title = newTitle;
		this.year = newYear;
	}

	/**
	 *
	 * Get the duration of the movie.
	 * 
	 * @return Duration of movie (in minutes).
	 */
	public int getDuration() {
		return this.duration;
	}

	/**
	 *
	 * Get the title of the movie.
	 * 
	 * @return Title of movie.
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 *
	 * Get the year the movie was released.
	 * 
	 * @return Year movie was released.
	 */
	public int getYear() {
		return this.year;
	}

	/**
	 * 
	 * Format the movie object as a String.
	 * 
	 * @return Duration, year, and title of movie.
	 */
	public String toString() {
		String output = String.format("%-9s%s\t%s", duration, year, title);
		return output;
	}

	/**
	 * Format the movie object as a String to write into the database.
	 * 
	 * @return Duration, year, and title of movie.
	 */
	public String formatForFile() {
		String toFile = String.format("%s,%s,%s", duration, title, year);
		return toFile;
	}
}
