package com.example.android.bookstore;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.bookstore.data.BookContract;
import com.example.android.bookstore.data.BookDbHelper;

public class AddBookActivity extends AppCompatActivity {

    private BookDbHelper mDbHelper;
    private EditText mNameEditText;
    private EditText mPriceEditText;
    private EditText mQuantityEditText;
    private EditText mSupplierNameEditText;
    private EditText mSupplierPhoneNumberEditText;
    private Spinner mCategorySpinner;

    private int mCategory = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbook);

        mNameEditText = findViewById(R.id.edit_book_name);
        mPriceEditText = findViewById(R.id.edit_book_price);
        mQuantityEditText = findViewById(R.id.edit_book_quantity);
        mSupplierNameEditText = findViewById(R.id.edit_book_supplier_name);
        mSupplierPhoneNumberEditText = findViewById(R.id.edit_book_supplier_phone_number);
        mCategorySpinner = findViewById(R.id.spinner_book_category);
        mDbHelper = new BookDbHelper(this);
        setupSpinner();

    }

    private void setupSpinner() {
        ArrayAdapter categorySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.book_categories, android.R.layout.simple_spinner_item);

        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mCategorySpinner.setAdapter(categorySpinnerAdapter);

        mCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);

                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.book_category_fiction))) {
                        mCategory = BookContract.BookEntry.CATEGORY_FICTION;
                    } else if (selection.equals(getString(R.string.book_category_history))) {
                        mCategory = BookContract.BookEntry.CATEGORY_HISTORY;
                    } else if (selection.equals(getString(R.string.book_category_technology))) {

                        mCategory = BookContract.BookEntry.CATEGORY_TECHNOLOGY;
                    } else {
                        mCategory = BookContract.BookEntry.CATEGORY_UNKNOWN;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // Insert screen data to SQLite books database.
    private void insertBook() {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        //Create a ContentValues object to insert data into database table.
        ContentValues values = new ContentValues();
        values.put(BookContract.BookEntry.COLUMN_BOOK_NAME, mNameEditText.getText().toString());
        values.put(BookContract.BookEntry.COLUMN_BOOK_PRICE, String.valueOf(mPriceEditText.getText()));
        values.put(BookContract.BookEntry.COLUMN_BOOK_QUANTITY, String.valueOf(mQuantityEditText.getText()));
        values.put(BookContract.BookEntry.COLUMN_BOOK_CATEGORY, String.valueOf(mCategory));
        values.put(BookContract.BookEntry.COLUMN_BOOK_SUPPLIERNAME, mSupplierNameEditText.getText().toString());
        values.put(BookContract.BookEntry.COLUMN_BOOK_SUPPLIERPHONENUMBER, mSupplierPhoneNumberEditText.getText().toString());

        long newRowId = db.insert(BookContract.BookEntry.TABLE_NAME, null, values);

        Toast.makeText(getApplicationContext(), "New Row Id: " + newRowId, Toast.LENGTH_SHORT).show();
    }

    //Create options menu for AddBookActivity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_addbook, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                insertBook();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
