package im;

import java.util.ArrayList;
import java.util.List;

public class IntervalMerger {
	public static List<Interval> merge(List<Interval> intervals) throws IllegalArgumentException {
		List<Interval> resultList = new ArrayList<Interval>();
		
		// Step 0: Make sure the list isn't empty
		if (intervals.isEmpty()) {
			throw new IllegalArgumentException("The list should was empty.");
		}
		
		// Step 1: Sort the list
		// A new list with the same elements is created, because otherwise, the order of the list given as a parameter would be changed,
		// which would be unexpected behaviour for this method.
		List<Interval> sortedIntervals = new ArrayList<Interval>(intervals);
		sortedIntervals.sort(null); // Use the implemented compare method for Intervals to sort by the left endpoint value.
				
		// Step 2: Go through the now sorted list and merge overlapping intervals
		Interval currentMergedInterval = null;
		for (Interval sortedInterval : sortedIntervals) {
			
			// This is for the first iteration only and will create the first interval for the result 
			// (though the bounds might not be final yet since no merging has happened)
			if (currentMergedInterval == null) {
				currentMergedInterval = new Interval(sortedInterval);
				continue;
			}
			
			// As long as the next interval in the sorted list starts before the current result interval ends, their bounds will
			// be merged in the current result interval.
			if (sortedInterval.getLeftEndpoint() <= currentMergedInterval.getRightEndpoint()) {
				if (sortedInterval.getRightEndpoint() > currentMergedInterval.getRightEndpoint()) {
					currentMergedInterval.setBounds(currentMergedInterval.getLeftEndpoint(), sortedInterval.getRightEndpoint());
				}
			} else {
				// Once we get to an interval that is outside of the current result interval, that result interval is finished.
				// There can't be any more intervals that overlap with it, because the list is ordered by the left endpoint, 
				// meaning none of the remaining intervals will start inside of it (or even left of it) ever again.
				// It gets added to the list and then a new one is created from the sorted interval that was outside of the old one's bounds.
				resultList.add(currentMergedInterval);
				currentMergedInterval = new Interval(sortedInterval);
			}
		}
		
		// The final result interval still needs to be added to the list once the loop is finished.
		resultList.add(currentMergedInterval);
		
		return resultList;
	}
}
