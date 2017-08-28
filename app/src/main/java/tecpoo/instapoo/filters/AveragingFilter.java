package tecpoo.instapoo.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by JBarco on 8/27/2017.
 */

public class AveragingFilter {
    public Bitmap getFilterImage(Bitmap previewImage) {
        int height = previewImage.getHeight();
        int width = previewImage.getWidth();
        int pixel = 0;
        int red = 0;
        int green = 0;
        int blue = 0;
        Bitmap filterImage = Bitmap.createBitmap(height, width, Bitmap.Config.ARGB_8888);
        for (int h = 1; h < height; h++) {
            for (int w = 1; w < width; w++) {
                pixel = previewImage.getPixel(h, w);
                red = (pixel >> 16) & 0xff;
                green = (pixel >> 8) & 0xff;
                blue = pixel & 0xff;
                int gray = (red + green + blue) / 3;
                filterImage.setPixel(h, w, Color.rgb(gray, gray, gray));
            }
        }
        return filterImage;
    }
}
