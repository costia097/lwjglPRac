package ui.legui.system.handler;

import ui.legui.component.Frame;
import ui.legui.system.context.Context;
import ui.legui.system.event.SystemEvent;

/**
 * Created by ShchAlexander on 10.02.2017.
 */
public interface SystemEventHandler<E extends SystemEvent> {

    void handle(E event, Frame frame, Context context);
}

