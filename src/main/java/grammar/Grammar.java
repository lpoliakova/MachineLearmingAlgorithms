package grammar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grammar {
    private final Map<WordType, Map<WordType, WordType>> rules = new HashMap<>();

    public Grammar(String[] stringRules) {
        for (String stringRule : stringRules) {
            String[] ruleParts = stringRule.split(" ");
            Map<WordType, WordType> fromFirst = rules.getOrDefault(toType(ruleParts[0]), new HashMap<>());
            fromFirst.put(toType(ruleParts[1]), toType(ruleParts[2]));
            rules.put(toType(ruleParts[0]), fromFirst);
        }
    }

    public List<Tree> resolveSentence(String sentence) {
        List<WordFormation>[][] typeTable = countTypeTable(sentence);
        return buildTrees(typeTable);
    }

    List<WordFormation>[][] countTypeTable(String sentence) {
        List<WordFormation>[][] typeTable = initTypeTable(sentence);
        for (int line = 1; line < typeTable.length; line++) {
            List[] typeRow = new List[typeTable.length - line];
            for (int col = 0; col < typeRow.length; col++) {
                typeRow[col] = resolveWord(line, col, typeTable);
            }
            typeTable[line] = typeRow;
        }
        return typeTable;
    }

    List<Tree> buildTrees(List<WordFormation>[][] typeTable) {
        ArrayList<Tree> trees = new ArrayList<>(typeTable[typeTable.length - 1][0].size());
        for (WordFormation word : typeTable[typeTable.length - 1][0]) {
            trees.add(buildTree(word, typeTable));
        }
        return trees;
    }

    private List<WordFormation>[][] initTypeTable(String sentence) {
        String[] words = sentence.split(" ");
        List[][] typeTable = new List[words.length][];
        List[] initialRow = new List[words.length];
        for (int i = 0; i < words.length; i++) {
            List<WordFormation> type = new ArrayList<>(1);
            type.add(new WordFormation(toType(words[i])));
            initialRow[i] = type;
        }
        typeTable[0] = initialRow;
        return typeTable;
    }

    private List<WordFormation> resolveWord(int line, int col, List<WordFormation>[][] typeTable) {
        List<WordFormation> sources = new ArrayList<>();
        for (int firstSourceLine = 0; firstSourceLine < line; firstSourceLine++) {
            int firstSourceColumn = col;
            List<WordFormation> firstSource = typeTable[firstSourceLine][firstSourceColumn];
            for (int firstSourcePosition = 0; firstSourcePosition < firstSource.size(); firstSourcePosition++) {
                Map<WordType, WordType> fromFirst = rules.get(firstSource.get(firstSourcePosition).getType());
                if (fromFirst != null) {
                    int secondSourceLine = line - firstSourceLine - 1;
                    int secondSourceColumn = col + firstSourceLine + 1;
                    List<WordFormation> secondSource = typeTable[secondSourceLine][secondSourceColumn];
                    for (int secondSourcePosition = 0; secondSourcePosition < secondSource.size(); secondSourcePosition++) {
                        WordType fromSecond = fromFirst.get(secondSource.get(secondSourcePosition).getType());
                        if (fromSecond != null) {
                            sources.add(new WordFormation(fromSecond,
                                    firstSourceLine, firstSourceColumn, firstSourcePosition,
                                    secondSourceLine, secondSourceColumn, secondSourcePosition));
                        }
                    }
                }
            }
        }
        return sources;
    }

    private Tree buildTree(WordFormation word, List<WordFormation>[][] typeTable) {
        if (word.isStart()) {
            return new Tree(word.getType());
        }
        return new Tree(word.getType(),
                buildTree(typeTable[word.getFirstLine()][word.getFirstColumn()].get(word.getFirstPosition()), typeTable),
                buildTree(typeTable[word.getSecondLine()][word.getSecondColumn()].get(word.getSecondPosition()), typeTable));
    }

    private WordType toType(String string) {
        return WordType.valueOf(string);
    }
}
