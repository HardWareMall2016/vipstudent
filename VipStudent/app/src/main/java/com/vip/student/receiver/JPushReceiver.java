package com.vip.student.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.vip.student.MainActivity;
import com.vip.student.persistobject.PushMessageInfo;
import com.vip.student.receiver.bean.MessageBean;
import com.vip.student.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class JPushReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";

	@Override
	public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
		Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
		
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                        
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
        	Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
        	//processCustomMessage(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
			parseNotification(bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
			openNotification(context,bundle);
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
        	
        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
        	boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
        	Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
        	Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
	}

	private void parseNotification(Bundle bundle) {
		MessageBean bean = parseToMessageBean(bundle);
		if(bean!=null&&MessageBean.MSG_TYPE_NOTIFY_FOR_CLASS.equals(bean.getMessageTypeCode())){
			PushMessageInfo pushMessageInfo= PushMessageInfo.getInstance();
			//如果推送的消息不是最新的就忽略
			MessageBean persisMsg=pushMessageInfo.getLatestMessageBean();
			if(persisMsg!=null&& Utils.parseServerTime(persisMsg.getCreateTime())>Utils.parseServerTime(bean.getCreateTime())){
				return;
			}
			pushMessageInfo.setLatestMessageBean(bean);
			pushMessageInfo.setHasRead(false);
			pushMessageInfo.savePushMessageInfo();
		}
	}

	private void openNotification(Context context,Bundle bundle) {
		MessageBean bean = parseToMessageBean(bundle);
		if(bean!=null&&MessageBean.MSG_TYPE_NOTIFY_FOR_CLASS.equals(bean.getMessageTypeCode())){
			Intent homePageIntent = new Intent(context, MainActivity.class);
			homePageIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
			homePageIntent.putExtra(MainActivity.EXT_KEY_SHOW_PAGE,MainActivity.TAG_MESSAGEE);
			context.startActivity(homePageIntent);
		}
	}

	private MessageBean parseToMessageBean(Bundle bundle){
		if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
			Log.i(TAG, "This message has no Extra data");
		}
		String json=bundle.getString(JPushInterface.EXTRA_EXTRA);
		Log.i(TAG, "Notification EXTRA_EXTRA : " +json);
		Gson gson = new Gson();
		MessageBean bean = null;
		try {
			bean = gson.fromJson(json, MessageBean.class);
		} catch (JsonSyntaxException exp) {
			Log.e(TAG, "fromJson error : " + exp.getMessage());
		}
		return bean;
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
					Log.i(TAG, "This message has no Extra data");
					continue;
				}
				Log.i(TAG, "EXTRA_EXTRA : "+bundle.getString(JPushInterface.EXTRA_EXTRA));
				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it =  json.keys();

					while (it.hasNext()) {
						String myKey = it.next().toString();
						sb.append("\nkey:" + key + ", value: [" +
								myKey + " - " +json.optString(myKey) + "]");
					}
				} catch (JSONException e) {
					Log.e(TAG, "Get message extra JSON error!");
				}

			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}
}
