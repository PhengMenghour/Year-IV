import java.util.Scanner;

public class TP01_3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Enter number of thread: ");
        int numberOfThread = scanner.nextInt();

        // Unordered thread
        System.out.println("Thread is unordered");
        UnorderedThread unordered = new UnorderedThread();
        unordered.createThreads(numberOfThread);


        // Ordered thread
        System.out.println("Thread is in order");
        OrderedThread ordered = new OrderedThread();
        ordered.createThreads(numberOfThread);

        scanner.close();
    }
}
