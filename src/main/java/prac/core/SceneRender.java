package prac.core;

import org.apache.commons.io.IOUtils;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.charset.Charset;
import java.util.Random;

import static com.sun.org.apache.bcel.internal.util.SecuritySupport.getResourceAsStream;
import static prac.core.Window.cameraFront;
import static prac.core.Window.cameraPos;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glTexParameterfv;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgramiv;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderiv;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

@SuppressWarnings("Duplicates")
public class SceneRender {

    private int VAO;
    private int shaderProgram;

    /*
    yeap important naming like model  so there are 4 types ...
     */
    private int uniformModel;
    private boolean direction = true;
    private float triOffset = 0.0f;
    @SuppressWarnings("FieldCanBeLocal")
    private final float maxTriOff = 0.7f;
    @SuppressWarnings("FieldCanBeLocal")
    private final float triIncremnt = 0.005f;
    @SuppressWarnings("FieldCanBeLocal")
    private final float toRadians = (float) (Math.PI / 180.0);

    public static float deltaTime = 0.0f; // Time between current frame and last frame
    private float lastFrame = 0.0f; // Time of last frame

    private Random random = new Random();

//    private Vector3f[] cubePositions = {
//            new Vector3f(0.0f,  0.0f,  0.0f), //0
//            new Vector3f(2.0f,  5.0f, -15.0f), //1
//            new Vector3f(-1.5f, -2.2f, -2.5f), //2
//            new Vector3f(-3.8f, -2.0f, -12.3f), //3
//            new Vector3f( 2.4f, -0.4f, -3.5f), //4
//            new Vector3f(-1.7f,  3.0f, -7.5f), //5
//            new Vector3f(1.3f, -2.0f, -2.5f), //6
//            new Vector3f(1.5f,  2.0f, -2.5f), //7
//            new Vector3f(1.5f,  0.2f, -1.5f), //8
//            new Vector3f(-1.3f,  1.0f, -1.5f), //9
//    };

    private Vector3f[] cubePositions = new Vector3f[]{
            new Vector3f(0, 0, 0),
            new Vector3f(1, 0, 0),
            new Vector3f(-1, 0, 0),
    };

    private void initCubePositions() {
        for (int i = 0; i < 1; i++) {
            Vector3f vector3f = new Vector3f(random.nextFloat() * 5, random.nextFloat() , random.nextFloat() * 3);
            cubePositions[i] = vector3f;
        }
    }

    private FloatBuffer modelFloatBuffer = BufferUtils.createFloatBuffer(16);


    public SceneRender() {
        /*
        i think clean screen before actions
        make it fully black
         */
        glClearColor(0.2f, 0.3f, 0.3f, 1.0f);

//        initCubePositions();
    }

