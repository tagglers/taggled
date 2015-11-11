package io.taggled;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

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
    static final String ITEM_SKU = "android.test.purchased";

    IabHelper mHelper;

    Context mContext;

  //  private Button clickButton;
    private Button buyButton;


    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.fab) FloatingActionButton fab;

//    @OnClick(R.id.fab)
//    void onClickFab(View view) {
//        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate running");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        TextDrawable textDrawable = new TextDrawable("$");
        fab.setImageDrawable(textDrawable);



        mContext = this;

        //buyButton = (Button)findViewById(R.id.buyButton);
        // clickButton = (Button)findViewById(R.id.clickButton);
        //buyButton.setEnabled(false);

        String base64EncodedPublicKey = getResources().getString(R.string.public_key);

        // compute your public key and store it in base64EncodedPublicKey
        mHelper = new IabHelper(this, base64EncodedPublicKey);
        mHelper.enableDebugLogging(true);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    Log.d(TAG, "In-app Billing setup failed: " +
                            result);
                } else {
                    Log.d(TAG, "In-app Billing is set up OK");
                    //consumeItem();
                }
            }
           });

    }
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result,
                                          Purchase purchase)
        {
            Log.d(TAG, "onIabPurchaseFinishedListener running..");
            if (result.isFailure()) {
                // Handle error
                consumeItem();
                return;
            }
            else if (purchase.getSku().equals(ITEM_SKU)) {
                Log.d(TAG, "Purchase SKU match");
                consumeItem();
                //
                //buyButton.setEnabled(false);
            }

        }
    };
    public void consumeItem() {
        Log.d(TAG, "consumeItem running");
        mHelper.queryInventoryAsync(mReceivedInventoryListener);
    }

    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener
            = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result,
                                             Inventory inventory) {

            Log.d(TAG, "onQueryInventoryFinished listener");
            if (result.isFailure()) {
                // Handle failure
            } else {
                Log.d(TAG, "consumeAsync about to run");
                mHelper.consumeAsync(inventory.getPurchase(ITEM_SKU),
                        mConsumeFinishedListener);
            }
        }
    };
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
            new IabHelper.OnConsumeFinishedListener() {
                public void onConsumeFinished(Purchase purchase,
                                              IabResult result) {

                    if (result.isSuccess()) {
                        //clickButton.setEnabled(true);
                        Intent intent = new Intent(mContext, CampaignDetailActivity.class);
                        intent.putExtra(CampaignDetailActivity.EXTRA_START_TAGGLE, true);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {
                        // handle error
                    }
                }
            };
    public void buttonClicked (View view)
    {
        Log.d(TAG, "ButtonClicked");
        //clickButton.setEnabled(false);
        //buyButton.setEnabled(true);
    }
    public void buyClick(View view) {
        mHelper.launchPurchaseFlow(this, ITEM_SKU, 10001,
                mPurchaseFinishedListener, "mypurchasetoken");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data)
    {
        Log.d(TAG, "onActivityResult running");
        if (!mHelper.handleActivityResult(requestCode,
                resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }
}
