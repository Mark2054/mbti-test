package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import im.Interval;
import im.IntervalMerger;

class IntervalMergerTest {

	@Test
	void testMergeEmptyList() {
		List<Interval> list = new ArrayList<Interval>();
		assertThrows(IllegalArgumentException.class, () -> {
			IntervalMerger.merge(list);
		});
	}
	
	@Test
	void testMergeOneElement() {
		List<Interval> list = new ArrayList<Interval>();
		list.add(new Interval(0, 4));
		
		List<Interval> resultList = IntervalMerger.merge(list);
		assertEquals(resultList.size(), 1);
		assertEquals(resultList.get(0).getLeftEndpoint(), 0);
		assertEquals(resultList.get(0).getRightEndpoint(), 4);
	}
	
	@Test
	void testMergeTwoElementsNoMerge() {
		List<Interval> list = new ArrayList<Interval>();
		list.add(new Interval(0, 4));
		list.add(new Interval(5, 9));
		
		List<Interval> resultList = IntervalMerger.merge(list);
		assertEquals(resultList.size(), 2);
		assertEquals(resultList.get(0).getLeftEndpoint(), 0);
		assertEquals(resultList.get(0).getRightEndpoint(), 4);
		assertEquals(resultList.get(1).getLeftEndpoint(), 5);
		assertEquals(resultList.get(1).getRightEndpoint(), 9);
	}
	
	@Test
	void testMergeTwoElementsMerged() {
		List<Interval> list = new ArrayList<Interval>();
		list.add(new Interval(0, 4));
		list.add(new Interval(2, 9));
		
		List<Interval> resultList = IntervalMerger.merge(list);
		assertEquals(resultList.size(), 1);
		assertEquals(resultList.get(0).getLeftEndpoint(), 0);
		assertEquals(resultList.get(0).getRightEndpoint(), 9);
	}
	
	@Test
	void testMultiElements() {
		List<Interval> list = new ArrayList<Interval>();
		list.add(new Interval(25, 30));
		list.add(new Interval(2, 19));
		list.add(new Interval(14, 23));
		list.add(new Interval(4, 8));
		
		List<Interval> resultList = IntervalMerger.merge(list);
		assertEquals(resultList.size(), 2);
		assertEquals(resultList.get(0).getLeftEndpoint(), 2);
		assertEquals(resultList.get(0).getRightEndpoint(), 23);
		assertEquals(resultList.get(1).getLeftEndpoint(), 25);
		assertEquals(resultList.get(1).getRightEndpoint(), 30);
	}
	
	@Test
	void test1000Elements() {
		List<Interval> list = new ArrayList<Interval>();
		for (int i = 0; i < 1000; i++) {
			int x = (int)(Math.random()*Integer.MAX_VALUE / 1000) + i * (Integer.MAX_VALUE / 2000);
			int y = (int)(Math.random()*Integer.MAX_VALUE / 1000) + i * (Integer.MAX_VALUE / 2000);
			list.add(new Interval(x, y));
		}
		
		List<Interval> resultList = IntervalMerger.merge(list);
		
		assertTrue(resultList.size() <= list.size());
				
		for (Interval firstInterval : resultList) {
			for (Interval secondInterval : resultList) {
				if (firstInterval.equals(secondInterval)) {
					continue;
				}
				assertFalse(firstInterval.overlaps(secondInterval));
			}
		}
	}
}
