package ui.legui.component.event.textarea;

import ui.legui.component.Frame;
import ui.legui.component.TextAreaField;
import ui.legui.event.Event;
import ui.legui.system.context.Context;

/**
 * @author ShchAlexander.
 */
public class TextAreaFieldUpdateEvent extends Event<TextAreaField> {

    public TextAreaFieldUpdateEvent(TextAreaField component, Context context, Frame frame) {
        super(component, context, frame);
    }

}
