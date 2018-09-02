package fikavassalis.hw3;

import java.util.Random;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * The {@code Heap} class provides a static methods for heapsorting an array.
 * <p>
 * For additional documentation, see
 * <a href="https://algs4.cs.princeton.edu/24pq">Section 2.4</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class Heap {

	public static int exchanges = 0;
	public static int comparisons = 0;

	/**
	 * Rearranges the array in ascending order, using the natural order.
	 * 
	 * @param a
	 *            the array to be sorted
	 */
	public static void sort(Comparable[] a) {
		int n = a.length;
		exchanges = 0;
		comparisons = 0;

		// construct heap from the raw array of which we know nothing.
		for (int k = n / 2; k >= 1; k--) {
			sink(a, k, n);
		}

		// at this point, a has been turned into a heap.

		// From this point on, do not increment exchanges or comparisons.
		while (n > 1) {
			exch(a, 1, n--, false);
			sink(a, 1, n, false);

		}
	}

	/***************************************************************************
	 * Helper functions to restore the heap invariant.
	 ***************************************************************************/

	private static void sink(Comparable[] pq, int k, int n) {
		while (2 * k <= n) {
			int j = 2 * k;
			if (j < n && less(pq, j, j + 1))
				j++;
			if (!less(pq, k, j))
				break;
			exch(pq, k, j);
			k = j;
		}
	}
	
	private static void sink(Comparable[] pq, int k, int n, boolean b) {
		while (2 * k <= n) {
			int j = 2 * k;
			if (j < n && less(pq, j, j + 1, b))
				j++;
			if (!less(pq, k, j, b))
				break;
			exch(pq, k, j, b);
			k = j;
		}
	}

	/***************************************************************************
	 * Helper functions for comparisons and swaps. Indices are "off-by-one" to
	 * support 1-based indexing.
	 ***************************************************************************/
	private static boolean less(Comparable[] pq, int i, int j) {
	    comparisons++;
		return pq[i - 1].compareTo(pq[j - 1]) < 0;
	}

	private static void exch(Object[] pq, int i, int j) {
		exchanges++;		
		Object swap = pq[i - 1];
		pq[i - 1] = pq[j - 1];
		pq[j - 1] = swap;
	}
	
	private static boolean less(Comparable[] pq, int i, int j, boolean b) {
		if(b) {
			comparisons++;
		}
		return pq[i - 1].compareTo(pq[j - 1]) < 0;
	}

	private static void exch(Object[] pq, int i, int j, boolean b) {
		if(b) {
			exchanges++;
		}
		Object swap = pq[i - 1];
		pq[i - 1] = pq[j - 1];
		pq[j - 1] = swap;
	}

	// print array to standard output
	private static void show(Comparable[] a) {
		for (int i = 0; i < a.length; i++) {
			StdOut.println(a[i]);
		}
	}

	
	// GENERATE ARRAY OF N RANDOM ELEMENTS FROM 0.0 TO 1.0 
	static Comparable[] generateData(int N) {
		
//		Random ran = new Random();
		Comparable[] integerValues = new Comparable[N];

		for (int j = 0; j < N; j++) {
			integerValues[j] = StdRandom.uniform(0.0, 1.0);
		}
                                                                 
		return integerValues;
	}

	/**
	 * Reads in a sequence of strings from standard input; heapsorts them; and
	 * prints them to standard output in ascending order.
	 *
	 * @param args
	 *            the command-line arguments
	 */
	public static void main(String[] args) {

//		String[] a = StdIn.readAllStrings();
//		Heap.sort(a);
//		show(a);
		
		int comps = 0;
		int exch = 0;
		
		int T = 10;
		StdOut.println("N\tAveComp\tAveExch");
		for (int n = 16; n <= 512; n *= 2) {
			comps = 0;
			exch = 0;
			for (int t = 0; t < T; t++) {
				Comparable[] arr = generateData(n);
				Heap.sort(arr);
				if((comps<comparisons) && (exch<exchanges)) {
					comps = comparisons;
					exch = exchanges;
				}
				comparisons = 0;
				exchanges = 0;
				arr = null;
			}
			StdOut.println(n + "\t" + comps + "\t" + exch);
		}
	}
}
