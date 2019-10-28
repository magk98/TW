package drukarka;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorDrukarek {
    final Lock lock = new ReentrantLock();
    final Condition notEmpty = lock.newCondition();
    Queue<Drukarka> printersAvailable = new LinkedList<>();

    MonitorDrukarek(int count) {
        for(int i = 0; i < count; i++)
            printersAvailable.add(new Drukarka(i));
    }

    public Drukarka wezDrukarke() throws InterruptedException {
        lock.lock();
        try {
            while (printersAvailable.isEmpty())
                notEmpty.await();
            return printersAvailable.remove();
        } finally {
            lock.unlock();
        }
    }

    public void oddajDrukarke(Drukarka drukarka) {
        lock.lock();
        try {
            printersAvailable.add(drukarka);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }
}

