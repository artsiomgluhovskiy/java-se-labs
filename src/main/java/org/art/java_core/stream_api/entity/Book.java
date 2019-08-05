package org.art.java_core.stream_api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Year;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Book {

    public enum Topic {
        HISTORY,
        PROGRAMMING,
        FICTION,
        MEDICINE,
        COMPUTING
    }

    private String title;
    private List<String> authors;
    private int[] pageCounts;
    private Topic topic;
    private Year pubDate;
    private double height;
}
