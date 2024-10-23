import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class CustomizeKeyListener implements NativeKeyListener {

    @Override
    public void nativeKeyPressed(NativeKeyEvent arg0) {
        // TODO Auto-generated method stub
        // System.out.println("You Press key board " + NativeKeyEvent.getKeyText(arg0.getKeyCode()));
        // NativeKeyListener.super.nativeKeyPressed(arg0);

        if (arg0.getKeyCode() == NativeKeyEvent.VC_ENTER) {
            ConstantVariable.isRunning = false;
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }
    
}
