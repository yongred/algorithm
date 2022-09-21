/**
 * Given a file of currency conversion rates, write a function that converts one currency to another.
 *
 * Sample log file entries:
 * {"from":"USD", "to":"EUR", "rate":1.1}
 * {"from":"EUR", "to":"GBP", "rate":1.2}
 *
 * Means USD is worth 1.32 GBP
 *
 * NOTES for interviewer:
 * Requires the candidate to ask clarifying questions on the intention. Sometimes they will miss the fact that you have to convert to intermediate currencies. If they miss this, direct them as an interviewer. Clarify the question is not to have the candidate find the BEST conversion, but to find A conversion.
 * No domain knowledge needed, we could re-name the currencies to ABC and it would still have the same details to solve it. currency exchange is a universal topic
 *
 * Follow up questions as the interviewer for L5 SDE data points:
 * Get through problem and then identify 2 edge cases
 * 1) what if there is not a conversion rate that exists and then
 * 2) they should recognize that each currency conversion rate in the file is given 2 pieces of information. This is the cycle problem, and code for this.
 */

/*
Similar to Leetcode "Evaluate Division"
Qs:
- Input?
  - currencies [["USD", "EUR"], ["EUR", "GBP"]]
  - ratio [1.1, 1.2]
  - convertingFromCurrency, ex: "USD"
  - convertingToCurrency, ex: "GBP"
- Output
  - ratio of the two currency
  - Decimal point precision? 2 decimal points.
- What if no rate for input currencies?
  - Return -1

Solution:
- Step1: Construct a graph using map
  - USD -> {EUR, 1.1}, EUR -> {GBP, 1.2}
  - Two directions: USD -> {EUR, 1.1}, EUR -> {USD, 0.91}
- Step2: use DFS to traverse the graph
  - from inputs currency1 to currency2
  - multiply the edge value {EUR, 1.1} = 1.1 each time it traverse
  - mark the EDGE as visited.
  - Visited represents the edge direction like "USD:EUR"
  - Termination condition:
    - node visited, return -1
    - from == to, return 1 for ratio
 */

package problem_solving;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CurrencyConvertion {

    public double getRatioOfTheCurrencies(String fromCurrency, String toCurrency,
                                          List<List<String>> currencyPairs, double[] ratios) {
        Map<String, Map<String, Double>> ratioGraph = constructRatioGraph(currencyPairs, ratios);
        // traverse the graph using DFS fromCurrency -> toCurrency, return accumulated ratio.
        Set<String> visitedEdges = new HashSet<>();
        return depthFirstSearchRatio(fromCurrency, toCurrency, ratioGraph, visitedEdges);
    }

    private double depthFirstSearchRatio(String fromCurrency, String toCurrency,
                                         Map<String, Map<String, Double>> ratioGraph, Set<String> visitedEdges) {
        String ratioEdge = fromCurrency + ":" + toCurrency;
        if (visitedEdges.contains(ratioEdge) || !ratioGraph.containsKey(fromCurrency) ||
                !ratioGraph.containsKey(toCurrency)) {
            return -1.0;
        }
        if (fromCurrency.equals(toCurrency)) {
            return 1.0;
        }
        double ratioForTargetCurrencies = -1.0;
        visitedEdges.add(ratioEdge);

        Map<String, Double> neighborEdges = ratioGraph.get(fromCurrency);
        for (String neighborCurrency : neighborEdges.keySet()) {
            double ratioFromNeighborToTarget = depthFirstSearchRatio(
                    neighborCurrency, toCurrency, ratioGraph, visitedEdges);
            if (Double.compare(ratioFromNeighborToTarget, -1.0) != 0) {
                double ratioCurrencyToNeighbor = neighborEdges.get(neighborCurrency);
                ratioForTargetCurrencies = ratioCurrencyToNeighbor * ratioFromNeighborToTarget;
                break;
            }
        }

        return ratioForTargetCurrencies;
    }

    private Map<String, Map<String, Double>> constructRatioGraph(
            List<List<String>> currencyPairs, double[] ratios) {
        Map<String, Map<String, Double>> ratioGraph = new HashMap<>();
        for (int i = 0; i < currencyPairs.size(); i++) {
            List<String> currencyPair = currencyPairs.get(i);
            String fromCurrency = currencyPair.get(0);
            String toCurrency = currencyPair.get(1);
            double ratio = ratios[i];

            connectNodesWithRatioEdge(fromCurrency, toCurrency, ratio, ratioGraph);
            connectNodesWithRatioEdge(toCurrency, fromCurrency, 1.0/ratio, ratioGraph);
        }

        return ratioGraph;
    }

    private void connectNodesWithRatioEdge(String fromCurrency, String toCurrency, double ratio,
                                           Map<String, Map<String, Double>> ratioGraph) {
        ratioGraph.putIfAbsent(fromCurrency, new HashMap<>());
        Map<String, Double> ratioEdge = ratioGraph.get(fromCurrency);
        ratioEdge.put(toCurrency, ratio);
    }

    public static void main(String[] args) {
        List<List<String>> currencyPairs = Arrays.asList(
                Arrays.asList(new String[] {"USD", "EUR"}),
                Arrays.asList(new String[] {"EUR", "GBP"})
        );
        double[] ratios = new double[] {1.1, 1.2};

        CurrencyConvertion object = new CurrencyConvertion();
        String fromCurrency = "USD";
        String toCurrency = "GBP";
        double result = object.getRatioOfTheCurrencies(fromCurrency, toCurrency, currencyPairs, ratios);
        System.out.println("From " + fromCurrency + " to " + toCurrency + " = " + result);
    }
}
