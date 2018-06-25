package org.art.java_core.design.patterns.builder.generic;

import lombok.ToString;

import java.util.Objects;

@ToString(callSuper = true)
public class NyPizza extends Pizza {

    private int cheesePortions;

    public enum Size {
        SMALL, MEDIUM, LARGE
    }

    private final Size size;

    private NyPizza(Builder builder) {
        super(builder);
        this.size = builder.size;
        this.cheesePortions = builder.cheesePortions;
    }

    public static class Builder extends Pizza.Builder<Builder> {

        private final Size size;
        private int cheesePortions;

        public Builder(Size size) {
            this.size = Objects.requireNonNull(size);
        }

        public Builder addCheese(int portions) {
            cheesePortions = portions;
            return self();
        }

        @Override
        public NyPizza build() {
            return new NyPizza(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
