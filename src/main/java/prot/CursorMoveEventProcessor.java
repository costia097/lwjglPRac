package prot;

import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_NORMAL;
import static org.lwjgl.glfw.GLFW.GLFW_HAND_CURSOR;
import static org.lwjgl.glfw.GLFW.glfwGetFramebufferSize;
import static org.lwjgl.glfw.GLFW.glfwSetCursor;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
import static prot.CommonUtils.normalizedPixelCoordinate;
import static prot.OverlapUtils.isOverlapPointWithBorderBox;
import static prot.Start.drawElementContexts;

class CursorMoveEventProcessor {
    private int[] bufferWith = new int[1];
    private int[] bufferHeight = new int[1];

    void processEvent(CursorMoveEvent event) {
        glfwGetFramebufferSize(event.getWindowPointer(), bufferWith, bufferHeight);

        double x = normalizedPixelCoordinate(event.getX(), bufferWith[0], true);
        double y = normalizedPixelCoordinate(event.getY(), bufferHeight[0], false);

        for (DrawElementContext drawElementContext : drawElementContexts) {
            if (!RenderElementType.UI_ELEMENT.equals(drawElementContext.getRenderElementType())) {
                continue;
            }
            UiElement uiElement = (UiElement) drawElementContext;
            if (UiElementType.UI_BUTTON.equals(uiElement.getUiElementType())) {
                UiButton uiButton = (UiButton) drawElementContext;
                BorderBox borderBox = uiButton.getBorderBox();
                if (isOverlapPointWithBorderBox(x, y, borderBox)) {
                    System.out.println("");
                }
            }
        }
    }
}
