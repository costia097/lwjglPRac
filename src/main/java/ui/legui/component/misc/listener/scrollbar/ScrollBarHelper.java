package ui.legui.component.misc.listener.scrollbar;

import ui.legui.component.Frame;
import ui.legui.component.ScrollBar;
import ui.legui.component.event.scrollbar.ScrollBarChangeValueEvent;
import ui.legui.listener.processor.EventProcessor;
import ui.legui.system.context.Context;

public final class ScrollBarHelper {
    private ScrollBarHelper() {
    }

    public static void updateScrollBarValue(double offset, Context context, Frame frame, ScrollBar scrollBar) {
        float maxValue = scrollBar.getMaxValue();
        float minValue = scrollBar.getMinValue();
        float curValue = scrollBar.getCurValue();
        float visibleAmount = scrollBar.getVisibleAmount();
        float valueRange = scrollBar.getMaxValue() - scrollBar.getMinValue();
        if (valueRange - visibleAmount < 0.001f) {
            return;
        }
        float newVal = (float) (curValue - scrollBar.getScrollStep() * offset * visibleAmount * valueRange / (valueRange - visibleAmount));

        if (newVal > maxValue) {
            newVal = maxValue;
        }
        if (newVal < minValue) {
            newVal = minValue;
        }

        EventProcessor.getInstance().pushEvent(new ScrollBarChangeValueEvent<>(scrollBar, context, frame, curValue, newVal));
        scrollBar.setCurValue(newVal);
    }

}
