/**
71. Simplify Path
Given an absolute path for a file (Unix-style), simplify it. 

For example,
path = "/home/", => "/home"
path = "/a/./b/../../c/", => "/c"
path = "/a/../../b/../c//.//", => "/c"
path = "/a//b////c/d//././/..", => "/a/b/c"

In a UNIX-style file system, a period ('.') refers to the current directory, so it can be ignored in a simplified path. Additionally, a double period ("..") moves up a directory, so it cancels out whatever the last directory was. For more information, look here: https://en.wikipedia.org/wiki/Path_(computing)#Unix_style

Corner Cases:

Did you consider the case where path = "/../"?
In this case, you should return "/".
Another corner case is the path might contain multiple slashes '/' together, such as "/home//foo/".
In this case, you should ignore redundant slashes and return "/home/foo".
*/

/**
Solution: Use Deque or Stack to store all dirs.
How to Arrive:
* Cases: "." ignore, ".." remove last dir. "" empty ignore, "/+" = 1 /;
* Since ".." remove last dir, that could be a good point to use a Stack. stack.pop();
* Since dirs are separated by /s, we can use the Split("/") or Split("/+") method.
```
String[] dirs = path.split("/");
```
* **Key**: java Split("/") will create leading "" empty string elm, and "" elms if "///" cases.
* **Key**: java Split("/+") will just create a leading "" empty string elm;
* So we **need a case to check skip empty string** when looping through all dirs.
* Case: "." or "" skip
* Case: ".." pop last
* Other cases: stack.push(dir);
* Then use a StringBuilder to append all dirs in Stack in **Back/Bottom to Front/Top** order. for (String str : stack); Or use Iterator; Or use deque to pollFirst;
* Don't forget if stack is empty, return "/";
```
StringBuilder res = new StringBuilder();
while (!deque.isEmpty()) {
	// remove from the front like queue, for correct order.
	res.append("/").append(deque.removeFirst());
}
// if empty return /. else return joined string path.
return res.length() > 0 ? res.toString() : "/";
```
* Time: O(n);
* Space: O(N);
*/

class SimplifyPath {

  public String simplifyPath(String path) {
    if (path == null || path.length() == 0) {
      return "/";
    }
    // split by /+; 1 or more /s. remove leading /s, prevent create 1st "" elm.
    // String[] dirs = path.replaceAll("^/+", "").split("/+");
    // Or just split by /, and check skip empty "" in loop
    String[] dirs = path.split("/");
    if (dirs.length == 0) {
      return "/";
    }
    // stack for store dir names
    Deque<String> deque = new ArrayDeque<>();
    // loop dirs
    for (int i = 0; i < dirs.length; i++) {
      // check empty String and "." ;
      if (dirs[i].equals(".") || dirs[i].equals("")) {
        continue;
      }
      if (dirs[i].equals("..")) {
        if (!deque.isEmpty()) {
          // remove top elm
          deque.removeLast();
        }
      } else {
        // add to top like stack
        deque.addLast(dirs[i]);
      }
    }
    
    StringBuilder res = new StringBuilder();
    while (!deque.isEmpty()) {
      // remove from the front like queue, for correct order.
      res.append("/").append(deque.removeFirst());
    }
    // if empty return /. else return joined string path.
    return res.length() > 0 ? res.toString() : "/";
  }
}