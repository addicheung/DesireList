package cn.studyjams.s1.sj32.zhangqidi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.studyjams.s1.sj32.zhangqidi.R;
import cn.studyjams.s1.sj32.zhangqidi.util.DataBaseHelper;
import cn.studyjams.s1.sj32.zhangqidi.util.OperateTable;
import cn.studyjams.s1.sj32.zhangqidi.view.RectangleView;

/**
 * Created by AddiCheung on 2016/4/26 0026.
 */
public class ProfileFragment extends Fragment {


    private TextView tv_profile_totaltask;
    private TextView tv_profile_totaldesire;
    private RectangleView rectangle_profile_left;
    private RectangleView rectangle_profile_earn;
    private RectangleView rectangle_profile_cost;
    private SwipeRefreshLayout srl_profile;
    private DataBaseHelper dbHelper;

    /**
     * return the instance od profileFragment
     *
     * @return
     */
    public static ProfileFragment getInstance() {
        return new ProfileFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        tv_profile_totaltask = (TextView) v.findViewById(R.id.tv_profile_totaltask);
        tv_profile_totaldesire = (TextView) v.findViewById(R.id.tv_profile_totaldesire);
        rectangle_profile_left = (RectangleView) v.findViewById(R.id.rectangle_profile_left);
        rectangle_profile_earn = (RectangleView) v.findViewById(R.id.rectangle_profile_earn);
        rectangle_profile_cost = (RectangleView) v.findViewById(R.id.rectangle_profile_cost);
        srl_profile = (SwipeRefreshLayout) v.findViewById(R.id.srl_profile);
        srl_profile.setColorSchemeColors(new int[]{0xff009688});
        dbHelper = new DataBaseHelper(getContext());
        setData();
        srl_profile.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setData();
            }
        });
        return v;
    }

    private void setData() {
        OperateTable op = new OperateTable(dbHelper.getReadableDatabase());
        int earnPoints = op.getPoints("task");
        int costPoints = op.getPoints("desire");
        int leftPoints = earnPoints-costPoints;
        rectangle_profile_left.setProgressRange(leftPoints,leftPoints*10);
        rectangle_profile_earn.setProgressRange(earnPoints,earnPoints*10);
        rectangle_profile_cost.setProgressRange(Math.abs(costPoints),Math.abs(costPoints)*10);
        int totaltask  =op.getTotalOfType("task");
        int totaldesire  =op.getTotalOfType("desire");
        tv_profile_totaltask.setText(""+totaltask);
        tv_profile_totaldesire.setText(""+totaldesire);
        srl_profile.setRefreshing(false);
    }


}
