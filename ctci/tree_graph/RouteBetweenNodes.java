/**
4.1 Route Between Nodes: Given a directed graph, design an algorithm to find out whether there is a route between two nodes.
Hints: #127
*/

import graphpackage.*;
import java.util.*;

public class RouteBetweenNodes{

	public boolean findRoute(AdjListGraph graph, AdjListGraph.Node n1, AdjListGraph.Node n2){
		if(n1 == null || n2 == null)
			return false;
		if( DFS(graph, n1, n2) == true)
			return true;
		else
			return DFS(graph, n2, n1);
	}

	public boolean DFS(AdjListGraph graph, AdjListGraph.Node root, AdjListGraph.Node target){
		if(root == target)
			return true;
		//clear all properties to research
		for(int i = 0; i< graph.sizeNow; i++){
			graph.nodes[i].distance = Integer.MAX_VALUE;
			graph.nodes[i].parent = null;
			graph.nodes[i].visited = false;
		}
		//root.visited = true;
		Stack<AdjListGraph.Node> stack = new Stack<AdjListGraph.Node>();
		stack.push(root);
		while(!stack.isEmpty()){
			AdjListGraph.Node cur = stack.pop();
			System.out.println("cur " + cur.data);
			if(!cur.visited){
				if(cur == target)
					return true;
				cur.visited = true;
				for(AdjListGraph.Node child : cur.children){
					System.out.println("child: " + child.data + " " + child.visited);
					if(!child.visited) //the last child pushed is visited first
						stack.push(child);
				}
			}

		}//end while
		return false;
	}//end DFS

	public static void main(String [] args){
		RouteBetweenNodes obj = new RouteBetweenNodes();
		AdjListGraph graph = new AdjListGraph();
		AdjListGraph.Node nodeA = graph.new Node("A");
		AdjListGraph.Node nodeB = graph.new Node("B");
		AdjListGraph.Node nodeC = graph.new Node("C");
		AdjListGraph.Node nodeD = graph.new Node("D");
		AdjListGraph.Node nodeE = graph.new Node("E");
		AdjListGraph.Node nodeF = graph.new Node("F");
		AdjListGraph.Node nodeG = graph.new Node("G");
		AdjListGraph.Node nodeH = graph.new Node("H");

		nodeA.children.add(nodeC);
		nodeB.children.add(nodeA);
		nodeB.children.add(nodeF);
		nodeC.children.add(nodeA);
		nodeC.children.add(nodeD);
		nodeC.children.add(nodeG);
		nodeD.children.add(nodeC);
		nodeE.children.add(nodeG);
		nodeH.children.add(nodeB);
		nodeH.children.add(nodeF);

		graph.add( nodeA ); 
		graph.add( nodeB); 
		graph.add( nodeC ); 
		graph.add( nodeD ); 
		graph.add( nodeE ); 
		graph.add( nodeF ); 
		graph.add( nodeG ); 
		graph.add( nodeH ); 

		System.out.println(obj.findRoute(graph, nodeD, nodeH));
	}
}