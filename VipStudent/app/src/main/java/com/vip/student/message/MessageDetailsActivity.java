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
import com.vip.student.db.MessageDB;
import com.vip.student.db.dbobject.Message;
import com.vip.student.message.bean.SetMsgReadRequestBean;
import com.vip.student.network.ApiUrls;
import com.vip.student.persistobject.UserInfo;
import com.vip.student.utils.Utils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by WuYue on 2015/12/11.
 */
public class MessageDetailsActivity extends BaseActivity {
    public final static String EXT_KEY_MSG_TYPE="MSG_TYPE";
    //Views
    private ListView mListViewMessage;

    //Tools
    private MessageAdapter mMessageAdapter;
    private LayoutInflater mInflater;
    private MessageDB mMessageDB;

    //Data
    private int mMessageTypeCode;
    private List<Message> mMsgList=new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        mInflater=getLayoutInflater();
        mMessageDB=new MessageDB(this);

        mMessageTypeCode=getIntent().getIntExtra(EXT_KEY_MSG_TYPE,0);

        //按时间降序排列
        mMsgList=mMessageDB.queryMessage(UserInfo.getCurrentUser().getUserId(),mMessageTypeCode);

        mListViewMessage=(ListView)findViewById(R.id.message_list);
        mMessageAdapter=new MessageAdapter();
        mListViewMessage.setAdapter(mMessageAdapter);

        setMessageRead();
    }

    private void setMessageRead(){
        Message message=null;
        if(mMsgList.size()>0){
            message=mMsgList.get(0);
        }

        if(message==null||message.Status==1){
            return;
        }
        //本地设置为已读
        mMessageDB.setMessageRead(message.MessageID);

        //服务器设为已读
        List<Integer> data=new LinkedList<>();
        data.add((int) message.MessageID);
        SetMsgReadRequestBean requestBean=new SetMsgReadRequestBean();
        requestBean.setData(data);

        startRequest(ApiUrls.SET_MSG_READ,requestBean,null);
    }

    private class ViewHolder {
        ImageView icon;
        TextView content;
        TextView time;
    }

    private class MessageAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mMsgList.size();
        }

        @Override
        public Object getItem(int i) {
            return mMsgList.get(i);
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

                view.setTag(holder);
            }else{
                holder=(ViewHolder)view.getTag();
            }

            Message msg= mMsgList.get(i);

            switch (msg.MessageTypeCode){
                case Message.MSG_TYPE_SYS:
                    holder.icon.setImageResource(R.mipmap.icon_msg_system);
                    break;
                case Message.MSG_TYPE_LEAVE_MSG:
                    holder.icon.setImageResource(R.mipmap.icon_msg_remind);
                    break;
                case Message.MSG_TYPE_NOTIFY:
                    holder.icon.setImageResource(R.mipmap.icon_msg_notify);
                    break;
            }

            holder.content.setText(msg.Content);
            holder.time.setText(Utils.getFullFormateTimeStr(msg.CreateTime));

            return view;
        }
    }
}
