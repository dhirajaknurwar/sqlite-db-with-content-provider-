package com.sqlitewithasynctask;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class LoadDataRecyclerViewAdapter extends RecyclerView.Adapter<LoadDataRecyclerViewAdapter.MyViewHolder> {

    private ArrayList<DataModel> dataModelArrayList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, mDescription, mPrice, mSellerName;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.name);
            mSellerName = (TextView) view.findViewById(R.id.sellerName);
        }
    }


    public LoadDataRecyclerViewAdapter(ArrayList<DataModel> dataModelArrayList) {
        this.dataModelArrayList = dataModelArrayList;
    }

    @Override
    public LoadDataRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_load_data, parent, false);

        return new LoadDataRecyclerViewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LoadDataRecyclerViewAdapter.MyViewHolder holder, int position) {
        DataModel dataModel = dataModelArrayList.get(position);
        holder.title.setText(dataModel.getTitle());
        holder.mSellerName.setText(dataModel.getSellersName());
    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

}
