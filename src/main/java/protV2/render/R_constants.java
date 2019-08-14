package protV2.render;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL41.glProgramUniformMatrix4fv;
import static org.lwjgl.opengl.GL41.glProgramUniformMatrix4x2fv;
import static org.lwjgl.opengl.GL41.glProgramUniformMatrix4x3fv;

public class R_constants {

    //todo
    public void set(R_constant C, R_constant_load L, Matrix4f A) {
        Vector4f[] it = new Vector4f[4];


        switch (L.getCls())
        {
            case 4:
//                RC_2x4
//                A._11, A._21, A._31, A._41)
                it[0].set(A.m00(), A.m10(), A.m20(), A.m30());
//                A._12, A._22, A._32, A._42
//                it[1].set(A._12, A._22, A._32, A._42);
//                glProgramUniformMatrix4x2fv(L.program, L.location, 1, true, null);
                break;
            case 5:
//                RC_3x4
//                it[0].set(A._11, A._21, A._31, A._41);
//                it[1].set(A._12, A._22, A._32, A._42);
//                it[2].set(A._13, A._23, A._33, A._43);
//                glProgramUniformMatrix4x3fv(L.program, L.location, 1, true, null);
                break;
            case 6:
//                RC_4x4
//                it[0].set(A._11, A._21, A._31, A._41);
//                it[1].set(A._12, A._22, A._32, A._42);
//                it[2].set(A._13, A._23, A._33, A._43);
//                it[3].set(A._14, A._24, A._34, A._44);
//                glProgramUniformMatrix4fv(L.program, L.location, 1, true, null);
                break;
            default:
        }
    }

    public void set(R_constant C, R_constant_load L, Vector4f A) {

    }
}
