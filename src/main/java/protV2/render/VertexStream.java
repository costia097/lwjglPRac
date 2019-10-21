package protV2.render;

import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.ARBMapBufferRange.GL_MAP_INVALIDATE_BUFFER_BIT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL15.glUnmapBuffer;
import static org.lwjgl.opengl.GL30.GL_MAP_UNSYNCHRONIZED_BIT;
import static org.lwjgl.opengl.GL30.GL_MAP_WRITE_BIT;
import static org.lwjgl.opengl.GL30.glMapBufferRange;


@Getter
@Setter
public class VertexStream {
    private static final int rsDVB_Size = 4096; //Fixed: (bytes_need<=mSize) && vl_Count
    private static final int rsDIB_Size = 512;

    private int pVB;
    private int mSize;
    private int mPosition;
    private int mDiscardID;

    private int oldPVB;

    public static final int LOCKFLAGS_FLUSH = GL_MAP_WRITE_BIT | GL_MAP_UNSYNCHRONIZED_BIT | GL_MAP_INVALIDATE_BUFFER_BIT;
    public static final int LOCKFLAGS_APPEND = GL_MAP_WRITE_BIT | GL_MAP_UNSYNCHRONIZED_BIT;


    public VertexStream() {
        clear();
    }

    public void create() {
        mSize = rsDVB_Size * 1024;
        pVB = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, pVB);
    }

    public void destroy() {
        if (pVB != 0) {
            glDeleteBuffers(pVB);
        } else {
            System.out.println("Error");
        }
    }

    public void lock(int vlCount, int stride, int vOffset) {
        // Ensure there is enough space in the VB for this data
        int bytesNeed = vlCount * stride;
        if (bytesNeed <= mSize) {
            System.out.println("bytes_need =  " + bytesNeed + ", mSize = " + mSize + ", vl_Count = " + vlCount);
        }

        // Vertex-local info
        int vlMSize = mSize / stride;
        int vlMPosition = mPosition / stride + 1;

        ByteBuffer pData;

        // Check if there is need to flush and perform lock
        glBindBuffer(GL_ARRAY_BUFFER, pVB);

        if ((vlCount + vlMPosition) >= vlMSize) {
            // FLUSH-LOCK
            mPosition = 0;
            vOffset = 0;
            mDiscardID++;

            pData = glMapBufferRange(GL_ARRAY_BUFFER, mPosition, bytesNeed, LOCKFLAGS_FLUSH);
        } else {
            // APPEND-LOCK
            mPosition = vlMPosition * stride;
            vOffset = vlMPosition;

            pData = glMapBufferRange(GL_ARRAY_BUFFER, mPosition, bytesNeed, LOCKFLAGS_APPEND);
        }

        if (pData == null) {
            System.out.println("Error");
        }
    }

    public void unlock(int count, int stride) {
        mPosition += count * stride;

        glBindBuffer(GL_ARRAY_BUFFER, pVB);
        if (glUnmapBuffer(GL_ARRAY_BUFFER)) {
            System.out.println("Suc");
        } else {
            System.out.println("Failed");
        }
    }

    public void resetBegin() {
        oldPVB = pVB;
        destroy();
    }

    public void resetEnd() {
        create();
    }

    public void clear() {
        pVB = 0;
        mSize = 0;
        mPosition = 0;
        mDiscardID = 0;
    }
}
