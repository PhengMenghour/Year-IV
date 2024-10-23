import com.github.kwhat.jnativehook.GlobalScreen;

public class TP01_1 {
    public static void main(String[] args) throws Exception {
        // System.out.println("Hello, World!");
        GlobalScreen.registerNativeHook(); // Enable thread

        // Key Listener
        GlobalScreen.addNativeKeyListener(new CustomizeKeyListener());

        // main thread run forever
        while (ConstantVariable.isRunning) {
            System.out.println(ConstantVariable.message + " ");
            Thread.sleep(250);
        }
        System.out.println();
        System.out.println("Thank you!");
    }
}
