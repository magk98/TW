package zad2.naive;

import zad2.Rand;

public class Main {
    public static void main(String[] args){
        int producers = 10, consumers = 10, M = 1000;
        Rand random = new Rand(M);
        Buffer buffer = new Buffer(M);
        Thread[] threads = new Thread[producers+consumers];
        for (int i = 0; i < producers; i++)
            threads[i] = new Thread(new Tutor(random, buffer, i));
        for (int i = 0; i < consumers; i++)
            threads[producers+i] = new Thread(new Student(random, buffer, i));
        for (Thread thread : threads)
            thread.start();
    }
}
