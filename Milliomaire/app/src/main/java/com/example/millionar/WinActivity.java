package com.example.millionar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WinActivity extends AppCompatActivity {
    private TextView UserName;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
        database = FirebaseDatabase.getInstance();
        SetDetails();
        UpdateTableScore();
    }

    private void SetDetails() {
        UserName = (TextView)findViewById(R.id.textViewNameWinner);
        UserName.setText(Config.getInstance().getUser().getName());
    }

    private void UpdateTableScore() {
        ScoreTableModel userDetails = new ScoreTableModel(UserName.getText().toString(), Config.getInstance().getLastScore());
        myRef = database.getReference().child("scores");
        myRef.push().setValue(userDetails);
    }

    public void NewGame(View view) {
        Intent intentMainActivity = new Intent(this,MoneyTreeActivity.class);
        startActivity(intentMainActivity);
    }

    public void Exit(View view) {
        Intent intentMainActivity = new Intent(this,MainActivity.class);
        startActivity(intentMainActivity);
    }

    public void GoToTheTableScore(View view) {
        Intent intentMainActivity = new Intent(this,ScoreTableActivity.class);
        startActivity(intentMainActivity);
    }
}
