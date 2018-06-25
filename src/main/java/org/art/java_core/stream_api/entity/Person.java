package org.art.java_core.stream_api.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Person {

    private String firstName;
    private String lastName;
    private int age;
}
