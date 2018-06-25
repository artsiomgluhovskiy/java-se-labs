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
public class Touchpad implements Serializable {

    private static final long serialVersionUID = 2L;

    private String touchpadModel;
    private double size;
    private int model;
}
