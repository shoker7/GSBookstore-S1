package com.android.chrishsu.gsbookstore.data;

import android.provider.BaseColumns;

public final class BookContract {

    private BookContract() {}

    public static final class BookEntry implements BaseColumns {

        public final static String TABLE_NAME = "books";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_PRODUCT_NAME = "name";
        public final static String COLUMN_PRICE = "price";
        public final static String COLUMN_QTY = "qty";
        public final static String COLUMN_SUPPLIER_NAME = "supplier";
        public final static String COLUMN_SUPPLIER_PHONE = "supplierphone";

    }

}
