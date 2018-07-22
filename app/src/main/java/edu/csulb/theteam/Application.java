package edu.csulb.theteam;

import android.app.Activity;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

/**
 * Application class responsible for initializing singletons and other
 * common components
 */
public class Application extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        AWSProvider.initialize(getApplicationContext());

        registerActivityLifecycleCallbacks(new ActivityLifeCycle());

    }
}

class ActivityLifeCycle implements android.app.Application.ActivityLifecycleCallbacks {
    private int depth = 0;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (depth == 0) {
            Log.d("ActivityLifeCycle", "Application entered foreground");
            AWSProvider.getInstance().getPinpointManager().getSessionClient().startSession();
            AWSProvider.getInstance().getPinpointManager().getAnalyticsClient().submitEvents();
        }
        depth++;
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        depth--;
        if (depth == 0) {
            AWSProvider.getInstance().getPinpointManager().getSessionClient().stopSession();
            AWSProvider.getInstance().getPinpointManager().getAnalyticsClient().submitEvents();
            Log.d("ActivityLifeCycle", "Application entered background");
        }

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}