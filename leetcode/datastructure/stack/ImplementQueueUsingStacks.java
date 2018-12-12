/**
232. Implement Queue using Stacks
Implement the following operations of a queue using stacks.

push(x) -- Push element x to the back of queue.
pop() -- Removes the element from in front of queue.
peek() -- Get the front element.
empty() -- Return whether the queue is empty.
Example:

MyQueue queue = new MyQueue();

queue.push(1);
queue.push(2);  
queue.peek();  // returns 1
queue.pop();   // returns 1
queue.empty(); // returns false
Notes:

You must use only standard operations of a stack -- which means only push to top, peek/pop from top, size, and is empty operations are valid.
Depending on your language, stack may not be supported natively. You may simulate a stack by using a list or deque (double-ended queue), as long as you use only standard operations of a stack.
You may assume that all operations are valid (for example, no pop or peek operations will be called on an empty queue).
*/

/**
Solution: Using 2 stacks. Origin and Reversed elms.
Solution: Using 2 stacks. Origin and Reversed elms.
How to arrive:
* Queue is FIFO, Stack is LIFO, Their different in terms of Queue pop and peek from bottom (first added), Stack from top (last added).
* But if our stack pushed to another stack, all the elms are reversed, so last elm on Top of reversed Stack. So peek() and Pop() will be the same as a Queue.
* So, we will have 2 stacks, Origin and Reversed. Push elms to Origin, Pop and Peek from Reversed.
* When Pop and Peek, if Reversed is Empty, Origin will pop all the elms it has to Reversed.
* This will take amortized time of O(1) for peek and pop. Because we all fill the Reversed when it is empty. If user pop every time, only 1 to fill. Use need to use up all last popped elms in order for us to fill again. So, O(1),O(1),O(1)...O(n) ... O(1), O(1).. O(n);
* Time: O(1);
* Space: O(n) for elms;

*/

class ImplementQueueUsingStacks {
  // use 2 stacks
  Stack<Integer> origin;
  Stack<Integer> reversed;
  
  /** Initialize your data structure here. */
  public ImplementQueueUsingStacks() {
    origin = new Stack<>();
    reversed = new Stack<>();
  }

  /** Push element x to the back of queue. */
  public void push(int x) {
    origin.push(x);
  }

  /** Removes the element from in front of queue and returns that element. */
  public int pop() {
    // amortized O(1); don't need to refill every time.
    if (!empty()) {
      // last origin pushed elms are all popped.
      if (reversed.isEmpty()) {
        // push all origin elms sofar to reversed
        while (!origin.isEmpty()) {
          reversed.push(origin.pop());
        }
      }
      return reversed.pop();
    } else {
      return -1;
    }
  }

  /** Get the front element. */
  public int peek() {
    // amortized O(1); don't need to refill every time.
    if (!empty()) {
      // last origin pushed elms are all popped.
      if (reversed.isEmpty()) {
        // push all origin elms sofar to reversed
        while (!origin.isEmpty()) {
          reversed.push(origin.pop());
        }
      }
      return reversed.peek();
    } else {
      return -1;
    }
  }

  /** Returns whether the queue is empty. */
  public boolean empty() {
    return reversed.isEmpty() && origin.isEmpty();
  }
}