    public void render() {
        /*
       clear screen color before render something
         */
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        /*
        sets the given shader program to render
        - Every shader and rendering call after glUseProgram
         */
        glUseProgram(shaderProgram);


        /*
        some logic
         */
//        if (direction) {
//            triOffset += triIncremnt;
//        } else {
//            triOffset -= triIncremnt;
//        }
//
//        if (Math.abs(triOffset) >= maxTriOff) {
//            direction = !direction;
//        }
//
//        /*
//        some logic with rotate
//         */
//        currentAngle += 0.3f;

        /*
        idk -???
         */
//        glUniform1f(uniformModel, triOffset);

        /*
        need to create flat buffer
        16 floats because matrix 4on 4 equal 16 slots
         */

//        int model = glGetUniformLocation(shaderProgram, "model");

//        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);


//        Matrix4f translate = new Matrix4f()
//                .translate(new Vector3f(triOffset, 0, 0))
//                .rotate(-45 * toRadians, new Vector3f(0, 0, 1));

//        translate.get(matrixBuffer);

        /*
        idk -->??
         */
//        glUniformMatrix4fv(model, false, matrixBuffer);

        /*
        create transformations
         */
//        currentModel = currentModel.rotate(-0.3f * toRadians, new Vector3f(0.5f, 1, 0));
        Matrix4f view = new Matrix4f();

        /*
        some camera manipulations
         */

        view.lookAt(cameraPos, new Vector3f(cameraPos.x + cameraFront.x, cameraPos.y+cameraFront.y, cameraPos.z + cameraFront.z), new Vector3f(0, 1.0f, 0));

        Matrix4f projection = new Matrix4f().perspective(60.0f * toRadians, 800f / 600f, 0.1f, 100f);

        /*
        retrieve the matrix uniform locations
         */
//        FloatBuffer modelBuffer = BufferUtils.createFloatBuffer(16);
        FloatBuffer viewBuffer = BufferUtils.createFloatBuffer(16);
        FloatBuffer projectionBuffer = BufferUtils.createFloatBuffer(16);

//        currentModel.get(modelBuffer);
        view.get(viewBuffer);
        projection.get(projectionBuffer);

//        int modelLoc = glGetUniformLocation(shaderProgram, "model");
        int viewLoc = glGetUniformLocation(shaderProgram, "view");
        int projectionLoc = glGetUniformLocation(shaderProgram, "projection");

//        glUniformMatrix4fv(modelLoc, false, modelBuffer);
        glUniformMatrix4fv(viewLoc, false, viewBuffer);
        glUniformMatrix4fv(projectionLoc, false, projectionBuffer);

//        double timeValue = GLFW.glfwGetTime();
//
//        float greenValue = (float) ((Math.sin(timeValue) / 2.0d) + 0.5d);
//
//        int vertexColorLocation = glGetUniformLocation(shaderProgram, "ourColor");
//
//        glUniform4f(vertexColorLocation, 0, greenValue, 0, 1);

        /*
        Binds a vertex array object
         */
        glBindVertexArray(VAO);

       /*
       The glDrawArrays function takes as its first argument the OpenGL primitive type we would like to draw.
       Since I said at the start we wanted to draw a triangle, and I don't like lying to you, we pass in GL_TRIANGLES.
       The second argument specifies the starting index of the vertex array we'd like to draw; we just leave this at 0.
       The last argument specifies how many vertices we want to draw, which is 3 (we only render 1 triangle from our data, which is exactly 3 vertices long).
        */
//        glDrawArrays(GL_TRIANGLES, 0, 36);

        /*
         -- !!! Use that to indicate that we want to draw from an index buffer. For example in ebo (element buffer object) where we pass indexes
         */
//        glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_INT, 0);


        for (Vector3f cubePosition : cubePositions) {
            Matrix4f model = new Matrix4f()
                    .translate(cubePosition);
//                    .rotate( 20.0f * toRadians, new Vector3f(0.5f, 1, 0));

            model.get(modelFloatBuffer);

            int modelLoc = glGetUniformLocation(shaderProgram, "model");
            glUniformMatrix4fv(modelLoc, false, modelFloatBuffer);

            glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_INT, 0);

            modelFloatBuffer.clear();
        }

//        FloatBuffer modelBuffer = BufferUtils.createFloatBuffer(16);
//        Matrix4f model = new Matrix4f()
//                .translate(new Vector3f(3, 3, 0));
//
//        model.get(modelBuffer);
//
//        int modelLoc = glGetUniformLocation(shaderProgram, "model");
//        glUniformMatrix4fv(modelLoc, false, modelBuffer);
//
//        glDrawArrays(GL_TRIANGLES, 0, 3);

        float currentFrame = (float) GLFW.glfwGetTime();

        deltaTime = currentFrame - lastFrame;
        lastFrame = currentFrame;

        /*
        unbiding
         */
        glBindVertexArray(0);

