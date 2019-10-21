package protV2.render;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class R_constant {
    private String name;
    private short type; // float=0/integer=1/boolean=2

    private R_constant_setup.RC_dest destination;

    private R_constant_load ps;
    private R_constant_load vs;
    private R_constant_load gs;

    public boolean equals() {
        //todo
        return false;
    }
}
