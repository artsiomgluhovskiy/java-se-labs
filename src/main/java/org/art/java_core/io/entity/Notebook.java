package org.art.java_core.io.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Notebook extends Computer {

    private String model;
    private transient int year;
    private Touchpad touchpad;
}
