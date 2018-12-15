/**
84. Largest Rectangle in Histogram
Given n non-negative integers representing the histogram's bar height where the width of each bar is 1, find the area of largest rectangle in the histogram.

Above is a histogram where width of each bar is 1, given height = [2,1,5,6,2,3].

The largest rectangle is shown in the shaded area, which has area = 10 unit.

Example:

Input: [2,1,5,6,2,3]
Output: 10
*/

/**
Solution: Use 2 stacks, 1 for startPos, 1 for heights.
Ex:
[2,1,5,6,2,3]
cur: i0 2, i1 1, i2 5, i3 6, i4 2, i5 3
startpos: 0, 2, 5
height:   1, 2, 3
largest: h(2) * w(1-0)= 2p, h(6) * w(4-3)= 6p, h(5) * w(4-2) = 10,
Finished loop: 
cur: i6 = whole width;
startpos: 0, 2, 5
height:   1, 2, 3
largest: h(5) * w(4-2)=10, 3 * (6-5)=3p, 2 * (6-2)=8p, 1 * (5-0)=5p;
ANS = 10;

How to arrive:
* Observation: The width of the rect continues until meet a shorter rect.
* Ex: 5,6,2; 5+5 stop at 2;
* In other words, the start of a rect is at it's pos, with shortest width of 1. It can expand until it meets a shorter rect.
* So we can calc the rect with w(stopPos - startPos) * h (startBar's height);
* With this idea, we can use stacks to keep track of still continuous Rects. It's StartPos and it's Height.
* That give us 2 cases:
	* When curBar < prevBar, we have a new Rect with StartPos and Height.
	* When curBar > prevBar, we calc all the rects stopped at cur pos.
		* w(curPos - startPos) * h (startBar's height) = RectVal;
* Keep track of the Max by compare prevMax with RectVal calculated.
* Time: O(n);
* Space: O(n);
*/

class LargestRectangleHistogram {

	/**
	 * Solution: Use 2 stacks, 1 for startPos, 1 for heights.
	 * Time: O(n);
	 * Space: O(n);
	 */
  public int largestRectangleArea(int[] heights) {
    if (heights == null || heights.length == 0) {
      return 0;
    }
    // 2 stacks pos, and height
    // start pos of width
    Stack<Integer> positions = new Stack<>();
    // height of cur rectangle.
    Stack<Integer> heightStack = new Stack<>();
    int max = 0;
    // loop heights
    for (int i = 0; i < heights.length; i++) {
      // if heights stack empty or prev height is < cur, new rect of cur height. 
      if (heightStack.isEmpty() || heightStack.peek() < heights[i]) {
        // taller found, push cur pos and height
        positions.push(i);
        heightStack.push(heights[i]);
      } else if (heightStack.peek() > heights[i]) {
        // if prev height > cur, prev height cannot continue. 
        // prev pos update to cur height. Pop all prev height > cur,
        int startPos = positions.peek();
        while (!heightStack.isEmpty() && heightStack.peek() > heights[i]) {
          int prevHeight = heightStack.pop();
          startPos = positions.pop();
          // calc prev height with the prev start pos, compare to max;
          int width = i - startPos;
          int rectVal = prevHeight * width;
          max = Math.max(max, rectVal);
        }
        // push cur height to start pos of last barHeight > curHeight.
        // Ex: 1,5,6,2 => cur is 2, rect is from idx 1->3 (5->2) bars.
        heightStack.push(heights[i]);
        positions.push(startPos);
      }
      // if prev = cur, don't push or pop, same height start from same pos.
    }
    // Now calc all rect left in stacks.
    // From largest width (heights.len) to prev start positions.
    int n = heights.length;
    while (!heightStack.isEmpty()) {
      int height = heightStack.pop();
      int startPos = positions.pop();
      // calc rectVal, compare max
      int width = n - startPos;
      int rectVal = height * width;
      max = Math.max(max, rectVal);
    }
    return max;
  }
}