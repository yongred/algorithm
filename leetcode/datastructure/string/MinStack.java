/**
155. Min Stack
Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.

push(x) -- Push element x onto stack.
pop() -- Removes the element on top of the stack.
top() -- Get the top element.
getMin() -- Retrieve the minimum element in the stack.
Example:
MinStack minStack = new MinStack();
minStack.push(-2);
minStack.push(0);
minStack.push(-3);
minStack.getMin();   --> Returns -3.
minStack.pop();
minStack.top();      --> Returns 0.
minStack.getMin();   --> Returns -2.
*/

/**
Solution 1: Create a double linkedlist Node with Min Node.
Ex:
push 3, 2, 3, 6, 5;
5 min 2;
6 min 2
3 min 2
2 min 2
3 min: 3, 

How to arrive:
* With a double linkedlist, we can remove and add topNode in O(1); 
* We need a Node topNode, to keep track of topNode val. 
* Also in topNode there is a topNode.minNode, is the min Node from parents till top node.
* When push, check if new val < minNode.val. Assign topNode.min = newNode if newMin found.
* Pop just pop normally, connecting prev node to null. Don't forget to check empty.
* getMin will just be topNode.min.val;
* Time: O(1) for functions.
* Space: O(n) for nodes.

----------------
Solution 2: just use 2 stacks. 1 for elms, 2 for mins at each level.
How to arrive:
* Same idea as solution1, just use minStack to keep track of min elm at each level.

----------------
Solution3: Keep prevMin under topNode. And keep a min int.
Ex: push 4, push 3, push 2, push 3, pop 3, pop 2, pop 3;
S: (min) MAX, 4, (min)4p, 3p, (min)3p, 2p, 3p
int min: MAXp, 4p, 3p, 2p, (from S) 3, (from S) 4;
How to arrive:
* We just need to know when the min changed, when the min change we keep prevMin under topNode. Update min int.
```
public void push(int x) {
	if (x <= min) {
		stack.push(min);
		min = x;
	}
	stack.push(x);
}
```
* When we pop, if pop val = curMin, then pop the next val which is prevMin, assign to min int.
*/

/**
	Solution 1: Create a double linkedlist Node with Min Node.
 */
class MinStack {
	public Node dummy;
	public Node topNode;

	public class Node {
		int val;
		Node next;
		Node prev;
		// min upto this node (inclusive).
		int min;

		public Node(int val) {
			this.val = val;
		}
	}

	/** initialize your data structure here. */
	public MinStack() {
		this.dummy = new Node(Integer.MIN_VALUE);
		this.topNode = null;
	}

	public void push(int x) {
		// first node
		if (isEmpty()) {
			this.topNode = new Node(x);
			// connect to dummy
			this.dummy.next = this.topNode;
			this.topNode.prev = this.dummy;
			// set minNode to topNode
			this.topNode.min = this.topNode.val;
		} else {
			// not first node, connect topNode to newNode, newNode becomes topNode.
			Node newNode = new Node(x);
			this.topNode.next = newNode;
			newNode.prev = this.topNode;
			this.topNode = newNode;
			// compare prevMin with curVal, see if newVal is newMin
			this.topNode.min = (x <= this.topNode.prev.min) ? x : this.topNode.prev.min;
		}
	}

	public void pop() {
		// if not empty pop the top val
		if (!isEmpty()) {
			// disconnect topNode from the list.
			this.topNode.prev.next = null;
			// topNode becomes prevNode.
			this.topNode = this.topNode.prev;
		}
	}

	public int top() {
		return this.topNode.val;
	}

	public int getMin() {
		return this.topNode.min;
	}

	public boolean isEmpty() {
		return (this.dummy.next == null) ? true : false;
	}
}


/**
 * Solution 2: just use 2 stacks. 1 for elms, 2 for mins at each level
 * Time: O(1)
 * Space: O(n)
 */
class MinStack {
  // store normal elms
  Stack<Integer> stack;
  // track min at each level of stack elm.
  Stack<Integer> minStack;
  /** initialize your data structure here. */
  public MinStack() {
    stack = new Stack<>();
    minStack = new Stack<>();
  }

  public void push(int x) {
    if (stack.isEmpty()) {
      stack.push(x);
      minStack.push(x);
    } else {
      // normal push elm
      stack.push(x);
      // determine at cur level, minVal
      if (x < minStack.peek()) {
        minStack.push(x);
      } else {
        // same min as prev
        minStack.push(minStack.peek());
      }
    }
    
  }

  public void pop() {
    if (stack.isEmpty()) {
      return;
    }
    stack.pop();
    minStack.pop();
  }

  public int top() {
    if (!stack.isEmpty()) {
      return stack.peek();
    } else {
      return -1;
    }
  }

  public int getMin() {
    if (!minStack.isEmpty()) {
      return minStack.peek();
    } else{
      return -1;
    } 
  }
}

/**
 * Solution 3: keep prevMin under topNode.
 * Time: O(1)
 * Space: O(n)
 */
class MinStack {

	Stack<Integer> stack;
	int min;
	/** initialize your data structure here. */
	public MinStack() {
		min = Integer.MAX_VALUE;
		stack = new Stack<>();
	}

	public void push(int x) {
		if (x <= min) {
			stack.push(min);
			min = x;
		}
		stack.push(x);
	}

	public void pop() {
		if (stack.empty()) {
			return;
		}
		if (min == stack.pop()) {
			min = stack.pop();
		}
	}

	public int top() {
		return stack.peek();
	}

	public int getMin() {
		return min;
	}
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */