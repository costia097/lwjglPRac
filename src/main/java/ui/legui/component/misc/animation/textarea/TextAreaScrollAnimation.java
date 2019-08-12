package ui.legui.component.misc.animation.textarea;

import ui.legui.component.Component;
import ui.legui.component.ScrollBar;
import ui.legui.component.TextArea;
import ui.legui.component.misc.animation.ViewportAnimation;

/**
 * @author Aliaksandr_Shcherbin.
 */
public class TextAreaScrollAnimation extends ViewportAnimation<TextArea> {

    public TextAreaScrollAnimation(TextArea textArea, double updateTime) {
        super(textArea, updateTime);
    }

    public TextAreaScrollAnimation(TextArea textArea) {
        this(textArea, 0.2d);
    }


    protected void updateViewport(TextArea scrollablePanel, double delta) {
        Component viewport = scrollablePanel.getViewport();
        Component textAreaField = scrollablePanel.getTextAreaField();
        ScrollBar verticalScrollBar = scrollablePanel.getVerticalScrollBar();
        ScrollBar horizontalScrollBar = scrollablePanel.getHorizontalScrollBar();

        super.updateViewport(viewport, textAreaField, verticalScrollBar, horizontalScrollBar, delta);
    }

}
