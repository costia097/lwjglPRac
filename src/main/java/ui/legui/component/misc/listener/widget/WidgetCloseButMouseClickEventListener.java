package ui.legui.component.misc.listener.widget;

import ui.legui.component.Widget;
import ui.legui.component.event.widget.WidgetCloseEvent;
import ui.legui.event.MouseClickEvent;
import ui.legui.listener.MouseClickEventListener;
import ui.legui.listener.processor.EventProcessor;

import static ui.legui.event.MouseClickEvent.MouseClickAction.CLICK;

/**
 * @author ShchAlexander.
 */
public class WidgetCloseButMouseClickEventListener implements MouseClickEventListener {

    private Widget widget;

    public WidgetCloseButMouseClickEventListener(Widget widget) {
        this.widget = widget;
    }

    @Override
    public void process(MouseClickEvent event) {
        if (CLICK == event.getAction()) {
            widget.hide();
            EventProcessor.getInstance().pushEvent(new WidgetCloseEvent<>(widget, event.getContext(), event.getFrame()));
        }
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null) && ((obj == this) || ((obj != this) && (obj.getClass() == this.getClass())));
    }
}
