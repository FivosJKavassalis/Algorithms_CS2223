package fikavassalis.hw2;

/**
 * This data type offers Bag-like behavior with the added constraint that it
 * tries to minimize space by keeping track of the count of each item in the
 * bag.
 *
 * Find the definition of MultiSet on Wikipedia
 * (https://en.wikipedia.org/wiki/Multiset)
 *
 * In all of the performance specifications, N refers to the total number of
 * items in the MultiSet while U refers to the total number of unique items.
 * 
 * Note that you will only have U nodes, one for each distinct item, and so U <=
 * N; however, you can't know in advance HOW many duplicates will exist, so in
 * the worst case, some computations will still depend upon N.
 * 
 * @param <Item>
 */
public class MultiSet<Item extends Comparable<Item>> {

	Node head;
	int N;

	/**
	 * You must use this Node class as part of a LinkedList to store the MultiSet
	 * items. Do not modify this class.
	 */
	class Node {
		private Item item;
		private int count;
		private Node next;
	}

	/** Create an empty MultiSet. */
	public MultiSet() {
		head = null;
	}
	// Create another constructor for MultiSet ???

	/**
	 * Initialize the MultiSet to contain the unique elements found in the initial
	 * list.
	 * 
	 * Performance is allowed to be dependent on N*N, where N is the number of total
	 * items in initial.
	 */
	public MultiSet(Item[] initial) {

		for (int i = 0; i < initial.length; i++) {
			this.add(initial[i]); // OR USE this (INSTEAD OF "set")? -- (USE ADD FUNCTION CREATED BELOW!)
		}

	}

	/**
	 * Return the number of items in the MultiSet.
	 * 
	 * Performance must be independent of the number of items in the MultiSet, or ~
	 * 1.
	 */
	public int size() {
		return N;
	}

	/**
	 * Determines equality with another MultiSet objects.
	 * 
	 * Assume U=number of unique items in self while UO=number of unique items in
	 * other.
	 * 
	 * Performance must be linearly dependent upon min(U1,U2)
	 */
	public boolean identical(MultiSet<Item> other) {
		if ((other == null) && (this != null))
			return false;

		if ((other != null) && (this == null))
			return false;

		Node a = head;
		Node b = other.head;

		if ((a.item != b.item) || (a.count != b.count))
			return false; // return false if different size.

		while ((a != null) && (b != null)) {

			/*
			 * Move on to next nodes if previous nodes hold the same item and the same count
			 */
			if (a.item == b.item && a.count == b.count) {
				a = a.next;
				b = b.next;
			} else {
				return false;
			}
		}

		/*
		 * If linked lists are identical, then 'a' and 'b' must be null here
		 */
		return (a == null && b == null);
	}

	/**
	 * Return an array that contains the items from the MultiSet.
	 * 
	 * Performance must be linearly dependent on N.
	 */
	public Item[] toArray() {
		Node tmp = head;

		Comparable[] newArray = new Comparable[N];

		int i = 0;
		while (tmp != null && i < N) {
			for (int j = 0; j < tmp.count; j++) {
				newArray[i] = tmp.item;
				i++;
			}
			tmp = tmp.next;
		}

		return (Item[]) newArray;
	}

	/**
	 * Add an item to the MultiSet.
	 * 
	 * Performance must be no worse than linearly dependent on N.
	 */
	public boolean add(Item it) {
		// student fills in
		N++;

		// is empty?
		if (head == null) {
			head = new Node();
			head.item = it;
			head.count = 1;
			head.next = null;
			return true;
		}

		// is the new item smaller than the head
		int rc = head.item.compareTo(it);

		if (rc > 0) {
			Node node = new Node();
			node.item = it;
			node.count = 1;
			node.next = head;
			head = node;
			return true;
		}

		Node here = head;
		Node next = here.next;
		while (here != null) {
			rc = here.item.compareTo(it); // check each time

			if (rc == 0) {
				here.count++;
				return true;
			}

			if (next == null) { // it > last one
				Node node = new Node();
				node.item = it;
				node.count = 1;
				node.next = null;
				here.next = node;
				return true;
			}

			// it > here, let me know if it < next
			int nrc = next.item.compareTo(it);
			if (nrc > 0) {
				// here ... it ... next
				Node node = new Node();
				node.item = it;
				node.count = 1;
				node.next = next;
				here.next = node;
				return true;
			}

			// it > next
			next = next.next;
			here = here.next;
		}

		return true;
	}

	/**
	 * Remove an item from the MultiSet; return false if not in the MultiSet to
	 * begin with, otherwise returns true on success.
	 * 
	 * Performance must be no worse than linearly dependent on N.
	 */
	public boolean remove(Item it) {
		
		Node tmp = head;
		Node prev = null;
		
		N--;
		
		while (tmp != null) {
			// check if "it" is same item that tmp points to
			if (tmp.item.equals(it)) { // if it is found

				if (prev == null) {
					if (tmp.count == 1)
						head = tmp.next; // acting like element does not have count !!!!!!!!!!!!!!!!!!!!!!!!!!!!
					else
						tmp.count = tmp.count - 1;
				} else {
					if (tmp.count == 1)
						prev.next = tmp.next;
					else
						tmp.count = tmp.count - 1;
				}
				return true;
			}

			prev = tmp;
			tmp = tmp.next; // advance to next one (if it exists)
		}

		N++;
		return false; // list is empty or element not found in the list
	}

