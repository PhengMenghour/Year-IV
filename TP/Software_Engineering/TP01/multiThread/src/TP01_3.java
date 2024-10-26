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
  
        scanner.close();
    }
}
