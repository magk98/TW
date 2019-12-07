package zad1;

import java.io.BufferedReader;
import java.io.FileOutputStream;

public class FileChanger {

    public static void addSpaces(){
        String inputFilename = "data.txt";
        String outputFilename = "processed_data.txt";

        try {
            BufferedReader file = new BufferedReader(new java.io.FileReader(inputFilename));
            StringBuilder inputBuffer = new StringBuilder();
            String line;

            int i = 0;
            while ((line = file.readLine()) != null){
                if (i < 100)
                    line = line.replaceFirst(" ", "  ");
                i += 1;
                inputBuffer.append(line);
                inputBuffer.append('\n');
            }
            file.close();

            FileOutputStream fileOut = new FileOutputStream(outputFilename);
            fileOut.write(inputBuffer.toString().getBytes());
            fileOut.close();

        }
        catch (Exception e){
            System.out.println("Problem reading file.");
        }
    }
}
