package com.example.android.pokeshop.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.pokeshop.data.ProductsContract.ProductsEntry;

/**
 * Manage products database.
 */
public class ProductsDbHelper extends SQLiteOpenHelper {

    // Database properties
    public static final String DATABASE_NAME = "products.db";
    public static final int DATABASE_VERSION = 1;

    /**
     * Database helper constructor.
     *
     * @param context Info about current app state.
     */
    public ProductsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase productsDatabase) {

        String COMMA = ", ";

        String CREATE_TABLE_SQL = "CREATE TABLE " + ProductsEntry.TABLE_NAME + " (" +
                ProductsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA +
                ProductsEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL" + COMMA +
                ProductsEntry.COLUMN_PRODUCT_PRICE + " INTEGER NOT NULL DEFAULT 0" + COMMA +
                ProductsEntry.COLUMN_PRODUCT_QUANTITY + " INTEGER NOT NULL DEFAULT 0" + COMMA +
                ProductsEntry.COLUMN_PRODUCT_SUPPLIER + " TEXT NOT NULL" + COMMA +
                ProductsEntry.COLUMN_PRODUCT_SUPPLIER_PHONE + " TEXT NOT NULL" + ");";

        Log.e("CREATE_TABLE_SQL: ", CREATE_TABLE_SQL); // To check statement correctness

        productsDatabase.execSQL(CREATE_TABLE_SQL); // Create products table
    }

    public void onUpgrade(SQLiteDatabase productsDatabase, int oldVersion, int newVersion) {

        String DELETE_TABLE_SQL = "DROP TABLE IF EXISTS " + ProductsEntry.TABLE_NAME;

        Log.e("DELETE_TABLE_SQL: ", DELETE_TABLE_SQL); // To check statement correctness

        productsDatabase.execSQL(DELETE_TABLE_SQL); // Delete products table
        onCreate(productsDatabase); // Create products table anew
    }
}
