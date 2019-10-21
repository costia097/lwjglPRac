package protV2.render;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ShaderElement {

    private List<SPass> passes = new ArrayList<>();
}
