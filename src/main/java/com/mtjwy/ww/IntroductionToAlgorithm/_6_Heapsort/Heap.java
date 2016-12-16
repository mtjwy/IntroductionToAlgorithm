package com.mtjwy.ww.IntroductionToAlgorithm._6_Heapsort;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Heapsort's running time is O(n*lgn), and it is in place
 * 
 * Heap is an array that we can view as a nearly complete binary tree.
 * 
 * 
 * Implementation referred to Robert Sedgewick's book Algorithms.
 *
 */
public class Heap<T> {
	private T[] hp; // store keys at indices 1 to n
	private int n; // number of keys in heap
	private Comparator<T> comparator; // optional Comparator

	@SuppressWarnings("unchecked")
	public Heap(int initCapacity) {
		hp = (T[]) new Object[initCapacity + 1];
		n = 0;
	}

	public Heap() {
		this(1);
	}
	
	/**********************************************************/
	
	@SuppressWarnings("unchecked")
	public Heap(int initCapacity, Comparator<T> comparator) {
		this.comparator = comparator;
		hp = (T[]) new Object[initCapacity + 1];
		n = 0;
	}
	
	public Heap(Comparator<T> comparator) {
		this(1, comparator);
	}
	
	@SuppressWarnings("unchecked")
	public Heap(T[] keys) {
		
		hp = (T[]) new Object[keys.length + 1];
		n = 0;
		for (int i = 0; i < keys.length; i++) {
			insert(keys[i]);
		}
		
		assert isMaxHeap();
	}
	
	
	/**********************************************************/
	
	

	/**
	 * Promotion in a heap
	 * 
	 * Exchange key in child with key in parent. Repeat until heap order
	 * restored.
	 */
	private void swim(int k) {
		while (k > 1 && less(k / 2, k)) {
			exch(k / 2, k);
			k = k / 2;
		}
		
	}

	/**
	 * Insert: Add node at end, then swim it up
	 */
	public void insert(T x) {
		if (n >= hp.length - 1) {
			resize(2 * hp.length);
		}

		hp[n + 1] = x;
		n++;
		swim(n);
		
		
	}

	/**
	 * Demotion in a heap
	 * 
	 * Exchange key in parent with key in larger child. Repeat until heap order
	 * restored.
	 */
	private void sink(int k) {
		while (2 * k <= n) {
			int j = 2 * k;
			if (j < n && less(j, j + 1)) {
				j = j + 1;
			}
            if (!less(k, j)) {
            	break;
            }
			exch(k, j);
			k = j;
		}
	}

	/**
	 * Delete the maximum in a heap
	 * 
	 * Exchange root with node at end, then sink it down
	 */
	public T delMax() {
		if (isEmpty()) throw new NoSuchElementException("Heap underflow");
		T max = hp[1];
		exch(1, n);
		n--;
		sink(1);
		hp[n + 1] = null;
		if ((n > 0) && (n == (hp.length - 1) / 4)) {
			resize(hp.length / 2);
		}
		assert isMaxHeap();
		return max;
	}
	
	
	
	public boolean isEmpty() {
		return n == 0;
	}
	
	public int size() {
		return n;
	}
	
	

	@SuppressWarnings("unchecked")
	private boolean less(int i, int j) {
		if (comparator == null) {
			return (((Comparable<T>) hp[i]).compareTo(hp[j])) < 0;
		} else {
			return comparator.compare(hp[i], hp[j]) < 0;
		}
	}

	private void exch(int i, int j) {
		T tmp = hp[i];
		hp[i] = hp[j];
		hp[j] = tmp;
	}

	@SuppressWarnings("unchecked")
	private void resize(int capacity) {
		T[] newHp = (T[]) new Object[capacity];
		for (int i = 1; i <= n; i++) {
			newHp[i] = hp[i];
		}

		hp = newHp;
	}
	
	private boolean isMaxHeap() {
		return isMaxHeap(1);
	}
	
	//is subtree rooted at k a max heap?
	private boolean isMaxHeap(int k) {
		if (k > n) {
			return true;
		}
		
		int left = 2 * k;
		int right = 2 * k + 1;
		if (left <= n && less(k, left)) return false;
		if (right <= n && less(k, right)) return false;
		
		return isMaxHeap(left) && isMaxHeap(right);
	}
	
	
	public static void main(String[] args) {
		Integer[] nums = {3, 5, 1, 2, 6, 9, 8, 7, 0, 4};
		Heap<Integer> inst = new Heap<>(nums);
		while (!inst.isEmpty()) {
			System.out.println(inst.delMax());
		}
	}

}
