package io.taggled;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.firebase.client.Firebase;

/**
 * Created by jhoon on 11/10/15.
 * Application used for initiating both Firebase and Facebook's Android SDK
 */
public class TaggleApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        FacebookSdk.sdkInitialize(this);
    }
}
