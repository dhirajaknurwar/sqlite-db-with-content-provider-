package com.sqlitewithasynctask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;


public class ShowEmployeeActivity extends AppCompatActivity implements OnEmployeeDataRetrievedInterface {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_employee_activity_layout);
        initViews();
    }

    private void initViews() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.employeeSwipeRefreshLayout);
        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                getAndShowEmployeeData();
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.employeeRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ShowEmployeeActivity.this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        getAndShowEmployeeData();
    }

    private void getAndShowEmployeeData() {
        GetEmpDataInBackgroundTask getEmpDataInBackgroundTask = new GetEmpDataInBackgroundTask(ShowEmployeeActivity.this);
        getEmpDataInBackgroundTask.onEmployeeDataRetrievedInterface = this;
        getEmpDataInBackgroundTask.execute();

    }

    @Override
    public void onEmployeeDataRetrieved(ArrayList<Employee> employeeArrayList) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (employeeArrayList != null && employeeArrayList.size() > 0) {
            EmployeeListRecyclerViewAdapter mEmployeeListRecyclerViewAdapter = new EmployeeListRecyclerViewAdapter(employeeArrayList);
            mRecyclerView.setAdapter(mEmployeeListRecyclerViewAdapter);
        }
    }
}
