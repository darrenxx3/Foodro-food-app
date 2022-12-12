package com.latutslab_00000053580.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    //DATABASE NAME
    public static final String DATABASE_NAME = "foodro";

    //DATABASE VERSION
    public static final int DATABASE_VERSION = 3;

    //USERS TABLE
    //TABLE NAME
    public static final String TABLE_USERS = "users";
    public static final String USER_ID = "id";
    public static final String USER_EMAIL = "email";
    public static final String USER_FIRSTNAME = "firstname";
    public static final String USER_LASTNAME = "lastname";
    public static final String USER_ROLE = "role_id";
    public static final String USER_IMG = "image";
    public static final String SQL_TABLE_USERS = " CREATE TABLE " + TABLE_USERS
            + " ( "
            + USER_ID + " INTEGER PRIMARY KEY, "
            + USER_EMAIL + " TEXT,"
            + USER_FIRSTNAME + " TEXT,"
            + USER_LASTNAME + " TEXT,"
            + USER_ROLE + " INTEGER,"
            + USER_IMG + " TEXT"
            + " ) ";

    //CART TABLE
    //TABLE NAME
    public static final String TABLE_CART = "carts";
    public static final String CART_MERCHANT_ID = "merchantID";
    public static final String CART_ITEM_ID = "itemID";
    public static final String CART_ITEM_NAME = "name";
    public static final String CART_ITEM_PRICE = "price";
    public static final String CART_QUANTITY = "quantity";
    public static final String CART_IMAGE = "image";
    public static final String SQL_TABLE_CART = " CREATE TABLE " + TABLE_CART
            + " ( "
            + CART_ITEM_ID + " INTEGER PRIMARY KEY, "
            + CART_QUANTITY + " INTEGER, "
            + CART_ITEM_NAME + " TEXT, "
            + CART_ITEM_PRICE + " INTEGER, "
            + CART_IMAGE + " TEXT, "
            + CART_MERCHANT_ID + " INTEGER"
            + " ) ";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create Table when oncreate gets called
        sqLiteDatabase.execSQL(SQL_TABLE_USERS);
        sqLiteDatabase.execSQL(SQL_TABLE_CART);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //drop table to create new one if database version updated
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.setVersion(oldVersion);
    }
}