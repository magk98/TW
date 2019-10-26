package kelner;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Kelner {
    private final Lock stolik = new ReentrantLock();

    public void zarzadzaj(){
        stolik.lock();
        try{
            Long duration = (long) (Math.random() * 10000);
            System.out.println("Pair " + Thread.currentThread().getId() + " was eating for " + duration  + "ms");
            Thread.sleep(duration);
        } catch (InterruptedException e){
            e.printStackTrace();
        } finally{
            stolik.unlock();
        }
    }
}
