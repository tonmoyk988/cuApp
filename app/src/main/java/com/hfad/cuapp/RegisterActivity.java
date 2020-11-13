package com.hfad.cuapp;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText name,pass,email;
    private Button reg;
    private FirebaseAuth registermAuth;
    private DatabaseReference dReff;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbar = (Toolbar)findViewById(R.id.registertoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("CU App");

        dialog=new ProgressDialog(this);

        registermAuth = FirebaseAuth.getInstance();
        //dReff= FirebaseDatabase.getInstance().getReference().child("Users");

        name=(EditText)findViewById(R.id.personname);
        pass=(EditText)findViewById(R.id.passwword);
        email=(EditText)findViewById(R.id.emailaddressofuser);
        reg=(Button)findViewById(R.id.registerbutton);


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                starrtregister();
            }
        });


    }

    private void starrtregister() {

        final String pname = name.getText().toString().trim();
        final String pemail = email.getText().toString().trim();
        final String ppass= pass.getText().toString().trim();


        if(!TextUtils.isEmpty(pname)&&!TextUtils.isEmpty(pemail)&&!TextUtils.isEmpty(ppass)){
            dialog.setMessage("Signing Up...");
            dialog.show();
            registermAuth.createUserWithEmailAndPassword(pemail,ppass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        //dReff= FirebaseDatabase.getInstance().getReference("Post");
                        //String user_id =(String) registermAuth.getCurrentUser().getUid();
                       // DatabaseReference databaseReference=dReff.child(user_id);
                        //databaseReference.child("name").setValue(pname);
                        dialog.dismiss();
                        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }

                    else {
                        Toast.makeText(RegisterActivity.this,"PassWord must be at least 6 characters Or Check Email",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }



                }
            });
        }
        else {
            Toast.makeText(RegisterActivity.this,"Please Fill All The Info",Toast.LENGTH_SHORT).show();
        }

    }
}
