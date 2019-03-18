package com.gentwo.th.spotbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gentwo.th.spotbook.drawer.NavigationView;

public class NavigateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NavigationView navigationView = new NavigationView(getApplicationContext());

        setContentView(navigationView);
    }
}
