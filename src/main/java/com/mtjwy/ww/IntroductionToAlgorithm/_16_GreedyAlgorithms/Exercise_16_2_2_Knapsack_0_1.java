package com.mtjwy.ww.IntroductionToAlgorithm._16_GreedyAlgorithms;

public class Exercise_16_2_2_Knapsack_0_1 {
	/**
	 * 16.2-2 Give a dynamic-programming solution to the 0-1 knapsack problem
	 * that runs in O(n*W) time, where n is the number of items and W is the
	 * maximum weight of items that the thief can put in his knapsack.
	 */

	/**
	 * optimal-substructure property:
	 * 
	 * consider the most valuable load that weighs at most W pounds. If we remove
	 * item j from this load, the remaining load must be the most valuable load
	 * weighing at most W - wj that the thief can take from the n - 1 original items
	 * excluding j.
	 * 
	 * define V[i, w] to be the max_value can get for items  1,2, . . . , i and maximum weight w
	 * 
	 *    V[k, w] = V[k-1, w]   if wk > w   (case 1: Item k can’t be part of the solution)
	 *            = max{V[k-1,w], V[k-1, w-wk] + vk}   if wk <= w   (case2: solution either contains item k or not)
	 * 
	 */
	
	public int maxValueKnapsack_0_1(Item[] items, int W) {
		if (items == null || items.length == 0) {
			return 0;
		}
		int[][] V = new int[items.length + 1][W + 1];
		int offset = 1;
		for(int i = 1; i < V.length; i++) {
			for (int w = 1; w < V[0].length; w++) {
				if (items[i - offset].weight > w) {
					V[i][w] = V[i - 1][w];
				} else {
					V[i][w] = Math.max(V[i - 1][w], V[i - 1][w - items[i - offset].weight] + items[i - offset].value);
				}
			}
		}
		
		printKnapsackedItems(V, items);
		
		return V[V.length - 1][V[0].length - 1];
		
	}
	
	private void printKnapsackedItems (int[][] V, Item[] items) {
		int offset = 1;
		int i = V.length - 1;
		int w = V[0].length - 1;
		while (i > 0 && w > 0) {
			if (V[i][w] != V[i - 1][w]) {
				System.out.println("items[" + (i - offset) + "] : " + items[i - offset]);
				w = w - items[i - offset].weight;
				i = i - 1;
			} else {
				i = i - 1;
			}
		}
	}
	
	private static class Item {
		int weight;
		int value;
		Item(int weight, int value) {
			this.weight = weight;
			this.value = value;
		}
		
		public String toString() {
			return "{weight = " + weight + ", value = " + value + "}";
		}
	}
	
	
	public static void main(String[] args) {
		Exercise_16_2_2_Knapsack_0_1 instance = new Exercise_16_2_2_Knapsack_0_1();
		Item[] items = {
						new Item(2, 3),
						new Item(3, 4),
						new Item(4, 5),
						new Item(5, 6)
						};
		int res = instance.maxValueKnapsack_0_1(items, 10);
		
		System.out.println("Total knapsacked value = " + res);
	}
	

}
