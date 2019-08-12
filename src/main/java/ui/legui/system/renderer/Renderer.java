package ui.legui.system.renderer;

import ui.legui.component.Frame;
import ui.legui.system.context.Context;

/**
 * Created by ShchAlexander on 19.09.2017.
 */
public interface Renderer {

    void initialize();

    void render(Frame frame, Context context);

    void destroy();
}
