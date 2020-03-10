package com.example.android.camera2video;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.VideoView;

//import android.support.v7.app.AppCompatActivity;




public class video_player extends AppCompatActivity {


    private static final int REQUEST_TAKE_GALLERY_VIDEO = 10 ;
    private VideoView videoView;
    private Uri videoUri;
        Camera2VideoFragment camera2VideoFragment= new Camera2VideoFragment();
        private String MEDIA_PATH ="/storage/emulated/0/Android/data/com.example.android.camera2video/files/1583779132398.mp4";// = "/storage/emulated/0/Android/data/com.example.android.camera2video/files/1583779132398.mp4";//Environment.getExternalStorageDirectory().getPath() + "/Download/Sample_Videos2.mp4";

       @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_video_player);

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

            videoView = findViewById(R.id.videoView);

        }

        public void PlayButton(View view){



            if(videoView.isPlaying())
            {
                videoView.resume();
            }
            else{
                videoView.start();
            }

            new CountDownTimer(videoView.getDuration(),1){

                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onTick(long l) {
                }

                @Override
                public void onFinish() {

                }
            }.start();
        }

        public void PauseButton(View view){
            videoView.pause();

        }
        public void RestartButton(View view){
            videoView.stopPlayback();
            videoView.setVideoURI(videoUri);
            videoView.start();;
        }
        public void StopButton(View view){
            videoView.stopPlayback();
            videoView.setVideoURI(videoUri);
        }

        String giveMePath;
    Intent intent = new Intent();

    public void SelectButton(View view) {
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        startActivityForResult(Intent.createChooser(intent,"Select Video"), REQUEST_TAKE_GALLERY_VIDEO);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

     switch (requestCode){
         case 10:
             if(resultCode==RESULT_OK){
                 videoUri= data.getData();
                 videoView.setVideoURI(videoUri);
             }
     }


    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
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


    public void FilterButton(View view) {

        videoView.setBackgroundColor(CalendarContract.Colors.TYPE_CALENDAR);

    }
}
