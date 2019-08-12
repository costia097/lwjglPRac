package ui.legui.theme.colored.def;

import ui.legui.component.Button;
import ui.legui.component.Panel;
import ui.legui.theme.colored.FlatColoredTheme.FlatColoredThemeSettings;

/**
 * Dark Button Theme for all buttons. Used to make button dark.
 *
 * @param <T> {@link Button} subclasses.
 */
public class FlatPanelTheme<T extends Panel> extends FlatComponentTheme<T> {

    private FlatColoredThemeSettings settings;

    public FlatPanelTheme(FlatColoredThemeSettings settings) {
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
        component.getStyle().getBackground().setColor(settings.backgroundColor());
    }
}
