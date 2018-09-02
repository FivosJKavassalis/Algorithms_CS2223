package fikavassalis.hw1;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Standard Tower of Hanoi disk problem (https://en.wikipedia.org/wiki/Tower_of_Hanoi).
 */
public class TowerOfHanoi {

	/** Three separate stacks, each to hold up to four disks. Leave this alone. */
	static FixedCapacityStackOfInts stacks[] = new FixedCapacityStackOfInts[] {
		new FixedCapacityStackOfInts(4), new FixedCapacityStackOfInts(4), new FixedCapacityStackOfInts(4)
	};

	/** 
	 * Output the state in visible form. Modify this method.
	 * 
	 * You need to modify this method so it outputs state as follows:
	 * 
	 * Stack1: 4321
	 * Stack2:
	 * Stack3:
	 * 
	 * If, given this above state, you make a move from stack1 to stack3, then the output would be:
	 * 
	 * Stack1: 432
	 * Stack2:
	 * Stack3: 1
	 */
	public static void outputState() {
		
			StdOut.print("Stack 1: ");
			String state = "";
			for(int val: stacks[0]) {
				state = val + state;
			}
			StdOut.println(state);	
			
			StdOut.print("Stack 2: ");
			state = "";
			for(int val: stacks[1]) {
				state = val + state;
			}
			StdOut.println(state);	
			
			StdOut.print("Stack 3: ");
			state = "";
			for(int val: stacks[2]) {
				state = val + state;
			}
			StdOut.println(state);	
				
	}

	/**
	 * You must write this method. A move is allowed if the disk from the top of
	 * stack 'from' is smaller in size than the topmost disk on the top of stack 'to'.
	 * 
	 * @param from   stack containing the topmost disk to move
	 * @param to     stack representing the destination disk
	 */
	public static boolean move(int from, int to) {
	  // For case of destination stack being empty, just move the disk to that stack  
			if (stacks[to-1].isEmpty()) {
				 int movingDisk = stacks[from-1].pop();
				 stacks[to-1].push(movingDisk);
				 return true;
			}
	 // For case that the moving disk is smaller than the top disk
	 // (smaller disk in that stack thus far), move that disk to that stack 
		    int movingItem = stacks[from-1].pop(); // in order to be able to compare, we have to pop 
			int topItem = stacks[to-1].pop();      // them from their respective stacks 
			if(movingItem < topItem) {
				stacks[to-1].push(topItem); 
				stacks[to-1].push(movingItem);
				return true;
			}
			
	// if cases above fail, leaving state as is
			stacks[from-1].push(movingItem);
			stacks[to-1].push(topItem);
			
		return false;
	}
	
	/** You do not need to modify this method. */
	public static void main(String[] args) {
		/* Load up four disks on stack 1, so disk-4 is at the bottom and disk-1 is at the top. */
		stacks[0].push(4);
		stacks[0].push(3);
		stacks[0].push(2);
		stacks[0].push(1);
		
		int numMoves = 0;
		while (!stacks[0].isEmpty() || !stacks[1].isEmpty()) {
			outputState();
			StdOut.println ("Enter two stack numbers A B to move top disk on A to B. You win when all disks are on Stack 3.");
			int from = StdIn.readInt();
			int to = StdIn.readInt();
			
			System.out.println("Moving top disk from stack " + from + " to stack " + to);
			if (!move(from, to)) {
				StdOut.println ("That move is not allowed!");
			} else {
				numMoves++;
			}
		}
		
		StdOut.println ("Congratulations! You completed the puzzle in " + numMoves + " moves.");
	}                                                        
}
