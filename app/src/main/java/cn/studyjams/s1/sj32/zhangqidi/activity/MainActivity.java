package cn.studyjams.s1.sj32.zhangqidi.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.studyjams.s1.sj32.zhangqidi.R;
import cn.studyjams.s1.sj32.zhangqidi.util.DataBaseHelper;
import cn.studyjams.s1.sj32.zhangqidi.util.OperateTable;
import cn.studyjams.s1.sj32.zhangqidi.beans.Taskbean;
import cn.studyjams.s1.sj32.zhangqidi.fragment.GuideFragment;
import cn.studyjams.s1.sj32.zhangqidi.fragment.HomeFragment;
import cn.studyjams.s1.sj32.zhangqidi.fragment.ProfileFragment;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FloatingActionButton fab;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private View dialogView;
    private DataBaseHelper helper;
    private OperateTable table;
    private boolean isExit = false;
    private View container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initEvents();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        switch2Home();
    }

    /**
     * switch to HomeFragment
     */
    private void switch2Home() {
        Fragment fr = HomeFragment.getInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.rl_content, fr, "homefragment").commit();
    }

    /**
     * switch to ProfileFragment
     */
    private void switch2Profile() {
        Fragment fr = ProfileFragment.getInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.rl_content, fr, "progfilefragment").commit();
    }

    /**
     * switch to GuideFragment
     */
    private void switch2Guide() {
        Fragment fr = GuideFragment.getInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.rl_content, fr, "guidefragment").commit();
    }

    /**
     * get all views frim R.layout.activity_main
     */
    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        helper = new DataBaseHelper(this);
        table = new OperateTable(helper.getReadableDatabase());
        container = findViewById(R.id.rl_content);
    }

    /**
     * init all events
     */
    private void initEvents() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAddDialog();
                fab.animate().scaleX(0f).scaleY(0f).setInterpolator(new AccelerateInterpolator()).setDuration(300).start();
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });
    }

    /**
     * show the add dialog
     */
    private void setAddDialog() {
        dialogView = this.getLayoutInflater().inflate(R.layout.dialog_add, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();

        final String[] type = {null};
        final int[] points = {0};
        final String[] detail = new String[1];
        /**
         * create listener
         */
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                fab.animate().scaleX(1f).scaleY(1f).setInterpolator(new AccelerateInterpolator()).setDuration(300).start();

            }
        });

        ((SeekBar) dialogView.findViewById(R.id.sb_dialog)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ((TextView) dialogView.findViewById(R.id.tv_dialog_points)).setText("" + progress);
                points[0] = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                return;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                return;
            }
        });

        ((RadioGroup) dialogView.findViewById(R.id.rg_dialog)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radbtn = (RadioButton) dialogView.findViewById(checkedId);
                type[0] = radbtn.getText().toString();
                Log.i("RadioGroup", type[0].toLowerCase());
                type[0] = type[0].toLowerCase();
            }
        });

        final Toast toast = Toast.makeText(MainActivity.this, "type is empty!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        ((Button) dialogView.findViewById(R.id.btn_dialog_add)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextInputLayout) dialogView.findViewById(R.id.tl_dialog)).setErrorEnabled(false);
                detail[0] = ((EditText) dialogView.findViewById(R.id.et_dialog_detail)).getText().toString();
                if (detail[0].equals("")) {
                    ((TextInputLayout) dialogView.findViewById(R.id.tl_dialog)).setError("Empty！");
                    return;
                }
                if (type[0] == null) {
                    toast.show();
                    return;
                }
                if (points[0] == 0) {
                    toast.setText("points must be more than 0 !");
                    toast.show();
                    return;
                }
                Date currentTime = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = formatter.format(currentTime);
                int isdone = 0;
                if (type[0].equals("task")||table.isEnough(points[0])) {
                    Taskbean bean = new Taskbean(0, type[0], dateString, detail[0], points[0], isdone);
                    insertData(bean);
                    dialog.dismiss();
                } else {
                    toast.setText("Not enough points !");
                    toast.show();
                    return;
                }
            }
        });
        ((Button) dialogView.findViewById(R.id.btn_dialog_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * insert data to database
     * @param bean
     */
    private void insertData(Taskbean bean) {
        DataBaseHelper helper = new DataBaseHelper(this);
        OperateTable table = new OperateTable(helper.getWritableDatabase());
        table.insert(bean);
        HomeFragment.handler.sendEmptyMessage(0x123);
        Toast.makeText(MainActivity.this, "Add Succeed!", Toast.LENGTH_SHORT).show();
    }

    /**
     * create menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * menu item is selected will call this method
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }
        return true;
    }

    /**
     * onNavigationItemSelected
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            toolbar.setTitle(R.string.app_name);
            fab.animate().scaleX(1f).scaleY(1f).setInterpolator(new AccelerateInterpolator()).setDuration(300).start();
            switch2Home();
        } else if (id == R.id.nav_profile) {
            toolbar.setTitle(R.string.nav_profile);
            fab.animate().scaleX(0f).scaleY(0f).setInterpolator(new AccelerateInterpolator()).setDuration(300).start();
            switch2Profile();
        } else if (id == R.id.nav_guide) {
            fab.animate().scaleX(0f).scaleY(0f).setInterpolator(new AccelerateInterpolator()).setDuration(300).start();
            toolbar.setTitle(R.string.nav_guide);
            switch2Guide();
        } else if (id == R.id.nav_share) {
            sendEmail();
        } else if (id == R.id.nav_exit) {
            finish();
            System.exit(0);
            return true;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * send an email to me
     */
    private void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, "I find a good app!");
        intent.putExtra(Intent.EXTRA_TEXT, "Hello,I find a good app called DesireList!\nAnd I want to share it whit you!" +
                "The github site of this app is https://github.com/addiz/DesireList");
        intent.setType("text/plain");
        startActivity(intent);

    }

    /**
     * exit press
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                exit();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * exit the app method
     */
    private void exit() {
        if (!isExit) {
            isExit = true;
            Snackbar.make(container,"Press Back Again To Exit",Snackbar.LENGTH_SHORT).show();
            myhandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }


    /**
     * exit handler
     */
    Handler myhandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
}
