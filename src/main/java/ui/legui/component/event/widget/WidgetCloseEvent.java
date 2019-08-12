package ui.legui.component.event.widget;

import ui.legui.component.Frame;
import ui.legui.component.Widget;
import ui.legui.event.Event;
import ui.legui.system.context.Context;

/**
 * @author ShchAlexander.
 */
public class WidgetCloseEvent<T extends Widget> extends Event<T> {

    public WidgetCloseEvent(T component, Context context, Frame frame) {
        super(component, context, frame);
    }
}
