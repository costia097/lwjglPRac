package ui.legui.component.misc.listener.radiobutton;

import ui.legui.component.RadioButton;
import ui.legui.event.MouseClickEvent;
import ui.legui.listener.MouseClickEventListener;

import static ui.legui.event.MouseClickEvent.MouseClickAction.CLICK;

/**
 * RadioButton {@link MouseClickEvent} event listener. Used to update state of radio buttons in current radio button group.
 */
public class RadioButtonClickEventListener implements MouseClickEventListener {

    /**
     * Used to handle {@link MouseClickEvent}
     *
     * @param event event to handle.
     */
    @Override
    public void process(MouseClickEvent event) {
        if (event.getAction() == CLICK) {
            RadioButton component = (RadioButton) event.getTargetComponent();
            component.setChecked(true);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj == this || obj.getClass() == this.getClass());
    }
}
