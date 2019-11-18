package zad2.naive;


import zad2.Data;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private int Mx2;
    private int M;
    private int inBuffer;
    private Data[] data;
    private ReentrantLock lock;
    private Condition producer;
    private Condition consumer;

    public Buffer(int M) {
        assert M > 0;
        this.M = M;
        this.Mx2 = 2 * M;
        this.inBuffer = 0;
        this.data = new Data[Mx2];
        this.lock = new ReentrantLock();
        this.producer = lock.newCondition();
        this.consumer = lock.newCondition();
    }

    public void P(int k, Data[] dataToPlace) {//release
        assert k == dataToPlace.length && k <= M;
        long time1 = System.nanoTime();
        lock.lock();
        while (Mx2 - inBuffer < k)
            try { producer.await(); } catch (InterruptedException exc) { exc.printStackTrace(); }
        int putIndex = 0;
        for (int i = 0; i < Mx2 && putIndex < k; i++) {
            if (data[i] == null) {
                data[i] = dataToPlace[putIndex];
                putIndex++;
                inBuffer++;
            }
        }
        consumer.signal();
        lock.unlock();
        long time2 = System.nanoTime();
        System.out.println("Producer " + k + " " + (time2 - time1) + " ns");
    }

    public Data[] V(int k) {//get
        assert 1 <= k && k <= M;
        long time1 = System.nanoTime();
        lock.lock();
        while (inBuffer < k)
            try { consumer.await(); } catch (InterruptedException exc) { exc.printStackTrace(); }
        Data[] dataToReturn = new Data[k];
        int getIndex = 0;
        for (int i = 0; i < Mx2 && getIndex < k; i++)
            if (data[i] != null) {
                dataToReturn[getIndex] = data[i];
                data[i] = null;
                getIndex++;
                inBuffer--;
            }
        producer.signal();
        lock.unlock();
        long time2 = System.nanoTime();
        System.out.println("Consumer " + k + " " + (time2 - time1) + " ns");
        return dataToReturn;
    }
}
