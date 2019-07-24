package prot;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetFramebufferSize;
import static prot.CommonUtils.normalizedPixelCoordinate;

class CursorMouseButtonEventProcessor {

    private double[] xPos = new double[1];
    private double[] yPos = new double[1];

    private int[] bufferWith = new int[1];
    private int[] bufferHeight = new int[1];

    void processEvent(CursorMouseButtonEvent event) {
        glfwGetCursorPos(event.getWindowPointer(), xPos, yPos);
        glfwGetFramebufferSize(event.getWindowPointer(), bufferWith, bufferHeight);

//        if (event.getButton() == GLFW_MOUSE_BUTTON_LEFT && event.getAction() == GLFW_PRESS) {
//
//        }

        if (event.getButton() == GLFW_MOUSE_BUTTON_LEFT && event.getAction() == GLFW_RELEASE) {
            float x = (float) normalizedPixelCoordinate(xPos[0], bufferWith[0], true);
            float y = (float) normalizedPixelCoordinate(yPos[0], bufferHeight[0], false);

            UiElement uiElementByPosition = PositionToElementMapper.getUIElementByPosition(new Fpoint(x, y, 0));
            if (uiElementByPosition != null) {
                uiElementByPosition.getColor().setX((float) (uiElementByPosition.getColor().getX() - 0.1));
                uiElementByPosition.getClickListener().accept("");
            }
        }

    }
}
