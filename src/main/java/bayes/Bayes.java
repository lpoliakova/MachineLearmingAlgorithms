package bayes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Bayes {
    private Map<String, Double> categoryProbabilities;
    private Map<String, Map<String, Double>> wordInCategoryProbabilities;
    private Integer uniqueAmount;

    public void train(Map<String, String[]> texts) {
        categoryProbabilities = trainCategoryProbability(texts);
        wordInCategoryProbabilities = trainWordInCategoryProbability(texts);
    }

    public Map<String, Double> test(String text) {
        String[] words = text.split(" ");
        Map<String, Double> probabilities = new HashMap<>();
        for (Map.Entry<String, Double> category : categoryProbabilities.entrySet()) {
            Double probability = category.getValue();
            for (String word : words) {
                probability *= wordInCategoryProbabilities.get(category.getKey()).getOrDefault(word, 1.0 / uniqueAmount);
            }
            probabilities.put(category.getKey(), probability);
        }
        return probabilities;
    }

    Map<String, Double> trainCategoryProbability(Map<String, String[]> texts) {
        int amountOfTexts = texts.values().stream().mapToInt(s -> s.length).sum();
        return texts.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().length * 1.0 / amountOfTexts));
    }

    Map<String, Map<String, Double>> trainWordInCategoryProbability(Map<String, String[]> texts) {
        Map<String, String[]> words = convertTextInWords(texts);

        Map<String, Map<String, Integer>> wordsInCategory = new HashMap<>();
        Map<String, Integer> wordsEverywhere = new HashMap<>();
        for (Map.Entry<String, String[]> category : words.entrySet()) {
            Map<String, Integer> wordsInCurrentCategory = new HashMap<>();
            for (String word : category.getValue()) {
                addWord(word, wordsInCurrentCategory);
                addWord(word, wordsEverywhere);
            }
            wordsInCategory.put(category.getKey(), wordsInCurrentCategory);
        }

        uniqueAmount = wordsEverywhere.keySet().size();
        Map<String, Map<String, Double>> result = new HashMap<>();
        for (Map.Entry<String, Map<String, Integer>> category : wordsInCategory.entrySet()) {
            Map<String, Double> categoryResult = new HashMap<>();
            for (Map.Entry<String, Integer> word : wordsEverywhere.entrySet()) {
                Integer wordInCategory = category.getValue().getOrDefault(word.getKey(), 0);
                categoryResult.put(word.getKey(), (wordInCategory + 1) * 1.0 / (word.getValue() + uniqueAmount));
            }
            result.put(category.getKey(), categoryResult);
        }
        return result;
    }

    private Map<String, String[]> convertTextInWords(Map<String, String[]> texts) {
        return texts.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> convertTextInWords(e.getValue())));
    }

    private String[] convertTextInWords(String[] texts) {
        ArrayList<String> words = new ArrayList<>();
        for (String text : texts) {
            String[] wordsInText = text.split(" ");
            words.addAll(Arrays.asList(wordsInText));
        }
        return words.toArray(new String[0]);
    }

    private void addWord(String word, Map<String, Integer> words) {
        Integer count = words.getOrDefault(word, 0);
        words.put(word, ++count);
    }
}
