package com.hfad.cuapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hfad.cuapp.viewModel.Global_variable;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;


public class new_user_setup extends AppCompatActivity {

    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("User_info");

    private Button btn ;
    private TextView tv;
    private Spinner spinner;
    private EditText batch;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_setup);

        toolbar = (Toolbar)findViewById(R.id.new_user_setup);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Welcome to CuApp !");

        mAuth=FirebaseAuth.getInstance();

        tv =(TextView)findViewById(R.id.new_user_n);
        String uname="Welcome "+mAuth.getCurrentUser().getDisplayName()+" :)";
        tv.setText(uname);

        spinner =(Spinner)findViewById(R.id.batchspinner);

        batch=(EditText)findViewById(R.id.batch);
        batch.setVisibility(View.GONE);


        //populateSpinner with batch name
        populayeBatchSpinner();

        //DataSnapshot dataSnapshot = database.getReference().child("Batch_List");

        btn=(Button)findViewById(R.id.complete);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Test
               // DatabaseReference batchs=database.getReference("Batch_List");
                //batchs.push().child("batch").setValue(batch.getText().toString().trim());

                String ubatch = spinner.getSelectedItem().toString();
                databaseReference.child(mAuth.getCurrentUser().getUid()).child("batch").setValue(ubatch);
                databaseReference.child(mAuth.getCurrentUser().getUid()).child("name").setValue(mAuth.getCurrentUser().getDisplayName());
                Global_variable.batchForpost=ubatch;
                startLoginProc();
/*
                if(!TextUtils.isEmpty(batch.getText().toString().trim())){
                    String ubatch = batch.getText().toString().trim();
                    databaseReference.child(mAuth.getCurrentUser().getUid()).child("batch").setValue(ubatch);
                    databaseReference.child(mAuth.getCurrentUser().getUid()).child("name").setValue(mAuth.getCurrentUser().getDisplayName());
                    startLoginProc();
                }
                else {
                    Toast.makeText(new_user_setup.this,"Complete the fields !",Toast.LENGTH_SHORT).show();
                }*/
            }
        });
    }

    private void populayeBatchSpinner() {

        DatabaseReference batchs=database.getReference("Batch_List");
        batchs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> allBatch = new ArrayList<String>();
                for (DataSnapshot batchSnapshot: dataSnapshot.getChildren()) {
                    String batchName = batchSnapshot.child("batch").getValue(String.class);
                    allBatch.add(batchName);
                }
                ArrayAdapter<String> batchAdapter =
                        new ArrayAdapter<String>(new_user_setup.this, android.R.layout.simple_spinner_item, allBatch);
                spinner =(Spinner)findViewById(R.id.batchspinner);
                spinner.setAdapter(batchAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    private void startLoginProc() {

        startActivity(new Intent(this,MainActivity.class));

        finish();

    }
}
