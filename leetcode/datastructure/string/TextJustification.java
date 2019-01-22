/**
Leetcode 68. Text Justification

Given an array of words and a width maxWidth, format the text such that each line has exactly maxWidth characters and is fully (left and right) justified.

You should pack your words in a greedy approach; that is, pack as many words as you can in each line. Pad extra spaces ' ' when necessary so that each line has exactly maxWidth characters.

Extra spaces between words should be distributed as evenly as possible. If the number of spaces on a line do not divide evenly between words, the empty slots on the left will be assigned more spaces than the slots on the right.

For the last line of text, it should be left justified and no extra space is inserted between words.

Note:

A word is defined as a character sequence consisting of non-space characters only.
Each word's length is guaranteed to be greater than 0 and not exceed maxWidth.
The input array words contains at least one word.
Example 1:

Input:
words = ["This", "is", "an", "example", "of", "text", "justification."]
maxWidth = 16
Output:
[
   "This    is    an",
   "example  of text",
   "justification.  "
]
Example 2:

Input:
words = ["What","must","be","acknowledgment","shall","be"]
maxWidth = 16
Output:
[
  "What   must   be",
  "acknowledgment  ",
  "shall be        "
]
Explanation: Note that the last line is "shall be    " instead of "shall     be",
             because the last line must be left-justified instead of fully-justified.
             Note that the second line is also left-justified becase it contains only one word.
Example 3:

Input:
words = ["Science","is","what","we","understand","well","enough","to","explain",
         "to","a","computer.","Art","is","everything","else","we","do"]
maxWidth = 20
Output:
[
  "Science  is  what we",
  "understand      well",
  "enough to explain to",
  "a  computer.  Art is",
  "everything  else  we",
  "do                  "
]
*/

/**
Solution: Count how many words will be in curLine before filling.
How to Arrive:
* Question want us to figure out what words are in curLine.
* To do that we need to know the Len of words so far, and Len + (# words - 1) spaces <= maxWidth;
**Algorithm**:
* No need to conver corner cases, b/c question's note mentions non-space chars only, word.len > 0 <= maxwidth, and words.len >= 1;
**Step 1**: Figura out words for curLine.
* Keep a int K for counting # of words for curLine.
* Loop i=0 to i < words.len; (every words); Increment by i+k; (finished this line's words, starting next line's word);
  * Init K = 0; Len = 0; (curLine's words' totalLen w/o spaces);
  * While i+k < words.len, not out of bounce; And Len + nextWordLen + K <= maxWidth: (Len + nextWordLen + K) is + nextWord's len + K (#ofWords - 1) spaces;
    * This word can be included in curLine.
    * We add curWord's len to Len; and increment K++, to check next word;
  * Now we have found out all words in curLine.
  * K is at next line's word, last word in curLine is words[i+k-1]; So curLine = words[i] -> words[i+k-1];
  * So we will Fill Lines from i to i+K-1;
**Step 2**: Fille CurLine.
* We Know: StartIndex -> endIndex of words in curLine, And we know the Len (words lenth w/o spaces);
* Now we need to find out the totalSpaces in curLine, the inBetween spaces between 2 words, And the extra spaces when spaces cannot be evenly divided:
  ```
  int spaces = maxWidth - len;
   int between = spaces / (end - start);
   int extra = spaces % (end - start);
  ```
* Now we start the Filling of curLine:
* Use a StringBuilder sb to do that.
* There are 3 Cases:
* Case 1: Only 1 word in curLine, fill right with spaces.
  * sb adds the word first.
  * While spaces > 0:
    * sb adds a space; spaces--;
* Case 2: Cur line is the last line.
  * For last line, only 1 space between words, and fill right spots with leftover spaces.
  * Loop from startIndex -> endIndex:
    * if index = end; Last word:
      * sb add this word;
      * Fill right with spaces leftover;
    * if not last word:
      * sb add word + " "; space--;
* Case 3: Not only 1 word, Not last Line.
  * We already know the inBetween spaces, and extra spaces;
  * First we add curWord to sb;
  * Check if is last word of Line:
    * break, if is last word, no need for space.
  * if Not last word:
  * First fill inBetween spaces, sb add inBetween # of spaces;
  * Then if there is leftover extra, sb add 1 more space, extra--;
* Finish Filling curLine, Add to Res;
* 
* Time: O(n)
* Space: O(n)

*/

class TextJustification {

  /**
   * Solution: Count how many words will be in curLine before filling.
   * Time: O(n)
   * Space: O(n)
   */
  List<String> res = new ArrayList<>();
  
  public List<String> fullJustify(String[] words, int maxWidth) {    
    for (int i = 0, k = 0; i < words.length; i += k) {
      // k is # of words follows words[i] in curLine == least spaces needed.
      k = 0;
      // len of words in line, w/o spaces.
      int len = 0;
      // calc cur line len & words.
      while (i + k < words.length && len + words[i + k].length() + k <= maxWidth) {
        int wordLen = words[i + k].length();
        len += wordLen;
        k++;
      }
      // k is at next line's word, last word in curLine is words[i+k-1];
      // curLine = words[i] -> words[i+k-1];
      // fill all spaces & words in line;
      fillLine(words, i, i + k - 1, len, maxWidth);
    }
    
    return res;
  }
    
  public void fillLine(String[] words, int start, int end, int len, int maxWidth) {
    // sb for cur line.
    StringBuilder sb = new StringBuilder();
    // calc total spaces
    int spaces = maxWidth - len;
    // if only 1 word, fill right with spaces.
    if (start == end) {
      sb.append(words[start]);
      while (spaces > 0) {
        sb.append(" ");
        spaces--;
      }
      res.add(sb.toString());
      return;
    }
    // more than 1 word.
    // spaces in between = totalSpaces / (words-1);
    int between = spaces / (end - start);
    // extra spaces, if cannot evenly divide. 
    int extra = spaces % (end - start);
    
    // last line case.
    if (end == words.length - 1) {
      for (int i = start; i <= end; i++) {
        if (i == end) {
          // at last word. add word and fill spaces right
          sb.append(words[i]);
          while (spaces > 0) {
            sb.append(" ");
            spaces--;
          }
          break;
        }
        // if not at last word. add 1 space.
        sb.append(words[i] + " ");
        spaces--;
      }
    } else {
      // not last line and not only 1 word.
      for (int i = start; i <= end; i++) {
        // append word, calc space.
        sb.append(words[i]);
        // if last word in line, no need for space.
        if (i == end) {
          break;
        }
        // fill between spaces.
        for (int b = 0; b < between; b++) {
          sb.append(" ");
          spaces--;
        }
        // add extra spaces, if there is extra leftover.
        if (extra > 0) {
          sb.append(" ");
          extra--;
          spaces--;
        }
      } 
    }
    // add curLine to res.
    res.add(sb.toString());
  }
    

}