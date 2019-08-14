package protV2.render;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SGeometry {

    private SDeclaration dcl;
    private int vb;
    private int ib;
    private int vbStride;
}
