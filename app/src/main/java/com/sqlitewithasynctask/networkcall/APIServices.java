package com.sqlitewithasynctask.networkcall;

import com.sqlitewithasynctask.DataModel;

import java.util.ArrayList;

import retrofit2.http.GET;
import rx.Observable;

/*APIServices*/
public interface APIServices {
    /**/

    @GET(APIConstants.GET_DATA)
    Observable<ArrayList<DataModel>> getData();
}
