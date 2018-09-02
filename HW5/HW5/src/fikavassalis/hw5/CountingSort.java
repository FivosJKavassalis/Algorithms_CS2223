package fikavassalis.hw5;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

// Proper Merge Sort from Sedgewick, 4ed
public class CountingSort {

	public static void sort(Integer[] values, int k) {
		// fill in...
		int n = values.length;
		
		Integer[] output = new Integer[n];
		
		Integer[] count = new Integer[k + 1];
		
		// Initialize output.
		for(int m=0; m<output.length; m++)
			output[m] = 0;
		
		// Initialize count.
		for(int m=0; m<count.length; m++)
			count[m] = 0;
		
		for(int number : values) {
			count[number]++;
		}
		
		int current = 0;
		
		// For every number in count.
		for(int i=0; i<count.length; i++) {
			int c = count[i];
			
			// For the number of times that the number occurs.
			for(int j=0; j<c; j++) {
				output[current] = i;
				current++;
			}
		}
		
		// Copy output to values.
		values = output;
	}

	/**
	 * Reads in a sequence of strings from standard input; selection sorts them; 
	 * and prints them to standard output in ascending order. 
	 */
	public static void main(String[] args) {
		
		// choose some k value
		int k = 50;  
		
		int current = 0;
		Integer[][] copies = new Integer[8][];
		
		StdOut.println("N\tTime");
		for (int N = 4096; N <= 524288; N *= 2) {
			Integer[] ar = new Integer[N];
			copies[current] = new Integer[N];
			for (int i = 0; i < ar.length; i++) {
				int val = StdRandom.uniform(k);
				ar[i] = val;
				copies[current][i] = val;
			}

			Stopwatch sw = new Stopwatch();
			sort(ar, k);
			
			StdOut.printf("%d\t%f\n", N, sw.elapsedTime());
			current++;
		}
		
		current = 0;
		
		StdOut.println();
		StdOut.println("Comparing against MergeSort:");
		StdOut.println("N\tTime");
		for(int N = 4096; N <= 524288; N *= 2) {
			
			Stopwatch sw = new Stopwatch();
			Merge.sort(copies[current]);
			
			StdOut.printf("%d\t%f\n", N, sw.elapsedTime());
			current++;
		}
		
		
		StdOut.println("Therefore the timing results demonstrate improvement for Counting Sort.");
	}

}
