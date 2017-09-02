package me.devhi.timeart;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private TextView txtTitle;
    private LinearLayout btnReportTab, btnMoniterTab, btnHomeTab, btnInfoTab, btnSettingTab;

    private final int FRAGMENT_REPORT = 1;
    private final int FRAGMENT_MONITER = 2;
    private final int FRAGMENT_HOME = 3;
    private final int FRAGMENT_INFO = 4;
    private final int FRAGMENT_SETTING = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTitle = (TextView) findViewById(R.id.txtTitle);

        btnReportTab = (LinearLayout) findViewById(R.id.btnReportTab);
        btnMoniterTab = (LinearLayout) findViewById(R.id.btnMoniterTab);
        btnHomeTab = (LinearLayout) findViewById(R.id.btnHomeTab);
        btnInfoTab = (LinearLayout) findViewById(R.id.btnInfoTab);
        btnSettingTab = (LinearLayout) findViewById(R.id.btnSettingTab);

        btnReportTab.setOnClickListener(this);
        btnMoniterTab.setOnClickListener(this);
        btnHomeTab.setOnClickListener(this);
        btnInfoTab.setOnClickListener(this);
        btnSettingTab.setOnClickListener(this);

        changeFragment(FRAGMENT_HOME);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view == btnHomeTab) {
            changeFragment(FRAGMENT_HOME);
        } else if (view == btnReportTab) {

        } else if (view == btnMoniterTab) {

        } else if (view == btnInfoTab) {
            changeFragment(FRAGMENT_INFO);
        } else if (view == btnSettingTab) {
            changeFragment(FRAGMENT_SETTING);
        }
    }

    private void changeFragment(int fragmentNo) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (fragmentNo){
            case FRAGMENT_HOME:
                HomeFragment homeFragment = new HomeFragment();
                transaction.replace(R.id.fragment_container, homeFragment);
                transaction.commit();
                txtTitle.setText("공기를 봅니다.");
                break;
            case FRAGMENT_INFO:
                InfoFragment infoFragment = new InfoFragment();
                transaction.replace(R.id.fragment_container, infoFragment);
                transaction.commit();
                txtTitle.setText("정보");
                break;
            case FRAGMENT_SETTING:
                SettingFragment settingFragment = new SettingFragment();
                transaction.replace(R.id.fragment_container, settingFragment);
                transaction.commit();
                txtTitle.setText("설정");
                break;
        }

    }
}
