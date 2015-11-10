package io.taggled.nfc;


import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Parcelable;
import android.util.Log;

import io.taggled.R;

/**
 * Include the following code as an intent filter for the calling Activity:
 * <intent-filter>
 *      <action android:name="android.nfc.action.NDEF_DISCOVERED"/>
 *      <category android:name="android.intent.category.DEFAULT"/>
 *      <data android:mimeType="@string/mime_type"/>
 * </intent-filter>
 */
public class NfcController implements NfcAdapter.CreateNdefMessageCallback {

    private static final String TAG = "NfcController";

    private Activity mActivity;
    private NfcAdapter mNfcAdapter;

    /**
     * Only create in onCreate() in calling Activity
     *
     * @param activity the calling activity
     */
    public NfcController(Activity activity) {
        this.mActivity = activity;

        mNfcAdapter = NfcAdapter.getDefaultAdapter(mActivity);
        if (mNfcAdapter != null) {  // check if nfc is available
            mNfcAdapter.setNdefPushMessageCallback(this, mActivity);
        }
    }

    /**
     * Call onResume in onResume() callback of calling Activity
     * @param intent getIntent() from the calling Activity
     */
    public void onResume(Intent intent) {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            processIntent(intent);
        }
    }

    private void processIntent(Intent intent) {
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        NdefMessage msg = (NdefMessage)rawMsgs[0];  // get the mime type
        printNdef(msg.getRecords());
    }

    private void printNdef(NdefRecord[] records) {
        if (records == null) return;

        for (int i = 0; i < records.length; i++) {
            Log.d(TAG, "RECORD " + i + " PAYLOAD: " + new String(records[i].getPayload()));
            Log.d(TAG, "RECORD " + i + " MIME TYPE: " + records[i].toMimeType());
        }
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        return new NdefMessage(new NdefRecord[]{
                NdefRecord.createMime(mActivity.getString(R.string.mime_type), getUserId().getBytes()),
                NdefRecord.createApplicationRecord("io.taggled")});
    }

    private String getUserId() {
        // TODO: get unique user id from the backend
        return "12345";
    }


}
