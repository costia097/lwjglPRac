package prot;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_POINT;
import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glGetIntegerv;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.GL_MAX_VERTEX_ATTRIBS;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

@SuppressWarnings("Duplicates")
public class Start {

    public static List<DrawContext> drawContexts = new ArrayList<>();

    public static void main(String[] args) {
        Start start = new Start();
        start.start();
    }

    public void start() {
        System.out.println("LWJGL Version " + Version.getVersion() + " is working.");

        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            glfwTerminate();
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        Window window = new Window(800, 600, "Prot");

        int[] params = new int[1];

        glGetIntegerv(GL_MAX_VERTEX_ATTRIBS, params);

//        List<DrawContext> drawContexts = new ArrayList<>();

        Shader shader = new Shader("shaders/prot/fragment/shader.frag", "shaders/prot/vertex/shader.vert");

        shader.compile();

        Shader shaderTest = new Shader("shaders/prot/fragment/test.frag", "shaders/prot/vertex/test.vert");

        shaderTest.compile();

        SceneRender sceneRender = new SceneRender();

//        float[] verticesOne = {
//                -0.5f, -0.5f, 0.0f,
//                0.5f, -0.5f, 0.0f,
//                0.0f, 0.5f, 0.0f
//        };
//
//        float[] verticesTwo = {
//                -0.6f, -0.6f, 0.0f,
//                0.6f, -0.6f, 0.0f,
//                0.0f, 0.6f, 0.0f
//        };

//        DrawContext drawContext = drawPrimitive(verticesOne, 3);
//        DrawContext drawContextTwo = drawPrimitive(verticesTwo, 3);

//        drawContext.setShaderProgram(shader.getShaderProgram());
//        drawContextTwo.setShaderProgram(shaderTest.getShaderProgram());

//        drawContexts.add(drawContext);
//        drawContexts.add(drawContextTwo);

//        float[] vertices = {
//                0.5f, 0.5f, 0.0f,
//                0.5f, -0.5f, 0.0f,
//                -0.5f, -0.5f, 0.0f,
//                -0.5f, 0.5f, 0.0f
//        };
//        int[] indices = {
//                0, 1, 3,
//                1, 2, 3
//        };
//
//        DrawContext drawContext = drawIndexed(vertices, indices, 3);
//        drawContext.setShaderProgram(shader.getShaderProgram());
//
//        drawContexts.add(drawContext);

//        float[] verices = {
//                -0.5f, 0.5f, 0f,
//                0.5f, -0.5f, 0f
//        };
//
//        DrawContext drawContext = drawLine(verices, 3);
//        drawContext.setShaderProgram(shader.getShaderProgram());
//
//        drawContexts.add(drawContext);

//        Fvector first = new Fvector(-0.5f, 0.5f, 0f);
//        Fvector second = new Fvector(0.5f, -0.5f, 0f);
//
//        DrawContext drawContext = drawLine(first, second);
//        drawContext.setShaderProgram(shader.getShaderProgram());

//        drawContexts.add(drawContext);

//        DrawContext drawContext = drawPoint(new Fvector(0, 0, 0));
//        drawContext.setShaderProgram(shader.getShaderProgram());

//        drawContexts.add(drawContext);

        DrawContext drawContext = drawRectangle(
                new Fvector(-0.2f, -0.2f, 0),
                new Fvector(0.2f, -0.2f, 0),
                new Fvector(0.2f, 0.2f, 0),
                true);

        drawContext.setPosition(new Fvector(0, 0, 0));

        drawContext.setShaderProgram(shader.getShaderProgram());

//        drawContexts.add(drawContext);

        drawContexts.add(drawContext);

        window.loop(() -> sceneRender.render(drawContexts));

        glfwTerminate();
    }

    private static DrawContext drawPrimitive(float[] vertices, int size) {
        int ebo = glGenBuffers();
        int vao = glGenVertexArrays();

        glBindVertexArray(vao);

        glBindBuffer(GL_ARRAY_BUFFER, ebo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

        glBindVertexArray(0);
        return new DrawContext(vao, vertices.length / size, GL_TRIANGLES, false);
    }

    private static DrawContext drawCube() {
        return null;
    }

    private static DrawContext drawIndexed(float[] vertices, int[] indexes, int size) {
        int ebo = glGenBuffers();
        int vbo = glGenBuffers();
        int vao = glGenVertexArrays();

        glBindVertexArray(vao);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexes, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

        glBindVertexArray(0);

        return new DrawContext(vao, vertices.length, GL_TRIANGLES, true);
    }

    /**
     *
     * @param a left bottom vertex
     * @param b right bottom vertex
     * @param c right top vertex
     * @param isWireMode is draw only wire
     * @return DrawContext
     * @see DrawContext
     */
    private static DrawContext drawRectangle(Fvector a, Fvector b, Fvector c, boolean isWireMode) {
        int vbo = glGenBuffers();
        int ebo = glGenBuffers();
        int vao = glGenVertexArrays();

        glBindVertexArray(vao);

        float[] vertices = {
                a.getX(), a.getY(), a.getZ(),
                b.getX(), b.getY(), b.getZ(),
                c.getX(), c.getY(), c.getZ(),
                a.getX(), c.getY(), c.getZ()
        };

        int[] indexes = {
                0, 1, 2,
                2, 3, 0
        };

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexes, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        if (isWireMode) {
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        }

        glBindVertexArray(0);

        return new DrawContext(vao, 6, GL_TRIANGLES, true);
    }

    private static DrawContext drawPoint(Fvector fvector) {
        int vbo = glGenBuffers();
        int vao = glGenVertexArrays();

        glBindVertexArray(vao);

        float[] vertices = {
                fvector.getX(), fvector.getY(), fvector.getZ()
        };

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glBindVertexArray(0);

        return new DrawContext(vao, 1, GL_POINTS, false);
    }

    private static DrawContext drawLine(Fvector first, Fvector second) {
        int vbo = glGenBuffers();
        int vao = glGenVertexArrays();

        glBindVertexArray(vao);

        float[] vertices = {
                first.getX(), first.getY(), first.getZ(),
                second.getX(), second.getY(), second.getZ()
        };

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glBindVertexArray(0);

        return new DrawContext(vao, 2, GL_LINE_STRIP, false);
    }

    private static DrawContext drawLine(float[] vertices, int size) {
        int vbo = glGenBuffers();
        int vao = glGenVertexArrays();

        glBindVertexArray(vao);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);


        glBindVertexArray(0);

        return new DrawContext(vao, vertices.length / size, GL_LINE_STRIP, false);
    }
}
