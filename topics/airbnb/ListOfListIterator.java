/**
List of List (2D List) Iterator

For example, Given 2d vector = [ [1,2], [3], [4,5,6] ]
By calling next repeatedly until hasNext returns false, the order of elements returned by next should be:
[1,2,3,4,5,6].
• boolean hasNext() return true if there is another element in the set
• int next() return the value of the next element in the array
• void remove()
	o remove the last element returned by the iterator.
	o That is, remove the element that the previous next() returned.
	o This method can be called only once per call to next(), otherwise an exception will be
	thrown.

Removes from the underlying collection the last element returned by this iterator (optional operation).
This method can be called only once per call to next(). The behavior of an iterator is unspecified if the
underlying collection is modified while the iteration is in progress in any way other than by calling this
method.
So the remove() method actually removes the element returned from the next().
*/

/**
Solution: Have a rowIter for all lists in 2dlist, and a colIter for every elm in curList.
* HasNext: the question says 'calling next repeatedly until hasNext returns false'.
	* And for iterator, we call hashNext before we call next(); So we check/assign next list in rowIter here.
	* If curList in colIter == null or don't hasNext elm, means we need another list in rowIter.
		* So check if rowIter.hasNext, if it does, assign nextList rowIter.next.iterator to colIter.
	* return true if colIter != null or hasNext elm;
* Next: the next function simply return colIter.next();
	* b/c, hasNext is called before next() is called.
	* and we assigned nextList to colIter in hasNext function when colIter is empty.
	* Also, if hasNext return false (colIter == null or !hasNext), then we won't call next() again.
* Remove: remove current elm, which is previous next() returned elm;
	* Question says 'This method can be called only once per call to next()';
	* That means only when next() called, we can call remove() 1 time. No more than 1 time.
	* No matter how many times next() is called, remove() can only be called 1 time right after a next() call;
	* Ex: next() remove(), next() remove() Correct;  
	* next() next() remove() remove() Wrong; remove() only allowed once after next();
	* It is the same property as Iterator lib in Java/C++ or others. So all we need to do is make sure colIter != null;
	* if (colIter != null) colIter.remove(); Other stuff taking care of in Java lib Iterator.

*/

import java.io.*;
import java.util.*;

public class ListOfListIterator {
	
	public class NestedListIterator implements Iterator<Integer> {

		Iterator<List<Integer>> rowIter;
		Iterator<Integer> colIter;

		public NestedListIterator(List<List<Integer>> list2d) {
			// iter with all lists in list2d.
			rowIter = list2d.iterator();
			// iter with no elm.
			colIter = Collections.emptyIterator();
		}

		/**
		 * Time: O(1)
		 */
		@Override
		public boolean hasNext() {
			// hasNext is called before call next
			// check if colIter = null or don't have Next elm, we go to nextList in rowIter.
			// iterates rowIter until nonEmpty list.
			if ((colIter == null || !colIter.hasNext()) && rowIter.hasNext()) {
				colIter = rowIter.next().iterator();
			}
			// return if not null and hasNext
			return (colIter != null && colIter.hasNext());
		}

		/**
		 * Time: O(1)
		 */
		@Override
		public Integer next() {
			// hasNext is called before call next, hasNext assign nextList iterator to colIter.
			return colIter.next();
		}

		/**
		 * Time: O(1)
		 */
		@Override
		public void remove() {
			// remove cur/prevNext elm, last next() getted elm.
			// should not be called more than next() calls. Only once per every next() call.
			// if colIter is null, get nextList iterator; (Skeptical if this statement is needed at all).
			if (colIter == null && rowIter.hasNext()) {
				colIter = rowIter.next().iterator();
			}
			// if not null, just remove the cur elm, which is the prev next() elm;
			if (colIter != null) {
				colIter.remove();
			}
		}
	}

	/**
	 * Follow up: Multiple layers of nested
	 * Leetcode 341. Flatten Nested List Iterator
	 */
	public class NestedIterator implements Iterator<Integer> {
	  
	  Stack<NestedInteger> stack = new Stack<>();
	    
	  public NestedIterator(List<NestedInteger> nestedList) {
	    for (int i = nestedList.size() - 1; i >= 0; i--) {
	      stack.push(nestedList.get(i));
	    }
	  }

	  @Override
	  public Integer next() {
	    return stack.pop().getInteger();
	  }

	  @Override
	  public boolean hasNext() {
	    while (!stack.isEmpty()) {
	      NestedInteger cur = stack.peek();
	      if (cur.isInteger()) {
	        return true;
	      }
	      stack.pop();
	      for (int i = cur.getList().size() - 1; i >= 0; i--) {
	        stack.push(cur.getList().get(i));
	      }
	    }
	    return false;
	  }
	}

	public static void main(String[] args) {
		ListOfListIterator obj = new ListOfListIterator();
		List<List<Integer>> list2d = new ArrayList<>();
		int[][] arrs = {{1,2}, {3}, {4,5,6}};
		for (int[] array : arrs) {
			List<Integer> list = new ArrayList<>();
			for (int n : array) {
				list.add(n);
			}
			list2d.add(list);
		}
		ListOfListIterator.NestedListIterator iter = obj.new NestedListIterator(list2d);
		int count = 0;
		while (iter.hasNext()) {
			System.out.println("next " + iter.next());
			count++;
			if (count % 3 == 0) {
				System.out.println("remove");
				iter.remove();
			}
		}

		list2d.forEach(list -> {
			list.forEach(n -> {
				System.out.print(n + ", ");
			});
			System.out.println();
		});

	}

}