package com.example.android.pokeshop.data;

import android.provider.BaseColumns;

/**
 * Products database schema.
 */
public class ProductsContract {

    /**
     * Products table constants.
     */
    public final static class ProductsEntry implements BaseColumns {

        // Table name
        public final static String TABLE_NAME = "products";

        // Table constants
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_PRODUCT_NAME = "name";
        public final static String COLUMN_PRODUCT_PRICE = "price";
        public final static String COLUMN_PRODUCT_QUANTITY = "quantity";
        public final static String COLUMN_PRODUCT_SUPPLIER = "supplier";
        public final static String COLUMN_PRODUCT_SUPPLIER_PHONE = "supplier_phone";
    }
}
