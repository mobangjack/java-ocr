package net.sourceforge.javaocr.matcher;

/**
 * container to serialize and deserialize free space matcher data
 */
public class FreeSpacesContainer {
    Character[] characters;
    int count;

    

    public Character[] getCharacters() {
        return characters;
    }

    public void setCharacters(Character[] characters) {
        this.characters = characters;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
