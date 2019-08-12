package ui.legui.component.misc.listener.textarea;

import ui.legui.component.TextAreaField;
import ui.legui.component.event.textarea.TextAreaFieldUpdateEvent;
import ui.legui.event.MouseDragEvent;
import ui.legui.listener.MouseDragEventListener;
import ui.legui.listener.processor.EventProcessor;

import static ui.legui.input.Mouse.MouseButton.MOUSE_BUTTON_LEFT;

/**
 * Mouse drag event listener for text area. Used to update selection indices.
 */
public class TextAreaFieldDragEventListener implements MouseDragEventListener {

    /**
     * Used to handle {@link MouseDragEvent}.
     *
     * @param event event to handle.
     */
    @Override
    public void process(MouseDragEvent event) {
        TextAreaField textAreaField = (TextAreaField) event.getTargetComponent();
        if (MOUSE_BUTTON_LEFT.isPressed()) {
            int mouseCaretPosition = textAreaField.getMouseCaretPosition();
            textAreaField.setCaretPosition(mouseCaretPosition);
            textAreaField.setEndSelectionIndex(mouseCaretPosition);

            EventProcessor.getInstance().pushEvent(new TextAreaFieldUpdateEvent(textAreaField, event.getContext(), event.getFrame()));
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj == this || obj.getClass() == this.getClass());
    }
}
