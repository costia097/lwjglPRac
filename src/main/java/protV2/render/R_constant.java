package protV2.render;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class R_constant {
    private String name;
    private short type; // float=0/integer=1/boolean=2

    private int destination;

    private R_constant_load ps;
    private R_constant_load vs;

    public boolean equals() {
        //todo
        return false;
    }
}
