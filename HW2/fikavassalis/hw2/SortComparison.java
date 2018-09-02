package fikavassalis.hw2;

import java.util.Random;

import algs.days.day06.Insertion;
import algs.days.day07.Merge;
import algs.days.day08.Quick;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StopwatchCPU;

// Template to use for Question 2 on Homework 2. Fix/update as needed.
public class SortComparison {

	// These are the proper values to use for HW2. if you want to try your code on smaller values,
	// then reduce these, say, to lowSize=5 and highSize=11
	static final int lowSize = 13;   // 2^13 = 8192
	static final int highSize = 19;	 // 2^19 = 524288
	
	
	/** Copied from StdRandom.shuffle(). Bringing here to avoid counting exchanges. */
    public static void shuffle(Object[] a) {
        if (a == null) throw new NullPointerException("argument array is null");
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = i + StdRandom.uniform(N-i);     // between i and N-1
            Object swap = a[i];
            a[i] = a[r];
            a[r] = swap;
        }
    }
	
    /**
     * Generates an array of N=2^k unique integers in random arrangement. 
     * 
     * @param k  power of 2 unique integers to be returned
     * @return array is shuffled.
     */
    static Integer[] generateUniqueData(int k) {
    	// Students must fill this in. This code is here to make sure we can run right from initial start of HW2
    	Random randomizer = new Random();
        Integer[] integerValues = new Integer[k];
        int arrayStatus = 0;
        int counter;
        while (arrayStatus < k) {
			counter = (int) Math.pow(2, randomizer.nextInt(k));
			integerValues[arrayStatus] = counter;
			for(int j=0; j<arrayStatus; j++) {
				if (integerValues[j] == counter) {
					counter = (int) Math.pow(2, randomizer.nextInt(k));
					integerValues[arrayStatus] = counter;
				}
			}
            arrayStatus++;
        }
        
        shuffle(integerValues);
        
    	return integerValues;
    }
    
    // Helper function for generateHighDuplicateData
	static int generateDuplicateData(Integer[] vals, int target) {
		
		for(int i=0; i<vals.length; i++) {
			int low = 0;
			int high = vals.length-1;
			int inspect = 0;
		
			while (low <= high) {
					int mid = (low+high)/2;
					
					int rc = vals[mid] - target;
					inspect++;
					if (rc < 0) {
						// Increment mid by one; assumes array is sorted
						low = mid+1;
					} else if (rc > 0) {
						// Decrement mid by one; assumes array is sorted
						high = mid-1;
					} else {
						return inspect;
					}
				
			}
		
		}
	
		return -1;
	}
    
    /**
     * Write a method that returns a shuffled integer array which has the following properties.
     * 
     *   + The size of the array is N=2^k and it contains (k+1) distinct values.
     *   
     *   + 1 appears one time
     *   + 2 appears two times
     *   + 3 appears four times
     *   + ...
     *   + k-1  appears 2^(k-2) times
     *   + k appears 2^(k-1) times
     *   + k+1 appears one time
     *  
     * The following is a valid pre-shuffled array for k=5:
     * 
     * [5,4,5,3,5,4,5,2,5,4,5,3,5,4,5,1,5,4,5,3,5,4,5,2,5,4,5,3,5,4,5,6] 
     *
     * @param k  power of 2 unique integers to be returned
     * @return array is shuffled
     */
	static Integer[] generateHighDuplicateData(int k) {
		// Students must fill this in. This code is here to make sure we can run right from initial start of HW2
		int N = (int) Math.pow(2, k);
		Integer[] vals = new Integer[N];
		Integer[] valsInts = new Integer[N];
		int inspections = 0;
		
		for(int i=1; i<=N; i++) {
			vals[i-1] = i;
		}
		
		for(int i=1; i<=N; i++) {
			inspections = generateDuplicateData(vals, i);
			valsInts[i-1] = inspections;
		}
		
		shuffle(valsInts);
		
    	return valsInts;
	}
	
	// These have been placed here so you can double check that each of the sorting algorithms
	// did indeed sort the data. Note: DO NOT CALL THIS FUNCTION WITHIN YOUR Stopwatch time period
	// when you are checking for the total elapsed time for the respective algorithm to complete
	static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
	static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }
	
	// You are responsible for writing code that updates these values. There are three values for s: 0,1,2
	//
	// unique[n][s][0] = best time for algorithm #s on a dataset of size n with n unique values
	// unique[n][s][1] = fewest number of exch operations for algorithm #s on a dataset of size n with n unique values
	// unique[n][s][2] = fewest number of less operations for algorithm #s on a dataset of size n with n unique values
	static double[][][] unique = new double[8][highSize-lowSize+1][3];
	
	// duplicates[n][s][0] = best time for algorithm #s on a dataset of size n with mostly duplicate values
	// duplicates[n][s][1] = fewest number of exch operations for algorithm #s on a dataset of size n with mostly duplicate values
	// duplicates[n][s][2] = fewest number of less operations for algorithm #s on a dataset of size n with mostly duplicate values
	static double[][][] duplicates = new double[8][highSize-lowSize+1][3];
	
	// Generates the table with the proper formatting. Use as is. DO NOT MODIFY THIS METHOD.
	public static void generateReport() {
		StdOut.println("Time Trials");
		StdOut.println("N\tMergeU\tQuickU\tQuickAU\t|\tMergeD\tQuickD\tQuickAD");
		for (int k = lowSize, idx = 0; k <= highSize; k++, idx++) {
			int n = (int) Math.pow(2, k);
			StdOut.printf("%2d\t%.3f\t%.3f\t%.3f\t|\t%.3f\t%.3f\t%.3f%n", 
					n,
					unique[idx][0][0], unique[idx][1][0], unique[idx][2][0], 
					duplicates[idx][0][0], duplicates[idx][1][0], duplicates[idx][2][0]);
		}
		
		StdOut.println();
		StdOut.println("Exch Results");
		StdOut.println("N\tMergeU\t\tQuickU\t\tQuickAU\t\t|\tMergeD\t\tQuickD\t\tQuickAD");
		for (int k = lowSize, idx = 0; k <= highSize; k++, idx++) {
			int n = (int) Math.pow(2, k);
			StdOut.printf("%2d\t%.3e\t%.3e\t%.3e\t|\t%.3e\t%.3e\t%.3e%n", 
					n,
					unique[idx][0][1], unique[idx][1][1], unique[idx][2][1],
					duplicates[idx][0][1], duplicates[idx][1][1], duplicates[idx][2][1]);
			
		}
		
		StdOut.println();
		StdOut.println("Less Results");
		StdOut.println("N\tMergeU\t\tQuickU\t\tQuickAU\t\t|\tMergeD\t\tQuickD\t\tQuickAD");
		for (int k = lowSize, idx = 0; k <= highSize; k++, idx++) {
			int n = (int) Math.pow(2, k);
			StdOut.printf("%2d\t%.3e\t%.3e\t%.3e\t|\t%.3e\t%.3e\t%.3e%n", 
					n,
					unique[idx][0][2], unique[idx][1][2], unique[idx][2][2],
					duplicates[idx][0][2], duplicates[idx][1][2], duplicates[idx][2][2]);
		}
	}

	// I suggest it might be useful to have a helper method such as this, which properly updates
	// the results[n][entry][xxx] values where xxx is 0 for time, 1 for exchange, and 2 for less.
	// 
	// The purpose of this method is to ensure you record the minimum time, the fewest number of
	// exchanges, and the fewest number of comparison operations, for the unique data set
	static void updateUniqueEntry(int trial, int n, int entry, long exch, long less, double time) {
		// Student fills in...
		switch (trial) {
		case 0:
			if(unique[n][entry][trial] >= 0.0 || time < unique[n][entry][trial]) {
				unique[n][entry][trial] = time;
			}
			break;
		case 1:
			if(unique[n][entry][trial] >= 0.0 || exch < unique[n][entry][trial]) {
				unique[n][entry][trial] = exch;
			}
			break;
		case 2:
			if(unique[n][entry][trial] >= 0.0 || less < unique[n][entry][trial]) {
				unique[n][entry][trial] = less;
			}
			break;
		default:
			System.out.println("ERROR!");
			return;
		}
				
	}
	
	static void updateDuplicatesEntry(int trial, int n, int entry, long exch, long less, double time) {
		// Student fills in
		switch (trial) {
		case 0:
			if(duplicates[n][entry][trial] >= 0.0 || time < duplicates[n][entry][trial]) {
				duplicates[n][entry][trial] = time;
			}
			break;
		case 1:
			if(duplicates[n][entry][trial] >= 0.0 || exch < duplicates[n][entry][trial]) {
				duplicates[n][entry][trial] = exch;
			}
			break;
		case 2:
			if(duplicates[n][entry][trial] >= 0.0 || less < duplicates[n][entry][trial]) {
				duplicates[n][entry][trial] = less;
			}
			break;
		default:
			System.out.println("ERROR!");
			return;
		}
	}
	
