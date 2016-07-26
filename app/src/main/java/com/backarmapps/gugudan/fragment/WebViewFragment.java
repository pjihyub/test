package com.backarmapps.gugudan.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.backarmapps.gugudan.activities.MainActivity;
import com.backarmapps.gugudan.util.CommonUtil;
import com.backarmapps.gugudan.R;
import com.backarmapps.gugudan.util.ProgressDialog;

/**
 * Created by iamabook on 2015. 11. 6..
 */
public class WebViewFragment extends BaseFragment implements View.OnClickListener{

    private View rootView;

    private WebView mWebview;
    private ProgressDialog progressDialog;

    private Button btn_back, btn_gugu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_webview, container, false);

        progressDialog = ProgressDialog.create(this.getActivity(), null, null);

        initButton();
        initView();

        String url = getArguments().getString("powerURL");

        if(url != null && !url.equals(""))
            loadWebView_w_URL(url);

        return rootView;
    }

    private void loadWebView_w_URL(String url) {

        mWebview.loadUrl(url);

    }

    private void initButton() {
        btn_back = (Button) rootView.findViewById(R.id.btn_back);
        btn_gugu = (Button) rootView.findViewById(R.id.btn_gugu);

        btn_back.setOnClickListener(this);
        btn_gugu.setOnClickListener(this);

    }


    private void initView(){

        mWebview = (WebView) rootView.findViewById(R.id.powerWebView);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.clearCache(true);
        mWebview.setBackgroundColor(0);
        mWebview.setWebChromeClient(new WebChromeClient());
        mWebview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if(progressDialog != null){
                    progressDialog.show();
                }

            }
            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
                if(progressDialog != null){
                    progressDialog.dismiss();
                }



            }
        });



    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.btn_back:
                CommonUtil.replaceFragment(getContext(), MainActivity.FRAGMENT_MAIN, "BACK_TO_MAIN");

                break;

            case R.id.btn_gugu:
                CommonUtil.replaceFragment(getContext(), MainActivity.FRAGMENT_MUNJE, "");

                break;

            default:
                break;


        }

    }


}
