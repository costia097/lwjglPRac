package protV2.render;

import lombok.Getter;
import lombok.Setter;
import org.joml.Vector4f;

@Getter
@Setter
public class FVF {
    @Getter
    @Setter
    public static class L {
        Vector4f p;
        int color;

        //todo
        public void set() {
        }
    }

    @Getter
    @Setter
    public static class V {
        Vector4f p;
        Vector4f t;

        //todo
        public void set() {
        }
    }
}
