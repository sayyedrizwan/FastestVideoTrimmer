package com.rizwan.fastestvideotrimmer;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {


    public static final String EXTRA_PATH = "path";
    private static final int REQUEST_TAKE_GALLERY_VIDEO = 1;
    private String selectedImagePath, filemanagerstring;
    private RelativeLayout recordvideo, selectvideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectvideo = findViewById(R.id.selectvideo);
        recordvideo = findViewById(R.id.recordvideo);


        selectvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Video"), REQUEST_TAKE_GALLERY_VIDEO);
            }
        });

        recordvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent videoCapture = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivityForResult(videoCapture, REQUEST_TAKE_GALLERY_VIDEO);


            }
        });


    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_GALLERY_VIDEO) {
                Uri selectedImageUri = data.getData();

                // OI FILE Manager
                filemanagerstring = selectedImageUri.getPath();

                // MEDIA GALLERY
                selectedImagePath = getPath(selectedImageUri);

                if (selectedImagePath != null) {

                    Intent intent = new Intent(MainActivity.this, VideoCropping.class);
                    helper.Path = selectedImagePath;
                    startActivity(intent);


                }
            }
        }
    }


    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }


}
