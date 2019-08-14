package protV2.render;

import lombok.Getter;
import lombok.Setter;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11C.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.glFramebufferTexture2D;

@Getter
@Setter
public class CBackend {
    private VertexStream vertexStream;
    private IndexStream indexStream;

    private int QuadIB;
    private int old_QuadIB;
    private int CuboidIB;

    // Render-targets
    private int pFB;
    private int[] pRT = new int[4];
    private int pZB;

    private int vb;
    private int ib;

    private int vbStride;

    private R_constants constants;
    private R_constant_table ctable;

    private SState state;

    private int ps;
    private int vs;
    private int gs;

    //todo
    public void setXform(int id, Matrix4f M) {

    }

    //todo
    public int getFB() {
        return pFB;
    }

    //todo
    public void setRT(int rt, int id) {
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 + id, GL_TEXTURE_2D, rt, 0);
    }

    //todo
    public void setZB(int zb) {

    }

    public void setPS() {

    }

    //todo
    public void setFormat() {

    }
}
