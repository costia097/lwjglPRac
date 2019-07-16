package benchmark;

import org.joml.Matrix4d;

import java.util.Random;

/*
The main purpose of this is compare performance between java and c++ math evaluation.
For now using matrix 4 on 4 plus and multiple.
About result hm... pretty not sure Need to double check further ...
 */

@SuppressWarnings("unused")
public class MatrixAddAndMultiply {
    public static void main(String[] args) {
        Matrix4d matrixOne = createMatrcix4on4Inline();

        Matrix4d matrixToAdd = createMatrcix4on4Inline();

        long startTime = System.currentTimeMillis();

        Matrix4d result = null;

        for (int i = 0; i < 1_000; i++) {
            result = matrixOne.mul(matrixToAdd);
        }

        System.out.println(result.m33());
        long endTime = System.currentTimeMillis();

        System.out.println("Time taken: " + (endTime - startTime));
    }

    private static Matrix4d createMatrcix4on4Inline() {

        return new Matrix4d(0.53, 0.55, 0.56, 0.57,
                0.58, 0.59, 0.6, 0.61,
                0.62, 0.63, 0.64, 0.65,
                0.66, 0.67, 0.68, 0.69);
    }

    private static Matrix4d createMatrix4on4() {
        Random random = new Random();
        Matrix4d matrixd = new Matrix4d();

        matrixd._m00(random.nextDouble()* 100);
        matrixd._m01(random.nextDouble()* 100);
        matrixd._m02(random.nextDouble() * 100);
        matrixd._m03(random.nextDouble() * 100);

        matrixd._m10(random.nextDouble()* 100);
        matrixd._m11(random.nextDouble()* 100);
        matrixd._m12(random.nextDouble()* 100);
        matrixd._m13(random.nextDouble()* 100);

        matrixd._m20(random.nextDouble()* 100);
        matrixd._m21(random.nextDouble()* 100);
        matrixd._m22(random.nextDouble()* 100);
        matrixd._m23(random.nextDouble()* 100);

        matrixd._m30(random.nextDouble()* 100);
        matrixd._m31(random.nextDouble()* 100);
        matrixd._m32(random.nextDouble()* 100);
        matrixd._m33(random.nextDouble()* 100);

        return matrixd;
    }
}
