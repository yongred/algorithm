/**
Graph: with adjacency list
*/

package graphpackage;
import java.util.*;

public class AdjListGraph{

	public class Node{
		public Node parent;
		public String data = "";
		public ArrayList<Node> children;
		public boolean visited;
		public int distance;  //distant from root
		public Node(){
			children = new ArrayList<Node>();
		}
		public Node(String data){
			this();
			this.data = data;
		}
	}
	/* ex: n[0]: -> n[2] -> n[4];
		   n[1]: -> n[0] 
		   n[2]: -> n[3] -> n[2]; etc...
	*/
	//all edges
	public Node[] nodes;
	static final int CAPACITY = 100;
	public int sizeNow;

	public AdjListGraph(){
		nodes = new Node[CAPACITY];
		sizeNow = 0;
	}

	public void add(Node node){
		if(sizeNow >= CAPACITY)
			return;
		nodes[sizeNow] = node;
		sizeNow++;
	}

	public void breadthFirstSearch(Node root){
		//clear all properties to reconstruct
		for(int i = 0; i< sizeNow; i++){
			nodes[i].distance = Integer.MAX_VALUE;
			nodes[i].parent = null;
			nodes[i].visited = false;
		}
		MyQueue<Node> queue = new MyQueue<Node>();
		root.visited = true;
		root.distance = 0;  //from node to root
		queue.enqueue(root);
		while(!queue.isEmpty()){ 
		//loop from level 1 to n, root to distance farthest
			Node cur = queue.dequeue();
			System.out.println("cur " + cur.data);
			for(Node child : cur.children){ //visit all children(not visited) of cur
				if( !child.visited ){ //if not visited

					child.visited = true;
					child.distance = cur.distance + 1;
					child.parent = cur;
					queue.enqueue(child);
				}
			}

		}
	}

	public void depthFirstSearch(Node root){
		if(root == null)
			return;
		//clear all properties to reconstruct
		for(int i = 0; i< sizeNow; i++){
			nodes[i].distance = Integer.MAX_VALUE;
			nodes[i].parent = null;
			nodes[i].visited = false;
		}

		DFSvisit(root);
	}

	public void DFSvisit(Node cur){
		System.out.println("cur " + cur.data);
		cur.visited = true;
		//visit each child, and go depth on 1 child at a time
		for(Node child : cur.children){
			if(!child.visited){
				child.parent = cur;
				DFSvisit(child); //go depth on this child
			}
		}
	}

	public void printGraph(){
		for(int i=0; i< sizeNow; i++){
			Node cur = nodes[i];
			System.out.print(cur.data + ": ");
			for(Node child : cur.children){
				System.out.print(" " + child.data + ", ");
			}
			System.out.println();
		}
	}

	public static void main(String [] args){
		AdjListGraph obj = new AdjListGraph();
		AdjListGraph.Node NodeA = obj.new Node("A");
		AdjListGraph.Node NodeB = obj.new Node("B");
		AdjListGraph.Node NodeC = obj.new Node("C");
		AdjListGraph.Node NodeD = obj.new Node("D");
		AdjListGraph.Node NodeE = obj.new Node("E");
		AdjListGraph.Node NodeF = obj.new Node("F");
		AdjListGraph.Node NodeG = obj.new Node("G");
		AdjListGraph.Node NodeH = obj.new Node("H");
		AdjListGraph.Node NodeI = obj.new Node("I");
		NodeA.children.add(NodeB);
		NodeA.children.add(NodeI);
		NodeB.children.add(NodeA);
		NodeI.children.add(NodeC);
		NodeI.children.add(NodeG);
		NodeI.children.add(NodeA);
		NodeC.children.add(NodeI);
		NodeC.children.add(NodeD);
		NodeC.children.add(NodeE);
		NodeC.children.add(NodeF);
		NodeG.children.add(NodeI);
		NodeG.children.add(NodeF);
		NodeG.children.add(NodeH);
		NodeD.children.add(NodeC);
		NodeE.children.add(NodeC);
		NodeE.children.add(NodeH);
		NodeF.children.add(NodeC);
		NodeF.children.add(NodeG);
		NodeH.children.add(NodeE);
		NodeH.children.add(NodeG);

		obj.add( NodeA ); 
		obj.add( NodeB); 
		obj.add( NodeC ); 
		obj.add( NodeD ); 
		obj.add( NodeE ); 
		obj.add( NodeF ); 
		obj.add( NodeG ); 
		obj.add( NodeH ); 
		obj.add( NodeI ); 

		obj.breadthFirstSearch(NodeA);
		obj.printGraph();
		obj.depthFirstSearch(NodeA);
		System.exit(0);
	}
}