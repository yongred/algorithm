/**
471. Top K Frequent Words
Given a list of words and an integer k, return the top k frequent words in the list.

Example
Given

[
    "yes", "lint", "code",
    "yes", "code", "baby",
    "you", "baby", "chrome",
    "safari", "lint", "code",
    "body", "lint", "code"
]
for k = 3, return ["code", "lint", "baby"].

for k = 4, return ["code", "lint", "baby", "yes"],

Challenge
Do it in O(nlogk) time and O(n) extra space.

Notice
You should order the words by the frequency of them in the return list, the most frequent one comes first. If two words has the same frequency, the one with lower alphabetical order come first.
*/

import java.util.*;
import java.io.*;

public class TopKFrequentWords {
	
    /**
     * @param words: an array of string
     * @param k: An integer
     * @return: an array of string
     * Solution:
     * to count the words, we can use hash to store.
     * after that we need to sort by count, then by alphabetical order
     * return it as String array
     * Time: O(NlgN); b/c sorting the list, so NlogN.
     * Space: 2*S(N) = O(N);
     *
     * Notes: Comparator, - goes first, + goes behind. 
     */
    public String[] topKFrequentWords(String[] words, int k) {
        HashMap<String, Integer> hash = new HashMap();
        for (String word : words) 
        {
        	hash.put(word, hash.getOrDefault(word, 0) + 1);
        }
        List<String> list = new ArrayList(hash.keySet());
        //hash.get(w2) - hash.get(w1) DESC, > Positive, < Negative, = Zero.
        //ex: if w1 = 3 > w2=2; 2-3 = -1; Negative vals in the front. Default ASC.
        //lambda operator -> body; here (w1, w2) represents 
        //new Comparator<String>( public int compare(String w1, String o2); )
        //ex: compare w1='code' 4 to w2'lint' 3. Desc: we want 'code' left, 'lint' right.
        //so if w1 > w2; give w1 -, w2 +. 
        Collections.sort(list, (w1, w2) -> hash.get(w1).equals(hash.get(w2)) ?
        	w1.compareTo(w2) : hash.get(w2) - hash.get(w1)
        	);
        return list.subList(0,k).toArray(new String[k]);
    }

    public static void main(String[] args) {
    	String [] words = {"yes","lint","code","yes","code","baby","you","baby","chrome","safari","lint","code","body","lint","code"};
    	int k = 3;
    	TopKFrequentWords obj = new TopKFrequentWords();
    	String [] ans  = obj.topKFrequentWords(words, k);
    	System.out.println(Arrays.toString(ans));
    }
}