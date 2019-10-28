package drukarka;

import java.util.LinkedList;

public class Main {

    public static void main(String[] args){
        int liczbaDrukarek = 2;
        int liczbaKlientow = 10;
        MonitorDrukarek monitorDrukarek = new MonitorDrukarek(liczbaDrukarek);
        LinkedList<Thread> threads = new LinkedList<>();

        for(int i = 0; i< liczbaKlientow; i++)
            threads.add(new Klient(monitorDrukarek, i));
        for (Thread thread : threads)
            thread.start();

        for (Thread thread : threads){
            try{
                thread.join();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
