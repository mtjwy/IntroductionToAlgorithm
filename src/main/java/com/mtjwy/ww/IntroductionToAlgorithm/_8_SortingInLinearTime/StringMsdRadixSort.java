package com.mtjwy.ww.IntroductionToAlgorithm._8_SortingInLinearTime;

import java.util.Arrays;

/**
 * Goal: Sort strings of variable-length
 * 
 * MSD no need to examine all characters.
 * Can be sublinear in input size!!!!
 * 
 *
 */
public class StringMsdRadixSort {
	private static final int R = 256;
	
	//Treat strings as if they had an extra char at end (smaller than any char)
	private static int charAt(String s, int d) {
		if (d < s.length()) {
			return s.charAt(d);
		} else {
			return -1;
		}
	}
	
	public static void sort(String[] a) {
		String[] aux = new String[a.length];
		sort(a, aux, 0, a.length-1, 0);
	}
	
	private static void sort(String[] a, String[] aux, int lo, int hi, int d) {
		if (hi <= lo) return;
		
		int[] count = new int[R + 2]; //added -1 to the end of each string
		for (int i = lo; i <= hi; i++) {
			count[charAt(a[i], d) + 2]++; //offset by 2, because we see as if char '-1' at the end of string
		}
		for (int r = 0; r <= R; r++) {
			count[r+1] += count[r];
		}
		for (int i = lo; i <= hi; i++) {
			aux[count[charAt(a[i], d) + 1]++] = a[i];
		}
		for (int i = lo; i <= hi; i++) {
			a[i] = aux[i - lo];//need to alien aux with a
		}
		
		//sort R subarrays recursively
		for(int r = 0; r < R; r++) {
			sort(a, aux, lo + count[r], lo + count[r+1] - 1, d + 1);
		}
	}
	
	private static void test() {
		String[] a = {"Jim", "John", "Jame", "Kim", "Adel", "Jose", "Bob", "Bill"};
		sort(a);
		System.out.println(Arrays.toString(a));
	}
	
	public static void main(String[] args) {
		test();
	}
	
	

}
