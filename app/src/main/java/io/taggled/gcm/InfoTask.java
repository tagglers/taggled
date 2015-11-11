package io.taggled.gcm;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.taggled.backend.myApi.MyApi;
import com.taggled.backend.myApi.model.UserProfile;

import java.io.IOException;

/**
 * Created by jhoon on 11/10/15.
 */
public class InfoTask extends AsyncTask<Integer, Void, String>{
    private static final String LOG_TAG = InfoTask.class.getSimpleName();
    MyApi myApi;
    private TagglerTaskListener mListener;

    public void setListener(TagglerTaskListener listener) {
        mListener = listener;
    }

    @Override
    protected String doInBackground(Integer... params) {
        MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                .setRootUrl("https://taggled-1125.appspot.com/_ah/api/");;
        myApi = builder.build();

        try {
            MyApi.GetProfile profile = myApi.getProfile(params[0]);
            Log.d(LOG_TAG, "profile: " + profile.toString());
            UserProfile user = profile.execute();
            Log.d(LOG_TAG, "user: " + user.toString());
            return user.getUser().getEmail();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mListener.onTaskComplete(s);
    }

    public interface TagglerTaskListener {
        void onTaskComplete(String data);
    }
}
