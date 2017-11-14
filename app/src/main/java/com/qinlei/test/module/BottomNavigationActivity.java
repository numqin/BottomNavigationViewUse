package com.qinlei.test.module;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.qinlei.test.R;
import com.qinlei.test.fragment.DashBoardFragment;
import com.qinlei.test.fragment.HomeFragment;
import com.qinlei.test.fragment.NotificationFragment;
import com.qinlei.test.fragment.PersonalFragment;
import com.qinlei.test.util.ActivityUtils;
import com.qinlei.test.util.BottomNavigationViewHelper;

import java.util.ArrayList;
import java.util.List;

public class BottomNavigationActivity extends AppCompatActivity {

    private List<Fragment> mFragmentList;
    private Fragment mCurrentFragment;
    private Toolbar mToolbar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    switchFragment(mCurrentFragment, mFragmentList.get(0), item.getTitle());
                    return true;
                case R.id.navigation_dashboard:
                    switchFragment(mCurrentFragment, mFragmentList.get(1), item.getTitle());
                    return true;
                case R.id.navigation_center:
                    //由 BottomNavigationView 上面的 imageview 处理点击事件
                    return true;
                case R.id.navigation_notifications:
                    switchFragment(mCurrentFragment, mFragmentList.get(2), item.getTitle());
                    return true;
                case R.id.navigation_person:
                    switchFragment(mCurrentFragment, mFragmentList.get(3), item.getTitle());
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(getString(R.string.title_home));

        mFragmentList = new ArrayList<>();
        mFragmentList.add(HomeFragment.newInstance());
        mFragmentList.add(DashBoardFragment.newInstance());
        mFragmentList.add(NotificationFragment.newInstance());
        mFragmentList.add(PersonalFragment.newInstance());
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mFragmentList.get(0), R.id.fragment);
        mCurrentFragment = mFragmentList.get(0);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ImageView imageView = (ImageView) findViewById(R.id.navigation_center_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BottomNavigationActivity.this, "Center", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void switchFragment(Fragment from, Fragment to, CharSequence title) {
        if (mCurrentFragment != to) {
            mCurrentFragment = to;
            FragmentTransaction transaction = getSupportFragmentManager().
                    beginTransaction();
            mToolbar.setTitle(title);
            if (!to.isAdded()) {
                transaction.hide(from).add(R.id.fragment, to).commit();
            } else {
                transaction.hide(from).show(to).commit();
            }
        }
    }

}
