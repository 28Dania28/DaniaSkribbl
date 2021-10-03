package com.dania.guessit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.util.Util;

import java.util.HashMap;

public class Dash extends AppCompatActivity {

    private Button playPublicBtn, enterRoomBtn, createRoomBtn;
    private EditText enterNameEt;
    private ImageView leftBtn, rightBtn, avatarIv;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        playPublicBtn = findViewById(R.id.playPublicBtn);
        enterRoomBtn = findViewById(R.id.enterRoomBtn);
        createRoomBtn = findViewById(R.id.createRoomBtn);
        enterNameEt = findViewById(R.id.enterNameEt);
        leftBtn = findViewById(R.id.leftBtn);
        rightBtn = findViewById(R.id.rightBtn);
        avatarIv = findViewById(R.id.avatarIv);
        db = FirebaseFirestore.getInstance();

        sharedPreferences
                = getSharedPreferences(
                "sharedPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (Common.my_uid==null){
            final String my_uid = sharedPreferences.getString("my_uid",null);
            if (my_uid==null){
                createId();
            }else {
                Common.my_uid = my_uid;
                final String my_name = sharedPreferences.getString("my_name",null);
                final int my_avatar = sharedPreferences.getInt("my_avatar",1);
                Common.my_name = my_name;
                Common.my_avatar = my_avatar;
                enterNameEt.setText(Common.my_name);
            }
        }else {
            if (Common.my_name!=null){
                enterNameEt.setText(Common.my_name);
            }
        }

//        if (Common.my_name!=null){
//            enterNameEt.setText(Common.my_name);
//        }else {
//            final String my_name = sharedPreferences.getString("my_name",null);
//            final int my_avatar = sharedPreferences.getInt("my_avatar",1);
//            Common.my_avatar = my_avatar;
//            if (my_name!=null){
//                Common.my_name = my_name;
//                enterNameEt.setText(Common.my_name);
//            }
//        }

        getAvatar();

        playPublicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!enterNameEt.getText().toString().isEmpty()){
                    Common.my_name = enterNameEt.getText().toString().trim();
                    editor.putString("my_name",Common.my_name);
                    editor.apply();
                    DocumentReference player = db.collection("Users").document(Common.my_uid);
                    player.update("game_name",Common.my_name);
                    player.update("avatar",Common.my_avatar);
                    Intent i2 = new Intent(getApplicationContext(),Playground.class);
                    startActivity(i2);
                }else {
                    enterNameEt.setError("Enter name");
                    enterNameEt.getError();
                }
            }
        });

        enterRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!enterNameEt.getText().toString().isEmpty()){
                    Common.my_name = enterNameEt.getText().toString().trim();
                    editor.putString("my_name",Common.my_name);
                    editor.apply();
                    DocumentReference player = db.collection("Users").document(Common.my_uid);
                    player.update("game_name",Common.my_name);
                    player.update("avatar",Common.my_avatar);
                    Intent i2 = new Intent(getApplicationContext(),EnterRoom.class);
                    startActivity(i2);
                }else {
                    enterNameEt.setError("Enter name");
                    enterNameEt.getError();
                }
            }
        });

        createRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!enterNameEt.getText().toString().isEmpty()){
                    Common.my_name = enterNameEt.getText().toString().trim();
                    editor.putString("my_name",Common.my_name);
                    editor.apply();
                    DocumentReference player = db.collection("Users").document(Common.my_uid);
                    player.update("game_name",Common.my_name);
                    player.update("avatar",Common.my_avatar);
                    Intent i2 = new Intent(getApplicationContext(),CreateRoom.class);
                    startActivity(i2);
                }else {
                    enterNameEt.setError("Enter name");
                    enterNameEt.getError();
                }
            }
        });

        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.my_avatar == 1){
                    Common.my_avatar = 16;
                    editor.putInt("my_avatar",Common.my_avatar);
                    editor.apply();
                }else {
                    Common.my_avatar = Common.my_avatar-1;
                    editor.putInt("my_avatar",Common.my_avatar);
                    editor.apply();
                }
                getAvatar();
            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.my_avatar == 16){
                    Common.my_avatar = 1;
                    editor.putInt("my_avatar",Common.my_avatar);
                    editor.apply();
                }else {
                    Common.my_avatar = Common.my_avatar+1;
                    editor.putInt("my_avatar",Common.my_avatar);
                    editor.apply();
                }
                getAvatar();
            }
        });

    }

    private void getAvatar() {
        switch (Common.my_avatar){
            case 1:
                avatarIv.setImageResource(R.drawable.avatar_1);
                break;
            case 2:
                avatarIv.setImageResource(R.drawable.avatar_2);
                break;
            case 3:
                avatarIv.setImageResource(R.drawable.avatar_3);
                break;
            case 4:
                avatarIv.setImageResource(R.drawable.avatar_4);
                break;
            case 5:
                avatarIv.setImageResource(R.drawable.avatar_5);
                break;
            case 6:
                avatarIv.setImageResource(R.drawable.avatar_6);
                break;
            case 7:
                avatarIv.setImageResource(R.drawable.avatar_7);
                break;
            case 8:
                avatarIv.setImageResource(R.drawable.avatar_8);
                break;
            case 9:
                avatarIv.setImageResource(R.drawable.avatar_9);
                break;
            case 10:
                avatarIv.setImageResource(R.drawable.avatar_10);
                break;
            case 11:
                avatarIv.setImageResource(R.drawable.avatar_11);
                break;
            case 12:
                avatarIv.setImageResource(R.drawable.avatar_12);
                break;
            case 13:
                avatarIv.setImageResource(R.drawable.avatar_13);
                break;
            case 14:
                avatarIv.setImageResource(R.drawable.avatar_14);
                break;
            case 15:
                avatarIv.setImageResource(R.drawable.avatar_15);
                break;
            case 16:
                avatarIv.setImageResource(R.drawable.avatar_16);
                break;
            default:
                avatarIv.setImageResource(R.drawable.avatar_1);
                break;
        }

    }

    private void createId() {
        DocumentReference player = db.collection("Users").document();
        String uid = player.getId();
        HashMap<String, Object> map = new HashMap<>();
        map.put("game_name",Common.my_name);
        map.put("avatar",Common.my_avatar);
        map.put("uid",uid);
        player.set(map);
        Common.my_uid = uid;
        editor.putString("my_uid",Common.my_uid);
        editor.apply();

    }


}