package ui.legui.system.renderer;

import ui.legui.component.Component;
import ui.legui.icon.Icon;
import ui.legui.image.Image;
import ui.legui.style.Border;
import ui.legui.system.renderer.nvg.NvgRendererProvider;

import java.util.List;

/**
 * Renderer provider. Used to provide specific renderers for main renderer.
 */
public abstract class RendererProvider {

    public static void setRendererProvider(RendererProvider provider) {
        RPH.I = provider;
    }

    public static RendererProvider getInstance() {
        return RPH.I;
    }

    public abstract <C extends Component> ComponentRenderer getComponentRenderer(Class<C> componentClass);

    public abstract <C extends Border> BorderRenderer getBorderRenderer(Class<C> borderClass);

    public abstract <I extends Icon> IconRenderer getIconRenderer(Class<I> iconClass);

    public abstract <I extends Image> ImageRenderer getImageRenderer(Class<I> imageClass);

    protected abstract <C extends Component, R extends ComponentRenderer<C>> void addComponentRenderer(Class<C> componentClass, R renderer);

    protected abstract <C extends Border, R extends BorderRenderer<C>> void addBorderRenderer(Class<C> borderClass, R renderer);

    protected abstract <I extends Icon, R extends IconRenderer<I>> void addIconRenderer(Class<I> iconClass, R renderer);

    protected abstract <I extends Image, R extends ImageRenderer<I>> void addImageRenderer(Class<I> imageClass, R renderer);

    public abstract List<ComponentRenderer> getComponentRenderers();

    private static class RPH {

        private static RendererProvider I = NvgRendererProvider.getInstance();
    }


}
