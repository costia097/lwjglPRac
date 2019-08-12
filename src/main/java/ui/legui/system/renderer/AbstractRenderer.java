package ui.legui.system.renderer;

import ui.legui.component.Frame;
import ui.legui.component.Layer;
import ui.legui.component.LayerContainer;
import ui.legui.system.context.Context;

/**
 * Base of main renderer which called by renderer thread.
 */
public abstract class AbstractRenderer implements Renderer {

    public abstract void initialize();

    protected abstract void preRender(Context context);

    protected abstract void postRender(Context context);

    public void render(Frame display, Context context) {
        preRender(context);
        for (Layer layer : display.getAllLayers()) {
            LayerContainer container = layer.getContainer();
            RendererProvider.getInstance().getComponentRenderer(container.getClass()).render(container, context);
        }
        postRender(context);
    }

    public abstract void destroy();

}
