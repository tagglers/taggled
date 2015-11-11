package io.taggled;

/**
 * Created by Cuong on 11/10/2015.
 */
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.taggled.backend.myApi.MyApi;

import java.io.IOException;

/**
 * Created by Cuong on 8/7/2015.
 */
class EndPointsAsyncTask extends AsyncTask<String, Void, String> {

    private static MyApi mApiService = null;

    public EndPointsAsyncTask(){
    }

    private MyApi getApiService() {
        if (mApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("https://taggled-1125.appspot.com/_ah/api");

            if (!builder.getRootUrl().startsWith("https:"))//dev server
                // options for running against local devappserver
                // - 10.0.2.2 is localhost's IP address in Android emulator
                // - turn off compression when running against local devappserver
                builder.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                });

            mApiService = builder.build();
        }
        return mApiService;
    }

    @Override
    public void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    public String doInBackground(String... params) {

        String which= params[0];
        try {
            return getApiService().getCampaignDetail(2).execute().getName();

        } catch (IOException e) {
            return "EXCEPTION: " + e.getMessage();
        }
    }

    @Override
    public void onPostExecute(String result) {
        System.out.println("EndPointsAsyncTask: " + result);
    }
}