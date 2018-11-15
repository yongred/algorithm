/**
4.7 Build Order: You are given a list of projects and a list of dependencies (which is a list of pairs of
projects, where the second project is dependent on the first project). All of a project's dependencies
must be built before the project is. Find a build order that will allow the projects to be built. If there
is no valid build order, return an error.
EXAMPLE
Input:
projects: a, b, c, d, e, f
dependencies: (a, d), (f, b), (b, d), (f, a), (d, c)
Output: f, e, a, b, d, c
Hints: #26, #47, #60, #85, # 125, # 133
*/
import java.util.*;

public class BuildOrder{
	/*
	represent a node on graph
	*/
	public class Project{
		public ArrayList<Project> children = new ArrayList<Project>();
		//use hashmap check duplicate child node
		public HashMap<String, Project> map = new HashMap<String, Project>();
		public String name; // val this node carry
		public int deps = 0; //dependencies

		public Project(String name){
			this.name = name;
		}

		public void pointsTo(Project child){
			//add child deps on this
			if(!map.containsKey(child.name)){ //not duplicated
				children.add(child);
				map.put(child.name, child); //add to map
				child.deps++;
				//System.out.println("pointsTo " + child.name + " " + child.deps);
			}
		}

	}//end project

	/*
	a directed graph
	*/
	public class Graph{
		public ArrayList<Project> nodes = new ArrayList<Project>();	
		//hashmap for checking duplicated node
		public HashMap<String, Project> map = new HashMap<String, Project>();

		//get the node contains name string
		public Project getOrCreateNode(String name){
			//if new name, create node, if not return node
			if(!map.containsKey(name)){
				Project node = new Project(name);
				nodes.add(node);
				map.put(name, node);
			}
			return map.get(name);
		}

		//node(strName) -> node(endName)
		public void addEdge(String strName, String endName){
			//System.out.println(strName + " " + endName);
			Project strNode = getOrCreateNode(strName);
			Project endNode = getOrCreateNode(endName);
			strNode.pointsTo(endNode);
		}

	}

	/*
	find order, time: O(P + D), Space: O(P + D)
	*/
	public Project [] buildOrder(String [] prjs, String [][] deps){
		//create a directed graph for project deps, T(P + D)
		Graph graph = buildGraph(prjs, deps);
		//order the graph nodes from non deps first, T(P + D)
		return orderProjects(graph.nodes);
	}

	/* 
		add prjs to graph in nodes project form, 
		set up edges for deps, T(P + D)
	*/
	public Graph buildGraph(String [] prjs, String [][] deps){
		//Space: O(P+D)
		Graph graph = new Graph();
		//add nodes to graph, T(P)
		for(String prj : prjs){
			graph.getOrCreateNode(prj);
		}
		//set up deps edges, T(D)
		for(String [] dep : deps){
			String first = dep[0];
			String second = dep[1];
			graph.addEdge(first, second);
		}
		return graph;
	}

	/* 
		create array of projects in order, T(P + D)
	*/
	public Project [] orderProjects(ArrayList<Project> prjs){
		//Space O(P)
		Project [] order = new Project[prjs.size()];
		//add roots to order arr first, T(P)
		int sizeNow = addNonDependent(order, prjs, 0);

		int curInd = 0;
		//T(P) + T(D)
		while(curInd < order.length){
			//first no dep node to check
			Project cur = order[curInd];
			//should be able to make every node independent, but if not, there is circular graph
			if(cur == null) //no independent node meaning circular
				return null;
			//remove cur deps on children
			ArrayList<Project> children = cur.children;
			for(Project child : children){
				child.deps--;
			}
			//add no deps children, next level to order after cur level node remove deps, T(D) for deps in all nodes
			sizeNow = addNonDependent(order, children, sizeNow);
			curInd++;
		}
		return order;
	}//end orderProject

	/*
	helper for add non dependent projects to order
	*/
	public int addNonDependent(Project [] order, ArrayList<Project> prjs, int offset){
		//T(P)
		for(Project prj : prjs){
			//System.out.println(prj.name + " " + prj.deps);
			if(prj.deps == 0){
				//System.out.println(prj.name);
				order[offset] = prj; //add non dep prj to order
				offset++; //increment sizeNow
			}
		}
		return offset;
	}//end addNonDependent


	public static void main(String [] args){
		BuildOrder obj = new BuildOrder();
		String [] prjs = {"a","b","c","d","e","f","g","h"};
		String [][] deps = {{"a","e"}, {"b","a"}, {"b","e"}, {"b","h"},
							{"c","a"}, {"d","g"}, {"f","a"}, 
							{"f","b"}, {"f","c"}};
		Project [] ans = obj.buildOrder(prjs, deps);
		System.out.println();
		for(Project p : ans){
			System.out.print(p.name + ", ");
		}
		System.out.println();
		Project [] ans2 = obj.buildOrder2(prjs, deps);
		for(Project p : ans2){
			System.out.print(p.name + ", ");
		}
		System.out.println();
	}
}








