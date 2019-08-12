package ui.legui.system.renderer.nvg.component;

import org.joml.Vector4f;
import ui.legui.component.Component;
import ui.legui.icon.Icon;
import ui.legui.style.Style;
import ui.legui.system.context.Context;
import ui.legui.system.renderer.RendererProvider;
import ui.legui.system.renderer.nvg.NvgComponentRenderer;
import ui.legui.system.renderer.nvg.util.NvgRenderUtils;
import ui.legui.system.renderer.nvg.util.NvgShapes;

import static ui.legui.system.renderer.nvg.NvgRenderer.renderBorderWScissor;
import static ui.legui.system.renderer.nvg.NvgRenderer.renderIcon;
import static ui.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static ui.legui.system.renderer.nvg.util.NvgRenderUtils.getBorderRadius;
import static ui.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;
import static org.lwjgl.nanovg.NanoVG.nvgRestore;
import static org.lwjgl.nanovg.NanoVG.nvgSave;

/**
 * Default component renderer.
 *
 * @param <C> component type.
 */
public class NvgDefaultComponentRenderer<C extends Component> extends NvgComponentRenderer<C> {

    /**
     * Used to render component.
     *
     * @param component component to render.
     * @param context legui context.
     * @param nanovg nanovg context pointer.
     */
    @Override
    protected void renderComponent(C component, Context context, long nanovg) {
        if (component.isVisible() && component.getSize().lengthSquared() > 0.01) {
            renderSelf(component, context, nanovg);
            renderChildComponents(component, context, nanovg);
            renderBorder(component, context, nanovg);
        }
    }

    /**
     * Used to render component without childComponents.
     *
     * @param component component to render.
     * @param context context.
     * @param nanovg nanovg context pointer.
     */
    protected void renderSelf(C component, Context context, long nanovg) {
        createScissor(nanovg, component);
        {
            renderBackground(component, context, nanovg);
        }
        resetScissor(nanovg);
    }

    protected void renderBackground(C component, Context context, long nanovg) {
        boolean focused = component.isFocused();
        boolean hovered = component.isHovered();
        boolean pressed = component.isPressed();

        Style style = component.getStyle();
        Style currStyle = component.getStyle();

        Icon bgIcon = style.getBackground().getIcon();
        Vector4f bgColor = style.getBackground().getColor();
        Vector4f cornerRadius = getBorderRadius(component);

        if (focused) {
            currStyle = component.getFocusedStyle();
            if (currStyle.getBackground().getColor() != null) {
                bgColor = currStyle.getBackground().getColor();
            }
            if (currStyle.getBackground().getIcon() != null) {
                bgIcon = currStyle.getBackground().getIcon();
            }
        }
        if (hovered) {
            currStyle = component.getHoveredStyle();
            if (currStyle.getBackground().getColor() != null) {
                bgColor = currStyle.getBackground().getColor();
            }
            if (currStyle.getBackground().getIcon() != null) {
                bgIcon = currStyle.getBackground().getIcon();
            }
        }
        if (pressed) {
            currStyle = component.getPressedStyle();
            if (currStyle.getBackground().getColor() != null) {
                bgColor = currStyle.getBackground().getColor();
            }
            if (currStyle.getBackground().getIcon() != null) {
                bgIcon = currStyle.getBackground().getIcon();
            }
        }

        NvgRenderUtils.renderShadow(nanovg, component);

        nvgSave(nanovg);
        NvgShapes.drawRect(nanovg, component.getAbsolutePosition(), component.getSize(), bgColor, cornerRadius);
        if (bgIcon != null) {
            renderIcon(bgIcon, component, context);
        }
        nvgRestore(nanovg);
    }

    /**
     * Used to render component childComponents.
     *
     * @param component component to render.
     * @param context context.
     * @param nanovg nanovg context pointer.
     */
    protected void renderChildComponents(C component, Context context, long nanovg) {
        for (Component child : component.getChildComponents()) {
            RendererProvider.getInstance().getComponentRenderer(child.getClass()).render(child, context);
        }
    }

    /**
     * Used to render component border.
     *
     * @param component component to render.
     * @param context context.
     * @param nanovg nanovg context pointer.
     */
    protected void renderBorder(C component, Context context, long nanovg) {
        renderBorderWScissor(component, context, nanovg);
    }
}
