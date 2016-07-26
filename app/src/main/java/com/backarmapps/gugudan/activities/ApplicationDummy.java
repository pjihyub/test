package com.backarmapps.gugudan.activities;

import android.app.Application;

import com.backarmapps.gugudan.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.tsengvn.typekit.Typekit;

/**
 * Created by iamabook on 2015. 12. 8..
 */
public class ApplicationDummy extends Application{

    private Tracker mTracker;

    public static boolean gotowebview = false;

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Typekit.getInstance().addNormal(Typekit.createFromAsset(this, "font/BM_JUA.otf")).addBold(Typekit.createFromAsset(this, "font/BM_JUA.otf"));

        try{

        }catch(Exception e){

        }
    }
}
