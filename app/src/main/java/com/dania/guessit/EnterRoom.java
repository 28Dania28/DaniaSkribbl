package com.dania.guessit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.model.mutation.ArrayTransformOperation;

import java.util.Arrays;

public class EnterRoom extends AppCompatActivity {

    private TextView nameTv;
    private EditText enterCodeEt;
    private Button joinRoomBtn;
    private ImageView avatarIv;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_room);
        nameTv = findViewById(R.id.nameTv);
        enterCodeEt = findViewById(R.id.enterCodeEt);
        joinRoomBtn = findViewById(R.id.joinRoomBtn);
        avatarIv = findViewById(R.id.avatarIv);
        db = FirebaseFirestore.getInstance();

        if (Common.my_name!=null){
            nameTv.setText(Common.my_name);
            getAvatar();
        }else {
            finish();
        }


        enterCodeEt.requestFocus(0);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(enterCodeEt, InputMethodManager.SHOW_IMPLICIT);

        joinRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!enterCodeEt.getText().toString().trim().isEmpty()){
                    enterRoom();
                }else {
                    enterCodeEt.setError("Enter Code");
                    enterCodeEt.getError();
                }
            }
        });

    }

    private void enterRoom() {
        DocumentReference room = db.collection("Rooms").document(enterCodeEt.getText().toString().trim());
        room.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    room.update("players", FieldValue.arrayUnion(Common.my_uid));
                    Intent i1 = new Intent(getApplicationContext(), Room.class);
                    i1.putExtra("roomId",enterCodeEt.getText().toString().trim());
                    startActivity(i1);
                    finish();
                }else {
                    Toast.makeText(EnterRoom.this, "This room does not exist.", Toast.LENGTH_SHORT).show();
                    enterCodeEt.setText("");
                }
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

}