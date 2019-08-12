package ui.legui.theme.colored;

import org.joml.Vector4f;
import ui.legui.component.Button;
import ui.legui.component.CheckBox;
import ui.legui.component.Component;
import ui.legui.component.Label;
import ui.legui.component.LayerContainer;
import ui.legui.component.Panel;
import ui.legui.component.ProgressBar;
import ui.legui.component.RadioButton;
import ui.legui.component.ScrollBar;
import ui.legui.component.ScrollablePanel;
import ui.legui.component.SelectBox;
import ui.legui.component.Slider;
import ui.legui.component.TextArea;
import ui.legui.component.TextAreaField;
import ui.legui.component.TextInput;
import ui.legui.component.ToggleButton;
import ui.legui.component.Tooltip;
import ui.legui.component.Widget;
import ui.legui.theme.DefaultThemeManager;
import ui.legui.theme.Theme;
import ui.legui.theme.ThemeManager;
import ui.legui.theme.colored.def.FlatButtonTheme;
import ui.legui.theme.colored.def.FlatCheckBoxTheme;
import ui.legui.theme.colored.def.FlatComponentTheme;
import ui.legui.theme.colored.def.FlatLabelTheme;
import ui.legui.theme.colored.def.FlatLayerContainerTheme;
import ui.legui.theme.colored.def.FlatPanelTheme;
import ui.legui.theme.colored.def.FlatProgressBarTheme;
import ui.legui.theme.colored.def.FlatRadioButtonTheme;
import ui.legui.theme.colored.def.FlatScrollBarTheme;
import ui.legui.theme.colored.def.FlatScrollablePanelTheme;
import ui.legui.theme.colored.def.FlatSelectBoxElementTheme;
import ui.legui.theme.colored.def.FlatSelectBoxScrollablePanelTheme;
import ui.legui.theme.colored.def.FlatSelectBoxTheme;
import ui.legui.theme.colored.def.FlatSliderTheme;
import ui.legui.theme.colored.def.FlatTextAreaFieldTheme;
import ui.legui.theme.colored.def.FlatTextAreaTheme;
import ui.legui.theme.colored.def.FlatTextInputTheme;
import ui.legui.theme.colored.def.FlatToggleButtonTheme;
import ui.legui.theme.colored.def.FlatTooltipTheme;
import ui.legui.theme.colored.def.FlatWidgetTheme;

/**
 * Dark Theme. Used to change theme of components to dark.
 */
public class FlatColoredTheme extends Theme {

    /**
     * Used to create theme instance.
     *
     * @param backgroundColor background color.
     * @param borderColor border color.
     * @param strokeColor stroke color.
     * @param allowColor allow color.
     * @param denyColor deny color.
     * @param shadowColor shadow color.
     */
    public FlatColoredTheme(
            Vector4f backgroundColor, Vector4f borderColor, Vector4f strokeColor,
            Vector4f allowColor, Vector4f denyColor, Vector4f shadowColor
    ) {
        super(createThemeManager(new FlatColoredThemeSettings(backgroundColor, borderColor, strokeColor, allowColor, denyColor, shadowColor)));
    }

