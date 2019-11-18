package zad2.fair;

import zad2.Data;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private int Mx2, M;
    private int inBuffer;
    private Data[] data;
    private ReentrantLock lock;
    private Condition first_prod, first_cons, rest_prod, rest_cons;
    private boolean first_prod_flag, first_cons_flag;

    public Buffer(int M) {
        assert M > 0;
        this.M = M;
        this.Mx2 = 2 * M;
        this.inBuffer = 0;
        this.data = new Data[Mx2];
        this.lock = new ReentrantLock();
        this.first_prod = lock.newCondition();
        this.first_cons = lock.newCondition();
        this.rest_prod = lock.newCondition();
        this.first_prod_flag = this.first_cons_flag = false;
        this.rest_cons = lock.newCondition();
    }

    public void P(int k, Data[] dataToPlace) {//release
        assert k == dataToPlace.length && k <= M;
        long time1 = System.nanoTime();
        lock.lock();
        try {
            if (first_prod_flag)
                rest_prod.await();
            first_prod_flag = true;
            while (Mx2 - inBuffer < k)
                first_prod.await();
            int putIndex = 0;
            for (int i = 0; i < Mx2 && putIndex < k; i++) {
                if (data[i] == null) {
                    data[i] = dataToPlace[putIndex];
                    putIndex++;
                    inBuffer++;
                }
            }
            first_prod_flag = false;
            rest_prod.signal();
            first_cons.signal();
        }
        catch (InterruptedException exc) {
            exc.printStackTrace();
        }
        finally {
            lock.unlock();
            long time2 = System.nanoTime();
            System.out.println("P " + k + " " + (time2 - time1));
        }
    }

    public Data[] V(int k) {//get
        assert 1 <= k && k <= M;
        long time1 = System.nanoTime();
        lock.lock();
        try {
            if (first_cons_flag)
                rest_cons.await();
            first_cons_flag = true;
            while (inBuffer < k)
                first_cons.await();
            Data[] dataToReturn = new Data[k];
            int getIndex = 0;
            for (int i = 0; i < Mx2 && getIndex < k; i++)
                if (data[i] != null) {
                    dataToReturn[getIndex] = data[i];
                    data[i] = null;
                    getIndex++;
                    inBuffer--;
                }
            first_cons_flag = false;
            rest_cons.signal();
            first_prod.signal();
            return dataToReturn;
        }
        catch (InterruptedException exc) {
            exc.printStackTrace();
            return null;
        }
        finally {
            lock.unlock();
            long time2 = System.nanoTime();
            System.out.println("C " + k + " " + (time2 - time1));
        }

    }
}
