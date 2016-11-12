package com.mtjwy.ww.IntroductionToAlgorithm._16_GreedyAlgorithms;

import java.util.Arrays;
import java.util.Comparator;

public class Exercise_16_1_1_ActivitySelectionDynamicProgramming {
	/**
	 * 16.1-1 Give a dynamic-programming algorithm for the activity-selection
	 * problem, based on recurrence (16.2). Have your algorithm compute the
	 * sizes c[i,j] as defined above and also produce the maximum-size subset of
	 * mutually compatible activities.Assume that the inputs have been sorted as
	 * in equation (16.1). Compare the running time of your solution to the
	 * running time of GREEDY-ACTIVITY-SELECTOR.
	 */

	/**
	 * Answer:
	 * 
	 * If activity k is in Sij , then we must have i < k < j, which means that 
	 * j − i ≥ 2, but we must also have that fi ≤ sk and fk ≤ sj . If we start k
	 * at j − 1 and decrement k, we can stop once k reaches i, but we can also
	 * stop once we find that fi < fk.
	 * 
	 * We create two fictitious activities, a0 with f0 = 0 and a_n+1 with s_n+1 = ∞ 
	 * We are interested in a maximum-size set A_0,n+1 of mutually compatible
	 * activities in S_0.n+1
	 * 
	 * We'll use table c[0..n+1, 0..n+1] to record size, table act[0..n+1, 0..n+1]
	 * to record the activity k that we choose to put into Aij
	 * 
	 * Bottom up DP
	 * 
	 * We fill the tables in according to increasing difference j - i, denoted by ll.
	 * Since Sij = 0 if j - i < 2, we initialize c[i,j] = 0 for all i == j,
	 * and c[i,i+1] = 0 
	 * 
	 * Then look at k from j - 1 to i + 1
	 * 
	 * time cost: O(n^3)
	 * 
	 */
	private static class Interval {
		int start;
		int finish;
		Interval(int start, int finish) {
			this.start = start;
			this.finish = finish;
		}
		public String toString() {
			return "{start = " + start + ", finish = " + finish + "}";
		}
	}
	
	// define a comparator for interval, sort by finished time
	private static class IntervalComparator implements Comparator<Interval> { 
		public int compare(Interval i1, Interval i2) {
			if (i1.finish == i2.finish) {
				return i1.start - i2.start;
			}
			return i1.finish - i2.finish;
		}
	}
	
	public void activitySelectorDynamicProgramming(Interval[] intervals) {
		//add two fictitious activities
		Interval[] intervalsCopy = new Interval[intervals.length + 2];
		intervalsCopy[0] = new Interval(-1, 0);
		System.arraycopy(intervals, 0, intervalsCopy, 1, intervals.length);
		intervalsCopy[intervalsCopy.length - 1] = 
				new Interval(Integer.MAX_VALUE, Integer.MAX_VALUE);
		
		Arrays.sort(intervalsCopy, new IntervalComparator());
		
		int icLen = intervalsCopy.length;
		int[][] c = new int[icLen][icLen];
		int[][] act = new int[icLen][icLen];
		
		for(int ll=2; ll <= icLen - 1; ll++) {
			for(int i = 0; i <= icLen - 1 - ll; i++) {
				int j = i + ll;
				c[i][j] = 0;
				int k = j - 1;
				while (intervalsCopy[i].finish < intervalsCopy[k].finish) {
					if (intervalsCopy[i].finish <= intervalsCopy[k].start
							&& intervalsCopy[k].finish <= intervalsCopy[j].start
							&& c[i][k] + 1 + c[k][j] > c[i][j]) {
						c[i][j] = c[i][k] + 1 + c[k][j];
						act[i][j] = k;
					}
					k = k - 1;
				}
			}	
		}
		System.out.println("A max size set of mutually compatible activities: ");
		System.out.println("size = " + c[0][icLen - 1]);
		System.out.println("The set contains: ");
		printActivities(c, act, 0, icLen - 1, intervalsCopy);	
	}
	
	private void printActivities(int[][] c, int[][] act, int i, int j, Interval[] intervals) {
		if (c[i][j] > 0) {
			System.out.println(intervals[act[i][j]]);
			int k = act[i][j];
			printActivities(c, act, i, k, intervals);
			printActivities(c, act, k, j, intervals);
		}	
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
		Exercise_16_1_1_ActivitySelectionDynamicProgramming instance 
		    = new Exercise_16_1_1_ActivitySelectionDynamicProgramming();
		instance.activitySelectorDynamicProgramming(intervals);
		
	}
	

}
