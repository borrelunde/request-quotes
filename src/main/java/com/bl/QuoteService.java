package com.bl;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class QuoteService {

    public Stream<CompletableFuture<Quote>> requestQuotesAsync(List<String> suppliers) {
        return suppliers.stream().map(this::requestQuoteAsync);
    }

    public CompletableFuture<Quote> requestQuoteAsync(String supplier) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.printf("Receiving quote from %s.%n", supplier);

            // Simulate that it takes time to get the price.
            RandomDurationProcess.simulate(3_000, 5_000);
            final double price = 50.0 + new Random().nextDouble(50.0);

            return new Quote(supplier, price);
        });
    }
}
