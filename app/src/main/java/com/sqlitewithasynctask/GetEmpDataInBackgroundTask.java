package com.sqlitewithasynctask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import java.util.ArrayList;

public class GetEmpDataInBackgroundTask extends AsyncTask<Void, Void, ArrayList<Employee>> implements Constants {
    @SuppressLint("StaticFieldLeak")
    private Context context;

    OnEmployeeDataRetrievedInterface onEmployeeDataRetrievedInterface;

    GetEmpDataInBackgroundTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected ArrayList<Employee> doInBackground(Void... params) {
        Cursor cursor = context.getContentResolver().query(MyContentProvider.CONTENT_URI, null, null, null, null);
        String id;
        String name;
        String designation;
        ArrayList<Employee> employeeArrayList = new ArrayList<>();
        int salary;
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {

                    id = cursor.getString(cursor.getColumnIndex(EMP_ID));
                    name = cursor.getString(cursor.getColumnIndex(EMP_NAME));
                    designation = cursor.getString(cursor.getColumnIndex(EMP_DESIGNATION));
                    salary = cursor.getInt(cursor.getColumnIndex(EMP_SALARY));

                    Employee employee = new Employee();
                    employee.setEmpId(id);
                    employee.setEmpName(name);
                    employee.setEmpDesignation(designation);
                    employee.setEmpSalary(salary);
                    employeeArrayList.add(employee);

                }
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return employeeArrayList;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(ArrayList<Employee> employeeArrayList) {
        onEmployeeDataRetrievedInterface.onEmployeeDataRetrieved(employeeArrayList);
    }
}
