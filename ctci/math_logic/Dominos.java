/**
6.3 Dominos: There is an 8x8 chessboard in which two diagonally opposite corners have been cut off.
You are given 31 dominos, and a single domino can cover exactly two squares. Can you use the 31
dominos to cover the entire board? Prove your answer (by providing an example or showing why
it's impossible).
Hints: #367, #397
*/

/**
ans: 
	W B W B W B W X
	B W B W B W B W
	W B W B W B W B
	B W B W B W B W
	W B W B W B W B
	B W B W B W B W
	W B W B W B W B
	X W B W B W B W

	get rids of corners, meaning 2 blacks, left 30 blacks & 32 whites;
	31 Dominos = 31 whites & 31 blacks,
	missing 1 white;
	Therefore: not able to complete 
*/