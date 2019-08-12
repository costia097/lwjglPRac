package ui.legui.system.renderer.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import ui.legui.component.ToggleButton;
import ui.legui.icon.Icon;
import ui.legui.style.Style;
import ui.legui.system.context.Context;
import ui.legui.system.renderer.nvg.util.NvgRenderUtils;
import ui.legui.system.renderer.nvg.util.NvgShapes;
import ui.legui.system.renderer.nvg.util.NvgText;

import static ui.legui.system.renderer.nvg.NvgRenderer.renderIcon;
import static ui.legui.system.renderer.nvg.util.NvgRenderUtils.createScissorByParent;
import static ui.legui.system.renderer.nvg.util.NvgRenderUtils.getBorderRadius;
import static ui.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;
import static org.lwjgl.nanovg.NanoVG.nvgIntersectScissor;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public class NvgToggleButtonRenderer extends NvgDefaultComponentRenderer<ToggleButton> {

    @Override
    protected void renderSelf(ToggleButton toggleButton, Context context, long nanovg) {
        NvgRenderUtils.createScissor(nanovg, toggleButton);
        {
            Vector2f pos = toggleButton.getAbsolutePosition();
            Vector2f size = toggleButton.getSize();

            // render background
            renderBackground(nanovg, toggleButton, pos, size, context);

            // Render text
            nvgIntersectScissor(nanovg, pos.x, pos.y, size.x, size.y);
            NvgText.drawTextLineToRect(nanovg, toggleButton.getTextState(), pos, size, true);
        }
        resetScissor(nanovg);
    }

    private void renderBackground(long nvg, ToggleButton agui, Vector2f pos, Vector2f size, Context context) {
        boolean focused = agui.isFocused();
        boolean hovered = agui.isHovered();
        boolean pressed = agui.isPressed();

        Style style = agui.getStyle();
        Style currStyle = agui.getStyle();

        Icon icon = style.getBackground().getIcon();
        Vector4f bgColor = style.getBackground().getColor();
        Vector4f cornerRadius = getBorderRadius(agui);

        if(focused) {
            currStyle = agui.getFocusedStyle();
            if (currStyle.getBackground().getColor() != null) {
                bgColor = currStyle.getBackground().getColor();
            }
            if (currStyle.getBackground().getIcon() != null) {
                icon = currStyle.getBackground().getIcon();
            }
        }
        if(hovered) {
            currStyle = agui.getHoveredStyle();
            if (currStyle.getBackground().getColor() != null) {
                bgColor = currStyle.getBackground().getColor();
            }
            if (currStyle.getBackground().getIcon() != null) {
                icon = currStyle.getBackground().getIcon();
            }
        }
        if(pressed) {
            currStyle = agui.getPressedStyle();
            if (currStyle.getBackground().getColor() != null) {
                bgColor = currStyle.getBackground().getColor();
            }
            if (currStyle.getBackground().getIcon() != null) {
                icon = currStyle.getBackground().getIcon();
            }
        }

        NvgRenderUtils.renderShadow(nvg, agui);

        boolean toggled = agui.isToggled();
        if (toggled) {
            NvgShapes.drawRect(nvg, pos, size, agui.getToggledBackgroundColor(), cornerRadius);
        } else {
            NvgShapes.drawRect(nvg, pos, size, bgColor, cornerRadius);
        }
        if (icon != null) {
            createScissorByParent(nvg, agui);
            renderIcon(icon, agui, context);
        }
    }

}
