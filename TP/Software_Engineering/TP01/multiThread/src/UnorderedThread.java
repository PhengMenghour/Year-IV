public class UnorderedThread {
    public void createThreads(int numberOfThread) {
        /*
            Thread scheduling is managed by the JVM and the underlying OS,
            and there's no guarantee that threads will finish in the order 
            they were started. Each thread runs independently and may be 
            pre-empted by another thread at any point in time.
         */
        for(int i = 0; i < numberOfThread; i++){
            int index = i;
            Thread thread = new Thread(() -> {
                System.out.println("Thread: " + index);
            });
            thread.start();
        }
    }
}
