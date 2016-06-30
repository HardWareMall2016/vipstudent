package com.vip.student.utils;

import com.alibaba.fastjson.JSON;
import com.vip.student.db.OrmDBHelper;
import com.vip.student.my.bean.ChangeListRequestBean;
import com.vip.student.network.BaseRequestBean;
import com.vip.student.persistobject.UserInfo;

import org.aisen.orm.extra.Extra;

import java.util.List;

/**
 * Created by WuYue on 2016/3/16.
 */
public class CacheUtility {
    public static Extra getExtra(String url,BaseRequestBean requestBean){
        //只比对具体参数部分，BaseRequestBean中的值始终保持不变
        //每次登录后token都发生变化,UserId 已经在Extra.owner中体现了
        String originToken=requestBean.getToken();
        String originUserId=requestBean.getUserId();
        //去掉差异化的值
        requestBean.setToken("");
        requestBean.setUserId("");
        String key=url+":"+ JSON.toJSONString(requestBean);
        //还原原始值
        requestBean.setToken(originToken);
        requestBean.setUserId(originUserId);

        //String userId=String.valueOf(UserInfo.getCurrentUser().getUserID());
        Extra extra = new Extra(UserInfo.getCurrentUser().getUserId(), Utils.md5(key));
        return extra;
    }

    public static <T> List<T> findCacheData(String url,BaseRequestBean requestBean, Class<T> responseCls) {
        if(UserInfo.getCurrentUser()==null||!UserInfo.getCurrentUser().isLogined()){
            return null;
        }
        Extra extra = getExtra(url, requestBean);
        List<T> beanList = OrmDBHelper.getCacheSqlite().select(extra, responseCls);

        return beanList;
    }

    public static <T> void putCacheDataList(String url, BaseRequestBean requestBean, List<T> dataList,Class<T> cls){
        if(UserInfo.getCurrentUser()==null||!UserInfo.getCurrentUser().isLogined()){
            return ;
        }
        if(dataList==null){
            return;
        }
        //先清空对应的缓存
        Extra extra = getExtra(url, requestBean);
        OrmDBHelper.getCacheSqlite().deleteAll(extra, cls);

        if(dataList.size()>0){
            OrmDBHelper.getCacheSqlite().insert(extra, dataList);
        }

        /*List<DailyLesson> tempList = DBHelper.getCacheSqlite().select(DailyLesson.class,null,null,null,null,"StartTime asc",null);
        Log.i("CacheUtility", "===================Total Size = " + tempList.size() + "====================");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (DailyLesson lesson:tempList){
            Log.i("CacheUtility","LessonId = "+lesson.getLessonId()+" Start Time = "+dateFormat.format(lesson.getStartTime()));
        }*/
    }

    public static <T> void clearCacheData(Class<T> responseCls){
        OrmDBHelper.getCacheSqlite().deleteAll(null,responseCls);
    }

    public static void clearAllCacheData(){
        OrmDBHelper.getCacheSqlite().deleteAll(null, ChangeListRequestBean.DataEntity.class);
        /*OrmDBHelper.getCacheSqlite().deleteAll(null, DailyLesson.class);
        OrmDBHelper.getCacheSqlite().deleteAll(null, WeekLesson.class);
        OrmDBHelper.getCacheSqlite().deleteAll(null, WeekPublicLesson.class);
        OrmDBHelper.getCacheSqlite().deleteAll(null, StudentBean.class);*/
    }
}
