/**
Least Recently Used Cache:
Design and implement a data structure for Least Recently Used (LRU) cache. It should support the following operations: get and set.

get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
set(key, value) - Set or insert the value if the key is not already present. When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.
*/

import java.util.*;
import java.io.*;

/**
Solution: Use doubleLinkedList; set topNode to be LeastRecently Used, and BottomNode to be MostRecently Used. So get() and Set() will both move Node to Bottom.
*/
public class LRUCache {
    //use double linkedlist
    public class Node {
        int val;
        int key;
        Node next;
        Node prev;
        Node(int key, int val) {
            this.val = val;
            this.key = key;
            this.next = null;
            this.prev = null;
        }
    }

    private int capacity;
    private Node top = null; //most recently used, to pop next
    private Node bottom = null; //least recently used, pop last
    private HashMap<Integer, Node> hm = new HashMap<Integer, Node>();

    /*
    * @param capacity: An integer
    */
    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    /*
     * @param key: An integer
     * @return: An integer
     */
    public int get(int key) {
        if(!hm.containsKey(key))
            return -1;
        Node target = hm.get(key);
        //target becomes most recently used (move to bottom);
        remove(target);
        moveToBottom(target);

        return target.val;
    }

    /*
     * @param key: An integer
     * @param value: An integer
     * @return: nothing
     */
    public void set(int key, int value) {
        //if already there, set to new value, and move to bottom.
        if(hm.containsKey(key)){
            Node target = hm.get(key);
            target.val = value;
            //target becomes most recently used (move to bottom);
            remove(target);
            moveToBottom(target);
        }else{
            //insert new node with key,value;
            Node newNode = new Node(key, value);
            //if reached capacity, pop top node;
            if(hm.size() >= this.capacity){
                //pop top node
                hm.remove(this.top.key);
                remove(this.top);
                //newNode just modified, move to bottom
                moveToBottom(newNode);

            }else{
                //newNode to bottom, w/o remove anything
                moveToBottom(newNode);
            }
            //add key in map for newNode
            hm.put(key, newNode);
        }
    }

    public void remove(Node target){
        if(target.prev != null){
            target.prev.next = target.next;
        }else{
            //if prev = null; meaning it's the topNode;
            this.top = target.next;
        }

        if(target.next != null){
            target.next.prev = target.prev;
        }else{
            //if next = null; meaning it's the bottomNode;
            this.bottom = target.prev;
        }
    }

    public void moveToBottom(Node target){
        target.prev = bottom;
        target.next = null;
        if(this.bottom != null){
            this.bottom.next = target;
        }
        this.bottom = target;

        //if only one element
        if(this.top == null){
            this.top = this.bottom;
        }
    }

    public void printCache(){
        Node cur = this.top;
        while(cur != null){
            System.out.print(cur.val + "<->");
            cur = cur.next;
        }
        System.out.println();
    }

    public static void main(String [] args){
        LRUCache obj = new LRUCache(2);
        obj.set(2,1);
        obj.set(1,1);
        obj.get(2);
        obj.set(4,1);
        obj.get(1);
        obj.get(2);

        obj.printCache();
     }


}

