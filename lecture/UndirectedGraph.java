import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

public class UndirectedGraph implements Graph{
	private List<Integer>[] items;
	private int vsize;
	private int esize;
	private Set<Edge> edges;

	public UndirectedGraph(int n){
		items = new List[n];
		vsize = n;
		esize = 0;
		for(int i = 0; i < n; i++){
			items[i] = new ArrayList<>();
		}
		edges = new HashSet<>();
	}

	@Override
	public Set<Edge> getEdges(){
		return edges;
	}

	@Override
	public void addEdge(int u, int v){
		if(!connected(u, v)){
			esize += 1;
		items[u].add(v);
		items[v].add(u);
		edges.add(new Edge(u, v, 1));
	}
	} 

	@Override
	public int V(){
		return vsize;
	}

	@Override
	public int E(){
		return esize;
	}

	@Override
	public boolean connected(int u, int v){
		return items[u].contains(v);
	}

	@Override
	public Set<Integer> adj(int u){
		Set<Integer> u_adj = new HashSet<>();
		for(int i = vsize - 1; i >= 0; i--){
			if(connected(u, i)){
				u_adj.add(i);
			}
		}
		return u_adj;
	}

	public void dfs(){
		int[] edgeTo = new int[vsize];
		Set<Integer> marked = new HashSet<>();
		Stack<Integer> fringe = new Stack<>();
		fringe.push(0);
		marked.add(0);
		while(!fringe.empty()) {
			int current = fringe.pop();
			System.out.print(current + " ");
			for(Integer neighbor: adj(current)){
				if(!marked.contains(neighbor)){
					fringe.push(neighbor);
					edgeTo[neighbor] = current;
					marked.add(neighbor);
				}
			}
		}
	}

	public void bfs(){
		int[] edgeTo = new int[vsize];
		int[] distTo = new int[vsize];
		Queue<Integer> fringe = new LinkedList<>();
		Set<Integer> marked = new HashSet<>();
		fringe.offer(0);
		marked.add(0);
		distTo[0] = 0;
		edgeTo[0] = 0;
		while(fringe.size() != 0){
			int current = fringe.poll();
			System.out.print(current + " ");
			for(Integer neighbor: adj(current)){
				if(!marked.contains(neighbor)){
					marked.add(neighbor);
					fringe.add(neighbor);
					edgeTo[neighbor] = current;
					distTo[neighbor] = distTo[current] + 1;
				}
			}
		}
		for(int i = 0; i < vsize; i++){
			System.out.println(i + " edgeTo:" + edgeTo[i] + " distTo:" + distTo[i]);
		}
	}


	public static void main(String[] args){
		UndirectedGraph udg = new UndirectedGraph(9);
		udg.addEdge(0, 1);
		udg.addEdge(1, 2);
		udg.addEdge(1, 4);
		udg.addEdge(2, 5);
		udg.addEdge(4, 5);
		udg.addEdge(3, 4);
		udg.addEdge(5, 8);
		udg.addEdge(6, 7);
		udg.addEdge(5, 6);
		udg.dfs();
		System.out.println();
		//udg.bfs();
	}
}