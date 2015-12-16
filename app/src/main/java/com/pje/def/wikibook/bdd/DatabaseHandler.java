package com.pje.def.wikibook.bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.pje.def.wikibook.model.GenderCollection;
import com.pje.def.wikibook.model.Genre;

import java.sql.SQLException;

/**
 * Created by Geoffrey on 22/10/2015.
 */
public class DatabaseHandler extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "wikibookdir.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<BookDetails, Integer> bookDao;
    private Dao<FilterDetails, Integer> filterDao;
    private Dao<GenderDetails, Integer> genderDao;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
        try {

            // Create tables. This onCreate() method will be invoked only once of the application life time i.e. the first time when the application starts.
            TableUtils.createTable(connectionSource, BookDetails.class);
            TableUtils.createTable(connectionSource, FilterDetails.class);
            TableUtils.createTable(connectionSource, GenderDetails.class);

            GenderCollection.addGender(new Genre(0, "No Gender"));
            GenderCollection.addGender(new Genre(1, "Thriller"));
            GenderCollection.addGender(new Genre(2, "Romantic"));
            GenderCollection.addGender(new Genre(3, "Comics"));
            GenderCollection.addGender(new Genre(4, "Novel"));
            GenderCollection.addGender(new Genre(5, "Biography"));




        } catch (SQLException e) {
            Log.e(DatabaseHandler.class.getName(), "Unable to create databases", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
        try {

            // In case of change in database of next version of application, please increase the value of DATABASE_VERSION variable, then this method will be invoked
            //automatically. Developer needs to handle the upgrade logic here, i.e. create a new table or a new column to an existing table, take the backups of the
            // existing database etc.

            TableUtils.dropTable(connectionSource, BookDetails.class, true);
            TableUtils.dropTable(connectionSource, FilterDetails.class, true);
            TableUtils.dropTable(connectionSource, GenderDetails.class, true);
            onCreate(sqliteDatabase, connectionSource);

        } catch (SQLException e) {
            Log.e(DatabaseHandler.class.getName(), "Unable to upgrade database from version " + oldVer + " to new "
                    + newVer, e);
        }
    }

    // Create the getDao methods of all database tables to access those from android code.
    // Insert, delete, read, update everything will be happened through DAOs

    public  Dao<GenderDetails, Integer> getGenderDao() throws  SQLException {
        if(genderDao == null){
            genderDao = getDao(GenderDetails.class);
        }
        return genderDao;
    }

    public Dao<BookDetails, Integer> getBookDao() throws SQLException {
        if (bookDao == null) {
            bookDao = getDao(BookDetails.class);
        }
        return bookDao;
    }

    public Dao<FilterDetails, Integer> getFilterDao() throws SQLException {
        if (filterDao == null) {
            filterDao = getDao(FilterDetails.class);
        }
        return filterDao;
    }
}
