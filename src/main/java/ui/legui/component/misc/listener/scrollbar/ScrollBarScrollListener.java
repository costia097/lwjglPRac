package ui.legui.component.misc.listener.scrollbar;

import ui.legui.component.ScrollBar;
import ui.legui.component.event.scrollbar.ScrollBarChangeValueEvent;
import ui.legui.event.ScrollEvent;
import ui.legui.listener.ScrollEventListener;

import static ui.legui.component.misc.listener.scrollbar.ScrollBarHelper.updateScrollBarValue;

/**
 * Default mouse scroll event listener for scrollbar. Generates {@link ScrollBarChangeValueEvent} event.
 */
public class ScrollBarScrollListener implements ScrollEventListener {

    public void process(ScrollEvent event) {
        ScrollBar scrollBar = (ScrollBar) event.getTargetComponent();
        if (Math.abs(event.getYoffset()) > 0)
            updateScrollBarValue(event.getYoffset(), event.getContext(), event.getFrame(), scrollBar);
        else if (Math.abs(event.getXoffset()) > 0)
            updateScrollBarValue(event.getXoffset(), event.getContext(), event.getFrame(), scrollBar);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj == this || obj.getClass() == this.getClass());
    }
}
