package ui.legui.component.misc.listener.slider;

import ui.legui.component.Slider;
import ui.legui.component.event.slider.SliderChangeValueEvent;
import ui.legui.event.ScrollEvent;
import ui.legui.listener.ScrollEventListener;
import ui.legui.listener.processor.EventProcessor;

/**
 * Slider mouse scroll event listener.
 */
public class SliderScrollEventListener implements ScrollEventListener {

    @Override
    public void process(ScrollEvent event) {
        Slider slider = (Slider) event.getTargetComponent();
        float oldValue = slider.getValue();
        float newValue = oldValue;
        // respect step size
        if (slider.getStepSize() > 0f) {
            newValue = newValue + slider.getStepSize() * (float) event.getYoffset();
        } else {
            newValue = newValue + (float) event.getYoffset();
        }
        // check for min/max values
        if (newValue > slider.getMaxValue()) {
            newValue = slider.getMaxValue();
        }
        if (newValue < slider.getMinValue()) {
            newValue = slider.getMinValue();
        }
        // set value & push event
        slider.setValue(newValue);
        EventProcessor.getInstance().pushEvent(
                new SliderChangeValueEvent(slider, event.getContext(), event.getFrame(), oldValue, newValue)
        );
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj == this || obj.getClass() == this.getClass());
    }
}
