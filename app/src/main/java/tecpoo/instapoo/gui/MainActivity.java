package tecpoo.instapoo.gui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.provider.MediaStore;
import android.content.Context;
import android.support.v4.content.ContextCompat;

import java.io.File;


import tecpoo.instapoo.R;


public class MainActivity extends AppCompatActivity {

    private FloatingActionButton cameraButton;
    private FloatingActionButton galleryButton;
    private Uri imageFile;
    private String imagePath;

    private final int ASK_FOR_PERMISSIONS_REQUEST = 0;
    private final int CAMERA_PICTURE_REQUEST = 100;
    private final int GALLERY_PICTURE_REQUEST = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        cameraButton = (FloatingActionButton) findViewById(R.id.camera);

        galleryButton = (FloatingActionButton) findViewById(R.id.gallery);

        if (!checkForPermissions(this)) {
            cameraButton.setEnabled(false);
            galleryButton.setEnabled(false);

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, ASK_FOR_PERMISSIONS_REQUEST);
        }
    }

    private boolean checkForPermissions(Context context) {
        //check for android version higher or equal to marshmallow
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }

        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == ASK_FOR_PERMISSIONS_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                cameraButton.setEnabled(true);
                galleryButton.setEnabled(true);
            }
        }
    }

    public void takePictureWithCamera(View view) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File tempFile = FileGenerator.getOutputMediaFile();
        imagePath = tempFile.getPath();
        imageFile = FileProvider.getUriForFile(this, "tecpoo.instapoo.fileprovider", tempFile);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFile);
            startActivityForResult(takePictureIntent, CAMERA_PICTURE_REQUEST);
        }

    }



    public void choosePictureFromGallery(View view){
        Intent choosePictureIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        if (choosePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(choosePictureIntent, GALLERY_PICTURE_REQUEST);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ((requestCode == GALLERY_PICTURE_REQUEST || requestCode == CAMERA_PICTURE_REQUEST) && resultCode == RESULT_OK){
            if (requestCode == GALLERY_PICTURE_REQUEST){
                imageFile = data.getData();
            }
            else{

                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(imagePath))));
            }

            Intent openPreviewIntent = new Intent(MainActivity.this,PreviewActivity.class);
            openPreviewIntent.putExtra("imageUri",imageFile);
            startActivity(openPreviewIntent);
        }


    }




}
