package com.crystalcube.android.doodledraw;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.test.ApplicationTestCase;

import com.crystalcube.android.doodledraw.ui.DoodleDrawApp;
import com.crystalcube.android.doodledraw.ui.view.MainActivity;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<DoodleDrawApp> {

    private static final int MSG_ACTIVITY_STARTED = 100;

    public ApplicationTest() {
        super(DoodleDrawApp.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        createApplication();
    }

    public void testMainActivityStarted() throws Exception {
        final Handler callbackHandler = new Handler();
        getApplication().registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                if (activity instanceof MainActivity) {
                    callbackHandler.sendEmptyMessage(MSG_ACTIVITY_STARTED);
                }
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });

        int time = 0;

        while (time < 5000) {
            try {
                Thread.sleep(1000);
                time += 1000;
                if (callbackHandler.obtainMessage(MSG_ACTIVITY_STARTED) != null) {
                    return;
                }
            } catch (InterruptedException e) {
                fail("Timed out, activity did not started in timely fashion");
            }
        }

        fail("Timed out, activity did not started in timely fashion");
    }
}