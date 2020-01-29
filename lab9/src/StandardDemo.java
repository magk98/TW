import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class StandardDemo {
    static int readerCount = 0;
    static Semaphore mutex = new Semaphore(1);
    static Semaphore wsem = new Semaphore(1);

    static class Reader implements Runnable {
        @Override
        public void run() {
            try {
                mutex.acquire();
                readerCount++;
                if (readerCount == 1) wsem.acquire();
                mutex.release();

                System.out.println(Thread.currentThread().getName() + " is reading");
                Thread.sleep(1500);
                System.out.println(Thread.currentThread().getName() + " has finished reading");

                mutex.acquire();
                readerCount--;
                if (readerCount == 0) wsem.release();
                mutex.release();

            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    static class Writer implements Runnable {
        @Override
        public void run() {
            try {
                wsem.acquire();
                System.out.println(Thread.currentThread().getName() + " is writing");
                Thread.sleep(2500);
                System.out.println(Thread.currentThread().getName() + " has finished writing");
                wsem.release();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        List<Thread> threadList = new LinkedList<>();
        Reader read = new Reader();
        Writer write = new Writer();
        for (int i = 0; i < 4; i ++){
            Thread r1 = new Thread(read);
            r1.setName("reader " + i);
            threadList.add(r1);
        }
        Thread w1 = new Thread(write);
        w1.setName("writer");
        threadList.add(w1);
        Writer write1 = new Writer();
        Thread w2 = new Thread(write1);
        w2.setName("writer 2");
        threadList.add(w2);

        for (Thread t : threadList)
            t.start();

        for (Thread t : threadList)
            t.join();

    }
}
