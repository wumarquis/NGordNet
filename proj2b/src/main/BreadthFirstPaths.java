package main;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;

public class BreadthFirstPaths {
    private Graph wnGraph;
    private boolean[] marked;
    private int[] edgeTo;
    private HashSet<Integer> hypSet;
    public BreadthFirstPaths(Graph g) {
        wnGraph = g;
        marked = new boolean[g.size()];
        edgeTo = new int[g.size()];
        hypSet = new HashSet<>();
    }
    public void bfs(Graph G, int s) {
        Queue<Integer> fringe = new PriorityQueue<>();
        fringe.add(s);
        marked[s] = true;
        while (!fringe.isEmpty()) {
            int v = fringe.poll();
            hypSet.add(v);
            if (G.traverse(v) != null) {
                for (int w : G.traverse(v)) {
                    if (!marked[w]) {
                        fringe.add(w);
                        marked[w] = true;
                        edgeTo[w] = v;
                    }
                }
            }
        }
    }
    public HashSet<Integer> returnHyp() {
        return hypSet;
    }
}
