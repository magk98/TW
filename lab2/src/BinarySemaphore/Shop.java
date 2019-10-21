package BinarySemaphore;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Shop {
    private CountingSemaphore freeCartsNumber;
    private BinarySemaphore movingCart = new BinarySemaphore();
    private Cart[] shoppingCarts;
    private boolean[] availableCarts;

    private int cartsNumber;
    private int clientsNumber;
    private static int minTime = 1;
    private static int maxTime = 5;

    public Shop(int cartsNumber, int clientsNumber){
        this.freeCartsNumber = new CountingSemaphore(cartsNumber);
        this.cartsNumber = cartsNumber;
        this.clientsNumber = clientsNumber;
        this.shoppingCarts = new Cart[cartsNumber];
        this.availableCarts = new boolean[cartsNumber];

        for (int i = 0; i < this.cartsNumber; i++) {
            this.shoppingCarts[i] = new Cart(i);
            this.availableCarts[i] = true;
        }
    }

    Cart takeCart() {
        this.freeCartsNumber.V();
        this.movingCart.V();
        for (int i = 0; i < this.cartsNumber; i++) {
            if (this.availableCarts[i]) {
                this.availableCarts[i] = false;
                Cart takenCart = this.shoppingCarts[i];
                this.movingCart.P();
                return takenCart;
            }
        }
        this.movingCart.P();
        return null;
    }

    void returnCart(Cart cart) {
        try{
            Thread.sleep(2000);
        } catch(InterruptedException e){
            System.out.println("cokolwiek");
        }
        this.movingCart.V();
        for (int i = 0; i < this.cartsNumber; i++) {
            if (!this.availableCarts[i]) {
                this.availableCarts[i] = true;
                this.shoppingCarts[i] = cart;
                this.movingCart.P();
            }
        }
        this.freeCartsNumber.P();
    }

    public static void main(String[] args){
        Shop shop = new Shop(3, 10);
        ArrayList<Thread> clients = new ArrayList<>();
        try{
            for (int i = 0; i < shop.clientsNumber; i++) {
                Client client = new Client(shop, new Random().nextInt((maxTime - minTime) + 1) + minTime);
                Thread newClient = new Thread(client);
                newClient.start();
                clients.add(newClient);
            }

            for (Thread thread: clients) {
                thread.join();
            }
        } catch(InterruptedException e){
            System.out.println("Clients interrupted shopping");
        }
    }
}
