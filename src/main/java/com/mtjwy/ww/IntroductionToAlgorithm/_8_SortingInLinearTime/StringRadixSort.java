package com.mtjwy.ww.IntroductionToAlgorithm._8_SortingInLinearTime;

import java.util.Arrays;

import org.omg.PortableServer.ServantActivator;

/**
 * Goal: sort an array of fixed length string.
 * 
 * Least significant digit first (process char from right to left)
 * 
 * Radix Sort:
 *    For each digit i, sort array A on digit i with a stable sort. 
 *
 */
public class StringRadixSort {
	
	public static void stringRadixSort(String[] a, int W) {
		int R = 256; // 256 English char
		String[] aux = new String[a.length];

		for (int d = W - 1; d >= 0; d--) {
			int[] count = new int[R + 1];
			
			for(int i = 0; i < a.length; i++) {
				count[a[i].charAt(d) + 1]++;
			}
			
			for(int r = 0; r < R; r++) {
				count[r + 1] += count[r];
			}
			
			for(int i = 0; i < a.length; i++) {
				aux[count[a[i].charAt(d)]] = a[i];
				count[a[i].charAt(d)]++;
			}
			
			for(int i = 0; i < a.length; i++) {
				a[i] = aux[i];
			}
		}
	}
	
	public static void test() {
		String[] a = {"Jime", "John", "Jame", "Kimy", "Adel", "Jose", "Bobe", "Bill"};
		stringRadixSort(a, 4);
		System.out.println(Arrays.toString(a));
	}
	
	public static void main(String[] args) {
		test();
	}

}
