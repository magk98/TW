package BinarySemaphore;

public class Client implements Runnable {
    private Shop shop;
    private int shoppingTime;

    Client(Shop shop, int shoppingTime) {
        this.shop = shop;
        this.shoppingTime = shoppingTime;
    }

    public void run() {
        long enterTime = System.currentTimeMillis();
        Cart cart = this.shop.takeCart();
        long gotCartTime = System.currentTimeMillis();
        try {
            Thread.sleep(shoppingTime * 1000);
        } catch (InterruptedException e) {
            System.out.println("Error while using carts");
        }

        long returnedCartTime = System.currentTimeMillis();
        synchronized (System.out) {
            System.out.println("Cart number: " + cart.number);
            this.shop.returnCart(cart);
            long exitTime = System.currentTimeMillis();
            System.out.println("Waiting for the car: " + (gotCartTime - enterTime) + "ms; In shop: "
                    + this.shoppingTime + "s; Returning the car: " + (exitTime - returnedCartTime) + "ms");
        }
    }
}
