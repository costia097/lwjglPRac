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
import static org.lwjgl.opengl.GL20.glUniform4f;
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

    void render(List<DrawElementContext> drawElementContexts) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        drawElementContexts.forEach(drawElementContext -> {
            glUseProgram(drawElementContext.getShaderProgram());

            glBindVertexArray(drawElementContext.getVao());

            positionVector.x = drawElementContext.getPosition().getX();
            positionVector.y = drawElementContext.getPosition().getY();
            positionVector.z = drawElementContext.getPosition().getZ();

            modelMatrix.identity();

            Matrix4f translate = modelMatrix.translate(positionVector);
            translate.get(modelFloatBuffer);

            int inColorLocation = glGetUniformLocation(drawElementContext.getShaderProgram(), "inColor");

            Fvector color = drawElementContext.getColor();
            glUniform4f(inColorLocation, color.getX(), color.getY(), 0, 0);

            int modelLoc = glGetUniformLocation(drawElementContext.getShaderProgram(), "model");
            glUniformMatrix4fv(modelLoc, false, modelFloatBuffer);

            if (drawElementContext.isIndexed()) {
                glDrawElements(drawElementContext.getMode(), drawElementContext.getCountOfVertex(), GL_UNSIGNED_INT, 0);
            } else {
                glDrawArrays(drawElementContext.getMode(), 0, drawElementContext.getCountOfVertex());
            }

            glBindVertexArray(0);
            glUseProgram(0);
            modelFloatBuffer.clear();
        });
    }
}
