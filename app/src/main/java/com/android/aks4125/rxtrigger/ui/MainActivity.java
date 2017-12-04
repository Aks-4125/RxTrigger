package com.android.aks4125.rxtrigger.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.aks4125.rxtrigger.App;
import com.android.aks4125.rxtrigger.R;
import com.aks4125.library.Trigger;
import com.android.aks4125.rxtrigger.ui.fragments.FragmentOne;
import com.android.aks4125.rxtrigger.ui.fragments.FragmentThree;
import com.android.aks4125.rxtrigger.ui.fragments.FragmentTwo;
import com.android.aks4125.rxtrigger.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.fabCounter)
    TextView fabCounter;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    private CompositeDisposable mDisposable;
    private App mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mApp = (App) getApplicationContext();
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        mDisposable = new CompositeDisposable();
        mDisposable.add(
                mApp
                        .reactiveTrigger() // singleton object of trigger
                        .toObservable()
                        .subscribeOn(Schedulers.io()) // push to io thread
                        .observeOn(AndroidSchedulers.mainThread()) // listen calls on main thread
                        .subscribe(object -> { //receive events here
                            if (object instanceof Trigger.Increment) {
                                fabCounter.setText(String.valueOf(Integer.parseInt(fabCounter.getText().toString()) + 1));
                            } else if (object instanceof Trigger.Decrement) {
                                if (Integer.parseInt(fabCounter.getText().toString()) != 0)
                                    fabCounter.setText(String.valueOf(Integer.parseInt(fabCounter.getText().toString()) - 1));
                            } else if (object instanceof Trigger.Reset) {
                                fabCounter.setText("0");
                            }
                        })
        );


        FragmentOne fragmentOne = new FragmentOne();
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                fragmentOne, R.id.vContainer);
        tvTitle.setText("Fragment 1");

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!mDisposable.isDisposed()) {
            mDisposable.clear();
            mDisposable.dispose();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            FragmentOne fragmentOne = new FragmentOne();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragmentOne, R.id.vContainer);
            tvTitle.setText("Fragment 1");

        } else if (id == R.id.nav_gallery) {
            Fragment fragmentTwo = new FragmentTwo();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragmentTwo, R.id.vContainer);

            tvTitle.setText("Fragment 2");
        } else if (id == R.id.nav_slideshow) {
            Fragment fragmentThree = new FragmentThree();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragmentThree, R.id.vContainer);
            tvTitle.setText("Fragment 3");

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @OnClick(R.id.fabReset)
    public void onViewClicked() {
        ((App) getApplicationContext())
                .reactiveTrigger()
                .send(new Trigger.Reset());
    }
}
