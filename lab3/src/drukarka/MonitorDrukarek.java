package drukarka;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorDrukarek {
    private final Lock kolejkaDrukarek = new ReentrantLock();

    public void drukuj(){
        kolejkaDrukarek.lock();
        try{
            Long duration = (long) (Math.random() * 1000);
            System.out.println(Thread.currentThread().getName() + " printing for " + duration + "ms");
            Thread.sleep(duration);
        } catch(InterruptedException e){
            e.printStackTrace();
        } finally {
            kolejkaDrukarek.unlock();
        }

    }
}
