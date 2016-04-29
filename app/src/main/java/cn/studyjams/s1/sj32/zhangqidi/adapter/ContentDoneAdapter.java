package cn.studyjams.s1.sj32.zhangqidi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.studyjams.s1.sj32.zhangqidi.R;
import cn.studyjams.s1.sj32.zhangqidi.beans.Taskbean;

/**
 * the adapter of the ContentFragmnet
 * Created by AddiCheung on 2016/4/27 0027.
 */
public class ContentDoneAdapter extends RecyclerView.Adapter<ContentDoneAdapter.MyViewHolder> {

    List<Taskbean> mData = new ArrayList<>();
    private Context context;

    public ContentDoneAdapter(List<Taskbean> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    @Override
    public ContentDoneAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_done_list, parent, false);
        MyViewHolder holder = new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Taskbean bean = mData.get(position);
        Log.i("ContentDoneAdapter",bean.getPoints()+"");
        if (bean.getType().equals("task")) {
            (holder).tv_done_type.setText("T");
            (holder).tv_done_type.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            (holder).tv_done_type.setText("D");
            (holder).tv_done_type.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        }
        (holder).tv_done_date.setText(context.getResources().getString(R.string.date)+": "+bean.getDate());
        (holder).tv_done_detail.setText(context.getResources().getString(R.string.hint)+": "+bean.getDetail());
        (holder).tv_done_points.setText(context.getResources().getString(R.string.point_dialog)+bean.getPoints()+"");

        if (bean.getType().equals("task")) {
           /* (holder).tv_done_type.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            (holder).tv_done_result.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            */(holder).tv_done_result.setText("+"+bean.getPoints());
        } else {
           /* (holder).tv_done_type.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            (holder).tv_done_result.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            */(holder).tv_done_result.setText("-"+bean.getPoints());
        }
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    private OnItemLongClickListener onItemLongClickListener;


    public interface OnItemLongClickListener {
       public void onItemLongClick(View view, int positions);
    }


    public void setOnItemLongClickListener(OnItemLongClickListener onItemClickListener) {
        this.onItemLongClickListener = onItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        public TextView tv_done_type;
        public TextView tv_done_date;
        public TextView tv_done_detail;
        public TextView tv_done_points;
        public TextView tv_done_result;

        public MyViewHolder(View v) {
            super(v);
            tv_done_type = (TextView) v.findViewById(R.id.tv_done_type);
            tv_done_date = (TextView) v.findViewById(R.id.tv_done_date);
            tv_done_detail = (TextView) v.findViewById(R.id.tv_done_detail);
            tv_done_points = (TextView) v.findViewById(R.id.tv_done_points);
            tv_done_result = (TextView) v.findViewById(R.id.tv_done_result);
            v.setOnLongClickListener(this);
        }


        @Override
        public boolean onLongClick(View v) {
            if (onItemLongClickListener != null) {
                onItemLongClickListener.onItemLongClick(v, this.getPosition());
            }
        return false;
        }
    }
}
