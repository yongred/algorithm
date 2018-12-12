/**
225. Implement Stack using Queues
Implement the following operations of a stack using queues.

push(x) -- Push element x onto stack.
pop() -- Removes the element on top of the stack.
top() -- Get the top element.
empty() -- Return whether the stack is empty.
Example:

MyStack stack = new MyStack();

stack.push(1);
stack.push(2);  
stack.top();   // returns 2
stack.pop();   // returns 2
stack.empty(); // returns false
Notes:

You must use only standard operations of a queue -- which means only push to back, peek/pop from front, size, and is empty operations are valid.
Depending on your language, queue may not be supported natively. You may simulate a queue by using a list or deque (double-ended queue), as long as you use only standard operations of a queue.
You may assume that all operations are valid (for example, no pop or top operations will be called on an empty stack).
*/

/**
Solution 1: Using 2 Queues, make push costly.
Ex: push 3, 2, 4; pop 4, 3, 3;
front left , back right.
Qm:    -> Qh: 2,3 -> Qm: 2p,3p -> Qh:
Qh: 3; -> Qm: 3p -> Qh: 4, 2, 3 -> Qm: 4,2,3
pop from Qm: 4, 2, 3 order correct for stack.

How to arrive:
* To make sure last elm is in the front. We can push last elm in empty Q, then push prev elms on top of it. Then use that Q for pop and peek.
* To do that, we use 2 Qs: Qh Qm; Qm for pop and peek, Qh is always empty, use to push new elm to it.
* When their is new push, x, we push x to Qh, then we push all Qm elms to Qh. Now last elm is in the Front of prevs in Qh. 
* But we need to have Qm use as pop and peek, So we just switch their name/role; Qh = Qm; Qm = Qh;
* If we do that every time, the order of the Qm will be reversed. Becomes LIFO.
* Time: Push O(n), Pop O(1);
* Space: O(n) elms;

------------------
Solution 2: using 2 Queues, make Pop costly.
Ex: push 3, 2, 4; peek 4; pop 4, 2, 3;
front left, back right;
pushes:
Qm: 3, 2, 4 
Qh: 
peek:
Qm: 4
Qh: 3, 2
pop:
Qm: 4p  -> Qh:         -> Qh: 3          -> Qm: 3p Done;
Qh: 3, 2 -> Qm: 3, 2 -> Qm: 3p, 2p -> Qh: 

How to Arrive:
* If we poll all elms from Queue ecept the last one, then that elm is same as stack pop and peek.
* To do that we can pop all front elms from mainQ to helperQ, and pop or peek the last elm in mainQ.
* When Pop, mainQ will become empty, helperQ will have rest of the elms not popped.
* Now we want to always pop and push from/to the mainQ, so we need to switch name after Pop.
* For peek, we will have remain last elm in mainQ, so no need to switch.
* Time: push O(1), pop O(n), peek is amortized O(1);
* Space: O(n) all elms.
*/

/**
Solution 1: Using 2 Queues, make push costly.
*/
class ImplementStackUsingQueues {
  // use 2 queues
  Deque<Integer> mainQueue;
  Deque<Integer> helperQueue;

  /** Initialize your data structure here. */
  public ImplementStackUsingQueues() {
    mainQueue = new ArrayDeque<>();
    helperQueue = new ArrayDeque<>();
  }

  /** Push element x onto stack. */
  public void push(int x) {
    // push new elm to empty helperQ, so new elm at front.
    helperQueue.addLast(x);
    // push all elms from mainQ to helperQ. Those elms are already in stack order.
    while (!mainQueue.isEmpty()) {
      helperQueue.addLast(mainQueue.pollFirst());
    }
    // Switch names
    Deque<Integer> temp = helperQueue;
    helperQueue = mainQueue;
    mainQueue = temp;
  }

  /** Removes the element on top of the stack and returns that element. */
  public int pop() {
    return empty() ? -1 : mainQueue.pollFirst();
  }

  /** Get the top element. */
  public int top() {
    return empty() ? -1 : mainQueue.peekFirst();
  }

  /** Returns whether the stack is empty. */
  public boolean empty() {
    // helperQ should always be empty.
    return mainQueue.isEmpty();
  }
}

/**
Solution 2: using 2 Queues, make Pop costly.
*/
class ImplementStackUsingQueues {
  Deque<Integer> helperQueue = new ArrayDeque<>();
  Deque<Integer> mainQueue = new ArrayDeque<>();

  /** Initialize your data structure here. */
  public ImplementStackUsingQueues() {}

  /** Push element x onto stack. */
  public void push(int x) {
    mainQueue.addLast(x);
  }

  /** Removes the element on top of the stack and returns that element. */
  public int pop() {
    if (empty()) {
        return -1;
    }
    // get the last elm on top.
    while (mainQueue.size() > 1) {
        helperQueue.addLast(mainQueue.pollFirst());
    }
    int last = mainQueue.pollFirst();
    // mainQ is now empty, swap name helperQ and mainQ.
    Deque<Integer> temp = mainQueue;
    mainQueue = helperQueue;
    helperQueue = temp;
    // last elm is top elm.
    return last;
  }

  /** Get the top element. */
  public int top() {
    if (empty()) {
        return -1;
    }
    // get the last elm
    while (mainQueue.size() > 1) {
        helperQueue.addLast(mainQueue.pollFirst());
    }
    // no need to swap since not empty.
    return mainQueue.peek();
  }

  /** Returns whether the stack is empty. */
  public boolean empty() {
    return mainQueue.isEmpty() && helperQueue.isEmpty();
  }
}