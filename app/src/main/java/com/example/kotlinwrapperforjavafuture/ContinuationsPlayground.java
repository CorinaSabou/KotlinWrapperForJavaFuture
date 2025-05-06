package com.example.kotlinwrapperforjavafuture;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public class ContinuationsPlayground {

    // Simulate a delay function, similar to Kotlin's delay
    static void delay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
            System.err.println("KWFJF: Thread interrupted: " + e.getMessage());
        }
    }

    // Function 1: Continuation with empty parameter list (represented by a Consumer<Void>)
    static CompletableFuture<Void> functionWithEmptyContinuation(Consumer<Void> continuation) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        System.out.println("KWFJF: functionWithEmptyContinuation started");
        // Simulate some work
        delay(1000);
        System.out.println("KWFJF: functionWithEmptyContinuation finished");
        continuation.accept(null); // Passing null to represent Unit
        future.complete(null);
        return future;
    }

    // Function 2: Continuation with a byte array parameter (represented by a Consumer<byte[]>)
    static CompletableFuture<byte[]> functionWithByteArrayContinuation(byte[] data, Consumer<byte[]> continuation) {
        CompletableFuture<byte[]> future = new CompletableFuture<>();
        System.out.println("KWFJF: functionWithByteArrayContinuation started with data: " + Arrays.toString(data));
        // Simulate processing the data
        delay(1500);
        byte[] result = data.clone(); // Use clone() for defensive copying
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) (result[i] + 1);
        }
        System.out.println("KWFJF: functionWithByteArrayContinuation finished, returning modified data: " + Arrays.toString(result));
        continuation.accept(result);
        future.complete(result);
        return future;
    }

    // Function 3: Continuation with an int parameter (represented by a Consumer<Integer>)
    static CompletableFuture<Integer> functionWithIntContinuation(Consumer<Integer> continuation) {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        System.out.println("KWFJF: functionWithIntContinuation started");
        // Simulate some calculation
        delay(800);
        int result = 42;
        System.out.println("KWFJF: functionWithIntContinuation finished, returning result: " + result);
        continuation.accept(result);
        future.complete(result);
        return future;
    }

    public static void main(String[] args) {
        System.out.println("KWFJF: Main function started");

        // Call the functions and handle their continuations using CompletableFuture
        try {
            functionWithEmptyContinuation(result -> {
                System.out.println("KWFJF: Continuation for functionWithEmptyContinuation completed successfully");
            }).get(2, TimeUnit.SECONDS); //Added timeout

            byte[] byteArrayData = {1, 2, 3};
            functionWithByteArrayContinuation(byteArrayData, result -> {
                System.out.println("KWFJF: Continuation for functionWithByteArrayContinuation completed successfully with result: " + Arrays.toString(result));
            }).get(2, TimeUnit.SECONDS); //Added timeout

            functionWithIntContinuation(result -> {
                System.out.println("KWFJF: Continuation for functionWithIntContinuation completed successfully with result: " + result);
            }).get(2, TimeUnit.SECONDS); //Added timeout
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        System.out.println("KWFJF: Main function finished");
    }
}