/**
487. Name Deduplication
Given a list of names, remove the duplicate names. Two name will be treated as the same name if they are equal ignore the case.

Return a list of names without duplication, all names should be in lowercase, and keep the order in the original list.

Example
Given:

["James", "james", "Bill Gates", "bill Gates", "Hello World", "HELLO WORLD", "Helloworld"]
return:

["james", "bill gates", "hello world", "helloworld"]
*/

public class NameDeduplication {
    /**
     * @param names: a string array
     * @return: a string array
     * Solution:
     * Finding the duplicates, meaning checking for existing. So a Hash would help.
	* we don't need to count the strs, just need to know if it already exist.
	* So, we can use HashSet {1,2,3} instead of HashMap {k1->v1, k2->v2}
	* If hashSet not contains str, it is unique, add it to list.
	* return the list
	 * Time: O(n) Space: O(n)
     */
    public List<String> nameDeduplication(String[] names) {
        HashSet<String> hash = new HashSet();
        List<String> ans = new ArrayList();
        for(String str : names)
        {
            str = str.toLowerCase();
            if(!hash.contains(str))
            {
                ans.add(str);
                hash.add(str);
            }
        }
        return ans;
    }
}