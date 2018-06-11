package com.konektedi.vs;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.konektedi.vs.home.elections.Elections;
import com.konektedi.vs.motions.Motions;
import com.konektedi.vs.news.NewsFragment;
import com.konektedi.vs.other.SupportActivityMain;
import com.konektedi.vs.student.Login;
import com.konektedi.vs.student.Settings;
import com.konektedi.vs.student.StudentPreferences;
import com.konektedi.vs.student.StudentProfile;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    public static Context contextOfApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contextOfApplication = getApplicationContext();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = findViewById(R.id.tabs);
        ViewPager viewPager = findViewById(R.id.container);
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new Elections(), "elections");
        adapter.AddFragment(new Motions(), "motions");
        adapter.AddFragment(new NewsFragment(), "News");


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public static Context getContextOfApplication() {
        return contextOfApplication;
        //TO UNDERSTAND WHAT IS DONE HERE
        //https://stackoverflow.com/questions/7491287/android-how-to-use-sharedpreferences-in-non-activity-class
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                intent = new Intent(MainActivity.this, StudentProfile.class);
                startActivity(intent);
                break;
            case R.id.logout:
                StudentPreferences.clearPreferences(this);
                intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                break;
            case R.id.settings:
                intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
                break;
            case R.id.about:
                showAbout();
                break;
            case R.id.support:
                intent = new Intent(MainActivity.this, SupportActivityMain.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAbout() {
        String version = "";
        int verCode = 0;

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
            verCode = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.v_about, null);
        TextView versionView = dialogView.findViewById(R.id.versionView);
        TextView verCodeView = dialogView.findViewById(R.id.verCodeView);

        String versionTitle = "Version: " + version;
        String versionCodeTitle = "Build: " + verCode;

        versionView.setText(versionTitle);
        verCodeView.setText(versionCodeTitle);

        builder.setCancelable(true);
        builder.setView(dialogView);

        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitles = new ArrayList<>();

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentTitles.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }

        void AddFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitles.add(title);
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        System.exit(0);
    }
}
