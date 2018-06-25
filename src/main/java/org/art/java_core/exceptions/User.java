package org.art.java_core.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * User entity implementation.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    private String fullName;
    private long userId;
}
