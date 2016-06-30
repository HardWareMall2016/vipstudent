package com.vip.student.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vip.student.db.dbobject.Message;

import java.util.LinkedList;
import java.util.List;

public class MessageDB extends BaseDB {
	public static final String TABLE = "PRACTICE_RECORD";

	public static final String USER_OBJECT_ID = "UserID";
	public static final String MESSAGE_ID = "MessageID";
	public static final String MESSAGE_TYPE_CODE = "MessageTypeCode";
	public static final String MESSAGE_TYPE_NAME = "MessageTypeName";
	public static final String TITLE = "Title";
	public static final String CONTENT = "Content";
	public static final String CREATE_TIME = "CreateTime";
	public static final String STATUS = "Status";
	
	public MessageDB(Context context) {
		super(context);
	}

	public void syncMessage(String userId,Message message){
		SQLiteDatabase db = mDBHelper.getWritableDatabase();

		boolean exist=false;
		String sql = "select * from " + TABLE + " where "+MESSAGE_ID+" = ? ";
		Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(message.MessageID)});
		if (cursor!=null&&cursor.moveToFirst()) {
			exist=true;
		}
		if (cursor != null) {
			cursor.close();
		}

		if(!exist){
			ContentValues values = new ContentValues();
			values.put(USER_OBJECT_ID, userId);
			values.put(MESSAGE_ID, message.MessageID);
			values.put(MESSAGE_TYPE_CODE, message.MessageTypeCode);
			values.put(MESSAGE_TYPE_NAME, message.MessageTypeName);
			values.put(TITLE, message.Title);
			values.put(CONTENT, message.Content);
			values.put(CREATE_TIME, message.CreateTime);
			values.put(STATUS, message.Status);

			db.insert(TABLE, null, values);
		}else{
			//已读才更新
			if(message.Status==1){
				ContentValues values = new ContentValues();
				values.put(STATUS, message.Status);
				db.update(TABLE, values, MESSAGE_ID + "=" + message.MessageID, null);
			}
		}

		db.close();
	}

	public void setMessageRead(long messageId){
		SQLiteDatabase db = mDBHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(STATUS, 1);
		db.update(TABLE, values, MESSAGE_ID + "=" + messageId, null);
		db.close();
	}

	private void save(String userId,Message message){
		SQLiteDatabase db = mDBHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(USER_OBJECT_ID, userId);
		values.put(MESSAGE_ID, message.MessageID);
		values.put(MESSAGE_TYPE_CODE, message.MessageTypeCode);
		values.put(MESSAGE_TYPE_NAME, message.MessageTypeName);
		values.put(TITLE, message.Title);
		values.put(CONTENT, message.Content);
		values.put(CREATE_TIME, message.CreateTime);
		values.put(STATUS, message.Status);

		db.insert(TABLE, null, values);
		db.close();
	}

	public void update(Message message){
		SQLiteDatabase db = mDBHelper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(MESSAGE_ID, message.MessageID);
		values.put(MESSAGE_TYPE_CODE, message.MessageTypeCode);
		values.put(MESSAGE_TYPE_NAME, message.MessageTypeName);
		values.put(TITLE, message.Title);
		values.put(CONTENT, message.Content);
		values.put(CREATE_TIME, message.CreateTime);
		values.put(STATUS, message.Status);

		db.update(TABLE, values, _ID + "=" + message.ID, null);

		db.close();
	}

	public void delete(long id) {
		SQLiteDatabase db = mDBHelper.getWritableDatabase();
		String sql = "delete from " + TABLE + " where " + _ID + "=" + id;
		db.execSQL(sql);
		db.close();
	}

	public void deleteAll() {
		SQLiteDatabase db = mDBHelper.getWritableDatabase();
		String sql = "delete from " + TABLE;
		db.execSQL(sql);
		db.close();
	}

	public Message queryLatestMessage(String userObjectID,int msgTypeCode){
		SQLiteDatabase db = mDBHelper.getReadableDatabase();

		String sql = "select * from " + TABLE + " where "+USER_OBJECT_ID+" = ? and "+MESSAGE_TYPE_CODE+" =? order by " + CREATE_TIME + " desc limit 1";
		Cursor cursor = db.rawQuery(sql, new String[]{userObjectID, String.valueOf(msgTypeCode)});

		Message data=null;
		if (cursor.moveToFirst()) {
			data = fromCursor(cursor);
		}
		if (cursor != null) {
			cursor.close();
		}

		db.close();
		return data;
	}

	public List<Message> queryMessage(String userObjectID,int msgTypeCode) {
		SQLiteDatabase db = mDBHelper.getReadableDatabase();
		List<Message> dataList = new LinkedList<>();

		String sql = "select * from " + TABLE + " where "+USER_OBJECT_ID+" = ? and "+MESSAGE_TYPE_CODE+" =? order by " + CREATE_TIME + " desc";
		Cursor cursor = db.rawQuery(sql, new String[]{userObjectID, String.valueOf(msgTypeCode)});

		Message data;
		if (cursor.moveToFirst()) {
			data = fromCursor(cursor);
			dataList.add(data);
			while (cursor.moveToNext()) {
				data = fromCursor(cursor);
				dataList.add(data);
			}
		}

		if (cursor != null) {
			cursor.close();
		}

		db.close();

		return dataList;
	}

	private Message fromCursor(Cursor cursor) {
		Message data = new Message();

		final int idIndex = cursor.getColumnIndexOrThrow(_ID);
		final int MessageIDIndex = cursor.getColumnIndexOrThrow(MESSAGE_ID);
		final int MessageTypeCodeIndex = cursor.getColumnIndexOrThrow(MESSAGE_TYPE_CODE);
		final int MessageTypeNameIndex = cursor.getColumnIndexOrThrow(MESSAGE_TYPE_NAME);
		final int TitleIndex = cursor.getColumnIndexOrThrow(TITLE);
		final int ContentIndex = cursor.getColumnIndexOrThrow(CONTENT);
		final int CreateTimeIndex = cursor.getColumnIndexOrThrow(CREATE_TIME);
		final int StatusIndex = cursor.getColumnIndexOrThrow(STATUS);

		data.ID = cursor.getInt(idIndex);
		data.MessageID = cursor.getInt(MessageIDIndex);
		data.MessageTypeCode = cursor.getInt(MessageTypeCodeIndex);
		data.MessageTypeName = cursor.getString(MessageTypeNameIndex);
		data.Title= cursor.getString(TitleIndex);
		data.Content = cursor.getString(ContentIndex);
		data.CreateTime = cursor.getLong(CreateTimeIndex);
		data.Status= cursor.getInt(StatusIndex);

		return data;
	}

}
