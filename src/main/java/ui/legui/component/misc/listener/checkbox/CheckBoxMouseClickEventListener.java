package ui.legui.component.misc.listener.checkbox;

import ui.legui.component.CheckBox;
import ui.legui.component.event.checkbox.CheckBoxChangeValueEvent;
import ui.legui.event.MouseClickEvent;
import ui.legui.listener.MouseClickEventListener;
import ui.legui.listener.processor.EventProcessor;

import static ui.legui.event.MouseClickEvent.MouseClickAction.CLICK;

/**
 * MouseClickEventListener for checkbox, used to toggle checkbox state on mouse click.
 */
public class CheckBoxMouseClickEventListener implements MouseClickEventListener {

    /**
     * Used to handle event.
     *
     * @param event event to handle.
     */
    @Override
    public void process(MouseClickEvent event) {
        CheckBox checkBox = (CheckBox) event.getTargetComponent();
        if (event.getAction() == CLICK) {
            boolean checked = checkBox.isChecked();
            checkBox.setChecked(!checked);
            EventProcessor.getInstance().pushEvent(new CheckBoxChangeValueEvent(checkBox, event.getContext(), event.getFrame(), checked, !checked));
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
        return obj == this || obj instanceof CheckBoxMouseClickEventListener;
    }
}
