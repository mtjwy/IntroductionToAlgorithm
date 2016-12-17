package com.mtjwy.ww.IntroductionToAlgorithm._7_Quicksort;

/**
 * Quicksort: 
 *            1. Quicksort with duplicate keys goes quadratic unless
 *            partitioning stops on equal keys!!!! 3-way partition deal with duplicate keys.
 *            2. shuffle input needed for performance guarantee.
 * 
 * Despite this slow worst-case running time, quicksort is often the best
 * practical choice for sorting because it is remarkably efficient on the
 * average: its expected running time is‚O(nlgn), and the constant factors
 * hidden in the‚ O(nlgn) notation are quite small. 
 * 
 * It also has the advantage of
 * sorting in place , and it works well even in virtual-memory environments.
 * 
 * Implementation referred to Robert Sedgewick's book Algorithms.
 */

public class QuickSort {
	
	/**
	 * Partition
	 * 
	 * Phase 1
	 *   Repeat until i and j pointers cross.
	 *      scan i from left to right so long as (a[i] < a[lo])
	 *      scan j from right to left so long as (a[j] > a[lo])
	 *      exchange a[i] with a[j]
	 *      
	 * Phase 2 
	 *   When pointers cross
	 *   Exchange a[lo] with a[j]
	 * 
	 * 
	 */
	
	private static int partition(Comparable[] a, int lo, int hi) {
		int i = lo;       // i offset to left by 1
		int j = hi + 1;   // j offset to right by 1
		Comparable pv = a[lo];
		
		//Phase 1
		while(true) {
			
			//find item on left to swap
			while(less(a[++i], pv)) {
				if (i == hi) {//guard
					break;
				}	
			}
			
			//find item on right to swap
			while(less(pv, a[--j])) {	
				if (j == lo) {//guard
					break;
				}
				
			}
			
			//check if pointers cross
			if (i >= j) {
				break;
			}
			
			//swap
			exch(a, i, j);
		}
		
		//Phase 2:
		
		//swap with partitioning item
		exch(a, lo, j);
		
		//retrn index of item now known to be in place
		return j;
	}
	
	private static void sort(Comparable[] a, int lo, int hi) {
		if (lo >= hi) {
			return;
		}
		
		int j = partition(a, lo, hi);
		sort(a, lo, j - 1);
		sort(a, j + 1, hi);
	}
	
	public static void sort(Comparable[] a) {
		//Shuffle array a   -- stay tuned
		
		sort(a, 0, a.length - 1);
	}
	
	
	
	
	
	
	
	
	/****************************************************************/
	
	//Quick Select : linear-time, use it if we don't need a full sort
	
	/**
	 * Select the k_th smallest item in array a
	 * @param a
	 * @param k
	 * @return the k_th item
	 */
	public static Comparable select(Comparable[] a, int k) {
		int lo = 0;
		int hi = a.length - 1;
		while(lo < hi) {
			int j = partition(a, lo, hi);
			if (j > k) {
				hi = j - 1;
			} else if (j < k) {
				lo = j + 1;
			} else {
				return a[k];
			}
		}
		
		return a[k];
	}
	
	/****************************************************************/
	
	
	
	
	
	
	
	
	
	private static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}
	
	private static void exch(Object[] a, int i, int j) {
		Object tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}
	
	
	private static void testPartition() {
		Integer[] a = { 3, 4, 5, 3, 2, 1, 0, 3, 6, 9 };
		int j = partition(a, 0, a.length - 1);
		System.out.println(j);
		for (Integer i : a) {
			System.out.print(i + " ");
		}
	}
	
	private static void testSort() {
		Integer[] a = { 3, 4, 5, 3, 2, 1, 0, 3, 6, 9 };
		sort(a);
		for (Integer i : a) {
			System.out.print(i + " ");
		}
	}
	
	private static void testSelect() {
		Integer[] a = { 3, 4, 5, 3, 2, 1, 0, 3, 6, 9 };
		System.out.println(select(a, 8));
		
	}
	
	public static void main(String[] args) {
		testPartition();
		System.out.println("\n\nResult of testing sort: ");
		testSort();
		System.out.println("\n\nResult of testing select: ");
		testSelect();
	}
	
}
