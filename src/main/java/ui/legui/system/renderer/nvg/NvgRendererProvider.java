package ui.legui.system.renderer.nvg;

import ui.legui.component.Button;
import ui.legui.component.CheckBox;
import ui.legui.component.Component;
import ui.legui.component.ImageView;
import ui.legui.component.Label;
import ui.legui.component.PasswordInput;
import ui.legui.component.ProgressBar;
import ui.legui.component.RadioButton;
import ui.legui.component.ScrollBar;
import ui.legui.component.Slider;
import ui.legui.component.TextAreaField;
import ui.legui.component.TextInput;
import ui.legui.component.ToggleButton;
import ui.legui.component.Tooltip;
import ui.legui.icon.CharIcon;
import ui.legui.icon.Icon;
import ui.legui.icon.ImageIcon;
import ui.legui.image.FBOImage;
import ui.legui.image.Image;
import ui.legui.image.LoadableImage;
import ui.legui.style.Border;
import ui.legui.style.border.SimpleLineBorder;
import ui.legui.system.renderer.BorderRenderer;
import ui.legui.system.renderer.ComponentRenderer;
import ui.legui.system.renderer.IconRenderer;
import ui.legui.system.renderer.ImageRenderer;
import ui.legui.system.renderer.RendererProvider;
import ui.legui.system.renderer.nvg.border.NvgDefaultBorderRenderer;
import ui.legui.system.renderer.nvg.border.NvgSimpleLineBorderRenderer;
import ui.legui.system.renderer.nvg.component.NvgButtonRenderer;
import ui.legui.system.renderer.nvg.component.NvgCheckBoxRenderer;
import ui.legui.system.renderer.nvg.component.NvgDefaultComponentRenderer;
import ui.legui.system.renderer.nvg.component.NvgImageViewRenderer;
import ui.legui.system.renderer.nvg.component.NvgLabelRenderer;
import ui.legui.system.renderer.nvg.component.NvgPasswordInputRenderer;
import ui.legui.system.renderer.nvg.component.NvgProgressBarRenderer;
import ui.legui.system.renderer.nvg.component.NvgRadioButtonRenderer;
import ui.legui.system.renderer.nvg.component.NvgScrollBarRenderer;
import ui.legui.system.renderer.nvg.component.NvgSliderRenderer;
import ui.legui.system.renderer.nvg.component.NvgTextAreaFieldRenderer;
import ui.legui.system.renderer.nvg.component.NvgTextInputRenderer;
import ui.legui.system.renderer.nvg.component.NvgToggleButtonRenderer;
import ui.legui.system.renderer.nvg.component.NvgTooltipRenderer;
import ui.legui.system.renderer.nvg.icon.NvgCharIconRenderer;
import ui.legui.system.renderer.nvg.icon.NvgDefaultIconRenderer;
import ui.legui.system.renderer.nvg.icon.NvgImageIconRenderer;
import ui.legui.system.renderer.nvg.image.NvgDefaultImageRenderer;
import ui.legui.system.renderer.nvg.image.NvgFBOImageRenderer;
import ui.legui.system.renderer.nvg.image.NvgLoadableImageRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ShchAlexander on 1/26/2017.
 */
public class NvgRendererProvider extends RendererProvider {

    private Map<Class<? extends Component>, ComponentRenderer<? extends Component>> componentRendererMap = new ConcurrentHashMap<>();
    private Map<Class<? extends Border>, BorderRenderer<? extends Border>> borderRendererMap = new ConcurrentHashMap<>();
    private Map<Class<? extends Icon>, IconRenderer<? extends Icon>> iconRendererMap = new ConcurrentHashMap<>();
    private Map<Class<? extends Image>, ImageRenderer<? extends Image>> imageRendererMap = new ConcurrentHashMap<>();

    private NvgComponentRenderer defaultComponentRenderer = new NvgDefaultComponentRenderer();
    private NvgBorderRenderer defaultBorderRenderer = new NvgDefaultBorderRenderer();
    private NvgIconRenderer defaultIconRenderer = new NvgDefaultIconRenderer();
    private NvgImageRenderer defaultImageRenderer = new NvgDefaultImageRenderer();

