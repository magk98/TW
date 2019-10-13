package zad_buffer;

public class Consumer implements Runnable {
    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {

        for(int i = 0;  i < 100;   i++) {
            String message = buffer.take();
            System.out.println(message);
        }
    }

    public static void main(String[] args){
        Buffer b1 = new Buffer();
        Producer p1 = new Producer(b1);
        Consumer c1 = new Consumer(b1);
        Thread t1 = new Thread(c1);
        Thread t2 = new Thread(p1);
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch(InterruptedException e){
            System.out.println("abc");
        }

    }
}
