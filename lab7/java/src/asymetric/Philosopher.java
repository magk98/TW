package asymetric;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class Philosopher implements Callable<ArrayList<Long>> {

    private int iterations;

    private Fork leftFork;

    private Fork rightFork;

    private int number;

    private Table table;

    Philosopher(int number, Table table, int iterations) {
        this.number = number;
        this.table = table;
        this.iterations = iterations;
    }

    public ArrayList<Long> call() {
        ArrayList<Long> times = new ArrayList<>();
        for (int i = 0; i < iterations; i++) {
            think();
            long start = System.nanoTime();
            waitForForks();
            long elapsedTime = System.nanoTime() - start;
            times.add(elapsedTime);
            eat();
            giveForksBack();
        }
        return times;
    }

    private void think() {
        System.out.println("Philosopher " + number + " is thinking...");
        try {
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void eat() {
        System.out.println("Philosopher " + number + " is eating delicious spaghetti...");
        try {
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void waitForForks() {
        table.getMyForks(this);
    }

    private void giveForksBack() {
        table.putMyForks(this);
    }

    void setLeftFork(Fork leftFork) {
        this.leftFork = leftFork;
    }

    void setRightFork(Fork rightFork) {
        this.rightFork = rightFork;
    }

    Fork getLeftFork() {
        return leftFork;
    }

    Fork getRightFork() {
        return rightFork;
    }

    int getNumber() {
        return number;
    }
}
