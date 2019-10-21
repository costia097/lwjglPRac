package protV2.render;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL41.glProgramUniform1f;
import static org.lwjgl.opengl.GL41.glProgramUniform2f;
import static org.lwjgl.opengl.GL41.glProgramUniform2fv;
import static org.lwjgl.opengl.GL41.glProgramUniform3f;
import static org.lwjgl.opengl.GL41.glProgramUniform3fv;
import static org.lwjgl.opengl.GL41.glProgramUniform4f;
import static org.lwjgl.opengl.GL41.glProgramUniformMatrix4fv;
import static org.lwjgl.opengl.GL41.glProgramUniformMatrix4x2fv;
import static org.lwjgl.opengl.GL41.glProgramUniformMatrix4x3fv;
import static org.lwjgl.opengl.GL41C.glProgramUniform1i;
import static org.lwjgl.opengl.GL41C.glProgramUniform4fv;
import static protV2.render.CHW.checkGL;

public class R_constants {

    //todo
    private void set(R_constant C, R_constant_load L, Matrix4f A) {
        Vector4f[] it = new Vector4f[4];


        switch (L.getCls()) {
            case RC_2x4:
                it[0].set(A.m00(), A.m10(), A.m20(), A.m30());
                it[1].set(A.m01(), A.m11(), A.m21(), A.m31());

                FloatBuffer floatBuffer = FloatBuffer.allocate(1);

                glProgramUniformMatrix4x2fv(L.getProgram(), L.getLocation(), true, floatBuffer);
                checkGL();
                break;
            case RC_3x4:
                it[0].set(A.m00(), A.m10(), A.m20(), A.m30());
                it[1].set(A.m01(), A.m11(), A.m21(), A.m31());
                it[2].set(A.m02(), A.m12(), A.m22(), A.m32());

                FloatBuffer allocate = FloatBuffer.allocate(1);

                glProgramUniformMatrix4x3fv(L.getProgram(), L.getLocation(), true, allocate);
                checkGL();
                break;
            case RC_4x4:
                it[0].set(A.m00(), A.m10(), A.m20(), A.m30());
                it[1].set(A.m01(), A.m11(), A.m21(), A.m31());
                it[2].set(A.m02(), A.m12(), A.m22(), A.m32());
                it[3].set(A.m03(), A.m13(), A.m23(), A.m33());

                FloatBuffer floatBufferA = FloatBuffer.allocate(1);

                glProgramUniformMatrix4fv(L.getProgram(), L.getLocation(), true, floatBufferA);
                checkGL();
                break;
            default:
        }
    }

    private void set(R_constant C, R_constant_load L, Vector4f A) {
        FloatBuffer allocate = FloatBuffer.allocate(4);
        FloatBuffer floatBuffer = A.get(allocate);

        switch (L.getCls()) {
            case RC_1x2:
                glProgramUniform2fv(L.getProgram(), L.getLocation(), floatBuffer);
                checkGL();
                break;
            case RC_1x3:
                glProgramUniform3fv(L.getProgram(), L.getLocation(), floatBuffer);
                checkGL();
                break;
            case RC_1x4:
                glProgramUniform4fv(L.getProgram(), L.getLocation(), floatBuffer);
                checkGL();
                break;
            default:
                throw new RuntimeException();
        }
    }

    private void set(R_constant C, R_constant_load L, float x, float y, float z, float w) {
        switch (L.getCls()) {
            case RC_1x2:
                glProgramUniform2f(L.getProgram(), L.getLocation(), x, y);
                checkGL();
                break;
            case RC_1x3:
                glProgramUniform3f(L.getProgram(), L.getLocation(), x, y, z);
                checkGL();
                break;
            case RC_1x4:
                glProgramUniform4f(L.getProgram(), L.getLocation(), x, y, z, w);
                checkGL();
                break;
            default:
                throw new RuntimeException();
        }
    }

    private void set(R_constant C, R_constant_load L, float A) {
        glProgramUniform1f(L.getProgram(), L.getLocation(), A);
        checkGL();
    }


    private void set(R_constant C, R_constant_load L, int A) {
        glProgramUniform1i(L.getProgram(), L.getLocation(), A);
        checkGL();
    }


    //todo
    public void set(R_constant C, Matrix4f A){

    }

    public void set(R_constant C, Vector4f A) {
        switch (C.getDestination()) {
            case RC_dest_pixel:
                set(C, C.getPs(), A);
                break;
            case RC_dest_vertex:
                set(C, C.getVs(), A);
                break;
            case RC_dest_geometry:
                set(C, C.getGs(), A);
                break;
            default:
                throw new RuntimeException();
        }
    }

    public void set(R_constant C, float x, float y, float z, float w) {

    }

    public  void set(R_constant C, float A){

    }

    public void set(R_constant C, int A) {

    }

    void seta(R_constant C, int e, Matrix4f A){

    }

    public void seta(R_constant C, int e, Vector4f A) {

    }

    public void seta(R_constant C, int e, float x, float y, float z, float w){

    }


}