public static void main(String[] args) {
	
		int T = 3;
		StopwatchCPU watch; 
		Integer[] data;
		double time;
		
		for (int t = 0; t < T; t++) {
			System.out.printf("Trial %d ...%n", t);
			for (int k = lowSize, idx = 0; k <= highSize; k++, idx++) {
				
				// For each algorithm to be compared (MergeSort,QuickSort,QuickSortAlternate)
				// you must generate the data set to use, create a Stopwatch to measure the time, launch the 
				// sort request, record the elapsed time, validate that the array was actually sorted, and 
				// then update the appropriate entry (whether Unique or Duplicates) with the recorded new values...
				
				// Here is a sample using Insertion Sort. You will have SIX such blocks: THREE for unique data,
				// and THREE for highly duplicated data
				
				data = generateUniqueData(k);
				watch = new StopwatchCPU();
				try {
					Merge.sort(data);
					time = watch.elapsedTime();
					if (!isSorted(data)) {
						System.out.println("ERROR with Merge sort");
					}
					
					updateUniqueEntry (t, idx, 0, Merge.exchCount, Merge.lessCount, time);
				} catch (StackOverflowError e) {
					// stack overflow! Be sure to put this in just in case (hint....)
					System.out.printf("Stack Overflow (%d)!%n", k);
				}
				
				Merge.exchCount = 0;
				Merge.lessCount = 0;
				
				data = generateHighDuplicateData(k);
				watch = new StopwatchCPU();
				try {
					Merge.sort(data);
					time = watch.elapsedTime();
					if (!isSorted(data)) {
						System.out.println("ERROR with Merge sort");
					}
					
					updateDuplicatesEntry (t, idx, 0, Merge.exchCount, Merge.lessCount, time);
				} catch (StackOverflowError e) {
					// stack overflow! Be sure to put this in just in case (hint....)
					System.out.printf("Stack Overflow (%d)!%n", k);
				}
				
				
				
				data = generateUniqueData(k);
				watch = new StopwatchCPU();
				try {
					Quick.sort(data);
					time = watch.elapsedTime();
					if (!isSorted(data)) {
						System.out.println("ERROR with Quick sort");
					}
					
					updateUniqueEntry (t, idx, 1, Quick.exchCount, Quick.lessCount, time);
				} catch (StackOverflowError e) {
					// stack overflow! Be sure to put this in just in case (hint....)
					System.out.printf("Stack Overflow (%d)!%n", k);
				}
				
				Quick.exchCount = 0;
				Quick.lessCount = 0;
				
				data = generateHighDuplicateData(k);
				watch = new StopwatchCPU();
				try {
					Quick.sort(data);
					time = watch.elapsedTime();
					if (!isSorted(data)) {
						System.out.println("ERROR with Quick sort");
					}
					
					updateDuplicatesEntry (t, idx, 1, Quick.exchCount, Quick.lessCount, time);
				} catch (StackOverflowError e) {
					// stack overflow! Be sure to put this in just in case (hint....)
					System.out.printf("Stack Overflow (%d)!%n", k);
				}
				
				
				data = generateUniqueData(k);
				watch = new StopwatchCPU();
				try {
					QuickAlternate.sort(data);
					time = watch.elapsedTime();
					if (!isSorted(data)) {
						System.out.println("ERROR with Quick Alternate sort");
					}
					
					updateUniqueEntry (t, idx, 2, QuickAlternate.exchCount, QuickAlternate.lessCount, time);
				} catch (StackOverflowError e) {
					// stack overflow! Be sure to put this in just in case (hint....)
					System.out.printf("Stack Overflow (%d)!%n", k);
				}
				
				QuickAlternate.exchCount = 0;
				QuickAlternate.lessCount = 0;
				
				data = generateHighDuplicateData(k);
				watch = new StopwatchCPU();
				try {
					QuickAlternate.sort(data);
					time = watch.elapsedTime();
					if (!isSorted(data)) {
						System.out.println("ERROR with Quick Alternate sort");
					}
					
					updateDuplicatesEntry (t, idx, 2, QuickAlternate.exchCount, QuickAlternate.lessCount, time);
				} catch (StackOverflowError e) {
					// stack overflow! Be sure to put this in just in case (hint....)
					System.out.printf("Stack Overflow (%d)!%n", k);
				}
				
			}
		}
		
		generateReport();
	}

}