package ui.legui.listener;

import ui.legui.event.WindowFocusEvent;

/**
 * Created by ShchAlexander on 04.02.2017.
 */
public interface WindowFocusEventListener extends EventListener<WindowFocusEvent> {

    void process(WindowFocusEvent event);
}
