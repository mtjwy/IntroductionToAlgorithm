package com.mtjwy.ww.IntroductionToAlgorithm._16_GreedyAlgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class _16_1_AnActivitySelectionProblem {
	/**
	 Problem description: P415 
	 Optimal substructure:
	    denote Sij  --- set of activities start after ai finishes,
	                    and finish before activity aj starts.
	           Aij  --- a maximum set of mutually compatible activities in Sij
	           ak   --- some activity in Aij
	    By including ak in an optimal solution, two subproblems left:
	       finding mutually compatible activities in Sik
	       finding mutually compatible activities in Skj
	       
	    Let Aik = Aij ∩ Sik
	        Akj = Aij ∩ Skj
	        
	    Thus Aij = Aik ∪ {ak} ∪ Akj
	    
	    So size |Aij| = |Aik| + 1 + |Akj|
	 
	 Optimal substructure suggests that we might solve the activity-selection problem by 
	 dynamic programming.
	     
	     denote c[i,j] --- size of an optimal solution for Sij
	     
	     c[i,j] = c[i,k] + 1 + c[k,j]
	     
	     which ak to choose? 
	     
	     We would have to examine all activities in Sij
	     
	     c[i,j] = 0   if Sij = ∅
	            = max {c[i,k] + c[k,j] + 1}, if Sij != ∅
	            ak ∈ Sij
	  
	  But we can do better.
	  Making the greedy choice:
	     consider only the greedy choice 
	     -– Choose an activity that leaves the resources available 
	        for as many activities as possible
	     -- One of the chosen activities must be the first one to finish
	     -- Choose the activity ai ∈ Sij with the earliest fi
	     -- Since the activities are sorted in monotonically increasing order 
	        by fi, the greedy choice is activity a1
         -- The remaining subproblem is to find activities that start after 
            a1 finishes (compatible activities to a1)
	     
	  Recursive greedy algorithm:
	  
	  // Return a maximum-size set of mutually compatible activities in S_k
      // Assumption: n input activities are sorted by monotonically increasing
      // finish time
	  algorithm recursive_activity_selector (
		  s, // Input: Array containing start time of activities
		  f, // Input: Array containing finish time of activities
		  k, // Input: Index k to define the subproblem S_k to be solved
		  n // Input: Size of the original problem
		  )
	  {
		  m = k + 1;
		  // Find the first activity in S_k to finish
	      while ( m <= n and s[m] < f[k] )
	         m = m + 1;
	      if ( m <= n )
	         return ( union ( {a_m}, recursive_activity_selector ( s, f, m, n ) ) );
	      return ( NULL );
       }
       
       – The implementation of the algorithm will add a fictitious activity a0 with f0 = 0
       - The initial call to solve the entire problem is
                       recursive_activity_selector ( s, f, 0, n );

       
       Iterative greedy algorithm:
       
       Greedy_Activity_Selector(s, f) {
          n = s.length
          A = {a1}
          k = 1
          for m = 2 to n
             if s[m] >= f[k]
                A = A ∪ {am}
                k = m
          return A         
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
	
	
	
	/**
	 * Recursive greedy
	 * 
	 * @param intervals
	 * @return a maximum-size set of mutually compatible activities
	 */
	public List<Interval> maxSizeActivitySelectorRecursive(Interval[] intervals) {
		
		Interval[] intervalsCopy = new Interval[intervals.length + 1];
		
		//add a fictitious interval, for recursive 
		intervalsCopy[0] = new Interval(-1, 0);
		System.arraycopy(intervals, 0, intervalsCopy, 1, intervals.length);

		//sort interval
        Arrays.sort(intervalsCopy, new IntervalComparator());
        
		List<Interval> res = new ArrayList<>();
		recursiveActivitySelector(intervalsCopy, 0, intervalsCopy.length - 1, res);
		return res;
	}
	
	
	private void recursiveActivitySelector(Interval[] intervals, int k, int n, List<Interval> res) {
		int m = k + 1;
		while (m <= n && intervals[m].start < intervals[k].finish) {
			m = m + 1;
		}
		if (m <= n) {
			res.add(intervals[m]);
			recursiveActivitySelector(intervals, m, n, res);
		} 
	}
	
	
	
	/**
	 * iterative greedy algorithm
	 *        
	 * @param intervals
	 * @return a maximum-size set of mutually compatible activities
	 */
	public List<Interval> greedyActivitySelector(Interval[] intervals) {
 	   Interval[] intervalsCopy = new Interval[intervals.length];
 	   System.arraycopy(intervals, 0, intervalsCopy, 0, intervals.length);
 	   Arrays.sort(intervalsCopy, new IntervalComparator());
 	   
 	   List<Interval> res = new ArrayList<>();
 	   res.add(intervalsCopy[0]);
 	   for(Interval intl : intervalsCopy) {
 		   if (intl.start > res.get(res.size() - 1).finish) {
 			   res.add(intl);
 		   }
 	   }
 	   return res;
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
		_16_1_AnActivitySelectionProblem instance = new _16_1_AnActivitySelectionProblem();
		List<Interval> res = instance.maxSizeActivitySelectorRecursive(intervals);
		System.out.println("Recursive result:");
		System.out.println(res);
		
		res = instance.greedyActivitySelector(intervals);
		System.out.println("Interative result:");
		System.out.println(res);
	}
}
	


