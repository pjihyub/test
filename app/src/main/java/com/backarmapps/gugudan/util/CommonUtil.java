package com.backarmapps.gugudan.util;

import android.content.Context;

import com.backarmapps.gugudan.activities.MainActivity;

/**
 * Created by iamabook on 2015. 11. 11..
 */
public class CommonUtil {

    public static void replaceFragment(Context context,int FRAGMENT,String url){

        MainActivity activity = (MainActivity)context;

        activity.replaceFragment(FRAGMENT, url);


    }

}
