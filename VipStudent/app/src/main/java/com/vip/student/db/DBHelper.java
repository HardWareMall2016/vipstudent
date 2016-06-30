package com.vip.student.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "vip_student.db";

	private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + MessageDB.TABLE + " ("
                    + MessageDB._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + MessageDB.USER_OBJECT_ID + " TEXT NOT NULL,"
                    + MessageDB.MESSAGE_ID + " INTEGER NOT NULL,"
                    + MessageDB.MESSAGE_TYPE_CODE + " INTEGER NOT NULL,"
                    + MessageDB.MESSAGE_TYPE_NAME + " TEXT DEFAULT NULL,"
                    + MessageDB.TITLE + " TEXT DEFAULT NULL,"
                    + MessageDB.CONTENT + " TEXT DEFAULT NULL,"
                    + MessageDB.CREATE_TIME + " INTEGER NOT NULL DEFAULT 0,"
                    + MessageDB.STATUS + " INTEGER NOT NULL DEFAULT 1"
                    + ");");
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i("DBHelper", "onUpgrade oldVersion = " + oldVersion + " newVersion = " + newVersion);
            //删除无用表
            db.execSQL("DROP TABLE IF EXISTS "+MessageDB.TABLE);
    }

}
