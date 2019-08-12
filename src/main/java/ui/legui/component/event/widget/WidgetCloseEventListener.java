package ui.legui.component.event.widget;

import ui.legui.listener.EventListener;

/**
 * @author ShchAlexander.
 */
public interface WidgetCloseEventListener<T extends WidgetCloseEvent> extends EventListener<T> {

    /**
     * Used to handle {@link WidgetCloseEvent} event.
     *
     * @param event event to handle.
     */
    void process(WidgetCloseEvent event);
}
