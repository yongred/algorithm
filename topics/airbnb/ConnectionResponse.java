/**
1. Key Value Store:
第二轮. 貌似遇到了新题，反正我之前没见过。就是给你的一个表，里面有各个connection下的request某个key以及相应的response结果的记录，如果key 存在，则 response最后一列就是该key的值，否则返回一个“-“ 表大概是长这个样子：
connid           tag            command         status             key
1                req               get                           "user_1"
3                req               get                           "user_2"
1                resp              get                           "user_1"
3                resp              get                           "-"
2                req               getk                          "user_1"
2                req               getk                          "listen_1"
2                req               getk         end_loop         "-"
2                resp              getk                          "user_1"
2                resp              getk                          "-"

类似这样，当同一个connection请求多于一个key时要用getk。
最后问给一个key 问这个key被hit了多少次（response成功）以及Miss了多少次。
比如 user_1 hits 2 miss 0。

说了，getk 表示同一个connection下发送多个get 请求，在end loop状态表示getK的k 个请求发送完毕

题目描述非常非常长，再加上我用的c++,面试官延了一些时间。关键是我问了面试官，response 是不是按照req顺序来的， 他说不是。弄的我很混乱，最后还是写的有问题。后来觉得他应该是指不是同一个connection 下的请求与响应不一定按照顺序，比如第三行和第4行是可能互换的。
总之凉凉，感觉找工作季人品差到极致，貌似这家我同学中只有我没遇到见过的题 T_T， 发个面经祝大家好运。


是不是简单计数就可以了？并不用考虑连接对应关系？遍历所有request，记录每个key出现几次。遍历所有response，记录每个key出现几次。hit次数从第二个表查，miss次数是第一个表的数减第二个表的数。我理解对吗？

LZ input是怎么给的, String[]吗

我用的c++，一开始面试官说的是一个string[], 但是后来为了节省parse的时间就直接给了一个2d 数组，每个元素就是一个component
*/

/**
Q: Can I assume all req gets a resp; Meaning req = resp #s; Unless end_loop
A: Assume all req with a 'key' gets either resp 'some_key' or '-';

Q: Can I get a wrong Key resp?
A: Assume only right key or '-';

Solution:
Count the # of res for 'key'; Count the success resp for 'key';
totalRes - successResp = missed;

*/

import java.io.*;
import java.util.*;

public class ConnectionResponse {
	// <Key, countRequest>
	Map<String, Integer> requests = new HashMap<>();
	// <Key, countSuccess>
	Map<String, Integer> successResp = new HashMap<>();

	public void countResponses(List<List<String>> statusList) {
		if (statusList == null || statusList.isEmpty()) {
			return;
		}
		for (List<String> tags : statusList) {
			// index1 = req/resp; index4= key; index3 status 'end_loop'
			if (tags.get(1).equals("req")) {
				if (tags.get(3).equals("end_loop")) {
					continue;
				}
				// count+1 for req of key 
				String key = tags.get(4);
				requests.put(key, requests.getOrDefault(key, 0) + 1);
			} else {
				// resp
				String key = tags.get(4);
				// check successful
				if (!key.equals("-")) {
					successResp.put(key, successResp.getOrDefault(key, 0) + 1);
				}
			}
		}
	}

	public int[] hitsAndMisses(String key) {
		// [0] = hits, [1] = misses
		int[] res = new int[2];
		Arrays.fill(res, -1);
		if (requests.containsKey(key)) {
			// have the req. check for success
			int success = successResp.getOrDefault(key, 0);
			// miss = reqs - success;
			int miss = requests.get(key) - success;
			res[0] = success;
			res[1] = miss;
		}
		return res;
	}

	/*
	1                req               get                           "user_1"
	3                req               get                           "user_2"
	1                resp              get                           "user_1"
	3                resp              get                           "-"
	2                req               getk                          "user_1"
	2                req               getk                          "listen_1"
	2                req               getk         end_loop         "-"
	2                resp              getk                          "user_1"
	2                resp              getk                          "-"
	*/

	public static void main(String[] args) {
		ConnectionResponse obj = new ConnectionResponse();
		List<List<String>> statusList = new ArrayList<>();
		String[][] lines = new String[][] {
			{"1", "req", "get", "", "user_1"},
			{"3", "req", "get", "", "user_2"},
			{"1", "resp", "get", "", "user_1"},
			{"3", "resp", "get", "", "-"},
			{"2", "req", "getk", "", "user_1"},
			{"2", "req", "getk", "", "listen_1"},
			{"2", "req", "getk", "end_loop", "-"},
			{"2", "resp", "getk", "", "user_1"},
			{"2", "resp", "getk", "", "-"},
		};

		for (String[] line : lines) {
			statusList.add(new ArrayList<>());
			for (String str : line) {
				statusList.get(statusList.size() - 1).add(str);
			}
		}

		obj.countResponses(statusList);
		// print all key
		for (String key : obj.requests.keySet()) {
			int[] hitMiss = obj.hitsAndMisses(key);
			System.out.println("key " + key + ", hits " + hitMiss[0] + ", misses " + hitMiss[1]);
		}
	}

}