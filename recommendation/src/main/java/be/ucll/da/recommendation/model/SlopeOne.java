package be.ucll.da.recommendation.model;

import java.util.*;

/**
 * Daniel Lemire
 * A simple implementation of the weighted slope one
 * algorithm in Java for item-based collaborative
 * filtering.
 * Assumes Java 1.5.
 * <p>
 * See main function for example.
 * <p>
 * June 1st 2006.
 * Revised by Marco Ponzi on March 29th 2007
 */

public class SlopeOne {
    private Map<User, Map<Item, Float>> data;
    private Map<Item, Map<Item, Float>> diffMatrix;
    private Map<Item, Map<Item, Integer>> frequencyMatrix;

    public SlopeOne(Map<User, Map<Item, Float>> data) {
        this.data = data;
        buildDiffMatrix();
    }

    /**
     * Based on existing data, and using weights,
     * try to predict all missing ratings.
     * The trick to make this more scalable is to consider
     * only diffMatrix entries having a large  (>1) frequencyMatrix
     * entry.
     * <p>
     * It will output the prediction 0 when no prediction is possible.
     */
    public Map<Item, Float> predict(Map<Item, Float> user) {
        Map<Item, Float> predictions = new HashMap<>();
        Map<Item, Integer> frequencies = new HashMap<>();

        for (Item j : diffMatrix.keySet()) {
            frequencies.put(j, 0);
            predictions.put(j, 0.0f);
        }

        for (Item j : user.keySet()) {
            for (Item k : diffMatrix.keySet()) {
                try {
                    float newValue = (diffMatrix.get(k).get(j) + user.get(j)) * frequencyMatrix.get(k).get(j);
                    predictions.put(k, predictions.get(k) + newValue);
                    frequencies.put(k, frequencies.get(k) + frequencyMatrix.get(k).get(j));
                } catch (NullPointerException ignored) {
                }
            }
        }

        Map<Item, Float> cleanpredictions = new HashMap<>();
        for (Item j : predictions.keySet()) {
            if (frequencies.get(j) > 0) {
                cleanpredictions.put(j, predictions.get(j) / frequencies.get(j));
            }
        }
        for (Item j : user.keySet()) {
            cleanpredictions.put(j, user.get(j));
        }
        return cleanpredictions;
    }

    /**
     * Based on existing data, and not using weights,
     * try to predict all missing ratings.
     * The trick to make this more scalable is to consider
     * only diffMatrix entries having a large  (>1) frequencyMatrix
     * entry.
     */
    public Map<Item, Float> weightlesspredict(Map<Item, Float> user) {
        Map<Item, Float> predictions = new HashMap<>();
        for (Item j : diffMatrix.keySet()) {
            predictions.put(j, 0.0f);
        }
        for (Item j : user.keySet()) {
            for (Item k : diffMatrix.keySet()) {
                //System.out.println("Average diff between "+j+" and "+ k + " is "+diffMatrix.get(k).get(j).floatValue()+" with n = "+frequencyMatrix.get(k).get(j).floatValue());
                float newval = diffMatrix.get(k).get(j) + user.get(j);
                predictions.put(k, predictions.get(k) + newval);
            }
        }
        for (Item j : predictions.keySet()) {
            predictions.put(j, predictions.get(j) / user.size());
        }
        for (Item j : user.keySet()) {
            predictions.put(j, user.get(j));
        }
        return predictions;
    }

    private void buildDiffMatrix() {
        diffMatrix = new HashMap<>();
        frequencyMatrix = new HashMap<>();
        // first iterate through users
        for (Map<Item, Float> user : data.values()) {
            // then iterate through user data
            for (Map.Entry<Item, Float> entry : user.entrySet()) {
                if (!diffMatrix.containsKey(entry.getKey())) {
                    diffMatrix.put(entry.getKey(), new HashMap<>());
                    frequencyMatrix.put(entry.getKey(), new HashMap<>());
                }
                for (Map.Entry<Item, Float> entry2 : user.entrySet()) {
                    int oldcount = 0;
                    if (frequencyMatrix.get(entry.getKey()).containsKey(entry2.getKey())) {
                        oldcount = frequencyMatrix.get(entry.getKey()).get(entry2.getKey());
                    }
                    float olddiff = 0.0f;
                    if (diffMatrix.get(entry.getKey()).containsKey(entry2.getKey())) {
                        olddiff = diffMatrix.get(entry.getKey()).get(entry2.getKey());
                    }
                    float observeddiff = entry.getValue() - entry2.getValue();
                    frequencyMatrix.get(entry.getKey()).put(entry2.getKey(), oldcount + 1);
                    diffMatrix.get(entry.getKey()).put(entry2.getKey(), olddiff + observeddiff);
                }
            }
        }
        for (Item j : diffMatrix.keySet()) {
            for (Item i : diffMatrix.get(j).keySet()) {
                float oldvalue = diffMatrix.get(j).get(i);
                int count = frequencyMatrix.get(j).get(i);
                diffMatrix.get(j).put(i, oldvalue / count);
            }
        }
    }
}
