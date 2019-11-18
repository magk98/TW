package zad1;

import java.util.Optional;

public class Processor implements Runnable {
    private static int nextProcessorId = 0;

    private final int processorId;
    private boolean consumer;
    private boolean producer;
    private JobBuffer jobBuffer;
    private int waitingTime;

    public Processor(JobBuffer jobBuffer, int waitingTime) {
        this.jobBuffer = jobBuffer;
        this.consumer = false;
        this.producer = false;
        this.processorId = nextProcessorId++;
        this.waitingTime = waitingTime;
    }


    public void process(Job job) throws InterruptedException {
        job.setData(job.getData() + processorId);
        System.out.println("Processor " + processorId + " processing job " + job.getJobId());
        if (producer) {
            job.start();
            System.out.println("Starting job " + job.getJobId());
        } else if (consumer) {
            System.out.println("Finishing job " + job.getJobId());
            job.stop();
        }
        Thread.sleep((long) (Math.random() * 1000 + waitingTime));
    }

    @Override
    public void run() {
        while (true) {
            Optional<Job> potentialJob = jobBuffer.getFirstJobForProcessor(this);
            if (potentialJob.isPresent()) {
                try {
                    process(potentialJob.get());
                } catch (InterruptedException e) {
                    System.out.println("Caught InterruptedException while processing:");
                    e.printStackTrace();
                } finally {
                    jobBuffer.finishProcessing(potentialJob.get());
                }
            }
        }
    }


    public int getProcessorId() {
        return processorId;
    }

    public boolean isConsumer() {
        return consumer;
    }

    public void setConsumer(boolean consumer) {
        this.consumer = consumer;
    }

    public boolean isProducer() {
        return producer;
    }

    public void setProducer(boolean producer) {
        this.producer = producer;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }


}