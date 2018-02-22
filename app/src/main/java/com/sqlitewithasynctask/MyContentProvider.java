package com.sqlitewithasynctask;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.HashMap;

public class MyContentProvider extends ContentProvider implements Constants {

    private static final String AUTHORITY = "com.sqlitewithasynctask";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + EMP_TABLE_NAME);

    private static final int TUTORIALS = 1;

    private static final int CAMPAIGN = 2;

    private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);

    /**
     * A projection map used to select columns from the database
     */
    private static HashMap<String, String> mEmployeeProjectionMap, mCampaignProjectionMap;

    static {
        sURIMatcher.addURI(AUTHORITY, EMP_TABLE_NAME, TUTORIALS);
        sURIMatcher.addURI(AUTHORITY, CAMPAIGN_TABLE_NAME, CAMPAIGN);

        mEmployeeProjectionMap = new HashMap<>();
        mCampaignProjectionMap = new HashMap<>();

        mEmployeeProjectionMap.put(Constants.EMP_ID,
                Constants.EMP_ID);
        mEmployeeProjectionMap.put(Constants.EMP_NAME,
                Constants.EMP_NAME);
        mEmployeeProjectionMap.put(Constants.EMP_DESIGNATION,
                Constants.EMP_DESIGNATION);
        mEmployeeProjectionMap.put(Constants.EMP_SALARY,
                Constants.EMP_SALARY);


        mCampaignProjectionMap.put(Constants.CAMPAIGN_TITLE,
                Constants.CAMPAIGN_TITLE);
        mCampaignProjectionMap.put(Constants.CAMPAIGN_DESC,
                Constants.CAMPAIGN_DESC);
        mCampaignProjectionMap.put(Constants.CAMPAIGN_IMG,
                Constants.CAMPAIGN_IMG);
        mCampaignProjectionMap.put(Constants.CAMPAIGN_SELLER_NAME,
                Constants.CAMPAIGN_SELLER_NAME);
        mCampaignProjectionMap.put(Constants.CAMPAIGN_PRODUCT_PRICE,
                Constants.CAMPAIGN_PRODUCT_PRICE);

    }


    private SqliteDbHelper sqliteDbHelper;

    @Override
    public boolean onCreate() {
        sqliteDbHelper = new SqliteDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();


        switch (sURIMatcher.match(uri)) {

            case TUTORIALS:
                queryBuilder.setTables(EMP_TABLE_NAME);
                queryBuilder.setProjectionMap(mEmployeeProjectionMap);
                break;

            case CAMPAIGN:
                queryBuilder.setTables(CAMPAIGN_TABLE_NAME);
                queryBuilder.setProjectionMap(mCampaignProjectionMap);
                break;
        }

        // Opens the database object in "read" mode, since no writes need to be done.
        SQLiteDatabase db = sqliteDbHelper.getReadableDatabase();

		/*
         * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
        try {
            return queryBuilder.query(
                    db,            // The database to query
                    projection,    // The columns to return from the query
                    selection,     // The columns for the where clause
                    selectionArgs, // The values for the where clause
                    null,          // don't group the rows
                    null,          // don't filter by row groups
                    sortOrder        // The sort order
            );
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        switch (sURIMatcher.match(uri)) {
            case TUTORIALS:
                SQLiteDatabase sqLiteDatabase = sqliteDbHelper.getWritableDatabase();
                long rowId = sqLiteDatabase.insert(EMP_TABLE_NAME, null, values);
                sqLiteDatabase.close();
                if (rowId > 0) {
                    return ContentUris.withAppendedId(CONTENT_URI,
                            rowId);
                }
                break;

            case CAMPAIGN:

                break;
        }
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int count = 0;

        String tableName = "";

        switch (sURIMatcher.match(uri)) {
            case CAMPAIGN:
                tableName = CAMPAIGN_TABLE_NAME;
                break;
        }


        if (!TextUtils.isEmpty(tableName)) {
            // Opens the database object in "write" mode.
            SQLiteDatabase db = sqliteDbHelper.getWritableDatabase();
            count = db.delete(
                    tableName,  // The database table name
                    selection,                     // The incoming where clause column names
                    selectionArgs                  // The incoming where clause values
            );
        }

        // Returns the number of rows deleted.
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        int rowIdRequest = 0;


        SQLiteDatabase sqLiteDatabase = sqliteDbHelper.getReadableDatabase();

        switch (sURIMatcher.match(uri)) {
            case CAMPAIGN:

                String sql = "INSERT INTO " + CAMPAIGN_TABLE_NAME
                        + " VALUES (?, ?, ?, ?, ?);";
                SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);

                sqLiteDatabase.beginTransaction();

                for (ContentValues value : values) {
                    try {
                        statement.clearBindings();
                        statement.bindString(1,
                                value.getAsString(CAMPAIGN_TITLE));
                        statement.bindString(2,
                                value.getAsString(CAMPAIGN_DESC));
                        statement.bindString(3,
                                value.getAsString(CAMPAIGN_IMG));
                        statement.bindString(4,
                                value.getAsString(CAMPAIGN_SELLER_NAME));
                        statement.bindLong(5,
                                value.getAsInteger(CAMPAIGN_PRODUCT_PRICE));

                        statement.execute();
                    } catch (Exception ignore) {
                    }
                }

                sqLiteDatabase.setTransactionSuccessful();
                sqLiteDatabase.endTransaction();

                break;
        }


        return rowIdRequest;
    }
}
