package protV2.render;

public class CDrawUtilities {

    private SPrimitiveBuffer m_SolidCone;
    private SPrimitiveBuffer m_WireCone;
    private SPrimitiveBuffer m_SolidSphere;
    private SPrimitiveBuffer m_WireSphere;
    private SPrimitiveBuffer m_SolidSpherePart;
    private SPrimitiveBuffer m_WireSpherePart;
    private SPrimitiveBuffer m_SolidCylinder;
    private SPrimitiveBuffer m_WireCylinder;
    private SPrimitiveBuffer m_SolidBox;
    private SPrimitiveBuffer m_WireBox;

    private SGeometry vs_L;
    private SGeometry vs_TL;
    private SGeometry vs_LIT;

    private CBackend RCache;

    //todo
    public void DrawIdentBox(boolean bSolid, boolean bWire, int clr_s, int clr_w) {
        if (bWire) {
//            DU_DRAW_SH_C(RImplementation.m_WireShader, clr_w);
        }
    }


    public void DU_DRAW_SH_C(Shader sh, int clr_w) {
    }
}
