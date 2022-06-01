package im;

/**
 * All intervals are considered closed intervals in between (and including) both endpoints.
 * An interval [x,y] is considered equal to [y,x].
 * When an interval object is being created, the endpoints will always be set correctly (meaning left endpoint <= right endpoint), 
 * regardless of the order given.
 * @author Mark
 */
public class Interval implements Comparable<Interval> {
	// Regex pattern used to decide if a string represents a valid interval or not
	private static final String PATTERN = "\\[-?\\d\\d*,-?\\d\\d*\\]";
	
	// The bounds of the interval
	private int leftEndpoint, rightEndpoint;
	
	public Interval(String interval) {
		if (!isInterval(interval)) {
			throw new IllegalArgumentException("The interval is not valid. Please use [x,y].");
		}
		
		String[] parts = interval.split(",");
		int x = Integer.parseInt(parts[0].substring(1));
		int y = Integer.parseInt(parts[1].substring(0, parts[1].length() - 1));
		setBounds(x, y);
	}
	
	public Interval(int x, int y) {
		setBounds(x, y);
	}
	
	public Interval(Interval toCopy) {
		setBounds(toCopy.getLeftEndpoint(), toCopy.getRightEndpoint());
	}

	/**
	 * An interval [x,y] is considered 'smaller' than [a,b] if x < a.
	 * y and b are not taken into account for this comparison. 
	 */
	@Override
	public int compareTo(Interval o) {		
		return leftEndpoint - o.getLeftEndpoint();
	}
	
	public int getLeftEndpoint() {
		return leftEndpoint;
	}
	
	public int getRightEndpoint() {
		return rightEndpoint;
	}
	
	/**
	 * Returns whether or not the given text represents a valid interval in the form of [x,y] with x and y being integer values.
	 */
	public static boolean isInterval(String text) {
		if (text == null) {
			return false;
		}
		return text.matches(PATTERN);
	}
	
	/**
	 * This method checks if another interval overlaps with this one.
	 * However, I did not end up using the method in the application, because I can check this with less conditions in an ordered list.
	 * The reason I left it anyway is because I use it to test my results in the JUnit tests.
	 * @param other
	 * @return true if the two intervals overlap
	 */
	public boolean overlaps(Interval other) {
		if (other.getLeftEndpoint() <= rightEndpoint && other.getRightEndpoint() >= leftEndpoint) {
			return true;
		}
		return false;
	}
	
	/**
	 * This method is the only way to set the bounds of the interval.
	 * It makes sure that the left bound is smaller than or equal to the right bound.
	 */
	public void setBounds(int x, int y) {
		if (x > y) {
			setLeftEndpoint(y);
			setRightEndpoint(x);
		} else {
			setLeftEndpoint(x);
			setRightEndpoint(y);		
		}
	}
	
	private void setLeftEndpoint(int min) {
		this.leftEndpoint = min;
	}
	
	private void setRightEndpoint(int max) {
		this.rightEndpoint = max;
	}
	
	public String toString() {
		return "[" + leftEndpoint + "," + rightEndpoint + "]";
	}
}
