package ui.legui.component.misc.listener.textarea;

import org.joml.Vector4f;
import ui.legui.component.Component;
import ui.legui.component.ScrollablePanel;
import ui.legui.component.TextArea;
import ui.legui.component.TextAreaField;
import ui.legui.event.ScrollEvent;
import ui.legui.input.Mouse;
import ui.legui.listener.EventListener;
import ui.legui.system.handler.SehUtil;

import java.util.ArrayList;

import static ui.legui.component.misc.listener.scrollbar.ScrollBarHelper.updateScrollBarValue;
import static ui.legui.style.util.StyleUtilities.getPadding;

/**
 * Created by ShchAlexander on 23.07.2017.
 */
public class TextAreaViewportScrollListener implements EventListener<ScrollEvent> {


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

        TextArea textArea = (TextArea) event.getTargetComponent().getParent();

        TextAreaField textAreaField = textArea.getTextAreaField();
        Vector4f padding = getPadding(textAreaField, textAreaField.getStyle());
        float maxTextWidth = Math.max(
            textAreaField.getMaxTextWidth() + padding.x + padding.z,
            textArea.getViewportSize().x
        );
        float maxTextHeight = Math.max(
            textAreaField.getMaxTextHeight() + padding.y + padding.w,
            textArea.getViewportSize().y
        );
        textAreaField.setSize(maxTextWidth, maxTextHeight);

        if (Math.abs(event.getYoffset()) > 0)
            updateScrollBarValue(event.getYoffset(), event.getContext(), event.getFrame(), textArea.getVerticalScrollBar());
        if (Math.abs(event.getXoffset()) > 0)
            updateScrollBarValue(event.getXoffset(), event.getContext(), event.getFrame(), textArea.getHorizontalScrollBar());
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj == this || obj.getClass() == this.getClass());
    }
}
