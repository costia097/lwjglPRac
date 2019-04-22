package core;

import org.joml.Math;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;

import static core.SceneRender.deltaTime;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
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

public class Window {

    /*
    something like this  -> GLFWwindow *window
     */
    private long windowPointer;

    static Vector3f cameraPos = new Vector3f(0, 0, 3.0f);
    static Vector3f cameraFront = new Vector3f(0, 0, -1.0f);

    private final float toRadians = (float) (Math.PI / 180.0);

    private float lastX = 400;
    private float lastY = 300;
    private float yaw = -90.0f;
    private float pitch = 0;
    private boolean firstMouse = true;


    @FunctionalInterface
    public interface LoopFunction {
        void doLoop();
    }


    public Window(int width, int height, String caption) {
        /*
        IDK why?
         */
        glfwDefaultWindowHints();

        /*
        min max version
         */
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);

        /*
        is use deprecate code
        if core -> no backward compatibility
         */
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        /*
        allow forward compatibility
         */
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);


        /*
        some widow configuration
         */
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        /*
        create glfw window
         */
        windowPointer = glfwCreateWindow(width, height, caption, 0, 0);

        if (windowPointer == GLFW_FALSE) {
            glfwTerminate();
            throw new RuntimeException("Failed to create the GLFW window");
        }

        /*
        Get buffer size information
       info about height and with in first argument in special massive
         */
        int[] bufferWith = new int[1];
        int[] bufferHeight = new int[1];

        /*
        pass the parameters
         */
        glfwGetFramebufferSize(windowPointer, bufferWith, bufferHeight);


        /*
        Idk what is this ???
         */
        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        if (vidMode == null) {
            glfwTerminate();
            throw new RuntimeException("VidMode is null");
        }

        /*
        Set position of window -> magic with x and y
         */
        glfwSetWindowPos(windowPointer, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);


        /*
        make the current active context for window
         */
        glfwMakeContextCurrent(windowPointer);

        /*
        show the current window
         */
        glfwShowWindow(windowPointer);

        /*
        screen vsycn enable or disable
         */
        glfwSwapInterval(1);

        /*
        idk ???
         */
        GL.createCapabilities();

        /*
        when user resize window
        of course in case when we using window mode
         */
        glfwSetWindowSizeCallback(windowPointer, new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                glViewport(0, 0, width, height);
            }
        });

        glfwSetInputMode(windowPointer, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        glfwSetCursorPosCallback(windowPointer, (window, xpos, ypos) -> {

            if (firstMouse) {
                lastX = (float) xpos;
                lastY = (float) ypos;
                firstMouse = false;
            }

            float xOffset = (float) xpos - lastX;
            float yOffset = lastY - (float) ypos;

            lastX = (float) xpos;
            lastY = (float) ypos;

            float sensivity = 0.1f;

            xOffset *= sensivity;
            yOffset *= sensivity;

            yaw += xOffset;
            pitch += yOffset;

            if (pitch > 89.0f)
                pitch = 89.0f;
            if (pitch < -89.0f)
                pitch = -89.0f;

            Vector3f front = new Vector3f((float) (java.lang.Math.cos(yaw * toRadians) * java.lang.Math.cos(pitch * toRadians))
                    , (float) java.lang.Math.sin(pitch * toRadians)
                    , (float) (java.lang.Math.sin(yaw * toRadians) * java.lang.Math.cos(pitch* toRadians)));

            cameraFront = front.normalize();
        });

        /*
        Idk why???
         */
        glViewport(0, 0, bufferWith[0], bufferHeight[0]);
    }

    private void processInput() {
        if (glfwGetKey(windowPointer, GLFW_KEY_ESCAPE) == GLFW_PRESS) {
            glfwSetWindowShouldClose(windowPointer, true);
        }

        float cameraSpeed = 2.5f;

        if (glfwGetKey(windowPointer, GLFW_KEY_UP) == GLFW_PRESS) {
            cameraPos.add(new Vector3f(cameraFront).mul(cameraSpeed * deltaTime));
        }

        if (glfwGetKey(windowPointer, GLFW_KEY_DOWN) == GLFW_PRESS) {
            cameraPos.sub(new Vector3f(cameraFront).mul(cameraSpeed * deltaTime));
        }

        if (glfwGetKey(windowPointer, GLFW_KEY_LEFT) == GLFW_PRESS) {
            Vector3f up = new Vector3f(0, 1.0f, 0);

            Vector3f cross = up.cross(cameraFront);
            Vector3f normalize = cross.normalize();

            cameraPos.add(new Vector3f(normalize).mul(cameraSpeed * deltaTime));
        }

        if (glfwGetKey(windowPointer, GLFW_KEY_RIGHT) == GLFW_PRESS) {
            Vector3f up = new Vector3f(0, 1.0f, 0);

            Vector3f cross = up.cross(cameraFront);
            Vector3f normalize = cross.normalize();

            cameraPos.sub(new Vector3f(normalize).mul(cameraSpeed * deltaTime));
        }

    }

    public void loop(LoopFunction loopFunction) {
        /*
        While window is not closed do some logic
         */
        while (!glfwWindowShouldClose(windowPointer)) {
            /*
            render logic
             */
            loopFunction.doLoop();

            /*
            input logic
             */
            processInput();

            /*
            idk???
             */
            glfwSwapBuffers(windowPointer);

            /*
            ok any events like keyboard , mouse, resize
             */
            glfwPollEvents();
        }
    }
}
