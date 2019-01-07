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

import com.example.android.pokeshop.data.ProductsContract.ProductsEntry;
import com.example.android.pokeshop.data.ProductsDbHelper;

public class MainActivity extends AppCompatActivity {

    /**
     * Products database helper.
     */
    private ProductsDbHelper productsDbHelper;

    /**
     * Set activity contents.
     *
     * @param savedInstanceState
     */
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
        displayDatabase();
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
            case R.id.action_add_dummy_product:
                addDummyProduct();
                displayDatabase();
                return true;
            case R.id.action_delete_all_products:
                // Do nothing for now
                return true;
        }

        return super.onOptionsItemSelected(menuItemClicked);
    }

    /**
     * Add dummy product (with predefined data) to the database.
     */
    private void addDummyProduct() {

        // Open writable database
        SQLiteDatabase productsDb = productsDbHelper.getWritableDatabase();

        // Build row
        ContentValues productContentValues = new ContentValues();
        productContentValues.put(ProductsEntry.COLUMN_PRODUCT_NAME, "aProduct");
        productContentValues.put(ProductsEntry.COLUMN_PRODUCT_PRICE, 100);
        productContentValues.put(ProductsEntry.COLUMN_PRODUCT_QUANTITY, 200);
        productContentValues.put(ProductsEntry.COLUMN_PRODUCT_SUPPLIER, "aSupplier");
        productContentValues.put(ProductsEntry.COLUMN_PRODUCT_SUPPLIER_PHONE, "12345");

        // Add row to database
        long newRowId = productsDb.insert(ProductsEntry.TABLE_NAME, null, productContentValues);

        Log.e("MainActivity", "New row ID: " + newRowId);
    }

    /**
     * Display products database.
     */
    private void displayDatabase() {

        // Open readable database
        SQLiteDatabase productsDb = productsDbHelper.getReadableDatabase();

        // Build projection (list of columns we're interested in)
        String[] projection = {
                ProductsEntry._ID,
                ProductsEntry.COLUMN_PRODUCT_NAME,
                ProductsEntry.COLUMN_PRODUCT_PRICE,
                ProductsEntry.COLUMN_PRODUCT_QUANTITY,
                ProductsEntry.COLUMN_PRODUCT_SUPPLIER,
                ProductsEntry.COLUMN_PRODUCT_SUPPLIER_PHONE
        };

        // Query database. Data is returned as a cursor.
        Cursor cursor = productsDb.query(
                ProductsEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        try {

            // Get products list view
            TextView productsListTextView = findViewById(R.id.products_list_text_view);

            // Display database row count
            productsListTextView.setText("Number of rows in products database: " + cursor.getCount() + "\n\n");

            // Display database headers
            productsListTextView.append(
                    ProductsEntry._ID + " --- " +
                    ProductsEntry.COLUMN_PRODUCT_NAME + " --- " +
                    ProductsEntry.COLUMN_PRODUCT_PRICE + " --- " +
                    ProductsEntry.COLUMN_PRODUCT_QUANTITY + " --- " +
                    ProductsEntry.COLUMN_PRODUCT_SUPPLIER + " --- " +
                    ProductsEntry.COLUMN_PRODUCT_SUPPLIER_PHONE + "\n");

            // Get column indices
            int idColumnIndex = cursor.getColumnIndex(ProductsEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(ProductsEntry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(ProductsEntry.COLUMN_PRODUCT_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(ProductsEntry.COLUMN_PRODUCT_QUANTITY);
            int supplierColumnIndex = cursor.getColumnIndex(ProductsEntry.COLUMN_PRODUCT_SUPPLIER);
            int supplierPhoneColumnIndex = cursor.getColumnIndex(ProductsEntry.COLUMN_PRODUCT_SUPPLIER_PHONE);

            // Loop over cursor rows
            while (cursor.moveToNext()) {

                // Get value of each field in current row
                int currentId = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String currentSupplier = cursor.getString(supplierColumnIndex);
                String currentSupplierPhone = cursor.getString(supplierPhoneColumnIndex);

                // Display row
                productsListTextView.append("\n" +
                        currentId + " --- " +
                        currentName + " --- " +
                        currentPrice + " --- " +
                        currentQuantity + " --- " +
                        currentSupplier + " --- " +
                        currentSupplierPhone);
            }
        } finally {
            cursor.close(); // To avoid memory leaks
        }
    }
}
