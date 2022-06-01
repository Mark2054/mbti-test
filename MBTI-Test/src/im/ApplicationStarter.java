package im;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ApplicationStarter {
	public static final String PATTERN = "\\[-?\\d*,-?\\d*\\]";
		
	public static void main(String[] args) {
		List<Interval> intervals = new ArrayList<Interval>();

		ExecutionMode mode = ExecutionMode.Input;
		if (args.length > 0) {
			mode = ExecutionMode.Param;		
			
			if (args.length == 1 && !isInterval(args[0])) {
				mode = ExecutionMode.File;
			}
		}
		
				
		if (mode == ExecutionMode.Input) {
			intervals = inputIntervals();
		} else if (mode == ExecutionMode.Param) {
			intervals = readParamsAsIntervals(args);
		} else {
			intervals = readIntervalsFromFile(args[0]);
		}
		
		List<Interval> mergedIntervals = IntervalMerger.merge(intervals);
		
		System.out.println("Intervals before merge:");
		for (Interval interval : intervals) {
			System.out.print(interval + " ");
		}
		System.out.println();
		
		System.out.println("Intervals after merge:");
		for (Interval interval : mergedIntervals) {
			System.out.print(interval + " ");
		}
		System.out.println();
	}
	
	public static List<Interval> readIntervalsFromFile(String path) {
		List<Interval> intervals = new ArrayList<Interval>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
			String[] intervalStrings = reader.readLine().split(" ");
			intervals = readParamsAsIntervals(intervalStrings);
		} catch (FileNotFoundException e) {
			System.out.println("Could not find file: " + path);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error while trying to read file: " + path);
			e.printStackTrace();
		}
		
		return intervals;		
	}
	
	public static List<Interval> readParamsAsIntervals(String[] args) {
		List<Interval> intervals = new ArrayList<Interval>();
		
		List<String> invalidParams = new ArrayList<String>();
	
		for (int i = 0; i < args.length; i++) {
			if (args[i] == null || "".equals(args[i]) || !isInterval(args[i])) {
				invalidParams.add(args[i]);
			} else {
				intervals.add(new Interval(args[i]));
			}
		}
		
		if (!invalidParams.isEmpty()) {
			System.out.println(invalidParams.size() + " invalid parameters found:");
			for (String invalidParam : invalidParams) {
				System.out.println(invalidParam);
			}
		}
		
		return intervals;
	}
	
	public static List<Interval> inputIntervals() {
		List<Interval> intervals = new ArrayList<Interval>();
		
		Scanner scanner = new Scanner(System.in);

		System.out.println("You may add intervals by writing: [x,y]");
		System.out.println("x and y have to be integer values.");
		System.out.println("Putting in anything else will finish the input process.");
		
		System.out.print("Add Interval: ");
		String currentInput = scanner.next();
		
		while (currentInput != null && isInterval(currentInput)) {
			intervals.add(new Interval(currentInput));
			
			System.out.print("Add another Interval: ");
			currentInput = scanner.next();
		}
		
		return intervals;
	}
	
	public static boolean isInterval(String text) {
		return text.matches(PATTERN);
	}
}
