package org.art.java_core.concurrent.design.port_simulation;

import lombok.Data;
import lombok.ToString;

/**
 * Ship container implementation.
 */
@Data
@ToString
public class Container {

    private int id;

    public Container(int id) {
        this.id = id;
    }
}
