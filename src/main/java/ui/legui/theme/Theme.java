package ui.legui.theme;

import ui.legui.component.Component;
import ui.legui.component.Frame;
import ui.legui.component.Layer;

import java.util.List;

/**
 * Created by ShchAlexander on 2/6/2017.
 */
public abstract class Theme {

    private ThemeManager themeManager;

    protected Theme(ThemeManager themeManager) {
        this.themeManager = themeManager;
    }

    public ThemeManager getThemeManager() {
        return themeManager;
    }

    public <T extends Component> void apply(T component) {
        AbstractTheme<T> componentTheme = themeManager.getComponentTheme((Class<T>) component.getClass());
        componentTheme.apply(component);
    }

    public <T extends Component> void applyAll(T component) {
        AbstractTheme<T> componentTheme = themeManager.getComponentTheme((Class<T>) component.getClass());
        componentTheme.applyAll(component);
    }

    public void applyAll(Frame frame) {
        List<Layer> allLayers = frame.getAllLayers();
        for (Layer allLayer : allLayers) {
            applyAll(allLayer.getContainer());
        }
    }
}
