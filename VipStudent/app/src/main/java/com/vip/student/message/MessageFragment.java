package com.vip.student.message;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.vip.student.R;
import com.vip.student.base.BaseFragment;
import com.vip.student.db.MessageDB;
import com.vip.student.db.dbobject.Message;
import com.vip.student.message.bean.MessagInfo;
import com.vip.student.message.bean.MsgListResponseBean;
import com.vip.student.network.ApiUrls;
import com.vip.student.network.BaseHttpRequestHandler;
import com.vip.student.network.BaseRequestBean;
import com.vip.student.persistobject.PushMessageInfo;
import com.vip.student.receiver.bean.MessageBean;
import com.vip.student.persistobject.UserInfo;
import com.vip.student.utils.Utils;
import com.vip.student.view.RedDotView;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by WuYue on 2015/12/4.
 */
public class MessageFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    //View
    private ListView mMessageListView;
    private LinearLayout mOtherMsgContent;
    private View mEmptyView;

    //Tools
    private MessageAdapter mAdapter;
    private LayoutInflater mInflater;
    private MessageDB mMessageDB;

    private final SimpleDateFormat mFullDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat mShotDateFormat = new SimpleDateFormat("MM-dd");
    private final SimpleDateFormat mTimeFormat = new SimpleDateFormat("HH:mm");

    //Data
    private List<MessagInfo> mMessageList=new LinkedList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater=inflater;

        mMessageDB=new MessageDB(getActivity());
        getMessageList();

        View contentView=inflater.inflate(R.layout.message_frag_layout,null);

        mOtherMsgContent=(LinearLayout)contentView.findViewById(R.id.other_msg_content);
        mEmptyView=contentView.findViewById(R.id.empty);

        mMessageListView=(ListView)contentView.findViewById(R.id.message_list);
        mAdapter=new MessageAdapter();
        mMessageListView.setAdapter(mAdapter);
        mMessageListView.setOnItemClickListener(this);

        return contentView;
    }

    //上课提醒
    private void populateNotification() {
        mOtherMsgContent.removeAllViews();
        final PushMessageInfo pushMessageInfo = PushMessageInfo.getInstance();
        if (pushMessageInfo.getLatestMessageBean()!=null) {
            View view = mInflater.inflate(R.layout.message_frag_list_item, null);
            mOtherMsgContent.addView(view);

            ImageView imgIcon = (ImageView) view.findViewById(R.id.icon);
            TextView title = (TextView) view.findViewById(R.id.title);
            TextView summary = (TextView) view.findViewById(R.id.summary);
            TextView time = (TextView) view.findViewById(R.id.time);
            final RedDotView redDotView = (RedDotView) view.findViewById(R.id.icon_read);

            MessageBean msg=pushMessageInfo.getLatestMessageBean();
            imgIcon.setImageResource(R.mipmap.icon_msg_remind);
            title.setText(msg.getTitle());
            summary.setText(msg.getContent());
            time.setText(getTimeStr(Utils.parseServerTime(msg.getCreateTime())));
            redDotView.setVisibility(pushMessageInfo.isHasRead() ? View.INVISIBLE : View.VISIBLE);

            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    pushMessageInfo.setHasRead(true);
                    pushMessageInfo.savePushMessageInfo();
                    redDotView.setVisibility(View.INVISIBLE);

                    Intent intent=new Intent(getActivity(),NotificationDetailsActivity.class);
                    intent.putExtra(NotificationDetailsActivity.EXT_KEY_MSG_TYPE,MessageBean.MSG_TYPE_NOTIFY_FOR_CLASS);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //只有在当前Frag是可见时才加载
        if(isVisible()){
            loadMessage();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            releaseAllRequest();
            closeRotateProgressDialog();
        }else{
            loadMessage();
        }
    }

    private void loadMessage(){
        showRotateProgressDialog(getString(R.string.message_loading), true);
        populateNotification();
        queryStudentMessage();
    }

    private void queryStudentMessage(){
        BaseRequestBean requestBean=new BaseRequestBean();
        startRequest(ApiUrls.GET_STUDENT_MSG, requestBean, new BaseHttpRequestHandler() {
            @Override
            public void onRequestFinished() {
                super.onRequestFinished();
                closeRotateProgressDialog();
                checkShowEmptyView();
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
                MsgListResponseBean bean = Utils.parseJsonTostError(content, MsgListResponseBean.class);
                if (bean != null && bean.getData() != null) {
                    for (MsgListResponseBean.DataEntity dataEntity : bean.getData()) {
                        Message mgs = new Message();
                        mgs.MessageID = Integer.parseInt(dataEntity.getMessageID());
                        mgs.MessageTypeCode = Integer.valueOf(dataEntity.getMessageTypeCode());
                        mgs.MessageTypeName = dataEntity.getMessageTypeName();
                        mgs.Title = dataEntity.getTitle();
                        mgs.Content = dataEntity.getContent();
                        mgs.CreateTime = Utils.parseServerTime(dataEntity.getCreateTime());
                        mgs.Status = Integer.valueOf(dataEntity.getStatus());

                        mMessageDB.syncMessage(UserInfo.getCurrentUser().getUserId(), mgs);
                    }
                }

                getMessageList();

                mAdapter.notifyDataSetChanged();
                super.onRequestSucceeded(content);
            }
        });
    }

    private void getMessageList(){
        mMessageList.clear();

        Message sysMsg = mMessageDB.queryLatestMessage(UserInfo.getCurrentUser().getUserId(), Message.MSG_TYPE_SYS);
        if (sysMsg != null) {
            MessagInfo msgInfo = new MessagInfo();
            msgInfo.setMessageID(sysMsg.MessageID);
            msgInfo.setMessageTypeCode(sysMsg.MessageTypeCode);
            msgInfo.setMessageTypeName(sysMsg.MessageTypeName);
            msgInfo.setTitle(sysMsg.Title);
            msgInfo.setContent(sysMsg.Content);
            msgInfo.setCreateTime(sysMsg.CreateTime);
            msgInfo.setStatus(sysMsg.Status);
            mMessageList.add(msgInfo);
        }

        Message notifyMsg = mMessageDB.queryLatestMessage(UserInfo.getCurrentUser().getUserId(),Message.MSG_TYPE_NOTIFY);
        if (notifyMsg != null) {
            MessagInfo msgInfo = new MessagInfo();
            msgInfo.setMessageID(notifyMsg.MessageID);
            msgInfo.setMessageTypeCode(notifyMsg.MessageTypeCode);
            msgInfo.setMessageTypeName(notifyMsg.MessageTypeName);
            msgInfo.setTitle(notifyMsg.Title);
            msgInfo.setContent(notifyMsg.Content);
            msgInfo.setCreateTime(notifyMsg.CreateTime);
            msgInfo.setStatus(notifyMsg.Status);
            mMessageList.add(msgInfo);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        MessagInfo msgInfo=mMessageList.get(i);
        if(msgInfo.getStatus()==0){
            msgInfo.setStatus(1);//已读
            mAdapter.notifyDataSetChanged();
        }

        Intent intent=new Intent(getActivity(),MessageDetailsActivity.class);
        intent.putExtra(MessageDetailsActivity.EXT_KEY_MSG_TYPE,msgInfo.getMessageTypeCode());
        startActivity(intent);
    }

    private class ViewHolder {
        ImageView imgIcon;
        TextView title;
        TextView summary;
        RedDotView redDotView;
        TextView time;
    }

    private class MessageAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return mMessageList.size();
        }

        @Override
        public Object getItem(int i) {
            return mMessageList.get(i);
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
                view = mInflater.inflate(R.layout.message_frag_list_item, null);
                holder.imgIcon = (ImageView) view.findViewById(R.id.icon);
                holder.title = (TextView) view.findViewById(R.id.title);
                holder.summary = (TextView) view.findViewById(R.id.summary);
                holder.time = (TextView) view.findViewById(R.id.time);
                holder.redDotView = (RedDotView) view.findViewById(R.id.icon_read);
                view.setTag(holder);
            }else{
                holder=(ViewHolder)view.getTag();
            }

            MessagInfo msgInfo=mMessageList.get(i);

            switch (msgInfo.getMessageTypeCode()){
                case Message.MSG_TYPE_SYS:
                    holder.imgIcon.setImageResource(R.mipmap.icon_msg_system);
                    break;
                case Message.MSG_TYPE_LEAVE_MSG:
                    holder.imgIcon.setImageResource(R.mipmap.icon_msg_remind);
                    break;
                case Message.MSG_TYPE_NOTIFY:
                    holder.imgIcon.setImageResource(R.mipmap.icon_msg_notify);
                    break;
            }
            holder.title.setText(msgInfo.getMessageTypeName());
            holder.summary.setText(msgInfo.getContent());
            holder.time.setText(getTimeStr(msgInfo.getCreateTime()));
            holder.redDotView.setVisibility(msgInfo.getStatus()==0?View.VISIBLE:View.INVISIBLE);

            return view;
        }
    }

    private String getTimeStr(long time){
        //是否是当天
        String curDateStr=getFormatTimeStr(mFullDateFormat,System.currentTimeMillis());
        String timeStr=getFormatTimeStr(mFullDateFormat,time);

        //当天
        if(curDateStr.equals(timeStr)){
            return getFormatTimeStr(mTimeFormat,time);
        }else{
            return getFormatTimeStr(mShotDateFormat,time);
        }
    }

    private String getFormatTimeStr(SimpleDateFormat format,long time){
        String timeStr = format.format(time);
        return timeStr;
    }

    private void checkShowEmptyView(){
        final PushMessageInfo pushMessageInfo = PushMessageInfo.getInstance();
        int msgCount=mMessageList.size()+(pushMessageInfo.getLatestMessageBean()==null?0:1);
        if(msgCount>0){
            mEmptyView.setVisibility(View.GONE);
        }else{
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }
}
