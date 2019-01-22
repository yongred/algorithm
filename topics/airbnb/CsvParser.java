/**
CSV Parser
Parse an escaped string into csvformat

Input: csvformat

John,Smith,john.smith@gmail.com,Los Angeles,1
Jane,Roberts,janer@msn.com,"San Francisco, CA",0
"Alexandra ""Alex""",Menendez,alex.menendez@gmail.com,Miami,1 """Alexandra Alex"""

Output: escaped string

John|Smith|john.smith@gmail.com|Los Angeles|1
Jane|Roberts|janer@msn.com|San Francisco, CA|0
Alexandra "Alex"|Menendez|alex.menendez@gmail.com|Miami|1 "Alexandra Alex"
*/

/**
"word, word2" = word, word2;
"word ""word2"" word3" = word "word2" word3;
word "word2, ""word3""" = word word2, "word3";
Only when there is "," or when cur is the last char in whole csv string we add curSentence.
*/

import java.io.*;
import java.util.*;

public class CsvParser {

	public static String parseCSV(String str) {
		List<String> res = new ArrayList<>();
		boolean inQuote = false;
		StringBuilder sb = new StringBuilder();

		// loop str
		for (int i = 0; i < str.length(); i++) {
			char cur = str.charAt(i);
			// check if inside a quote
			if (inQuote) {
				if (cur == '"') {
					// check if next is also '\"', check for "" 2 quotes together. Add 1 quote to sb.
					if (i + 1 < str.length() && str.charAt(i + 1) == '"') {
						sb.append("\"");
						i++;
					} else {
						// if not 2 quotes together, end inQuote.
						inQuote = false;
					}
				} else {
					// not a quotation, just add the chars for cur sentence.
					sb.append(cur);
				}
			} else {
				// Not in Quote, look for ',' to end the sentence.
				if (cur == ',') {
					res.add(sb.toString());
					// reset sb, for next sentence.
					sb.setLength(0);
				} else if (cur == '"') {
					// if quotation
					inQuote = true;
				} else {
					sb.append(cur);
				}
			}
			// check if this is last char, end of all strs.
			if (i == str.length() - 1) {
				// add the last sentence.
				res.add(sb.toString());
				sb.setLength(0);
			}
		}

		return String.join("|", res);
	}

	public static void main(String[] args) {
		String csvStr = "John,Smith,john.smith@gmail.com,Los Angeles,1";
		String csvStr2 = "Jane,Roberts,janer@msn.com,\"San Francisco, CA\",0";
		String csvStr3 = "\"Alexandra \"\"Alex\"\"\",Menendez,alex.menendez@gmail.com,Miami,1 \"\"\"Alexandra Alex\"\"\"";
		System.out.println(parseCSV(csvStr));
		System.out.println(parseCSV(csvStr2));
		System.out.println(parseCSV(csvStr3));
	}


}