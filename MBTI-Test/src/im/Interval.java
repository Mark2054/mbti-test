package im;

/**
 * All intervals are considered closed intervals in between (and including) min and max.
 * An interval [x,y] is considered equal to [y,x].
 * When an interval object is being created, min and max will always be set correctly (meaning min <= max), regardless of the order given.
 * @author Mark
 */
public class Interval implements Comparable<Interval> {
	private int min, max;
	
	public Interval(String interval) {
		String[] parts = interval.split(",");
		int x = Integer.parseInt(parts[0].substring(1));
		int y = Integer.parseInt(parts[1].substring(0, parts[1].length() - 1));

		if (x > y) {
			int newX = y;
			y = x;
			x = newX;
		}
		
		setMin(x);
		setMax(y);
	}
	
	public Interval(int x, int y) {
		if (x > y) {
			int newX = y;
			y = x;
			x = newX;
		}
		
		setMin(x);
		setMax(y);
	}

	/**
	 * An interval [x,y] is considered 'smaller' than [a,b] if x < a.
	 * y and b are not taken into account for this comparison. 
	 */
	@Override
	public int compareTo(Interval o) {		
		return min - o.getMin();
	}
	
	public int getMax() {
		return max;
	}
	
	public int getMin() {
		return min;
	}
	
	/**
	 * This method checks if another interval overlaps with this one.
	 * However, I did not end up using the method in the task, because I can check this with less conditions in an ordered list.
	 * @param other
	 * @return true if the two intervals overlap
	 */
	public boolean overlaps(Interval other) {
		if (other.getMin() >= min && other.getMin() <= max) {
			return true;
		}
		if (other.getMax() <= max && other.getMax() >= min) {
			return true;
		}
		return false;
	}
	
	public void setMax(int max) {
		this.max = max;
	}
	
	public void setMin(int min) {
		this.min = min;
	}
	
	public String toString() {
		return "[" + min + "," + max + "]";
	}
}
