/**
6.1 The Heavy Pill: You have 20 bottles of pills. 19 bottles have 1.0 gram pills, but one has pills of weight
1.1 grams. Given a scale that provides an exact measurement, how would you find the heavy bottle?
You can only use the scale once.
Hints: # 186, #252, #319, #387
*/

/*
	put in different number of pills for each bottle. ex: 1 + 2 + 3 + ... + 20;
	Sigma(N) i=1 to n = n*(n+1) / 2;
	if 1st one is 1.1, then total is 210.1; 2nd = 210.2; 13th = 211.3;
	Formula: totalWeight - 210 = extra;  extra / 0.1 = bottle #;
			(totalWeight - 210) / 0.1;
*/

import java.util.*;
import java.io.*;

public class HeavyPill{
	public static void getHeavyPill(double [] bottles){
		double totalWeight = 0;
		for(int i=1; i< bottles.length; i++){
			totalWeight += (bottles[i] * i);
		}
		System.out.println("totalWeight " + totalWeight);
		System.out.println("pill # " + (int)((totalWeight - 210) / 0.1) );
	}

	public static void main(String [] args){
		Scanner input = new Scanner(System.in);
		System.out.println("Index of heavy > ");
		int heavyIndex = input.nextInt();
		double [] bottles = new double[20+1];
		for(int i=1; i< bottles.length; i++){
			if(i == heavyIndex)
				bottles[i] = 1.1;
			else
				bottles[i] = 1;
		}
		getHeavyPill(bottles);
	}


}