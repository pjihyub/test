package com.backarmapps.gugudan.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.backarmapps.gugudan.R;
import com.backarmapps.gugudan.activities.MainActivity;
import com.backarmapps.gugudan.util.CommonUtil;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


/**
 * Created by iamabook on 2015. 11. 6..
 */
public class MainFragment extends BaseFragment implements View.OnClickListener{

    View rootView,mun_je,gugu_pyo;
    Context mContext;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getContext();
        rootView = inflater.inflate(R.layout.fragment_main, container, false);

        initEditTextView();

        initView();

        return rootView;

    }




    @Override
    public void onResume() {
        super.onResume();

    }


    private void initView() {

        mun_je = rootView.findViewById(R.id.gugu_munje);
        gugu_pyo = rootView.findViewById(R.id.gugu_pyo);

        mun_je.setOnClickListener(this);
        gugu_pyo.setOnClickListener(this);
    } //initview end



    private void initEditTextView(){


    } // initeditview end

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gugu_munje:

                CommonUtil.replaceFragment(mContext,MainActivity.FRAGMENT_MUNJE,"");


                break;
            case R.id.gugu_pyo:

                CommonUtil.replaceFragment(mContext, MainActivity.FRAGMENT_WEBVIEW, getResources().getString(R.string.powerurl));

                break;



        }


    } // onclick end




    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }





}
