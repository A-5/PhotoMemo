package com.example.momomo00.photomemo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

/**
 * フォトメモアプリ
 */
public class MyActivity extends Activity {
    private PhotoMemoView photoMemoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        photoMemoView = new PhotoMemoView(this);


        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(PhotoFile.getInstance().getPhotoFile()));
        startActivityForResult(intent, DefineData.REQUEST_CODE_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case DefineData.REQUEST_CODE_CAMERA :
                if(resultCode == Activity.RESULT_OK) {
                    setScreenOrientation();
                    photoMemoView.setBitmapImage(data, PhotoFile.getInstance().getPhotoFile());
                    setContentView(photoMemoView);
                }
                break;
            default :
                break;
        }
    }

    private void setScreenOrientation() {
        int screenOrientation;

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            screenOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        } else {
            screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        }

        setRequestedOrientation(screenOrientation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 選ばれたメニューの種類(ID)に応じて、処理する
        switch (item.getItemId()) {
            // メニュー「線の太さ」の処理
            case R.id.menu_paintwidth:
                MenuForPaintWidth.getInstance().init(this, photoMemoView).selectPaintWidth();
                break;

            // メニュー「線の色」の処理
            case R.id.menu_paintcolor:
                MenuForPaintColor.getInstance().init(this, photoMemoView).selectPaintColor();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
