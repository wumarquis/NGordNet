package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNet wn;
    private NGramMap ngm;
    public HyponymsHandler(WordNet wordnet, NGramMap ngrammap) {
        wn = wordnet;
        ngm = ngrammap;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();

        int startYear = q.startYear();
        int endYear = q.endYear();

        Set<String> response = wn.getHyponyms(words.get(0));

        for (String s : words) {
            response.retainAll(wn.getHyponyms(s));
        }

        if (q.k() != 0) {
            HashSet<String> popWords = new HashSet<>();
            HashMap<String, Double> popHash = new HashMap<>();
            TreeSet<Double> popSet = new TreeSet<>(Collections.reverseOrder());
            for (String hyp : response) {
                TimeSeries currTS = ngm.countHistory(hyp, startYear, endYear);
                Collection<Double> currValues = currTS.values();
                double sum = currValues.stream().mapToDouble(Double::doubleValue).sum();
                popHash.put(hyp, sum);
                popSet.add(sum);
            }
            int curr = q.k();
            for (double i : popSet) {
                if (curr == 0) {
                    break;
                } else {
                    for (String wc : popHash.keySet()) {
                        if (popHash.get(wc).equals(i)) {
                            if (i > 0.0) {
                                popWords.add(wc);
                                curr--;
                            }
                        }
                    }
                }
            }
            ArrayList<String> popArray = new ArrayList<>(popWords);
            popArray.sort(new StringComparator());
            return popArray.toString();
        }

        ArrayList<String> hypArray = new ArrayList<>(response);
        hypArray.sort(new StringComparator());

        return hypArray.toString();
    }
}
