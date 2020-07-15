package com.example.millionar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManagerActivity extends AppCompatActivity {

    private EditText editTextEmail,editTextUpdateEmail,editTextName;
    private String emailString, emailStringUpdate,nameString;
    FirebaseDatabase database;
    private DatabaseReference myRef;
    private List<User> usersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("users");
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextUpdateEmail = findViewById(R.id.editTextEmailUpdate);
        editTextName = findViewById(R.id.editTextName);
        getTableUsersDataBase();
    }

    public void OpenActivityAddQuestion(){
        Intent intentChangeQuestion = new Intent(this,ChangeQuestionActivity.class);
        startActivity(intentChangeQuestion);
    }

    @Override
    public void onBackPressed()
    {
        Intent intentChangeQuestion = new Intent(this,MenuActivity.class);
        startActivity(intentChangeQuestion);
    }

    public void OpenEdit(View view) {
        OpenActivityAddQuestion();
    }

    public void OpenActivityAddUser(){
        Intent intentAddUser = new Intent(this,AddNewUserActivity.class);
        startActivity(intentAddUser);
    }

    public void AddUser(View view) {
        OpenActivityAddUser();
    }

    public void DeleteUser(View view) {
        GetEmail();
        delete();
    }

    private void GetUpdateDetails()
    {
        emailStringUpdate = editTextUpdateEmail.getText().toString();
        nameString = editTextName.getText().toString();
    }

    private void GetEmail() {
        emailString = editTextEmail.getText().toString();
    }

    private void getTableUsersDataBase(){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User userInfo = snapshot.getValue(User.class);
                    userInfo.setKey(snapshot.getKey());
                    usersList.add(userInfo);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        myRef.addValueEventListener(postListener);
    }

    public void Update(View view)
    {
        GetUpdateDetails();
        for(User u : usersList)
        {
            if(u.getEmail().equals(emailStringUpdate))
            {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(u.getKey());
                User updateUser = new User(u.getEmail(),u.getPassword(),nameString,u.getLevel());
                databaseReference.setValue(updateUser);
                Toast.makeText(this,u.getName() + " is changed",Toast.LENGTH_LONG).show();
            }

        }


    }

    public void delete() {
        for(User u : usersList)
        {
            if(u.getEmail().equals(emailString))
            {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(u.getKey());
                databaseReference.removeValue();
                Toast.makeText(this,u.getName() + " is deleted",Toast.LENGTH_LONG).show();
            }

        }
    }
}