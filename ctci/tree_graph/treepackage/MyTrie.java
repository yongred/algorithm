/**
Trie implementation;
Trie: words like dictionary, each node contain a character;
*/
package treepackage;
import java.util.*;

public class MyTrie{

	public class Node{
		private Node parent;
		private char character;
		private Node [] children;
		private boolean isLeaf;
		private boolean isWord;
		//root node for empty constructor
		public Node(){ 
			children = new Node[26]; //26 letters
			isLeaf = true;
			isWord = false;
		}

		public Node(char character){
			this();
			this.character = character;
		}

		public void addWord(String word){
			isLeaf = false;
			int charPos = word.charAt(0) - 'a';  //0-25, a-z
			//!= null means already have this character, go to next char
			if(children[charPos] == null){ //doesnt have this char as child yet
				children[charPos] = new Node(word.charAt(0));
				children[charPos].parent = this;
			}

			if(word.length() > 1){
				children[charPos].addWord(word.substring(1));
			}else{
				children[charPos].isWord = true;
			}
		}

		//child a-z
		public Node getChildNode(char c){
			return children[c - 'a'];
		}

		//return a list of string words children and grand-childrens of this node
		public List getWords(){
			List list = new ArrayList();

			if(this.isWord){
				list.add(this.toString());
			}
			//have children
			if(!this.isLeaf){
				//add words in children
				for(int i=0; i< children.length; i++){
					if(children[i] != null)
						//add list items to list
						list.addAll(children[i].getWords());
				}
			}
			return list;
		}

		//this node and its parents toString
		public String toString(){
			if(this.parent == null)
				return "";
			else{
				return this.parent.toString() + new String(new char[] {this.character});
			}
		}

	}//end node

	private Node root;

	public MyTrie(){
		this.root = new Node();
	}
	//add word to trie
	public void addWord(String word){
		root.addWord(word.toLowerCase());
	}

	//get the words in trie with prefix
	public List getWords(String prefix){
		Node lastNode = root;
		for(int i=0; i< prefix.length(); i++){
			lastNode = lastNode.getChildNode(prefix.charAt(i));

			//a letter in prefix not found, no match
			if(lastNode == null)
				return new ArrayList();
		}

		return lastNode.getWords(); //from lastnode print its parents
	}

	public static void main(String [] args){
		MyTrie obj = new MyTrie();
		obj.addWord("apple");
		obj.addWord("able");
		obj.addWord("bee");
		obj.addWord("children");
		obj.addWord("child");
		obj.addWord("abort");

		List list = obj.getWords("");
		for(int i=0; i< list.size(); i++){
			System.out.println(list.get(i));
		}
	}
}