package com.example.android.pokeshop;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Inflate activity content
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Open editor on FAB click
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            /**
             * Open editor on FAB click.
             *
             * @param clickedView View clicked.
             */
            @Override
            public void onClick(View clickedView) {
                Intent openEditorIntent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(openEditorIntent);
            }
        });
    }

    /**
     * Inflate options menu.
     *
     * @param menu Activity's menu.
     * @return true to add menu to app bar.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true; // To add menu to app bar
    }
}
