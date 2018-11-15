/**
Check for balanced parentheses in an expression:
1.9
Given an expression string exp , write a program to examine whether the pairs and the orders of “{“,”}”,”(“,”)”,”[“,”]” are correct in exp. For example, the program should print true for exp = “[()]{}{[()()]()}” and false for exp = “[(])”
*/


import java.util.*;
import java.io.*;

public class BalancedParentheses{
	/*
     * @param s: A string
     * @return: whether the string is a valid parentheses
     * Solution: use stack to pop all pairs; since only pairs can be inside other pairs, that means by pop off matching pairs, prev and next should always be a pair.
     That means we can pop until stack is empty.
     e: [(){()}] = [(, [()pop->[, [{, [{(, [{()pop-> [{, [{}pop-> [, []pop-> empty;
     * Time: O(n); Space: O(n);
     */
    public static boolean isValidParentheses(String s) {

    	Stack<Character> stack = new Stack<Character>();

    	for (int i=0; i< s.length(); i++) {
    		char ch = s.charAt(i);
    		//get prev for pair comparison
			char prev = '\u0000';
			if(!stack.isEmpty()){
				prev = stack.peek();
			}
			//if it's closing, check for match and pop them
    		if(prev == '(' && ch == ')')
    			stack.pop();
    		else if(prev == '[' && ch == ']')
    			stack.pop();
    		else if(prev == '{' && ch == '}')
    			stack.pop();
    		//if opening, push to stack
    		else if(ch == '(' || ch == '[' || ch == '{')
    			stack.push(ch);
    		//either closing & have a pair, or opening for push, everything else is false
    		else
    			return false;
    	}
    	//everything matches, stack is empty
    	return stack.isEmpty();

    }


    public static void main(String [] args){
    	boolean ans = isValidParentheses("{}[]()");
    	System.out.println(ans);
    }
}