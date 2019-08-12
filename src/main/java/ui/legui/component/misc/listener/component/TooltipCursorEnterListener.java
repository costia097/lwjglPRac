package ui.legui.component.misc.listener.component;

import ui.legui.component.Component;
import ui.legui.component.Tooltip;
import ui.legui.event.CursorEnterEvent;
import ui.legui.listener.CursorEnterEventListener;

/**
 * Default event listener for {@link CursorEnterEvent} to add tooltip to tooltip layer and make it visible or not visible.
 */
public class TooltipCursorEnterListener implements CursorEnterEventListener {

    /**
     * Used to process {@link CursorEnterEvent}.
     *
     * @param event event to process.
     */
    @Override
    public void process(CursorEnterEvent event) {
        Component component = event.getTargetComponent();
        Tooltip tooltip = component.getTooltip();
        if (tooltip != null) {
            if (event.isEntered()) {
                event.getFrame().getTooltipLayer().add(tooltip);
            } else {
                event.getFrame().getTooltipLayer().remove(tooltip);
            }
        }
    }

    /**
     * Used to compare instances of this event listener.
     *
     * @param obj object to compare.
     *
     * @return true if equals.
     */
    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof TooltipCursorEnterListener;
    }
}
