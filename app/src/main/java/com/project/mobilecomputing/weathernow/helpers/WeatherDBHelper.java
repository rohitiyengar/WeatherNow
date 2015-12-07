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
 * Reference: http://hmkcode.com/android-simple-sqlite-database-tutorial/
 */
public class WeatherDBHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "LocationDB";
    private static final String TABLE_HISTORY = "history";
    private static final String TABLE_FAVORITES = "favorites";

    private static final String KEY_ID = "id";
    private static final String KEY_LOCATION = "location";
    private static final String[] COLUMNS = {KEY_ID, KEY_LOCATION};

    public WeatherDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_HISTORY_TABLE = "CREATE TABLE history ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "location TEXT UNIQUE)";

        String CREATE_FAVORITES_TABLE = "CREATE TABLE favorites ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "location TEXT UNIQUE)";


        db.execSQL(CREATE_HISTORY_TABLE);

        db.execSQL(CREATE_FAVORITES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS history");
        db.execSQL("DROP TABLE IF EXISTS favorites");

        this.onCreate(db);
    }

    /***
     * Add a row to the history table
     *
     * @param history
     */
    public void addHistory(History history) {
        try {
            Log.d("addHistory", history.toString());

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_LOCATION, history.getLocation());

            db.insertOrThrow(TABLE_HISTORY,
                    null,
                    values);
            db.close();
        } catch (Exception e) {
            Log.d("Duplicate", history.toString());
        }
    }

    /***
     * Add a row to the favorites table
     *
     * @param favorite
     */
    public void addFavorite(Favorites favorite) {
        try {
            Log.d("addFavorite", favorite.toString());
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_LOCATION, favorite.getLocation());
            db.insertOrThrow(TABLE_FAVORITES,
                    null,
                    values);
            db.close();
        } catch (Exception e) {
            Log.d("Duplicate", favorite.toString());
        }
    }

    /***
     * Retrieve Single History row.
     *
     * @param id
     * @return History object with values from the row.
     */
    public History getHistory(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_HISTORY,
                        COLUMNS,
                        " id = ?",
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        null,
                        null);

        if (cursor != null)
            cursor.moveToFirst();

        History history = new History();
        history.setId(Integer.parseInt(cursor.getString(0)));
        history.setLocation(cursor.getString(1));

        Log.d("getHistory(" + id + ")", history.toString());

        return history;
    }

    /***
     * Retrieve single Favorites row
     *
     * @param id
     * @return Favorites object with values from the row.
     */
    public Favorites getFavorite(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_FAVORITES,
                        COLUMNS,
                        " id = ?",
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        null,
                        null);

        if (cursor != null)
            cursor.moveToFirst();

        Favorites favorite = new Favorites();
        favorite.setId(Integer.parseInt(cursor.getString(0)));
        favorite.setLocation(cursor.getString(1));

        Log.d("getFavorite(" + id + ")", favorite.toString());

        return favorite;
    }

    /***
     * Get all History data
     *
     * @return All rows in the history table as a list.
     */
    public List<History> getAllHistory() {
        List<History> historyList = new LinkedList<History>();

        String query = "SELECT  * FROM " + TABLE_HISTORY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        History history = null;
        if (cursor.moveToFirst()) {
            do {
                history = new History();
                history.setId(Integer.parseInt(cursor.getString(0)));
                history.setLocation(cursor.getString(1));
                historyList.add(history);
            } while (cursor.moveToNext());
        }
        return historyList;
    }

    /***
     * Get all Favorites data
     *
     * @return ALl rows in favorites table as a list.
     */
    public List<Favorites> getAllFavorites() {
        List<Favorites> favoritesList = new LinkedList<Favorites>();
        String query = "SELECT  * FROM " + TABLE_FAVORITES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Favorites favorite = null;
        if (cursor.moveToFirst()) {
            do {
                favorite = new Favorites();
                favorite.setId(Integer.parseInt(cursor.getString(0)));
                favorite.setLocation(cursor.getString(1));
                favoritesList.add(favorite);
            } while (cursor.moveToNext());
        }
        return favoritesList;
    }

    /***
     * Delete a single history row.
     *
     * @param history
     */
    public void deleteHistory(History history) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HISTORY,
                KEY_ID + " = ?",
                new String[]{String.valueOf(history.getId())});
        db.close();
        Log.d("deleteHistory", history.toString());

    }

    /***
     * Delete a single favorites row.
     *
     * @param favorite
     */
    public void deleteFavorite(Favorites favorite) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_FAVORITES,
                KEY_ID + " = ?",
                new String[]{String.valueOf(favorite.getId())});
        db.close();

        Log.d("deleteFavorites", favorite.toString());

    }

    /***
     * Delete all history
     */
    public void deleteAllHistory() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_HISTORY);
        db.close();
        Log.d("deleteAllHistory", "All rows from History deleted.");
    }

    /***
     * Delete all favorites
     */
    public void deleteAllFavorites() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_FAVORITES);
        db.close();
        Log.d("deleteAllFavorites", "All rows from Favorites deleted.");
    }
}
