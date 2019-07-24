package prot;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class PositionToElementMapper {

    private static Map<BorderBox, UiElement> uiElementPer = new ConcurrentHashMap<>();

    static void addElement(UiElement uiElement) {
        uiElementPer.put(uiElement.getBorderBox(), uiElement);
    }

    static UiElement getUIElementByPosition(Fpoint fpoint) {
        for (Map.Entry<BorderBox, UiElement> borderBoxUiElementEntry : uiElementPer.entrySet()) {
            BorderBox key = borderBoxUiElementEntry.getKey();
            if (OverlapUtils.isOverlapPointWithBorderBox(fpoint, key)) {
                return borderBoxUiElementEntry.getValue();
            }
        }
        return null;
    }
}
