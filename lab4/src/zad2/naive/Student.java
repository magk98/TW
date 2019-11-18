package zad2.naive;

import zad2.Data;
import zad2.Rand;

public class Student implements Runnable {
    private Rand random;
    private Buffer buffer;
    private int id;

    public Student(Rand random, Buffer buffer, int id) {
        this.random = random;
        this.buffer = buffer;
        this.id = id;
    }

    @Override
    public void run() {
        int round = 0;
        boolean shouldContinue = true;
        while (shouldContinue) {
            int numberOfElements = random.getNumberOfElements();
            Data[] dataToGet = buffer.V(numberOfElements);
            round++;
            //if (round >= 1)
            // shouldContinue = false;
        }
    }

    @Override
    public String toString() {
        return "Consumer " + id;
    }
}
