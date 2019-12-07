package zad1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Naive {
    public static long count(){
        String filename = "processed_data.txt";

        long startTime = System.currentTimeMillis();
        int counter = 0;
        try (Stream<String> stream = Files.lines(Paths.get(filename))){
            counter = stream.mapToInt(line -> line.split("\\s+").length).sum();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();

        System.out.println("Naive - Words in file: " + counter + ", counting took " + (endTime - startTime) + " ms");
        return endTime - startTime;
    }
}
