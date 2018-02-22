package com.sqlitewithasynctask;

import android.net.Uri;

public interface Constants {
    public static String EMP_TABLE_NAME = "employee";
    public static String EMP_ID = "empId";
    public static String EMP_NAME = "empName";
    public static String EMP_DESIGNATION = "empDesignation";
    public static String EMP_SALARY = "empSalary";
    public static String EMP_ADDRESS = "address";

    public static String CAMPAIGN_TABLE_NAME = "campainlist";
    public static String CAMPAIGN_TITLE = "title";
    public static String CAMPAIGN_DESC = "description";
    public static String CAMPAIGN_IMG = "img";
    public static String CAMPAIGN_SELLER_NAME = "sellersName";
    public static String CAMPAIGN_PRODUCT_PRICE = "price";

    public static Uri CAMPAIGN_CONTENT_URI = Uri.parse("content://com.sqlitewithasynctask" + "/" + CAMPAIGN_TABLE_NAME);

}
