package prac.physics;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

public class Main {
    private static World world;
    private static Body rectangle;
    private static double lastY;
    private static double lastVelY;

    public static void main(String[] args) {
        world = new World();

        Body floor = new Body();
        floor.addFixture(Geometry.createRectangle(10, 1));
        floor.setMass(MassType.INFINITE);
        world.addBody(floor);

        rectangle = new Body();
        rectangle.addFixture(Geometry.createRectangle(2, 2));
        rectangle.setMass(MassType.NORMAL);
        rectangle.translate(new Vector2(2, 5));

        world.addBody(rectangle);

        long last = 0;

        while (true) {
            long currentTime = System.currentTimeMillis();
            long deltaTime = currentTime - last;
            last = currentTime;

            double deltaTimeInSec = deltaTime / 1000.0;

            world.update(deltaTimeInSec);

            System.out.println(deltaTimeInSec);
            doCaptureSnapshotOfWorld();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void doCaptureSnapshotOfWorld() {
        if (lastY == 0) {
            lastY = rectangle.getTransform().getTranslationY();
        }
        if (lastVelY == 0) {
            lastVelY = rectangle.getLinearVelocity().y;
        }
        double currentY = rectangle.getTransform().getTranslationY();
        double deltaY = lastY - currentY;
        lastY = currentY;

        double currentVelocityY = rectangle.getLinearVelocity().y;
        double deltaVelocityY = Math.abs(currentVelocityY) - Math.abs(lastVelY);
        lastVelY = currentVelocityY;

        System.out.println("Delta Y: " + deltaY * 1000 + " | " + " deltaVelY: " + Math.abs(deltaVelocityY));
    }
}
