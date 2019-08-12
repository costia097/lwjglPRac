package ui.legui.system.renderer.nvg.icon;

import org.joml.Vector2f;
import org.joml.Vector4f;
import ui.legui.component.Component;
import ui.legui.icon.Icon;
import ui.legui.style.color.ColorConstants;
import ui.legui.system.context.Context;
import ui.legui.system.renderer.nvg.NvgIconRenderer;
import ui.legui.system.renderer.nvg.util.NvgShapes;

import static ui.legui.system.renderer.nvg.util.NvgRenderUtils.getBorderRadius;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public class NvgDefaultIconRenderer extends NvgIconRenderer {

    @Override
    protected void renderIcon(Icon icon, Component component, Context context, long nanovg) {
        if (!component.isVisible()) {
            return;
        }
        // render simple rectangle border
        Vector2f iconSize = icon.getSize();
        Vector2f p        = calculateIconPosition(icon, component, iconSize);
        float    w        = iconSize.x;
        float    h        = iconSize.y;

        NvgShapes.drawRect(nanovg, new Vector4f(p.x, p.y, w, h), ColorConstants.red, getBorderRadius(component));
        NvgShapes.drawRectStroke(nanovg, new Vector4f(p.x, p.y, w, h), ColorConstants.black, 1, getBorderRadius(component));
    }
}
