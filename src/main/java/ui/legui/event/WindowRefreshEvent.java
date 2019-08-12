package ui.legui.event;

import ui.legui.component.Component;
import ui.legui.component.Frame;
import ui.legui.system.context.Context;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class WindowRefreshEvent<T extends Component> extends Event<T> {

    public WindowRefreshEvent(T component, Context context, Frame frame) {
        super(component, context, frame);
    }
}
