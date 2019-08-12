package ui.legui.theme.colored.def;

import ui.legui.component.Component;
import ui.legui.component.Tooltip;
import ui.legui.style.shadow.Shadow;
import ui.legui.theme.AbstractTheme;
import ui.legui.theme.Themes;
import ui.legui.theme.colored.FlatColoredTheme.FlatColoredThemeSettings;

import java.util.List;

/**
 * Dark Component Theme for all components. Used to make component dark.
 *
 * @param <T> {@link Component} subclasses.
 */
public class FlatComponentTheme<T extends Component> extends AbstractTheme<T> {

    private final FlatColoredThemeSettings settings;

    public FlatComponentTheme(FlatColoredThemeSettings settings) {
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
        component.getStyle().setBorder(null);
        component.getStyle().setBorderRadius(2f);
        component.getStyle().getBackground().setColor(settings.backgroundColor());
        component.getStyle().setFocusedStrokeColor(settings.strokeColor());

        if (settings.shadowColor()== null || settings.shadowColor().length() > 0.00001f) {
            component.getStyle().setShadow(new Shadow(1, 1, 16, -4, settings.shadowColor()));
        } else {
            component.getStyle().setShadow(null);
        }

        Tooltip tooltip = component.getTooltip();
        if (tooltip != null) {
            Themes.getDefaultTheme().applyAll(tooltip);
        }
        List<? extends Component> childComponents = component.getChildComponents();
        for (Component child : childComponents) {
            Themes.getDefaultTheme().applyAll(child);
        }
    }
}