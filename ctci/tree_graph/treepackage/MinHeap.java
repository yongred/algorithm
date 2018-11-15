/**
binary heap: min-heap: node.parent <= node 

heap: a specialized tree-based data structure that satisfies the 
	-heap property: If A is a parent node of B then the key (the value) of node A is ordered with respect to the key of node B with the same ordering applying across the heap.

binary-heap: 
	-Shape property: a binary heap is a complete binary tree; that is, all levels of the tree, except possibly the last one (deepest) are fully filled, and, if the last level of the tree is not complete, the nodes of that level are filled from left to right.

	-Heap property: the key stored in each node is either greater than or equal to or less than or equal to the keys in the node's children, according to some total order.
*/

package treepackage;
import java.util.*;

public class MinHeap{
	static final int CAPACITY = 1000;
	private int [] heap;
	private int elementNum; //total elements now, since skipped 0 index, it is also the largest index

	public MinHeap(){
		heap = new int[CAPACITY];
		elementNum = 0;
		//heap[1] is root
		heap[0] = 0;  //avoid index 0 for easier computation
	}

	public MinHeap(int [] arr){
		elementNum = arr.length -1;
		 buildMinHeap(arr);
		 heap = arr;
		
	}
	//return index of the parent node, @para node = index of childnode
	public int parent(int node){
		return node / 2;  //ex: 3 is parent of 6,7; 1 id parent of 2,3
	}
	//return index of the left node, @para node = index of parentNode
	public int left(int node){
		return 2 * node;
	}
	//return index of the right node, @para node = index of parentNode
	public int right(int node){
		return 2 * node + 1;
	}

	//index i is the root of subtree needed to minHeapify, O(lg n), height
	public void minHeapify(int [] arr, int i){
		int l = left(i);
		int r = right(i);
		int smallest = i; //smallest index among 3 
		//find the smallest index
		if( l <= elementNum){ //within # of elements now
			if(arr[l] < arr[smallest])
				smallest = l;
		}
		//System.out.println(smallest);
		if( r <= elementNum ){
			if(arr[r] < arr[smallest])
				smallest = r;
		}
		//if root is not the smallest, swap elements and heapify subtree of smallest
		if(smallest != i){
			swap(arr, i, smallest);
			minHeapify(arr, smallest);
		}
	}
	//heapify from bottom up, sort the array in minHeap order, O(lg n) for each, n/2 total, so n/2 * lgn = O(n lg n);
	public void buildMinHeap(int [] arr){

		int lastNotLeaf =  parent(arr.length -1); //arr.length is last element, arr.length / 2 == parent of last element with children
		for(int i= lastNotLeaf; i >= 1; i--){
			//System.out.println(i);
			minHeapify(arr, i);
		}
	}

	public void insert(int value){
		heap[++elementNum] = value;
		int ind = elementNum;
		while(ind != 1){
			minHeapify(heap, parent(ind));
		}
	}

	//return index of node deleted
	public void delete(int nodeInd){
		if(nodeInd < 1 || nodeInd > elementNum)
			return;
		heap[nodeInd] = heap[elementNum];  //put last(rightest) node in nodeind first
		heap[elementNum--] = 0; //remove last, set to default value, 
		minHeapify(heap, nodeInd); //ordered in minHeap
	}

	public void preorderWalk(int cur){
		if(cur > elementNum)
			return;
		if(heap[cur] == 0)
			return;
		System.out.print(heap[cur] + " ");
		preorderWalk( left(cur) );
		preorderWalk( right(cur) );

	}

	public void printHeap(){
		for(int i =1; i<= elementNum; i++){
			System.out.print(heap[i] + " ");
		}
		System.out.println();
	}
	//swap 2 index elements
	public void swap(int [] arr, int i, int j){
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
		//System.out.println(arr[i] + " " + arr[j]);
	}

	public int [] getHeap(){
		return heap;
	}

	public int getElementNum(){
		return elementNum;
	}

	public static void main(String [] args){
		int [] arr = {0, 90, 100, 20, 10, 50, 30, 40, 60, 80, 70};
		MinHeap obj = new MinHeap(arr);
		//obj.preorderWalk(1);
		obj.printHeap();
		obj.delete(1);
		obj.printHeap();
	}
	

}