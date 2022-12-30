package com.catexam.app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;

import com.catexam.app.response.DatabaseModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "CAT";
    private static final int version = 1;
    public static final String DB_NAME = "ar.db";
    private static final String SP_KEY_DB_VER = "db_ver";
    private static final String TABLE_NAME = "parent";
    private static final String TABLE_NAME1 = "sub_category";
    private static final String TABLE_NAME2 = "QuestionsTable";
    /* private static final String TABLE_NAME3 = "mathematics_section";
     private static final String TABLE_NAME4 = "science_section";
    */
    private Context context;
    public SQLiteDatabase database;
    String DB_PATH;


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, version);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void initialize() {
        if (databaseExists()) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            int dbVersion = prefs.getInt(SP_KEY_DB_VER, 1);

            if (version != dbVersion) {
                File dbFile = context.getDatabasePath(DB_NAME);
                if (!dbFile.delete()) {
                    Log.w(TAG, "Unable to update database");
                }
            }
        }
        if (!databaseExists()) {
            createDatabase();
        }
    }

    public boolean databaseExists() {
        File dbFile = context.getDatabasePath(DB_NAME);
        return dbFile.exists();
    }

    public void createDatabase() {
        String parentPath = context.getDatabasePath(DB_NAME).getParent();
        DB_PATH = context.getDatabasePath(DB_NAME).getPath();

        File file = new File(parentPath);
        if (!file.exists()) {
            if (!file.mkdir()) {
                Log.e(TAG, "Unable to create database directory");
                return;
            }
        }
        InputStream is = null;
        OutputStream os = null;
        try {
            is = context.getAssets().open(DatabaseHelper.DB_NAME);
            os = new FileOutputStream(DB_PATH);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();
            os.close();
            Log.e("Activity", "DB Copied");
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(SP_KEY_DB_VER, version);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void closeDatabase() {
        if (database != null) {
            database.close();
        }
    }


    public void isAttempted(int attempted, int id) {

        database = this.getWritableDatabase();
        String updateQuery = "Update " + TABLE_NAME1 + " set status = '" + attempted + "' where sub_category_id =" + "'" + (id + 1) + "'";
        database.execSQL(updateQuery);
        database.close();
    }


    public List<DatabaseModel> getParent(int id) {
        DatabaseModel levelItem = null;
        List<DatabaseModel> items = new ArrayList<>();
        database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select * From " + TABLE_NAME + " where main_cat_id = " + "'" + id + "'", null);
        if (cursor != null && cursor.moveToFirst()) {

            do {

                levelItem = new DatabaseModel();
                levelItem.setMain_cat_id(cursor.getInt(0));
                levelItem.setMain_category(cursor.getString(1));
                items.add(levelItem);

            } while (cursor.moveToNext());

        }

        cursor.close();
        closeDatabase();
        return items;
    }
    public List<DatabaseModel> getSub_category(int id) {
        DatabaseModel levelItem = null;
        List<DatabaseModel> items = new ArrayList<>();
        database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(" SELECT * FROM " + TABLE_NAME1 + " where main_category_id = " + "'" + id + "'", null);
        if (cursor != null && cursor.moveToFirst()) {

            do {

                levelItem = new DatabaseModel();
                levelItem.setIndex(cursor.getInt(0));
                levelItem.setMain_category_id(cursor.getInt(1));
                levelItem.setSub_category_id(cursor.getInt(2));
                levelItem.setSub_category_title(cursor.getString(3));
                levelItem.setIsAttempted(cursor.getInt(4));
                items.add(levelItem);

            } while (cursor.moveToNext());

        }

        cursor.close();
        closeDatabase();
        for (int i = 0; i < items.size(); i++) {
            Log.e(TAG, "getSub_category: "+items.get(i).getMain_category());
        }
        return items;
    }

    public List<DatabaseModel> getQuestionsTable(int id) {
        DatabaseModel levelItem = null;
        //createDatabase();
        List<DatabaseModel> items = new ArrayList<>();
        database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME2 + " where sub_category_id =" + "'" + id + "'", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                levelItem = new DatabaseModel();
                levelItem.setQuestion(cursor.getString(0));
                levelItem.setOption_1(cursor.getString(1));
                levelItem.setOption_2(cursor.getString(2));
                levelItem.setOption_3(cursor.getString(3));
                levelItem.setOption_4(cursor.getString(4));
                levelItem.setAnswer(cursor.getInt(5));
                levelItem.setExplaination(cursor.getString(6));
                levelItem.setSub_category_id(cursor.getInt(7));
                levelItem.setQue_id(cursor.getInt(9));
                levelItem.setBookmark(cursor.getInt(10));
                items.add(levelItem);
            } while (cursor.moveToNext());

        }

        for (int i = 0; i < items.size(); i++) {
            Log.e(TAG, "getQuestion: " + items.get(i).getQuestion());
        }

        cursor.close();
        closeDatabase();
        return items;
    }


    public List<DatabaseModel> getAllQuestions(int id) {

        DatabaseModel levelItem = null;
        //createDatabase();
        List<DatabaseModel> items = new ArrayList<>();
        database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME2 + " where sub_category_id =" + "'" + id + "'", null);
        if (cursor != null && cursor.moveToFirst()) {

            do {

                levelItem = new DatabaseModel();
                levelItem.setQuestion(cursor.getString(0));
                levelItem.setOption_1(cursor.getString(1));
                levelItem.setOption_2(cursor.getString(2));
                levelItem.setOption_3(cursor.getString(3));
                levelItem.setOption_4(cursor.getString(4));
                levelItem.setAnswer(cursor.getInt(5));
                levelItem.setExplaination(cursor.getString(6));
                levelItem.setSub_category_id(cursor.getInt(7));
                levelItem.setQue_id(cursor.getInt(9));
                levelItem.setBookmark(cursor.getInt(10));
                items.add(levelItem);

            } while (cursor.moveToNext());

        }

        for (int i = 0; i < items.size(); i++) {
            Log.e(TAG, "getQuestion: " + items.get(i).getQuestion());
        }

        cursor.close();
        closeDatabase();
        return items;
    }


    public String updateBookmark(int id) {
        String query = "Update " + TABLE_NAME2 + " set bookmark = 1 where id=" + "'" + id + "'";
        database = this.getWritableDatabase();
        database.execSQL(query);
        closeDatabase();
        return query;

    }

    public List<DatabaseModel> getIsAttempted(int attempted , int id) {
        DatabaseModel levelItem = null;
        database = this.getWritableDatabase();
        //createDatabase();
        List<DatabaseModel> items = new ArrayList<>();
        database = this.getWritableDatabase();
        String updateQuery = "Update " + TABLE_NAME1 + " set status = '" + attempted + "' where sub_category_id =" + "'" + (id + 1) + "'";
        database.execSQL(updateQuery);
        database.close();
        return items;
    }





    public String removeBookmark(int id) {

        String query = "Update " + TABLE_NAME2 + " set bookmark = 0 where question=" + "'" + id + "'";
        database = this.getWritableDatabase();

        database.execSQL(query);
        closeDatabase();
        return query;
    }

    public List<DatabaseModel> isBookmark() {
        DatabaseModel levelItem = null;
        List<DatabaseModel> items = new ArrayList<>();
        database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery("Select * from mathematics_section where bookmark = 1", null);
        if (cursor != null && cursor.moveToFirst()) {

            do {
                levelItem = new DatabaseModel();
                levelItem.setQuestion(cursor.getString(0));
                levelItem.setOption_1(cursor.getString(1));
                levelItem.setOption_2(cursor.getString(2));
                levelItem.setOption_3(cursor.getString(3));
                levelItem.setOption_4(cursor.getString(4));
                levelItem.setAnswer(cursor.getInt(5));
                items.add(levelItem);

            } while (cursor.moveToNext());
        }

        cursor.close();
        closeDatabase();
        return items;
    }


}
