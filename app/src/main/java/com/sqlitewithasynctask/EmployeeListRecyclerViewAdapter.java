package com.sqlitewithasynctask;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class EmployeeListRecyclerViewAdapter extends RecyclerView.Adapter<EmployeeListRecyclerViewAdapter.MyViewHolder> {

    private ArrayList<Employee> employeeArrayList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mId, mName, mDesignation, mSalary;

        public MyViewHolder(View view) {
            super(view);
            mId = (TextView) view.findViewById(R.id.id);
            mName = (TextView) view.findViewById(R.id.name);
            mDesignation = (TextView) view.findViewById(R.id.designation);
            mSalary = (TextView) view.findViewById(R.id.salary);
        }
    }


    public EmployeeListRecyclerViewAdapter(ArrayList<Employee> employeeArrayList) {
        this.employeeArrayList = employeeArrayList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_employee_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Employee employee = employeeArrayList.get(position);
        holder.mId.setText(employee.getEmpId());
        holder.mName.setText(employee.getEmpName());
        holder.mDesignation.setText(employee.getEmpDesignation());
        holder.mSalary.setText(String.valueOf(employee.getEmpSalary()));
    }

    @Override
    public int getItemCount() {
        return employeeArrayList.size();
    }

}