	/**
	 * Returns the number of times item appears in the MultiSet.
	 * 
	 * If returns 0, then the item is not contained within this MultiSet.
	 * 
	 * Performance must be no worse than linearly dependent on U.
	 */
	public int multiplicity(Item it) {
		// student fills in
		Node tmp = head;

		while (tmp != null) {
			if (tmp.item.equals(it)) {
				return tmp.count;
			}

			tmp = tmp.next; // advance to the next one (if it exists)
		}

		return 0; // return 0 if the item is not contained in the MultiSet
	}

	/**
	 * Determine whether this MultiSet includes other MultiSet.
	 * 
	 * A MultiSet A includes a MultiSet B when: for all elements x in B with
	 * multiplicity mB(x), the multiplicity mA(x) in A is >= mB(x).
	 * 
	 * In degenerate case: 1. If this is empty, false is always returned. 2. If this
	 * is non-empty and other is empty, true is returned.
	 * 
	 * Performance must be linearly dependent on U + UO where U is the number of
	 * unique items in this and UO is the number of unique items in other.
	 */
	public boolean includes(MultiSet<Item> other) {
		Node tmp = head;
		Node otherTmp = other.head;

		// This is empty, return false
		if (tmp == null)
			return false;

		// This not empty, but other is empty
		if (tmp != null && other.head == null)
			return true;

		int count = 0; // Count the number of occurrences
		while (tmp != null && otherTmp != null) {
			int rc = tmp.item.compareTo(otherTmp.item);

			// This is greater than other, increment other
			if (rc > 0) {
				otherTmp = otherTmp.next;

				if (otherTmp == null)
					break;
			}

			// Other is greater that this, increment this
			if (rc < 0) {
				tmp = tmp.next;
			}

			// Items are identical
			if (rc == 0) {
				tmp = tmp.next;
				otherTmp = otherTmp.next;
				count++;
			}
		}

		if (count == other.N)
			return true;

		// Other is not included in this
		return false;
	}

	/**
	 * Return a MultiSet which represents intersection with existing MultiSet.
	 * 
	 * Performance must be linearly dependent on the number of unique items in both
	 * MultiSet objects, or in other words ~ U + UO where U is the number of items
	 * in this and MO is the number of items in other.
	 * 
	 * Consider definition of intersect on wikipedia page as to be the correct
	 * logic:
	 * 
	 * This is challenging.
	 */
	public MultiSet<Item> intersects(MultiSet<Item> other) {
		// student fills in

		MultiSet<Item> interSet = new MultiSet<Item>();
		Node tmp1 = this.head;
		Node tmp2 = other.head;

		while ((tmp1 != null) && (tmp2 != null)) {

			if ((tmp1.item.compareTo(tmp2.item)) < 0) {
				// keep going if items not the same
				tmp1 = tmp1.next;

			} else if (((tmp1.item.compareTo(tmp2.item)) > 0)) {
				// keep going if items not the same
				tmp2 = tmp2.next;
			} else {

				if (tmp1.count < tmp2.count) {
					while (tmp1.count != 0) {
						interSet.add(tmp1.item);// add element (count field will become 1)
						tmp1.count = tmp1.count - 1;
					}
				} // add to output
				else {
					while (tmp2.count != 0) {
						interSet.add(tmp2.item);// add element (count field will become 1)
						tmp2.count = tmp2.count - 1; // add to output
					}

					// interSet.add(tmp2.item); // if the same, add it to the output

				}
				
				
			}
			// keep searching
			tmp1 = tmp1.next;
			tmp2 = tmp2.next;
		}

		return interSet;
	}

	/**
	 * Return a MultiSet which represents union with existing MultiSet.
	 * 
	 * Performance must be linearly dependent on the number of items in both
	 * MultiSet objects, or in other words ~ UO + U where UO is the number of unique
	 * items in other and U is the number of unique items in this MultiSet.
	 * 
	 * Consider definition of intersect on wikipedia page as to be the correct
	 * logic:
	 * 
	 * This is challenging.
	 */
	public MultiSet<Item> union(MultiSet<Item> other) {

		MultiSet<Item> newSet = new MultiSet<Item>();

		Node tmp1 = this.head;
		Node tmp2 = other.head;

		while ((tmp1 != null) && (tmp2 != null)) {
			if ((tmp1.item.compareTo(tmp2.item)) < 0) {
				// add smaller to output
				while (tmp1.count != 0) {
					newSet.add(tmp1.item); // add will set count to 1, which is what we
					// want according to the definition
					tmp1.count = tmp1.count - 1;
				}

				tmp1 = tmp1.next; // Move to the next one

			} else if (((tmp1.item.compareTo(tmp2.item)) > 0)) {
				// add smaller to output
				while (tmp2.count != 0) {
					newSet.add(tmp2.item);// add element (count field will become 1)
					tmp2.count = tmp2.count - 1;
				}

				tmp2 = tmp2.next;
			} else {
				if (tmp1.count < tmp2.count) {
					while (tmp2.count != 0) {
						newSet.add(tmp2.item);// add element (count field will become 1)
						tmp2.count = tmp2.count - 1;
					}
				} // add to output
				else {
					while (tmp1.count != 0) {
						newSet.add(tmp1.item);// add element (count field will become 1)
						tmp1.count = tmp1.count - 1; // add to output
					}
				}
				tmp1 = tmp1.next;
				tmp2 = tmp2.next;

			}
		}

		while (tmp1 != null)

		{
			while (tmp1.count != 0) {
				newSet.add(tmp1.item);// add element (count field will become 1)
				tmp1.count = tmp1.count - 1;
			}

			tmp1 = tmp1.next;
		}

		while (tmp2 != null) {
			while (tmp2.count != 0) {
				newSet.add(tmp2.item);// add element (count field will become 1)
				tmp2.count = tmp2.count - 1;
			}

			tmp2 = tmp2.next;
		}

		return newSet;
	}

}
