package starter;

import core.SceneRender;
import core.Window;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

public class Main {

    public static void main(String[] args) {

        /*
        check version
         */
        System.out.println("LWJGL Version " + Version.getVersion() + " is working.");

        /*
        print error if any exist
         */
        GLFWErrorCallback.createPrint(System.err).set();

        /*
        try to init glfw
         */
        if (!glfwInit()) {
            glfwTerminate();
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        /*
        Create window object
         */
        Window window = new Window(1280, 720, "window");

        /*
        Create SceneRender
         */
        SceneRender sceneRender = new SceneRender();

        sceneRender.createTriangle();
        sceneRender.compileShaders();

        /*
        main loop
         */
        window.loop(sceneRender::render);

        /*
        exit
         */
        glfwTerminate();
    }
}
