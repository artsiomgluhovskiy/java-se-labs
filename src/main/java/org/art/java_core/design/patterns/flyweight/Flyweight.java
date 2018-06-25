package org.art.java_core.design.patterns.flyweight;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Flyweight pattern (from GoF) - simple code example.
 * Document text line printing simulation.
 */
public class Flyweight {

    public static void main(String[] args) {

        DocumentContainer docLine = new DocLine(new Font("TimesNewRoman", 14));
        DocCharFactory charFactory = new DocCharFactory();

        //Repeatable symbols are taken from the symbol pool
        docLine.addChild(charFactory.getDocChar('h'));
        docLine.addChild(charFactory.getDocChar('e'));
        docLine.addChild(charFactory.getDocChar('l'));
        docLine.addChild(charFactory.getDocChar('l'));
        docLine.addChild(charFactory.getDocChar('o'));

        docLine.print();

        System.out.println("Character pool size: " + charFactory.getCharPoolSize());
    }
}

interface DocElement {
    void print();
}

//Flyweight context (abstract container with external common parameter - text font)
abstract class DocumentContainer implements DocElement {

    private static final DocElement EMPTY_DOC_EL = new EmptyDocElement();

    private List<DocElement> children = Collections.synchronizedList(new ArrayList<>());

    public DocumentContainer(Font font) {
        this.font = font;
    }

    //External parameter (for all flyweights in the container)
    private Font font;

    DocElement getChild(int index) {
        if (index < children.size()) {
            return children.get(index);
        }
        return EMPTY_DOC_EL;
    }

    void addChild(DocElement child, int index) {
        if (index >= children.size()) {
            throw new IndexOutOfBoundsException("The index '" + index + "' is out of bounds!");
        }
        children.add(index, child);
    }

    void addChild(DocElement child) {
        children.add(child);
    }

    Font getFont() {
        return font;
    }

    List<DocElement> getChildren() {
        return new ArrayList<>(children);
    }

    private static class EmptyDocElement implements DocElement {

        @Override
        public void print() {
            System.out.print("");
        }

        @Override
        public String toString() {
            return "";
        }
    }
}

//Document text line
class DocLine extends DocumentContainer {

    public DocLine(Font font) {
        super(font);
    }

    @Override
    public void print() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        List<DocElement> children = getChildren();
        return children.stream()
                .map(DocElement::toString)
                .collect(Collectors.joining(" ", "[", "]"))
                + " <Font: " + getFont() + ">";
    }
}

//Document character (flyweight)
class DocChar implements DocElement {

    //Intrinsic flyweight parameter
    private char character;

    DocChar(char character) {
        this.character = character;
    }

    @Override
    public void print() {
        System.out.print(character);
    }

    public char getCharacter() {
        return character;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocChar docChar = (DocChar) o;
        return character == docChar.character;
    }

    @Override
    public int hashCode() {
        return Objects.hash(character);
    }

    @Override
    public String toString() {
        return String.valueOf(character);
    }
}

class DocCharFactory {

    private Map<Character, DocChar> docCharPool = new ConcurrentHashMap<>();

    DocChar getDocChar(char c) {
        DocChar docChar = docCharPool.get(c);
        if (docChar == null) {
            docChar = new DocChar(c);
            docCharPool.put(c, docChar);
        }
        return docChar;
    }

    int getCharPoolSize() {
        return docCharPool.size();
    }
}

class Font {

    private String fontName;
    private int fontSize;

    public Font(String fontName, int fontSize) {
        this.fontName = fontName;
        this.fontSize = fontSize;
    }

    public String getFontName() {
        return fontName;
    }

    public int getFontSize() {
        return fontSize;
    }

    @Override
    public String toString() {
        return fontName + ", " + fontSize + "pt";
    }
}

