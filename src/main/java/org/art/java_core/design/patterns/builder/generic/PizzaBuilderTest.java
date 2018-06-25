package org.art.java_core.design.patterns.builder.generic;

/**
 * Builder pattern (from GoF) - simple code example:
 * Pizza builder (with builder hierarchy - from Effective Java).
 */
public class PizzaBuilderTest {
    public static void main(String[] args) {

        NyPizza nyPizza = new NyPizza.Builder(NyPizza.Size.SMALL)
                .addCheese(2)
                .addTopping(Pizza.Topping.SAUSAGE)
                .addTopping(Pizza.Topping.ONION)
                .build();

        Calzone calzonePizza = new Calzone.Builder()
                .sauceInside()
                .addTopping(Pizza.Topping.PEPPER)
                .build();

        System.out.println(nyPizza);
        System.out.println(calzonePizza);
    }
}
