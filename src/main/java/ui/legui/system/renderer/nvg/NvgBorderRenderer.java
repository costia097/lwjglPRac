package ui.legui.system.renderer.nvg;

import ui.legui.component.Component;
import ui.legui.style.Border;
import ui.legui.system.context.Context;
import ui.legui.system.renderer.BorderRenderer;

import static ui.legui.system.renderer.nvg.NvgRenderer.NVG_CONTEXT;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public abstract class NvgBorderRenderer<B extends Border> extends BorderRenderer<B> {

    @Override
    public void renderBorder(B border, Component component, Context context) {
        long nanovgContext = (long) context.getContextData().get(NVG_CONTEXT);
        if (!border.isEnabled()) {
            return;
        }
        renderBorder(border, component, context, nanovgContext);
    }

    protected abstract void renderBorder(B border, Component component, Context context, long nanovg);

}
