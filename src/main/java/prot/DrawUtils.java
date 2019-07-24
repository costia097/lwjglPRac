package prot;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

class DrawUtils {
    private static float DEG2RAD = (float) (3.14159 / 180);

    /**
     * Example:
     * DrawElementContext drawContext = drawPoint(new Fvector(0, 0, 0));
     *
     * @param fvector coordinates of point
     * @return draw context
     * @see DrawElementContext
     */
    static DrawElementContext drawPoint(Fvector fvector) {
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
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        return new DrawElementContext(
                vao,
                1,
                GL_POINTS,
                false,
                RenderElementType.OTHER
        );
    }

    /**
     * Example:
     * float[] vertices = {
     * -0.5f, 0.5f, 0f,
     * 0.5f, -0.5f, 0f
     * };
     * <p>
     * DrawElementContext drawContext = drawLine(vertices, 3);
     *
     * @param vertices array of vertices
     * @param size     count of vertices
     * @return draw context
     * @see DrawElementContext
     */
    static DrawElementContext drawLine(float[] vertices, int size) {
        int vao = createAndFillBuffers(vertices);
        return new DrawElementContext(vao, size, GL_LINE_STRIP, false, RenderElementType.OTHER);
    }

    /**
     * Example:
     * Fvector first = new Fvector(-0.5f, 0.5f, 0f);
     * Fvector second = new Fvector(0.5f, -0.5f, 0f);
     * <p>
     * DrawElementContext drawContext = drawLine(first, second);
     *
     * @param first  first vector
     * @param second second vector
     * @return draw context
     * @see DrawElementContext
     */
    static DrawElementContext drawLine(Fpoint first, Fpoint second) {
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
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        return new DrawElementContext(vao, 2, GL_LINE_STRIP, false, null);
    }

    static DrawElementContext drawCircle(float x, float y, float z, float r) {
        int vbo = glGenBuffers();
        int vao = glGenVertexArrays();

        glBindVertexArray(vao);

        float[] vertices = new float[24 * 3];

        int localCounter = 0;

        for (int i = 15; i <= 360; i = i + 15) {
            float degInRad = i * DEG2RAD;

            float yLocal = (float) (Math.sin(degInRad) * r);
            float xLocal = (float) (Math.cos(degInRad) * r);

            vertices[localCounter++] = xLocal;
            vertices[localCounter++] = yLocal;
            vertices[localCounter++] = 0;
        }

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glBindVertexArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        return new DrawElementContext(vao, 24, GL_LINE_LOOP, false, null);
    }

    /**
     * Example:
     * float[] verticesOne = {
     * -0.5f, -0.5f, 0.0f,
     * 0.5f, -0.5f, 0.0f,
     * 0.0f, 0.5f, 0.0f
     * };
     * <p>
     * float[] verticesTwo = {
     * -0.6f, -0.6f, 0.0f,
     * 0.6f, -0.6f, 0.0f,
     * 0.0f, 0.6f, 0.0f
     * };
     * <p>
     * DrawElementContext drawContext = drawPrimitive(verticesOne, 3);
     * DrawElementContext drawContextTwo = drawPrimitive(verticesTwo, 3);
     *
     * @param vertices array of vertices
     * @param size     count of vertices
     * @return draw context
     * @see DrawElementContext
     */
    static DrawElementContext drawPrimitive(float[] vertices, int size) {
        int vao = createAndFillBuffers(vertices);
        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

        return new DrawElementContext(vao, vertices.length / size, GL_TRIANGLES, false, null);
    }

    /**
     * @param a          left bottom vertex
     * @param b          right bottom vertex
     * @param c          right top vertex
     * @param isWireMode is draw only wire
     * @return DrawElementContext
     * @see DrawElementContext
     */
    static DrawElementContext drawRectangleIndexed(Fpoint a,
                                                   Fpoint b,
                                                   Fpoint c,
                                                   @SuppressWarnings("SameParameterValue") boolean isWireMode) {
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

        return buildDrawElementContextForRectangle(isWireMode, vbo, ebo, vao, vertices);
    }

    /**
     *
     * @param a left bottom vertex
     * @param b right top vertex
     * @param isWireMode is wire mode
     * @return draw element context
     */
    static DrawElementContext drawRectangleIndexed(Fpoint a,
                                                   Fpoint b,
                                                   @SuppressWarnings("SameParameterValue") boolean isWireMode) {
        int vbo = glGenBuffers();
        int ebo = glGenBuffers();
        int vao = glGenVertexArrays();

        glBindVertexArray(vao);

        float[] vertices = {
                a.getX(), a.getY(), a.getZ(),
                b.getX(), a.getY(), b.getZ(),
                b.getX(), b.getY(), b.getZ(),
                a.getX(), b.getY(), a.getZ(),
        };

        return buildDrawElementContextForRectangle(isWireMode, vbo, ebo, vao, vertices);
    }

    private static DrawElementContext buildDrawElementContextForRectangle(boolean isWireMode, int vbo, int ebo, int vao, float[] vertices) {
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
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

        return new DrawElementContext(
                vao,
                6,
                GL_TRIANGLES,
                true,
                RenderElementType.OTHER
        );
    }


    /**
     * Example:
     * float[] vertices = {
     * 0.5f, 0.5f, 0.0f,
     * 0.5f, -0.5f, 0.0f,
     * -0.5f, -0.5f, 0.0f,
     * -0.5f, 0.5f, 0.0f
     * };
     * int[] indices = {
     * 0, 1, 3,
     * 1, 2, 3
     * };
     * <p>
     * DrawElementContext drawContext = drawIndexed(vertices, indices, 3);
     *
     * @param vertices array of vertices
     * @param indexes  array of indexes
     * @param size     count of vertices
     * @return draw context
     * @see DrawElementContext
     */
    static DrawElementContext drawIndexed(float[] vertices, int[] indexes, int size) {
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
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        return new DrawElementContext(vao, vertices.length, GL_TRIANGLES, true, null);
    }

    private static DrawElementContext drawCube() {
        return null;
    }

    private static int createAndFillBuffers(float[] vertices) {
        int vbo = glGenBuffers();
        int vao = glGenVertexArrays();

        glBindVertexArray(vao);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glBindVertexArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        return vao;
    }
}
