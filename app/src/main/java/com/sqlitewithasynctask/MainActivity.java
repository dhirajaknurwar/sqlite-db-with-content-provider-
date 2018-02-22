package com.sqlitewithasynctask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        TextView mAddEmployee = (TextView) findViewById(R.id.add);
        mAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SaveEmployeeDetailsActivity.class));
            }
        });

        TextView mShowEmployees = (TextView) findViewById(R.id.view);
        mShowEmployees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ShowEmployeeActivity.class));

            }
        });
        new DataSyncAlarm().setAlarm(this);
    }
}
