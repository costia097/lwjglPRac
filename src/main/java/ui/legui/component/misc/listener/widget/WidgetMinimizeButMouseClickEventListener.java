package ui.legui.component.misc.listener.widget;

import ui.legui.component.Widget;
import ui.legui.event.MouseClickEvent;
import ui.legui.listener.MouseClickEventListener;

import static ui.legui.event.MouseClickEvent.MouseClickAction.CLICK;

/**
 * @author ShchAlexander.
 */
public class WidgetMinimizeButMouseClickEventListener implements MouseClickEventListener {

    private Widget widget;

    public WidgetMinimizeButMouseClickEventListener(Widget widget) {
        this.widget = widget;
    }

    public void process(MouseClickEvent event) {
        if (CLICK == event.getAction()) {
            boolean newValue = !widget.isMinimized();
            widget.getMinimizeButton().getStyle().getBackground().setIcon(newValue ? widget.getMaximizeIcon() : widget.getMinimizeIcon());
            widget.setMinimized(newValue);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null) && ((obj == this) || ((obj != this) && (obj.getClass() == this.getClass())));
    }
}
