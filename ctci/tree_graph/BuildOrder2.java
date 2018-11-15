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

public class BuildOrder2{
	/**
	 DFS solutions
	*/

	public static class Project{
		public enum State {COMP, PART, BLANK};
		public State state = State.BLANK;
		public ArrayList<Project> children = new ArrayList<Project>();
		//use hashmap check duplicate child node
		public HashMap<String, Project> map = new HashMap<String, Project>();
		public String name;
		public int deps;
		//public int deps = 0; //dependencies

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

	}

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


	Stack<Project> buildOrder(String [] prjs, String[][] deps){
		Graph graph = buildGraph(prjs, deps);  //create graph first
		return orderProjects(graph.nodes);
	}
	//
	Stack<Project> orderProjects(ArrayList<Project> prjs){
		//Space: O(P)
		Stack<Project> stack = new Stack<Project>();  //LIFO to push last level first
		//T(P) + T(D); how many nodes, and how many edges
		for(Project prj: prjs){
			if(prj.state == Project.State.BLANK){ 
				//DFS every nodes in the graph
				if( !doDFS(prj, stack) )
					return null;
			}
		}

		return stack;
	}

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
	//Time T(D): completed nodes only visited once, so number of edges
	boolean doDFS(Project prj, Stack<Project> stack){
		if(prj.state == Project.State.PART){
			//already visited but still have child not complete, circular
			return false;
		}

		if(prj.state == Project.State.BLANK){
			//not visited, see if has child to visit
			prj.state = Project.State.PART;
			ArrayList<Project> children = prj.children;
			for(Project ch : children){
				if(!doDFS(ch, stack))
					return false;
			}
			prj.state = Project.State.COMP;
			stack.push(prj);
		}
		//complete
		return true;
	}

	public static void main(String [] args){
		BuildOrder2 obj = new BuildOrder2();
		String [] prjs = {"a","b","c","d","e","f","g","h"};
		String [][] deps = {{"a","e"}, {"b","a"}, {"b","e"}, {"b","h"},
							{"c","a"}, {"d","g"}, {"f","a"}, 
							{"f","b"}, {"f","c"}};
	
		System.out.println();
		Stack<Project> ans2 = obj.buildOrder(prjs, deps);
		while(!ans2.isEmpty()){
			System.out.print(" " + ans2.pop().name);
		}
		System.out.println();
	}
}