package org.art.java_core.concurrent.design.store_simulation;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Shopping basket entity implementation.
 */
@Data
@ToString
public class ShoppingBasket {

    private final List<Goods> listOfGoods;

    public ShoppingBasket() {
        this.listOfGoods = BoxGenerator.generateGoodsList();
    }

    private static class BoxGenerator {

        private static List<Goods> generateGoodsList() {
            Random random = new Random(System.nanoTime());
            Products[] productTypes = Products.values();
            List<Goods> goodsList = new ArrayList<>();
            int productTypesNumber = random.nextInt(5);
            //If customer chose nothing
            if (productTypesNumber == 0) {
                //Returning empty box
                return Collections.emptyList();
            } else {
                for (int i = 0; i < productTypesNumber; i++) {
                    int productType = random.nextInt(productTypes.length);
                    int productsNumber = random.nextInt(4) + 1;
                    goodsList.add(new Goods(productTypes[productType], productsNumber));
                }
            }
            return goodsList;
        }
    }
}
