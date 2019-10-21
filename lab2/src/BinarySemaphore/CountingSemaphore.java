package BinarySemaphore;

public class CountingSemaphore {
    private int capacity;
    private int available;

    public CountingSemaphore(int capacity){
        this.capacity = this.available = capacity;
    }

    public synchronized void V(){//wait
            while (available==0){
                try{
                    wait();
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            available--;
    }

    public synchronized void P(){//release
            available++;
        notifyAll();
    }
}