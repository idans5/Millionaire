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

public class AddNewUserActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextName,editTextPassword;
    private String EmailString , NameString ,PasswordString;
    private RadioButton admin,client;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_user);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextName = findViewById(R.id.editTextName);
        editTextPassword = findViewById(R.id.editTextPassword);

        admin = (RadioButton) findViewById(R.id.radioButtonAdmin);
        client = (RadioButton) findViewById(R.id.radioButtonClient);
        database = FirebaseDatabase.getInstance();
    }

    public void getUserDetails()
    {
        EmailString = editTextEmail.getText().toString();
        NameString = editTextName.getText().toString();
        PasswordString = editTextPassword.getText().toString();
    }

    public void AddUserToDatabase(View view)
    {
        getUserDetails();
        if(PasswordString.length() < 6 || NameString.length() < 3 || !EmailString.contains("@"))
        {
            Toast.makeText(this,"נסה שוב בבקשה",Toast.LENGTH_LONG).show();
        }
        else
        {
            addNewUsersToFireBase();
            goToManager();
        }

    }

    private void goToManager() {
        Intent intentMainActivity = new Intent(this,ManagerActivity.class);
        startActivity(intentMainActivity);
    }

    @Override
    public void onBackPressed()
    {
        Intent intentChangeQuestion = new Intent(this,ManagerActivity.class);
        startActivity(intentChangeQuestion);
    }

    private void addNewUsersToFireBase() {

        if(admin.isChecked())
        {
            User userDetails = new User(EmailString, PasswordString ,NameString,"Admin");
            myRef = database.getReference().child("users");
            myRef.push().setValue(userDetails);
            Succeeded();
        }

        else if(client.isChecked())
        {
            User userDetails = new User(EmailString, PasswordString ,NameString,"client");
            myRef = database.getReference().child("users");
            myRef.push().setValue(userDetails);
            Succeeded();
        }
    }

    public void Succeeded()
    {
        Toast.makeText(this,"המשתמש נוסף בהצלחה",Toast.LENGTH_SHORT).show();
    }
}
