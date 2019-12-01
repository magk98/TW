package asymetric;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Demo {

    private static final int NUMBER_OF_PHILOSOPHERS = 5;

    private static final int ITERATIONS = 10;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_PHILOSOPHERS);
        List<Future<ArrayList<Long>>> futures = new ArrayList<>();
        final Table table = new Table(NUMBER_OF_PHILOSOPHERS);
        for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
            futures.add(executorService.submit(new Philosopher(i, table, ITERATIONS)));
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<Double> lastResult = new ArrayList<>();
        for(Future<ArrayList<Long>> result : futures) {
            try {
                ArrayList<Long> times = result.get();
                Long sum = 0L;
                for(Long time : times) {
                    sum+=time;
                }
                lastResult.add((double)sum/ITERATIONS);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        Double avg = 0.0;
        for(Double lastResultVal:lastResult) {
            avg+=lastResultVal;
        }
        System.out.println(avg/5);
    }

}
