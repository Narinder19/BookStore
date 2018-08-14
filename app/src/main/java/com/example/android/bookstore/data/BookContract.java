package com.example.android.bookstore.data;

import android.provider.BaseColumns;

public final class BookContract {

    public static abstract class BookEntry implements BaseColumns {
        public static final String TABLE_NAME = "books";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_BOOK_NAME = "name";
        public static final String COLUMN_BOOK_PRICE = "price";
        public static final String COLUMN_BOOK_QUANTITY = "quantity";
        public static final String COLUMN_BOOK_SUPPLIERNAME = "suppliername";
        public static final String COLUMN_BOOK_SUPPLIERPHONENUMBER = "supplierphonenumber";
        public static final String COLUMN_BOOK_CATEGORY = "category";

        //Possible values for book category.
        public static final int CATEGORY_UNKNOWN = 0;
        public static final int CATEGORY_FICTION = 1;
        public static final int CATEGORY_HISTORY = 2;
        public static final int CATEGORY_TECHNOLOGY = 3;
    }
}
