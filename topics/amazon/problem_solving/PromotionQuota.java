/**
 * In promotions, we have a soft limit on how many promotions each seller can run at once to avoid spam and misuse. Every promotion has a start time and an end time. Let’s design and write a function to determine the maximum number of promotions running at once, given a list of promotions
 *
 * class Promotion {
 *   Date startTime;
 *   Date endTime;
 * }
 */

/*
https://quip-amazon.com/eXT2AQ0tXDtK/PS-Promotion-Quota

input:
- List<Promotion> promotions
- Promotion: startTime, endTime

Output:
- Basically the Maximum number of promotions overlapping at one time.

Similar to Maximum Number of Events That Can Be Attended
- Solution using PriorityQueue

Example:

Time: T1 - T2 - T3 - T4
A:    [---------------)
B:               [----)
C:         [-----)
D:    [----------)

Most running at once is 3, at T2

A starts on T1, ends before T4
B starts on T3, ends before T4
C starts on T2, ends before T3
D starts on T1, ends before T3

Qs:
- Start and end time inclusive? start yes, end no
- Output return max number of overlap, don't have to return time
- Is the input sorted
- How large is the data set?

Solution: by PriorityQueue
Promotions Sorting by startDate: A(t1), D(t1), C(t2), B(t3)
PriorityQueue sort by endDate
Loop Promotions
- PriorityQueue poll endDates <= curDate
- PriorityQueue add Promo
- Record PriorityQueue's size, keep track of maxSize

This basically goes through each Promo's startDate, +1 for startDate, -1 for endDate.
The remaining on that date is the ongoing promos.

-----

Solution: by sorting
Promos Sorting by startDate: A(t1,t4), D(t1,t3), C(t2,t3), B(t3,t4)
Promos Sorting by endDate: C(t2,t3), D(t1,t3), A(t1,t4), B(t3,t4)

Goes through each Promo's startDate, +1 for startDate, -1 for endDate.
The remaining on that date is the ongoing promos.

Max=0
runningPromo=0
Loop promos sorted by startDate:
A(t1,t4): start t1, index0, runningPromo+1
check endDate list ends on t1: runningPromo-0, index0
check startDate list starts on t1:
runningPromo=1
Max=1

D(t1,t3): start t1, index1, runningPromo+1
check endDate list ends on t1: runningPromo-0, index0
runningPromo=2
Max=2

C(t2,t3): start t2, index2, runningPromo+1
check endDate list ends on t2: runningPromo-0, index0
runningPromo=3
Max=3

B(t3,t4): start t3, index3, runningPromo+1
check endDate list ends on t3: runningPromo-2, index2
runningPromo=2
Max=3 (ANS)

*/

package problem_solving;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;

public class PromotionQuota {

    class Promotion {
        public Date startTime;
        public Date endTime;

        public Promotion(Date startTime, Date endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }

    public static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    /*
    Time: T1 - T2 - T3 - T4
    A:    [---------------)
    B:               [----)
    C:         [-----)
    D:    [----------)

    Promotions Sorting by startDate: A(t1,t4), D(t1,t3), C(t2,t3), B(t3,t4)
    A(t1,t4)
    PQ: A(t1,t4) size=1
    D(t1,t3)
    PQ: D(t1,t3) -> A(t1,t4) size=2
    C(t2,t3)
    PQ: D(t1,t3) -> C(t2,t3) -> A(t1,t4) size=3
    B(t3,t4)
    PQ: (t3 <= t3) polled D, C; Added B; B(t3,t4) -> A(t1,t4) size=2

    Time: O(nLgn)
    Space: O(n)
     */
    public int maxPromotionRunsPriorityQueue(List<Promotion> promotions) {
        promotions.sort(Comparator.comparing(promo -> promo.startTime));
        PriorityQueue<Promotion> promoEndTimePriorityQueue = new PriorityQueue<>(
                Comparator.comparing(promo -> promo.endTime));

        int maxPromoRuns = 0;
        for (Promotion promo : promotions) {
            System.out.println("StartDate " + dateFormat.format(promo.startTime));

            while (!promoEndTimePriorityQueue.isEmpty()) {
                Promotion nextEndTimePromo = promoEndTimePriorityQueue.peek();
                if (nextEndTimePromo.endTime.compareTo(promo.startTime) <= 0) {
                    promoEndTimePriorityQueue.poll();
                } else {
                    break;
                }
            }
            promoEndTimePriorityQueue.add(promo);
            maxPromoRuns = Math.max(promoEndTimePriorityQueue.size(), maxPromoRuns);
        }

        return maxPromoRuns;
    }

    /*
    Time: O(nlgn)
    Space: O(n)
     */
    public int maxPromotionRunsSorting(List<Promotion> promotions) {
        promotions.sort(Comparator.comparing(promo -> promo.startTime));
        List<Promotion> endTimeSortedPromos = new ArrayList<>(promotions);
        endTimeSortedPromos.sort(Comparator.comparing(promo -> promo.endTime));

        int maxPromoRunning = 0;
        int runningPromo;
        int startTimePromoIndex = 0;
        int endTimePromoIndex = 0;

        while (startTimePromoIndex < promotions.size()) {
            Date currentStartTime = promotions.get(startTimePromoIndex).startTime;
            startTimePromoIndex++;
            while (startTimePromoIndex < promotions.size() &&
                    promotions.get(startTimePromoIndex).startTime.equals(currentStartTime)) {
                startTimePromoIndex++;
            }

            while (endTimePromoIndex < endTimeSortedPromos.size() &&
                    endTimeSortedPromos.get(endTimePromoIndex).endTime.compareTo(currentStartTime) <= 0) {
                endTimePromoIndex++;
            }

            runningPromo = startTimePromoIndex - endTimePromoIndex;
            System.out.println("runningPromo " + runningPromo + " startTime " + dateFormat.format(currentStartTime));
            maxPromoRunning = Math.max(runningPromo, maxPromoRunning);
        }

        return maxPromoRunning;
    }

    public static void main(String[] args) {
        PromotionQuota object = new PromotionQuota();
        String time1 = "09/01/2022";
        String time2 = "09/02/2022";
        String time3 = "09/03/2022";
        String time4 = "09/04/2022";

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date dateTime1 = dateFormat.parse(time1);
            Date dateTime2 = dateFormat.parse(time2);
            Date dateTime3 = dateFormat.parse(time3);
            Date dateTime4 = dateFormat.parse(time4);

            Promotion promoA = object.new Promotion(dateTime1, dateTime4);
            Promotion promoB = object.new Promotion(dateTime3, dateTime4);
            Promotion promoC = object.new Promotion(dateTime2, dateTime3);
            Promotion promoD = object.new Promotion(dateTime1, dateTime3);

            List<Promotion> promotions = Arrays.asList(new Promotion[] {
                    promoA, promoB, promoC, promoD
            });

            int resultPQ = object.maxPromotionRunsPriorityQueue(promotions);
            int resultSorting = object.maxPromotionRunsSorting(promotions);
            System.out.println("Result from maxPromotionRunsPriorityQueue: " + resultPQ);
            System.out.println("Result from maxPromotionRunsSorting: " + resultSorting);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
