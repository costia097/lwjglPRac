package ui.legui.system.renderer.nvg.component;

import org.joml.Vector2f;
import ui.legui.component.ImageView;
import ui.legui.system.context.Context;
import ui.legui.system.renderer.nvg.util.NvgRenderUtils;

import java.util.HashMap;

import static ui.legui.system.renderer.ImageRenderer.C_RADIUS;
import static ui.legui.system.renderer.nvg.NvgRenderer.renderImage;
import static ui.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static ui.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public class NvgImageViewRenderer extends NvgDefaultComponentRenderer<ImageView> {

    @Override
    protected void renderSelf(ImageView imageView, Context context, long nanovg) {
        Vector2f size = imageView.getSize();
        Vector2f position = imageView.getAbsolutePosition();

        createScissor(nanovg, imageView);
        {
            HashMap<String, Object> p = new HashMap<>();
            p.put(C_RADIUS, NvgRenderUtils.getBorderRadius(imageView));

            renderBackground(imageView, context, nanovg);
            renderImage(imageView.getImage(), position, size, p, context);
        }
        resetScissor(nanovg);
    }
}
