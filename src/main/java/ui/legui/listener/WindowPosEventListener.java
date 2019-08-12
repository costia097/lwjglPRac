package ui.legui.listener;

import ui.legui.event.WindowPosEvent;

/**
 * Created by ShchAlexander on 04.02.2017.
 */
public interface WindowPosEventListener extends EventListener<WindowPosEvent> {

    void process(WindowPosEvent event);
}
