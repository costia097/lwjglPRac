package ui.legui.component.event.textinput;

import ui.legui.listener.EventListener;

/**
 * @author ShchAlexander.
 */
public interface TextInputContentChangeEventListener extends EventListener<TextInputContentChangeEvent> {

    /**
     * Used to handle {@link TextInputContentChangeEvent} event.
     *
     * @param event event to handle.
     */
    @Override
    void process(TextInputContentChangeEvent event);
}
