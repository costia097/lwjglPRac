package core;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwGetFramebufferSize;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
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
        idk ???
         */
//        glfwSetWindowSizeCallback(windowPointer, new GLFWWindowSizeCallback() {
//            @Override
//            public void invoke(long window, int width, int height) {
//                glViewport(0, 0, width, height);
//            }
//        });

        /*
        Idk why???
         */
        glViewport(0, 0, bufferWith[0], bufferHeight[0]);
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
