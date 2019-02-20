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

/**
Solution: Can use a Trie but not neccessary. Just use HashMaps.
How to Arrive:
* Init a fileMap <path, val>; Init ("", 0) empty directory, needed for 1st level dirs path creatation.
* Ex: /a, split "/", into "" and "a"; parent is ""; Check map.contains("");
* Create: 
	* Check if path already exist.
	* Check if parent directory exist, ex: create("/a/b/c", 3), check "/a/b" exist.
	* Pass these 2 checks, we put "path", val to fileMap.
	* Follow Up: Print callbacks in parent dirs if exist.
		* Split parent dirs into "", "a", "b" ..
		* Use a StringBuilder to build up each parent dir. Skip first "";
		* /a -> /a/b -> /a/b/c ... call their callback Runnables, run();
* Get:
	* fileMap.getOrDefault(path, -1); Just get the val of Path.
* Set:
	* Just update the Path val if exist.
* Watch: (Follow Up) 
	* Use a Map <path, Runnable(callback)> to store callbacks for Paths.
	* Check if fileMap contains path. If not fileSystem don't have that path, return false;
	* If fileMap have the path. Just store the Runnable(callback function) in callbackMap.
	* Use it when create children Paths. Calling/Run their parents' callbacks

*/

import java.util.*;
import java.io.*;

public class FileSystem {

	Map<String, Integer> fileMap = new HashMap<>();
	Map<String, Runnable> callbackMap = new HashMap<>();

	public FileSystem() {
		// empty dir as first dir. All paths follow it.
		fileMap.put("", 0);
	}
	
	/**
	 * Time: O(1); Follow up: O(p) # of parents dir. 
	 */
	public boolean create(String path, int val) {
		// check file path already exist
		if (fileMap.containsKey(path)) {
			return false;
		}
		// check if there is the path dir, substr a/b/c check 'a/b/' exist.
		int splitIndex = path.lastIndexOf("/");
		// get a/b, if in files.
		String dir = path.substring(0, splitIndex);
		if (!fileMap.containsKey(dir)) {
			// no such dir.
			return false;
		}
		// exist dir, save the path
		fileMap.put(path, val);

		// follow up watch callback, path's dir parents' callback.
		String[] parentFiles = dir.split("/");
		StringBuilder sb = new StringBuilder();b
		for (String file : parentFiles) {
			// /a -> /a/b -> /a/b/c ...
			if (sb.length() == 0 && file.equals("")) {
				// skip first "" or it will becomes "//""
				continue;
			}
			sb.append("/" + file);
			String curPath = sb.toString();
			if (callbackMap.containsKey(curPath)) {
				Runnable func = callbackMap.get(curPath);
				func.run();
			}
		}

		return true;
	}

	/**
	 * Time: O(1)
	 */
	public int get(String path) {
		return fileMap.getOrDefault(path, -1);
	}

	/**
	 * Time: O(1)
	 */
	public boolean set(String path, int val) {
		// no such path
		if (!fileMap.containsKey(path)) {
			return false;
		}
		// update val
		fileMap.put(path, val);
		return true;
	}

	/**
	follow up 是写一个 watch 函数,比如 watch("/a",new Runnable(){System.out.println("helloword");})
	后,每当 create("/a/b",1) 等在/a 之下的目录不产生 error 的话,都会执行绑在“/a”上的 callback
	函数

	比如 watch("/a",System.out.println("yes"))
	watch("/a/b",System.out.println("no"))
	当 create("/a/b/c",1)时,两个 callback 函数都会被触发,会 output yes 和 no
	*/
	public boolean watch(String path, Runnable callback) {
		// if no such path in files.
		if (!fileMap.containsKey(path)) {
			return false;
		}
		// add callback to file
		callbackMap.put(path, callback);
		return true;
	}

	public static void main(String[] args) {
		FileSystem obj = new FileSystem();
		System.out.println("create /aa, 1");
		obj.create("/aa", 1);
		System.out.println("/aa " + obj.get("/aa"));

		System.out.println("create /aa/bb, 2");
		obj.create("/aa/bb", 2);
		System.out.println("/bb " + obj.get("/bb"));
		System.out.println("/aa/bb " + obj.get("/aa/bb"));

		System.out.println("set /aa, 10");
		obj.set("/aa", 10);
		System.out.println("/aa " + obj.get("/aa"));

		System.out.println("watch /aa, print Yes");
		obj.watch("/aa", new Runnable() {
			@Override
			public void run() {
				System.out.println("/aa Yes");
			}
		});

		System.out.println("watch /aa/bb, print No");
		obj.watch("/aa/bb", () -> {
			System.out.println("/aa/bb Yes");
		});

		System.out.println("create /dd/cc, 3");
		obj.create("/dd/cc", 3);
		System.out.println("/dd/cc " + obj.get("/dd/cc"));

		System.out.println("create /aa/bb/cc, 3");
		obj.create("/aa/bb/cc", 3);
		System.out.println("/aa/bb/cc " + obj.get("/aa/bb/cc"));
	}

}