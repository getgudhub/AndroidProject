package com.example.vidas.androidproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DatabaseSQLiteUser extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION   = 2;
    private static final String DATABASE_NAME   = "db";

    private static final int ADMIN_LEVEL        = 9;
    private static final String TABLE_USERS     = "users";
    private static final String USER_ID         = "userid";
    private static final String USER_LEVEL      = "userlevel";
    private static final String USER_NAME       = "name";
    private static final String USER_PASSWORD   = "password";
    private static final String USER_EMAIL      = "email";

    String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS
            + "("
            + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + USER_LEVEL + " INTEGER,"
            + USER_NAME + " TEXT,"
            + USER_PASSWORD + " TEXT,"
            + USER_EMAIL + " TEXT"
            + ")";

    public DatabaseSQLiteUser(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        // Create tables again
        onCreate(db);
    }

    void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_LEVEL,      user.getUserlevel());
        values.put(USER_NAME,       user.getUsernameForRegister());
        values.put(USER_PASSWORD,   user.getPasswordForRegister());
        values.put(USER_EMAIL,      user.getEmailForRegister());

        // Inserting Row
        db.execSQL(CREATE_USERS_TABLE);
        db.insert(TABLE_USERS, null, values);

        // Closing database connection
        db.close();
    }

    User getUser(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = new User();
        Cursor cursor = db.query(
                TABLE_USERS,
                new String[]{
                        USER_ID,
                        USER_LEVEL,
                        USER_NAME,
                        USER_PASSWORD,
                        USER_EMAIL
                },
                USER_NAME + "=?",
                new String[]{String.valueOf(name)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst() ){


            user = new User(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            );
            cursor.close();
        }
        db.close();
        return user;

    }

    public boolean isAdmin(int userlevel){
        if(ADMIN_LEVEL==userlevel){
            return true;
        }else {
            return false;
        }
    }

    public boolean isValidUser(String username, String password){
        Cursor c = getReadableDatabase().rawQuery(
                "SELECT * FROM " + TABLE_USERS + " WHERE "
                        + USER_NAME + "='" + username + "'AND " +
                        USER_PASSWORD + "='" + password + "'" , null);
        if (c.getCount() > 0)
            return true;
        c.close();
        return false;

    }

    public boolean checkName(String  name){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "Select * from " + TABLE_USERS + " where " + USER_NAME + " = '" + name +"'";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }else{
            cursor.close();
            return true;
        }
    }
    /*

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();

                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setUserlevel(cursor.getInt(1));
                user.setUsernameForRegister(cursor.getString(2));
                user.setPasswordForRegister(cursor.getString(3));
                user.setEmailForRegister(cursor.getString(4));

                // adding user to list
                users.add(user);
            } while (cursor.moveToNext());
        }   cursor.close();

        // return users list
        return users;

    }

    public String getNameById(int id){
        String username="";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("Select * From "+ TABLE_USERS + " Where "+ USER_ID +" = "+ id, null);
        if (c != null)
            if(c.moveToFirst()) {
                do {
                    username = c.getString( c.getColumnIndex(USER_NAME) );
                } while (c.moveToNext());
            }
        c.close();
        return username;

    }*/
}