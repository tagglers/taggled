package io.taggled;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import io.taggled.nfc.NfcController;

public class CampaignDetailActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    public static final String EXTRA_START_TAGGLE = "start_taggle";

    private NfcController mController;

    private boolean readyToTaggle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_detail);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mController = new NfcController(this);

        readyToTaggle = getIntent().getBooleanExtra(EXTRA_START_TAGGLE, false);
        Bundle fragmentArgs = new Bundle();

        fragmentArgs.putBoolean(CampaignDetailActivity.EXTRA_START_TAGGLE, readyToTaggle);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), fragmentArgs);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager)findViewById(R.id.container_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                if (readyToTaggle){
                    mController.invokeBeam();
                } else {
                    Intent intent = new Intent(CampaignDetailActivity.this, PayActivity.class);
                    startActivity(intent);
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_swipe, menu);
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


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        Bundle data;

        public SectionsPagerAdapter(FragmentManager fm, Bundle bundle) {
            super(fm);
            data = bundle;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0:
                    return CampaignDetailActivityFragment.newInstance("test", data);
                case 1: return CampaignDetailUpdateActivityFragment.newInstance("test2");
                default:return CampaignDetailActivityFragment.newInstance("test", data);
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "CAMPAIGN";
                case 1:
                    return "CAMPAIGN UPDATES";
            }
            return null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mController.onResume(getIntent());
    }
}
