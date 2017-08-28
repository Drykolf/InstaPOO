package tecpoo.instapoo.gui;
import android.graphics.Bitmap;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import tecpoo.instapoo.filters.*;

import java.io.File;
import java.io.FileNotFoundException;


import tecpoo.instapoo.R;

public class PreviewActivity extends AppCompatActivity{

    private File imageLocalCopy;
    private Bitmap imageBitMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        Intent intent = getIntent();
        Uri imageUri = intent.getParcelableExtra("imageUri");

        imageLocalCopy = FileGenerator.getOutputMediaFile();


        try {
            InputStreamFileLoader.copyInputStreamToFile(getContentResolver().openInputStream(imageUri),imageLocalCopy);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        imageBitMap = ImageOrientationFixer.fixOrientation(this, imageLocalCopy);
        ImageView previewImage = (ImageView) findViewById(R.id.imageViewPreview);
        Button btn = (Button) findViewById(R.id.saveButton);

        //aplicar filtro
        DecompositionFilter filter = new DecompositionFilter();
        Bitmap filterImage = filter.getFilterImage(imageBitMap,1);

        previewImage.setImageBitmap(filterImage);


    }


}
