package fikavassalis.hw3;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * You will modify this class to support Q4 on the homework assignment.
 * 
 * @author heineman
 *
 * @param <Key>
 * @param <Value>
 */
public class BST<Key extends Comparable<Key>, Value> {

	Node root;               // root of the tree
	static int iPath = 0;
	static double miss = 0.0;
	static double comparisons = 0.0;
	
	class Node {
		Key    key;          
		Value  val;         
		Node   left, right;  // left and right subtrees
		int    N;            // number of nodes in subtree

		public Node(Key key, Value val, int N) {
			this.key = key;
			this.val = val;
			this.N = N;
		}
	}

	public boolean isEmpty() { return size() == 0; }

	/** Return number of key-value pairs in ST. */
	public int size()                { return size(root); }

	// Helper method that deals with "empty nodes"
	private int size(Node node) {
        if (node == null) return 0;
        
        return node.N;
    }

	// One-line method for containment. 
	public boolean contains(Key key) { return get(key) != null; }

	/** Search parent. */
	public Value get(Key key)        { return get(root, key); }
	
	private Value get(Node parent, Key key) {
		if (parent == null) {
			return null;
		}
		
		int cmp = key.compareTo(parent.key);
		
		if (cmp < 0) {
			miss++;
			comparisons++;
			return get(parent.left, key);
		}
		else if (cmp > 0) {
			miss++;
			comparisons++;
			return get(parent.right, key);
		}
		else {
			return parent.val;
		}
	}

	/** Invoke put on parent, should it exist. */
	public void put(Key key, Value val) {
		root = put(root, key, val);
	}

	private Node put(Node parent, Key key, Value val) {
		if (parent == null) return new Node(key, val, 1);
		iPath++;
		
		int cmp = key.compareTo(parent.key);
		if (cmp < 0) {
			parent.left  = put(parent.left,  key, val);
		}
		else if (cmp > 0) {
			parent.right = put(parent.right, key, val);
		}
		else {
			parent.val   = val;
		}
		
		parent.N = 1 + size(parent.left) + size(parent.right);
		return parent;
	}
	
	// Generate array of N random elements from 0 to 1.
	static Comparable[] generateData(int N) {
		
		Double[] integerValues = new Double[N];

		for (int j = 0; j < N; j++) {
			integerValues[j] = StdRandom.uniform(0.0, 1.0);
		}

		return integerValues;
	}
	
	public static void main(String[] args) {
		StdOut.println("N\tCn\tH-Ave\tM-Ave\tModel");
		
		BST<Double, Boolean> bst = new BST<Double, Boolean>();
		
		for (int n = 64; n <= 1024; n = n * 2) {
			Comparable[] arr = generateData(n);

			for(Comparable k: arr) {
				bst.put((Double) k, true);
			}
			
			for(Comparable k: arr) {
				bst.get((Double) k);
			}
			
			double hAve = comparisons;
			hAve /= n;
			hAve = Math.round(hAve * 100.0) / 100.0;
			
			double model = 1.39*(Math.log(n) / Math.log(2));
			model = Math.round(model * 100.0) / 100.0;
		
			miss = 0.0;
			for(Comparable k: arr) {
				bst.get(StdRandom.uniform(0.0, 1.0));
			}
			
			double mAve = miss;
			mAve /= n;
			mAve = Math.round(mAve * 100.0) / 100.0;
			
			StdOut.println(n + "\t" + iPath + "\t" + hAve + "\t" + mAve + "\t" + model);
			
			hAve = 0.0;
			mAve = 0.0;
			model = 0.0;
			comparisons = 0.0;
			miss = 0.0;
			
		}
	}
	
}