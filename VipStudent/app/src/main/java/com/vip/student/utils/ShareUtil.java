package com.vip.student.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.vip.student.base.App;

public class ShareUtil {
	private static final String PREFERENCE_NAME = "KYKP";
	public static final String REMIND_SETTING ="remind_setting";
	public static final String REMIND_SETTING_VOICE ="remind_setting_voice";
	public static final String REMIND_SETTING_POPUP ="remind_setting_popup";
	public static final String REMIND_SETTING_SHOCK ="remind_setting_shock";

	//version版本  判断引导页是否显示
	public static final String VERSION = "version";

	//boolean 开关
	public static final String VALUE_TURN_OFF ="0";
	public static final String VALUE_TURN_ON ="1";

	//极光推送别名
	public static final String JPUSH_ALIAS = "JPush_Alias";

	private static SharedPreferences getInstance() {
		return App.ctx.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
	}

	public static void setValue(String key, String value) {
		Editor edit = getInstance().edit();
		edit.putString(key, value);
		edit.commit();
	}
	public static String getStringValue(String key) {
		return getInstance().getString(key, null);
	}
	public static String getStringValue(String key,String def) {
		return getInstance().getString(key, def);
	}
}
