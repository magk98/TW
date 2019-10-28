package BoundedBuffer;

import java.util.LinkedList;
import java.util.List;

public class TestBuffer {

    public static void main(String[] args){
        BoundedBuffer buf = new BoundedBuffer();
        int consumerNumber = 5;
        int producerNumber = 5;

        List<Thread> threadList = new LinkedList<>();
        for (int i = 0; i < consumerNumber; i++){
            threadList.add(new Thread(new Consumer(buf)));
        }
        for (int i = 0; i < producerNumber; i++){
            threadList.add(new Thread(new Producer(buf)));
        }

        for(Thread t: threadList){
            t.start();
        }
        try {
            for (Thread t : threadList) {
                t.join();
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}
