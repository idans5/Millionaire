package com.example.millionar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        playSound();
    }

    private void playSound(){
        MusicManger.getInstance().initalizeMediaPlayer(this, R.raw.opening);
        MusicManger.getInstance().startPlaying();
    }

    private void moveToScoreTableActivity()
    {
        Intent intent = new Intent(this, ScoreTableActivity.class);
        this.startActivity(intent);
    }

    public void openScorePageActivity(){
        boolean[] helpState = {false,false,false};
        Intent intentScorePage = new Intent(this, MoneyTreeActivity.class);
        intentScorePage.putExtra("index",1);
        intentScorePage.putExtra("used", helpState);
        startActivity(intentScorePage);
    }

    public void startGame(View view) {
        MusicManger.getInstance().stopPlaying();
        openScorePageActivity();

    }

    public void scoreTableClick(View view) {
        MusicManger.getInstance().stopPlaying();
        moveToScoreTableActivity();
    }

    public void OpenManagment(View view) {
        if(Config.getInstance().getUser().getLevel().equals("Admin"))
        {
            Intent intent = new Intent(this, ManagerActivity.class);
            this.startActivity(intent);
        }
        else{
            Toast.makeText(this,"אתה לא מנהל",Toast.LENGTH_LONG).show();
        }

    }
}