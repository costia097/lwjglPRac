package prot;

import com.sun.jna.Native;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import prac.starter.RenderdocLibrary;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.glfwGetFramebufferSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.glGetIntegerv;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL20.GL_MAX_VERTEX_ATTRIBS;

public class Start {

    static List<DrawElementContext> drawElementContexts = new ArrayList<>();
    private static final boolean isLoadRenderDoc = false;

    public static void main(String[] args) {
        Start start = new Start();
        start.start();
    }

    private void start() {
        System.out.println("LWJGL Version " + Version.getVersion() + " is working.");

        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            glfwTerminate();
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        if (isLoadRenderDoc) {
            Native.load("/home/kostia/librenderdoc.so", RenderdocLibrary.class);
        }

        Window window = new Window(640, 480, "Prot");

        int[] params = new int[1];
        glGetIntegerv(GL_MAX_VERTEX_ATTRIBS, params);

        System.out.println("Max vertex attribs is: " + params[0]);

        Shader shader = new Shader("src/main/resources/shaders/prot/fragment/shader.frag", "src/main/resources/shaders/prot/vertex/shader.vert");
        shader.compile();

        Shader shaderTest = new Shader("src/main/resources/shaders/prot/fragment/test.frag", "src/main/resources/shaders/prot/vertex/test.vert");
        shaderTest.compile();

        SceneRender sceneRender = new SceneRender();

        DrawElementContext drawElementContext = DrawUtils.drawRectangleIndexed(
                new Fpoint(-0.2f, -0.2f, 0),
                new Fpoint(0.2f, -0.2f, 0),
                new Fpoint(0.2f, 0.2f, 0),
                true
        );
        drawElementContext.setPosition(new Fvector(0, 0, 0));
        drawElementContext.setShaderProgram(shader.getShaderProgram());

        UiButton uiButton = DrawUiUtils.drawIUButton();

        uiButton.setShaderProgram(shader.getShaderProgram());
        uiButton.setPosition(new Fvector(0, 0, 0));

        drawElementContexts.add(uiButton);

        int[] bufferWith = new int[1];
        int[] bufferHeight = new int[1];

        glfwGetFramebufferSize(window.getWindowPointer(), bufferWith, bufferHeight);

        glViewport(0, 0, bufferWith[0], bufferHeight[0]);

        window.loop(() -> sceneRender.render(drawElementContexts));

        glfwTerminate();
    }
}
