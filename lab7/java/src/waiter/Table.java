package waiter;


import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Table {

    private ArrayList<Fork> forks = new ArrayList<>();

    private Lock lock = new ReentrantLock();

    private Condition freeForks = lock.newCondition();

    private Condition allPhilosophersAreHungry = lock.newCondition();

    private int hungryPhilosophers = 0;

    Table(int forkNum) {
        for (int i = 0; i < forkNum; i++) {
            forks.add(new Fork(true));
        }
    }

    void getMyForks(Philosopher philosopher) {
        lock.lock();
        final Fork rightFork = forks.get(philosopher.getNumber());
        final Fork leftFork = forks.get((philosopher.getNumber()+1) % forks.size());
        hungryPhilosophers++;
        while(hungryPhilosophers == forks.size()) {
            try {
                allPhilosophersAreHungry.await();
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        while(!(leftFork.isFree() && rightFork.isFree())) {
            try {
                freeForks.await();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        rightFork.setFree(false);
        leftFork.setFree(false);
        philosopher.setLeftFork(leftFork);
        philosopher.setRightFork(rightFork);
        hungryPhilosophers--;
        allPhilosophersAreHungry.signalAll();
        lock.unlock();
    }

    void putMyForks(Philosopher philosopher) {
        lock.lock();
        philosopher.getLeftFork().setFree(true);
        philosopher.getRightFork().setFree(true);
        philosopher.setLeftFork(null);
        philosopher.setRightFork(null);
        freeForks.signalAll();
        lock.unlock();
    }
}
