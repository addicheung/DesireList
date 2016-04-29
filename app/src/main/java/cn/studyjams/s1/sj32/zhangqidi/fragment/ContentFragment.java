package cn.studyjams.s1.sj32.zhangqidi.fragment;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.studyjams.s1.sj32.zhangqidi.R;
import cn.studyjams.s1.sj32.zhangqidi.util.DataBaseHelper;
import cn.studyjams.s1.sj32.zhangqidi.util.OperateTable;
import cn.studyjams.s1.sj32.zhangqidi.adapter.ContentDoneAdapter;
import cn.studyjams.s1.sj32.zhangqidi.adapter.ContentToDoAdapter;
import cn.studyjams.s1.sj32.zhangqidi.beans.Taskbean;

/**
 * Created by AddiCheung on 2016/4/26 0026.
 */
public class ContentFragment extends Fragment {

    List<Taskbean> mData = new ArrayList<>();
    private int type;
    private RecyclerView recyclerView;
    private ContentDoneAdapter contentDoneAdapter;
    private ContentToDoAdapter contentToDoAdapter;
    private LinearLayoutManager limanager;
    private IntentFilter intentFilter;
    private TextView tv_content_isshow;

    /**
     * return the instance od ContentFragment
     *
     * @return
     */
    public static ContentFragment getInstance(int TYPE) {
        Bundle bundle = new Bundle();
        bundle.putInt("TYPE", TYPE);
        ContentFragment fragment = new ContentFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt("TYPE");
        intentFilter = new IntentFilter();
        intentFilter.addAction("cn.sjudyjams.s1.sj32.INSERT");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rlv_content);
        tv_content_isshow = (TextView) view.findViewById(R.id.tv_content_isshow);
        limanager = new LinearLayoutManager(getContext());
        limanager.setOrientation(LinearLayoutManager.VERTICAL);
        if (type == 0) {
            setUpToDoData();
        } else if (type == 1) {
            setUpDoneData();
        }
        recyclerView.addOnScrollListener(scrollListener);
        return view;

    }

    /**
     * IF recyclerView is scrooled then hide or show the FAB
     * @param recyclerView
     * @param dx
     * @param dy
     */
    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        private static final int HIDE_THRESHOLD = 15;
        private int scrolledDistance = 0;
        private boolean controlsVisible = true;
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
                getActivity().findViewById(R.id.fab).animate().translationY(300f).setInterpolator(new AccelerateInterpolator(2)).start();
                controlsVisible = false;
                scrolledDistance = 0;
            } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
                getActivity().findViewById(R.id.fab).animate().translationY(0f).setInterpolator(new AccelerateInterpolator(2)).start();
                controlsVisible = true;
                scrolledDistance = 0;
            }
            if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
                scrolledDistance += dy;
            }
        }
    };

    /**
     * set up the done data
     */
    private void setUpDoneData() {
        mData = loadData(type);
        if(mData.size()>0) {
            tv_content_isshow.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            contentDoneAdapter = new ContentDoneAdapter(mData, getContext());
            recyclerView.setAdapter(contentDoneAdapter);
            recyclerView.setLayoutManager(limanager);
            contentDoneAdapter.setOnItemLongClickListener(onItemLongClickListener);
        }else{
            recyclerView.setVisibility(View.GONE);
            tv_content_isshow.setVisibility(View.VISIBLE);
        }
    }

    /**
     * set up the to do data
     */
    private void setUpToDoData() {
        mData = loadData(type);
        if(mData.size()>0) {
            tv_content_isshow.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            contentToDoAdapter = new ContentToDoAdapter(mData, getContext());
            recyclerView.setAdapter(contentToDoAdapter);
            recyclerView.setLayoutManager(limanager);
            contentToDoAdapter.setOnItemClickListener(onItemClickListener);
        }else{
            recyclerView.setVisibility(View.GONE);
            tv_content_isshow.setVisibility(View.VISIBLE);
        }
    }

    /**
     * OnItemClickListener
     */
    private ContentToDoAdapter.OnItemClickListener onItemClickListener = new ContentToDoAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int positions) {
            Log.i("OnItemClickListener",mData.size()+"");
            Toast.makeText(getContext(), "Well Done！", Toast.LENGTH_SHORT).show();
            int id = view.getId();
            if (id == R.id.iv_todo_tick) {
                if (mData.get(positions).getType().equals("task"))
                    ((ImageView) view).setImageResource(R.drawable.tickgreen);
                else if (mData.get(positions).getType().equals("desire"))
                    ((ImageView) view).setImageResource(R.drawable.tickyellow);
            }
            Taskbean bean = mData.get(positions);
            contentToDoAdapter.removeItem(mData.get(positions));
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            updateData(bean);
        }
    };


    /**
     * updateData from DB
     * @param bean
     */
    private void updateData(Taskbean bean) {
        DataBaseHelper helper = new DataBaseHelper(getContext());
        OperateTable table = new OperateTable(helper.getWritableDatabase());
        table.upDateBean(bean);
        table.closeDB();
        HomeFragment.handler.sendEmptyMessage(0x123);
    }

    /**
     * OnItemLongClickListener
     */
    private ContentDoneAdapter.OnItemLongClickListener onItemLongClickListener = new ContentDoneAdapter.OnItemLongClickListener() {

        @Override
        public void onItemLongClick(View view, int positions) {
            Toast.makeText(getContext(), "Nothing to do", Toast.LENGTH_SHORT).show();
            Log.i("LongClick", "");
        }
    };

    /**
     * load data from DB
     * @param type
     * @return
     */
    private List<Taskbean> loadData(int type) {
        List<Taskbean> list;
        DataBaseHelper helper = new DataBaseHelper(getContext());
        OperateTable table = new OperateTable(helper.getReadableDatabase());
        list = table.getBeanByCondition(type);
        Log.i("loadData", "" + list.size());
        table.closeDB();
        return list;
    }

}
