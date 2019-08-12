package ui.legui.listener;

import ui.legui.event.CharEvent;

/**
 * Created by ShchAlexander on 2/14/2017.
 */
public interface CharEventListener extends EventListener<CharEvent> {

    /**
     * Used to handle {@link CharEvent}.
     *
     * @param event event to handle.
     */
    void process(CharEvent event);
}
