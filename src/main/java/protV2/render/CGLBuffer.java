package protV2.render;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;

public class CGLBuffer {

    private int m_RefCount = 1;
    private int m_Buffer = 0;

    //todo
    public void release() {

    }

    //todo
    public void deleteBuffer() {
        glDeleteBuffers(m_Buffer);
    }

}
