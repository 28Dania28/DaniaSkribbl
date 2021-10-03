package com.dania.guessit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;

public class Playground extends AppCompatActivity {

    private RelativeLayout keyboardRl;
    private EditText et;
    private SimpleDraweeView wb;
    private ImageView timerIv;
    private boolean drawing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeUI();

    }

    private void initializeUI() {
        if (drawing){
            setContentView(R.layout.activity_playground2);
        }else {
            setContentView(R.layout.activity_playground);
        }
        keyboardRl = findViewById(R.id.keyboardRl);
        et = findViewById(R.id.et);
        wb = (SimpleDraweeView) findViewById(R.id.wb);
        timerIv = findViewById(R.id.timerIv);

        keyboardRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openKeyboard();
            }
        });

        timerIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawing){
                    drawing = false;
                    initializeUI();
                }else {
                    drawing = true;
                    initializeUI();
                }
            }
        });
    }

    private void openKeyboard() {
//        InputMethodManager inputMethodManager =
//                (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputMethodManager.toggleSoftInputFromWindow(
//                et.getApplicationWindowToken(),
//                InputMethodManager.SHOW_FORCED, 0);
        et.requestFocus();
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }
}