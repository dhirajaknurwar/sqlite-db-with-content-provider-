package com.sqlitewithasynctask;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;


public class SaveEmpDataInBackgroundTask extends AsyncTask<String, Void, String> implements Constants {
    private Context ctx;

    SaveEmpDataInBackgroundTask(Context context) {
        this.ctx = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(EMP_ID, params[0]);
        contentValues.put(EMP_NAME, params[1]);
        contentValues.put(EMP_DESIGNATION, params[2]);
        contentValues.put(EMP_SALARY, params[3]);
       // contentValues.put(EMP_ADDRESS, params[4]);//this you can enable once alter table in next DB version
        ctx.getContentResolver().insert(MyContentProvider.CONTENT_URI, contentValues);

        return "ok";
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            Toast.makeText(ctx, "Employee data Saved Successfully", Toast.LENGTH_SHORT).show();
        }

    }
}
