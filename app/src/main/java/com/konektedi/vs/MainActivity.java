package com.konektedi.vs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.konektedi.vs.Elections.Elections;
import com.konektedi.vs.News.News;
import com.konektedi.vs.Petitions.Petitions;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        showFragment(new Elections());
    }

    private boolean showFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.messenger_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.title_elections:
                fragment = new Elections();
                break;
            case R.id.title_petitions:
                fragment = new Petitions();
                break;
            case R.id.title_news:
                fragment = new News();
                break;
        }
        return showFragment(fragment);
    }

}
