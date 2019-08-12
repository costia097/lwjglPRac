package ui.legui.listener;

import ui.legui.event.WindowIconifyEvent;

/**
 * Created by ShchAlexander on 04.02.2017.
 */
public interface WindowIconifyEventListener extends EventListener<WindowIconifyEvent> {

    void process(WindowIconifyEvent event);
}
