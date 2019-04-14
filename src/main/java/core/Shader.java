package core;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUseProgram;

//todo in progress
public class Shader {
    private final int programId;
    private final String fragmentShaderCode;
    private final String vertexShaderCode;

    public Shader(int programId, String fragmentShaderCode, String vertexShaderCode) {
        this.programId = programId;
        this.fragmentShaderCode = fragmentShaderCode;
        this.vertexShaderCode = vertexShaderCode;
    }

    void use() {
        glUseProgram(programId);
    }

    void setBool(String name, boolean value) {
        glUniform1i(glGetUniformLocation(programId, name), value ? 1 : 0);
    }

    void setInt(String name, int value) {
        glUniform1i(glGetUniformLocation(programId, name), value);
    }

    void setFloat(String name, float value) {
        glUniform1f(glGetUniformLocation(programId, name), value);
    }

    public int getProgramId() {
        return programId;
    }

    public String getFragmentShaderCode() {
        return fragmentShaderCode;
    }

    public String getVertexShaderCode() {
        return vertexShaderCode;
    }
}
