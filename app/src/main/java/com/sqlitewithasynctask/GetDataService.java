package com.sqlitewithasynctask;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.sqlitewithasynctask.networkcall.APIServices;
import com.sqlitewithasynctask.networkcall.ServiceFactory;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class GetDataService extends Service implements Constants {

    private Subscription mDataSubscription;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getDataFromServer();
        return START_STICKY;
    }

    private void getDataFromServer() {
       /* RequestInterface requestInterface = APIRequest.getRetrofit().create(RequestInterface.class);
        Call<ArrayList<DataModel>> call = requestInterface.getData();
        APIRequest.getInstance().doRequest(call, new RequestListener<ArrayList<DataModel>>() {
            @Override
            public void onResponse(final ArrayList<DataModel> response) {
                if (response != null) {
                    if (response.size() > 0) {
                        saveDataToDB(response);
                        Log.d("RES", "Data Saved in DB");

                    }

                } else {

                }
            }

            @Override
            public void onDisplayError(String errorMsg) {
                stopSelf();
                if (errorMsg != null) {
                    Log.d("ERROR", errorMsg);

                }
            }
        });
        */

        APIServices service = ServiceFactory.createRetrofitService(APIServices.class);
        mDataSubscription = service.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<DataModel>>() {
                    @Override
                    public void onCompleted() {
                        mDataSubscription = null;
                    }

                    @Override
                    public void onError(Throwable e) {
                        mDataSubscription = null;

                    }

                    @Override
                    public void onNext(ArrayList<DataModel> response) {
                        if (response != null) {
                            if (response.size() > 0) {
                                saveDataToDB(response);
                                Log.d("RES", "Data Saved in DB");

                            }

                        }
                    }
                });
    }

    private void saveDataToDB(final List<DataModel> dataModelList) {

        Thread saveDataThread = new Thread(new Runnable() {
            @Override
            public void run() {
                getContentResolver().delete(CAMPAIGN_CONTENT_URI, null, null);
                ArrayList<ContentValues> contentValuesArrayList = new ArrayList<>();

                for (DataModel dataModel : dataModelList) {
                    ContentValues values = new ContentValues();
                    values.put(CAMPAIGN_TITLE, dataModel.getTitle());
                    values.put(CAMPAIGN_DESC, dataModel.getDescription());
                    values.put(CAMPAIGN_IMG, dataModel.getImg());
                    values.put(CAMPAIGN_SELLER_NAME, dataModel.getSellersName());
                    values.put(CAMPAIGN_PRODUCT_PRICE, dataModel.getPrice());
                    contentValuesArrayList.add(values);
                }

                ContentValues[] dataArray = new ContentValues[contentValuesArrayList.size()];

                //Bulk insert
                getContentResolver().bulkInsert(CAMPAIGN_CONTENT_URI,
                        contentValuesArrayList.toArray(dataArray));

                stopSelf();
            }
        });

        saveDataThread.start();

    }
}
