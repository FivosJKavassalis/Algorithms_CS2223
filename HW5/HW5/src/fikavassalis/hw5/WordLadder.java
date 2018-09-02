package fikavassalis.hw5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import algs.days.day12.SeparateChainingHashST;
import algs.days.day20.Stack;

/**
 * Modify this class for problem 1 on HW5.
 */
public class WordLadder {

	/**
	 * Represent the mapping of (uniqueID, 4-letter word)
	 */
	static SeparateChainingHashST<String, Integer> table = new SeparateChainingHashST<String, Integer>();
	static SeparateChainingHashST<Integer, String> reverse = new SeparateChainingHashST<Integer, String>();

	/**
	 * Determine if the two same-sized words are off by just a single character.
	 */
	public static boolean offByOne(String w1, String w2) {
		int diffChars = 0;
		for (int i = 0; i < w1.length(); i++) {
			if (w1.charAt(i) != w2.charAt(i)) { // if this character from word 1 does not equal the character from word
												// 2
				diffChars += 1;
			}
		}

		if (diffChars != 1)
			return false;

		return true;
	}

	/**
	 * Main method to execute.
	 * 
	 * From console, enter the start and end of the word ladder.
	 */
	public static void main(String[] args) throws FileNotFoundException {

		// Use this to contain all four-letter words that you find in dictionary
		AVL<String> avl = new AVL<String>();

		// create a graph where each node represents a four-letter word.
		// Also construct avl tree of all four-letter words.
		// Note: you will have to copy this file into your project to access it, unless
		// you
		// are already writing your code within the SedgewickAlgorithms4ed project.
		int i = 0;
		String filePath = System.getProperty("user.dir");

		Scanner sc;

		if ((new File(filePath + "/fikavassalis")).exists()) {
			sc = new Scanner(new File(System.getProperty("user.dir") + "/fikavassalis/hw5/words.english.txt"));
		} else if ((new File(filePath + "/src").exists())) {
			sc = new Scanner(new File(System.getProperty("user.dir") + "/src/fikavassalis/hw5/words.english.txt"));
		} else {
			sc = new Scanner(new File("words.english.txt"));
		}
		while (sc.hasNext()) {
			String s = sc.next();
			if (s.length() == 4) {
				table.put(s, i);
				reverse.put(i, s);
				avl.insert(s);
				i++;
			}
		}
		sc.close();

		// now construct graph, where each node represents a word, and an edge exists
		// between
		// two nodes if their respective words are off by a single letter. Hint: use the
		// keys() method provided by the AVL tree.
		// fill in here...

		Queue<String> treeKeys = (Queue) avl.keys();
		Graph g = new Graph(treeKeys.size());

		for (int j = 0; j < i; j++) {
			for (int k = 0; k < i; k++) {
				if (reverse.get(j).length() == 4 && reverse.get(k).length() == 4) {
					if (offByOne(reverse.get(j), reverse.get(k))) {
						g.addEdge(k, j);
					}
				}
			}
		}

		StdOut.println("Enter word to start from (all in lower case):");
		String start = StdIn.readString().toLowerCase();
		StdOut.println("Enter word to end at (all in lower case):");
		String end = StdIn.readString().toLowerCase();

		// need to validate that these are both actual four-letter words in the
		// dictionary
		if (!avl.contains(start)) {
			StdOut.println(start + " is not a valid word in the dictionary.");
			System.exit(-1);
		}
		if (!avl.contains(end)) {
			StdOut.println(end + " is not a valid word in the dictionary.");
			System.exit(-1);
		}

		// Once both words are known to exist in the dictionary, then create a search
		// that finds shortest distance (should it exist) between start and end.
		// be sure to output the words in the word ladder, IN ORDER, from the start to
		// end.

		// fill in here...

		BreadthFirstSearch node_search = new BreadthFirstSearch(g, table.get(start));

		StdOut.println(node_search.getDistTo()[table.get(end)]);

		Stack<Integer> path = (Stack<Integer>) node_search.pathTo(table.get(end));

		try {
			
			while(!path.isEmpty()) {
				StdOut.print(reverse.get(path.pop()) + " ");
			}
		
		} catch (NullPointerException e) {                             
			
			StdOut.print("no word ladder");
			
		}

	}
}