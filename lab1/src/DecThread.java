public class DecThread implements Runnable{
    Counter c;
    public DecThread(Counter c){
        this.c = c;
    }

    public void run(){
        for (int i = 0; i < 100000000; i++) {
            c.decValue();
        }
    }
}
