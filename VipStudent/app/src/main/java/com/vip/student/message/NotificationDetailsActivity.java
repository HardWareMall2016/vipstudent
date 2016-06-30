package com.vip.student.message;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.vip.student.R;
import com.vip.student.base.BaseActivity;
import com.vip.student.message.bean.GetPushMessageBean;
import com.vip.student.message.bean.SetMsgReadRequestBean;
import com.vip.student.network.ApiUrls;
import com.vip.student.network.BaseHttpRequestHandler;
import com.vip.student.network.BaseRequestBean;
import com.vip.student.persistobject.PushMessageInfo;
import com.vip.student.receiver.bean.MessageBean;
import com.vip.student.utils.Utils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by WuYue on 2015/12/11.
 */
public class NotificationDetailsActivity extends BaseActivity {
    public final static String EXT_KEY_MSG_TYPE="MSG_TYPE";
    //Views
    private ListView mListViewMessage;

    //Tools
    private MessageAdapter mMessageAdapter;
    private LayoutInflater mInflater;
    private List<Notification> mNotifications=new LinkedList<>();

    //Data
    private String mMessageTypeCode;

    private class Notification{
        int id;
        String MessageTypeCode;
        String Content;
        String CreateTime;
        long longCreateTime;
        String Status;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        mInflater=getLayoutInflater();

        mMessageTypeCode=getIntent().getStringExtra(EXT_KEY_MSG_TYPE);

        mListViewMessage=(ListView)findViewById(R.id.message_list);
        mMessageAdapter=new MessageAdapter();
        mListViewMessage.setAdapter(mMessageAdapter);
        showRotateProgressDialog(getString(R.string.message_loading), true);
        queryPushMsg();
    }

    private class ViewHolder {
        ImageView icon;
        TextView content;
        TextView time;
    }

    private void setMessageRead(int msgId){
        SetMsgReadRequestBean requestBean=new SetMsgReadRequestBean();
        //服务器设为已读
        List<Integer> data=new LinkedList<>();
        data.add(msgId);
        requestBean.setData(data);

        startRequest(ApiUrls.SET_PUSH_MSG_READ,requestBean,null);
    }

    private void queryPushMsg(){
        BaseRequestBean requestBean=new BaseRequestBean();
        startRequest(ApiUrls.GET_PUSH_MSG, requestBean, new BaseHttpRequestHandler() {
            @Override
            public void onRequestFinished() {
                super.onRequestFinished();
                closeRotateProgressDialog();
            }

            @Override
            public void onRequestFailed(String errorMsg) {
                super.onRequestFailed(errorMsg);
                Utils.toast(errorMsg);
            }

            @Override
            public void onRequestFailedNoNetwork() {
                super.onRequestFailedNoNetwork();
                Utils.toast(R.string.network_disconnect);
            }

            @Override
            public void onTimeout() {
                super.onTimeout();
                Utils.toast(R.string.network_timeout);
            }

            @Override
            public void onRequestSucceeded(String content) {
                GetPushMessageBean bean = Utils.parseJsonTostError(content, GetPushMessageBean.class);
                if (bean != null && bean.getData() != null) {
                    for (GetPushMessageBean.DataEntity data : bean.getData()) {
                        if (data.getMessageTypeCode().equals(mMessageTypeCode)) {
                            Notification notification = new Notification();
                            notification.id = data.getId();
                            notification.CreateTime = data.getCreateTime();
                            notification.longCreateTime = Utils.parseServerTime(data.getCreateTime());
                            notification.Content = data.getContent();
                            notification.MessageTypeCode = data.getMessageTypeCode();
                            notification.Status = data.getStatus();
                            mNotifications.add(notification);
                        }
                    }
                    //最新消息设为已读
                    if (bean.getData().size() > 0 && "0".equals(bean.getData().get(0).getStatus())) {
                        setMessageRead(bean.getData().get(0).getId());
                    }
                    //最新消息同步到本地
                    PushMessageInfo pushMessageInfo = PushMessageInfo.getInstance();
                    GetPushMessageBean.DataEntity latestData=null;
                    if(bean.getData().size()>0){
                        latestData=bean.getData().get(0);
                    }
                    MessageBean persisMsg = pushMessageInfo.getLatestMessageBean();
                    if (persisMsg != null&&latestData!=null&& Utils.parseServerTime(persisMsg.getCreateTime()) < Utils.parseServerTime(latestData.getCreateTime())) {
                        persisMsg.setContent(latestData.getContent());
                        persisMsg.setCreateTime(latestData.getCreateTime());
                        persisMsg.setMessageID(String.valueOf(latestData.getId()));
                        persisMsg.setMessageTypeCode(latestData.getMessageTypeCode());
                        persisMsg.setMessageTypeName(latestData.getMessageTypeName());
                        persisMsg.setStatus("1");
                        persisMsg.setTitle(latestData.getMessageTypeName());//Title是空的，需要用getMessageTypeName中的
                        pushMessageInfo.setLatestMessageBean(persisMsg);
                        pushMessageInfo.setHasRead(true);
                        pushMessageInfo.savePushMessageInfo();
                    }
                }
                mMessageAdapter.notifyDataSetChanged();
                super.onRequestSucceeded(content);
            }
        });
    }

    private class MessageAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mNotifications.size();
        }

        @Override
        public Object getItem(int i) {
            return mNotifications.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if(view==null) {
                holder = new ViewHolder();
                view = mInflater.inflate(R.layout.message_details_list_item, null);
                holder.icon=(ImageView)view.findViewById(R.id.icon);
                holder.content=(TextView)view.findViewById(R.id.content);
                holder.time=(TextView)view.findViewById(R.id.time);

                if(MessageBean.MSG_TYPE_NOTIFY_FOR_CLASS.equals(mMessageTypeCode))
                holder.icon.setImageResource(R.mipmap.icon_msg_remind);

                view.setTag(holder);
            }else{
                holder=(ViewHolder)view.getTag();
            }

            Notification msg= mNotifications.get(i);

            holder.content.setText(msg.Content);
            holder.time.setText(Utils.getFullFormateTimeStr(msg.longCreateTime));

            return view;
        }
    }
}
