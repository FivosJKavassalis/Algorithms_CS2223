package fikavassalis.hw1;

import algs.hw1.TwiceSorted;

/**
 * Copy this class into your package, which must be USERID.hw1
 */
public class TwiceSorted_Solution extends TwiceSorted {
	
	/** Construct problem solution for given array. Do not modify this method. */
	public TwiceSorted_Solution(int[][] a) {
		super(a);
	}

	/** Construct problem solution using default sample array. Do not modify this method. */
	public TwiceSorted_Solution() {
		super();
	}

	/** 
	 * For this homework assignment, you need to complete the implementation of this
	 * method.
	 */
	@Override
	public int[] locate(int target) {
	// Do Binary Array Search in each row to locate target value	
		for(int r=0; r<length(); r++) {
			int low = 0;
			int high = length()-1;

			while (low <= high) {
				int mid = (low+high)/2;

				int rc = inspect(r,mid) - target;
				if (rc < 0) {
					low = mid+1;
				} else if (rc > 0) {
					high = mid-1;
				} else {
					return new int[] {r, mid};
				}
			}
		}	
		return null;

	}	
	/*	// Only look at the value in the upper right corner of the TwiceSorted array.
		if (inspect(0,length()-1) == target) {
			return new int[] {0, length()-1};
		}
		
		// if target value is not found in array, then return null 
		return null; 
	} */
		
	/** Be sure that you call your class constructor. Do not modify this method. */ 
	public static void main (String args[]) {
		System.out.println("Number of inspections:" + new TwiceSorted_Solution(sample).trial(512));
	}
	
	

}
