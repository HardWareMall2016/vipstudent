package com.vip.student.db;

import android.content.Context;

public class BaseDB {
	public static final String _ID = "_id";
	
	protected DBHelper mDBHelper;
	
	protected Context mContext;
	
	public BaseDB(Context context){
		mContext=context;
		mDBHelper = new DBHelper(context);
	}
}
