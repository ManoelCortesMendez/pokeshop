package com.example.android.pokeshop;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.pokeshop.data.ProductsContract.ProductsEntry;
import com.example.android.pokeshop.data.ProductsDbHelper;

public class EditorActivity extends AppCompatActivity {

    // Input fields
    private EditText nameEditText;
    private EditText priceEditText;
    private EditText quantityEditText;
    private EditText supplierEditText;
    private EditText supplierPhoneEditText;

    /**
     * Inflate activity contents.
     *
     * @param savedInstanceState Previous state of activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Set activity layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Get input views
        nameEditText = findViewById(R.id.name_edit_text_view);
        priceEditText = findViewById(R.id.price_edit_text_view);
        quantityEditText = findViewById(R.id.quantity_edit_text_view);
        supplierEditText = findViewById(R.id.supplier_edit_text_view);
        supplierPhoneEditText = findViewById(R.id.supplier_phone_edit_text_view);
    }

    /**
     * Inflate menu content.
     *
     * @param menu Menu to inflate.
     * @return true to add menu to app bar.
     */
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate menu
        getMenuInflater().inflate(R.menu.editor_menu, menu);
        return true; // To add menu to app bar
    }

    /**
     * Respond to menu item click.
     *
     * @param menuItemClicked Menu item clicked.
     * @return true to consume event here. (Otherwise, pass up the chain using 'super'.)
     */
    public boolean onOptionsItemSelected(MenuItem menuItemClicked) {
        switch(menuItemClicked.getItemId()) {
            case R.id.action_save:
                addProduct();
                finish();
                return true;
        }

        return super.onOptionsItemSelected(menuItemClicked);
    }

    /**
     * Add custom product to database.
     */
    private void addProduct() {

        // Instantiate database helper
        ProductsDbHelper productsDbHelper = new ProductsDbHelper(this);

        // Get writable database
        SQLiteDatabase productsDatabase = productsDbHelper.getWritableDatabase();

        // Get user inputted values
        String name = nameEditText.getText().toString().trim();
        int price = Integer.parseInt(priceEditText.getText().toString().trim());
        int quantity = Integer.parseInt(quantityEditText.getText().toString().trim());
        String supplier = supplierEditText.getText().toString().trim();
        String supplierPhone = supplierPhoneEditText.getText().toString().trim();

        // Build row
        ContentValues productContentValues = new ContentValues();
        productContentValues.put(ProductsEntry.COLUMN_PRODUCT_NAME, name);
        productContentValues.put(ProductsEntry.COLUMN_PRODUCT_PRICE, price);
        productContentValues.put(ProductsEntry.COLUMN_PRODUCT_QUANTITY, quantity);
        productContentValues.put(ProductsEntry.COLUMN_PRODUCT_SUPPLIER, supplier);
        productContentValues.put(ProductsEntry.COLUMN_PRODUCT_SUPPLIER_PHONE, supplierPhone);

        // Add row to database
        long newRowId = productsDatabase.insert(ProductsEntry.TABLE_NAME, null, productContentValues);

        // Display toast success or failure message
        String toastMessage = (newRowId == -1) ? "Error saving product" : "Product saved with ID: " + newRowId;
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
    }
}
