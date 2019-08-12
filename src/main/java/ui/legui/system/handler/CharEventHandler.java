package ui.legui.system.handler;

import ui.legui.component.Component;
import ui.legui.component.Frame;
import ui.legui.event.CharEvent;
import ui.legui.listener.processor.EventProcessor;
import ui.legui.system.context.Context;
import ui.legui.system.event.SystemCharEvent;

/**
 * Created by ShchAlexander on 2/14/2017.
 */
public class CharEventHandler implements SystemEventHandler<SystemCharEvent> {

    @Override
    public void handle(SystemCharEvent event, Frame frame, Context context) {
        Component focusedGui = context.getFocusedGui();
        if (focusedGui == null) {
            return;
        }

        EventProcessor.getInstance().pushEvent(new CharEvent(focusedGui, context, frame, event.codepoint));
    }
}
