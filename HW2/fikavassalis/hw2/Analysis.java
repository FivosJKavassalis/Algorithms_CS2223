package fikavassalis.hw2;

import edu.princeton.cs.algs4.StdOut;

/**
 * Your goal is to output a table that looks like this:

N	Avg.	Inspectk
1	1.00	1
2	1.50	1,2
4	2.00	2,1,2,3
8	2.63	3,2,3,1,3,2,3,4
16	3.38	4,3,4,2,4,3,4,1,4,3,4,2,4,3,4,5
32	4.22	5,4,5,3,5,4,5,2,5,4,5,3,5,4,5,1,5,4,5,3,5,4,5,2,5,4,5,3,5,4,5,6
64	5.13	6,5,6,4,6,5,6,3,6,5,6,4,6,5,6,2,6,5,6,4,6,5,6,3,6,5,6,4,6,5,6,1,6,5,6,4,6,5,6,3,6,5,6,4,6,5,6,2,6,5,6,4,6,5,6,3,6,5,6,4,6,5,6,7


Bonus Question (1pt.)

See homework. Come up with a mathematical formula that computes this average value.

 */

public class Analysis {
	
	public Analysis() {
		System.out.println("N | Avg | Inspectk");
		
		for(int k=0; k<7; k++) {
			int N = (int) Math.pow(2, k);
			int[] vals = new int[N];
			int[] valsInspections = new int[N];
			int inspections = 0;
			
			for(int i=1; i<=N; i++) {
				vals[i-1] = i;
			}
			
			for(int i=1; i<=N; i++) {
				inspections = locate(vals, i);
				valsInspections[i-1] = inspections;
			}
			
			
			
			StdOut.println(N + " | " + averageCalculation(valsInspections) + " | " + outputInts(valsInspections));
		}
	}

	String outputInts(int[] arr) {
		String output = "";
		for(int i=0; i<arr.length; i++) {
			if(i==(arr.length-1))
				output += String.valueOf(arr[i]);
			else
				output += String.valueOf(arr[i]) + ", ";
		}
		return output;
	}
	
	int locate(int[] arrayOfInts, int target) {
		
		for(int i=0; i<arrayOfInts.length; i++) {
			// Binary Array Search Algorithm
			int low = 0;
			int high = arrayOfInts.length-1;
			int inspect = 0;
		
			while (low <= high) {
					int mid = (low+high)/2;
					
					int rc = arrayOfInts[mid] - target;
					inspect++;
					if (rc < 0) {
						// Increment mid by one; assumes array is sorted
						low = mid+1;
					} else if (rc > 0) {
						// Decrement mid by one; assumes array is sorted
						high = mid-1;
					} else {
						// Found target, return position
						return inspect;
					}
				
			}
		
		}
		
		// If target value is not found in array, then return null 
		return -1;
	}
	
	double averageCalculation(int[] arrayOfInts) {
		double sum = 0;
		for(int i=0; i<arrayOfInts.length; i++) {
			sum += arrayOfInts[i];
		}
		
		return sum / arrayOfInts.length;
	}
	
	public static void main(String[] args) {
		// Fill in here. Add whatever fields and helper methods you need to produce the above table.
		@SuppressWarnings("unused")
		Analysis analysis = new Analysis();
	}
}

