package com.example.mapstest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mapstest.data.model.UserPass;

public class UserHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MyDBName.db";
    private static final String TABLE_UserPass = "userpass";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PASS = "passwd";
    private static final String KEY_TYPE = "type";

    public UserHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_UserPass + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PASS + " TEXT," + KEY_TYPE + "TEXT " +")";
        db.execSQL(CREATE_USER_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UserPass);

        // Create tables again
        onCreate(db);
    }

    // code to add the new user
    void addUserPass(UserPass user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.get_email()); // UserPass Name
        values.put(KEY_PASS, user.get_password()); // UserPass Pass

        // Inserting Row
        db.insert(TABLE_UserPass, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single user
    public String get_type(String name,String passwd) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor;
        cursor = db.query(TABLE_UserPass,new String[] {KEY_TYPE},KEY_NAME+ "=?" +"and" + KEY_PASS+ "=?",new String[] {name,passwd},null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
            return cursor.getString(0);
        }
        else
            return "";
//
//        Cursor cursor = db.query(TABLE_UserPass, new String[] { KEY_ID,
//                        KEY_NAME, KEY_PASS }, KEY_ID + "=?",
//                new String[] { String.valueOf(id) }, null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        UserPass user = new UserPass(Integer.parseInt(cursor.getString(0)),
//                cursor.getString(1), cursor.getString(2),cursor.getString(3));
//        // return user
//        return user;
    }
//
//    // code to get all users in a list view
//    public List<UserPass> getAllUser() {
//        List<UserPass> userList = new ArrayList<UserPass>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_UserPass;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                UserPass user = new UserPass();
//                user.set_id(Integer.parseInt(cursor.getString(0)));
//                user.set_email(cursor.getString(1));
//                user.set_password(cursor.getString(2));
//                // Adding user to list
//                userList.add(user);
//            } while (cursor.moveToNext());
//        }
//
//        // return user list
//        return userList;
//    }

    // code to update the single user
//    public int updateUser(UserPass user) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, user.get_email());
//        values.put(KEY_PASS, user.get_password());
//        values.put(KEY_PASS, user.get_type());
//
//        // updating row
//        return db.update(TABLE_UserPass, values, KEY_ID + " = ?",
//                new String[] { String.valueOf(user.get_id()) });
//    }

    // Deleting single user
//    public void deleteUser(UserPass user) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_UserPass, KEY_ID + " = ?",
//                new String[] { String.valueOf(user.get_id()) });
//        db.close();
//    }
    public boolean UserExist(String name) {
        String countQuery = "SELECT  * FROM " + TABLE_UserPass + "WHERE name=" + name;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_UserPass,new String[] {KEY_ID},KEY_NAME+"=?",new String[] {name},null,null,null );
        int count=cursor.getCount();
        cursor.close();

        // return count
        return (count==1);
    }

    // Getting users Count
    public boolean UserPassExist(String name, String passwd) {
        String countQuery = "SELECT  * FROM " + TABLE_UserPass + "WHERE name=" + name + "and passwd = " + passwd;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_UserPass,new String[] {KEY_ID},KEY_NAME+"=? and " + KEY_PASS +"=?",new String[] {name,passwd},null,null,null );
        int count=cursor.getCount();
        cursor.close();

        // return count
        return (count==1);
    }

}

