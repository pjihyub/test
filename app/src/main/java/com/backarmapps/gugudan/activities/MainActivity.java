package com.backarmapps.gugudan.activities;

import com.backarmapps.gugudan.R;
import com.backarmapps.gugudan.fragment.MainFragment;
import com.backarmapps.gugudan.fragment.CloseDialogFragment;
import com.backarmapps.gugudan.fragment.MunjeFragment;
import com.backarmapps.gugudan.fragment.WebViewFragment;
import com.backarmapps.gugudan.util.BackkeyListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.tsengvn.typekit.TypekitContextWrapper;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	// 광-고
	private AdRequest.Builder builder;
	private AdView mAdView;

	//앱이 실행되어 있는지 여부
	public static boolean isRunning = false;

	private static BackkeyListener mBackkeyListner;
	
	public static void setBackkeyListener(BackkeyListener listener) {
		mBackkeyListner = listener;
		return; 
	}

	private boolean mIsBackKeyPressed = false;
	private static final int MSG_TIMER_EXPIRED = 1;
	private static final int BACKKEY_TIMEOUT = 2;
	private static final int MILLIS_IN_SEC = 2000;

	public static final int FRAGMENT_MAIN = 0;
	public static final int FRAGMENT_WEBVIEW = 1;
	public static final int FRAGMENT_MUNJE = 2;


	private int current_fragment = -1;

	private String powerURL;

	@Override
	protected void attachBaseContext(Context newBase) {
		super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		makeAD();

		changeFragment();

	}

	private void changeFragment() {

		if(ApplicationDummy.gotowebview)
		{
			replaceFragment(FRAGMENT_WEBVIEW, getResources().getString(R.string.powerurl));
			ApplicationDummy.gotowebview = false;
		}
		else
		{
			replaceFragment(FRAGMENT_MAIN, "MAIN");
		}

//		if(getIntent().getStringExtra("powerurl") != null)
//			replaceFragment(FRAGMENT_WEBVIEW, getIntent().getStringExtra("powerurl"));
//		else
//			replaceFragment(FRAGMENT_MAIN, "MAIN");

	}

	@Override
	protected void onStart() {
		isRunning =true;
		super.onStart();

	}

	@Override
	protected void onResume() {
		super.onResume();

		if(ApplicationDummy.gotowebview) {
			changeFragment();
		}
	}

	@Override
	protected void onStop() {
		isRunning = false;

		super.onStop();
	}

	//광-고-
	private void makeAD(){
		mAdView = (AdView) findViewById(R.id.poweradView);

		builder = new AdRequest.Builder();
		builder.addTestDevice("7098F7E9014733A668D34EAE7C01D8BC"); // s4
		builder.addTestDevice("422180EA62306351229E1B405D08267E"); // s2
		builder.addTestDevice("7784EAFE79D4264FADDCB213D4BB1E46"); // vega
		builder.addTestDevice("63DD795EC89B3B125C1E718C31096011"); // s3
		mAdView.loadAd(builder.build());

	}
	
	
	
	
	// BakcKey Handler
	private Handler mBackTimerHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case MSG_TIMER_EXPIRED:
				mIsBackKeyPressed = false;
				break;
			}
		}
	};

	@Override
	public void onBackPressed() {

		if( mBackkeyListner != null) {
			Log.d("test", "onBackPressed()");
			if( mBackkeyListner.onBackkeyListener() )
				return;
		}

		if(current_fragment != FRAGMENT_MAIN){
			replaceFragment(FRAGMENT_MAIN,"BACK_TO_MAIN");
			return;
		}

//		if( mIsBackKeyPressed) {
//			mBackTimerHandler = null;
//			finish();
//		} else {
//			mIsBackKeyPressed = true;
//			Toast.makeText(this, "한번 더 누르시면 종료 합니다.", Toast.LENGTH_SHORT).show();
//			mBackTimerHandler.sendEmptyMessageDelayed(MSG_TIMER_EXPIRED, BACKKEY_TIMEOUT * MILLIS_IN_SEC);
//		}

//		FragmentManager fm = getSupportFragmentManager();
//        CloseDialogFragment dialogFragment = new CloseDialogFragment();
//        dialogFragment.show(fm, "fragment_dialog_test");
//		getSupportFragmentManager().beginTransaction().add(R.id.close_dialogfragment,new CloseDialogFragment(),"ByeBye").addToBackStack(null).commit();
//        CloseDialogFragment dialogFragment = new CloseDialogFragment();
//        dialogFragment.show(fm, "fragment_dialog_test");
//
		CloseDialogFragment fragment = CloseDialogFragment.newInstance();
		fragment.show(getSupportFragmentManager(), "Tablet_specific");



		return;
	}


	//url dㅓㅂㅎㅅ으면 ""

	/**
	 * url:::
	 * BACK_TO_MAIN -> pop to main
	 * MAIN -> make main fragment name MAIN
	 *
	 *
	 * @param groupIdx
	 * @param url
	 */
	public void replaceFragment(int groupIdx,String url)
	{
		Fragment newFragment = null;

		Log.w("iamabook","url is " + url);

//iamabook. if have stack.
//        if(url.equals("BACK_TO_MAIN")){
//
//			FragmentManager fm = getSupportFragmentManager();
////			fm.popBackStack ("MAIN", FragmentManager.POP_BACK_STACK_INCLUSIVE);
//			fm.popBackStackImmediate();
//			current_fragment = FRAGMENT_MAIN;
//
//			return;
//		}

		if(url != null)
            this.powerURL = url;
        else
            this.powerURL = "";

		newFragment = getFragment(groupIdx);

		// replace fragment
		final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

		transaction.replace(R.id.main_container, newFragment);

		transaction.commit();
	}

	private Fragment getFragment(int groupIdx)
	{
		Fragment fragment = null;
		Bundle args = new Bundle();
		switch(groupIdx)
		{
			case FRAGMENT_MAIN:		// HOME
				fragment = new MainFragment();
				current_fragment = FRAGMENT_MAIN;
				break;
			case FRAGMENT_WEBVIEW:
				fragment = new WebViewFragment();
				args.putString("powerURL",powerURL);
				fragment.setArguments(args);
				current_fragment = FRAGMENT_WEBVIEW;
				break;
			case FRAGMENT_MUNJE:		// MUNJE
				fragment = new MunjeFragment();
				current_fragment = FRAGMENT_MUNJE;
				break;

			default:
				break;
		}

		return fragment;

	}

}
