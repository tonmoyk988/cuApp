package com.hfad.cuapp;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hfad.cuapp.Firebase.authentication.firebaseauthui;

import java.util.Arrays;
import java.util.List;


public class loginActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private DatabaseReference dReff;
    private EditText logmail,logpass;
    private Button loginbut,newRegBut,complete;
    private ProgressDialog prg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //toolbar = (Toolbar)findViewById(R.id.logipagenbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("CU App");

        //dReff= FirebaseDatabase.getInstance().getReference().child("Users");
        //Offline capability
        //dReff.keepSynced(true);
        mAuth=FirebaseAuth.getInstance();
        prg=new ProgressDialog(this);

        


        //logmail = (EditText) findViewById(R.id.loginemail);
        //logpass=(EditText)findViewById(R.id.loginpassword);
        //loginbut=(Button)findViewById(R.id.loginButton);
        //newRegBut=(Button)findViewById(R.id.needanaccount);
        /*complete =(Button) findViewById(R.id.complete);

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoginProc();
            }
        });
        newRegBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginActivity.this, firebaseauthui.class));
                /*Intent intent=new Intent(loginActivity.this,RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });*/

    }

    private void startLoginProc() {
        String email = logmail.getText().toString().trim();
        String passa =logpass.getText().toString().trim();
        //startActivity(new Intent(loginActivity.this,MainActivity.class));
        finish();
/*
        if(!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(passa)){
            prg.setMessage("Logging in...");
            prg.show();
            mAuth.signInWithEmailAndPassword(email,passa).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //For User More info.
                    //Not need right Now
                    if(task.isSuccessful()){
                        final String user_id=mAuth.getCurrentUser().getUid();

                        Intent intent=new Intent(loginActivity.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                        dReff.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.hasChild(user_id)){
                                    Intent intent=new Intent(loginActivity.this,MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                                else{
                                    Intent intent=new Intent(loginActivity.this,MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        prg.dismiss();
                    }
                    else {
                        Toast.makeText(loginActivity.this,"Error LogIn!",Toast.LENGTH_SHORT).show();
                        prg.dismiss();
                    }*//*

                }
            });
        }*/

    }
}
