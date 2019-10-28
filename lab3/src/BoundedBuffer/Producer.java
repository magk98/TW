package BoundedBuffer;

public class Producer implements Runnable{
    BoundedBuffer buf;

    public Producer(BoundedBuffer buf){
        this.buf = buf;
    }

    public void run(){
        try {
            for (int i = 0; i < 100; i++) {
                buf.put("message " + i);
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}
