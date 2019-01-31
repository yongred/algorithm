/**
File System

Write a file system class, which has two functions: create, and get

create("/a",1)
get("/a") //get 1
create("/a/b",2)
get("/a/b") //get 2
create("/c/d",1) //Error, because "/c" is not existed
get("/c") //Error, because "/c" is not existed

开始的时候是写两个 function, create 和 get

create("/a",1)
get("/a") //得到 1
create("/a/b",2)
get("/a/b") //得到 2
create("/c/d",1) //Error,因为它的上一级“/c”并不存在
get("/c") //Error,因为“/c”不存在


follow up 是写一个 watch 函数,比如 watch("/a",new Runnable(){System.out.println("helloword");})
后,每当 create("/a/b",1) 等在/a 之下的目录不产生 error 的话,都会执行绑在“/a”上的 callback
函数

比如 watch("/a",System.out.println("yes"))
watch("/a/b",System.out.println("no"))
当 create("/a/b/c",1)时,两个 callback 函数都会被触发,会 output yes 和 no

我对 java 的 callback 并不是很熟悉,面试官小哥很好地给了 trigger callback 的接口,因为之前在
地里看到说没必要建 trie,所以直接 hashmap 解决,但处理字符串找它的上一层目录处理“/”比较
容易出 bug,要注意判断根目录的情况 .

三个 method:create(path, value), set_value(path, value), get_value(path)

让你实现一个长成这样的tree:
root
/ \
NA EU
/ \
CA US
其中root是没有name和value,剩下的每个点都有name和value.
• create(path,value):给你一个path,比如“NA/MX”,和value,比如“3”。 那么你就在NA下面
创建一个点叫MX,值是3。

• set_value(path, value): 给你一个path,找到path的叶子,然后set value,如果 叶子不存在,
返回false;
• get_value(path): 给你一个path,返回叶子的值,没有叶子的话返回NULL。
*/

import java.util.*;
import java.io.*;

public class FileSystem2 {

	class TrieNode {
		Map<String, TrieNode> children = new HashMap<>();
		int value;
		// String name;

		TrieNode(int value) {
			this.value = value;
			// this.name = name;
		}

		TrieNode() {
			this.value = -1;
			// this.name = null;
		}
	}

	TrieNode root = new TrieNode();

	public void create(String path, int value) {
		TrieNode curNode = root;
		String[] dirs = path.split("/");
		//path dirs parents
		for (int i = 0; i < dirs.length; i++) {
			String curDir = dirs[i];
			// check node
			if (!curNode.children.containsKey(curDir)) {
				curNode.children.put(curDir, new TrieNode());
			}
			curNode = curNode.children.get(curDir);
		}
		// cur dir
		curNode.value = value;
	}

	public boolean setValue(String path, int value) {
		TrieNode curNode = root;
		String[] dirs = path.split("/");
		//path dirs parents
		for (int i = 0; i < dirs.length; i++) {
			String curDir = dirs[i];
			// check node
			if (!curNode.children.containsKey(curDir)) {
				return false;
			}
			curNode = curNode.children.get(curDir);
		}
		// cur dir
		curNode.value = value;
		return true;
	}

	public Integer getValue(String path) {
		TrieNode curNode = root;
		String[] dirs = path.split("/");
		//path dirs parents
		for (int i = 0; i < dirs.length; i++) {
			String curDir = dirs[i];
			// check node
			if (!curNode.children.containsKey(curDir)) {
				return null;
			}
			curNode = curNode.children.get(curDir);
		}

		return curNode.value == -1 ? null : curNode.value;
	}

	public static void main(String[] args) {
		FileSystem2 obj = new FileSystem2();
		obj.create("AA", 1);
		obj.create("AA/BB/CC", 3);
		System.out.println("AA: " + obj.getValue("AA"));
		System.out.println("AA/BB: " + obj.getValue("AA/BB"));
		System.out.println("AA/BB/CC " + obj.getValue("AA/BB/CC"));
		obj.setValue("AA/BB", 2);
		System.out.println("AA/BB: " + obj.getValue("AA/BB"));
	}


}



