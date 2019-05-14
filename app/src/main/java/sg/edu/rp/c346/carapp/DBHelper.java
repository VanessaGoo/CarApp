package sg.edu.rp.c346.carapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    // increment by 1 whenever db schema changes.
    private static final int DATABASE_VER = 1;
    // Filename of the database
    private static final String DATABASE_NAME = "Car.db";

    private static final String TABLE_CAR = "car";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_BRAND = "brand";
    private static final String COLUMN_LITRE = "litre";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO CREATE TABLE Note
        String createTableSql = "CREATE TABLE " + TABLE_CAR +  "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_BRAND + " TEXT,"
                + COLUMN_LITRE + " INTEGER )";
        db.execSQL(createTableSql);
        Log.i("info" ,"created tables");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAR);
        onCreate(db);
    }
    public void insertTask(String brand, Double litre){

        // Get an instance of the database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        // We use ContentValues object to store the values for
        //  the db operation
        ContentValues values = new ContentValues();
        // Store the column name as key and the description as value
        values.put(COLUMN_BRAND, brand);
        // Store the column name as key and the date as value
        values.put(COLUMN_LITRE, litre);
        // Insert the row into the TABLE_TASK
        db.insert(TABLE_CAR, null, values);
        // Close the database connection
        db.close();
    }
    public ArrayList<Car> getTaskContent() {
        ArrayList<Car> tasks = new ArrayList<Car>();
        String selectQuery = "SELECT " + COLUMN_ID + ", "
                + COLUMN_BRAND + ", "
                + COLUMN_LITRE
                + " FROM " + TABLE_CAR;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                Double gpa = cursor.getDouble(2);
                Car obj = new Car(id, name, gpa);
                tasks.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }
}
