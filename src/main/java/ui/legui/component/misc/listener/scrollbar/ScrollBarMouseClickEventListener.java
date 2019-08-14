package ui.legui.component.misc.listener.scrollbar;

import org.joml.Vector2f;
import ui.legui.component.Frame;
import ui.legui.component.ScrollBar;
import ui.legui.component.event.scrollbar.ScrollBarChangeValueEvent;
import ui.legui.component.optional.Orientation;
import ui.legui.event.Event;
import ui.legui.event.MouseClickEvent;
import ui.legui.input.Mouse;
import ui.legui.listener.MouseClickEventListener;
import ui.legui.listener.processor.EventProcessor;
import ui.legui.system.context.Context;

import static ui.legui.input.Mouse.MouseButton.MOUSE_BUTTON_LEFT;

/**
 * Default mouse click event listener for scrollbar. Generates {@link ScrollBarChangeValueEvent} event.
 */
public class ScrollBarMouseClickEventListener implements MouseClickEventListener {

    /**
     * Used to handle {@link MouseClickEvent}
     *
     * @param event event to handle.
     */
    @Override
    public void process(MouseClickEvent event) {
        ScrollBar scrollBar = (ScrollBar) event.getTargetComponent();
        boolean released = event.getAction() != MouseClickEvent.MouseClickAction.PRESS;
        if (!event.getButton().equals(MOUSE_BUTTON_LEFT)) {
            return;
        }

        Vector2f pos = scrollBar.getAbsolutePosition();
        Vector2f cursorPosition = Mouse.getCursorPosition();

        float visibleAmount = scrollBar.getVisibleAmount();
        boolean vertical = Orientation.VERTICAL.equals(scrollBar.getOrientation());

        Vector2f guiSize = scrollBar.getSize();
        float arrowSize = scrollBar.isArrowsEnabled() ? scrollBar.getArrowSize() : 0;
        float scrollBarSize = (vertical ? guiSize.y : guiSize.x) - 2 * arrowSize;
        float maxValue = scrollBar.getMaxValue();
        float minValue = scrollBar.getMinValue();
        float valueRange = maxValue - minValue;

        if (valueRange - visibleAmount < 0.001f) {
            return;
        }

        float barSize = scrollBarSize * visibleAmount / valueRange;
        if (barSize < ScrollBar.MIN_SCROLL_SIZE) {
            barSize = ScrollBar.MIN_SCROLL_SIZE;
        }

        float curValue = scrollBar.getCurValue();
        float scrollPosAccordingToScrollBounds = (scrollBarSize - barSize) * curValue / valueRange;

        float left;
        float curPos;
        float newVal;
        if (vertical) {
            left = pos.y + scrollPosAccordingToScrollBounds + arrowSize;
            curPos = cursorPosition.y;
        } else {
            left = pos.x + scrollPosAccordingToScrollBounds + arrowSize;
            curPos = cursorPosition.x;
        }
        if (curPos < left) {
            newVal = curValue - 0.5f * visibleAmount * valueRange / (valueRange - visibleAmount);
            if (!released) {
                updateViewport(event, scrollBar, maxValue, minValue, newVal);
            }
            scrollBar.setScrolling(false);
        } else if (curPos > left + barSize) {
            newVal = curValue + 0.5f * visibleAmount * valueRange / (valueRange - visibleAmount);
            if (!released) {
                updateViewport(event, scrollBar, maxValue, minValue, newVal);
            }
            scrollBar.setScrolling(false);
        } else {
            scrollBar.setScrolling(!released);
        }
    }

    /**
     * Used to update viewport.
     *
     * @param event     event.
     * @param scrollBar scrollbar.
     * @param maxValue  maximum scrollbar value.
     * @param minValue  minimum scrollbar value.
     * @param newValue  new scrollbar value.
     */
    private void updateViewport(Event event, ScrollBar scrollBar, float maxValue, float minValue, float newValue) {
        float valueToUse = newValue;
        if (newValue > maxValue) {
            valueToUse = maxValue;
        } else if (newValue < minValue) {
            valueToUse = minValue;
        }
        Context context = event.getContext();
        Frame frame = event.getFrame();
        float curValue = scrollBar.getCurValue();
        EventProcessor.getInstance().pushEvent(new ScrollBarChangeValueEvent<>(scrollBar, context, frame, curValue, valueToUse));
        scrollBar.setCurValue(valueToUse);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj == this || obj.getClass() == this.getClass());
    }
}