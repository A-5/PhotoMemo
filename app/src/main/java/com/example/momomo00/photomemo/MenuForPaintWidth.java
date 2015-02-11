package com.example.momomo00.photomemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 *
 *
 * Created by momomo00 on 14/09/17.
 */
public class MenuForPaintWidth
        implements SeekBar.OnSeekBarChangeListener, DialogInterface.OnClickListener {
    private final static MenuForPaintWidth instance = new MenuForPaintWidth();
    private Activity activity;
    private PhotoMemoView photoMemoView;

    private TextView textView;

    private int paintStrokeWidth;

    /**
     * コンストラクタ。シングルトンパターン採用のためprivate
     */
    private MenuForPaintWidth() {}

    /**
     * シングルトンインスタンスを取得
     *
     * @return インスタンス
     */
    public static MenuForPaintWidth getInstance() {
        return instance;
    }

    /**
     * 初期化処理
     *
     * @param activity アクティビティ
     * @param photoMemoView 画像データ
     */
    public MenuForPaintWidth init(Activity activity, PhotoMemoView photoMemoView) {
        this.activity = activity;
        this.photoMemoView = photoMemoView;

        return getInstance();
    }

    /**
     * メニュー：線の太さの選択を処理
     */
    public void selectPaintWidth() {
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_select_paint_width, null);

        SeekBar seekBar = (SeekBar)view.findViewById(R.id.SeekBar);
        paintStrokeWidth = photoMemoView.getPaintStrokeWidth();
        seekBar.setProgress(paintStrokeWidth);
        seekBar.setMax(DefineData.MAX_STROKE_WIDTH);
        seekBar.setOnSeekBarChangeListener(this);

        textView = (TextView)view.findViewById(R.id.TextView);
        int progress = seekBar.getProgress();
        textView.setText(Integer.toString(progress));

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("線の太さ")
                .setView(view)
                .setPositiveButton("はい", this)
                .setNegativeButton("キャンセル", this)
                .show();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        paintStrokeWidth = progress;
        textView.setText(Integer.toString(paintStrokeWidth));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch(which) {
            case DialogInterface.BUTTON_POSITIVE :
                photoMemoView.setPaintStrokeWidth(paintStrokeWidth);
                break;

            default :
                break;
        }
    }
}
