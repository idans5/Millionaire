package com.example.millionar;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ScoreTableActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference().child("scores");
    private TableLayout tableLayout;
    private ArrayList<ScoreTableModel> scoreList;
    private boolean flagFirstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_table);

        tableLayout = (TableLayout) findViewById(R.id.TableLayout);
        getTableScoreDataBase();
    }

    private void getTableScoreDataBase(){
        ValueEventListener tableListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!flagFirstTime){
                    clearTableView();
                }
                flagFirstTime = false;
                scoreList = new ArrayList<ScoreTableModel>();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    ScoreTableModel scoreRow = dsp.getValue(ScoreTableModel.class);
                    scoreList.add(scoreRow);
                }
                sortList();
                setTableInView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        myRef.addValueEventListener(tableListener);
    }

    private void sortList(){

        Comparator<ScoreTableModel> compareByScore = new Comparator<ScoreTableModel>() {
            @Override
            public int compare(ScoreTableModel o1, ScoreTableModel o2) {
                return Integer.valueOf(o2.getScore().substring(0,o2.getScore().length()-1))
                        .compareTo(Integer.valueOf(o1.getScore().substring(0,o1.getScore().length()-1)));
            }
        };
        Collections.sort(scoreList, compareByScore);
    }

    public void setTableInView(){

        for(int i = 0; i < scoreList.size() && i < 15; i++)
        {
            TableRow tableRow = new TableRow(this);
            setTableRowProperty(tableRow, scoreList.get(i),i);
            tableLayout.addView(tableRow);
        }
    }

    public void setTableRowProperty(TableRow tableRow, ScoreTableModel scoreTableModel, int index){
        if(index % 2 == 0)
        {
            tableRow.setBackgroundColor(Color.parseColor("#7F87B6"));
        }
        else{
            tableRow.setBackgroundColor(Color.parseColor("#B5BADA"));
        }

        tableRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
        tableRow.setOrientation(LinearLayout.VERTICAL);
        tableRow.setPaddingRelative(10,0,0,0);
        tableRow.setPadding(5,5,5,5);
        TextView scoreOfUser = new TextView(this);
        scoreOfUser.setText(scoreTableModel.getScore());
        setTextViewProperty(scoreOfUser);
        tableRow.addView(scoreOfUser);
        TextView nameOfUser = new TextView(this);
        nameOfUser.setText(scoreTableModel.getName());
        setTextViewProperty(nameOfUser);
        tableRow.addView(nameOfUser);
    }

    public void setTextViewProperty(TextView textView){
        textView.setLayoutParams(new TableRow.LayoutParams(
                0,TableRow.LayoutParams.WRAP_CONTENT,1.0f));
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER);
        textView.setTextDirection(View.TEXT_DIRECTION_RTL);
        textView.setTextSize(18);
    }

    public void clearTableView(){
        tableLayout.removeViewsInLayout(1,scoreList.size());
    }
}
