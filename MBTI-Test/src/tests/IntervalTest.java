package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import im.Interval;

class IntervalTest {

	@Test
	void testIntervalCreationFromString() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Interval("Guten Abend");
		});
		
		Interval interval = new Interval("[-8,5]");
		assertEquals(interval.getLeftEndpoint(), -8);
		assertEquals(interval.getRightEndpoint(), 5);		

		interval = new Interval("[4,-7]");
		assertEquals(interval.getLeftEndpoint(), -7);
		assertEquals(interval.getRightEndpoint(), 4);		
	}
	
	@Test
	void testIntervalCreationFromInterval() {
		Interval oldInterval = new Interval(2, 8);
		Interval newInterval = new Interval(oldInterval);
		assertEquals(newInterval.getLeftEndpoint(), 2);
		assertEquals(newInterval.getRightEndpoint(), 8);
	}
	
	@Test
	void testIsInterval() {
		assertFalse(Interval.isInterval(null));
		assertFalse(Interval.isInterval(""));
		assertFalse(Interval.isInterval("Guten Tag"));
		assertFalse(Interval.isInterval("[]"));
		assertFalse(Interval.isInterval("[12]"));
		assertFalse(Interval.isInterval("[,]"));
		assertFalse(Interval.isInterval("[1,]"));
		assertFalse(Interval.isInterval("[,2]"));
		assertFalse(Interval.isInterval("[1.2]"));
		assertFalse(Interval.isInterval("[a,b]"));
		assertFalse(Interval.isInterval("[1,b]"));
		assertFalse(Interval.isInterval("[a,2]"));
		assertFalse(Interval.isInterval("[a,2]"));
		assertFalse(Interval.isInterval("1,2]"));
		assertFalse(Interval.isInterval("[1,2"));
		assertTrue(Interval.isInterval("[1,2]"));
		assertTrue(Interval.isInterval("[2,1]"));
		assertTrue(Interval.isInterval("[-14124,235654]"));
		assertTrue(Interval.isInterval("[523423,-15536]"));
	}
	
	@Test
	void testOverlaps() {
		Interval interval1 = new Interval(1,5);
		Interval interval2 = new Interval(3,7);
		Interval interval3 = new Interval(6,6);
		Interval interval4 = new Interval(-5,0);
		assertTrue(interval1.overlaps(interval2));
		assertFalse(interval1.overlaps(interval3));
		assertFalse(interval1.overlaps(interval4));
		assertTrue(interval2.overlaps(interval1));
		assertTrue(interval2.overlaps(interval3));
		assertFalse(interval2.overlaps(interval4));
		assertFalse(interval3.overlaps(interval1));
		assertTrue(interval3.overlaps(interval2));
		assertFalse(interval3.overlaps(interval4));
		assertFalse(interval4.overlaps(interval1));
		assertFalse(interval4.overlaps(interval2));
		assertFalse(interval4.overlaps(interval3));
	}
	
	@Test
	void testSetBounds() {
		Interval interval = new Interval(0, 0);
		interval.setBounds(5, 9000);
		assertEquals(interval.getLeftEndpoint(), 5);
		assertEquals(interval.getRightEndpoint(), 9000);
		interval.setBounds(9000, 5);
		assertEquals(interval.getLeftEndpoint(), 5);
		assertEquals(interval.getRightEndpoint(), 9000);
	}

}
