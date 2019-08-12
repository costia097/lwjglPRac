package ui.legui.theme.colored.def;

import org.joml.Vector4f;
import ui.legui.component.Component;
import ui.legui.component.ScrollablePanel;
import ui.legui.component.TextArea;
import ui.legui.style.color.ColorConstants;
import ui.legui.style.color.ColorUtil;
import ui.legui.theme.Themes;
import ui.legui.theme.colored.FlatColoredTheme.FlatColoredThemeSettings;

/**
 * Dark ScrollablePanel Theme for all scrollable panels. Used to make scrollable panel dark.
 *
 * @param <T> {@link ScrollablePanel} subclasses.
 */
public class FlatTextAreaTheme<T extends TextArea> extends FlatComponentTheme<T> {

    private FlatColoredThemeSettings settings;

    public FlatTextAreaTheme(FlatColoredThemeSettings settings) {
        super(settings);
        this.settings = settings;
    }

    /**
     * Used to apply theme only for component and not apply for child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void apply(T component) {
        super.apply(component);

        Vector4f bgc = ColorUtil.oppositeBlackOrWhite(settings.backgroundColor().mul(3)).add(settings.backgroundColor().mul(3)).div(4);
        component.getStyle().getBackground().setColor(bgc);


        Component viewport = component.getViewport();
        Themes.getDefaultTheme().apply(viewport);
        Themes.getDefaultTheme().applyAll(component.getVerticalScrollBar());
        Themes.getDefaultTheme().applyAll(component.getHorizontalScrollBar());
        viewport.getStyle().getBackground().setColor(ColorConstants.transparent());
        component.getViewport().getStyle().setShadow(null);
    }

}
