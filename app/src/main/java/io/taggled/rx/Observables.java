package io.taggled.rx;


import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.taggled.backend.myApi.MyApi;
import com.taggled.backend.myApi.model.UserProfile;

import java.io.IOException;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Observables {

    public static Observable<UserProfile> getUserProfile(final int userId) throws IOException {
        MyApi myApiService = getMyApiService();
        return Observable.just(myApiService.getProfile(userId).execute())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io());
    }

    private static MyApi getMyApiService() {
        MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                .setRootUrl("https://taggled-1125.appspot.com/_ah/api/");

        return builder.build();
    }
}
