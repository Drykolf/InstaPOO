package tecpoo.instapoo.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;



import java.io.File;
import java.io.IOException;




class ImageOrientationFixer {

    public static Bitmap fixOrientation(Context context, File imageFile) {

        Bitmap myBitmap;
        try {
            myBitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.fromFile(imageFile));

            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
            }
            else if (orientation == 3) {
                matrix.postRotate(180);
            }
            else if (orientation == 8) {
                matrix.postRotate(270);
            }
            myBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(), myBitmap.getHeight(), matrix, true); // rotating bitmap
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


}
