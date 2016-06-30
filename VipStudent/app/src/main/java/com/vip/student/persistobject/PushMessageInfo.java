package com.vip.student.persistobject;

import com.vip.student.receiver.bean.MessageBean;

/**
 * Created by WuYue on 2016/1/18.
 */
public class PushMessageInfo extends BasePersistObject {
    private MessageBean latestMessageBean;
    private boolean hasRead=false;

    public MessageBean getLatestMessageBean() {
        return latestMessageBean;
    }

    public void setLatestMessageBean(MessageBean latestMessageBean) {
        this.latestMessageBean = latestMessageBean;
    }

    public boolean isHasRead() {
        return hasRead;
    }

    public void setHasRead(boolean hasRead) {
        this.hasRead = hasRead;
    }

    private static PushMessageInfo sPushMessageInfo;

    public static PushMessageInfo getInstance(){
        sPushMessageInfo=getPersisObject(PushMessageInfo.class);
        if(sPushMessageInfo==null){
            sPushMessageInfo=new PushMessageInfo();
            sPushMessageInfo.setHasRead(true);
            sPushMessageInfo.setLatestMessageBean(null);
            persisObject(sPushMessageInfo);
        }
        return sPushMessageInfo;
    }

    public void savePushMessageInfo(){
        persisObject(this);
    }

    public static void clearPushMessageInfo() {
        deletePersistObject(PushMessageInfo.class);
        sPushMessageInfo=null;
    }

}