        /*
        unbiding
         */
        glUseProgram(0);
    }

    /*
    Note about VAO (vertex array object)
    and VBO (vertex buffer object)
    --
    vao consist from vbo values
    --
    create vertex for triangle (x,y,z) diapazon from -1 to 1
    0,0,0 -> point is on a middle of the screen
     */
    public void createTriangle() {

        /*
        coordinates of triangle
         */
        float[] vertices = {
                -1.0f, -1.0f, 0.0f,
                1.0f, -1.0f, 0.0f,
                0.0f, 1.0f, 0.0f
        };

        /*
        generate vertex array object
         */
        int vao = glGenVertexArrays();

        VAO = vao;

        /*
        biding vertex array object
         */
        glBindVertexArray(vao);

        /*
        generate vertex buffer object
        --
        seems like can create with parameters
        we can create one or many buffers objects VBO
         */
        int vbo = glGenBuffers();

        /*
        binding vertex buffer object

        target can be specified -- yeap
         */
        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        /*
        copy previous vertex data to buffer memory
        -- target where we copy is previously bound buffer

        The fourth parameter specifies how we want the graphics card to manage the given data. This can take 3 forms:
        -- GL_STATIC_DRAW: the data will most likely not change at all or very rarely.
        -- GL_DYNAMIC_DRAW: the data is likely to change a lot.
        -- GL_STREAM_DRAW: the data will change every time it is drawn.
         */
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        /*
        The function glVertexAttribPointer has quite a few parameters so let's carefully walk through them:

        -- The first parameter specifies which vertex attribute we want to configure.
             Remember that we specified the location of the position vertex attribute in the vertex shader with layout (location = 0).
             This sets the location of the vertex attribute to 0 and since we want to pass data to this vertex attribute, we pass in 0.

        -- The next argument specifies the size of the vertex attribute. The vertex attribute is a vec3 so it is composed of 3 values.

        -- The third argument specifies the type of the data which is GL_FLOAT (a vec* in GLSL consists of floating point values).

        -- The next argument specifies if we want the data to be normalized.
             If we're inputting integer data types (int, byte) and we've set this to GL_TRUE, the integer data is normalized to 0 (or -1 for signed data) and 1 when converted to float.
             This is not relevant for us so we'll leave this at GL_FALSE.

        -- The fifth argument is known as the stride and tells us the space between consecutive vertex attributes.
             Since the next set of position data is located exactly 3 times the size of a float away we specify that value as the stride.
             Note that since we know that the array is tightly packed (there is no space between the next vertex attribute value) we could've also specified the stride as 0 to let OpenGL
             determine the stride (this only works when values are tightly packed).

        -- The last parameter is of type void* and thus requires that weird cast. This is the offset of where the position data begins in the buffer.
             Since the position data is at the start of the data array this value is just 0. We will explore this parameter in more detail later on
         */
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        /*
        Enables a generic vertex attribute array.
         */
        glEnableVertexAttribArray(0);

        /*
        unbinding what??
         */
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        /*
        unbinding vertex array object
         */
        glBindVertexArray(0);
    }

    public void createRectangle() {

        int vbo, vao, ebo;

        /*
        vertices of rectangle
         */
        float[] vertices = {
                0.5f,  0.5f, 0.0f,  // top right // 0
                0.5f, -0.5f, 0.0f,  // bottom right // 1
                -0.5f, -0.5f, 0.0f,  // bottom left // 2
                -0.5f,  0.5f, 0.0f   // top left // 3
        };

        /*
        idk
         */
        int[] indexes = {
                0, 1, 3,   // first triangle
                1, 2, 3    // second triangle
        };

        ebo = glGenBuffers();
        vbo = glGenBuffers();
        vao = glGenVertexArrays();

        this.VAO = vao;

        glBindVertexArray(vao);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexes, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    }

    public void createColorTriangle() {
        int vbo, vao;

        float[] vertices = {
                // positions         // colors rgb
                0.5f, -0.5f, 0.0f,  1.0f, 0.0f, 0.0f,   // bottom right
                -0.5f, -0.5f, 0.0f,  0.0f, 1.0f, 0.0f,   // bottom left
                0.0f,  0.5f, 0.0f,  0.0f, 0.0f, 1.0f    // top
        };

        vbo = glGenBuffers();
        vao = glGenVertexArrays();

        this.VAO = vao;

        glBindVertexArray(vao);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        //position attribute
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 24, 0);
        glEnableVertexAttribArray(0);

        //color attribute
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 24, 12);
        glEnableVertexAttribArray(1);
    }

    public void createTexturedRectangle() {
        /*
        -- Note important !!! for textures coordinates

         float[] texCoords = {
                0.0f, 0.0f,  // lower-left corner
                1.0f, 0.0f,  // lower-right corner
                0.5f, 1.0f   // top-center corner
        };

         */

//        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_MIRRORED_REPEAT);
//        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_MIRRORED_REPEAT);

        /*
        for z axis use GL_TEXTURE_WRAP_R
         */

//        float[] borderColor = {1.0f, 1.0f, 0.0f, 1.0f};

//        glTexParameterfv(GL_TEXTURE_2D, GL_TEXTURE_BORDER_COLOR, borderColor);

        /*
        can be GL_NEAREST or GL_LINEAR
         */

//        imgByteBuffer.clear();

        //rgb

        float[] vertices = {
                0.5f,  0.5f, 0.0f,   1.0f, 0.0f, 0.0f,   1.0f, 1.0f,
                0.5f, -0.5f, 0.0f,   0.0f, 1.0f, 0.0f,   1.0f, 0.0f,
                -0.5f, -0.5f, 0.0f,   0.0f, 0.0f, 1.0f,   0.0f, 0.0f,
                -0.5f,  0.5f, 0.0f,   1.0f, 1.0f, 0.0f,   0.0f, 1.0f
        };

        int[] indexes = {
                3, 0, 1,
                1, 2, 3
        };

        int vbo, vao, ebo;

        ebo = glGenBuffers();
        vbo = glGenBuffers();
        vao = glGenVertexArrays();

        glBindVertexArray(vao);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexes, GL_STATIC_DRAW);

        this.VAO = vao;

        /*
        coordinates
         */
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 8 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        /*
        color
         */
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 8 * Float.BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);

        /*
        texture coordinate
         */
        glVertexAttribPointer(2, 2, GL_FLOAT, false, 8 * Float.BYTES, 6 * Float.BYTES);
        glEnableVertexAttribArray(2);

        int width = 0;
        int height = 0;

        ByteBuffer imgByteBuffer = ByteBuffer.allocate(0);

        try {
            File input = new File("C:/Users/Kostia/IdeaProjects/lwjglPRac/src/main/resources/textures/wall.jpg");
            BufferedImage bufferedImageOne = ImageIO.read(input);

            width = bufferedImageOne.getWidth();
            height = bufferedImageOne.getHeight();

            imgByteBuffer = BufferUtils.createByteBuffer(width * height * 4);

            int[] pixeslRaw = bufferedImageOne.getRGB(0, 0, width, height, null, 0, width);

            for (int i = 0; i <height; i++) {
                for (int j = 0; j < width; j++) {
                    int pixel = pixeslRaw[i * width + j];
                    imgByteBuffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                    imgByteBuffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                    imgByteBuffer.put((byte) (pixel & 0xFF));               // Blue component
                    /*
                    in png format exist alfa channel
                     */
//                    buffer.put((byte) ((pixel >> 24) & 0xFF));
                }
            }

            imgByteBuffer.flip();

        } catch (IOException e) {
            e.printStackTrace();
        }

        int textureOne = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureOne);

        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, imgByteBuffer);
        glGenerateMipmap(GL_TEXTURE_2D);

        imgByteBuffer.clear();

