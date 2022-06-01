package im;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Used to start up the application. 
 * There are three ways to call the main method:<br>
 * #1: No parameters.<br>
 * In this case, the user will be prompted to input a list of intervals.<br>
 * #2: A single parameter that is not a valid representation of an interval.<br>
 * This will let the application assume that it is a file path and try to access the corresponding file to read the intervals from there.<br>
 * #3: At least one parameter (and if it is only one, the first one has to be a valid representation of an interval)<br>
 * The final case will handle the parameters as the list of intervals that should be used
 * @author Mark
 */
public class ApplicationStarter {		
	public static void main(String[] args) {
		List<Interval> intervals = new ArrayList<Interval>();

		// The first step is to identify what the parameters of the main method call mean and where the method can get the intervals from.
		if (args.length == 0) {
			// If there is no parameter given, the user will need to be prompted to input some intervals.
			intervals = inputIntervals();
		} else {
			if (args.length == 1 && !Interval.isInterval(args[0])) {
				// If there is only one parameter and that isn't an interval, it is accepted as a path to a file to read the intervals from.
				intervals = readIntervalsFromFile(args[0]);
			} else {
				// In all other cases, the parameters are accepted as a direct list of intervals.
				intervals = createIntervalList(args);
			}			
		}		
		
		// If there is no valid interval in the list at this point, this is where the application should terminate.
		if (intervals.isEmpty()) {
			System.out.println("There are no valid intervals to work with.");
			return;
		}
		
		System.out.println("Intervals before merge:");
		System.out.println(intervals.toString().substring(1, intervals.toString().length()-1));
		
		List<Interval> mergedIntervals = IntervalMerger.merge(intervals);
		
		System.out.println("Intervals after merge:");
		System.out.println(mergedIntervals.toString().substring(1, mergedIntervals.toString().length()-1));
	}
	
	/**
	 * This method creates a list of Interval objects from the given string array.
	 * Each of the intervals in string form should look like [x,y], or they will not be added to the list, but instead be printed out as invalid.
	 * This is not an exception though, just a warning. The list will still be generated with the remaining intervals (even if it turns out to be empty).
	 * @param intervalsAsStrings Array of intervals in string form. 
	 * @return List of all valid intervals found within the given string array
	 */
	private static List<Interval> createIntervalList(String[] intervalsAsStrings) {
		List<Interval> intervals = new ArrayList<Interval>();		
		
		// List of invalid intervals in the given String array. Only used to print out to the user.
		List<String> invalidIntervals = new ArrayList<String>(); 
	
		for (int i = 0; i < intervalsAsStrings.length; i++) {			
			// If the current string represents a valid interval, it's added to the result list, otherwise to the list of invalid ones.
			if (Interval.isInterval(intervalsAsStrings[i])) {
				intervals.add(new Interval(intervalsAsStrings[i]));
			} else {
				invalidIntervals.add(intervalsAsStrings[i]);
			}
		}
		
		// Print the invalid intervals out to give feedback to the user
		if (!invalidIntervals.isEmpty()) {
			System.out.println(invalidIntervals.size() + " invalid interval(s) found:");
			for (String invalidParam : invalidIntervals) {
				System.out.println(invalidParam);
			}
		}
		
		return intervals;
	}
	
	/**
	 * Reads a single line filled with intervals from the given file and turns them into a list of intervals.
	 * @param path The path to the file.
	 * @return The list of valid intervals read from the given file.
	 */
	private static List<Interval> readIntervalsFromFile(String path) {
		List<Interval> intervals = new ArrayList<Interval>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
			String[] intervalStrings = reader.readLine().split(" ");
			intervals = createIntervalList(intervalStrings);
		} catch (FileNotFoundException e) {
			System.out.println("Could not find file: " + path);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error while trying to read file: " + path);
			e.printStackTrace();
		}
		
		return intervals;		
	}
	
	/**
	 * Prompts the user to input a list of intervals and then returns that as an actual list of interval objects.
	 * @return The list of valid intervals read from the given input.
	 */
	private static List<Interval> inputIntervals() {		
		System.out.println("You may add intervals by writing: [a,b] [c,d] [e,f] ...");
		System.out.println("x and y have to be integer values.");
		System.out.println();
		System.out.print("Add Interval: ");
		
		Scanner scanner = new Scanner(System.in);
		String[] intervalStrings = scanner.nextLine().split(" ");
		scanner.close();
				
		return createIntervalList(intervalStrings);
	}
}
