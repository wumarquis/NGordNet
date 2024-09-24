package main;

import java.util.*;

public class Graph {
    HashMap<Integer, Set<Integer>> connect;
    int vertices;
    public Graph() {
        connect = new HashMap<>();
    }

    public void addEdge(int par, int child) {
        if (connect.containsKey(par)) {
            Set<Integer> children = connect.get(par);
            children.add(child);
            vertices += 1;
        } else {
            connect.put(par, new HashSet<>());
            Set<Integer> children = connect.get(par);
            children.add(child);
            vertices += 2;
        }
    }

    public Iterable<Integer> traverse(int par) {
        return connect.get(par);
    }

    public int size() {
        return vertices;
    }
}
