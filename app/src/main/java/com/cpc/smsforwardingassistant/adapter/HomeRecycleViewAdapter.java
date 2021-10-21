package com.cpc.smsforwardingassistant.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cpc.smsforwardingassistant.R;
import com.cpc.smsforwardingassistant.constant.Conf;
import com.cpc.smsforwardingassistant.dao.room.ShortMessage;
import com.cpc.smsforwardingassistant.util.DataUtils;

import java.util.List;

public class HomeRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "适配器";
    private Context context;
    private List<ShortMessage> data;
    private LayoutInflater layoutInflater;

    public HomeRecycleViewAdapter(Context context , List<ShortMessage> data) {
        this.context = context;
        this.data = data;
        layoutInflater = LayoutInflater.from(context);
    }

    /**
     * 设置数据
     * @param data
     */
    public void setShortMessage(List<ShortMessage> data){
        this.data.clear();
        this.data.addAll(data);
        //修改适配器绑定的数据后不用重新刷新
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(viewType, parent, false);
        //因为两种类型布局一样所以不再判断viewType
        return new SMViewHolder(view);
    }


    /**
     *
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //从集合里拿到数据
        ShortMessage sm = data.get(position);
//        Log.d(TAG, "适配器绑定数据: 获取到数据:" + sm);
        int type = getItemViewType(position);
//        Log.d(TAG, "适配器绑定数据: 获取到类型:" + type);
        SMViewHolder smViewHolder = (SMViewHolder) holder;
        if (type == R.layout.home_recycle_short_messorg){
//            Log.d(TAG, "适配器绑定数据: 设置数据:"+DataUtils.longDateToFormatStr(sm.getDate())+sm.getTitle()+sm.getContent()+sm.getPhoneNumber());
            smViewHolder.tvTime.setText(DataUtils.longDateToFormatStr(sm.getDate()));
            smViewHolder.tvInfo.setText(sm.getTitle()+sm.getContent()+sm.getPhoneNumber());
        }else if (type ==R.layout.home_recycle_email_failed){
//            Log.d(TAG, "适配器绑定数据: 设置数据:"+DataUtils.longDateToFormatStr(sm.getDate())+sm.getNotice());
            smViewHolder.tvTime.setText(DataUtils.longDateToFormatStr(sm.getDate()));
            smViewHolder.tvInfo.setText(sm.getNotice()+sm.getTitle()+sm.getContent()+sm.getPhoneNumber());
        } else {
            //R.layout.home_recycle_error_info
            //R.layout.home_recycle_email_success
            //R.layout.home_recycle_email_failed
            //上面三种情况都用下面的方式绑定布局
//            Log.d(TAG, "适配器绑定数据: 设置数据:"+DataUtils.longDateToFormatStr(sm.getDate())+sm.getNotice());
            smViewHolder.tvTime.setText(DataUtils.longDateToFormatStr(sm.getDate()));
            smViewHolder.tvInfo.setText(sm.getNotice());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * 显示类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        ShortMessage shortMessage = data.get(position);
        int type = shortMessage.getType();
        switch (type){
            case Conf.SHORT_MESSAGE:
                return R.layout.home_recycle_short_messorg;
            case Conf.ERROR_INFO:
                return R.layout.home_recycle_error_info;
            case Conf.EMAIL_SUCCESS:
                return R.layout.home_recycle_email_success;
            case Conf.EMAIL_FAILED:
                return R.layout.home_recycle_email_failed;
            default:
                return R.layout.home_recycle_email_failed;
        }
    }

    //getItemViewType两种布局内的组件一样一个viewholder
    class SMViewHolder extends RecyclerView.ViewHolder {

        TextView tvTime;
        TextView tvInfo;

        public SMViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvInfo = itemView.findViewById(R.id.tv_info);
        }
    }
}
