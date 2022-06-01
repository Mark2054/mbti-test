package im;

import java.util.ArrayList;
import java.util.List;

public class IntervalMerger {
	public static List<Interval> merge(List<Interval> intervals) {
		List<Interval> resultList = new ArrayList<Interval>();
		
		intervals.sort(null); // Use the implemented compare method for Intervals to sort by the lower value
		
		Interval current = null;
		for (Interval interval : intervals) {
			
			if (current == null) {
				current = new Interval(interval.getMin(), interval.getMax());
				continue;
			}
			
			if (interval.getMin() <= current.getMax()) {
				if (interval.getMax() > current.getMax()) {
					current.setMax(interval.getMax());
				}
			} else {
				resultList.add(current);
				current = new Interval(interval.getMin(), interval.getMax());
			}
		}
		
		if (current != null) {
			resultList.add(current);
		}
		
		return resultList;
	}
}
