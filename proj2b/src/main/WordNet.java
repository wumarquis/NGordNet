package main;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class WordNet {
    Graph graph;
    HashMap<String, HashSet<Integer>> wordsToNum;
    HashMap<Integer, String[]> numToWords;
    public WordNet(String hyponymsFileName, String synsetsFileName) {
        In hyponymFile = new In(hyponymsFileName);
        In synsetFile = new In(synsetsFileName);
        wordsToNum = new HashMap<>();
        numToWords = new HashMap<>();
        while (!synsetFile.isEmpty()) {
            String line = synsetFile.readLine();
            String[] splitLine = line.split(",");
            String word = splitLine[1];
            int number = Integer.parseInt(splitLine[0]);
            String[] splitWord = word.split(" ");
            for (String s : splitWord) {
                if (wordsToNum.containsKey(s)) {
                    Set<Integer> nums = wordsToNum.get(s);
                    nums.add(number);
                } else {
                    wordsToNum.put(s, new HashSet<>());
                    Set<Integer> nums = wordsToNum.get(s);
                    nums.add(number);
                }
            }
            numToWords.put(number, splitWord);
        }
        graph = new Graph();
        while (!hyponymFile.isEmpty()) {
            String line = hyponymFile.readLine();
            String[] splitLine = line.split(",");
            int word = Integer.parseInt(splitLine[0]);
            for (int i = 1; i < splitLine.length; i++) {
                int hyponym = Integer.parseInt(splitLine[i]);
                graph.addEdge(word, hyponym);
            }
        }
    }
    public HashSet<String> getHyponyms(String s) {
        if (!wordsToNum.containsKey(s)) {
            return new HashSet<>();
        }
        BreadthFirstPaths b = new BreadthFirstPaths(graph);
        HashSet<Integer> syn = wordsToNum.get(s);
        HashSet<String> hypString = new HashSet<>();
        Iterator<Integer> iter = syn.iterator();
        while (iter.hasNext()) {
            int syns = iter.next();
            b.bfs(graph, syns);
            HashSet<Integer> hyps = b.returnHyp();
            Iterator<Integer> hypsIter = hyps.iterator();
            while (hypsIter.hasNext()) {
                int hypsNum = hypsIter.next();
                String[] wordsFromNum = numToWords.get(hypsNum);
                hypString.addAll(Arrays.asList(wordsFromNum));
            }
        }
        return hypString;
    }
}
