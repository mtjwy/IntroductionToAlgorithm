package com.mtjwy.ww.IntroductionToAlgorithm._16_GreedyAlgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;


public class Exercise_16_1_4_MinLectureHallsActivityScheduling {
	/**
	 * Suppose that we have a set of activities to schedule among a large number
	 * of lecture halls, where any activity can take place in any lecture hall.
	 * We wish to schedule all the activities using as few lecture halls as
	 * possible. Give an efficient greedy algorithm to determine which activity
	 * should use which lecture hall.
	 * 
	 * (This problem is also known as the interval-graph coloring problem. We
	 * can create an interval graph whose vertices are the given activities and
	 * whose edges connect incompatible activities. The smallest number of
	 * colors required to color every vertex so that no two adjacent vertices
	 * have the same color corresponds to finding the fewest lecture halls
	 * needed to schedule all of the given activities.)
	 */

	/**
	 * Greedy algorithm
	 * 
	 * For lecture hall 1, schedule a1, then choose from the remaining
	 * activities for next activity that has the earliest start time and is
	 * compatible with a1, then choose next compatible, choose next compatible ...
	 * ...until we have no more compatible activities to choose.
	 * 
	 * If there are remaining non-scheduled activities, we get a new lecture
	 * hall and schedule for it. Do it in the same greedy way as above.
	 * 
	 * If needed, get new lecture hall and schedule ....
	 * 
	 * In the above algorithm, we need to loop through activities many times,
	 * how to do better?
	 * 
	 * Do it in one loop. 
	 * 
	 * Put a1 in hall1. For a2, if compatible with a1, put a2
	 * in hall1, if not compatible, get a new hall, put a2 in hall2. For a3,
	 * need to compare a3 with each last finished activity in previous halls,
	 * #NOTE: here we need to find the compatible hall with the earliest finished time#
	 * if find such a compatible hall, put a3 in it, if not, get new hall for a3 ...
	 * 
	 * How to find compatible hall with the earliest finished time fast?
	 * 
	 * If maintain halls in list, we need O(N) time to find earliest finished time.
	 * If maintain halls in heap, we can find in O(1) time, but insert to heap is O(lgN) time
	 * 
	 */
	
	private static class Interval {
		
		static final Comparator<Interval> BY_START_TIME = new ByStartTime();
		
		int start;
		int finish;

		Interval(int start, int finish) {
			this.start = start;
			this.finish = finish;
		}

		public String toString() {
			return "{start = " + start + ", finish = " + finish + "}";
		}
		
		// define a comparator for interval, sort by starting time
		private static class ByStartTime implements Comparator<Interval> {
			public int compare(Interval i1, Interval i2) {
				if (i1.start == i2.start) {
					return i1.finish - i2.finish;
				}
				return i1.start - i2.start;
			}
		}
		
	}
	
	private static class Hall {
		static final Comparator<Hall> BY_FINISH_TIME = new ByFinishTime();
		
		int finish;
		List<Interval> intervals;
		
		Hall() {
			finish = 0;
			intervals = new ArrayList<Interval>();
		}
		
		void addInterval(Interval interval) {
			intervals.add(interval);
			finish = Math.max(finish, interval.finish);
		}
		
		public String toString() {
			return intervals.toString();
		}
		
		// define a comparator for interval, sort by starting time
		private static class ByFinishTime implements Comparator<Hall> {
			public int compare(Hall h1, Hall h2) {
				return h1.finish - h2.finish;
			}
		}	
	}
	
	public List<Hall> minLectureHalls(Interval[] intervals) {	
		if (intervals == null || intervals.length == 0) {
			return new ArrayList<Hall>();
		}
		
		Interval[] intervalsCopy = new Interval[intervals.length];
		System.arraycopy(intervals, 0, intervalsCopy, 0, intervals.length);
		
		Arrays.sort(intervalsCopy, Interval.BY_START_TIME);
		
		PriorityQueue<Hall> minHeap = new PriorityQueue<>(Hall.BY_FINISH_TIME);
		Hall hall = new Hall();
		hall.addInterval(intervalsCopy[0]);
		minHeap.offer(hall);
		
		for (int i = 1; i < intervalsCopy.length; i++) {
			if (intervalsCopy[i].start >= minHeap.peek().finish) {
				hall = minHeap.poll();	
			} else {
				hall = new Hall();	
			}
			hall.addInterval(intervalsCopy[i]);
			minHeap.offer(hall);
		}	
		return Arrays.asList(minHeap.toArray(new Hall[0]));
	}
	
	
	public static void main(String[] args) {
		Interval[] intervals = {
				                new Interval(3, 9),
				                new Interval(5, 9),
				                new Interval(6, 10),
				                new Interval(8, 11),
				                new Interval(8, 12),
				                new Interval(2, 14),
				                new Interval(12, 16),
				                new Interval(1, 4),
				                new Interval(3, 5),
				                new Interval(0, 6),
				                new Interval(5, 7)
				                };
		Exercise_16_1_4_MinLectureHallsActivityScheduling instance 
		    = new Exercise_16_1_4_MinLectureHallsActivityScheduling();
		List<Hall> res = instance.minLectureHalls(intervals);
		printResult(res);
		
		System.out.println();
		
		Interval[] intervals2 = {
                new Interval(0, 30),
                new Interval(5, 10),
                new Interval(15, 20) 
                };
		res = instance.minLectureHalls(intervals2);
		printResult(res);
	}
	
	private static void printResult(List<Hall> res) {
		int i = 0;
		for (Hall hall : res) {
			System.out.print("Hall_" + (i++) + ":");
			System.out.println(hall);
		}
	}

}
