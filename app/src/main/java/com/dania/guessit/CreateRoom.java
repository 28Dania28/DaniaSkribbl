package com.dania.guessit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;

public class CreateRoom extends AppCompatActivity{

    private Spinner roundsSpinner, timeSpinner;
    private Button confirmBtn;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        roundsSpinner = findViewById(R.id.roundsSpinner);
        timeSpinner = findViewById(R.id.timeSpinner);
        confirmBtn = findViewById(R.id.confirmBtn);
        ArrayAdapter<CharSequence> roundsAdapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.rounds_number, android.R.layout.simple_spinner_item);
        roundsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roundsSpinner.setAdapter(roundsAdapter);
        roundsSpinner.setSelection(1);

        ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.draw_time, android.R.layout.simple_spinner_item);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(timeAdapter);
        timeSpinner.setSelection(15);
        db = FirebaseFirestore.getInstance();

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference room = db.collection("Rooms").document();
                String room_id = room.getId();
                HashMap<String, Object> roomData = new HashMap<>();
                roomData.put("room_id",room_id);
                roomData.put("admin",Common.my_uid);
                roomData.put("rounds",roundsSpinner.getSelectedItem());
                roomData.put("timer",timeSpinner.getSelectedItem());
                roomData.put("players", Arrays.asList(Common.my_uid));
                room.set(roomData);
                Intent i1 = new Intent(getApplicationContext(), Room.class);
                i1.putExtra("roomId",room_id);
                startActivity(i1);
                finish();
            }
        });

    }

}