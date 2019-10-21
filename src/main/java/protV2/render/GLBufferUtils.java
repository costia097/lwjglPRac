package protV2.render;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;

public class GLBufferUtils {

    private static void createBuffer(int pBuffer, float[] data, int dataSize, boolean bImmutable, boolean bIndexBuffer) {
        int usage = bImmutable ? GL_STATIC_DRAW : GL_DYNAMIC_DRAW;
        int target = bIndexBuffer ? GL_ELEMENT_ARRAY_BUFFER : GL_ARRAY_BUFFER;

        pBuffer = glGenBuffers();
        glBindBuffer(target, pBuffer);
        glBufferData(target, data, usage);
    }

    public static void createVertexBuffer(int pBuffer, float[] pData, int DataSize, boolean bImmutable) {
        createBuffer(pBuffer, pData, DataSize, bImmutable, false);
    }

    public static void createIndexBuffer(int pBuffer, float[] pData, int DataSize, boolean bImmutable) {
        createBuffer(pBuffer, pData, DataSize, bImmutable, true);
    }
}
