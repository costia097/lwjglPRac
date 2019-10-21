package protV2.render;

import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL15.glUnmapBuffer;
import static org.lwjgl.opengl.GL15C.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL30.glMapBufferRange;
import static protV2.render.VertexStream.LOCKFLAGS_APPEND;
import static protV2.render.VertexStream.LOCKFLAGS_FLUSH;

@Getter
@Setter
public class IndexStream {
    private static final int rsDVB_Size = 4096; //Fixed: (bytes_need<=mSize) && vl_Count
    private static final int rsDIB_Size = 512;

    private int pIB;

    private int mSize; // real size (usually mCount, aligned on 512b boundary)
    private int mPosition;
    private int mDiscardID;

    private int old_pIB;

    public void create() {
        mSize = rsDIB_Size * 1024;
        pIB = glGenBuffers();

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, pIB);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, mSize, GL_DYNAMIC_DRAW);
    }

    public void destroy() {
        if (pIB != 0) {
            glDeleteBuffers(pIB);
        } else {
            System.out.println("Error");
        }
    }

    public void lock(int Count, int vOffset) {
        vOffset = 0;
        ByteBuffer pLockedData;

        // Ensure there is enough space in the VB for this data
        if ((2 * Count <= mSize)) {
            System.out.println("Error");
        }

        // If either user forced us to flush,
        // or there is not enough space for the index data,
        // then flush the buffer contents
        int dwFlags = LOCKFLAGS_APPEND;

        if (2 * (Count + mPosition) >= mSize)
        {
            mPosition = 0; // clear position
            dwFlags = LOCKFLAGS_FLUSH; // discard it's contens
            mDiscardID++;
        }

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, pIB);
        pLockedData = glMapBufferRange(GL_ELEMENT_ARRAY_BUFFER, mPosition * 2, Count * 2, dwFlags);

        if (pLockedData == null) {
            System.out.println("Error");
        }
    }

    public void unlock(int RealCount) {
        mPosition += RealCount;

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, pIB);
        if (glUnmapBuffer(GL_ELEMENT_ARRAY_BUFFER)) {
            System.out.println("Suc");
        } else {
            System.out.println("Error");
        }
    }

    private void resetBegin() {
        old_pIB = pIB;
        destroy();
    }

    private void resetEnd() {
        create();
    }

    public void clear() {
        pIB = 0;
        mSize = 0;
        mPosition = 0;
        mDiscardID = 0;
    }
}
