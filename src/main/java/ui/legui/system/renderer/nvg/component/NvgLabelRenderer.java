package ui.legui.system.renderer.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import ui.legui.component.Label;
import ui.legui.component.optional.TextState;
import ui.legui.system.context.Context;
import ui.legui.system.renderer.nvg.util.NvgText;

import static ui.legui.style.util.StyleUtilities.getInnerContentRectangle;
import static ui.legui.style.util.StyleUtilities.getPadding;
import static ui.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static ui.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public class NvgLabelRenderer extends NvgDefaultComponentRenderer<Label> {

    @Override
    public void renderSelf(Label label, Context context, long nanovg) {
        createScissor(nanovg, label);
        {
            Vector2f pos = label.getAbsolutePosition();
            Vector2f size = label.getSize();

            /*Draw background rectangle*/
            renderBackground(label, context, nanovg);

            // draw text into box
            TextState textState = label.getTextState();
            Vector4f padding = getPadding(label, label.getStyle());
            Vector4f rect = getInnerContentRectangle(pos, size, padding);
            NvgText.drawTextLineToRect(nanovg, textState, rect, false);
        }
        resetScissor(nanovg);
    }
}
