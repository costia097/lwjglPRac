package protV2.render;

public class Resptrcode_geom {

    private CResourceManager resourceManager;

    public void create(D3DVERTEXELEMENT9 decl, int vb, int ib){
        SGeometry sGeometry = resourceManager.CreateGeom(decl, vb, ib);


    }

    public void create(int FVF, int vb, int ib){

    }

    public void create(D3DVERTEXELEMENT9 decl, CGLBuffer vb, CGLBuffer ib){

    }

    public void create(int FVF, CGLBuffer vb, CGLBuffer ib) {

    }
}
