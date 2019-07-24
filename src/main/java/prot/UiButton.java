package prot;

import java.util.Random;
import java.util.function.Consumer;

public class UiButton extends UiElement {
    private int id = new Random().nextInt();

    private String label;

    UiButton(int vao,
             int countOfVertex,
             int mode,
             boolean isIndexed,
             BorderBox borderBox,
             Consumer<Object> clickListener
    ) {
        super(vao, countOfVertex, mode, isIndexed, borderBox, UiElementType.UI_BUTTON, clickListener);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getId() {
        return id;
    }
}
