package ui.legui.component.misc.listener.textinput;

import ui.legui.component.TextInput;
import ui.legui.event.MouseDragEvent;
import ui.legui.listener.MouseDragEventListener;

import static ui.legui.input.Mouse.MouseButton.MOUSE_BUTTON_LEFT;

/**
 * Mouse drag event listener for text input. Used to update selection indices.
 */
public class TextInputDragEventListener implements MouseDragEventListener {

    @Override
    public void process(MouseDragEvent event) {
        TextInput textInput = (TextInput) event.getTargetComponent();
        if (MOUSE_BUTTON_LEFT.isPressed()) {
            int mouseCaretPosition = textInput.getMouseCaretPosition();
            textInput.setCaretPosition(mouseCaretPosition);
            textInput.setEndSelectionIndex(mouseCaretPosition);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj == this || obj.getClass() == this.getClass());
    }
}
