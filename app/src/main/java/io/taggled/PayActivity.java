package io.taggled;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.taggled.util.IabHelper;
import io.taggled.util.IabResult;
import io.taggled.util.Inventory;
import io.taggled.util.Purchase;

public class PayActivity extends AppCompatActivity {

    public static final String TAG = PayActivity.class.getSimpleName();
    IabHelper mHelper;

    @Bind(R.id.toolbar) Toolbar toolbar;

    @OnClick(R.id.fab)
    void onClickFab(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        String base64EncodedPublicKey = getResources().getString(R.string.public_key);

        // compute your public key and store it in base64EncodedPublicKey
        mHelper = new IabHelper(this, base64EncodedPublicKey);
        mHelper.enableDebugLogging(true);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    Log.d(TAG, "Problem setting up In-app Billing: " + result);
                } else {
                    Log.d(TAG, "Setup working");
                }
            }
        });
        List additionalSkuList = new ArrayList();
        additionalSkuList.add("testid");

        try {
            mHelper.queryInventoryAsync(mQueryFinishedListener);
        } catch (IllegalStateException ioe) {
            Log.d(TAG, "IllegalStateException, can't query");
        }
        mHelper.launchPurchaseFlow(this, "testid", 10001,
                mPurchaseFinishedListener, "");

    }
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase)
        {
            if (result.isFailure()) {
                Log.d(TAG, "Error purchasing: " + result);
                return;
            }
            else if (purchase.getSku().equals("testid")) {
                // consume the gas and update the UI
                Log.d(TAG, "testid Taggle for good id recognized");
            }
        }
    };
    // Listener that's called when we finish querying the items we own
    IabHelper.QueryInventoryFinishedListener mQueryFinishedListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result,
                                             Inventory inventory) {
            Log.d(TAG, "Queried the inventory finished");
            if (!result.isFailure()) {
                if (inventory.hasPurchase("testid")){
                    //we are premium, do things
                    Log.d(TAG, "Already has purchase");
                }
            }
            else{
                //oops
            }
        }
    };
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_splash, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
