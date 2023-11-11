package com.bl;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class Main {

    public static void main(String[] args) {

        final LocalTime start = LocalTime.now();

        final Consumer<Quote> presentQuote = quote ->
                System.out.printf("Received quote from %s: %f sestertii %n",
                        quote.getName(),
                        quote.getPrice());

        final List<String> suppliers = Arrays.asList(
                "Nerva-Antonine Group",
                "Trajan Institute of Expansion",
                "Hadrian`s Masons");

        final List<CompletableFuture<Void>> futures = new QuoteService()
                .requestQuotesAsync(suppliers)
                .map(future -> future.thenAccept(presentQuote))
                .toList();

        try {
            CompletableFuture
                    .allOf(futures.toArray(new CompletableFuture[0]))
                    .thenRun(() -> {
                        final LocalTime end = LocalTime.now();
                        final Duration duration = Duration.between(start, end);
                        System.out.printf("Received all quotes in %d ms.%n", duration.toMillis());
                    })
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
