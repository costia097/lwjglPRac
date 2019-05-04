package prac.starter;

import com.sun.jna.Native;
import prac.core.SceneRender;
import prac.core.Window;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

public class StartEngine {

    private static final boolean isLoadRenderDoc = false;

    public static void main(String[] args) {
        StartEngine startEngine = new StartEngine();
        startEngine.start();
    }

    public void start() {
          /*
        check version
         */
        System.out.println("LWJGL Version " + Version.getVersion() + " is working.");

        if (isLoadRenderDoc) {
            Native.load("renderdoc", RenderdocLibrary.class);
        }

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
        Window window = new Window(800, 600, "LearnOpenGL");

        /*
        Create SceneRender
         */
        SceneRender sceneRender = new SceneRender();

//        sceneRender.createTriangle();
//        sceneRender.createRectangle();
//        sceneRender.createColorTriangle();


//        sceneRender.createTexturedRectangle();

//        sceneRender.createCubeNotIptimazed();

        sceneRender.createCubeOptimized();

//        sceneRender.createRectangleWithTrianglePhysicsScene();

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
