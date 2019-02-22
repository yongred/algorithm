/**
12. Text Justification
Leetcode 相似问题: Leetcode #68 Text Justification
http://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=299301
Given a JSON input with a list of strings, e.g.
    {
        ‘text’: 'first word',
        ‘text’: 'my second sentence',        
        ‘text’: ’now it's third‘,
    }
要求print出来这样的形式（格式崩了，大家意会即可，左右间距以最长str为准）
+------------------+
|first word        |
+------------------+
|my second sentence|
+------------------+
|now it's third    |
+------------------+
和小哥大致讨论了下，秒了。

然后小哥来了个follow up, 说input JSON里再加一个width, 要求左右间距以width为准，左缩进，print出来大概这样：
+---------------+
|first word     |
+---------------+
|my second      |
|sentence       |
+---------------+
|now it's third |
+---------------+

又秒了。
之后小哥说奥森！但还有个follow up，这回你不用写code了，大致说下想法就好：input JSON 为list of lists, 一级list表示一行，二级list表示每行中的一列，每列规定width（相当于每个string都会有自己的width）, print出来大概这样：
+--------------+----------+
|first word    |my        |
|              |second    |
|              |sentence  |
+--------------+----------+
|now it's third           |
+-------------------------+
大致讲了下想法,小哥说破费*2,然后时间到了欢乐得 say 了 bye。


之后一个 followup 说的是如果一个单词一行装不下怎么办,面试官题示说可以用'-';比如[bbbbbb,
bbb], n = 3;

bb-
bb-
bb-
bb-
b__

空格用‘_’代替;可以做成预处理[bb-, bb-, bb-, bb-, b]之后用原来的代码,我和面试官说了思路但是
代码没有写完,面试官安慰说我知道你的意思了,followup 没关系。
*/

import java.util.*;
import java.io.*;

public class TextJustification2 {

	public static void printList(List<String> res) {
		res.forEach(str -> {
			System.out.println("\"" + str + "\"" + "  len: " + str.length());
		});
	}
	
	/**
	 * question1: Use longest Str.len as maxWidth;
	 */
	public static void justifyMaxStrLen(String[] strs) {
		List<String> res = new ArrayList<>();
		// find longest
		int maxWidth = 0;
		for (String str : strs) {
			maxWidth = Math.max(maxWidth, str.length());
		}
		// loop through strs, add " "s to right until reached maxWidth;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strs.length; i++) {
			sb.append(strs[i]);
			// fill spaces;
			while(sb.length() < maxWidth) {
				sb.append(" ");
			}
			// add to res
			res.add(sb.toString());
			sb.setLength(0);
		} 
		printList(res);
	}

	/**
	 * Follow up 1: Add a maxWidth input.
	 */
	public static void justifyMaxWidth(String[] strs, int maxWidth) {
		List<String> res = new ArrayList<>();
		// divide each sentence into words,
		for (int i = 0; i < strs.length; i++) {
			String[] words = strs[i].split(" ");
			// reform sentence with maxWidth.
			String sentence = reform(words, maxWidth);
			res.add(sentence);
		}
		printList(res);
	}

	public static String reform(String[] words, int maxWidth) {
		StringBuilder sb = new StringBuilder();
		// loop words, find out each line's words
		for (int i = 0, k = 0; i < words.length; i += k) {
			k = 0;
			// words len w/o spaces.
			int len = 0;
			// if a word > width limit; error.
      if (words[i + k].length() > maxWidth) {
        return "";
      }
			while (i + k < words.length && len + words[i + k].length() + k <= maxWidth) {
				len += words[i + k].length();
				k++;
			}
			// k is finished/out of curLine.
			int spaces = maxWidth - len;
			// fill words in curLine. And fill right with spaces until maxWidth reached.
			for (int j = i; j <= i + k - 1; j++) {
				sb.append(words[j]);
        if (j == i + k - 1) {
          while (spaces > 0) {
            sb.append(" ");
            spaces--;
          }
          break;
        }
        sb.append(" ");
        spaces--;
			}
			// add line end, except for last line.
			if (i + k < words.length) {
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	/**
	 * Follow up 3: break next word if not able to fit this line.
	 */
	static List<String> res = new ArrayList<>();

	public static void justifyMaxWidthBreak(String[] strs, int maxWidth) {
		// divide each sentence into words,
		for (int i = 0; i < strs.length; i++) {
			// reform sentence with maxWidth.
			reformBreak(strs[i], maxWidth);
		}
		printList(res);
	}

	public static void reformBreak(String sentence, int maxWidth) {
		if (sentence.length() < maxWidth) {
			res.add(sentence);
			return;
		}
		// loop sentence, break when each to maxWidth.
		// split substr at width
		for (int i = 0; i < sentence.length(); i += (maxWidth - 1)) {
			// if not last line.
			if (i + maxWidth - 1 < sentence.length()) {
				// ex: k=5; 0->3 abcd; abcd + '-'= "abcd-"";
				String line = sentence.substring(i, i + maxWidth - 1);
				// add char '-' 
				res.add(line + "-");
			} else {
				String line = sentence.substring(i, sentence.length());
				StringBuilder sb = new StringBuilder(line);
				// fill line " " spaces until maxWidth.
				while (sb.length() < maxWidth) {
					sb.append(" ");
				}
				res.add(sb.toString());
			}
		}
	}

	public static void main(String[] args) {
   	String[] strs = new String[] {"first word", "my second sentence", "now it's third"};
   	justifyMaxStrLen(strs);
   	System.out.println("followup 1: -----------");
   	justifyMaxWidth(strs, 8);
   	System.out.println("followup 2: -----------");
   	justifyMaxWidthBreak(strs, 5);
	}
}
