package com.hfad.cuapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

public class routinr_Class extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routinr__class);

        toolbar=(Toolbar)findViewById(R.id.classroutinebar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Class Routine");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
