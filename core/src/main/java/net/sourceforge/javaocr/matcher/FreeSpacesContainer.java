package net.sourceforge.javaocr.matcher;

/**
 * container to serialize and deserialize free space matcher data
 */
public class FreeSpacesContainer {
    char[] characters;
    int[] counts;
    int count;

    

    public char[] getCharacters() {
        return characters;
    }

    public void setCharacters(char[] characters) {
        this.characters = characters;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int[] getCounts() {
        return counts;
    }

    public void setCounts(int[] counts) {
        this.counts = counts;
    }
}
