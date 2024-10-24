public class UnorderedThread {
    public void createThreads(int numberOfThread) {
        for(int i = 0; i < numberOfThread; i++){
            int index = i;
            Thread thread = new Thread(() -> {
                System.out.println("Thread: " + index);
            });
            thread.start();
        }
    }
}
