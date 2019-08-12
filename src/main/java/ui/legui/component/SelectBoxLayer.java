package ui.legui.component;

import ui.legui.component.SelectBox.SelectBoxScrollablePanel;

/**
 * @author Aliaksandr_Shcherbin.
 */
public class SelectBoxLayer extends Layer<SelectBoxScrollablePanel> {

    /**
     * Default constructor of dialog layer.
     */
    public SelectBoxLayer() {
        setEventPassable(true);
        setEventReceivable(true);
    }
}
