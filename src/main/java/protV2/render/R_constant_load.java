package protV2.render;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class R_constant_load {
    private int index;
    private int cls;

    private int location;
    private int program;

    public boolean equals(R_constant_load C) {
        return (index == C.index) && (cls == C.cls) && (location == C.location) && (program == C.program);
    }
}
