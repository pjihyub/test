package com.backarmapps.gugudan.fragment;

import android.support.v4.app.Fragment;

import com.backarmapps.gugudan.activities.ApplicationDummy;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by iamabook on 2015. 12. 10..
 */
public class BaseFragment extends Fragment{


    @Override
    public void onResume() {

        super.onResume();

        final Tracker t = ((ApplicationDummy)getActivity().getApplication()).getDefaultTracker();
        if(t != null){

            t.setScreenName(getClass().getSimpleName());
            t.send(new HitBuilders.ScreenViewBuilder().build());
        }
    }



}
