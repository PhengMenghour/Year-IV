import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class PrimeTask implements Runnable {
    private final int start;
    private final int end;
    private final String threadName;
    private static final AtomicInteger sum = new AtomicInteger(0);

    public PrimeTask(int start, int end, String threadName) {
        this.start = start;
        this.end = end;
        this.threadName = threadName;
    }

    private boolean isPrime(int num) {
        if (num < 2) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) { // Corrected the divisor check
                return false;
            }
        }
        return true;
    }

    private void addToSum(int prime) {
        sum.addAndGet(prime);
    }

    public static int getSum() {
        return sum.get();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        for (int i = start; i <= end; i++) {
            if (isPrime(i)) {
                System.out.printf(threadName + i + " ");
                addToSum(i);
            }
        }
    }

}

public class TP01_4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.printf("Enter start number: ");
        int startNum = scanner.nextInt();

        System.out.printf("Enter end number: ");
        int endNum = scanner.nextInt();

        int rangeSize = endNum - startNum + 1;
        int maxNumberPerThread = 100;
        int numberThreads = (int) Math.ceil((double) rangeSize / maxNumberPerThread);

        System.out.println("Running: " + numberThreads + " threads.");

        ExecutorService executor = Executors.newFixedThreadPool(numberThreads);
        List<Runnable> tasks = new ArrayList<>();

        for (int i = 0; i < numberThreads; i++) {
            int threadStart = startNum + i * maxNumberPerThread;
            int threadEnd = Math.min(threadStart + maxNumberPerThread - 1, endNum);
            String threadName = "T" + i + "-";
            PrimeTask primeTask = new PrimeTask(threadStart, threadEnd, threadName);
            tasks.add(primeTask);
            executor.submit(primeTask);
        }

        executor.shutdown();
        try {
            // Wait for all tasks to complete with a 1-hour timeout
            if (!executor.awaitTermination(1, TimeUnit.HOURS)) {
                System.err.println("Threads didn't finish in the allocated time.");
            }
        } catch (InterruptedException e) {
            System.err.println("Thread execution was interrupted.");
        }

        System.out.println("\nThreads completed");
        System.out.println("Sum of prime numbers: " + PrimeTask.getSum());

        scanner.close();

    }
}
