package io.taggled.tasks;


import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.taggled.backend.myApi.MyApi;
import com.taggled.backend.myApi.model.UserProfile;

import java.io.IOException;

public class Tasks {

    public static String getUserTasks(int userId) {
        final String[] email = {""};

        new AsyncTask<Integer, Void, UserProfile>() {
            @Override
            protected void onPostExecute(UserProfile userProfile) {
                super.onPostExecute(userProfile);
                email[0] = userProfile.getUser().getEmail();
            }

            @Override
            protected UserProfile doInBackground(Integer... params) {
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://taggled-1125.appspot.com/_ah/api/");

                MyApi myApiService = builder.build();

                try {
                    return myApiService.getProfile(params[0]).execute();
                } catch (IOException e) {
                    e.printStackTrace();

                }
                return null;
            }
        }.execute(userId);

        return email[0];
    }
}
