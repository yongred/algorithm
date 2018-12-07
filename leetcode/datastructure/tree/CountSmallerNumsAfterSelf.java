/**
315. Count of Smaller Numbers After Self
You are given an integer array nums and you have to return a new counts array. The counts array has the property where counts[i] is the number of smaller elements to the right of nums[i].

Example:

Input: [5,2,6,1]
Output: [2,1,1,0] 
Explanation:
To the right of 5 there are 2 smaller elements (2 and 1).
To the right of 2 there is only 1 smaller element (1).
To the right of 6 there is 1 smaller element (1).
To the right of 1 there is 0 smaller element.
*/

/**
Solution 1: construct a Binary Search Tree.
Ex: [3,2,6,3,1]  Res=[2,1,2,1,0];  1(0,1) 0 is leftbranch counts, 1 is dup.
		1 (0,1) Ans=0
					\
					 3 (1,2) Ans=[1](0+1)=1; Ans2=[1](0+1) + LeftBranch (1) = 2;
				/			                          \
2(0,1) Ans=	[1](0+1)=1;			 6 (0,1) Ans= [1](0+1) + [3](0+1)=2;

How to arrive:
* We need all < #s from rightside of array. So if we iterates in reverse <- order when we reach to a elm, we already know how many smallers on the right.
* Now we need to think about how to memorize the smaller #s like DP.
* We can construct a BST, that way we know **all leftside of tree are prev smaller than curVal**.
* We will have Node with leftSum and Dup.
* leftSum: record the leftBranch counts, so when new val > curNode, we don't have to count the leftside again, just use leftSum num.
* Dup: we need to count dup so newNode knows how many to add.
* We will iterates from n-1 -> 0; And insert curVal into the correct pos of the tree. While recording leftSum and Dup.
* We use a **preSum var to record all the counts on the leftTree**. So every time we traverse right node (curVal > nodeVal), we add preSum += leftSum + dup; (the leftbranch #s and curNode's appearances);
```
preSum += curNode.leftSum + curNode.dup;
curNode.right = insert(curNode.right, val, preSum, index, res);
```

* (curVal < nodeVal) we curNode.leftSum++; Since curVal will a newNode on the leftBranch. Then traverse left.
```
curNode.leftSum++;
curNode.left = insert(curNode.left, val, preSum, index, res);
```
* We record the preSum to res[index] when we find the pos. Either create a new node or increment the dup of existing node.
```
if (curNode == null) {
	// record all right nums < curVal.
	res[index] = preSum;
	// create new Node for this val.
	return new TreeNode(val, 0);
}
```
* For (curVal == nodeVal); curNode.dup++; Record the preSum with the leftSum, since there are new smaller nodes added after last same val elm. (Everything on the left tree counts);
```
curNode.dup++;
res[index] = preSum + curNode.leftSum;
```
* Time: O(nlgn) on average; O(n^2) on worst. Ex: [3,2,1,0] linear traverse everytime.
* Space: O(n);

---------------

Solution 2: Binary Search, Insert new val to sorted partial arr right->left;
Ex: [3,2,6,3,1] 
reverse iteration: [1,3,6,2,3];
[1] val=1 index 0; [1,3] val=3 index 1; [1,3,6] val=6 index=2; 
[1,2,3,6] val=2 index=1; [1,2,3,3,6] val=3 index=2;
Put all index in reverse order:  Ans = [2,1,2,1,0];

How to arrive:
* Like last solution, reverse order iteration can allow us to know prev right side #s < cur.
* If we keep a sorted list of traversed right elms in ASC order, the **index of the list == how many elms < cur.** 
Ex: [1,2,3,3,6]; 2 elms smaller than 3, 1 elm smaller than 2;
* B/c partial sorted list is sorted, we can use binary search to find out where the curVal would insert in the list.
* We add that index to the res. And insert the val to list at the index pos.
```
for (int i = nums.length - 1; i >= 0; i--) {
	// find index of curVal to insert into sorted list of all the right nums so far.
	int index = findInsertIndex(sorted, nums[i]);
	// index = count of right nums < curVal.
	res[i] = index;
	// this cost O(n); arraylist.add(index, elm); 
	sorted.add(index, nums[i]);
}
```
* Don't forget corner case in binary search find index. Ex: [1,3] val=6; index should be 2, which is out of the scope.
* Time: O(n^2); B/c arraylist.add(index, elm) cost O(n); if insert is O(1) then O(nLgn);
* Space: O(n)

------------------
Solution 3: Merge Sort the Indexes.
Ex: [3,2,6,3,1] => indexes [0,1,2,3,4] -> [4,1,0,3,2];
idx [0] [1] (3,2) merge R1 (2) < L0 (3); find a right smaller, Counts [1,0,0,0,0], count[0]++;
idx [3] [4] (3,1) merge R3 (1) < L3 (3); find a right smaller, Counts [1,0,0,1,0], count[3]++;

How to arrive:
* Problem wants us to compare Left #s to Right #s, and record the Right #s count if < Left #.
* That sounds similar to Merge Sort. Where we split left and right to merge.
* But sorting the vals will prevent us from knowing how many right side smaller #s, since order is changed.
* We can sort the indexes, that way we still have access to origin ordered vals.
* The mergeSort process are the same, just passing in the indexes arr.
* We modify the merge function to record the resCount. When we comparing Left #s to Right #s.
* We keep a smallerRightCount of how many right #s smaller than left #s. When a find right # >= left, we record the count to resCounts arr. Move on to next left #.
* B/c LeftArr and RightArr are sorted, we know if right # < prev left #, then it will also be < cur left #. So smallerRightCount will carryover to the next left # count.
```
if (nums[rightIndexes[r]] < nums[leftIndexes[l]]) {
	indexes[sortedIndex] = rightIndexes[r];
	smallerRightCount++;
	r++;
} else {
	indexes[sortedIndex] = leftIndexes[l];
	resCounts[leftIndexes[l]] += smallerRightCount;
	l++;
}
```
* Time: O(nlgn);
* Space: O(n);
*/

