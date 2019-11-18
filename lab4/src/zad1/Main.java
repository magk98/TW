package zad1;

public class Main {
    private static final int processorsNumber = 5;
    private static final int jobsNumber = 100;

    public static void main(String[] args) {
        JobBuffer jb = new JobBuffer(jobsNumber);
        Processor[] processors = new Processor[processorsNumber];
        Thread[] threads = new Thread[processorsNumber];
        for (int i = 0; i < processorsNumber; i++) {
            processors[i] = new Processor(jb, i*100);
            threads[i] = new Thread(processors[i]);
            processors[i].setWaitingTime(0);
        }
        processors[0].setProducer(true);
        processors[processorsNumber - 1].setConsumer(true);
        processors[processorsNumber / 2].setWaitingTime(1000);

        for(Thread t: threads)
            t.start();
    }


}
