package com.example.android.pokeshop;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.pokeshop.data.ProductsContract;
import com.example.android.pokeshop.data.ProductsContract.ProductsEntry;
import com.example.android.pokeshop.data.ProductsDbHelper;

public class MainActivity extends AppCompatActivity {

    // Database helper
    private ProductsDbHelper productsDbHelper;

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

        // Instantiate database helper
        productsDbHelper = new ProductsDbHelper(this);

        // Print database row count
        summarizeDb();
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

    /**
     * Respond to menu item click.
     *
     * @param menuItemClicked Menu item clicked.
     * @return true to consume event here. (Otherwise, pass up the chain using 'super'.)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItemClicked) {
        switch (menuItemClicked.getItemId()) {
            case R.id.action_add_dummy_pokemon:
                addDummyPokemon();
                summarizeDb();
                return true;
            case R.id.action_delete_all_pokemon:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(menuItemClicked);
    }

    private void addDummyPokemon() {

        // Get database in write mode
        SQLiteDatabase productsDb = productsDbHelper.getWritableDatabase();

        // Build row
        ContentValues productContentValues = new ContentValues();
        productContentValues.put(ProductsEntry.COLUMN_PRODUCT_NAME, "Pokeball");
        productContentValues.put(ProductsEntry.COLUMN_PRODUCT_PRICE, 1);
        productContentValues.put(ProductsEntry.COLUMN_PRODUCT_QUANTITY, 2);
        productContentValues.put(ProductsEntry.COLUMN_PRODUCT_SUPPLIER, "PokeSupplier");
        productContentValues.put(ProductsEntry.COLUMN_PRODUCT_SUPPLIER_PHONE, "1234");

        // Add row to database
        long newRowId = productsDb.insert(ProductsEntry.TABLE_NAME, null, productContentValues);

        Log.e("MainActivity", "New row id: " + newRowId);
    }

    /**
     * Print database row count.
     */
    private void summarizeDb() {
        ProductsDbHelper productsDbHelper = new ProductsDbHelper(this);
        SQLiteDatabase productsDb = productsDbHelper.getReadableDatabase();
        Cursor cursor = productsDb.rawQuery("SELECT * FROM " + ProductsEntry.TABLE_NAME, null);

        try {
            TextView productsListTextView = findViewById(R.id.products_list_text_view);
            productsListTextView.setText("Number of rows in products database: " + cursor.getCount());
        } finally {
            cursor.close();
        }
    }
}
