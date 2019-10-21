package BinarySemaphore;

public class Counter {
    private final BinarySemaphore binarySemaphore;
    private int value = 0;

    public Counter(){
        this.binarySemaphore = new BinarySemaphore();
    }

    public void inc(){
        this.binarySemaphore.V();
        value++;
        this.binarySemaphore.P();
    }

    public void dec(){
        this.binarySemaphore.V();
        value--;
        this.binarySemaphore.P();
    }

    public int getValue() {
        return value;
    }

    public static void main(String[] args){
        Counter c = new Counter();
        new Thread(() -> {
            for (int i = 0 ; i < 100000000; i++)
                c.inc();
        }).run();

        new Thread(() -> {
            for (int i = 0 ; i < 100000000; i++)
                c.dec();
        }).run();

        System.out.println(c.getValue());
    }
}
