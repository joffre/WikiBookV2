package com.pje.def.wikibook.bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.pje.def.wikibook.model.Genre;

import java.sql.SQLException;

/**
 * Database Manager
 * Created by Geoffrey on 22/10/2015.
 */
public class DatabaseHandler extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "wikibookdir.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<BookDetails, Integer> bookDao;
    private Dao<FilterDetails, Integer> filterDao;
    private Dao<GenreDetails, Integer> genderDao;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Create tables et initiate them
     * @param sqliteDatabase
     * @param connectionSource
     */
    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, BookDetails.class);
            TableUtils.createTable(connectionSource, FilterDetails.class);
            TableUtils.createTable(connectionSource, GenreDetails.class);

            GenreCollection.addGender(new Genre(0, "No Genre"));
            GenreCollection.addGender(new Genre(1, "Thriller"));
            GenreCollection.addGender(new Genre(2, "Romantic"));
            GenreCollection.addGender(new Genre(3, "Comics"));
            GenreCollection.addGender(new Genre(4, "Novel"));
            GenreCollection.addGender(new Genre(5, "Biography"));
        } catch (SQLException e) {
            Log.e(DatabaseHandler.class.getName(), "Unable to create databases", e);
        }
    }

    /**
     * Drop table on upgrade
     * @param sqliteDatabase
     * @param connectionSource
     * @param oldVer
     * @param newVer
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
        try {
            TableUtils.dropTable(connectionSource, BookDetails.class, true);
            TableUtils.dropTable(connectionSource, FilterDetails.class, true);
            TableUtils.dropTable(connectionSource, GenreDetails.class, true);
            onCreate(sqliteDatabase, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHandler.class.getName(), "Unable to upgrade database from version " + oldVer + " to new "
                    + newVer, e);
        }
    }

    /**
     *  Genres table accessor
     */
    public  Dao<GenreDetails, Integer> getGenreDao() throws  SQLException {
        if(genderDao == null){
            genderDao = getDao(GenreDetails.class);
        }
        return genderDao;
    }

    /**
     * Books table accessor
     */
    public Dao<BookDetails, Integer> getBookDao() throws SQLException {
        if (bookDao == null) {
            bookDao = getDao(BookDetails.class);
        }
        return bookDao;
    }

    /**
     * Book filters table accessor
     */
    public Dao<FilterDetails, Integer> getFilterDao() throws SQLException {
        if (filterDao == null) {
            filterDao = getDao(FilterDetails.class);
        }
        return filterDao;
    }
}
