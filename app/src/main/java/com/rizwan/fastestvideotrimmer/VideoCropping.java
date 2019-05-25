package com.rizwan.fastestvideotrimmer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.rizwan.videocropper.interfaces.interfaces.interfaces.OnCropVideoListener;
import com.rizwan.videocropper.interfaces.interfaces.interfaces.OnTrimVideoListener;
import com.rizwan.videocropper.interfaces.interfaces.videoCropper;

    public class VideoCropping extends AppCompatActivity implements OnTrimVideoListener, OnCropVideoListener {


    private ProgressDialog progressDialog;
    private videoCropper videoCropper;
    private Context context = VideoCropping.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_cropping);


        //setting progressbar
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Cropping Video.. Please Wait!!");


        videoCropper = (videoCropper) findViewById(R.id.croppingactivity);

        if (videoCropper != null) {
            videoCropper.setMaxDuration(204); //3.40 Min
            videoCropper.setOnTrimVideoListener(this);
            videoCropper.setOnK4LVideoListener(this);
            videoCropper.setDestinationPath("/storage/emulated/0/Download"); //custom path = /storage/emulated/0/
            videoCropper.setVideoURI(Uri.parse(helper.Path));
            videoCropper.setVideoInformationVisibility(true);

        }

    }

    @Override
    public void onVideoPrepared() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(VideoCropping.this, "Perparing video", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onTrimStarted() {

        progressDialog.show();
    }

    @Override
    public void getResult(final Uri uri) {
        progressDialog.cancel();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(VideoCropping.this, "Video Saved At: " + uri.getPath(), Toast.LENGTH_SHORT).show();




                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Choose The Player")
                        .setItems(R.array.menu, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (which == 0){

                                    Intent intent = new Intent(VideoCropping.this, VideoPlayer.class);
                                    helper.Path = uri.getPath();
                                    startActivity(intent);
                                    finish();

                                }else if (which == 1){
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    intent.setDataAndType(uri, "video/mp4");
                                    startActivity(intent);
                                    finish();

                                }else {
                                    Toast.makeText(VideoCropping.this, "select it", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                builder.show();



            }
        });


    }

    @Override
    public void cancelAction() {


    }

    @Override
    public void onError(final String message) {
        progressDialog.cancel();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(VideoCropping.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Called when pointer capture is enabled or disabled for the current window.
     *
     * @param hasCapture True if the window has pointer capture.
     */
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
