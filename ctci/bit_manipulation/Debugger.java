/**
5.5 Debugger: Explain what the following code does: ( (n & (n -1) ) == 0) .
Hints: #151, #202, #261, #302, #346, #372, #383, #398
*/

/*
-When A & B == 0; means A and B does not have the same 1s in any bits, ex: 11001 & 00110 = 0;
-When 101000 -1 = 100111; meaning i becomes 0 follows by 1s. 
-Therefore when 10000 - 1 = 01111; 10000 & 01111 = 0; When n has only one 1 digit.
-meaning n = 2^i; n is a power of 2
*/