class CountSmallerNumsAfterSelf {

	/**
	 * Solution: Merge Sort indexes.
	 * Time : O(nlgn)
	 * Space: O(n)
	 */
	private int[] resCounts;
  
  public List<Integer> countSmaller(int[] nums) {
    if (nums == null || nums.length == 0) {
      return new ArrayList<>();
    }
    // sort nums' indexes by their vals.
    int[] indexes = new int[nums.length];
    resCounts = new int[nums.length];
    // init indexes.
    for (int i = 0; i < nums.length; i++) {
      indexes[i] = i;
    }
    // merge sort indexes
    mergeSort(nums, indexes, 0, nums.length - 1);
    // convert res array to list
    List<Integer> reslist = new ArrayList<>();
    for (int count : resCounts) {
      reslist.add(count);
    }
    return reslist;
  }
  
  public void mergeSort(int[] nums, int[] indexes, int start, int end) {
    if (start >= end) {
      return;
    }
    int mid = start + (end - start) / 2;
    mergeSort(nums, indexes, start, mid);
    mergeSort(nums, indexes, mid + 1, end);
    merge(nums, indexes, start, mid, end);
  }
  
  public void merge(int[] nums, int[] indexes, int start, int mid, int end) {
    // start->mid
    int l = 0;
    // mid+1 -> end
    int r = 0;
    // left and right indexes arr.
    int[] leftIndexes = new int[mid - start + 1];
    int[] rightIndexes = new int[end - mid];
    // copy indexes over to left and right arrays, since we are changing vals in indexes array.
    for (int i = start; i <= mid; i++) {
      leftIndexes[i - start] = indexes[i];
    }
    for (int j = mid + 1; j <= end; j++) {
      rightIndexes[j - (mid + 1)] = indexes[j];
    }
    // index already sorted
    int sortedIndex = start;
    // smaller right #s count
    int smallerRightCount = 0;
    // compare leftVal to rightVal. Sort the indexes, also count smaller right #s.
    while (l < leftIndexes.length && r < rightIndexes.length) {
      // val of sorted indexes compare
      if (nums[rightIndexes[r]] < nums[leftIndexes[l]]) {
        // right # is smaller
        // smaller # first. Sort ASC order.
        indexes[sortedIndex] = rightIndexes[r];
        // 1 more smaller # on rightside.
        smallerRightCount++;
        r++;
      } else {
        // rightVal >= leftVal
        // left is smaller, sort ASC.
        indexes[sortedIndex] = leftIndexes[l];
        // cur rightVal >= leftVal means rest of the right #s also >= curLeft. Since both arrs are sorted.
        // Add the smaller #s on the rightArr to curLeft pos count.
        resCounts[leftIndexes[l]] += smallerRightCount;
        // go sort and count rightSmaller for next # on leftArr.
        l++;
      }
      // index of this pos sorted.
      sortedIndex++;
    }
    // Left #s not finish
    while (l < leftIndexes.length) {
      // add rest left #s to sortedIndexes.
      indexes[sortedIndex] = leftIndexes[l];
      // rightArr finished iter, means rest of the left #s are > all right #s.
      // Add the smaller right #s count to res.
      resCounts[leftIndexes[l]] += smallerRightCount;
      l++;
      sortedIndex++;
    }
    // right #s not finish
    while (r < rightIndexes.length) {
      // add rest right #s to sortedIndexes.
      indexes[sortedIndex] = rightIndexes[r];
      // rest of the right #s are > all leftside, so no more smaller right #s.
      r++;
      sortedIndex++;
    }
    
  }

  
  /**
   * BST solution node
   */
  public class TreeNode {
    TreeNode left;
    TreeNode right;
    int val;
    // sum of leftBranch, smaller and on leftside of the array.
    int leftSum;
    // duplicates in adj pos. Not adj don't count.
    int dup = 1;
    
