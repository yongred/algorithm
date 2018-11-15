/**
The Alerter is a simple monitoring tool, intended to help detectincreases in response time for some process. It does that
by computing a few statistics about the process across a'window' of a certain number of runs, and alerting (returning true)
if certain thresholds are met.
It takes the following parameters:
- inputs: A list of integer times for the process. This list may be very long
- window size: how many runs long a window is, as aninteger
- allowedIncrease: howfar over 'average' a window or value is allowed to be, as a percent. This isrepresented as a decimal value based on one, so a 50% allowable increase wouldbe represented as 1.5

Your Alerter should return true if either of the following conditions are met:
* Any value is more than the allowed increase above the window average in ALL windows in which it appears.

For example:
alert({1, 2, 100,2, 2}, 3, 1.5) should alert: the value 100 appears in three windows, and in all cases is more than 50% over the average value
alert({1, 2, 4,2, 2}, 3, 2) should not alert: the largest outlier is 4, and that value appears in a window with average value 2.6, less than 100% of that average

* Any window's average is more than the acceptable increase over any previous window's average value

For example:
alert({1,2,100,2,2}, 2, 2.5) should alert: Even though no individual value causes an alert, there is a window with average 1.5 and a later window with an average more than 2.5 times larger

Otherwise, you should return false.
*/

import java.util.*;
import java.io.*;

public class Alerter {

	public static boolean alert(int [] nums, int k, double limit) {
		boolean res = false;
		if (nums.length == 0) {
			return res;
		}
		List<Integer> maxes = new ArrayList<>();
		List<Double> avgs = new ArrayList<>();
		Deque<Integer> deque = new ArrayDeque<>();

		int windowSum = 0;
		int index = 0;
    // init first window.
    while (index < k && index < nums.length) {
      // keep the largest on the head. Newest add to the tail 
      while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[index]) {
        deque.removeLast();
      }
      windowSum += nums[index];
      deque.addLast(index);
      index++;
    }
    // add head (largest in first window) to max.
    maxes.add(nums[deque.peekFirst()]);
    avgs.add(windowSum / (double)k);
    // sec window to last.
    for (int j = k; j < nums.length; j++) {
      // remove indexes in prev window. j is endInd, - k == startInd.
      if (!deque.isEmpty() && deque.peekFirst() <= j - k) {
        deque.removeFirst();
      }
      // calc new avg
      windowSum -= nums[j - k];
      windowSum += nums[j];
      avgs.add(windowSum / (double)k);
      // any prev smaller than cur num, is useless. we only need largest in 
      // cur window.
      while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[j]) {
        deque.removeLast();
      }
      deque.addLast(j);
      // add head (largest in window) to max.
      maxes.add(nums[deque.peekFirst()]);
    }
    res = checkLimit(limit, maxes, avgs);

    return res;
	}

	public static boolean checkLimit(double limit, List<Integer> maxes, List<Double> avgs) {
		boolean res = false;
		// pre
    boolean prevAbove = false;
    boolean valAbove = true;
    // last max is the same
    boolean continuation = false;
    // smallest avg so far
    double smallestAvg = avgs.get(0);

		for (int i = 0; i < maxes.size(); i++) {
    	double increase = maxes.get(i) / avgs.get(i);
    	System.out.println("max increase: " + increase);
    	if (i > 0) {
    		continuation = (maxes.get(i) == maxes.get(i - 1)) ? true : false;
    		// avg calc, check if curAvg / smallestAvg > limit
    		double avgAbove = Double.MIN_VALUE;
    		if (avgs.get(i) < smallestAvg) {
    			avgAbove =  smallestAvg / avgs.get(i);
    			smallestAvg = avgs.get(i);
    		} else {
    			avgAbove = avgs.get(i) / smallestAvg;
    		}
    		System.out.println("avgAbove: " + avgAbove);
    		if (Double.compare(avgAbove, limit) > 0) {
					res = true;
					break;
				}
    	}
    	
    	valAbove = (Double.compare(increase, limit) > 0) ? true : false;
    	// last same max not above, no need to check this one.
    	if (continuation) {
    		if (!prevAbove) {
    			continue;
    		} else { // prev is above
    			// if last elm, and prev and cur are ==, and both above.
    			if (i == maxes.size() - 1 && valAbove) {
    				res = true;
    				break;
    			}
    		}
    	} else {
    		// new num;
    		// if prev nums is above, then we alert
    		if (prevAbove) {
    			res = true;
    			break;
    		}
    		// if it's the last
    		if (i == maxes.size() - 1 && valAbove) {
  				res = true;
  				break;
  			}
    	}
    	// for next iter.
    	prevAbove = valAbove;
    	
    }

    return res;
	}

	public static void main(String[] args) {
		int [] nums = {1,2,4,2,2};
		int k = 3;
		double limit = 2;
		boolean ans = alert(nums, k, limit);
		System.out.println("ans: " + ans);
	}
}