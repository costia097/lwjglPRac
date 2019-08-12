package ui.legui.component.misc.listener.slider;

import ui.legui.component.Slider;
import ui.legui.component.event.slider.SliderChangeValueEvent;
import ui.legui.event.MouseClickEvent;
import ui.legui.input.Mouse;
import ui.legui.listener.MouseClickEventListener;
import ui.legui.listener.processor.EventProcessor;

/**
 * Slider mouse click event listener. Used to change slider value. Generates slider value change event.
 */
public class SliderMouseClickEventListener implements MouseClickEventListener {

    @Override
    public void process(MouseClickEvent event) {
        if (!event.getButton().equals(Mouse.MouseButton.MOUSE_BUTTON_LEFT) || event.getAction() != MouseClickEvent.MouseClickAction.PRESS) {
            return;
        }
        Slider slider = (Slider) event.getTargetComponent();
        // calculate new value
        float value = SliderHelper.determineSliderValue(slider, Mouse.getCursorPosition());
        // set value & push event
        float oldValue = slider.getValue();
        slider.setValue(value);
        EventProcessor.getInstance().pushEvent(
                new SliderChangeValueEvent(slider, event.getContext(), event.getFrame(), oldValue, slider.getValue())
        );
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj == this || obj.getClass() == this.getClass());
    }
}
