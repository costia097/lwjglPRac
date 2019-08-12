package ui.legui.listener;

import ui.legui.event.ScrollEvent;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public interface ScrollEventListener extends EventListener<ScrollEvent> {

    void process(ScrollEvent event);
}
