package org.art.java_core.algorithms.sorting;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
        String fieldText = "Text field content and notation{note}Footnote number one with {link https://www.onliner.by/}link1{link} which goes here {note} here " +
                "plus second notation{note} Footnote number two with{link https://www.tut.by/}link2{link} again {note} here";

        Pattern notePattern = Pattern.compile("\\{note}.*?\\{note}");
        Matcher noteMatcher = notePattern.matcher(fieldText);
        Map<String, String> notesMap = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        int noteNumber = 0;
        while (noteMatcher.find()) {
            String footnotePlaceholder = "{note" + noteNumber + "}";
            noteMatcher.appendReplacement(sb, footnotePlaceholder);
            String noteContentRaw = noteMatcher.group();
            String processedNoteContent = processNoteContent(noteContentRaw);
            notesMap.put(footnotePlaceholder, processedNoteContent);
            noteNumber++;
        }
        noteMatcher.appendTail(sb);
        String fieldTextTemplate = sb.toString();
        System.out.println(fieldTextTemplate);
        System.out.println(notesMap);
    }

    private static String processNoteContent(String noteContent) {
        return noteContent + " modified!";
    }
}
