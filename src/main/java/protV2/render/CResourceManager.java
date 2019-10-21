package protV2.render;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class CResourceManager {

    private List<SDeclaration> v_declarations = new ArrayList<>();
    private List<SGeometry> v_geoms = new ArrayList<>();

    public static SState createState() {
        return null;
    }

    public SGeometry CreateGeom(D3DVERTEXELEMENT9 decl, int vb, int ib) {
        SDeclaration dcl = _CreateDecl(decl);

        //todo

        SGeometry Geom = new SGeometry();

        Geom.setDcl(dcl);
        Geom.setVb(vb);
        Geom.setIb(ib);

        //todo
        Geom.setVbStride(0);

        v_geoms.add(Geom);

        return Geom;
    }

    public SDeclaration _CreateDecl(D3DVERTEXELEMENT9 dcl) {
        SDeclaration D = new SDeclaration();
        D.setDcl(glGenVertexArrays());
        D.setFVF(0);

        //todo

        v_declarations.add(D);

        return D;
    }
}
