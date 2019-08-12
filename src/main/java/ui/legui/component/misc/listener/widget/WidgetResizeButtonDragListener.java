package ui.legui.component.misc.listener.widget;

import org.joml.Vector2f;
import ui.legui.component.Button;
import ui.legui.component.Widget;
import ui.legui.event.MouseDragEvent;
import ui.legui.input.Mouse;
import ui.legui.listener.MouseDragEventListener;
import ui.legui.style.length.Length;
import ui.legui.style.util.StyleUtilities;

import static ui.legui.style.length.LengthType.pixel;

/**
 * Created by ShchAlexander on 11.03.2018.
 */
public class WidgetResizeButtonDragListener implements MouseDragEventListener {

    private Button resizeButton;
    private Widget widget;

    public WidgetResizeButtonDragListener(Button resizeButton, Widget widget) {
        this.resizeButton = resizeButton;
        this.widget = widget;
    }

    /**
     * Used to handle {@link MouseDragEvent}.
     *
     * @param event event to handle.
     */
    @Override
    public void process(MouseDragEvent event) {
        Vector2f delta = event.getDelta();

        Vector2f cursorPositionPrev = Mouse.getCursorPositionPrev();
        Vector2f cursorPosition = Mouse.getCursorPosition();

        float xx = widget.getSize().x + delta.x;
        float yy = widget.getSize().y + delta.y;

        Vector2f deltaSize = new Vector2f();

        Length minWidthU = widget.getStyle().getMinWidth();
        if (minWidthU == null) {
            minWidthU = pixel(1f);
        }
        Length minHeightU = widget.getStyle().getMinHeight();
        if (minHeightU == null) {
            minHeightU = pixel(widget.getTitleContainer().getSize().y);
        }

        Length maxWidthU = widget.getStyle().getMaxWidth();
        Length maxHeightU = widget.getStyle().getMaxHeight();

        Float minWidth = StyleUtilities.getFloatLength(minWidthU, widget.getParent().getSize().x);
        Float minHeight = StyleUtilities.getFloatLength(minHeightU, widget.getParent().getSize().y);
        Float maxWidth = StyleUtilities.getFloatLength(maxWidthU, widget.getParent().getSize().x);
        Float maxHeight = StyleUtilities.getFloatLength(maxHeightU, widget.getParent().getSize().y);

        if (
            (
                delta.x < 0 && (cursorPositionPrev.x <= resizeButton.getAbsolutePosition().x + resizeButton.getSize().x
                    || cursorPosition.x <= resizeButton.getAbsolutePosition().x + resizeButton.getSize().x))
                || (
                (delta.x > 0 && (cursorPositionPrev.x >= resizeButton.getAbsolutePosition().x
                    || cursorPosition.x >= resizeButton.getAbsolutePosition().x)))
        ) {
            if (xx >= minWidth && (maxWidth == null || xx <= maxWidth)) {
                deltaSize.x = delta.x;
            }
        }
        if (
            (
                delta.y < 0 && (cursorPositionPrev.y <= resizeButton.getAbsolutePosition().y + resizeButton.getSize().y
                    || cursorPosition.y <= resizeButton.getAbsolutePosition().y + resizeButton.getSize().y))
                || (
                (delta.y > 0 && (cursorPositionPrev.y >= resizeButton.getAbsolutePosition().y
                    || cursorPosition.y >= resizeButton.getAbsolutePosition().y)))
        ) {
            if (yy >= minHeight && (maxHeight == null || yy <= maxHeight)) {
                deltaSize.y = delta.y;
            }
        }

        widget.getSize().add(deltaSize);

    }
}
