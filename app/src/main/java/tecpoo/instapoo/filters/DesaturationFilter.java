package tecpoo.instapoo.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by JBarco on 8/27/2017.
 */

public class DesaturationFilter {
    private int Max(int a,int b,int c){
        if(a>b && a>c)return a;
        else if(b>a && b>c)return b;
        else return c;
    }
    private int Min(int a,int b,int c){
        if(a<b && a<c)return a;
        else if(b<a && b<c)return b;
        else return c;
    }
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
                int gray = (Max(red,green,blue)+Min(red,green,blue))/2;
                filterImage.setPixel(h, w, Color.rgb(gray,gray,gray));
            }
        }
        return filterImage;
    }
}
