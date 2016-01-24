package com.jehandadk.picnic.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.jehandadk.picnic.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.frame_fragment)
    FrameLayout frameFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

//        if (getCurrentFragment() == null)
//            setFrameFragment(ProductsFagment.newFragment());
    }

    protected void setFrameFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment, fragment).commit();
    }

    protected void addFrameFragment(Fragment fragment, String fragId) {
        getSupportFragmentManager().beginTransaction().addToBackStack(fragId).replace(R.id.frame_fragment, fragment).commit();
    }

    protected Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.frame_fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
        if (id == R.id.action_about) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
