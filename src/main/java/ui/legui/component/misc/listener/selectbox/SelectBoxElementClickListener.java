package ui.legui.component.misc.listener.selectbox;

import ui.legui.component.SelectBox;
import ui.legui.component.event.selectbox.SelectBoxChangeSelectionEvent;
import ui.legui.event.MouseClickEvent;
import ui.legui.input.Mouse;
import ui.legui.listener.MouseClickEventListener;
import ui.legui.listener.processor.EventProcessor;

import static ui.legui.event.MouseClickEvent.MouseClickAction.CLICK;

/**
 * @author ShchAlexander.
 */
public class SelectBoxElementClickListener<T> implements MouseClickEventListener {

    private SelectBox<T> selectBox;

    public SelectBoxElementClickListener(SelectBox<T> selectBox) {
        this.selectBox = selectBox;
    }

    @Override
    public void process(MouseClickEvent event) {
        SelectBox<T>.SelectBoxElement<T> component = (SelectBox<T>.SelectBoxElement<T>) event.getTargetComponent();
        if (event.getAction() == CLICK && event.getButton().equals(Mouse.MouseButton.MOUSE_BUTTON_1)) {
            T selection = selectBox.getSelection();
            T newValue = component.getObject();
            selectBox.setSelected(newValue, true);
            EventProcessor.getInstance().pushEvent(new SelectBoxChangeSelectionEvent<>(selectBox, event.getContext(), event.getFrame(), selection, newValue));
            selectBox.setCollapsed(true);
            event.getFrame().removeLayer(selectBox.getSelectBoxLayer());
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj == this || obj.getClass() == this.getClass());
    }
}
