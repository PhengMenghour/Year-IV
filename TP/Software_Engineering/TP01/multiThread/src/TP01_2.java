import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class TP01_2 {
    static boolean isRunning = true;
    static String message = "Hit me!";

    public static void main(String[] args) throws Exception {
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
            @Override
            public void nativeKeyPressed(NativeKeyEvent arg0) {
                if (arg0.getKeyCode() == NativeKeyEvent.VC_ENTER) {
                    isRunning = false;
                    return;
                }
                message = NativeKeyEvent.getKeyText(arg0.getKeyCode()).toLowerCase();

                NativeKeyListener.super.nativeKeyPressed(arg0);
            }
        });
        // main thread loop with condition
        while (isRunning) {
            System.out.println(message);
            Thread.sleep(200);
        }
        System.out.println();
        System.out.println("Thank You!");


    }
}
