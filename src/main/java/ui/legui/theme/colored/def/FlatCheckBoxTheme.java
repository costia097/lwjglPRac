package ui.legui.theme.colored.def;

import org.joml.Vector2f;
import ui.legui.component.CheckBox;
import ui.legui.component.optional.align.HorizontalAlign;
import ui.legui.icon.CharIcon;
import ui.legui.style.color.ColorConstants;
import ui.legui.style.color.ColorUtil;
import ui.legui.theme.colored.FlatColoredTheme.FlatColoredThemeSettings;

import static ui.legui.style.font.FontRegistry.MATERIAL_ICONS_REGULAR;

/**
 * Dark CheckBox Theme for all check boxes. Used to make check box dark.
 *
 * @param <T> {@link CheckBox} subclasses.
 */
public class FlatCheckBoxTheme<T extends CheckBox> extends FlatComponentTheme<T> {

    private FlatColoredThemeSettings settings;

    public FlatCheckBoxTheme(FlatColoredThemeSettings settings) {
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
        component.setIconUnchecked(
            new CharIcon(new Vector2f(14), MATERIAL_ICONS_REGULAR, (char) 0xE835, ColorUtil.oppositeBlackOrWhite(settings.backgroundColor())));
        component
            .setIconChecked(new CharIcon(new Vector2f(14), MATERIAL_ICONS_REGULAR, (char) 0xE834, ColorUtil.oppositeBlackOrWhite(settings.backgroundColor())));
        component.getIconUnchecked().setHorizontalAlign(HorizontalAlign.LEFT);
        component.getIconChecked().setHorizontalAlign(HorizontalAlign.LEFT);
    }
}
