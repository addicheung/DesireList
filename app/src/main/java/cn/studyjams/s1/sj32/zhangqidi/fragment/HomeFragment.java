package cn.studyjams.s1.sj32.zhangqidi.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.studyjams.s1.sj32.zhangqidi.R;

/**
 * Created by AddiCheung on 2016/4/26 0026.
 */
public class HomeFragment extends Fragment {

    private static final int TODO = 0;
    private static final int DONE = 1;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private static MyPagerAdapter adapter;
    /**
     * return the instance od HomeFragment
     * @return
     */
    public static HomeFragment getInstance(){
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        viewPager = (ViewPager) view.findViewById(R.id.vp_home);
        tabLayout = (TabLayout) view.findViewById(R.id.tl_home);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.todo)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.done)));
        setupAdapter();
        return view;
    }

    /**
     * setup the adapter
     */
    private void setupAdapter() {
        Fragment fr1 = ContentFragment.getInstance(TODO);
        Fragment fr2 = ContentFragment.getInstance(DONE);

        List<Fragment> mData = new ArrayList<>();
        mData.add(fr1);
        mData.add(fr2);
       adapter = new MyPagerAdapter(getChildFragmentManager(),mData);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        adapter.notifyDataSetChanged();
    }


    public class  MyPagerAdapter extends FragmentPagerAdapter{
        private String[] titles = new String[]{getResources().getString(R.string.todo),getResources().getString(R.string.done)};
        private List<Fragment> mData = new ArrayList<>();
        public MyPagerAdapter(FragmentManager fm, List<Fragment> mData) {
            super(fm);
            this.mData = mData;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mData.get(position);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }


        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.i("HomeFragment","handleMessage");
            adapter.notifyDataSetChanged();
            super.handleMessage(msg);
        }
    };


}
