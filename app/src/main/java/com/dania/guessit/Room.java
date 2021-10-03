package com.dania.guessit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class Room extends AppCompatActivity {

    private CircleImageView adminIv;
    private TextView adminTv, startTv, roomIdTv, timerTv, roundsTv;
    private String room_id;
    private ImageView copyIv, backIv, shareIv;
    private FirebaseFirestore db;
    private Dialog dialogLeaveRoom;
    private Button dlrLeaveRoomBtn;
    private Button dlrCancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        adminIv = findViewById(R.id.adminIv);
        adminTv = findViewById(R.id.adminTv);
        startTv = findViewById(R.id.startTv);
        roomIdTv = findViewById(R.id.roomIdTv);
        copyIv = findViewById(R.id.copyIv);
        backIv = findViewById(R.id.backIv);
        shareIv = findViewById(R.id.shareIv);
        timerTv = findViewById(R.id.timerTv);
        roundsTv = findViewById(R.id.roundsTv);
        dialogLeaveRoom = new Dialog(this);
        dialogLeaveRoom.setContentView(R.layout.dialog_leave_room);
        dialogLeaveRoom.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogLeaveRoom.setCanceledOnTouchOutside(false);
        dlrLeaveRoomBtn = dialogLeaveRoom.findViewById(R.id.dlrLeaveRoomBtn);
        dlrCancelBtn = dialogLeaveRoom.findViewById(R.id.dlrCancelBtn);
        db = FirebaseFirestore.getInstance();


        if (Common.my_name!=null){

        }else {
            finish();
        }

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b!=null){
            room_id = b.getString("roomId");
            roomIdTv.setText(room_id);
        }

        setAdmin();

        startTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Playground.class);
                startActivity(i);
            }
        });

        copyIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("roomId",roomIdTv.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(Room.this, "Room Id copied.", Toast.LENGTH_SHORT).show();
            }
        });

        shareIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/plain");
                //intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.share_subject));
                intent.putExtra(android.content.Intent.EXTRA_TEXT, room_id);
                startActivity(Intent.createChooser(intent,room_id));
            }
        });

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dlrLeaveRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.admin.equals(Common.my_uid)){
                    changeAdmin();
                    finish();
                }else {
                    finish();
                }
            }
        });

        dlrCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLeaveRoom.dismiss();
            }
        });

    }

    private void changeAdmin() {

    }

    private void setAdmin() {
        DocumentReference room = db.collection("Rooms").document(room_id);
        room.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String rounds = documentSnapshot.get("rounds").toString();
                String timer = documentSnapshot.get("timer").toString();
                timerTv.setText(timer);
                roundsTv.setText("Rounds : "+rounds);
                String admin_uid = documentSnapshot.get("admin").toString();
                Common.admin = admin_uid;
                if (!admin_uid.equals(Common.my_uid)){
                    startTv.setEnabled(false);
                    startTv.setTextColor(getResources().getColor(R.color.fun_text2));
                }else {
                    startTv.setEnabled(true);
                    startTv.setTextColor(getResources().getColor(R.color.fun_anahata));
                }
                DocumentReference adminRef = db.collection("Users").document(admin_uid);
                adminRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String adminName = documentSnapshot.get("game_name").toString();
                        String adminAvatar = documentSnapshot.get("avatar").toString();
                        adminTv.setText(adminName);
                        getAvatar(Integer.parseInt(adminAvatar));
                    }
                });
            }
        });

        /*room.collection("admin").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots,
                                @Nullable FirebaseFirestoreException e) {
                for (DocumentChange dc : snapshots.getDocumentChanges()){
                    if (dc.getType().equals(DocumentChange.Type.MODIFIED)){
                        String admin_uid = dc.getDocument().getData().toString();
                        Common.admin = admin_uid;
                        if (!admin_uid.equals(Common.my_uid)){
                            startTv.setEnabled(false);
                            startTv.setTextColor(getResources().getColor(R.color.fun_text2));
                        }else {
                            startTv.setEnabled(true);
                            startTv.setTextColor(getResources().getColor(R.color.fun_anahata));
                        }

                    }
                }
            }
        });

         */
    }

    @Override
    public void onBackPressed() {
        dialogLeaveRoom.show();
    }

    private void getAvatar(int adminAvatar) {
        switch (adminAvatar) {
            case 1:
                adminIv.setImageResource(R.drawable.avatar_1);
                break;
            case 2:
                adminIv.setImageResource(R.drawable.avatar_2);
                break;
            case 3:
                adminIv.setImageResource(R.drawable.avatar_3);
                break;
            case 4:
                adminIv.setImageResource(R.drawable.avatar_4);
                break;
            case 5:
                adminIv.setImageResource(R.drawable.avatar_5);
                break;
            case 6:
                adminIv.setImageResource(R.drawable.avatar_6);
                break;
            case 7:
                adminIv.setImageResource(R.drawable.avatar_7);
                break;
            case 8:
                adminIv.setImageResource(R.drawable.avatar_8);
                break;
            case 9:
                adminIv.setImageResource(R.drawable.avatar_9);
                break;
            case 10:
                adminIv.setImageResource(R.drawable.avatar_10);
                break;
            case 11:
                adminIv.setImageResource(R.drawable.avatar_11);
                break;
            case 12:
                adminIv.setImageResource(R.drawable.avatar_12);
                break;
            case 13:
                adminIv.setImageResource(R.drawable.avatar_13);
                break;
            case 14:
                adminIv.setImageResource(R.drawable.avatar_14);
                break;
            case 15:
                adminIv.setImageResource(R.drawable.avatar_15);
                break;
            case 16:
                adminIv.setImageResource(R.drawable.avatar_16);
                break;
            default:
                adminIv.setImageResource(R.drawable.avatar_1);
                break;
        }
    }

}

//https://lottiefiles.com/53943-gamer
//https://lottiefiles.com/59772-game-loader
//https://lottiefiles.com/24085-playing-ukelele-at-home
//https://lottiefiles.com/61224-beer-can
//https://lottiefiles.com/77-im-thirsty
//https://lottiefiles.com/19669-coffee-meditation
//https://lottiefiles.com/18409-breathe

//backgrounds
//https://lottiefiles.com/37384-background-animation-of-love-sign
//https://lottiefiles.com/9878-background-full-screen


//Firebase
//https://youtu.be/741QCymuky4
/*
 <com.airbnb.lottie.LottieAnimationView
        android:layout_width="180dp"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        app:lottie_autoPlay="true"
        app:lottie_loop="true"
        app:lottie_rawRes="@raw/gamer"/>

 */