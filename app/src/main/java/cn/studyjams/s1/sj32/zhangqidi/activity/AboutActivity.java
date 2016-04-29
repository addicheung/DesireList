package cn.studyjams.s1.sj32.zhangqidi.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import cn.studyjams.s1.sj32.zhangqidi.R;

/**
 * Created by AddiCheung on 2016/4/26 0026.
 */
public class AboutActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tl_about);
        toolbar.setTitle(getResources().getString(R.string.nav_about));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
