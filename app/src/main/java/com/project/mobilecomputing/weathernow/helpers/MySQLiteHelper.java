package com.project.mobilecomputing.weathernow.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.project.mobilecomputing.weathernow.models.Favorites;
import com.project.mobilecomputing.weathernow.models.History;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kannan on 11/13/2015.
 * REFERENCE: http://hmkcode.com/android-simple-sqlite-database-tutorial/
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "LocationDB";

    //Tables
    private static final String TABLE_HISTORY = "history";
    private static final String TABLE_FAVORITES = "favorites";

    // Columns
    private static final String KEY_ID = "id";
    private static final String KEY_LOCATION = "location";
    private static final String[] COLUMNS = {KEY_ID,KEY_LOCATION};

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create history table
        String CREATE_HISTORY_TABLE = "CREATE TABLE history ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "location TEXT UNIQUE)";
        // SQL statement to create favorites table
        String CREATE_FAVORITES_TABLE = "CREATE TABLE favorites ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "location TEXT UNIQUE)";

        // create history table
        db.execSQL(CREATE_HISTORY_TABLE);

        // create favorites table
        db.execSQL(CREATE_FAVORITES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS history");
        db.execSQL("DROP TABLE IF EXISTS favorites");
        // create fresh tables
        this.onCreate(db);
    }

    public void addHistory(History history){
        //for logging
        Log.d("addHistory", history.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_LOCATION, history.getLocation()); //

        // 3. insert
        db.insert(TABLE_HISTORY, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public void addFavorite(Favorites favorite){
        //for logging
        Log.d("addFavorite", favorite.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_LOCATION, favorite.getLocation()); //

        // 3. insert
        db.insert(TABLE_FAVORITES, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public History getHistory(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_HISTORY, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build history object
        History history = new History();
        history.setId(Integer.parseInt(cursor.getString(0)));
        history.setLocation(cursor.getString(1));

        //log
        Log.d("getHistory(" + id + ")", history.toString());

        // 5. return history
        return history;
    }

    public Favorites getFavorite(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_FAVORITES, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build favorite object
        Favorites favorite = new Favorites();
        favorite.setId(Integer.parseInt(cursor.getString(0)));
        favorite.setLocation(cursor.getString(1));

        //log
        Log.d("getFavorite(" + id + ")", favorite.toString());

        // 5. return favorite
        return favorite;
    }

    public List<History> getAllHistory() {
        List<History> historyList = new LinkedList<History>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_HISTORY;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build history and add it to list
        History history = null;
        if (cursor.moveToFirst()) {
            do {
                history = new History();
                history.setId(Integer.parseInt(cursor.getString(0)));
                history.setLocation(cursor.getString(1));

                // Add history to history list
                historyList.add(history);
            } while (cursor.moveToNext());
        }

        Log.d("getAllHistory()", history.toString());

        // return history list
        return historyList;
    }

    public List<Favorites> getAllFavorites() {
        List<Favorites> favoritesList = new LinkedList<Favorites>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_FAVORITES;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build favorite and add it to list
        Favorites favorite = null;
        if (cursor.moveToFirst()) {
            do {
                favorite = new Favorites();
                favorite.setId(Integer.parseInt(cursor.getString(0)));
                favorite.setLocation(cursor.getString(1));

                // Add history to favorites list
                favoritesList.add(favorite);
            } while (cursor.moveToNext());
        }

        Log.d("getAllFavorites()", favorite.toString());

        // return favorite list
        return favoritesList;
    }

    public void deleteHistory(History history) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_HISTORY, //table name
                KEY_ID+" = ?",  // selections
                new String[] { String.valueOf(history.getId()) }); //selections args

        // 3. close
        db.close();

        //log
        Log.d("deleteHistory", history.toString());

    }

    public void deleteFavorite(Favorites favorite) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_FAVORITES, //table name
                KEY_ID+" = ?",  // selections
                new String[] { String.valueOf(favorite.getId()) }); //selections args

        // 3. close
        db.close();

        //log
        Log.d("deleteFavorites", favorite.toString());

    }

    public void deleteAllHistory()
    {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.execSQL("delete * from " + TABLE_HISTORY);

        // 3. close
        db.close();

        //log
        Log.d("deleteAllHistory","All rows from History deleted.");
    }

    public void deleteAllFavorites()
    {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.execSQL("delete * from "+TABLE_FAVORITES);

        // 3. close
        db.close();

        //log
        Log.d("deleteAllFavorites","All rows from Favorites deleted.");
    }
}
