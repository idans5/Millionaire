package com.example.millionar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

public class MoneyTreeActivity extends AppCompatActivity {

    private int index = 1;
    private boolean[] StateOfHelps = {false,false,false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MusicManger.getInstance().stopPlaying();
        getVars();
        playSound();
        setContentView(R.layout.activity_money_tree);
        paintTableRow();
        checkUsedHelp();
        startAndStopWindow();
    }

    @Override
    public void onBackPressed()
    {

    }

    private void checkUsedHelp() {
        String imageTag;
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.imageLyout);

        for(int i=0;i<3;i++)
        {
            imageTag = "imageX" + String.valueOf(i+1);
            ImageView imageView = linearLayout.findViewWithTag(imageTag);
            if(StateOfHelps[i])
            {
                imageView.setVisibility(View.VISIBLE);
            }
        }

    }

    private void startAndStopWindow(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(MoneyTreeActivity.this, GameActivity.class);
                MoneyTreeActivity.this.startActivity(mainIntent);
                MoneyTreeActivity.this.finish();
            }
        }, 5000);
    }

    private void playSound(){
        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.moneytreesound);
        mediaPlayer.start();
    }

    private void getVars(){
        Bundle getIndexInTree = getIntent().getExtras();
        if (getIndexInTree != null) {
            index = getIndexInTree.getInt("index");
        }
        Bundle getStateOfHelps = getIntent().getExtras();
        if (StateOfHelps != null) {
            StateOfHelps = getStateOfHelps.getBooleanArray("used");
        }
    }

    private void paintTableRow() {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.moneyTree);
        TableRow falseTableRow;
        String nameOfTableRow = "TableRow" + String.valueOf(index);
        falseTableRow = (TableRow) tableLayout.findViewWithTag(nameOfTableRow);
        falseTableRow.setVisibility(View.VISIBLE);
        falseTableRow.startAnimation(flashBackground());
    }

    private Animation flashBackground() {
        Animation animation = new AlphaAnimation(1, 0);
        animation.setDuration(300);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(8);
        animation.setRepeatMode(Animation.REVERSE);

        return animation;
    }
}