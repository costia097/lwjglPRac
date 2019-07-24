package prot;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.glViewport;

class Window {

    private long windowPointer;

    Window(int width, int height, String caption) {
        glfwDefaultWindowHints();

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);

        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        windowPointer = glfwCreateWindow(width, height, caption, 0, 0);

        if (windowPointer == GLFW_FALSE) {
            glfwTerminate();
            throw new RuntimeException("Failed to create the GLFW window");
        }

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        if (vidMode == null) {
            glfwTerminate();
            throw new RuntimeException("VidMode is null");
        }

        glfwSetWindowPos(windowPointer, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);

        glfwMakeContextCurrent(windowPointer);

        glfwShowWindow(windowPointer);

        glfwSwapInterval(1);

        GL.createCapabilities();

        glfwSetWindowSizeCallback(windowPointer, new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                glViewport(0, 0, width, height);
            }
        });

        CursorMouseButtonEventProcessor cursorMouseButtonEventProcessor = new CursorMouseButtonEventProcessor();

        glfwSetMouseButtonCallback(windowPointer, (window, button, action, mods) -> {
            CursorMouseButtonEvent cursorMouseButtonEvent = new CursorMouseButtonEvent(window, button, action, mods);
            cursorMouseButtonEventProcessor.processEvent(cursorMouseButtonEvent);
        });

        CursorMoveEventProcessor cursorMoveEventProcessor = new CursorMoveEventProcessor();

        glfwSetCursorPosCallback(windowPointer, (window, xpos, ypos) -> {
//            CursorMoveEvent cursorMoveEvent = new CursorMoveEvent(window, xpos, ypos);
//            cursorMoveEventProcessor.processEvent(cursorMoveEvent);
        });
    }


    /**
     *  Main loop function
     * @param renderLoop for now its render loop function
     */
    void loop(RenderLoop renderLoop) {
        while (!glfwWindowShouldClose(windowPointer)) {
            glfwPollEvents();

            renderLoop.doLoop();

            processInput();

            glfwSwapBuffers(windowPointer);
        }
    }

    private void processInput() {
        if (glfwGetKey(windowPointer, GLFW_KEY_ESCAPE) == GLFW_PRESS) {
            glfwSetWindowShouldClose(windowPointer, true);
        }

//        if (glfwGetKey(windowPointer, GLFW_KEY_LEFT) == GLFW_PRESS) {
//            DrawElementContext drawElementContext = drawElementContexts.get(0);
//            drawElementContext.setPosition(new Fvector(drawElementContext.getPosition().getX() - 0.01f, drawElementContext.getPosition().getY(), drawElementContext.getPosition().getZ()));
//        }

//        if (glfwGetKey(windowPointer, GLFW_KEY_RIGHT) == GLFW_PRESS) {
//            DrawElementContext drawElementContext = drawElementContexts.get(0);
//            drawElementContext.setPosition(new Fvector(drawElementContext.getPosition().getX() + 0.01f, drawElementContext.getPosition().getY(), drawElementContext.getPosition().getZ()));
//        }
    }

    long getWindowPointer() {
        return windowPointer;
    }
}
