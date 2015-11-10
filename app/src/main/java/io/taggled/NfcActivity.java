package io.taggled;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import butterknife.OnClick;

import static butterknife.ButterKnife.bind;
import static butterknife.ButterKnife.findById;

public class NfcActivity extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback {

    private static final String TAG = "NfcActivity";

    NfcAdapter mNfcAdapter;

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

        setupNfcAdapter();
    }

    /**
     * Register to send messages via Android Beam
     *
     * @return registered successfully
     */
    private boolean setupNfcAdapter() {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter != null) {
            mNfcAdapter.setNdefPushMessageCallback(this, this);
            return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // indicates that the activity was started by an Android Beam
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            processIntent(getIntent());
        }
    }

    private void processIntent(Intent intent) {
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        NdefMessage msg = (NdefMessage) rawMsgs[0];  // get the mime type
        printNdef(msg.getRecords());
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        return new NdefMessage(new NdefRecord[]{
                NdefRecord.createMime(getString(R.string.mime_type), getUserId().getBytes()),
                NdefRecord.createApplicationRecord("io.taggled")});
    }

    private String getUserId() {
        // TODO: get unique user id from the backend
        return "12345";
    }

    private void printNdef(NdefRecord[] records) {
        if (records == null) return;

        for (int i = 0; i < records.length; i++) {
            Log.d(TAG, "RECORD " + i + " PAYLOAD: " + new String(records[i].getPayload()));
            Log.d(TAG, "RECORD " + i + " MIME TYPE: " + records[i].toMimeType());
        }
    }
}
