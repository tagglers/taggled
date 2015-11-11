package io.taggled.nfc;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import io.taggled.R;
import io.taggled.gcm.InfoTask;

public class NfcActivity extends AppCompatActivity implements InfoTask.TagglerTaskListener{

    private NfcController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InfoTask task = new InfoTask();
                task.setListener(NfcActivity.this);
                task.execute(9);

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mController = new NfcController(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mController.onResume(getIntent());
    }

    @Override
    public void onTaskComplete(String data) {
        Snackbar.make(findViewById(R.id.toolbar), data, Snackbar.LENGTH_LONG).show();
    }
}
