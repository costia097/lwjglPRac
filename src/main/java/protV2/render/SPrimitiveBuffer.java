package protV2.render;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SPrimitiveBuffer {

    private SGeometry pGeom;
    private int v_cnt;
    private int i_cnt;
    private CBackend.D3DPRIMITIVETYPE p_type;
    private int p_cnt;
}
