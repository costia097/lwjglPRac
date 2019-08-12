package ui.legui.system.handler;

import ui.legui.component.Component;
import ui.legui.component.Frame;
import ui.legui.event.KeyEvent;
import ui.legui.input.Keyboard;
import ui.legui.listener.processor.EventProcessor;
import ui.legui.system.context.Context;
import ui.legui.system.event.SystemKeyEvent;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * System handler that used to update key states of {@link Keyboard.Key}.
 */
public class KeyEventHandler implements SystemEventHandler<SystemKeyEvent> {

    @Override
    public void handle(SystemKeyEvent event, Frame frame, Context context) {
        int keyCode = event.key;
        if (keyCode != -1) {
            Keyboard.Key key = Keyboard.Key.getByCode(keyCode);
            if (key != null) {
                key.setPressed(event.action != GLFW_RELEASE);
            }
        }

        Component focusedGui = context.getFocusedGui();
        if (focusedGui == null) {
            return;
        }

        EventProcessor.getInstance().pushEvent(new KeyEvent(focusedGui, context, frame, event.action, keyCode, event.mods, event.scancode));
    }
}
