package ui.legui.theme.colored.def;

import org.joml.Vector4f;
import ui.legui.component.Tooltip;
import ui.legui.style.color.ColorUtil;
import ui.legui.style.shadow.Shadow;
import ui.legui.theme.colored.FlatColoredTheme.FlatColoredThemeSettings;

/**
 * Dark Tooltip Theme for all tooltips. Used to make tooltip dark.
 *
 * @param <T> tooltip subclasses.
 */
public class FlatTooltipTheme<T extends Tooltip> extends FlatComponentTheme<T> {

    private FlatColoredThemeSettings settings;

    public FlatTooltipTheme(FlatColoredThemeSettings settings) {
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
        Vector4f bgc = ColorUtil.negativeColorRGB(settings.backgroundColor());
        component.getStyle().getBackground().setColor(bgc);
        component.getStyle().setShadow(new Shadow(1, 1, 16, -4, ColorUtil.oppositeBlackOrWhite(bgc).mul(0.8f)));

        component.getTextState().setTextColor(ColorUtil.oppositeBlackOrWhite(bgc));
    }
}
