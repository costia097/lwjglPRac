package ui.legui.system.handler;

import ui.legui.component.Frame;
import ui.legui.component.Layer;
import ui.legui.event.Event;
import ui.legui.system.context.Context;
import ui.legui.system.event.SystemEvent;

import java.util.Collections;
import java.util.List;

/**
 * Abstract handler for {@link SystemEvent}s. Used to handle events and transform them to UI events (Subclasses of {@link Event}).
 */
public abstract class AbstractSystemEventHandler<E extends SystemEvent> implements SystemEventHandler<E> {

    /**
     * Default implementation of event handler {@link SystemEventHandler#handle(SystemEvent, Frame, Context)} method. Used to handle events and check if event
     * should be passed to underlying layer or not.
     *
     * @param event event to handle.
     * @param frame target frame for event.
     * @param context context.
     */
    public final void handle(E event, Frame frame, Context context) {
        preHandle(event, frame, context);
        List<Layer> layers = frame.getAllLayers();
        Collections.reverse(layers);
        for (Layer layer : layers) {
            if (layer.isEventReceivable()) {
                if (!layer.getContainer().isVisible() || !layer.getContainer().isEnabled()) {
                    continue;
                }
                if (handle(event, layer, context, frame)) {
                    return;
                }
            }
            if (!layer.isEventPassable()) {
                return;
            }
        }
        postHandle(event, frame, context);
    }

    /**
     * This method should be overrided to pre-handle some event.
     *
     * @param event event which should be pre-processed.
     * @param frame target frame for event.
     * @param context context.
     */
    protected void preHandle(E event, Frame frame, Context context) {
        // This method should be overrided to pre-handle some event.
    }

    /**
     * This method used to handle some {@link SystemEvent} and produce (or not) {@link Event} instances (which are UI events).
     *
     * @param event event to be processed.
     * @param layer target event layer.
     * @param context context.
     * @param frame frame.
     *
     * @return true if shouldn't be processed in other underlying layers.
     */
    protected boolean handle(E event, Layer layer, Context context, Frame frame) {
        return false;
    }

    /**
     * This method should be overrided to post-handle some event.
     *
     * @param event event which should be post-processed.
     * @param frame target frame for event.
     * @param context context.
     */
    protected void postHandle(E event, Frame frame, Context context) {
        // This method should be overrided to post-handle some event.
    }
}
