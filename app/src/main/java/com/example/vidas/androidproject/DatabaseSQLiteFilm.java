package com.example.vidas.androidproject;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseSQLiteFilm extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION   = 2;
    private static final String DATABASE_NAME   = "db";

    private static final String USERNAME            = "username";
    private static final String TABLE_FILMS         = "films";
    private static final String FILM_ID             = "id";
    private static final String FILM_NAME           = "name";
    private static final String FILM_DIRECTOR       = "director";
    private static final String FILM_ACTORS         = "actors";
    private static final String FILM_GENRE          = "genre";
    private static final String FILM_LENGTH         = "length";
    private static final String FILM_DATE           = "date";
    private static final String FILM_RATING         = "rating";
    private static final String FILM_RATING_POINTS  = "ratingPoints";
    private static final String FILM_VOTES          = "votes";
    private static final String FILM_CENZ           = "cenz";


    String CREATE_IF_NOT_EXISTS_FILMS_TABLE = "CREATE TABLE IF NOT EXISTS "+
            TABLE_FILMS
            + "("
            + FILM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + FILM_NAME + " TEXT,"
            + FILM_DIRECTOR + " TEXT,"
            + FILM_ACTORS + " TEXT,"
            + FILM_GENRE + " TEXT,"
            + FILM_LENGTH +" INTEGER,"
            + FILM_DATE + " TEXT,"
            + FILM_RATING + " INTEGER,"
            + FILM_RATING_POINTS + " INTEGER,"
            + FILM_VOTES + " INTEGER,"
            + FILM_CENZ + " TEXT,"
            + USERNAME + " TEXT"
            + ")";

    public DatabaseSQLiteFilm(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILMS);

        // Create tables again
        onCreate(db);
    }

    public void addFilm(Film film){
        SQLiteDatabase db = this.getWritableDatabase();

        /* // when the other method is a mess - this one's the smooth one
        db.execSQL("INSERT INTO "+ TABLE_FILMS +" " +
                        "("+FILM_NAME+", "+FILM_DIRECTOR+", "+FILM_ACTORS+", "+
                            FILM_GENRE+", "+FILM_LENGTH+", "+FILM_DATE+", "+FILM_RATING+", "+
                            FILM_RATING_POINTS+", "+FILM_VOTES+", "+FILM_CENZ+", "+USERNAME+") " +
                        "VALUES ('"+ film.getName()+"', '"+ film.getDirector()+"', '"+ film.getActors()+"', '"+ film.getGenre()+"', '"+
                            film.getLength()+"', '"+ film.getDate()+"', '"+ film.getRating()+"', '"+ film.getRatingPoints()+"', '"+
                            film.getVotes()+"', '"+ film.getCenzas()+"', '"+ film.getUsername()+"')" ) ;*/
        ContentValues values = new ContentValues();
        values.put(FILM_NAME,           film.getName());
        values.put(FILM_DIRECTOR,       film.getDirector());
        values.put(FILM_ACTORS,         film.getActors());
        values.put(FILM_GENRE,          film.getGenre());
        values.put(FILM_LENGTH,         film.getLength());
        values.put(FILM_DATE,           film.getDate());
        values.put(FILM_RATING,         film.getRating());
        values.put(FILM_RATING_POINTS,  film.getRatingPoints());
        values.put(FILM_VOTES,          film.getVotes());
        values.put(FILM_CENZ,           film.getCenzas());
        values.put(USERNAME,            film.getUsername());

        // Inserting Row
        db.insert(TABLE_FILMS, null, values);

        // Closing database connection
        db.close();
    }

    public int updateFilm( Film film){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FILM_NAME,            film.getName());
        values.put(FILM_DIRECTOR ,       film.getDirector());
        values.put(FILM_ACTORS ,         film.getActors());
        values.put(FILM_GENRE,           film.getGenre());
        values.put(FILM_LENGTH,          film.getLength());
        values.put(FILM_DATE,            film.getDate());
        values.put(FILM_RATING,          film.getRating());
        values.put(FILM_RATING_POINTS,   film.getRatingPoints());
        values.put(FILM_VOTES,           film.getVotes());
        values.put(FILM_CENZ,            film.getCenzas());

        int i = db.update(TABLE_FILMS, values,
                FILM_ID + " = ?",
                new String[] {String.valueOf(film.getId())});

        db.close();
        return i;

    }

    public boolean deleteFilm(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_FILMS, FILM_ID + "=" + id, null) > 0;
    }

    /*public Cursor getAllFilms() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.query(TABLE_FILMS, new String[]{
                        FILM_ID, FILM_NAME, FILM_DIRECTOR,
                        FILM_ACTORS, FILM_GENRE, FILM_LENGTH, FILM_DATE,
                        FILM_RATING, FILM_RATING_POINTS, FILM_VOTES, USERNAME},
                null, null, null, null, null);
    }*/

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(CREATE_IF_NOT_EXISTS_FILMS_TABLE);
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_FILMS, null);
        return res;
    }


    public boolean checkId(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "Select * from " + TABLE_FILMS + " where " + FILM_ID + " = " + id;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }else{
            cursor.close();
            return true;
        }
    }
    public boolean checkName(String  name){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "Select * from " + TABLE_FILMS + " where " + FILM_NAME + " = '" + name +"'";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }else{
            cursor.close();
            return true;
        }
    }



    public ArrayList<Film> getAllFilms() {
        ArrayList<Film> films = new ArrayList<Film>();

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(CREATE_IF_NOT_EXISTS_FILMS_TABLE);

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FILMS;

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Film film = new Film();
                        film.setId(cursor.getInt(0));
                        film.setName(cursor.getString(1));
                        film.setDirector(cursor.getString(2));
                        film.setActors(cursor.getString(3));
                        film.setGenre(cursor.getString(4));
                        film.setLength(cursor.getInt(5));
                        film.setDate(cursor.getString(6));
                        film.setRating(cursor.getInt(7));
                        film.setRatingPoints(cursor.getInt(8));
                        film.setVotes(cursor.getInt(9));
                        film.setCenzas(cursor.getString(10));
                        film.setUsername(cursor.getString(11));

                films.add(film);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return films;
    }

    public ArrayList<Film> getUserFilms(String username) {
        ArrayList<Film> films = new ArrayList<Film>();

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(CREATE_IF_NOT_EXISTS_FILMS_TABLE);

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FILMS + " WHERE "+ USERNAME +" = '" + username + "'", null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Film film = new Film();

                film.setId(cursor.getInt(0));
                film.setName(cursor.getString(1));
                film.setDirector(cursor.getString(2));
                film.setActors(cursor.getString(3));
                film.setGenre(cursor.getString(4));
                film.setLength(cursor.getInt(5));
                film.setDate(cursor.getString(6));
                film.setRating(cursor.getInt(7));
                film.setRatingPoints(cursor.getInt(8));
                film.setVotes(cursor.getInt(9));
                film.setCenzas(cursor.getString(10));
                film.setUsername(username);

                films.add(film);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return films;
    }

}
