package bayes;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class BayesTests {
    private final static double PRECISION = 0.0000000000000000000000001;
    private final static String[] CATEGORIES = {"America", "Europe"};
    private final static String[] WORDS = {"New", "York", "Mexico", "San", "Francisco", "Denver", "Washington", "Chicago", "Michigan",
            "Seattle", "Orleans", "London", "Dover", "Castle", "Manchester", "Kyiv", "Brest", "Budapest"};

    @Test
    public void trainCategoryProbabilityTest() {
        Map<String, String[]> texts = createTrainTexts();

        Map<String, Double> categoryProbabilities = new HashMap<>();
        categoryProbabilities.put(CATEGORIES[0], 5.0 / 9.0);
        categoryProbabilities.put(CATEGORIES[1], 4.0 / 9.0);

        Bayes bayes = new Bayes();
        Map<String, Double> actual = bayes.trainCategoryProbability(texts);

        Assert.assertEquals(categoryProbabilities.size(), actual.size());
        for (String category : CATEGORIES) {
            Assert.assertEquals(categoryProbabilities.get(category), actual.get(category), PRECISION);
        }
    }

    @Test
    public void trainWordInCategoryProbabilityTest() {
        Map<String, String[]> texts = createTrainTexts();

        Map<String, Double> americaWords = new HashMap<>();
        americaWords.put(WORDS[0], (7 + 1) * 1.0 / (7 + 1 + WORDS.length));
        americaWords.put(WORDS[1], (5 + 1) * 1.0 / (5 + 1 + WORDS.length));
        americaWords.put(WORDS[2], (2 + 1) * 1.0 / (2 + WORDS.length));
        americaWords.put(WORDS[3], (1 + 1) * 1.0 / (1 + WORDS.length));
        americaWords.put(WORDS[4], (1 + 1) * 1.0 / (1 + WORDS.length));
        americaWords.put(WORDS[5], (1 + 1) * 1.0 / (1 + WORDS.length));
        americaWords.put(WORDS[6], (1 + 1) * 1.0 / (1 + WORDS.length));
        americaWords.put(WORDS[7], (2 + 1) * 1.0 / (2 + WORDS.length));
        americaWords.put(WORDS[8], (1 + 1) * 1.0 / (1 + WORDS.length));
        americaWords.put(WORDS[9], (1 + 1) * 1.0 / (1 + WORDS.length));
        americaWords.put(WORDS[10], 1.0 / (1 + WORDS.length));
        americaWords.put(WORDS[11], 1.0 / (4 + WORDS.length));
        americaWords.put(WORDS[12], 1.0 / (1 + WORDS.length));
        americaWords.put(WORDS[13], 1.0 / (1 + WORDS.length));
        americaWords.put(WORDS[14], 1.0 / (1 + WORDS.length));
        americaWords.put(WORDS[15], 1.0 / (2 + WORDS.length));
        americaWords.put(WORDS[16], 1.0 / (1 + WORDS.length));
        americaWords.put(WORDS[17], 1.0 / (1 + WORDS.length));

        Map<String, Double> europeWords = new HashMap<>();
        europeWords.put(WORDS[0], (1 + 1) * 1.0 / (7 + 1 + WORDS.length));
        europeWords.put(WORDS[1], (1 + 1) * 1.0 / (5 + 1 + WORDS.length));
        europeWords.put(WORDS[2], 1.0 / (2 + WORDS.length));
        europeWords.put(WORDS[3], 1.0 / (1 + WORDS.length));
        europeWords.put(WORDS[4], 1.0 / (1 + WORDS.length));
        europeWords.put(WORDS[5], 1.0 / (1 + WORDS.length));
        europeWords.put(WORDS[6], 1.0 / (1 + WORDS.length));
        europeWords.put(WORDS[7], 1.0 / (2 + WORDS.length));
        europeWords.put(WORDS[8], 1.0 / (1 + WORDS.length));
        europeWords.put(WORDS[9], 1.0 / (1 + WORDS.length));
        europeWords.put(WORDS[10], (1 + 1) * 1.0 / (1 + WORDS.length));
        europeWords.put(WORDS[11], (4 + 1) * 1.0 / (4 + WORDS.length));
        europeWords.put(WORDS[12], (1 + 1) * 1.0 / (1 + WORDS.length));
        europeWords.put(WORDS[13], (1 + 1) * 1.0 / (1 + WORDS.length));
        europeWords.put(WORDS[14], (1 + 1) * 1.0 / (1 + WORDS.length));
        europeWords.put(WORDS[15], (2 + 1) * 1.0 / (2 + WORDS.length));
        europeWords.put(WORDS[16], (1 + 1) * 1.0 / (1 + WORDS.length));
        europeWords.put(WORDS[17], (1 + 1) * 1.0 / (1 + WORDS.length));

        Map<String, Map<String, Double>> wordInCategoryProbability = new HashMap<>();
        wordInCategoryProbability.put(CATEGORIES[0], americaWords);
        wordInCategoryProbability.put(CATEGORIES[1], europeWords);

        Bayes bayes = new Bayes();
        Map<String, Map<String, Double>> actual = bayes.trainWordInCategoryProbability(texts);

        Assert.assertEquals(wordInCategoryProbability.size(), actual.size());
        for (String category : CATEGORIES) {
            Assert.assertEquals(wordInCategoryProbability.get(category).size(), actual.get(category).size());
            for (String word : WORDS) {
                Assert.assertEquals(wordInCategoryProbability.get(category).get(word), actual.get(category).get(word), PRECISION);
            }
        }
    }

    @Test
    public void testTest() {
        Map<String, String[]> texts = createTrainTexts();
        Bayes bayes = new Bayes();
        bayes.train(texts);

        Map<String, Double> probabilities = new HashMap<>();
        Double americaProbability = (5.0 / 9.0) * (8.0 / (8.0 + WORDS.length)) * (8.0 / (8.0 + WORDS.length)) * (8.0 / (8.0 + WORDS.length))
                * (6.0 / (6.0 + WORDS.length)) * (3.0 / (2.0 + WORDS.length)) * (1.0 / (4.0 + WORDS.length)) * (1.0 / (1.0 + WORDS.length))
                * (1.0 / (2.0 + WORDS.length)) * (1.0 / WORDS.length);
        probabilities.put(CATEGORIES[0], americaProbability);
        Double europeProbability = (4.0 / 9.0) * (2.0 / (8.0 + WORDS.length)) * (2.0 / (8.0 + WORDS.length)) * (2.0 / (8.0 + WORDS.length))
                * (2.0 / (6.0 + WORDS.length)) * (1.0 / (2.0 + WORDS.length)) * (5.0 / (4.0 + WORDS.length)) * (2.0 / (1.0 + WORDS.length))
                * (3.0 / (2.0 + WORDS.length)) * (1.0 / WORDS.length);
        probabilities.put(CATEGORIES[1], europeProbability);

        Map<String, Double> actual = bayes.test(createTestText());

        Assert.assertEquals(probabilities.size(), actual.size());
        for (String category : CATEGORIES) {
            Assert.assertEquals(probabilities.get(category), actual.get(category), PRECISION);
        }
    }

    private Map<String, String[]> createTrainTexts() {
        Map<String, String[]> texts = new HashMap<>();


        String[] americaTexts = {
                WORDS[0] + " " + WORDS[1] + " " + WORDS[0] + " " + WORDS[2],
                WORDS[0] + " " + WORDS[1] + " " + WORDS[3] + " " + WORDS[4] + " " + WORDS[5],
                WORDS[6] + " " + WORDS[0] + " " + WORDS[1] + " " + WORDS[7],
                WORDS[8] + " " + WORDS[9] + " " + WORDS[0] + " " + WORDS[1] + " " + WORDS[0] + " " + WORDS[2],
                WORDS[0] + " " + WORDS[1] + " " + WORDS[7]
        };
        String[] europeTexts = {
                WORDS[1] + " " + WORDS[10] + " " + WORDS[11] + " " + WORDS[12],
                WORDS[11] + " " + WORDS[0] + " " + WORDS[13] + " " + WORDS[11] + " " + WORDS[14],
                WORDS[15] + " " + WORDS[16],
                WORDS[11] + " " + WORDS[15] + " " + WORDS[17]
        };

        texts.put(CATEGORIES[0], americaTexts);
        texts.put(CATEGORIES[1], europeTexts);

        return texts;
    }

    private String createTestText() {
        return WORDS[0] + " " + WORDS[1] + " " + WORDS[0] + " " + WORDS[2] + " " + WORDS[15] + " " + WORDS[11] + " "
                + WORDS[0] + " " + WORDS[13] + " " + "Warsaw";
    }
}
