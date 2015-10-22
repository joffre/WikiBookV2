package com.pje.def.wikibook.bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Geoffrey on 22/10/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper{

    public static final String TABLE_BOOK = "book";
    public static final String COLUMN_ISBN = "isbn";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_AUHTOR = "author";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_GENRE = "genre";
    public static final String COLUMN_DESCRIPTION = "description";
    private static final String DATABASE_NAME = "book.db";
    private static final int DATABASE_VERSION = 1;
    // Commande sql pour la création de la base de données
    private static final String DATABASE_CREATE = "create table "
            + TABLE_BOOK + "("
            + COLUMN_ISBN + " text primary key "
            + COLUMN_TITLE + " text, "
            + COLUMN_AUHTOR + " text, "
            + COLUMN_YEAR + " text, "
            + COLUMN_GENRE + " text, "
            + COLUMN_DESCRIPTION + " text, "
            + ");";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(DatabaseHandler.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        onCreate(sqLiteDatabase);
    }
}
