package com.example.millionar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LossGameActivity extends AppCompatActivity {

    private TextView TotalMoney,UserName;
    private GamePlay gamePlay;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loss_game);
        database = FirebaseDatabase.getInstance();
        gamePlay = GamePlay.getInstance(Config.getInstance().getUser().getName());
        SetDetails();
        UpdateTableScore();
    }

    private void SetDetails() {
        TotalMoney = (TextView)findViewById(R.id.textViewSumLose);
        UserName = (TextView)findViewById(R.id.textViewName);
        TotalMoney.setText(Config.getInstance().getLastScore());
        UserName.setText(Config.getInstance().getUser().getName());
    }

    @Override
    public void onBackPressed()
    {

    }

    private void UpdateTableScore() {
        ScoreTableModel userDetails = new ScoreTableModel(UserName.getText().toString(), TotalMoney.getText().toString());
        myRef = database.getReference().child("scores");
        myRef.push().setValue(userDetails);
    }

    public void NewGame(View view) {
        Intent intentMainActivity = new Intent(this,MoneyTreeActivity.class);
        gamePlay.resetGame();
        intentMainActivity.putExtra("index", gamePlay.getLevelInt()+1);
        intentMainActivity.putExtra("used", gamePlay.getHelpState());
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
