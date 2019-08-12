package ui.legui.system.handler;

import ui.legui.component.Component;
import ui.legui.component.Frame;
import ui.legui.component.Layer;
import ui.legui.event.ScrollEvent;
import ui.legui.input.Mouse;
import ui.legui.listener.processor.EventProcessor;
import ui.legui.system.context.Context;
import ui.legui.system.event.SystemScrollEvent;

import java.util.List;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class ScrollEventHandler extends AbstractSystemEventHandler<SystemScrollEvent> {

    @Override
    protected boolean handle(SystemScrollEvent event, Layer layer, Context context, Frame frame) {
        List<Component> targetComponentList = SehUtil.getTargetComponentList(layer, Mouse.getCursorPosition());
        for (Component component : targetComponentList) {
            EventProcessor.getInstance().pushEvent(new ScrollEvent<>(component, context, frame, event.xoffset, event.yoffset));
        }
        return false;
    }
}
