package com.example.momomo00.photomemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by momomo00 on 14/09/21.
 */
public class MenuInputString implements DialogInterface.OnClickListener {

    private static final MenuInputString instance = new MenuInputString();

    private Activity activity;
    private PhotoMemoView photoMemoView;

    private EditText editText;

    private MenuInputString() {}
    public static MenuInputString getInstance() {
        return instance;
    }

    public MenuInputString init(Activity activity, PhotoMemoView photoMemoView) {

        this.activity = activity;
        this.photoMemoView = photoMemoView;

        return instance;
    }

    public void show() {
        View view = LayoutInflater.from(this.activity).inflate(R.layout.dialog_input_string, null);

        this.editText = (EditText)view.findViewById(R.id.editTextForInputString);

        AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);
        builder.setTitle("文字列入力")
                .setView(view)
                .setPositiveButton("はい", this)
                .setNegativeButton("いいえ", this)
                .show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        switch(which) {
            case DialogInterface.BUTTON_POSITIVE :
                photoMemoView.drawText(this.editText.getText().toString());
                break;
            default :
                break;
        }
    }
}