//        glUniform1i(glGetUniformLocation(shaderProgram, "texture1"), 0);
    }

    public void createCubeNotIptimazed() {

        glEnable(GL_DEPTH_TEST);

        float[] vertices = {

                //x     //y     //z

                -0.5f, -0.5f, -0.5f,   0.0f, 0.0f,
                0.5f, -0.5f, -0.5f,  1.0f, 0.0f,
                0.5f, 0.5f, -0.5f,   1.0f, 1.0f,
                0.5f, 0.5f, -0.5f,   1.0f, 1.0f,
                -0.5f, 0.5f, -0.5f,   0.0f, 1.0f,
                -0.5f, -0.5f, -0.5f,   0.0f, 0.0f,

                -0.5f, -0.5f, 0.5f,   0.0f, 0.0f,
                0.5f, -0.5f, 0.5f,   1.0f, 0.0f,
                0.5f, 0.5f, 0.5f,   1.0f, 1.0f,
                0.5f, 0.5f, 0.5f,   1.0f, 1.0f,
                -0.5f, 0.5f, 0.5f,   0.0f, 1.0f,
                -0.5f, -0.5f, 0.5f,   0.0f, 0.0f,

                -0.5f, 0.5f, 0.5f,   1.0f, 0.0f,
                -0.5f, 0.5f, -0.5f,   1.0f, 1.0f,
                -0.5f, -0.5f, -0.5f,   0.0f, 1.0f,
                -0.5f, -0.5f, -0.5f,   0.0f, 1.0f,
                -0.5f, -0.5f, 0.5f,   0.0f, 0.0f,
                -0.5f, 0.5f, 0.5f,   1.0f, 0.0f,

                0.5f, 0.5f, 0.5f,   1.0f, 0.0f,
                0.5f, 0.5f, -0.5f,   1.0f, 1.0f,
                0.5f, -0.5f, -0.5f,   0.0f, 1.0f,
                0.5f, -0.5f, -0.5f,   0.0f, 1.0f,
                0.5f, -0.5f, 0.5f,   0.0f, 0.0f,
                0.5f, 0.5f, 0.5f,   1.0f, 0.0f,

                -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
                0.5f, -0.5f, -0.5f, 1.0f, 1.0f,
                0.5f, -0.5f, 0.5f, 1.0f, 0.0f,
                0.5f, -0.5f, 0.5f, 1.0f, 0.0f,
                -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
                -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,

                -0.5f, 0.5f, -0.5f, 0.0f, 1.0f,
                0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
                0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
                0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
                -0.5f, 0.5f, 0.5f, 0.0f, 0.0f,
                -0.5f, 0.5f, -0.5f, 0.0f, 1.0f
        };

//        https://technology.cpm.org/general/3dgraph/?graph3ddata=____bWuRQuRQuRQRzqSuRQuRQTzqSzqSuRQUzqSzqSuRQVuRQzqSuRQWuRQuRQuRQbw7kw7kxmudDsaSuRQuRQzqSTzqSuRQzqSUzqSzqSzqSVzqSzqSzqSWuRQzqSzqSRuRQuRQzqSmw7kw7kxmudH1c

//        float[] indexes = {
//
//        };

        int ebo, vbo, vao;

//        ebo = glGenBuffers();
        vbo = glGenBuffers();
        vao = glGenVertexArrays();

        this.VAO = vao;

        glBindVertexArray(vao);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

//        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
//        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexes, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 2, GL_FLOAT, false, 5 * Float.BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);

//        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

        int width = 0;
        int height = 0;

        ByteBuffer imgByteBuffer = ByteBuffer.allocate(0);

        try {
            File input = new File("C:/Users/Kostia/IdeaProjects/lwjglPRac/src/main/resources/textures/wall.jpg");
            BufferedImage bufferedImageOne = ImageIO.read(input);

            width = bufferedImageOne.getWidth();
            height = bufferedImageOne.getHeight();

            imgByteBuffer = BufferUtils.createByteBuffer(width * height * 4);

            int[] pixeslRaw = bufferedImageOne.getRGB(0, 0, width, height, null, 0, width);

            for (int i = 0; i <height; i++) {
                for (int j = 0; j < width; j++) {
                    int pixel = pixeslRaw[i * width + j];
                    imgByteBuffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                    imgByteBuffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                    imgByteBuffer.put((byte) (pixel & 0xFF));               // Blue component
//                    imgByteBuffer.put((byte) ((pixel >> 24) & 0xFF));
                }
            }

            imgByteBuffer.flip();

        } catch (IOException e) {
            e.printStackTrace();
        }

        int textureOne = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureOne);


        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, imgByteBuffer);
        glGenerateMipmap(GL_TEXTURE_2D);

        imgByteBuffer.clear();

        glUniform1i(glGetUniformLocation(shaderProgram, "texture1"), 0);
    }

    public void createCubeOptimized() {

//        https://technology.cpm.org/general/3dgraph/?graph3ddata=____bWuRQuRQuRQRzqSuRQuRQTzqSzqSuRQUzqSzqSuRQVuRQzqSuRQWuRQuRQuRQbw7kw7kxmudDsaSuRQuRQzqSTzqSuRQzqSUzqSzqSzqSVzqSzqSzqSWuRQzqSzqSRuRQuRQzqSmw7kw7kxmudH1c

        glEnable(GL_DEPTH_TEST);

        float[] vertices = {

                -0.5f, -0.5f, -0.5f,   0.0f, 0.0f, //0
                0.5f, -0.5f, -0.5f,  1.0f, 0.0f,  //1
                0.5f, 0.5f, -0.5f,   1.0f, 1.0f,  // 2
                -0.5f, 0.5f, -0.5f,   0.0f, 1.0f, //3

                -0.5f, -0.5f, 0.5f,   0.0f, 0.0f, //4
                0.5f, -0.5f, 0.5f,   1.0f, 0.0f, //5
                0.5f, 0.5f, 0.5f,   1.0f, 1.0f, //6
                -0.5f, 0.5f, 0.5f,   0.0f, 1.0f, // 7

                -0.5f, 0.5f, 0.5f,   1.0f, 0.0f, //8
                -0.5f, 0.5f, -0.5f,   1.0f, 1.0f, //9
                -0.5f, -0.5f, -0.5f,   0.0f, 1.0f, //10
                -0.5f, -0.5f, 0.5f,   0.0f, 0.0f, //11

                0.5f, 0.5f, 0.5f,   1.0f, 0.0f, //12
                0.5f, 0.5f, -0.5f,   1.0f, 1.0f, //13
                0.5f, -0.5f, -0.5f,   0.0f, 1.0f, //14
                0.5f, -0.5f, 0.5f,   0.0f, 0.0f, //15

                -0.5f, -0.5f, -0.5f, 0.0f, 1.0f, //16
                0.5f, -0.5f, -0.5f, 1.0f, 1.0f, //17
                0.5f, -0.5f, 0.5f, 1.0f, 0.0f, //18
                -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,//19

                -0.5f, 0.5f, -0.5f, 0.0f, 1.0f, //20
                0.5f, 0.5f, -0.5f, 1.0f, 1.0f, //21
                0.5f, 0.5f, 0.5f, 1.0f, 0.0f, //22
                -0.5f, 0.5f, 0.5f, 0.0f, 0.0f, //23
        };

        int[] indexes = {
                0,1,2,
                2,3,0,

                4,5,6,
                6,7,4,

                8,9,10,
                10,11,8,

                12,13,14,
                14,15,12,

                16,17,18,
                18,19,16,

                20,21,22,
                22,23,20
        };

        int vbo, ebo , vao;

        vbo = glGenBuffers();
        ebo = glGenBuffers();
        vao = glGenVertexArrays();

        glBindVertexArray(vao);

        this.VAO = vao;

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexes, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 2, GL_FLOAT, false, 5 * Float.BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);

        int width = 0;
        int height = 0;

        ByteBuffer imgByteBuffer = ByteBuffer.allocate(0);

        try {
            File input = new File("C:/Users/Kostia/IdeaProjects/lwjglPRac/src/main/resources/textures/wall.jpg");
            BufferedImage bufferedImageOne = ImageIO.read(input);

            width = bufferedImageOne.getWidth();
            height = bufferedImageOne.getHeight();

            imgByteBuffer = BufferUtils.createByteBuffer(width * height * 4);

            int[] pixeslRaw = bufferedImageOne.getRGB(0, 0, width, height, null, 0, width);

            for (int i = 0; i <height; i++) {
                for (int j = 0; j < width; j++) {
                    int pixel = pixeslRaw[i * width + j];
                    imgByteBuffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                    imgByteBuffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                    imgByteBuffer.put((byte) (pixel & 0xFF));               // Blue component
//                    imgByteBuffer.put((byte) ((pixel >> 24) & 0xFF));
                }
            }

            imgByteBuffer.flip();

        } catch (IOException e) {
            e.printStackTrace();
        }

        int textureOne = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureOne);

        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, imgByteBuffer);
        glGenerateMipmap(GL_TEXTURE_2D);

        imgByteBuffer.clear();

        glUniform1i(glGetUniformLocation(shaderProgram, "texture1"), 0);

