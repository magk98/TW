package zad_buffer;

public class Buffer {

    String buf;
    int counter = 0;
    public Buffer(){}

    synchronized void put(String message){
        while(counter == 1) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("blabla");
            }
        }
        buf = message;
        counter++;
        notifyAll();
    }

    synchronized String take(){
        while(counter == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("whatever");
            }
        }
        counter--;
        notifyAll();
        return buf;
    }
}
