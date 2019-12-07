package zad3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class App {
    private URL kayneRestApiURL;

    public App(){
        try {
            this.kayneRestApiURL = new URL("https://api.kanye.rest/");
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
    }

    private String getData() {
        try {
            URLConnection conn = kayneRestApiURL.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
                response.append('\n');
            }

            return response.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // error
        return "";
    }

    public String getQuoteSync(){
        String response = getData();
        String responseLeftStripped = response.substring(10);
        return responseLeftStripped.substring(0, responseLeftStripped.length() - 3);
    }

    public CompletableFuture<String> getQuoteAsync(){
        CompletableFuture<String> quoteFuture = CompletableFuture.supplyAsync(this::getData);
        quoteFuture.thenApply((response) -> response.substring(10));
        quoteFuture.thenApply((response) -> response.substring(0, response.length() - 3));
        return quoteFuture;
    }

    public static void task()
    {
        App api = new App();
        List<String> syncQuotes = new ArrayList<>();
        List<String> asyncQuotes = new ArrayList<>();
        try {
            long startTime = System.currentTimeMillis();
            syncQuotes = IntStream.range(0, 200)
                    .mapToObj(i -> api.getQuoteSync())
                    .collect(Collectors.toList());
            long endTime = System.currentTimeMillis();

            System.out.println("Synchronous time: " + (endTime - startTime) + " ms");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            long startTime = System.currentTimeMillis();
            asyncQuotes = IntStream.range(0, 200)
                    .mapToObj(i -> api.getQuoteAsync())
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList());
            long endTime = System.currentTimeMillis();

            System.out.println("Asynchronous time: " + (endTime - startTime) + " ms");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        /*
        System.out.println("\nSynchronous quotes:");
        for (String quote: syncQuotes)
            System.out.println(quote);
        System.out.println("\nAsynchronous quotes:");
        for (String quote: asyncQuotes)
            System.out.println(quote);
         */
    }
}
