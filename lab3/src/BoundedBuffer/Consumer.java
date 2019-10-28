package BoundedBuffer;


public class Consumer implements Runnable{
    BoundedBuffer buf;

    public Consumer(BoundedBuffer buf){
        this.buf = buf;
    }

    public void run(){
        try {
            for (int i = 0; i < 100; i++){
                String message = buf.take().toString();
                System.out.println(message);
            }

        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
