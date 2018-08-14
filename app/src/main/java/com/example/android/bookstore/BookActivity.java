package com.example.android.bookstore;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.android.bookstore.data.BookContract;
import com.example.android.bookstore.data.BookDbHelper;

public class BookActivity extends AppCompatActivity {

    public static final String LOG_TAG = BookActivity.class.getSimpleName();
    private BookDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookActivity.this, AddBookActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new BookDbHelper(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        //Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {BookContract.BookEntry._ID, BookContract.BookEntry.COLUMN_BOOK_NAME,
                BookContract.BookEntry.COLUMN_BOOK_PRICE, BookContract.BookEntry.COLUMN_BOOK_QUANTITY,
                BookContract.BookEntry.COLUMN_BOOK_SUPPLIERNAME, BookContract.BookEntry.COLUMN_BOOK_CATEGORY,
                BookContract.BookEntry.COLUMN_BOOK_SUPPLIERPHONENUMBER};
        String selection = null;
        String[] selectionArgs = null;

        //Perform this raw SQL query "SELECT * FROM books"
        // to get a Cursor that contains all rows from the books table;
        Cursor cursor = db.query(BookContract.BookEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        try {

            TextView displayView = findViewById(R.id.text_view_book);
            displayView.setText("The Book table contains: " + cursor.getCount() + " books.\n\n");

            // Display column header .
            displayView.append(getString(R.string.table_header));

            //Find the index of each column
            int idColumnIndex = cursor.getColumnIndex(BookContract.BookEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_NAME);
            int priceColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_QUANTITY);
            int categoryColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_CATEGORY);
            int suppliernameColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_SUPPLIERNAME);
            int supplierphonenumberColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_SUPPLIERPHONENUMBER);

            // Move the cursor to the next row is it exists.
            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                int currentCategory = cursor.getInt(categoryColumnIndex);
                String currentSupplierName = cursor.getString(suppliernameColumnIndex);
                String currentSupplerPhoneNumber = cursor.getString(supplierphonenumberColumnIndex);

                // Display current row data.
                displayView.append("\n" + currentID + " - " + currentName + " - " + currentPrice + " - " + currentQuantity
                        + " - " + currentCategory + " - " + currentSupplierName + " - " + currentSupplerPhoneNumber);
            }
        } finally {
            // Close the cursor. This releases all its resources and makes it invalid.
            cursor.close();
        }

    }


}

