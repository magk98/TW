import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class SecondDemo {
    static int AW = 0;
    static int AR = 0;
    static int WW = 0;
    static int WR = 0;

    static Semaphore mutex = new Semaphore(1);
    static Semaphore toRead = new Semaphore(4);
    static Semaphore toWrite = new Semaphore(1);

    static class Reader implements Runnable {
        @Override
        public void run() {
            try {//P - acquire, V - release !!!! TODO
                mutex.acquire();
                if(AW + WW > 0)
                    WR++;
                else{
                    toRead.release();
                    AR++;
                }
                mutex.release();
                toRead.acquire();

                System.out.println(Thread.currentThread().getName() + " is reading");
                Thread.sleep(1500);
                System.out.println(Thread.currentThread().getName() + " has finished reading");

                mutex.acquire();
                System.out.println("AW: "+ AW);
                mutex.release();

                mutex.acquire();
                AR--;
                if (AR == 0 && WW > 0) {
                    toWrite.release();
                    AW++;
                    WW--;
                }
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
                mutex.acquire();//P - acquire, V - release !!!! TODO
                if(AW + AR > 0)
                    WW++;
                else{
                    toWrite.release();
                    AW++;
                }
                mutex.release();
                toWrite.acquire();

                System.out.println(Thread.currentThread().getName() + " is writing");
                Thread.sleep(2500);
                System.out.println(Thread.currentThread().getName() + " has finished writing");

                //if(AR > 0)
                mutex.acquire();
                    System.out.println(AR);
                mutex.release();
                mutex.acquire();
                AW--;
                if(WW > 0){
                    toWrite.release();
                    AW++;
                    WW--;
                } else if(WR > 0){
                    toRead.release();
                    AR++;
                    WR--;
                }
                mutex.release();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        List<Thread> threadList = new LinkedList<>();
        Reader read = new Reader();
        Writer write = new Writer();
        Thread w1 = new Thread(write);
        w1.setName("writer");
        threadList.add(w1);
        Writer write1 = new Writer();
        Thread w2 = new Thread(write1);
        w2.setName("writer 2");
        threadList.add(w2);
        for (int i = 0; i < 4; i ++){
            Thread r1 = new Thread(read);
            r1.setName("reader " + i);
            threadList.add(r1);
        }


        for (Thread t : threadList)
            t.start();

        for (Thread t : threadList)
            t.join();

    }
}
