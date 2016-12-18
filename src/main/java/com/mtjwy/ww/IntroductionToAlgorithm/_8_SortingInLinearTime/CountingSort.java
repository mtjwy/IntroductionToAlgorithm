package com.mtjwy.ww.IntroductionToAlgorithm._8_SortingInLinearTime;

import java.util.Arrays;

/**
 * Linear time, in place sort.
 * 
 * Assumption: Keys are integers between 0 and R-1
 * Implication: Can use key as an array index.
 * 
 *
 */
public class CountingSort {
	
	public static void countingSort(int[] a, int R) {
		
		int[] count = new int[R + 1];
		
		//count
		for (int i = 0; i < a.length; i++) {
			count[a[i] + 1]++;   //index in count[] offset by one
		}
		
		//compute cumulates
		for (int r = 0; r < R; r++) {
			count[r + 1] += count[r];
		}
		
		//move items
		int[] aux = new int[a.length];
		for (int i = 0; i < a.length; i++) {
			aux[count[a[i]]] = a[i];
			count[a[i]]++;
		}
		
		
		//copy back
		for (int i = 0; i < a.length; i++) {
			a[i] = aux[i];
		}
		
	}
	
	
	private static void test() {
		int[] a = {2,4,1,8,5,9,0};
		countingSort(a, 10);
		System.out.println(Arrays.toString(a));
	}
	
	public static void main(String[] args) {
		test();
	}

}
