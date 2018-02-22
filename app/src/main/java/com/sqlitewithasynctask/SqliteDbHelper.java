package com.sqlitewithasynctask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqliteDbHelper extends SQLiteOpenHelper implements Constants {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "employee_info.db";
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE " + EMP_TABLE_NAME + "(" + EMP_ID + " text," + EMP_NAME + " text," + EMP_DESIGNATION + " text," +
            EMP_SALARY + " integer);";


    private static final String CREATE_CAMPAIGN_TABLE_QUERY = "CREATE TABLE " + CAMPAIGN_TABLE_NAME + "(" + CAMPAIGN_TITLE + " text," + CAMPAIGN_DESC + " text," + CAMPAIGN_IMG + " text," +
            CAMPAIGN_SELLER_NAME + " EMP_SALARY text," + CAMPAIGN_PRODUCT_PRICE + " integer);";


    public SqliteDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
        db.execSQL(CREATE_CAMPAIGN_TABLE_QUERY);
    }

    public void saveEmployeeData(SQLiteDatabase sqLiteDatabase, String id, String name, String designation, int salary) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMP_ID, id);
        contentValues.put(EMP_NAME, name);
        contentValues.put(EMP_DESIGNATION, designation);
        contentValues.put(EMP_SALARY, salary);
        sqLiteDatabase.insert(EMP_TABLE_NAME, null, contentValues);
    }

    public Cursor getEmployeeData(SQLiteDatabase sqLiteDatabase) {
        String[] projections = {EMP_ID, EMP_NAME, EMP_DESIGNATION, EMP_SALARY};
        return sqLiteDatabase.query(EMP_TABLE_NAME, projections, null, null, null, null, null);
    }

    public Cursor getData(SQLiteDatabase sqLiteDatabase) {
        String[] projections = {CAMPAIGN_TITLE, CAMPAIGN_DESC, CAMPAIGN_IMG, CAMPAIGN_SELLER_NAME, CAMPAIGN_PRODUCT_PRICE};
        return sqLiteDatabase.query(CAMPAIGN_TABLE_NAME, projections, null, null, null, null, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            if (newVersion == 2) {
                try {
                    db.execSQL("ALTER TABLE " + Constants.EMP_TABLE_NAME + " ADD COLUMN " +
                            Constants.EMP_ADDRESS + " TEXT");
                } catch (SQLException e) {
                    Log.i("ADD COLUMN", "Already exists");
                }
            }
        } else {
            deleteData(db);
        }
    }

    private void deleteData(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.EMP_TABLE_NAME);
        // Recreates the database with a new version
        onCreate(db);
    }
}