    private NvgRendererProvider() {

        // register component renderers
        componentRendererMap.put(Button.class, new NvgButtonRenderer());
        componentRendererMap.put(ToggleButton.class, new NvgToggleButtonRenderer());
        componentRendererMap.put(ImageView.class, new NvgImageViewRenderer());
        componentRendererMap.put(CheckBox.class, new NvgCheckBoxRenderer());
        componentRendererMap.put(Label.class, new NvgLabelRenderer());
        componentRendererMap.put(ProgressBar.class, new NvgProgressBarRenderer());
        componentRendererMap.put(RadioButton.class, new NvgRadioButtonRenderer());
        componentRendererMap.put(ScrollBar.class, new NvgScrollBarRenderer());
        componentRendererMap.put(Slider.class, new NvgSliderRenderer());
        componentRendererMap.put(TextAreaField.class, new NvgTextAreaFieldRenderer());
        componentRendererMap.put(TextInput.class, new NvgTextInputRenderer());
        componentRendererMap.put(PasswordInput.class, new NvgPasswordInputRenderer());
        componentRendererMap.put(Tooltip.class, new NvgTooltipRenderer());

        // register border renderers
        borderRendererMap.put(SimpleLineBorder.class, new NvgSimpleLineBorderRenderer());

        // register icon renderers
        iconRendererMap.put(ImageIcon.class, new NvgImageIconRenderer<>());
        iconRendererMap.put(CharIcon.class, new NvgCharIconRenderer<>());

        // register image renderers
        imageRendererMap.put(LoadableImage.class, new NvgLoadableImageRenderer<>());
        imageRendererMap.put(FBOImage.class, new NvgFBOImageRenderer());
    }

    public static NvgRendererProvider getInstance() {
        return NRPH.I;
    }

    @Override
    public <C extends Component> ComponentRenderer<C> getComponentRenderer(Class<C> componentClass) {
        return this.<C, ComponentRenderer<C>>cycledSearchOfRenderer(componentClass, componentRendererMap, defaultComponentRenderer);
    }

    @Override
    public <B extends Border> BorderRenderer<B> getBorderRenderer(Class<B> borderClass) {
        return this.<B, BorderRenderer<B>>cycledSearchOfRenderer(borderClass, borderRendererMap, defaultBorderRenderer);
    }

    @Override
    public <C extends Icon> IconRenderer getIconRenderer(Class<C> iconClass) {
        return this.<C, IconRenderer<C>>cycledSearchOfRenderer(iconClass, iconRendererMap, defaultIconRenderer);
    }

    @Override
    public <I extends Image> ImageRenderer getImageRenderer(Class<I> imageClass) {
        return this.<I, ImageRenderer<I>>cycledSearchOfRenderer(imageClass, imageRendererMap, defaultImageRenderer);
    }

    private <C, R> R cycledSearchOfRenderer(Class<C> componentClass, Map map, R defaultRenderer) {
        R renderer = null;
        Class cClass = componentClass;
        while (renderer == null) {
            renderer = ((Map<Class<C>, R>) map).get(cClass);
            if (cClass.isAssignableFrom(Component.class)) {
                break;
            }
            cClass = cClass.getSuperclass();
        }
        if (renderer == null) {
            renderer = defaultRenderer;
        }
        return renderer;
    }


    public <I extends Component, R extends NvgComponentRenderer<I>> void putComponentRenderer(Class<I> imageClass, R renderer) {
        addComponentRenderer(imageClass, renderer);
    }

    protected <I extends Component, R extends ComponentRenderer<I>> void addComponentRenderer(Class<I> imageClass, R renderer) {
        if (imageClass == null || renderer == null) {
            return;
        }
        componentRendererMap.put(imageClass, renderer);
    }

    public <I extends Border, R extends NvgBorderRenderer<I>> void putBorderRenderer(Class<I> imageClass, R renderer) {
        addBorderRenderer(imageClass, renderer);
    }

    protected <I extends Border, R extends BorderRenderer<I>> void addBorderRenderer(Class<I> imageClass, R renderer) {
        if (imageClass == null || renderer == null) {
            return;
        }
        borderRendererMap.put(imageClass, renderer);
    }


    public <I extends Icon, R extends NvgIconRenderer<I>> void putIconRenderer(Class<I> imageClass, R renderer) {
        addIconRenderer(imageClass, renderer);
    }

    protected <I extends Icon, R extends IconRenderer<I>> void addIconRenderer(Class<I> imageClass, R renderer) {
        if (imageClass == null || renderer == null) {
            return;
        }
        iconRendererMap.put(imageClass, renderer);
    }


    public <I extends Image, R extends NvgImageRenderer<I>> void putImageRenderer(Class<I> imageClass, R renderer) {
        addImageRenderer(imageClass, renderer);
    }

    protected <I extends Image, R extends ImageRenderer<I>> void addImageRenderer(Class<I> imageClass, R renderer) {
        if (imageClass == null || renderer == null) {
            return;
        }
        imageRendererMap.put(imageClass, renderer);
    }

    @Override
    public List<ComponentRenderer> getComponentRenderers() {
        return new ArrayList<>(componentRendererMap.values());
    }

    private static final class NRPH {

        private static final NvgRendererProvider I = new NvgRendererProvider();
    }
}
