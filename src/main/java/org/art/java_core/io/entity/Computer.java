package org.art.java_core.io.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Computer implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cpu;
    private int ram;
    private double frequency;
    private boolean notebook;
}
