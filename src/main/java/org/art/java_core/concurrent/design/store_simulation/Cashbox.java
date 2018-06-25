package org.art.java_core.concurrent.design.store_simulation;

/**
 * Store cashbox implementation.
 */
public class Cashbox extends Thread {

    private int cashboxId;
    private final StoreQueue storeQueue;

    public Cashbox(int cashboxId, StoreQueue store, ThreadGroup group) {
        super(group, "Cashbox_" + cashboxId);
        this.cashboxId = cashboxId;
        this.storeQueue = store;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " is running...");
        Customer customer;
        while (!isInterrupted()) {
            try {
                customer = storeQueue.takeCustomerFromQueue();
                serveCustomer(customer);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " was interrupted!");
                return;
            }
        }
    }

    private void serveCustomer(Customer customer) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " is serving Customer " + customer.getCustomerId()
                          + ", customer's purchases: " + customer.getShoppingBasket());
        Thread.sleep(customer.getShoppingBasket().getListOfGoods().size() * 1000);
        System.out.println(Thread.currentThread().getName() + " has served Customer " + customer.getCustomerId() + ". So, he is going home.");
    }
}
