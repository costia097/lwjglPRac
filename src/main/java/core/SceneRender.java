package core;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
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
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgramiv;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderiv;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class SceneRender {

    private int VAO;
    private int shader;

    public SceneRender() {
        /*
        i think clean screen before actions
        make it fully black
         */
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    public void render() {
        /*
        idk???
         */
        glClear(GL_COLOR_BUFFER_BIT);

        /*
        idk
         */
        glUseProgram(shader);

        /*
        idk
         */
        glBindVertexArray(VAO);

        /*
        idk
         */
        glDrawArrays(GL_TRIANGLES, 0, 3);

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
         */
        int vbo = glGenBuffers();

        /*
        binding vertex buffer object

        target can be specified -- ???
         */
        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        /*
        idk ??
        static_draw ??
        dynamic_draw ??
         */
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        /*
        idk >>> ???
         */
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        /*
        idk -???
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

    /*
    idk  -- >>???
     */
    private void addShader(int theProgram, String shaderCode, int shaderType) {
        int shader = glCreateShader(shaderType);

        /*
        idk -???
         */
        glShaderSource(shader, shaderCode);

        /*
        something compiling
         */
        glCompileShader(shader);

        int[] resultOfCompile = new int[1];

        glGetShaderiv(shader, GL_COMPILE_STATUS, resultOfCompile);

        /*
        if something went wrong
         */
        if (resultOfCompile[0] == 0) {
            /*
            info log
            */
            String infoLog = glGetShaderInfoLog(shader);
            System.out.println("BAD compile shader: " + infoLog);
            return;
        }

        /*
        idk -???
         */
        glAttachShader(theProgram, shader);



    }

    /*
    idk
     */
    public void compileShaders() {
        int shader = glCreateProgram();

        this.shader = shader;

        /*
        maybe thus ???
         */
        if (shader == 0) {
            System.out.println("Error while creating shader program");
            return ;
        }

        String vShader = "";
        String fShader = "";

        try {
            vShader = IOUtils.toString(getResourceAsStream("shader.vert"), Charset.defaultCharset());
            fShader = IOUtils.toString(getResourceAsStream("shader.frag"), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        add vertex shader
         */
        addShader(shader, vShader, GL_VERTEX_SHADER);

        /*
        add fragment shader
         */
        addShader(shader, fShader, GL_FRAGMENT_SHADER);

        /*
        idk ->>???
         */
        glLinkProgram(shader);

        int[] result = new int[1];

        glGetProgramiv(shader, GL_LINK_STATUS, result);

        /*
        if something went wrong
        hope so ???
         */
        if (result[0] == 0) {
            /*
            info log
            */
            String infoLog = glGetProgramInfoLog(shader);
            System.out.println("BAD: " + infoLog);
            return ;
        }

        /*
        validate something
         */
        glValidateProgram(shader);

        glGetProgramiv(shader, GL_VALIDATE_STATUS, result);

         /*
        if something went wrong
        hope so ???
         */
        if (result[0] == 0) {
            /*
            info log
            */
            String infoLog = glGetProgramInfoLog(shader);
            System.out.println("BADly when validate: " + infoLog);
        }
    }

}
