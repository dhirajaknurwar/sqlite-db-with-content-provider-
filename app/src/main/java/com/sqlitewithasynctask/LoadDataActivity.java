package com.sqlitewithasynctask;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class LoadDataActivity extends AppCompatActivity implements Constants {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_data_activity);
        init();
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.campaignRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        progressBar = (ProgressBar) findViewById(R.id.load_progressBar);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setData(getFromLoadDb());
    }

    private ArrayList<DataModel> getFromLoadDb() {
        Cursor cursor = getContentResolver().query(CAMPAIGN_CONTENT_URI, null, null, null, null);
        ArrayList<DataModel> dataModelArrayList = new ArrayList<>();
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                String title;
                String description;
                String img;
                String sellerName;
                int price;

                while (cursor.moveToNext()) {
                    title = cursor.getString(cursor.getColumnIndex(CAMPAIGN_TITLE));
                    description = cursor.getString(cursor.getColumnIndex(CAMPAIGN_DESC));
                    img = cursor.getString(cursor.getColumnIndex(CAMPAIGN_IMG));
                    sellerName = cursor.getString(cursor.getColumnIndex(CAMPAIGN_SELLER_NAME));
                    price = cursor.getInt(cursor.getColumnIndex(CAMPAIGN_PRODUCT_PRICE));

                    DataModel dataModel = new DataModel();
                    dataModel.setTitle(title);
                    dataModel.setDescription(description);
                    dataModel.setImg(img);
                    dataModel.setSellersName(sellerName);
                    dataModel.setPrice(price);

                    dataModelArrayList.add(dataModel);
                }
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return dataModelArrayList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private void setData(ArrayList<DataModel> dataModelArrayList) {
        recyclerView.setVisibility(View.VISIBLE);
        LoadDataRecyclerViewAdapter loadDataRecyclerViewAdapter = new LoadDataRecyclerViewAdapter(dataModelArrayList);
        recyclerView.setAdapter(loadDataRecyclerViewAdapter);
    }

}