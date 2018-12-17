package dataStruct;

public class CountingSort {
	
	public void countingSort(int[] nums, int min, int max) {
    // form a count arr, Ex: [0,1,2,0,0,1] = [3,2,1]; 3 0s, 2 1s, 1 2s.
    int[] counts = new int[max - min + 1];
    // count the nums appearances
    for (int num : nums) {
      counts[num]++;
    }
    // nums arr index for putting in numbers from smallest to largest.
    int index = 0;
    // min to max, >= 1 appeared nums put into arr from smallest to largest order.
    for (int n = min; n <= max; n++) {
      // if curNum appears 1 or more times, curNum is the smallest uninserted num.
      if (counts[n] > 0) {
        // put all curNum into the front positions.
        while (counts[n] > 0) {
          // put in the curNum, and go to next pos.
          nums[index] = n;
          index++;
          // putted the curNum in, decrement.
          counts[n]--;
        }
      }
    }
  }

}
