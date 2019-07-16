package prot;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

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
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgramiv;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderiv;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glValidateProgram;

@SuppressWarnings("Duplicates")
class Shader {
    private int shaderProgram;
    private String fragmentShaderPath;
    private String vertexShaderPath;

    Shader(String fragmentShaderPath, String vertexShaderPath) {
        this.fragmentShaderPath = fragmentShaderPath;
        this.vertexShaderPath = vertexShaderPath;
    }

    void compile() {
        int shaderProgram = glCreateProgram();
        this.shaderProgram = shaderProgram;

        if (shaderProgram == 0) {
            System.out.println("Error while creating shaderProgram program");
            return ;
        }

        String vShader;
        String fShader;

        try {
            File vertexShaderFile = new File(vertexShaderPath);
            File fragmentShaderFile = new File(fragmentShaderPath);

            vShader = IOUtils.toString(new FileInputStream(vertexShaderFile), Charset.defaultCharset());
            fShader = IOUtils.toString(new FileInputStream(fragmentShaderFile), Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException("Error while loading shaders ", e);
        }

        int vertexShader = addShader(shaderProgram, vShader, GL_VERTEX_SHADER);
        int fragmentShader = addShader(shaderProgram, fShader, GL_FRAGMENT_SHADER);

        glLinkProgram(shaderProgram);

        int[] result = new int[1];

        glGetProgramiv(shaderProgram, GL_LINK_STATUS, result);

        if (result[0] == 0) {
            String infoLog = glGetProgramInfoLog(shaderProgram);
            throw new RuntimeException("Error in shader code: " + infoLog);
        }

        glValidateProgram(shaderProgram);

        glGetProgramiv(shaderProgram, GL_VALIDATE_STATUS, result);

        if (result[0] == 0) {
            String infoLog = glGetProgramInfoLog(shaderProgram);
            throw new RuntimeException("Error in shader program: " + infoLog);
        }

        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
    }

    private int addShader(int shaderProgram, String shaderCode, int shaderType) {
        int shaderObject = glCreateShader(shaderType);

        glShaderSource(shaderObject, shaderCode);

        glCompileShader(shaderObject);

        int[] resultOfCompile = new int[1];

        glGetShaderiv(shaderObject, GL_COMPILE_STATUS, resultOfCompile);

        if (resultOfCompile[0] == 0) {
            String infoLog = glGetShaderInfoLog(shaderObject);
            throw new RuntimeException("BAD compile shader: " + infoLog);
        }

        glAttachShader(shaderProgram, shaderObject);

        return shaderObject;
    }

    int getShaderProgram() {
        return shaderProgram;
    }
}
