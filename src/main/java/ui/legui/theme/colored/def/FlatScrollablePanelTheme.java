package ui.legui.theme.colored.def;

import org.joml.Vector4f;
import ui.legui.component.Component;
import ui.legui.component.ScrollablePanel;
import ui.legui.style.color.ColorConstants;
import ui.legui.style.color.ColorUtil;
import ui.legui.theme.Themes;
import ui.legui.theme.colored.FlatColoredTheme.FlatColoredThemeSettings;

/**
 * Dark ScrollablePanel Theme for all scrollable panels. Used to make scrollable panel dark.
 *
 * @param <T> {@link ScrollablePanel} subclasses.
 */
public class FlatScrollablePanelTheme<T extends ScrollablePanel> extends FlatComponentTheme<T> {

    private FlatColoredThemeSettings settings;

    public FlatScrollablePanelTheme(FlatColoredThemeSettings settings) {
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
        component.getContainer().getStyle().getBackground().setColor(new Vector4f(bgc));
        component.getViewport().getStyle().setBorder(null);
        component.getViewport().getStyle().setShadow(null);
    }

}
