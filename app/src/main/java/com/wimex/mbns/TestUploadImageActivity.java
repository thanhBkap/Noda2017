package com.wimex.mbns;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class TestUploadImageActivity extends AppCompatActivity {
    int REQUEST_PICK = 99;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_upload_image);
        addControls();
        addEvents();
    }

    public void addControls() {
        imageView = (ImageView) findViewById(R.id.image);
    }

    public void addEvents() {
    }

    public void onImageGalleryClicked(View v) {
        //Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent i = new Intent(Intent.ACTION_PICK);
        File f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String picturePath = f.getPath();
        Uri data = Uri.parse(picturePath);
        i.setDataAndType(data, "image/*");
        startActivityForResult(i, REQUEST_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_PICK) {
                Uri dataUri = data.getData();
                InputStream inputStream;
                try {
                    inputStream = getContentResolver().openInputStream(dataUri);
                    Bitmap image = BitmapFactory.decodeStream(inputStream);
                    imageView.setImageBitmap(image);

                }catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "cant open image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
