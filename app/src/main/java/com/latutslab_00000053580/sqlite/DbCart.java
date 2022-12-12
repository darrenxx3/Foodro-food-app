package com.latutslab_00000053580.sqlite;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.latutslab_00000053580.foodro.Cart;

import java.util.ArrayList;

public class DbCart {

    private DatabaseHelper db;
    private Context context;
    private SQLiteDatabase database;

    public DbCart(Context context) {
        this.context = context;
    }

    public DbCart open() throws SQLException {
        db = new DatabaseHelper(context);
        database = db.getWritableDatabase();
        return this;
    }

    public void close(){
        db.close();
    }

    public int getCartMerchantID(){
        Cursor cursor = null;
        String selectQuery = String.format("SELECT %s from %s LIMIT 1", db.CART_MERCHANT_ID, db.TABLE_CART);
        cursor = database.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            return cursor.getInt(0);
        }

        return -1;
    }

    public Cursor getCartItem(int itemID){
        Cursor cursor = null;
        String selectQuery = String.format("SELECT %s FROM %s WHERE %s = %s ", db.CART_MERCHANT_ID, db.TABLE_CART, db.CART_ITEM_ID, itemID);
        cursor = database.rawQuery(selectQuery, null);

        return cursor;
    }

    public void minusCartQty(int itemID){
        String updateQuery = String.format("UPDATE %s SET " +
                        "%s = %s - 1 " +
                        "WHERE %s = %s",
                db.TABLE_CART, db.CART_QUANTITY, db.CART_QUANTITY,
                db.CART_ITEM_ID, itemID);

        database.execSQL(updateQuery);
        Toast.makeText(context, "Qty Decreased", Toast.LENGTH_SHORT).show();
        //database.update(db.TABLE_CART, values, String.format("%S = %s", db.CART_ITEM_ID, itemID) , null);
        return;
    }

    public void addCartQty(int itemID){
        String updateQuery = String.format("UPDATE %s SET " +
                        "%s = %s + 1 " +
                        "WHERE %s = %s",
                db.TABLE_CART, db.CART_QUANTITY, db.CART_QUANTITY,
                db.CART_ITEM_ID, itemID);

        database.execSQL(updateQuery);
        Toast.makeText(context, "Qty Decreased", Toast.LENGTH_SHORT).show();
        //database.update(db.TABLE_CART, values, String.format("%S = %s", db.CART_ITEM_ID, itemID) , null);
        return;
    }

    public ArrayList<Cart> getCartList(){
        ArrayList<Cart> cartArrayList = new ArrayList<Cart>();
        String query = "SELECT * FROM " + db.TABLE_CART;
        Cursor c = database.rawQuery(query, null);

        try{

            if(!c.moveToFirst()) return null;

            do{
                Log.i("SQLite", "Item ID: "+ c.getInt(0));
                cartArrayList.add(new Cart(c.getInt(0), c.getInt(1), c.getString(2), c.getInt(3), c.getString(4), c.getInt(5)));
            } while(c.moveToNext());
        } finally{
            c.close();
        }


        return cartArrayList;
    }

    public int getTotal(){
        String sumQuery = String.format("SELECT SUM(%s * %s) AS Total FROM %s", db.CART_QUANTITY, db.CART_ITEM_PRICE, db.TABLE_CART);
        Cursor cursor = database.rawQuery(sumQuery, null);

        if(cursor.moveToFirst()){
            return cursor.getInt(0);
        }

        return 0;
    }

    public int getQty(int itemID){
        String query = String.format("SELECT %s FROM %s where %s = %d", db.CART_QUANTITY, db.TABLE_CART, db.CART_ITEM_ID, itemID);
        Cursor cursor = database.rawQuery(query, null);

        if(cursor.moveToFirst()){
            return cursor.getInt(0);
        }

        return 0;

    }

    //using this method we can add users to user table
    public void addCart(Cart cart) {

        //create content values to insert
        ContentValues values = new ContentValues();
        int itemID = cart.getItemID();

        values.put(db.CART_ITEM_ID, itemID);
        values.put(db.CART_QUANTITY, cart.getQuantity());
        values.put(db.CART_ITEM_PRICE, cart.getPrice());
        values.put(db.CART_ITEM_NAME, cart.getName());
        values.put(db.CART_IMAGE, cart.getImage());
        values.put(db.CART_MERCHANT_ID, cart.getMerchantID());

        int merchant = getCartMerchantID();

        if(merchant == -1){
            database.insert(db.TABLE_CART, null, values);
            Toast.makeText(context, "Food added to cart", Toast.LENGTH_SHORT).show();
            return;
        } else if(merchant == cart.getMerchantID()){
            Cursor cursorItem = getCartItem(itemID);

            if(cursorItem != null && cursorItem.getCount() > 0){

                String updateQuery = String.format("UPDATE %s SET " +
                                "%s = %s + %d " +
                                "WHERE %s = %s",
                        db.TABLE_CART, db.CART_QUANTITY, db.CART_QUANTITY, cart.getQuantity(),
                        db.CART_ITEM_ID, itemID);

                database.execSQL(updateQuery);
                Toast.makeText(context, "Food updated to cart", Toast.LENGTH_SHORT).show();
                //database.update(db.TABLE_CART, values, String.format("%S = %s", db.CART_ITEM_ID, itemID) , null);
                return;

            }

            database.insert(db.TABLE_CART, null, values);
            Toast.makeText(context, "Food added to cart", Toast.LENGTH_SHORT).show();
            return;

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setTitle("Confirmation");
            builder.setMessage("Another resto detected, are you sure want to continue? your carts will be clear.");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing but close the dialog

                    clearCart();
                    database.insert(db.TABLE_CART, null, values);
                    Toast.makeText(context, "Cart cleared and food added to cart", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Do nothing
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            alert.show();
            return;
        }


    }

    public void clearCart(){
        database.execSQL("delete from " + db.TABLE_CART);
    }

    public void deleteCart (int itemID){
        String query = String.format("DELETE FROM %s WHERE %s = %d",
                db.TABLE_CART, db.CART_ITEM_ID, itemID, db.CART_MERCHANT_ID);
        database.execSQL(query);
    }

}
