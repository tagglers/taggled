package io.taggled;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.OnClick;

import static butterknife.ButterKnife.bind;
import static butterknife.ButterKnife.findById;

public class NfcActivity extends AppCompatActivity {

    private static final String TAG = "NfcActivity";

    @OnClick(R.id.fab)
    void onClickFab(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        bind(this);

        Toolbar toolbar = findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
