package com.latutslab_00000053580.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.latutslab_00000053580.foodro.User;

public class DbUser {

    private DatabaseHelper db;
    private Context context;
    private SQLiteDatabase database;

    public DbUser(Context context) {
        this.context = context;
    }

    public DbUser open() throws SQLException {
        db = new DatabaseHelper(context);
        database = db.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    //using this method we can add users to user table
    public void addUser(User user) {

        //create content values to insert
        ContentValues values = new ContentValues();

        values.put(db.USER_ID, user.getId());
        values.put(db.USER_EMAIL, user.getEmail());
        values.put(db.USER_FIRSTNAME, user.getFirstname());
        values.put(db.USER_LASTNAME, user.getLastname());
        values.put(db.USER_ROLE, user.getRole());
        values.put(db.USER_IMG, user.getImage());
        database.insert(db.TABLE_USERS, null, values);
    }

    //Check whether account existed or not
    public User Authenticate() {

        String sql = "SELECT * FROM " + db.TABLE_USERS + " LIMIT 1";
        Cursor cursor = database.rawQuery(sql, null);
        Log.i("SQLITE", cursor.toString());

        if (cursor != null && cursor.getCount() > 0) {

            for(String a : cursor.getColumnNames()){
                Log.i("SQLITE", a);
            }
            cursor.moveToFirst();


//            Log.i("SQLITE", "START");
//            Log.i("SQLITE", Integer.toString(cursor.getInt(0)));
            User user = new User(cursor.getInt(0), cursor.getString(2), cursor.getString(3), cursor.getString(1),cursor.getInt(4), 1, null);
            cursor.close();
            return user;
        } else {
            cursor.close();
            return null;
        }
    }

    public int getID(){
        Cursor cursor = database.query(db.TABLE_USERS, new String[] {db.USER_ID}, null, null, null, null, null, null);

        if(cursor != null){
            if(cursor.moveToFirst()){
                return cursor.getInt(0);
            }
        }

        return -1;
    }

    public String getName(){
        Cursor cursor = database.query(db.TABLE_USERS, new String[] {db.USER_FIRSTNAME, db.USER_LASTNAME}, null, null, null, null, null, null);

        if(cursor != null){
            if(cursor.moveToFirst()){
                return String.format("%s %s",cursor.getString(0), cursor.getString(1));
            }
        }

        return null;
    }

    public int getRole(){
        Cursor cursor = database.query(db.TABLE_USERS, new String[] {db.USER_ROLE}, null, null, null, null, null, null);

        if(cursor != null){
            if(cursor.moveToFirst()){
                return cursor.getInt(0);
            }
        }

        return -1;
    }

    public Cursor getUser() {

        // TODO add image
        Cursor cursor = this.database.query(db.TABLE_USERS, new String[]{db.USER_ID, db.USER_EMAIL, db.USER_FIRSTNAME, db.USER_LASTNAME, db.USER_ROLE, db.USER_IMG}, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    public void logout() {
        String sql = "DELETE FROM " + db.TABLE_USERS;
        database.execSQL(sql);
    }
}
