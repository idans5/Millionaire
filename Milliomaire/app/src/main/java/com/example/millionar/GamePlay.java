package com.example.millionar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class GamePlay {
    private static GamePlay instance = null;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference().child("quiz");
    private String NickName = null;
    private String CurrentMoney = null;
    private String SelectedAnswer = null ;
    private String CheckPointMoney = "0$";
    private boolean[] helpState = {false,false,false};
    private HashSet<Integer> SelecetedQuestions = new HashSet<>();
    private int Level = 0;
    private Questions question;
    private final String[] MoneyValue = {"500$", "1000$", "2000$", "3000$",
            "5000$", "7500$", "10,000$", "20,000$", "30,000$", "50,000$", "75,000$",
            "150,000$", "250,000$", "500,000$", "1MILLION$"};

    private GamePlay(String nickName) {
        NickName = nickName;
        CurrentMoney = MoneyValue[Level];
        SelecetedQuestions = new HashSet<>();
    }

    public String getCheckPointMoney() {
        return CheckPointMoney;
    }

    public String getLastMoney()
    {
        return MoneyValue[Level-1];
    }

    public boolean[] getHelpState()
    {
        return helpState;
    }

    public void useHelp(int index)
    {
        helpState[index] = true;
    }

    public String getCurrentMoney() {
        return CurrentMoney;
    }

    public static GamePlay getInstance(String NickName)
    {
        if (instance == null)
            instance = new GamePlay(NickName);

        return instance;
    }

    public String getLevel() {
        if (Level <= 4)
            return "easyQustions";
        else if (Level <= 9)
            return "mediumQuestions";
        else
            return "hardQuestions";
    }

    public int getLevelInt()
    {
        return Level;
    }

    public void resetGame()
    {
        Level = 0;
        CurrentMoney = MoneyValue[Level];
        CheckPointMoney = "0$";
        SelectedAnswer = null;
        helpState[0]=false;
        helpState[1]=false;
        helpState[2]=false;
    }

    public void selectQuestion(List<Questions> questions)
    {
        Random rand = new Random();
        int index = rand.nextInt(questions.size());
        while (SelecetedQuestions.contains(index))
        {
            index = rand.nextInt(questions.size());
        }
        SelecetedQuestions.add(index);
        question = questions.get(index);
    }

    public Questions getQuestion() {
        return question;
    }

    public boolean isRightAnswer(String CorrectIndex)
    {
        if (question.getCorrectIndex().equals(CorrectIndex))
        {
            Level++;
            CurrentMoney = MoneyValue[Level];
            checkPoint();
            return true;
        }

        return false;
    }

    public String GetCorrectIndex()
    {
        return question.getCorrectIndex();
    }

    private void checkPoint()
    {
        if ((Level == 5) || (Level == 10) || (Level == 15))
        {
            SelecetedQuestions = new HashSet<>();
            CheckPointMoney = MoneyValue[Level-1];
        }
    }

}