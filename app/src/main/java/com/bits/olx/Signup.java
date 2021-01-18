package com.bits.olx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Signup extends AppCompatActivity {
    private FirebaseAuth myAuth;
    DatabaseReference reference;
    private Button joinus;
    private EditText inputuser, inputemail,inputpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        inputuser=(EditText)findViewById(R.id.username);
        inputemail=(EditText)findViewById(R.id.email);
        inputpassword=(EditText)findViewById(R.id.pass);
        joinus=(Button)findViewById(R.id.join);
        myAuth=FirebaseAuth.getInstance();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(myToolbar);

        joinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = inputuser.getText().toString().trim();
                String email = inputemail.getText().toString().trim();
                String password = inputpassword.getText().toString().trim();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(Signup.this, "All Fields are required!", Toast.LENGTH_SHORT).show();
                }
                else if (password.length() < 6){
                    Toast.makeText(Signup.this, "Password must be atleast 6 characters long!", Toast.LENGTH_SHORT).show();
                }
                else {
                    register(username, email, password);
                    Toast.makeText(Signup.this, "Info registered successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Signup.this, Login.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void register(String username, String email, String password){

        myAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = myAuth.getCurrentUser();
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("User").child(userid);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username", username);
                            hashMap.put("imageURL", "default");

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Intent intent = new Intent(Signup.this, Login.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(Signup.this, "You cant register with your entered email and password!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}