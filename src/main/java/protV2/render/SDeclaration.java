package protV2.render;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SDeclaration {
    private int FVF;
    private int dcl;
    private List<D3DVERTEXELEMENT9> dcl_code = new ArrayList<>();


}
