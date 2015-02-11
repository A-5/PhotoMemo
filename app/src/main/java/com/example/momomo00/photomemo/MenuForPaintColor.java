package com.example.momomo00.photomemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

/**
 *
 * Created by momomo00 on 14/09/17.
 */
public class MenuForPaintColor
        implements DialogInterface.OnClickListener, View.OnClickListener{
    private static final MenuForPaintColor instance = new MenuForPaintColor();

    private Activity activity;
    private PhotoMemoView photoMemoView;

    private ImageView paintColorImage;

    private int paintColor;

    private MenuForPaintColor() {}
    public static MenuForPaintColor getInstance() {
        return instance;
    }

    public MenuForPaintColor init(Activity activity, PhotoMemoView photoMemoView) {
        this.activity = activity;
        this.photoMemoView = photoMemoView;

        return getInstance();
    }

    public void selectPaintColor() {
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_select_paint_color, null);
        paintColorImage = (ImageView)view.findViewById(R.id.nowColor);
        paintColor = photoMemoView.getPaintColor();
        paintColorImage.setBackgroundColor(paintColor);

        ((ImageView)view.findViewById(R.id.colorIsBlack)).setOnClickListener(this);
        ((ImageView)view.findViewById(R.id.colorIsRed)).setOnClickListener(this);
        ((ImageView)view.findViewById(R.id.colorIsBlue)).setOnClickListener(this);
        ((ImageView)view.findViewById(R.id.colorIsWhite)).setOnClickListener(this);
        ((ImageView)view.findViewById(R.id.colorIsGreen)).setOnClickListener(this);
        ((ImageView)view.findViewById(R.id.colorIsYellow)).setOnClickListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("線の色")
                .setView(view)
                .setPositiveButton("はい", this)
                .setNegativeButton("キャンセル", this)
                .show();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.colorIsBlack :
                paintColor = activity.getResources().getColor(R.color.black);
                break;

            case R.id.colorIsBlue :
                paintColor = activity.getResources().getColor(R.color.blue);
                break;

            case R.id.colorIsGreen :
                paintColor = activity.getResources().getColor(R.color.green);
                break;

            case R.id.colorIsRed :
                paintColor = activity.getResources().getColor(R.color.red);
                break;

            case R.id.colorIsWhite :
                paintColor = activity.getResources().getColor(R.color.white);
                break;

            case R.id.colorIsYellow :
                paintColor = activity.getResources().getColor(R.color.yellow);
                break;

            default :
                break;
        }
        paintColorImage.setBackgroundColor(paintColor);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch(which) {
            case DialogInterface.BUTTON_POSITIVE :
                photoMemoView.setPaintColor(paintColor);
                break;

            default:
                break;
        }
    }
}