//        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    }

    public void createRectangleWithTrianglePhysicsScene() {
        float[] verticesOfTriangle = {
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.0f, 0.5f, 0.0f
        };

        int vao = glGenVertexArrays();

        VAO = vao;

        glBindVertexArray(vao);

        int vbo = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, verticesOfTriangle, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    }


    private int addShader(int shaderProgram, String shaderCode, int shaderType) {

        /*
        creates a shader object.
         */
        int shaderObject = glCreateShader(shaderType);

        /*
        link the given shader object between source code of this shader
         */
        glShaderSource(shaderObject, shaderCode);

        /*
        compiling shader code
         */
        glCompileShader(shaderObject);

        int[] resultOfCompile = new int[1];

        /*
        get result of compiling
         */
        glGetShaderiv(shaderObject, GL_COMPILE_STATUS, resultOfCompile);

        /*
        if something went wrong
         */
        if (resultOfCompile[0] == 0) {
            /*
            info log
            */
            String infoLog = glGetShaderInfoLog(shaderObject);
            System.out.println("BAD compile shader: " + infoLog);
            return 0;
        }

        /*
        attach the shader object to shader program
         */
        glAttachShader(shaderProgram, shaderObject);

        return shaderObject;
    }

    /*
    idk
     */
    public void compileShaders() {
        /*
        create a program object
         */
        int shaderProgram = glCreateProgram();

        this.shaderProgram = shaderProgram;

        /*
        maybe thus ???
         */
        if (shaderProgram == 0) {
            System.out.println("Error while creating shaderProgram program");
            return ;
        }

        String vShader = "";
        String fShader = "";

        try {
            vShader = IOUtils.toString(getResourceAsStream("shaders/prac/vertex/shader.vert"), Charset.defaultCharset());
            fShader = IOUtils.toString(getResourceAsStream("shaders/prac/fragment/shader.frag"), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        add vertex shaderProgram
         */
        int vertexShader = addShader(shaderProgram, vShader, GL_VERTEX_SHADER);

        /*
        add fragment shaderProgram
         */
        int fragmentShader = addShader(shaderProgram, fShader, GL_FRAGMENT_SHADER);

        /*
        link all attached shaders in one final shader program object
         */
        glLinkProgram(shaderProgram);

        int[] result = new int[1];

        /*
        get info about shader link
         */
        glGetProgramiv(shaderProgram, GL_LINK_STATUS, result);

        /*
        if something went wrong
        hope so ???
         */
        if (result[0] == 0) {
            /*
            info log
            */
            String infoLog = glGetProgramInfoLog(shaderProgram);
            System.out.println("Error in shader code: " + infoLog);
            return ;
        }

        /*
        validate something
         */
        glValidateProgram(shaderProgram);

        /*
        get info about program
         */
        glGetProgramiv(shaderProgram, GL_VALIDATE_STATUS, result);

         /*
        if something went wrong
        hope so ???
         */
        if (result[0] == 0) {
            /*
            info log
            */
            String infoLog = glGetProgramInfoLog(shaderProgram);
            System.out.println("Error in shader program: " + infoLog);
        }

        /*
        delete shader to clean up memory
         */
        glDeleteShader(vertexShader);

        glDeleteShader(fragmentShader);

        /*
        something like biding
        idk -???
         */
//        uniformModel = glGetUniformLocation(shaderProgram, "model");
    }

}
