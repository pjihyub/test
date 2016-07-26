package com.backarmapps.gugudan.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.backarmapps.gugudan.activities.MainActivity;
import com.backarmapps.gugudan.R;
import com.backarmapps.gugudan.util.ProgressDialog;

/**
 * Created by iamabook on 2015. 11. 9..
 */
public class CloseDialogFragment extends DialogFragment implements View.OnClickListener {

    View rootView;
    WebView mWebView;
    private ProgressDialog progressDialog;

    Button finish_no,finish_yes;

    private static final String ARG_SHOW_AS_DIALOG = "DetailedFragment.ARG_SHOW_AS_DIALOG";

    public static CloseDialogFragment newInstance(boolean showAsDialog) {
        CloseDialogFragment fragment = new CloseDialogFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_SHOW_AS_DIALOG, showAsDialog);
        fragment.setArguments(args);
        return fragment;
    }

    public static CloseDialogFragment newInstance() {
        return newInstance(true);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            setShowsDialog(args.getBoolean(ARG_SHOW_AS_DIALOG, true));
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_closedialog, container);

        initView(rootView);




//        progressDialog = ProgressDialog.create(this.getActivity(), null, null);

        initWebView();

        // remove dialog title
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        // remove dialog background
        getDialog().getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        String[] popups = {getContext().getResources().getString(R.string.end_popup_url),getContext().getResources().getString(R.string.end_popup_url2)};

        int random_N = ((int) (Math.random()*10))%2;

        mWebView.loadUrl(popups[random_N]);

        return rootView;
    }

    private void initView(View rootView) {
        finish_no = (Button)rootView.findViewById(R.id.finish_no);
        finish_yes = (Button)rootView.findViewById(R.id.finish_yes);

        finish_no.setOnClickListener(this);
        finish_yes.setOnClickListener(this);

    }

    private void initWebView(){

        mWebView = (WebView) rootView.findViewById(R.id.powerWebViewDialog);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.clearCache(true);
        mWebView.setBackgroundColor(0);

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                if(progressDialog != null){
//                    progressDialog.show();
//                }

            }
            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
//                if(progressDialog != null){
//                    progressDialog.dismiss();
//                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url.contains("play.google.com")){
                    String pkg = url.substring(url.indexOf("id=")+3);

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("market://details?id=" + pkg));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getActivity().startActivity(intent);
                    return true;
                }

                return false;
            }
        });



    }


    @Override
    public void onClick(View v) {

        if(v == finish_no){
            dismiss();
        } else if( v ==finish_yes){
            ((MainActivity)getActivity()).finish();
        }



    }
}
