package prot;

class CommonUtils {

    /**
     * Main purpose is this method is convert pixel value to normal OpenGL value(-1; 1)
     *
     * @param pixel    value of pixel
     * @param maxValue max value of pixel
     * @param isX      is it x (width) pixel -> true or this is y pixel(height) -> false
     * @return normal value of pixel from -1 to 1
     */
    static double normalizedPixelCoordinate(double pixel, double maxValue, boolean isX) {
        double middle = maxValue / 2;
        double normalValue = (pixel - middle) / middle;
        if (pixel >= middle) {
            return isX ? normalValue : normalValue * -1;
        } else {
            return isX ? normalValue : normalValue * -1;
        }
    }
}
