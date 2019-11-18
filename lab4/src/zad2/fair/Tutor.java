package zad2.fair;

import zad2.Data;
import zad2.Rand;

public class Tutor implements Runnable {
    private Rand random;
    private Buffer buffer;
    private int id;

    public Tutor(Rand random, Buffer buffer, int id) {
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
            Data[] dataToPut = new Data[numberOfElements];
            for (int i = 0; i < numberOfElements; i++)
                dataToPut[i] = new Data();
            buffer.P(numberOfElements, dataToPut);
            round++;
        }
    }

    @Override
    public String toString() {
        return "Producer " + id;
    }
}
