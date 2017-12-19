package grammar;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GrammarTests {
    private static final String[] RULES = {
            "NP VP S",
            "NP PP NP",
            "P NP PP",
            "V NP VP",
            "ADJ NP NP"
    };

    @Test
    public void countTypeTableTest() {
        String sentence = "NP V NP P NP P NP";

        List[][] typeTable = new List[7][];
        for (int i = 0; i < 7; i++) {
            typeTable[i] = new List[7 - i];
            for (int j = 0; j < typeTable[i].length; j++) {
                typeTable[i][j] = new ArrayList<>();
            }
        }
        typeTable[0][0].add(new WordFormation(WordType.NP));
        typeTable[0][1].add(new WordFormation(WordType.V));
        typeTable[0][2].add(new WordFormation(WordType.NP));
        typeTable[0][3].add(new WordFormation(WordType.P));
        typeTable[0][4].add(new WordFormation(WordType.NP));
        typeTable[0][5].add(new WordFormation(WordType.P));
        typeTable[0][6].add(new WordFormation(WordType.NP));

        typeTable[1][1].add(new WordFormation(WordType.VP, 0, 1, 0, 0, 2, 0));
        typeTable[1][3].add(new WordFormation(WordType.PP, 0, 3, 0, 0, 4, 0));
        typeTable[1][5].add(new WordFormation(WordType.PP, 0, 5, 0, 0, 6, 0));

        typeTable[2][0].add(new WordFormation(WordType.S, 0, 0, 0, 1, 1, 0));
        typeTable[2][2].add(new WordFormation(WordType.NP, 0, 2, 0, 1, 3, 0));
        typeTable[2][4].add(new WordFormation(WordType.NP, 0, 4, 0, 1, 5, 0));

        typeTable[3][1].add(new WordFormation(WordType.VP, 0, 1, 0, 2, 2, 0));
        typeTable[3][3].add(new WordFormation(WordType.PP, 0, 3, 0, 2, 4, 0));

        typeTable[4][0].add(new WordFormation(WordType.S, 0, 0, 0, 3, 1, 0));
        typeTable[4][2].add(new WordFormation(WordType.NP, 0, 2, 0, 3, 3, 0));
        typeTable[4][2].add(new WordFormation(WordType.NP, 2, 2, 0, 1, 5, 0));

        typeTable[5][1].add(new WordFormation(WordType.VP, 0, 1, 0, 4, 2, 0));
        typeTable[5][1].add(new WordFormation(WordType.VP, 0, 1, 0, 4, 2, 1));

        typeTable[6][0].add(new WordFormation(WordType.S, 0, 0, 0, 5, 1, 0));
        typeTable[6][0].add(new WordFormation(WordType.S, 0, 0, 0, 5, 1, 1));

        Grammar grammar = new Grammar(RULES);
        List<WordFormation>[][] actual = grammar.countTypeTable(sentence);

        for (int i = 0; i < typeTable.length; i++) {
            for (int j = 0; j < typeTable[i].length; j++) {
                Assert.assertEquals("at [" + i + "][" + j + "]", typeTable[i][j], actual[i][j]);
            }
        }
    }

    @Test
    public void buildTreesTest() {
        String sentence = "NP V NP P NP P NP";
        Tree NP = new Tree(WordType.NP);
        Tree V = new Tree(WordType.V);
        Tree P = new Tree(WordType.P);
        Tree PP_P_NP = new Tree(WordType.PP, new Tree(WordType.P), new Tree(WordType.NP));
        Tree first = new Tree(WordType.S, NP, new Tree(WordType.VP, V, new Tree(WordType.NP, NP,
                new Tree(WordType.PP, P, new Tree(WordType.NP, NP, PP_P_NP)))));
        Tree second = new Tree(WordType.S, NP, new Tree(WordType.VP, V, new Tree(WordType.NP,
                new Tree(WordType.NP, NP, PP_P_NP), PP_P_NP)));

        List<Tree> trees = new ArrayList<>();
        trees.add(first);
        trees.add(second);

        Grammar grammar = new Grammar(RULES);
        List<WordFormation>[][] typeTable = grammar.countTypeTable(sentence);

        List<Tree> actual = grammar.buildTrees(typeTable);

        Assert.assertEquals(trees.size(), actual.size());
        for (Tree tree : trees) {
            Assert.assertTrue(actual.contains(tree));
        }
    }

    @Test
    public void resolveSentenceTest() {
        String sentence = "NP V ADJ NP P NP P NP";
        List<Tree> trees = new ArrayList<>();
        Tree NP = new Tree(WordType.NP);
        Tree ADJ = new Tree(WordType.ADJ);
        Tree V = new Tree(WordType.V);
        Tree P = new Tree(WordType.P);
        Tree PP_P_NP = new Tree(WordType.PP, P, NP);
        Tree NP_ADJ_NP = new Tree(WordType.NP, ADJ, NP);
        Tree NP_NP_PP = new Tree(WordType.NP, NP, PP_P_NP);
        Tree PP_P_NP_ = new Tree(WordType.PP, P, NP_NP_PP);
        Tree tree = new Tree(WordType.S, NP, new Tree(WordType.VP, V, new Tree(WordType.NP, ADJ,
                new Tree(WordType.NP, NP, PP_P_NP_))));
        trees.add(tree);
        tree = new Tree(WordType.S, NP, new Tree(WordType.VP, V, new Tree(WordType.NP, ADJ,
                new Tree(WordType.NP, NP_NP_PP, PP_P_NP))));
        trees.add(tree);
        tree = new Tree(WordType.S, NP, new Tree(WordType.VP, V, new Tree(WordType.NP, NP_ADJ_NP, PP_P_NP_)));
        trees.add(tree);
        tree = new Tree(WordType.S, NP, new Tree(WordType.VP, V, new Tree(WordType.NP,
                new Tree(WordType.NP, ADJ, NP_NP_PP), PP_P_NP)));
        trees.add(tree);
        tree = new Tree(WordType.S, NP, new Tree(WordType.VP, V, new Tree(WordType.NP,
                new Tree(WordType.NP, NP_ADJ_NP, PP_P_NP), PP_P_NP)));
        trees.add(tree);


        Grammar grammar = new Grammar(RULES);
        List<WordFormation>[][] typeTable = grammar.countTypeTable(sentence);

        List<Tree> actual = grammar.buildTrees(typeTable);

        Assert.assertEquals(5, actual.size());
        for (Tree tree1 : trees) {
            Assert.assertTrue(actual.contains(tree1));
        }
    }
}
