package asymetric;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Table {

    private ArrayList<Fork> forks = new ArrayList<>();
    private Lock lock = new ReentrantLock();
    private Condition freeLeft = lock.newCondition();
    private Condition freeRight = lock.newCondition();

    Table(int forkNum) {
        for (int i = 0; i < forkNum; i++) {
            forks.add(new Fork(true));
        }
    }

    ArrayList<Fork> getForks() {
        return forks;
    }

    void getMyForks(Philosopher philosopher) {
        lock.lock();
        final Fork rightFork = forks.get(philosopher.getNumber());
        final Fork leftFork = forks.get((philosopher.getNumber() + 1) % forks.size());
        if (philosopher.getNumber() % 2 == 0) {
            while (!(leftFork.isFree())) {
                try {
                    freeLeft.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            leftFork.setFree(false);
            philosopher.setLeftFork(leftFork);
            while (!rightFork.isFree()) {
                try {
                    freeRight.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            rightFork.setFree(false);
            philosopher.setRightFork(rightFork);
            lock.unlock();
        }
        else
        {
            while (!(rightFork.isFree())) {
                try {
                    freeRight.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            rightFork.setFree(false);
            philosopher.setRightFork(rightFork);
            while (!leftFork.isFree()) {
                try {
                    freeLeft.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            leftFork.setFree(false);
            philosopher.setLeftFork(leftFork);
            lock.unlock();
        }
    }

    void putMyForks(Philosopher philosopher) {
        lock.lock();
        philosopher.getLeftFork().setFree(true);
        philosopher.getRightFork().setFree(true);
        philosopher.setLeftFork(null);
        philosopher.setRightFork(null);
        freeLeft.signalAll();
        freeRight.signalAll();
        lock.unlock();
    }
}