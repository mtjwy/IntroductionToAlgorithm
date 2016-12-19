package com.mtjwy.ww.IntroductionToAlgorithm._8_SortingInLinearTime;

import java.util.Arrays;


/**
 * Sort based algorithm: Suffix sort
 *       Create suffixes
 *       Sort suffixes
 *       find longest common prefix between adjacent suffixes in sorted order
 * 
 *  Bad input for this algorithm: (Run time: Quadratic)
 *      same letter repeated N times
 *      two copies of the same java codebase
 *      
 *      
 *  Linearithmic time: Manber-Myers MSD algorithm 
 *  (double chars that it looks at in each pass)
 *  
 *      
 *      
 *
 */

/**
 * suffix sort also good for keyword-context search:
 *      create suffixes
 *      sort suffixes
 *      binary search suffixes for keyword
 */
public class LongestRepeatedSubstring {
	
	public static String longestRepeatedSubstring(String s) {
		int N = s.length();
		
		String[] suffixes = new String[N];
		for (int i = 0; i < N; i++) {
			suffixes[i] = s.substring(i);
		}
		
		Arrays.sort(suffixes);
		
		String lrs = "";
		for (int i = 0; i < N - 1; i++) {
			int len = longestCommonPrefix(suffixes[i], suffixes[i + 1]);
			if (len > lrs.length()) {
				lrs = suffixes[i].substring(0, len);
			}
		}
		
		return lrs;
	}
	
	private static int longestCommonPrefix(String s, String t) {
		int len = 0;
		while(len < s.length() && len < t.length() && s.charAt(len) == t.charAt(len)) {
			len++;
		}	
		return len;
	}
	
	private static void test() {
		String s = "mytwinsssmytwinslskjfljeo";
		String lrs = longestRepeatedSubstring(s);
		System.out.println(lrs);
	}
	
	public static void main(String[] args) {
		test();
	}

}
