package ui.legui.listener;

import ui.legui.event.KeyEvent;

/**
 * Created by ShchAlexander on 2/13/2017.
 */
public interface KeyEventListener extends EventListener<KeyEvent> {

    void process(KeyEvent event);
}
