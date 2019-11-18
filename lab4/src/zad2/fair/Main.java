package zad2.fair;

import zad2.Rand;

public class Main {
    public static void main(String[] args) {
        int n0 = 10, n1 = 10, M = 1000;
        Rand random = new Rand(M);
        Buffer buffer = new Buffer(M);
        Thread[] threads = new Thread[n0 + n1];
        for (int i = 0; i < n0; i++)
            threads[i] = new Thread(new Tutor(random, buffer, i));
        for (int i = 0; i < n1; i++)
            threads[n0 + i] = new Thread(new Student(random, buffer, i));
        for (Thread thread : threads)
            thread.start();
    }
}
