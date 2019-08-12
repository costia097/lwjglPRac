package ui.legui.theme.colored.def;

import ui.legui.component.Label;
import ui.legui.component.optional.align.HorizontalAlign;
import ui.legui.style.color.ColorConstants;
import ui.legui.style.color.ColorUtil;
import ui.legui.theme.colored.FlatColoredTheme.FlatColoredThemeSettings;

/**
 * Dark Label Theme for all labels. Used to make label dark.
 *
 * @param <T> {@link Label} subclasses.
 */
public class FlatLabelTheme<T extends Label> extends FlatComponentTheme<T> {

    private FlatColoredThemeSettings settings;

    public FlatLabelTheme(FlatColoredThemeSettings settings) {
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
        component.getStyle().setShadow(null);
        component.getStyle().getBackground().setColor(ColorConstants.transparent());
        component.getTextState().setTextColor(ColorUtil.oppositeBlackOrWhite(settings.backgroundColor()));
        component.getTextState().setHorizontalAlign(HorizontalAlign.LEFT);
    }
}
