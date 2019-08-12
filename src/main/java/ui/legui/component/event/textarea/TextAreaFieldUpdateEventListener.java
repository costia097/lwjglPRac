package ui.legui.component.event.textarea;

import ui.legui.listener.EventListener;

/**
 * @author ShchAlexander.
 */
public interface TextAreaFieldUpdateEventListener extends EventListener<TextAreaFieldUpdateEvent> {

    /**
     * Used to handle {@link TextAreaFieldUpdateEvent} event.
     *
     * @param event event to handle.
     */
    @Override
    void process(TextAreaFieldUpdateEvent event);
}
