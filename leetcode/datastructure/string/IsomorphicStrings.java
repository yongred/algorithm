/**
205. Isomorphic Strings
Given two strings s and t, determine if they are isomorphic.

Two strings are isomorphic if the characters in s can be replaced to get t.

All occurrences of a character must be replaced with another character while preserving the order of characters. No two characters may map to the same character but a character may map to itself.

Example 1:

Input: s = "egg", t = "add"
Output: true
Example 2:

Input: s = "foo", t = "bar"
Output: false
Example 3:

Input: s = "paper", t = "title"
Output: true
Note:
You may assume both s and t have the same length.
*/

/**
Solution: Use 2 ASCII or HashMap for <s, t> and <t, s>
How to Arrive:
* **Key**: both S and T chars cannot map to same val twice.
Ex: s: 'aa'  t: 'cb' a -> c, b False; 
s: 'cb' t: 'aa'; c,b <- a False;
* Since they S and T are the same length, and same char can only map to one char, we just need to store the maps both way, S->T, T->S; 
HashMap:
```
sMap.put(s.charAt(i), t.charAt(i));
tMap.put(t.charAt(i), s.charAt(i));
```
ASCII:
```
sChars[schar] = tchar;
 tChars[tchar] = schar;
```
* Check if both s and t mapping is only to 1 char.
```
if (sChars[schar] != 0) {
	// s mapped val not cur t
	if (sChars[schar] != tchar) {
		return false;
	}
} else if (tChars[tchar] != 0) {
	// t already mapped to a s
	// if t mapped val not cur s
	if (tChars[tchar] != schar) {
		return false;
	}
}
```
* Time: O(s);
* Space: O(1) using ASCII, HashMaps cost O(s + t);
*/

class IsomorphicStrings {

	/**
	 * Solution 2: use 2 Ascii table as hashes,  1 for <s,t>; 1 for <t, s>
	 * Time: O(s);
	 * Space: O(1)
	 */
	public boolean isIsomorphic(String s, String t) {
    // bb ac => b->a, b->c conflict b and a,c;
    // cd aa => c->a, d->a conflict c,d and a;
    // case empty
    if (s == "") {
      return true;
    }
    // hashes <s, t>
    int[] sChars = new int[256];
    // hashes <t, s>
    int[] tChars = new int[256];
    
    for (int i = 0; i < s.length(); i++) {
      char schar = s.charAt(i);
      char tchar = t.charAt(i);
      // s already mapped to a t
      if (sChars[schar] != 0) {
        // s mapped val not cur t
        if (sChars[schar] != tchar) {
          return false;
        }
      } else if (tChars[tchar] != 0) {
        // t already mapped to a s
        // if t mapped val not cur s
        if (tChars[tchar] != schar) {
          return false;
        }
      } else {
        // new s and t mapped.
        sChars[schar] = tchar;
        tChars[tchar] = schar;
      }
    }
    return true;
  }

	/**
	 * Solution 1: use 2 hashMap, 1 for <s,t>; 1 for <t, s>
	 * Time: O(s);
	 * Space: O(s + t)
	 */
  public boolean isIsomorphic(String s, String t) {
    // papere titleo => p->t, a->i, p->t, e->l, r->e, e->o Conflict. e and l,o
    // ad aa => a->a, d->a conflict a,d and a;
    // case empty
    if (s == "") {
      return true;
    }
    // use hashMap<s, t>
    HashMap<Character, Character> sMap = new HashMap<>();
    // map <t, s>
    HashMap<Character, Character> tMap = new HashMap<>();
    // check if chars at same pos is mapped to same char.
    for (int i = 0; i < s.length(); i++) {
      // no 2 keys map to same val.
      if (sMap.containsKey(s.charAt(i))) {
        if (sMap.get(s.charAt(i)) != t.charAt(i)) {
          return false;
        }
      } else if (tMap.containsKey(t.charAt(i))) {
        // no 2 vals map to same key.
        if (tMap.get(t.charAt(i)) != s.charAt(i)) {
          return false;
        }
      } else {
        // new map node
        sMap.put(s.charAt(i), t.charAt(i));
        tMap.put(t.charAt(i), s.charAt(i));
      }
    }
    return true;
  }
}