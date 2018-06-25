package org.art.java_core.concurrent.design.store_simulation;

import lombok.Data;
import lombok.ToString;

/**
 * Goods entity implementation.
 */
@Data
@ToString
public class Goods {

    private Products product;
    private int amount;

    public Goods(Products product, int amount){
        this.product = product;
        this.amount = amount;
    }
}
