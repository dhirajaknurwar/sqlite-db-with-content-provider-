package com.sqlitewithasynctask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class SaveEmployeeDetailsActivity extends AppCompatActivity {

    private EditText mEmpId;
    private EditText mEmpName;
    private EditText mEmpDesignation;
    private EditText mEmpSalary;
    private EditText mEmpAddress;

    private TextView mSaveEmployee;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_employee_details_activity_layout);
        initViews();
    }

    private void initViews() {

        mEmpId = (EditText) findViewById(R.id.employeeId);
        mEmpName = (EditText) findViewById(R.id.employeeName);
        mEmpDesignation = (EditText) findViewById(R.id.employeeDesignation);
        mEmpSalary = (EditText) findViewById(R.id.employeeSalary);
        mSaveEmployee = (TextView) findViewById(R.id.saveEmployee);
        mEmpAddress = (EditText) findViewById(R.id.employeeAddress);
        mSaveEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO validate for input field and then save employee data into DB
                if (inputValidate()) {
                    //TODO Procceds to save employee data into DB
                    SaveEmpDataInBackgroundTask saveEmpDataInBackgroundTask = new SaveEmpDataInBackgroundTask(SaveEmployeeDetailsActivity.this);
                    saveEmpDataInBackgroundTask.execute(mEmpId.getText().toString(), mEmpName.getText().toString(), mEmpDesignation.getText().toString(), mEmpSalary.getText().toString(), mEmpAddress.getText().toString());
                }

            }
        });
    }

    private boolean inputValidate() {

        if (mEmpId.getText().toString().length() == 0) {
            showToast("Enter Emp ID");
            return false;
        }

        if (mEmpName.getText().toString().length() == 0) {
            showToast("Enter Emp Name");
            return false;
        }

        if (mEmpDesignation.getText().toString().length() == 0) {
            showToast("Enter Emp Designation");
            return false;
        }

        if (mEmpSalary.getText().toString().length() == 0) {
            showToast("Enter Emp Salary");
            return false;
        }

        if (mEmpAddress.getText().toString().length() == 0) {
            showToast("Enter Emp Address");
            return false;
        }

        return true;
    }

    private void showToast(String msg) {
        Toast.makeText(SaveEmployeeDetailsActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
