package prot;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwGetFramebufferSize;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
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
import static prot.Start.drawContexts;

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

        int[] bufferWith = new int[1];
        int[] bufferHeight = new int[1];

        glfwGetFramebufferSize(windowPointer, bufferWith, bufferHeight);

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

//        glfwSetInputMode(windowPointer, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        glfwSetCursorPosCallback(windowPointer, (window, xpos, ypos) -> {
        });

        glViewport(0, 0, bufferWith[0], bufferHeight[0]);
    }

    void loop(LoopFunction loopFunction) {
        while (!glfwWindowShouldClose(windowPointer)) {
            glfwPollEvents();

            loopFunction.doLoop();

            processInput();

            glfwSwapBuffers(windowPointer);
        }
    }

    private void processInput() {
        if (glfwGetKey(windowPointer, GLFW_KEY_ESCAPE) == GLFW_PRESS) {
            glfwSetWindowShouldClose(windowPointer, true);
        }

        if (glfwGetKey(windowPointer, GLFW_KEY_LEFT) == GLFW_PRESS) {
            DrawContext drawContext = drawContexts.get(0);
            drawContext.setPosition(new Fvector(drawContext.getPosition().getX() - 0.01f, drawContext.getPosition().getY(), drawContext.getPosition().getZ()));
        }

        if (glfwGetKey(windowPointer, GLFW_KEY_RIGHT) == GLFW_PRESS) {
            DrawContext drawContext = drawContexts.get(0);
            drawContext.setPosition(new Fvector(drawContext.getPosition().getX() + 0.01f, drawContext.getPosition().getY(), drawContext.getPosition().getZ()));
        }
    }

    public long getWindowPointer() {
        return windowPointer;
    }
}
