package ui.legui.theme.colored.def;

import ui.legui.component.Button;
import ui.legui.style.color.ColorUtil;
import ui.legui.theme.colored.FlatColoredTheme.FlatColoredThemeSettings;

/**
 * Dark Button Theme for all buttons. Used to make button dark.
 *
 * @param <T> {@link Button} subclasses.
 */
public class FlatButtonTheme<T extends Button> extends FlatComponentTheme<T> {

    private final FlatColoredThemeSettings settings;

    public FlatButtonTheme(FlatColoredThemeSettings settings) {
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


        component.getHoveredStyle().getBackground()
            .setColor(settings.backgroundColor().mul(3).add(ColorUtil.oppositeBlackOrWhite(settings.backgroundColor())).div(4));
        component.getPressedStyle().getBackground()
            .setColor(settings.backgroundColor().mul(2).add(ColorUtil.oppositeBlackOrWhite(settings.backgroundColor())).div(3));
        component.getTextState().setTextColor(ColorUtil.oppositeBlackOrWhite(settings.backgroundColor()));
    }
}
