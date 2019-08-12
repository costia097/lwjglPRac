package ui.legui.image.loader;

import ui.legui.image.BufferedImage;
import ui.legui.image.LoadableImage;

/**
 * Created by ShchAlexander on 3/2/2017.
 */
public class DefaultImageLoader extends ImageLoader {

    @Override
    protected LoadableImage createImage(String path) {
        return new BufferedImage(path);
    }
}