    /**
     * Used to initialize theme manager.
     *
     * @param settings settings to use by components theme.
     * @return initialized theme manager.
     */
    private static ThemeManager createThemeManager(FlatColoredThemeSettings settings) {
        ThemeManager m = new DefaultThemeManager();
        //@formatter:off
        m.setComponentTheme(Button.class,                   new FlatButtonTheme<>                   (settings));
        m.setComponentTheme(Panel.class,                    new FlatPanelTheme<>                    (settings));
        m.setComponentTheme(CheckBox.class,                 new FlatCheckBoxTheme<>(settings));
        m.setComponentTheme(Component.class,                new FlatComponentTheme<>                (settings));
        m.setComponentTheme(Label.class,                    new FlatLabelTheme<>(settings));
        m.setComponentTheme(LayerContainer.class,           new FlatLayerContainerTheme<>(settings));
        m.setComponentTheme(ProgressBar.class,              new FlatProgressBarTheme<>              (settings));
        m.setComponentTheme(ScrollablePanel.class,          new FlatScrollablePanelTheme<>(settings));
        m.setComponentTheme(RadioButton.class,              new FlatRadioButtonTheme<>(settings));
        m.setComponentTheme(ScrollBar.class,                new FlatScrollBarTheme<>(settings));
        m.setComponentTheme(SelectBox.class,                new FlatSelectBoxTheme<>                (settings));
        m.setComponentTheme(SelectBox.SelectBoxScrollablePanel.class, new FlatSelectBoxScrollablePanelTheme<>(settings));
        m.setComponentTheme(SelectBox.SelectBoxElement.class,         new FlatSelectBoxElementTheme<>         (settings));
        m.setComponentTheme(Slider.class,                   new FlatSliderTheme<>(settings));
        m.setComponentTheme(TextArea.class,                 new FlatTextAreaTheme<>(settings));
        m.setComponentTheme(TextAreaField.class,            new FlatTextAreaFieldTheme<>(settings));
        m.setComponentTheme(TextInput.class,                new FlatTextInputTheme<>(settings));
        m.setComponentTheme(ToggleButton.class,             new FlatToggleButtonTheme<>(settings));
        m.setComponentTheme(Tooltip.class,                  new FlatTooltipTheme<>                  (settings));
        m.setComponentTheme(Widget.class,                   new FlatWidgetTheme<>(settings));
        //@formatter:on
        return m;
    }

    /**
     * Flat colored theme settings.
     */
    public static class FlatColoredThemeSettings {

        /**
         * Background color.
         */
        private final Vector4f backgroundColor;
        /**
         * Border color.
         */
        private final Vector4f borderColor;
        /**
         * Stroke color.
         */
        private final Vector4f allowColor;
        /**
         * Allow color.
         */
        private final Vector4f strokeColor;
        /**
         * Deny color.
         */
        private final Vector4f denyColor;
        /**
         * Shadow color.
         */
        private final Vector4f shadowColor;

        /**
         * Used to create theme settings instance.
         *
         * @param backgroundColor background color.
         * @param borderColor border color.
         * @param strokeColor stroke color.
         * @param allowColor allow color.
         * @param denyColor deny color.
         * @param shadowColor shadow color.
         */
        public FlatColoredThemeSettings(
                Vector4f backgroundColor, Vector4f borderColor, Vector4f strokeColor,
                Vector4f allowColor, Vector4f denyColor, Vector4f shadowColor
        ) {
            this.backgroundColor = backgroundColor;
            this.borderColor = borderColor;
            this.allowColor = allowColor;
            this.strokeColor = strokeColor;
            this.denyColor = denyColor;
            this.shadowColor = shadowColor;
        }

        /**
         * Returns background color.
         *
         * @return background color.
         */
        public Vector4f backgroundColor() {
            return backgroundColor == null ? null : new Vector4f(backgroundColor);
        }

        /**
         * Returns border color.
         *
         * @return border color.
         */
        public Vector4f borderColor() {
            return borderColor == null ? null : new Vector4f(borderColor);
        }

        /**
         * Returns stroke color.
         *
         * @return stroke color.
         */
        public Vector4f strokeColor() {
            return strokeColor == null ? null : new Vector4f(strokeColor);
        }

        /**
         * Returns allow color.
         *
         * @return allow color.
         */
        public Vector4f allowColor() {
            return allowColor == null ? null : new Vector4f(allowColor);
        }

        /**
         * Returns deny color.
         *
         * @return deny color.
         */
        public Vector4f denyColor() {
            return denyColor == null ? null : new Vector4f(denyColor);
        }

        /**
         * Returns shadow color.
         *
         * @return shadow color.
         */
        public Vector4f shadowColor() {
            return shadowColor == null ? null : new Vector4f(shadowColor);
        }
    }

}
