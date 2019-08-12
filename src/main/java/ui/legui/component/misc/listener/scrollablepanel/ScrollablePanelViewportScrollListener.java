package ui.legui.component.misc.listener.scrollablepanel;

import ui.legui.component.Component;
import ui.legui.component.ScrollablePanel;
import ui.legui.component.TextArea;
import ui.legui.event.ScrollEvent;
import ui.legui.input.Mouse;
import ui.legui.listener.EventListener;
import ui.legui.system.handler.SehUtil;

import java.util.ArrayList;

import static ui.legui.component.misc.listener.scrollbar.ScrollBarHelper.updateScrollBarValue;

/**
 * Created by ShchAlexander on 23.07.2017.
 */
public class ScrollablePanelViewportScrollListener implements EventListener<ScrollEvent> {

    /**
     * Used to handle specific event.
     *
     * @param event event to handle.
     */
    @Override
    public void process(ScrollEvent event) {
        ArrayList<Component> targetList = new ArrayList<>();
        SehUtil.recursiveTargetComponentListSearch(Mouse.getCursorPosition(), event.getTargetComponent(), targetList);
        for (Component component : targetList) {
            if ((component instanceof TextArea) || (component instanceof ScrollablePanel)) {
                return;
            }
        }

        ScrollablePanel scrollablePanel = (ScrollablePanel) event.getTargetComponent().getParent();

        if (Math.abs(event.getYoffset()) > 0)
            updateScrollBarValue(event.getYoffset(), event.getContext(), event.getFrame(), scrollablePanel.getVerticalScrollBar());
        if (Math.abs(event.getXoffset()) > 0)
            updateScrollBarValue(event.getXoffset(), event.getContext(), event.getFrame(), scrollablePanel.getHorizontalScrollBar());
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj == this || obj.getClass() == this.getClass());
    }
}
