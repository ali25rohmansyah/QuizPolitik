package com.example.quizpolitik.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.quizpolitik.R;
import com.example.quizpolitik.ui.fragment.HomeFragment;
import com.example.quizpolitik.ui.fragment.RankingFragment;

public class BaseActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    HomeFragment HomeFragment = new HomeFragment();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frameLayout, HomeFragment);
                    ft.commit();

                    return true;
                case R.id.navigation_ranking:
                    RankingFragment rankingFragment = new RankingFragment();
                    FragmentTransaction ftR = getSupportFragmentManager().beginTransaction();
                    ftR.replace(R.id.frameLayout, rankingFragment);
                    ftR.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);

        HomeFragment HomeFragment = new HomeFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, HomeFragment);
        ft.commit();
    }

}
