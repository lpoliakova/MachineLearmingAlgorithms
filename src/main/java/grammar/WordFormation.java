package grammar;

public class WordFormation {
    private static final int DEFAULT  = -1;
    private WordType type;
    private int firstLine;
    private int firstColumn;
    private int firstPosition;
    private int secondLine;
    private int secondColumn;
    private int secondPosition;

    public WordFormation(WordType type) {
        this.type = type;
        this.firstLine = DEFAULT;
        this.firstColumn = DEFAULT;
        this.firstPosition = DEFAULT;
        this.secondLine = DEFAULT;
        this.secondColumn = DEFAULT;
        this.secondPosition = DEFAULT;
    }

    public WordFormation(WordType type, int firstLine, int firstColumn, int firstPosition, int secondLine, int secondColumn, int secondPosition) {
        this.type = type;
        this.firstLine = firstLine;
        this.firstColumn = firstColumn;
        this.firstPosition = firstPosition;
        this.secondLine = secondLine;
        this.secondColumn = secondColumn;
        this.secondPosition = secondPosition;
    }

    public boolean isStart() {
        return firstLine == DEFAULT;
    }

    public WordType getType() {
        return type;
    }

    public int getFirstLine() {
        return firstLine;
    }

    public int getFirstColumn() {
        return firstColumn;
    }

    public int getFirstPosition() {
        return firstPosition;
    }

    public int getSecondLine() {
        return secondLine;
    }

    public int getSecondColumn() {
        return secondColumn;
    }

    public int getSecondPosition() {
        return secondPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WordFormation that = (WordFormation) o;

        if (firstLine != that.firstLine) return false;
        if (firstColumn != that.firstColumn) return false;
        if (firstPosition != that.firstPosition) return false;
        if (secondLine != that.secondLine) return false;
        if (secondColumn != that.secondColumn) return false;
        if (secondPosition != that.secondPosition) return false;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + firstLine;
        result = 31 * result + firstColumn;
        result = 31 * result + firstPosition;
        result = 31 * result + secondLine;
        result = 31 * result + secondColumn;
        result = 31 * result + secondPosition;
        return result;
    }
}
