package org.art.java_core.design.patterns.builder.generic;

import lombok.ToString;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

/**
 * Generic type Builder implementation with a recursive
 * type parameter (from J. Bloch - Effective Java).
 */
@ToString
public abstract class Pizza {

    public enum Topping {
        HAM, MUSHROOM, ONION, PEPPER, SAUSAGE
    }

    final Set<Topping> toppings;

    Pizza(Builder builder) {
        toppings = builder.toppings.clone();
    }

    abstract static class Builder<T extends Builder> {

        EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);

        public T addTopping(Topping topping) {
            toppings.add(Objects.requireNonNull(topping));
            return self();
        }

        abstract Pizza build();

        protected abstract T self();
    }
}
