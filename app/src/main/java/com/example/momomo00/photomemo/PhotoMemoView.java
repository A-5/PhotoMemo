package com.example.momomo00.photomemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 *
 * Created by momomo00 on 14/09/10.
 */
public class PhotoMemoView extends ImageView {
    private Activity activity;

    private Bitmap photo;
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;
    private Path path;

    private float startX;
    private float startY;

    private float textPositionX;
    private float textPositionY;

    /**
     * コンストラクタ
     *
     * @param context アクティビティ
     */
    public PhotoMemoView(Context context) {
        super(context);

        this.activity = (Activity)context;

        this.photo = null;
        this.bitmap = null;
        setBackgroundColor(Color.BLUE);

        paint = new Paint();
        setPaintStrokeWidth(DefineData.INIT_STROKE_WIDTH);
        setPaintColor(getResources().getColor(R.color.black));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);

        path = new Path();

        setOnTouchListener(PhotoTouchEvent.getInstance().init(this.activity, this));
    }

    public void setPaintStrokeWidth(int strokeWidth) {
        paint.setStrokeWidth((float) strokeWidth);
    }

    public int getPaintStrokeWidth() {
        return (int) paint.getStrokeWidth();
    }

    public void setPaintColor(int color) {
        paint.setColor(color);
    }

    public int getPaintColor() {
        return paint.getColor();
    }

    public void setLineFrom(float x, float y) {
        this.startX = x;
        this.startY = y;
    }

    public void drawLineTo(float x, float y) {
        this.canvas.drawLine(startX, startY, x, y, paint);
        invalidate();
        setLineFrom(x, y);
    }

    public void setTextPosition(float x, float y) {
        this.textPositionX = x;
        this.textPositionY = y;
    }

    public void drawText(String text) {

        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.red));
        paint.setTextSize(80.0f);
        paint.setAntiAlias(true);

        canvas.drawText(text, this.textPositionX, this.textPositionY, paint);
        invalidate();
    }

    /**
     * 画像ファイルを画面に表示する
     *
     * @param data      起動先データ
     * @param photoFile 画像データ
     */
    public void setBitmapImage(Intent data, File photoFile) {
        Bitmap bitmap = null;
        try {
            if (photoFile.exists()) {
                bitmap = loadBitmap(new FileInputStream(photoFile));

            } else if ((data != null) && (data.getData() != null)) {
                bitmap = loadBitmap(
                        activity.getContentResolver().openInputStream(data.getData()));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.photo = bitmap;
    }

    /**
     * 画像ファイルの読み込み
     *
     * @param inputStream バイトデータ
     * @return 読み込んだビットマップファイル
     */
    private Bitmap loadBitmap(InputStream inputStream) {
        Bitmap bitmap = null;

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();

            if (inputStream.available() > DefineData.BITMAP_MAX_SIZE) {
                options.inSampleSize = inputStream.available() / DefineData.BITMAP_MAX_SIZE + 1;
            }
            bitmap = BitmapFactory.decodeStream(inputStream, null, options);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        if (this.bitmap == null) {

            this.bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            this.canvas = new Canvas(this.bitmap);

            int bitmapWidth = photo.getWidth();
            int bitmapHeight = photo.getHeight();
            float degrees = 0f;

            if ((width > height) && (bitmapWidth < bitmapHeight)) {
                // 画面が横長の場合で画像が縦長の時は画像を２７０度回転する
                degrees = 270;

            } else if ((width < height) && (bitmapWidth > bitmapHeight)) {
                // 画面が縦長の場合で画像が横長の時は画像を９０度回転させる
                degrees = 90;
            }

            Matrix matrix = new Matrix();
            matrix.postRotate(degrees);

            Bitmap bitmap = Bitmap.createBitmap(this.photo, 0, 0, bitmapWidth, bitmapHeight, matrix, true);
            this.canvas.drawBitmap(bitmap, 0, 0, null);
            bitmap.recycle();
            bitmap = null;
        }
        setImageBitmap(this.bitmap);
    }
}

