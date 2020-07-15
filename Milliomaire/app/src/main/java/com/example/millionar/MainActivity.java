package com.example.millionar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference().child("users");
    private List<User> users = new ArrayList<>();
    private User m_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getUsersFromDataBase();

    }

    private void getUsersFromDataBase(){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    users.add(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        myRef.addValueEventListener(postListener);
    }

    private void writeUserToDataBase(User user)
    {
        DatabaseReference newPostRef = myRef.push();
        newPostRef.setValue(user);
        Toast.makeText(this,"Create New User",Toast.LENGTH_LONG).show();
    }

    public void LoginButton(View view) {
        EditText emailText = (EditText)findViewById(R.id.editTextEmail);
        EditText passwordText = (EditText)findViewById(R.id.editTextPassword);
        EditText nameText = (EditText)findViewById(R.id.editTextName);
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String name = nameText.getText().toString();
        boolean status = false;
        if (email.length() == 0 || password.length() == 0 || name.length() == 0 )
        {
            Toast.makeText(this,"Fill All Information",Toast.LENGTH_LONG).show();
        }
        else if (!email.contains("@"))
        {
            Toast.makeText(this,"Bad Email",Toast.LENGTH_LONG).show();
        }
        else if (password.length() < 6)
        {
            Toast.makeText(this,"Password must be min 6 length",Toast.LENGTH_LONG).show();
        }
        else if (name.length() < 3)
        {
            Toast.makeText(this,"Name must be min 3 length",Toast.LENGTH_LONG).show();
        }
        else {
            for (User user: users) {
                if (user.getEmail().equalsIgnoreCase(email))
                {
                    if (user.getPassword().equalsIgnoreCase(password))
                    {
                        status = true;
                        m_user = user;
                        Toast.makeText(this,"Login Successful",Toast.LENGTH_LONG).show();
                        moveToMenuActivity();
                        break;
                    }
                    else
                    {
                        Toast.makeText(this,"Login Filed Bad Password",Toast.LENGTH_LONG).show();
                    }
                    status = true;
                }
            }
            if (!status)
            {
                m_user = new User(email,password,name,"client");
                writeUserToDataBase(m_user);
                moveToMenuActivity();
            }
        }
    }

    private void moveToMenuActivity()
    {
        Config.getInstance().setUser(m_user);
        Intent intent = new Intent(this, MenuActivity.class);
        this.startActivity(intent);

    }
}
