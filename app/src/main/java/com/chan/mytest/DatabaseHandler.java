package com.chan.mytest;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.security.cert.Extension;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String LOG = "DatabaseHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "USERDB.db";
    private static final String TABLE_NAME = "USER";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHOTO = "image";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ADDRESS = "address";
    SQLiteDatabase db;
    ContentResolver contentResolver;
    Context context;

    final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +"(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_NAME + " TEXT NOT NULL," +
            KEY_MOBILE + " TEXT NOT NULL," +
            KEY_EMAIL + " TEXT NOT NULL," +
            KEY_ADDRESS + " TEXT NOT NULL," +
            KEY_PHOTO + " BLOB NOT NULL" + ");" ;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        db  = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(CREATE_TABLE);
            Log.e(LOG, "Table is created");
        }catch (Exception e){
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void insertData(String name, String mobile, String email, String address, byte[] image){
        try{
            db.isOpen();
            ContentValues cv = new ContentValues();
            cv.put(KEY_NAME, name);
            cv.put(KEY_MOBILE, mobile);
            cv.put(KEY_EMAIL,email);
            cv.put(KEY_ADDRESS, address);
            cv.put(KEY_PHOTO, image);
            long hh = db.insert(TABLE_NAME, null, cv);
            if(hh != 0){
                Toast.makeText(context,"Save successfully",Toast.LENGTH_LONG).show();
                db.close();
            }else{
                Toast.makeText(context,"Fail to data submitted",Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    public Cursor getData(String sql){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }
    public List<USER> getUserDetails(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<USER> users = new ArrayList<USER>();
        Cursor c = db.rawQuery("select * from USER where id =" + id, null);
        while (c.moveToNext()) {
            String name = c.getString(c.getColumnIndex(KEY_NAME));
            String mobile = c.getString(c.getColumnIndex(KEY_MOBILE));
            String email = c.getString(c.getColumnIndex(KEY_EMAIL));
            String address = c.getString(c.getColumnIndex(KEY_ADDRESS));
            byte[] imageBytes = c.getBlob(c.getColumnIndex(KEY_PHOTO));
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            users.add(new USER(name, mobile, email, address, bitmap));
        }
        return users;
    }

    public ArrayList<USER> getAllToUser() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<USER> users = new ArrayList<USER>();
            Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME, null);

            if (c.getCount() != 0) {
                while (c.moveToNext()) {
                    int id = c.getInt(c.getColumnIndex(KEY_ID));
                    String name = c.getString(c.getColumnIndex(KEY_NAME));
                    String mobile = c.getString(c.getColumnIndex(KEY_MOBILE));
                    String email = c.getString(c.getColumnIndex(KEY_EMAIL));
                    String address = c.getString(c.getColumnIndex(KEY_ADDRESS));
                    byte[] imageBytes = c.getBlob(c.getColumnIndex(KEY_PHOTO));
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    users.add(new USER(id,name,mobile,email,address,bitmap));

                    Log.d(LOG,"User "+users);
                }
                return users;
            } else {
                Toast.makeText(context, "No record available ", Toast.LENGTH_LONG).show();
                return null;
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }

    }

    public void insertData(USER user) {
        try{

            Bitmap bitmap = user.getImage();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            ContentValues cv = new ContentValues();
            cv.put(KEY_NAME, user.getName());
            cv.put(KEY_PHOTO, byteArray);

            long hh = db.insert(TABLE_NAME, null, cv);
            if(hh != 0){
                Toast.makeText(context,"Save successfully",Toast.LENGTH_LONG).show();
                db.close();
            }else{
                Toast.makeText(context,"Fail to data submitted",Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
