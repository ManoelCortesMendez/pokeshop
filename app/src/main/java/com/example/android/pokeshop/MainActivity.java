package com.example.android.pokeshop;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Inflate activity contents
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Open editor on FAB click
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clickedView) {
                Intent openEditorIntent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(openEditorIntent);
            }
        });
    }
}
