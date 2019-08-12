package ui.legui.theme.colored.def;

import org.joml.Vector2f;
import ui.legui.component.RadioButton;
import ui.legui.component.optional.align.HorizontalAlign;
import ui.legui.icon.CharIcon;
import ui.legui.style.color.ColorConstants;
import ui.legui.style.color.ColorUtil;
import ui.legui.theme.colored.FlatColoredTheme.FlatColoredThemeSettings;

import static ui.legui.style.font.FontRegistry.MATERIAL_ICONS_REGULAR;

/**
 * Dark RadioButton Theme for all radio buttons. Used to make radio button dark.
 *
 * @param <T> {@link RadioButton} subclasses.
 */
public class FlatRadioButtonTheme<T extends RadioButton> extends FlatComponentTheme<T> {

    private FlatColoredThemeSettings settings;

    public FlatRadioButtonTheme(FlatColoredThemeSettings settings) {
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
        component.setIconUnchecked(
            new CharIcon(new Vector2f(14), MATERIAL_ICONS_REGULAR, (char) 0xE836, ColorUtil.oppositeBlackOrWhite(settings.backgroundColor())));
        component
            .setIconChecked(new CharIcon(new Vector2f(14), MATERIAL_ICONS_REGULAR, (char) 0xE837, ColorUtil.oppositeBlackOrWhite(settings.backgroundColor())));
        component.getIconUnchecked().setHorizontalAlign(HorizontalAlign.LEFT);
        component.getIconChecked().setHorizontalAlign(HorizontalAlign.LEFT);
        component.getTextState().setHorizontalAlign(HorizontalAlign.LEFT);
    }
}
