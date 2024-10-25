public class OrderedThread {
    public void createThreads(int numberOfThread){
        /*
            By using thread.join(), we make the main thread wait for each individual
            thread to finish before proceeding to start the next one. This ensures that 
            the threads complete execution in the order they were created.
         */

        Thread[] threads = new Thread[numberOfThread];

        for (int i = 0; i < numberOfThread; i++){
            int index = i;
            threads[i] = new Thread(() -> {
                System.out.println("Thread: " + index);
            });
            threads[i].start();

            // Ensure thread finish in order
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }
}
