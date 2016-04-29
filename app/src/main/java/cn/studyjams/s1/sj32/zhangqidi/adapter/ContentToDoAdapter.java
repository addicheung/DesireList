package cn.studyjams.s1.sj32.zhangqidi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.studyjams.s1.sj32.zhangqidi.R;
import cn.studyjams.s1.sj32.zhangqidi.beans.Taskbean;

/**
 * the adapter of the ContentFragmnet
 * Created by AddiCheung on 2016/4/27 0027.
 */
public class ContentToDoAdapter extends RecyclerView.Adapter<ContentToDoAdapter.MyViewHolder> {

    private List<Taskbean> mData;
    private Context context;

    public ContentToDoAdapter(List<Taskbean> mData, Context context) {
        Log.i("ContentToDoAdapter", mData.size() + "");
        this.mData = mData;
        this.context = context;
    }

    public void setData(List<Taskbean> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public ContentToDoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("onCreateViewHolder todo", "");
        View v = LayoutInflater.from(context).inflate(R.layout.item_todo_list, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Taskbean bean = mData.get(position);
        String task = "task";
        Log.i("onBindViewH", bean.getType() + "-");
        (holder).tv_todo_date.setText(context.getResources().getString(R.string.date)+": " + bean.getDate());
        (holder).tv_todo_detail.setText(context.getResources().getString(R.string.hint)+": " + bean.getDetail());
        (holder).tv_todo_points.setText(context.getResources().getString(R.string.point_dialog) + bean.getPoints());
        if ((bean.getType()).equals(task)) {
            holder.iv_todo_type.setText("T");
            holder.iv_todo_type.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            (holder).iv_todo_type.setText("D");
            (holder).iv_todo_type.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        }
        holder.iv_todo_tick.setTag(position);
    }


    @Override
    public int getItemCount() {
        Log.d("getItemCount", String.format("getItemCount: %d", mData.size()));
        return mData.size();
    }

    public void removeItem(Taskbean bean){
       int pos = mData.indexOf(bean);
        mData.remove(pos);
        Log.i("ContentToDoAdapter",mData.size()+"");
        notifyItemRemoved(pos);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int positions);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView iv_todo_type;
        public TextView tv_todo_date;
        public TextView tv_todo_detail;
        public TextView tv_todo_points;
        public ImageView iv_todo_tick;

        public MyViewHolder(View v) {
            super(v);
            iv_todo_type = (TextView) v.findViewById(R.id.iv_todo_type);
            tv_todo_date = (TextView) v.findViewById(R.id.tv_todo_date);
            tv_todo_detail = (TextView) v.findViewById(R.id.tv_todo_detail);
            tv_todo_points = (TextView) v.findViewById(R.id.tv_todo_points);
            iv_todo_tick = (ImageView) v.findViewById(R.id.iv_todo_tick);
            iv_todo_tick.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, this.getPosition());
            }
        }
    }
}
