package core;

import org.apache.commons.io.IOUtils;
import org.joml.Matrix4d;
import org.joml.Vector3d;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.charset.Charset;

import static com.sun.org.apache.bcel.internal.util.SecuritySupport.getResourceAsStream;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
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
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

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

    private float currentAngle = 0.0f;

    public SceneRender() {
        /*
        i think clean screen before actions
        make it fully black
         */
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    public void render() {
        /*
       clear screen color before render something
         */
        glClear(GL_COLOR_BUFFER_BIT);

        /*
        sets the given shader program to render
        - Every shader and rendering call after glUseProgram
         */
        glUseProgram(shaderProgram);


        /*
        some logic
         */
        if (direction) {
            triOffset += triIncremnt;
        } else {
            triOffset -= triIncremnt;
        }

        if (Math.abs(triOffset) >= maxTriOff) {
            direction = !direction;
        }

        /*
        some logic with rotate
         */
        currentAngle += 0.1f;

        /*
        idk -???
         */
        glUniform1f(uniformModel, triOffset);

        /*
        need to create flat buffer
        16 floats because matrix 4on 4 equal 16 slots
         */
        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);


        Matrix4d translate = new Matrix4d()
                .translate(new Vector3d(triOffset, 0, 0))
                .rotate(currentAngle * toRadians, new Vector3d(0, 0, 1));

        translate.get(matrixBuffer);

        /*
        idk -->??
         */
        glUniformMatrix4fv(uniformModel, false, matrixBuffer);

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
        glDrawArrays(GL_TRIANGLES, 0, 3);

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
            vShader = IOUtils.toString(getResourceAsStream("shaders/vertex/shader.vert"), Charset.defaultCharset());
            fShader = IOUtils.toString(getResourceAsStream("shaders/fragment/shader.frag"), Charset.defaultCharset());
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
        uniformModel = glGetUniformLocation(shaderProgram, "model");
    }

}
