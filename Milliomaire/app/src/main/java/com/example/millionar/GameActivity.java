package com.example.millionar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference().child("quiz");
    private List<Questions> questions;
    private Questions question;
    private GamePlay gamePlay;
    private Button buttonAnswerOne;
    private Button buttonAnswerTwo;
    private Button buttonAnswerThree;
    private Button buttonAnswerFour;
    private Button buttonRetirement;
    private Button buttonTelephoneHelp;
    private Button buttonCrowdHelp;
    private Button button5050Help;
    private TextView questionView ;
    private TextView textViewMoney;
    private TextView textViewHelp;

    @Override
    protected  synchronized void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        buttonAnswerOne = (Button)findViewById(R.id.buttonAnswerOne);
        buttonAnswerTwo = (Button)findViewById(R.id.buttonAnswerTwo);
        buttonAnswerThree = (Button)findViewById(R.id.buttonAnswerThree);
        buttonAnswerFour = (Button)findViewById(R.id.buttonAnswerFour);
        buttonRetirement = (Button)findViewById(R.id.buttonRetirement);
        questionView = (TextView)findViewById(R.id.textViewQuestion);
        textViewMoney = (TextView)findViewById(R.id.textViewMoney);
        textViewHelp = (TextView)findViewById(R.id.textViewHelp);
        buttonTelephoneHelp = (Button)findViewById(R.id.buttonTelephoneHelp);
        buttonCrowdHelp = (Button)findViewById(R.id.buttonCrowdHelp);
        button5050Help = (Button)findViewById(R.id.button5050Help);
        start();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MenuActivity.class);
        this.startActivity(intent);
    }

    public synchronized void start()
    {
        gamePlay = GamePlay.getInstance(Config.getInstance().getUser().getName());
        if (gamePlay.getLevelInt() >= 5)
        {
            buttonRetirement.setVisibility(View.VISIBLE);
        }
        questions = new ArrayList<>();
        getQuestionsFromDataBase(gamePlay.getLevel());
        playMusic();
        getHelps();
    }

    private void playMusic() {
        if(gamePlay.getLevel() == "easyQustions"){
            MusicManger.getInstance().initalizeMediaPlayer(this,R.raw.easyquestionmusic);
            MusicManger.getInstance().startPlaying();
        }
        else if(gamePlay.getLevel() == "mediumQuestions"){
            MusicManger.getInstance().initalizeMediaPlayer(this,R.raw.midumqustionmusic);
            MusicManger.getInstance().startPlaying();
        }
        else if(gamePlay.getLevel() == "hardQuestions"){
            MusicManger.getInstance().initalizeMediaPlayer(this,R.raw.hardquestionmusic);
            MusicManger.getInstance().startPlaying();
        }
    }

    private boolean help(int precent)
    {
        Random rand = new Random();
        int chance = rand.nextInt(10);
        if (chance <= precent/10)
            return true;
        return false;
    }

    private void telephonehelp()
    {
        gamePlay.useHelp(1);
        Random rand = new Random();
        int chance = rand.nextInt(4);
        chance++;
        if (help(40))
        {
            int choose = Integer.parseInt(gamePlay.getQuestion().getCorrectIndex())+1;
            textViewHelp.setText("אני חושב שזה תשובה:" + choose);
        }
        else
        {
            textViewHelp.setText("אני חושב שזה תשובה:" + chance);
        }
    }

    private void crowdHelp()
    {
        gamePlay.useHelp(2);
        Random rand = new Random();
        int chance = rand.nextInt(4);
        chance++;
        if (help(70))
        {
            int choose = Integer.parseInt(gamePlay.getQuestion().getCorrectIndex())+1;
            textViewHelp.setText("רוב הקהל חושב שהתשובה הנכונה היא:" + choose);
        }
        else
        {
            textViewHelp.setText("רוב הקהל חושב שהתשובה הנכונה היא" + chance);
        }
    }

    private void fiftyfiftyHelp()
    {
        gamePlay.useHelp(0);
        Random rand = new Random();
        int chance = rand.nextInt(4);
        chance++;
        int choose = Integer.parseInt(gamePlay.getQuestion().getCorrectIndex())+1;
        while (chance == choose)
        {
            chance = rand.nextInt(4);
            chance++;
        }
        setVisibleOffAllButtons();
        if (chance == 1)
        {
            buttonAnswerOne.setVisibility(View.VISIBLE);
        }
        else if (chance == 2)
        {
            buttonAnswerTwo.setVisibility(View.VISIBLE);
        }
        else if (chance == 3)
        {
            buttonAnswerThree.setVisibility(View.VISIBLE);
        }
        else if (chance == 4)
        {
            buttonAnswerFour.setVisibility(View.VISIBLE);
        }
        if (choose == 1)
        {
            buttonAnswerOne.setVisibility(View.VISIBLE);
        }
        else if (choose == 2)
        {
            buttonAnswerTwo.setVisibility(View.VISIBLE);
        }
        else if (choose == 3)
        {
            buttonAnswerThree.setVisibility(View.VISIBLE);
        }
        else if (choose == 4)
        {
            buttonAnswerFour.setVisibility(View.VISIBLE);
        }
    }

    private void setVisibleOffAllButtons()
    {
        buttonAnswerOne.setVisibility(View.INVISIBLE);
        buttonAnswerTwo.setVisibility(View.INVISIBLE);
        buttonAnswerThree.setVisibility(View.INVISIBLE);
        buttonAnswerFour.setVisibility(View.INVISIBLE);
    }

    private void getHelps()
    {
        if (gamePlay.getHelpState()[0])
        {
            button5050Help.setBackgroundResource(R.drawable.fiftyfiftyx);
        }
        if (gamePlay.getHelpState()[1])
        {
            buttonTelephoneHelp.setBackgroundResource(R.drawable.telephonehelpx);
        }
        if (gamePlay.getHelpState()[2])
        {
            buttonCrowdHelp.setBackgroundResource(R.drawable.crowdhelpx);
        }
    }

    public void SetValue(Questions question)
    {
        textViewMoney.setText(gamePlay.getCurrentMoney());
        buttonAnswerOne.setText(question.getAnswers().get(0));
        buttonAnswerTwo.setText(question.getAnswers().get(1));
        buttonAnswerThree.setText(question.getAnswers().get(2));
        buttonAnswerFour.setText(question.getAnswers().get(3));
        questionView.setText(question.getQuestion());
    }

    private synchronized void getQuestionsFromDataBase(final String level) {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public synchronized void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.child(level).getChildren()) {
                    Questions question = snapshot.getValue(Questions.class);
                    //print(snapshot);
                    questions.add(question);
                }
                gamePlay.selectQuestion(questions);
                question = gamePlay.getQuestion();
                SetValue(question);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        myRef.addValueEventListener(postListener);
    }

    private void print(DataSnapshot snapshot)
    {
        Toast.makeText(this,snapshot.getValue(Questions.class).getQuestion(),Toast.LENGTH_LONG).show();
    }

    private void writeUserToDataBase(Questions question, String Level)
    {
        myRef = myRef.child(Level);
        DatabaseReference newPostRef = myRef.push();
        newPostRef.setValue(question);

    }

    private void setUnableAllButtons()
    {
        buttonAnswerOne.setEnabled(false);
        buttonAnswerTwo.setEnabled(false);
        buttonAnswerThree.setEnabled(false);
        buttonAnswerFour.setEnabled(false);
        buttonCrowdHelp.setEnabled(false);
        button5050Help.setEnabled(false);
        buttonTelephoneHelp.setEnabled(false);
        buttonRetirement.setEnabled(false);
    }

    private synchronized void SelectAnswer(final String index, final Button btn)
    {
        MusicManger.getInstance().stopPlaying();
        btn.setBackgroundResource(R.drawable.answerselected);
        MusicManger.getInstance().initalizeMediaPlayer(this,R.raw.finalanswer);
        MusicManger.getInstance().startPlaying();
        setUnableAllButtons();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (gamePlay.isRightAnswer(index))
                {
                    btn.setBackgroundResource(R.drawable.goodanswer);
                    Winning();
                    MusicManger.getInstance().initalizeMediaPlayer(GameActivity.this,R.raw.correctanswer);
                    MusicManger.getInstance().startPlaying();
                }
                else
                {
                    btn.setBackgroundResource(R.drawable.badanswer);
                    MusicManger.getInstance().initalizeMediaPlayer(GameActivity.this,R.raw.wronganswer);
                    MusicManger.getInstance().startPlaying();
                    if (gamePlay.getQuestion().getCorrectIndex().equals("0"))
                    {
                        buttonAnswerOne.setBackgroundResource(R.drawable.goodanswer);
                    }
                    else if (gamePlay.getQuestion().getCorrectIndex().equals("1"))
                    {
                        buttonAnswerTwo.setBackgroundResource(R.drawable.goodanswer);
                    }
                    else if (gamePlay.getQuestion().getCorrectIndex().equals("2"))
                    {
                        buttonAnswerThree.setBackgroundResource(R.drawable.goodanswer);
                    }
                    else if (gamePlay.getQuestion().getCorrectIndex().equals("3"))
                    {
                        buttonAnswerFour.setBackgroundResource(R.drawable.goodanswer);
                    }
                    Lossing();
                }
            }
        }, 2000);
    }


    private void Winning()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (gamePlay.getLevelInt() == 15)
                {
                    Config.getInstance().setLastScore(gamePlay.getCheckPointMoney().toString());
                    gamePlay.resetGame();
                    Intent intentScorePage = new Intent(GameActivity.this, WinActivity.class);
                    startActivity(intentScorePage);
                }
                else
                {
                    Intent intentScorePage = new Intent(GameActivity.this, MoneyTreeActivity.class);
                    intentScorePage.putExtra("index", gamePlay.getLevelInt()+1);
                    intentScorePage.putExtra("used", gamePlay.getHelpState());
                    startActivity(intentScorePage);
                }
            }
        }, 2000);
    }

    private void Lossing()
    {
        Config.getInstance().setLastScore(gamePlay.getCheckPointMoney().toString());
        gamePlay.resetGame();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(GameActivity.this,LossGameActivity.class);
                startActivity(intent);
            }
        }, 2000);
    }

    public void selectAnswerOne(View view) {
        SelectAnswer("0",buttonAnswerOne);
    }

    public void selectAnswerTwo(View view) {
        SelectAnswer("1",buttonAnswerTwo);
    }

    public void selectAnswerThree(View view) {
        SelectAnswer("2",buttonAnswerThree);

    }

    public void selectAnswerFour(View view) {
        SelectAnswer("3",buttonAnswerFour);
    }

    public void telephoneHelpClick(View view) {
        if (!gamePlay.getHelpState()[1])
        {
            telephonehelp();
            buttonTelephoneHelp.setBackgroundResource(R.drawable.telephonehelpx);
        }

    }

    public void crowdhelpClick(View view) {
        if (!gamePlay.getHelpState()[2])
        {
            crowdHelp();
            buttonCrowdHelp.setBackgroundResource(R.drawable.crowdhelpx);
        }
    }

    public void fiftyfiftyHelp(View view) {
        if (!gamePlay.getHelpState()[0])
        {
            fiftyfiftyHelp();
            button5050Help.setBackgroundResource(R.drawable.fiftyfiftyx);
        }
    }

    public void onClickRetriement(View view) {
        Config.getInstance().setLastScore(gamePlay.getLastMoney());
        gamePlay.resetGame();
        Intent intent = new Intent(GameActivity.this,LossGameActivity.class);
        startActivity(intent);
    }
}
