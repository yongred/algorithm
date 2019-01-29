/**
Minimum Vertices to Traverse Directed Graph

Given a directed grapjh, represented in a two dimension array, output a list of points that can be used to travese every points with the least number of visited vertices.

https://cs.stackexchange.com/questions/1698/find-the-minimal-number-of-runs-to-visit-every-edge-of-a-directed-graph

http://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=273389

流水题有向图什么的 我写出来的答案都非常短非常简单易懂 但是这都是我花了很久自己想出来的
绝对没有地里有些人说的那么难 什么 scc 的根本不需要 我也不认识

每选中一个点 则可从该点到达的所有点都算作被遍历了 求最少选中多少个点可以 遍历全图
不用 scc 那么复杂的东西 其实就是用一个 hashmap 记每个 node 的 source 最后算有几个 source 找
source 的时候先假设你现在看到的就是 source 然后一直往下找我用的是 dfs 注意处理环 你现在看
到的如果已经在 hashmap 里了 那就是之前已经见过了 所以说 source 已经找到过了 就可以 skip 不
知道我说的你能看懂么

不过好像觉得有 circle 的时候不太知道怎么处理,比如边是[[0, 1], [1, 0], [3, 1], [3, 2], [2, 1]] 的时候
我是每次 DFS 的时候另外用了个 unordered_set 记了一下都见过了什么 要是[[0, 1], [1, 0], [3, 1], [3,
2], [2, 1]]

假设从左到右 process 第一次 set 里就只有 0 跟 1 然后接着 process [1, 0]这个已经在 map 里了直
接跳过 [3, 1]没在 map 里所以 DFS 最后 set 里有 3210,也就是说 DFS 里又见到[1, 0]是不跳过的
处理环主要三种情况: 一个环连 0 个头, 一个环连着一个头, 一个环连着两个头
分开讨论:
0 个头: toposort 是找不到这个的因为入度不为零。这个可以说大约当成多少个环就有多少个头
(每个环只要一个点就能遍历)[1,2],[2,1]
1 个头:那就是一个入度为零的头可以遍历[1,2][2,3][3,2]
2 个头:两个入度为零的头可以遍历([1,2],[2,3],[3,2],[4,3])

思路:
1.处理(一个头,两个头)找所有入度为零的点,全加到 result 集,对每一个点 dfs 或者 bfs 皆
可,然后用一个 set 存所有没访问过的点,遇到即从 set 中删除
2.剩下的就是无头的环,继续 dfs/bfs 剩下的 set 中的点,直到 set 为空,每次 dfs 前把开始的点加
到 result 里(注意 set 的 concurrent modification error)

http://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=212885

  0  1  2  3  4  5  6  7  8  9
0[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
1[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
2[0, 0, 0 ,0, 0, 0, 0, 0, 0, 1],
3[0, 0, 0, 1, 0, 1, 0, 1, 0, 0],
4[0, 0, 0, 0, 0, 0 ,0, 0, 1, 0],
5[0, 0, 0, 0, 0, 0, 0, 0, 1, 0],
6[0, 0, 0, 0, 0, 0, 1, 0, 0 ,0],
7[0, 0, 0, 0, 1, 0, 0, 0, 0, 0],
8[0, 0, 0, 0, 0, 0, 0, 1, 0, 0],
9[0, 0, 0, 1, 0, 0, 1, 0, 0, 0]

这是当时的一个 test case,找最少的点遍历所有点,结果是 0,1,2.

http://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=220456

所以首先做 SCC (Strongly Connected Components),对,就是那个 Kosaraju's algorithm,参见
https://en.wikipedia.org/wiki/Kosaraju%27s_algorithm。然后取没有入度的 component。也是蛋疼。
应该是求 List of all vertices without in-degrees.
感觉这题这么处理太复杂了,直接扫所有有入度的点,从所有点的 set 中去掉,再返回 set.size()
即可
*/

public class MinimumVerticesTraverseDirectedGraph {
	
}
