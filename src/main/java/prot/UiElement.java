package prot;

import java.util.function.Consumer;

class UiElement extends DrawElementContext {
    private BorderBox borderBox;
    private UiElementType uiElementType;
    private Consumer<Object> clickListener;

    UiElement(int vao,
              int countOfVertex,
              int mode,
              boolean isIndexed,
              BorderBox borderBox,
              UiElementType uiElementType,
              Consumer<Object> clickListener
    ) {
        super(vao, countOfVertex, mode, isIndexed, RenderElementType.UI_ELEMENT);
        this.borderBox = borderBox;
        this.uiElementType = uiElementType;
        this.clickListener = clickListener;
    }

    BorderBox getBorderBox() {
        return borderBox;
    }

    UiElementType getUiElementType() {
        return uiElementType;
    }

    Consumer<Object> getClickListener() {
        return clickListener;
    }
}
