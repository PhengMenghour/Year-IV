import java.util.Scanner;

public class TP01_3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Enter number of thread: ");
        int numberOfThread = scanner.nextInt();
        System.out.print("Choose execution type (1 for unordered, 2 for ordered): ");
        int choice = scanner.nextInt();
        
        if (choice == 1) {
            UnorderedThread unordered = new UnorderedThread();
            unordered.createThreads(numberOfThread);
        } else if (choice == 2) {
            OrderedThread ordered = new OrderedThread();
            ordered.createThreads(numberOfThread);
        } else {
            System.out.println("Invalid choice.");
        }
        // // Unordered thread
        // System.out.println("Thread is unordered");
        // UnorderedThread unordered = new UnorderedThread();
        // unordered.createThreads(numberOfThread);


        // // Ordered thread
        // System.out.println("Thread is in order");
        // OrderedThread ordered = new OrderedThread();
        // ordered.createThreads(numberOfThread);

        scanner.close();
    }
}
