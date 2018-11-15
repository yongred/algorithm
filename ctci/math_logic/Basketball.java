/**
6.2 Basketball: You have a basketball hoop and someone says that you can play one of two games.
Game 1: You get one shot to make the hoop.
Game 2: You get three shots and you have to make two of three shots.
If p is the probability of making a particular shot, for which values of p should you pick one game
or the other?
Hints: # 181, #239, #284, #323
*/

/*
answer:
PG1 = p;
PG2 = s(2,3) + s(3,3); (make 2 of 3) + (make 3 of 3);
s(3,3) = p^3
s(2,3) = 3 * p^2 * (1-p) = 3p^2 - 3p^3;
s(2,3) + s(3,3) = 3p^2 - 3p^3 + p^3 = 3p^2 - 2p^3;

for PG2 > PG1
= 3p^2 - 2p^3 > p;
= 3p - 2p^2 > 1;
= 0 > 1-(3p - 2p^2);
= 0 > 2p^2 - 3p + 1;
= 0 > (2p - 1)(p - 1);
= p=0.5, p=1;
0__0.5__1;
0 !> (2*0.4 - 1)(0.4 - 1)= (-)(-) = (+); incorrect
0 > (2*0.6 - 1)(0.6 - 1)= (+)(-) = (-); correct

ans: when 0.5 < p < 1, PG2 is > PG1
*/