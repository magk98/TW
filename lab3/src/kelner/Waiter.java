package kelner;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Waiter {

    private final Lock lock = new ReentrantLock();
    private final Condition freeTable = lock.newCondition();
    private Condition[] waitingForPartner;

    private int peopleAtTable=0;
    private boolean [] someoneWaits;

    public Waiter(int N){
        waitingForPartner = new Condition[N];
        someoneWaits = new boolean[N];
        for(int i=0; i<N; i++) {
            waitingForPartner[i]=lock.newCondition();
            someoneWaits[i] = false;
        }
    }

    public void takeTable(Couple couple) throws InterruptedException{
        lock.lock();
        try{
            if(!someoneWaits[couple.num]){
                someoneWaits[couple.num]=true;
                waitingForPartner[couple.num].await();
                someoneWaits[couple.num]=false;
            }
            else {
                while (peopleAtTable > 0) {
                    freeTable.await();
                }
                peopleAtTable = 2;
                System.out.println("Table taken by couple " + couple.num);
                waitingForPartner[couple.num].signal();
            }
        } finally {
            lock.unlock();
        }
    }

    public void returnTable(){
        lock.lock();
        try{
            peopleAtTable-=1;
            if(peopleAtTable==0){
                System.out.println("Table is free");
                freeTable.signal();
            }
        }finally {
            lock.unlock();
        }
    }
}
