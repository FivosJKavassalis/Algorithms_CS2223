package fikavassalis.hw4;

import java.util.LinkedList;

import algs.days.day19.Graph;
import algs.days.day20.DepthFirstSearch;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Question3 {
	static LinkedList<String[]> edges = new LinkedList<String[]>();
	static Graph g;       // Graph being searched.
	
	/** Each location gets its own unique id, mapped using the symbol table. */
	static SeparateChainingHashST<String, Integer> table = new SeparateChainingHashST<>();
	
	/** Each unique ID gets mapped to a location. This is the inverse map of table. */
	static SeparateChainingHashST<Integer, String> reverse = new SeparateChainingHashST<>();

	// Add whatever methods you see fit to add...
	
	
	public static void main(String[] args) {
		
		StdOut.println ("Enter locations on a single line, separated by spaces. Note 'home' is special and does not need to be included in list.");

		// add necessary code. See homework instructions...
		
		table.put("home", 0);
		reverse.put(0, "home");
		
		// Read nodes
		String nodes = StdIn.readLine();
		String[] node_arr = nodes.split("\\s+");
		
		for(int i=0; i<node_arr.length; i++) {
			if(table.contains(node_arr[i])) {
				StdOut.println(node_arr[i] + " already exists.");
			} else {
				table.put(node_arr[i], i+1);
				reverse.put(i+1, node_arr[i]);
			}
		}
		
		while (StdIn.hasNextLine()) {
		      String line = StdIn.readLine();
		      
		      if(line.equals("0")) {
		    	  break;
		      }
		      
		      String[] in = line.split("\\s+");
		      
		      if(in.length != 2) {
		    	  StdOut.println("You must input 2 nodes");
		      } else if(!table.contains(in[0]) || !table.contains(in[1])) {
		    	  StdOut.println("Invalid nodes");
		      } else {
		    	  edges.add(in);
		      }
		     
		}
		
		// Create graph
		g = new Graph(edges.size() + 1);
		
		for(int i=0; i<edges.size(); i++) {
			g.addEdge(table.get(edges.get(i)[1]), table.get(edges.get(i)[0]));
		}
		
		DepthFirstSearch node_search = new DepthFirstSearch(g, 0);
		boolean visit_possible = true;
		
		for(int i=0; i<edges.size() - 1; i++) {
			if(!node_search.marked(i)) {
				visit_possible = false;
				StdOut.println("Can't get from 'home' to " + reverse.get(i));
			}
		}
		
		if(visit_possible) {
			StdOut.println("Can get everywhere!");
		}
		
	}
}