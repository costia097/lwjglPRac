package prot;

class OverlapUtils {

    static boolean isOverlapPointWithBorderBox(Fpoint fpoint, BorderBox borderBox) {
        return isOverlapPointWithBorderBox(fpoint.getX(), fpoint.getY(), borderBox);
    }

    static boolean isOverlapPointWithBorderBox(double x, double y, BorderBox borderBox) {
        return (x >= borderBox.getLeftBottomVertex().getX()) &&
                (x <= borderBox.getRightTopVertex().getX()) &&
                (y >= borderBox.getLeftBottomVertex().getY()) &&
                (y <= borderBox.getRightTopVertex().getY());
    }
}
