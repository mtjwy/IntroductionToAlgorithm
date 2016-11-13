package com.mtjwy.ww.IntroductionToAlgorithm._16_GreedyAlgorithms;

import java.util.Arrays;
import java.util.Comparator;


public class Exercise_16_1_5_MaxProfitActivityScheduling {

	/**
	 * 16.1-5 Consider a modification to the activity-selection problem in which
	 * each activity ai has, in addition to a start and finish time, a value vi.
	 * The objective is no longer to maximize the number of activities
	 * scheduled, but instead to maximize the total value of the activities
	 * scheduled. That is, we wish to choose a set A of compatible activities such
	 * that ∑(ak ∈ A) vk is maximized. Give a polynomial-time algorithm for this
	 * problem.
	 * 
	 */
	
	/**
	 * Similar to exercise 16.1.1
	 * but here we maximize value instead of size.
	 */
	
	/**
	   Denote the value of the optimal solution for the set Sij by val[i, j].
	   
	   val[i,j] = 0   if Sij = ∅
	            = max {val[i,k] + val[k,j] + vk}, if Sij != ∅
	            ak ∈ Sij
	  
	  
	 */
	
	private static class Interval {
		static final ByFinishTimeComparator BY_FINISH_TIME = new ByFinishTimeComparator();
		int start;
		int finish;
		int val;
		Interval(int start, int finish, int val) {
			this.start = start;
			this.finish = finish;
			this.val = val;
		}
		public String toString() {
			return "{start = " + start + ", finish = " + finish + ", val = " + val + "}";
		}
		
		// define a comparator for interval, sort by finished time
		private static class ByFinishTimeComparator implements Comparator<Interval> { 
			public int compare(Interval i1, Interval i2) {
				if (i1.finish == i2.finish) {
					return i1.start - i2.start;
				}
				return i1.finish - i2.finish;
			}
		}
	}
	
	public void activitySelectorDynamicProgramming(Interval[] intervals) {
		//add two fictitious activities
		Interval[] intervalsCopy = new Interval[intervals.length + 2];
		intervalsCopy[0] = new Interval(-1, 0, 0);
		System.arraycopy(intervals, 0, intervalsCopy, 1, intervals.length);
		intervalsCopy[intervalsCopy.length - 1] = 
				new Interval(Integer.MAX_VALUE, Integer.MAX_VALUE, 0);
		
		Arrays.sort(intervalsCopy, Interval.BY_FINISH_TIME);
		
		int icLen = intervalsCopy.length;
		int[][] v = new int[icLen][icLen];
		int[][] act = new int[icLen][icLen];
		
		for(int ll=2; ll <= icLen - 1; ll++) {
			for(int i = 0; i <= icLen - 1 - ll; i++) {
				int j = i + ll;
				v[i][j] = 0;
				int k = j - 1;
				while (intervalsCopy[i].finish < intervalsCopy[k].finish) {
					if (intervalsCopy[i].finish <= intervalsCopy[k].start
							&& intervalsCopy[k].finish <= intervalsCopy[j].start
							&& v[i][k] + intervalsCopy[k].val + v[k][j] > v[i][j]) {
						v[i][j] = v[i][k] + intervalsCopy[k].val + v[k][j];
						act[i][j] = k;
					}
					k = k - 1;
				}
			}	
		}
		System.out.println("A max value set of mutually compatible activities: ");
		System.out.println("value = " + v[0][icLen - 1]);
		System.out.println("The set contains: ");
		printActivities(v, act, 0, icLen - 1, intervalsCopy);	
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
				                new Interval(3, 9, 10),
				                new Interval(5, 9, 20),
				                new Interval(6, 10, 200),
				                new Interval(8, 11, 3),
				                new Interval(8, 12, 4),
				                new Interval(2, 14, 10),
				                new Interval(12, 16, 6),
				                new Interval(1, 4, 5),
				                new Interval(3, 5, 1),
				                new Interval(0, 6, 1),
				                new Interval(5, 7, 0)
				                };
		Exercise_16_1_5_MaxProfitActivityScheduling instance 
		    = new Exercise_16_1_5_MaxProfitActivityScheduling();
		instance.activitySelectorDynamicProgramming(intervals);
		
	}
	
	
}
