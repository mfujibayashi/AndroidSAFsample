package com.example.safsample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    private static final int READ_MEDIA_REQUEST_CODE = 42;
    private static final int READ_IMAGE_REQUEST_CODE = 43;
    private static final String TAG = "SAFSample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Music Openボタンの設定
        Button musicOpenButton = (Button)findViewById(R.id.buttonMusicOpen);
        MusicOpenButtonClickListener musicOpenButtonClickListener = new MusicOpenButtonClickListener();
        musicOpenButton.setOnClickListener(musicOpenButtonClickListener);

        //Image Openボタンの設定
        Button imageOpenButton = (Button)findViewById(R.id.buttonImageOpen);
        ImageOpenButtonClickListener imageOpenButtonClickListener = new ImageOpenButtonClickListener();
        imageOpenButton.setOnClickListener(imageOpenButtonClickListener);
    }

    class MusicOpenButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            //ファイル選択
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            //カテゴリー選択可能にする
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            //MIME Typeでフィルターする
            intent.setType("*/*");
            String[] mimeTypes = {"audio/*", "video/*"};
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            startActivityForResult(intent, READ_MEDIA_REQUEST_CODE);
            //Fragment操作
            VideoViewFragment videoViewFragment = new VideoViewFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, videoViewFragment);
            transaction.commit();
        }
    }

    class ImageOpenButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            //ファイル選択
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            //カテゴリー選択可能にする
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            //MIME Typeでフィルターする
            intent.setType("image/*");
            startActivityForResult(intent, READ_IMAGE_REQUEST_CODE);
            //Fragment操作
            ImageViewFragment imageViewFragment = new ImageViewFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, imageViewFragment);
            transaction.commit();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == READ_MEDIA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.i(TAG, "Media Uri: " + uri.toString());
                VideoView videoView = (VideoView)findViewById(R.id.videoView);
                videoView.setVideoURI(uri);
                videoView.setMediaController(new MediaController(this));
                videoView.start();
            }
        }
        if (requestCode == READ_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.i(TAG, "Image Uri: " + uri.toString());
                ImageView imageView = (ImageView)findViewById(R.id.imageView);
                imageView.setImageURI(uri);
            }
        }

    }

}
