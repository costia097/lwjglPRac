package prot;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

class SceneRender {

    SceneRender() {
        glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
    }

    private Matrix4f modelMatrix = new Matrix4f();
    private Vector3f positionVector = new Vector3f();
    private FloatBuffer modelFloatBuffer = BufferUtils.createFloatBuffer(16);

    void render(List<DrawContext> drawContexts) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        drawContexts.forEach(drawContext -> {
            glUseProgram(drawContext.getShaderProgram());

            glBindVertexArray(drawContext.getVao());

            positionVector.x = drawContext.getPosition().getX();
            positionVector.y = drawContext.getPosition().getY();
            positionVector.z = drawContext.getPosition().getZ();

            modelMatrix.identity();

            Matrix4f translate = modelMatrix.translate(positionVector);

            translate.get(modelFloatBuffer);

            int modelLoc = glGetUniformLocation(drawContext.getShaderProgram(), "model");
            glUniformMatrix4fv(modelLoc, false, modelFloatBuffer);

            if (drawContext.isIndexed()) {
                glDrawElements(drawContext.getMode(), drawContext.getCountOfVertex(), GL_UNSIGNED_INT, 0);
            } else {
                glDrawArrays(drawContext.getMode(), 0, drawContext.getCountOfVertex());
            }

            glBindVertexArray(0);
            glUseProgram(0);
            modelFloatBuffer.clear();
        });
    }
}
