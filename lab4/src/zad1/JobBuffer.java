package zad1;

import java.util.Optional;

public class JobBuffer {
    private final int size;
    private Job[] jobs;
    private BinarySemaphore[] semaphores;

    public JobBuffer(int size) {
        this.size = size;
        this.jobs = new Job[size];
        this.semaphores = new BinarySemaphore[size];
        for (int i = 0; i < size; i++) {
            jobs[i] = new Job();
            semaphores[i] = new BinarySemaphore();
        }
    }

    public synchronized Optional<Job> getFirstJobForProcessor(Processor processor) {
        int semaphoreIndex = 0;
        for (Job j: jobs) {
            if (j.getNextProcessorId() == processor.getProcessorId()) {
                semaphores[semaphoreIndex].V();
                j.setPreviousProcessorId(j.getNextProcessorId());
                j.setNextProcessorId(j.getNextProcessorId()+1);
                System.out.println("Process " + processor.getProcessorId() + " got job " + j.getJobId());
                return Optional.of(j);
            }
            semaphoreIndex++;
        }
        return Optional.empty();
    }

    public void finishProcessing(Job job) {
        int semaphoreIndex = 0;
        for (Job j: jobs) {
            if (job.getJobId() == j.getJobId()) {
                System.out.println("Job " + job.getJobId() + " given back, next processor id: " + job.getNextProcessorId());
                semaphores[semaphoreIndex].P();
                return;
            }
            semaphoreIndex++;
        }
        System.out.println("No job with id " + job.getJobId() + " found");
    }
}
