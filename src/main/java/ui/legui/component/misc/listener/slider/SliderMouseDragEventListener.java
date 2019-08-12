package ui.legui.component.misc.listener.slider;

import ui.legui.component.Slider;
import ui.legui.component.event.slider.SliderChangeValueEvent;
import ui.legui.event.MouseDragEvent;
import ui.legui.input.Mouse;
import ui.legui.listener.MouseDragEventListener;
import ui.legui.listener.processor.EventProcessor;

/**
 * Slider mouse drag event listener. Used to change slider value. Generates slider value change event.
 */
public class SliderMouseDragEventListener implements MouseDragEventListener {

    @Override
    public void process(MouseDragEvent event) {
        Slider slider = (Slider) event.getTargetComponent();
        if (!Mouse.MouseButton.MOUSE_BUTTON_LEFT.isPressed()) {
            return;
        }
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
