package org.art.java_core.cglib.tx_manager.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class User {

    public User(Long id, String firstName, String lastName, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private int currentAgeRating;
}
