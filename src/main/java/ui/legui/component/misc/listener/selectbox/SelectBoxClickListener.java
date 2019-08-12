package ui.legui.component.misc.listener.selectbox;

import org.joml.Vector2f;
import ui.legui.component.Frame;
import ui.legui.component.SelectBox;
import ui.legui.component.SelectBoxLayer;
import ui.legui.event.MouseClickEvent;
import ui.legui.listener.MouseClickEventListener;

import static ui.legui.event.MouseClickEvent.MouseClickAction.CLICK;

/**
 * Default mouse click listener for selectbox.
 * <p>
 * Used to expand/collapse selectbox if clicked on it.
 */
public class SelectBoxClickListener<T> implements MouseClickEventListener {

    private SelectBox<T> selectBox;

    public SelectBoxClickListener(SelectBox<T> selectBox) {
        this.selectBox = selectBox;
    }

    @Override
    public void process(MouseClickEvent event) {
        SelectBox<T> box = selectBox;
        if (event.getAction() == CLICK) {
            Frame frame = event.getFrame();
            SelectBoxLayer selectBoxLayer = box.getSelectBoxLayer();
            boolean collapsed = box.isCollapsed();
            box.setCollapsed(!collapsed);
            if (collapsed) {
                Vector2f layerSize = new Vector2f(frame.getContainer().getSize());
                selectBoxLayer.getContainer().setSize(layerSize);

                frame.addLayer(selectBoxLayer);
            } else {
                frame.removeLayer(selectBoxLayer);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj == this || obj.getClass() == this.getClass());
    }
}
