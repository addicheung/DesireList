package cn.studyjams.s1.sj32.zhangqidi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.studyjams.s1.sj32.zhangqidi.R;

/**
 * Created by AddiCheung on 2016/4/26 0026.
 */
public class GuideFragment extends Fragment {
    /**
     * return the instance od GuideFragment
     * @return
     */
    public static GuideFragment getInstance(){
        return new GuideFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide,container,false);

        return view;
    }
}
