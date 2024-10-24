public class OrderedThread {
    public void createThreads(int numberOfThread){
        Thread[] threads = new Thread[numberOfThread];

        for (int i = 0; i < numberOfThread; i++){
            int index = i;
            threads[i] = new Thread(() -> {
                System.out.println("Threado: " + index);
            });
            threads[i].start();
        }

        // Ensure threads finish in order
        for(int i = 0; i < numberOfThread; i++){
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }
}
