package com.android.chrishsu.gsbookstore;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.chrishsu.gsbookstore.data.BookDbHelper;
import com.android.chrishsu.gsbookstore.data.BookContract.BookEntry;

public class CatalogActivity extends AppCompatActivity {

    private BookDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        mDbHelper = new BookDbHelper(this);

        insertBooks();

    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDataBaseInfo();
    }

    private void insertBooks() {
        // Insert first book
        insertBook("The Ruin: A Novel",
                13,
                15,
                "Amazon",
                "800-280-4331");

        // Insert second book
        insertBook("Indianapolis",
                21,
                20,
                "Brooks Books",
                "800-220-1111");

        // Insert third book
        insertBook("What We Were Promised",
                22,
                10,
                "BN Online",
                "866-982-3299");
    }

    private void insertBook(String name,
                            int price,
                            int qty,
                            String supplier,
                            String suppplierPhone) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_PRODUCT_NAME, name);
        values.put(BookEntry.COLUMN_PRICE, price);
        values.put(BookEntry.COLUMN_QTY, qty);
        values.put(BookEntry.COLUMN_SUPPLIER_NAME, supplier);
        values.put(BookEntry.COLUMN_SUPPLIER_PHONE, suppplierPhone);

        long newRodId = db.insert(BookEntry.TABLE_NAME, null, values);

        Log.v("CatalogActivity", "New row id: " + newRodId);
    }

    private void displayDataBaseInfo() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                BookEntry._ID,
                BookEntry.COLUMN_PRODUCT_NAME,
                BookEntry.COLUMN_PRICE,
                BookEntry.COLUMN_QTY,
                BookEntry.COLUMN_SUPPLIER_NAME,
                BookEntry.COLUMN_SUPPLIER_PHONE,
        };

        Cursor cursor = db.query(
                BookEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        try {
            int logTotalBooksCount = 0;
            String logDbRecords = "\n\n------------------------------------" +
                    "----------------------------------------------------------------------\n";

            int idColumnIndex = cursor.getColumnIndex(BookEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRICE);
            int qtyColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_QTY);
            int supplierNameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_SUPPLIER_NAME);
            int supplierPhoneColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_SUPPLIER_PHONE);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
                int currentQty = cursor.getInt(qtyColumnIndex);
                String currentSupplierName = cursor.getString(supplierNameColumnIndex);
                String currentSupplierPhone = cursor.getString(supplierPhoneColumnIndex);

                logDbRecords += "ID: " + currentID + " | "
                        + "Name: " + currentName + " | "
                        + "Price: " + String.valueOf(currentPrice) + " | "
                        + "Qty: " + String.valueOf(currentQty) + " | "
                        + "Supplier: " + currentSupplierName + " | "
                        + "Suppier Phone: " + currentSupplierPhone + "\n";

                logTotalBooksCount++;
            }

            logDbRecords += "-------------------------------------------------------" +
                    "-------------------------------------------------\n\n";
            Log.d("DB Records", logDbRecords);

            Toast.makeText(this,
                    "Total books count: "
                            + String.valueOf(logTotalBooksCount),
                    Toast.LENGTH_SHORT).show();

        } finally {
            cursor.close();
        }
    }
}
