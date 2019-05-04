package prot;

import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIVector3D;
import org.lwjgl.assimp.Assimp;

import java.io.File;

public class AsimbTest {
    public static void main(String[] args) {
        File file = new File("C:/Users/Kostia/IdeaProjects/lwjglPRac/src/main/resources/cube.obj");

        boolean exists = file.exists();

        AIScene aiScene = Assimp.aiImportFile(file.toString(), Assimp.aiProcess_Triangulate);

        AIMesh mesh = AIMesh.create(aiScene.mMeshes().get(0));

        AIVector3D.Buffer aiVector3DS = mesh.mVertices();

        float x = aiVector3DS.x();
        float y = aiVector3DS.y();
        float z = aiVector3DS.z();

        System.out.println(x);
    }
}
