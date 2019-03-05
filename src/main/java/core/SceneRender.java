package core;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

public class SceneRender {
    public SceneRender() {
        /*
        i think clean screen before actions
         */
        glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
    }

    public void render() {
        /*
        idk???
         */
        glClear(GL_COLOR_BUFFER_BIT);
    }
}
