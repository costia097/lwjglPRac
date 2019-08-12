package ui.legui.system.handler;

import ui.legui.component.Component;
import ui.legui.component.Frame;
import ui.legui.component.Layer;
import ui.legui.event.WindowRefreshEvent;
import ui.legui.listener.processor.EventProcessor;
import ui.legui.system.context.Context;
import ui.legui.system.event.SystemWindowRefreshEvent;

import java.util.List;

/**
 * Created by ShchAlexander on 2/2/2017.
 */
public class WindowRefreshEventHandler extends AbstractSystemEventHandler<SystemWindowRefreshEvent> {

    @Override
    protected boolean handle(SystemWindowRefreshEvent event, Layer layer, Context context, Frame frame) {
        pushEvent(layer.getContainer(), context, frame);
        return false;
    }

    private void pushEvent(Component component, Context context, Frame frame) {
        if (!(component.isVisible())) {
            return;
        }
        EventProcessor.getInstance().pushEvent(new WindowRefreshEvent(component, context, frame));
        List<Component> childComponents = component.getChildComponents();
        for (Component child : childComponents) {
            pushEvent(child, context, frame);
        }
    }
}
