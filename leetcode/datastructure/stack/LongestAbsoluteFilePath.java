/**
388. Longest Absolute File Path
Suppose we abstract our file system by a string in the following manner:

The string "dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext" represents:

dir
    subdir1
    subdir2
        file.ext
The directory dir contains an empty sub-directory subdir1 and a sub-directory subdir2 containing a file file.ext.

The string "dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext" represents:

dir
    subdir1
        file1.ext
        subsubdir1
    subdir2
        subsubdir2
            file2.ext
The directory dir contains two sub-directories subdir1 and subdir2. subdir1 contains a file file1.ext and an empty second-level sub-directory subsubdir1. subdir2 contains a second-level sub-directory subsubdir2 containing a file file2.ext.

We are interested in finding the longest (number of characters) absolute path to a file within our file system. For example, in the second example above, the longest absolute path is "dir/subdir2/subsubdir2/file2.ext", and its length is 32 (not including the double quotes).

Given a string representing the file system in the above format, return the length of the longest absolute path to file in the abstracted file system. If there is no file in the system, return 0.

Note:
The name of a file contains at least a . and an extension.
The name of a directory or sub-directory will not contain a ..
Time complexity required: O(n) where n is the size of the input string.

Notice that a/aa/aaa/file1.txt is not the longest file path, if there is another path aaaaaaaaaaaaaaaaaaaaa/sth.png.
*/

/**
Solution: Using HashMap to keep track of each level. (easier than Stack)
dir
    subdir1
        file1.ext
        subsubdir1
    subdirr2
        subsubdirr2
            file2.ext

maxlen = 0p, "file1.ext" 12+9=21p, "file2.ext" 25+9=34;
map: 
level : pathlen
1: "dir/"= 3+1= 4
2: "subdir1/"= 7+1 + 4 = 12p, "subdirr2/"= 8+1 + 4= 13,
3: "subsubdir1/" = 10+1 + 12 = 23p, "subsubdirr2"= 11+1 + 13 = 25

How to Arrive:
* To know the fileLen, we need to know the curPathLen; Which means we need to keep track of the dir we are in.
* Each time we enter a new Dir, we calc the pathLen.
* When we found a file, we do pathLen + fileLen; Update maxLen if > len;
* We use MaxLen to keep track of max file path len;
* **Key**: when we are in a new dir, we already go through prev dirs and files <= curlevel. So just update curLevel path len to curDirLen; prevPathLen + curDirLen + 1 ('/');
* Algorithm:
	* Loop through chars:
		* Found out the level by counting '\t' chars; **\n or \t is 1 char**.
		* Count the curNameLen. While char != '\n' end of name; curNameLen++;
			* If encount '.', means is file. Use a var isFile to represent.
		* If is Dir: (isFile = false);
			* update pathlen for cur level. prevLevel + curDir + 1 ('/');
			* Use HashMap to store <level, pathLen>;
		* if is File:
			* Calc curFile path len; PrevLevel (dir contains curFile) len + curFileLen;
			* int curFileLen = levelMap.get(curLevel - 1) + curNameLen;
			* Check MaxLen; Update if curFilePathLen > MaxLen;
		* Increment i to next Name;  i is currently at '\n';
	* Return maxLen at the end;
* **Key**: Stupid Problem did not mention 4 spaces in beginning of str == 1 tab.
* Time: O(n);
* Space: O(n);
*/

class LongestAbsoluteFilePath {

	/**
	Solution: Using HashMap to keep track of each level. (easier than Stack)
	Time: O(n)
	Space: O(n)
	*/
  public int lengthLongestPath(String input) {
    int maxLen = 0;
    // <level, curPathlen>
    HashMap<Integer, Integer> levelMap = new HashMap<>();
    // init level 0, 0 len
    levelMap.put(0, 0);
    int i = 0;
    while (i < input.length()) {
      // init vals
      int curLevel = 1; // dir level.
      boolean isFile = false;
      int curNameLen = 0; // cur file or dir len.
      // \t counts for curlevel. 1 tab 1 level increase; start at level 1 (0 tabs); level2 is 1 tab.
      // beginning of curName
      while (i < input.length() && input.charAt(i) == '\t') {
        curLevel++;
        // iterates till 1st non tab
        i++;
      }
      // \n is end of the curName.
      while (i < input.length() && input.charAt(i) != '\n') {
        // check file
        if (input.charAt(i) == '.') {
          isFile = true;
        }
        // count curName len;
        curNameLen++;
        // iterates till \n;
        i++;
      }
      // at \n, end of curName.
      // dir
      if (!isFile) {
        // update pathlen for cur level. prevLevel + curDir + 1 ('/');
        int curPathLen = levelMap.get(curLevel - 1) + curNameLen + 1;
        levelMap.put(curLevel, curPathLen);
      } else {
        // file
        // filelen = dir (prevlevel) len + curfileName;
        int curFileLen = levelMap.get(curLevel - 1) + curNameLen;
        // maxLen compare
        maxLen = Math.max(maxLen, curFileLen);
      }
      // increment i, we are at \n end of curName, need to go to next name.
      i++;
    }
    return maxLen;
  }
}