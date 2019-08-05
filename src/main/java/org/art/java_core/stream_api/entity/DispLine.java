package org.art.java_core.stream_api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * File string line (with displacement) entity implementation.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DispLine {

    private int displacement;

    private String line;

    public String toString() {
        return displacement + " " + line;
    }
}
