package protV2.render;

import lombok.Getter;
import lombok.Setter;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11C.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glFramebufferTexture2D;
import static org.lwjgl.opengl.GL43.glBindVertexBuffer;
import static protV2.render.CHW.checkGL;

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

    private R_constants constants;
    private R_constant_table ctable;

    private SState state;

    private int ps;
    private int vs;
    private int gs;

    private SDeclaration decl;

    private int vb_stride;

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

    public void setFormat(SDeclaration _decl) {
        if (decl != _decl) {
            decl = _decl;
            glBindVertexArray(_decl.getDcl());
            checkGL();
            // Clear cached index buffer
            ib = 0;
        }
    }

    public void setGeometry(SGeometry _geom) {
        setFormat(_geom.getDcl());

        setVertices(_geom.getVb(), _geom.getVbStride());
        setIndices(_geom.getIb());
    }

    public void setVertices(int _vb, int _vb_stride) {
        if (vb != _vb || vb_stride != _vb_stride) {
            vb = _vb;
            vb_stride = _vb_stride;
            glBindVertexBuffer(0, vb, 0, vb_stride);
            checkGL();
        }
    }

    public void setIndices(int _ib) {
        if (ib != _ib){
            ib = _ib;
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ib);
            checkGL();
        }
    }

    public void dbg_DP(D3DPRIMITIVETYPE pt, SGeometry geom, int vBase, int pc) {

    }

    public enum D3DPRIMITIVETYPE {
        D3DPT_POINTLIST,
        D3DPT_LINELIST,
        D3DPT_LINESTRIP,
        D3DPT_TRIANGLELIST,
        D3DPT_TRIANGLESTRIP,
        D3DPT_TRIANGLEFAN,
        D3DPT_FORCE_DWORD,
        ;
    }
}
