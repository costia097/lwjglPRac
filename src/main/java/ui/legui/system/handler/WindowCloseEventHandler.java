package ui.legui.system.handler;

import ui.legui.component.Component;
import ui.legui.component.Frame;
import ui.legui.component.Layer;
import ui.legui.event.WindowCloseEvent;
import ui.legui.listener.processor.EventProcessor;
import ui.legui.system.context.Context;
import ui.legui.system.event.SystemWindowCloseEvent;

import java.util.List;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class WindowCloseEventHandler extends AbstractSystemEventHandler<SystemWindowCloseEvent> {

    @Override
    protected boolean handle(SystemWindowCloseEvent event, Layer layer, Context context, Frame frame) {
        pushEvent(layer.getContainer(), context, frame);
        return false;
    }

    private void pushEvent(Component component, Context context, Frame frame) {
        if (!(component.isVisible())) {
            return;
        }
        EventProcessor.getInstance().pushEvent(new WindowCloseEvent(component, context, frame));
        List<Component> childComponents = component.getChildComponents();
        for (Component child : childComponents) {
            pushEvent(child, context, frame);
        }
    }
}
