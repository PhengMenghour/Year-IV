package Course.Software_Engineering;
public class UseOfThreads{
    static int var1 = 0;

    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(() -> {
            for(int i = 0; i < 1000_000; i++){
                // System.out.println(i);
                var1++;
            }
        });
        t1.start();

        Thread t2 = new Thread(new Runnable() {
            public void run(){
                for(int i = 0; i < 1000_000; i++){
                    // System.out.println(i);
                    var1++;
                }
            }
        });
        t2.start();
        // t1.wait();
        // t1.notify();

        t1.join();
        t2.join();
    }
}