    public TreeNode(int val, int leftSum) {
      this.val = val;
      this.leftSum = leftSum;
    }
  }
  
  /**
   * Solution: construct a binary search tree.
   * Time: O(nlgn) on average; O(n^2) on worst. Ex: [3,2,1,0] linear traverse everytime.
   * Space: O(n);
   */
  public List<Integer> countSmaller(int[] nums) {
    // use array so we can insert val in reverse order. ex: [0,0,2,1,0] <-
    int[] res = new int[nums.length];
    if (nums == null || nums.length == 0) {
      return new ArrayList<Integer>();
    }
    TreeNode root = null;
    // traverse in reverse order, so we know how many on the right as we go.
    for (int i = nums.length - 1; i >= 0; i--) {
      // insert curVal to the correct position in tree.
      root = insert(root, nums[i], 0, i, res);
    }
    ArrayList<Integer> reslist = new ArrayList<>();
    // convert array to list.
    for (int num : res) {
      reslist.add(num);
    }
    return reslist;
  }
  
  /**
   * create new node from val add to root. And store prev right node's sums in res[index].
   */
  public TreeNode insert(TreeNode curNode, int val, int preSum, int index, int[] res) {
    // find position for this val.
    if (curNode == null) {
      // record all right nums < curVal.
      res[index] = preSum;
      // create new Node for this val.
      return new TreeNode(val, 0);
    }
    // smaller num on leftside of array.
    if (val < curNode.val) {
      // record # of leftBranch, so we don't have to go to leftbranch again to count elms.
      curNode.leftSum++;
      curNode.left = insert(curNode.left, val, preSum, index, res);
    } else if (val > curNode.val) {
      // larger num on leftside of array.
      // this val have a node smaller on the rightside of array.
      // All nums on cur's leftBranch and cur dups are < curVal on the rightside of array.
      preSum += curNode.leftSum + curNode.dup;
      curNode.right = insert(curNode.right, val, preSum, index, res);
    } else {
      // val == curNode.val;
      // record duplicates.
      curNode.dup++;
      // record all elms < curNode on rightside of array. 
      // Might be new smaller elms added after last same elm, 
      // ex: [3,2,3,1] => [2,1,1,0]; have to count cur left branch.
      res[index] = preSum + curNode.leftSum;
    }
    return curNode;
  }

  /**
   * Solution: Binary Search, sort partial arr right->left;
   * Time: O(n^2); B/c arraylist.add(index, elm) cost O(n); if insert is O(1) then O(nLgn);
   * Space: O(n)
   */
  public List<Integer> countSmaller(int[] nums) {
    if (nums == null || nums.length == 0) {
      return new ArrayList<>();
    }
    int[] res = new int[nums.length];
    // sorted nums from left to right.
    List<Integer> sorted = new ArrayList<>();
    for (int i = nums.length - 1; i >= 0; i--) {
      // find index of curVal to insert into sorted list of all the right nums so far.
      int index = findInsertIndex(sorted, nums[i]);
      // index = count of right nums < curVal.
      res[i] = index;
      // this cost O(n); arraylist.add(index, elm); 
      sorted.add(index, nums[i]);
    }
    // convert array to list.
    List<Integer> reslist = new ArrayList<>();
    for (int num : res) {
      reslist.add(num);
    }
    return reslist;
  }
  
  public int findInsertIndex(List<Integer> sorted, int val) {
    if (sorted.size() == 0) {
      return 0;
    }
    int start = 0;
    int end = sorted.size() - 1;
    // check corner cases. out of the cur list scope.
    if (val <= sorted.get(start)) {
      return 0;
    }
    if (val > sorted.get(end)) {
      return end + 1;
    }
    while (start < end) {
      int mid = start + (end - start) / 2;
      if (sorted.get(mid) == val) {
        // left side inclusive mid. We want the leftmost index, since we just counting <, not <=;
        // Ex: [1,2,3,6] val=3; [1,2,3,3,6] return idx 2;
        end = mid;
      } else if (sorted.get(mid) > val) {
        // val on the leftside of mid. Inclusive mid, since mid could be the pos.
        end = mid;
      } else {
        // midVal < val. Exclusive mid, since we will be insert atleast +1 pos.
        start = mid + 1;
      }
    }
    // last one left is the index.
    return start;
  }
}