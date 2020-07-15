package com.example.millionar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChangeQuestionActivity extends AppCompatActivity {

    private EditText editTextQuestion, editTextCorrectIndex;
    private EditText[] editTextAnswer = new EditText[4];
    private List<String> answers = new ArrayList<>();
    private String QuestionString,CorrectIndexString;
    private RadioButton easy;
    private RadioButton medium;
    private RadioButton hard;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_question);
        editTextQuestion = findViewById(R.id.editTextQuestion);
        editTextCorrectIndex = findViewById(R.id.editTextCorrectIndex);
        editTextAnswer[0] = findViewById(R.id.editTextAnswer0);
        editTextAnswer[1] = findViewById(R.id.editTextAnswer1);
        editTextAnswer[2] = findViewById(R.id.editTextAnswer2);
        editTextAnswer[3] = findViewById(R.id.editTextAnswer3);
        easy = (RadioButton) findViewById(R.id.radioButtonEasy);
        medium = (RadioButton) findViewById(R.id.radioButtonMedium);
        hard = (RadioButton) findViewById(R.id.radioButtonHard);
        database = FirebaseDatabase.getInstance();
    }

    public void addNewQuestionToFireBase()
    {
        if(easy.isChecked())
        {
            Questions questionDetails = new Questions(answers,String.valueOf(Integer.parseInt(CorrectIndexString)-1), QuestionString);
            myRef = database.getReference().child("quiz").child("easyQustions");
            myRef.push().setValue(questionDetails);
            Succeeded();

        }
        else if(medium.isChecked())
        {
            Questions questionDetails = new Questions(answers,CorrectIndexString, QuestionString);
            myRef = database.getReference().child("quiz").child("mediumQuestions");
            myRef.push().setValue(questionDetails);
            Succeeded();

        }
        else if(hard.isChecked())
        {
            Questions questionDetails = new Questions(answers,CorrectIndexString, QuestionString);
            myRef = database.getReference().child("quiz").child("hardQuestions");
            myRef.push().setValue(questionDetails);
            Succeeded();
        }
    }

    public void Succeeded()
    {
        Toast.makeText(this,"השאלה נוספה בהצלחה",Toast.LENGTH_SHORT).show();
    }

    public void getQuestionDetails(){
        QuestionString = editTextQuestion.getText().toString();
        CorrectIndexString = editTextCorrectIndex.getText().toString();

        answers.add(editTextAnswer[0].getText().toString());
        answers.add(editTextAnswer[1].getText().toString());
        answers.add(editTextAnswer[2].getText().toString());
        answers.add(editTextAnswer[3].getText().toString());

    }

    public void AddQuestion(View view) {

        getQuestionDetails();
        if(Integer.parseInt(CorrectIndexString) <= 4 && Integer.parseInt(CorrectIndexString) >= 1)
        {
            addNewQuestionToFireBase();
            goToManager();
        }
        else
        {
            Toast.makeText(this,"מספר התשובה לא תקין",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent intentChangeQuestion = new Intent(this,ManagerActivity.class);
        startActivity(intentChangeQuestion);
    }

    private void goToManager() {
        Intent intentMainActivity = new Intent(this,ManagerActivity.class);
        startActivity(intentMainActivity);
    }
}
