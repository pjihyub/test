package com.backarmapps.gugudan.activities;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.backarmapps.gugudan.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;

public class IntroActivity extends Activity{

	// 타이머
	private int DEFAULT_COUNT = 1;
	private int mCurrentTimer = 0;
	private final int ANI_TIMER = 99999;
	
	private Intent m_intent;

	private boolean isRequested;
	private boolean isGoolgleStore = false;
	
	private Context m_context;
	public static RequestQueue m_RequestQueue;
	
	//Template 설정 (0 : GNB UI, 1 : 서랍 UI)
	private int mTemplate = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro);
		
		m_context = this.getApplicationContext();
		m_RequestQueue = Volley.newRequestQueue(m_context);
		
		m_intent = getIntent();
		isRequested = false;
		requestCheckVersion2();
//		StartTimer();

	}

	private void StartTimer(){
		StopTimer();
		mCurrentTimer = DEFAULT_COUNT;
		mTimerHandler.sendEmptyMessageDelayed(ANI_TIMER, mCurrentTimer*1000);
	}

	private void StopTimer(){
		if(mTimerHandler != null) {
			mTimerHandler.removeMessages(ANI_TIMER);
		}
	}

	/**
	 * 타이머 핸들러
	 */
	private Handler mTimerHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Log.d("inergy", "mTimerHandler " + msg.what);
			if(msg.what >= ANI_TIMER && isRequested) {
				Log.d("inergy", "ANI_TIMER && isRequested " + msg.what);
				goMainActivity();
			}
		}
	};

	private void goMainActivity() {
		Class<?> contentClass = null;
			contentClass = MainActivity.class;
		Log.d("inergy", "goMainActivity1" + contentClass);
		if(contentClass!=null){
			Log.d("inergy", "goMainActivity2" + contentClass);
			Intent i = new Intent(this, contentClass);
			Log.d("inergy", "m_intent.getIntExtra : " + m_intent.getIntExtra("callFragmentIndex", 0)) ;
			i.putExtra("callFragmentIndex", m_intent.getIntExtra("callFragmentIndex", 0));
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			finish();
		}
	}

	public void requestCheckVersion2(){
		
		if(compareVersionCheck(null))
		{
			isRequested = true;
			StartTimer();			
		}
//		String root = this.getResources().getString(R.string.server_adress_root);
//		String url = this.getResources().getString(R.string.server_version_check_url);
//		Log.d("inergy", "requestVolley  url :: " + root + url);
//		StringRequest jor = new StringRequest(Request.Method.GET, root + url, new Response.Listener<String>() {
//			@Override
//			public void onResponse(String response) {
//				Log.d("inergy", "requestVolley onResponse");
//				String resultMsg="";
//				try {
//					resultMsg = new String(response.getBytes(), "utf-8").replace( System.getProperty( "line.separator" ), "" );
//				} catch (UnsupportedEncodingException e) {
//					e.printStackTrace();
//				}
//				if(resultMsg == null || resultMsg == "" || resultMsg.equalsIgnoreCase("")){
//					isGoolgleStore = false;
//					String message = m_context.getResources().getString(R.string.server_end_notice_message);
//					alert(message);
//				}else if(compareVersionCheck(resultMsg)){
//					isRequested = true;
//					StartTimer();
//				}else{
//					String message = m_context.getResources().getString(R.string.app_need_update_message);
//					isGoolgleStore = true;
//					alert(message);
//				}
//			}
//		}, new Response.ErrorListener() {
//			@Override
//			public void onErrorResponse(VolleyError error) {
//				String message = m_context.getResources().getString(R.string.network_error_message);
//				Log.d("inergy", "onRequestFailed" + message);
//				alert(message);
//			}
//		});
//		m_RequestQueue.add(jor);
	}

	public boolean compareVersionCheck(String serverVersion){
		return true;
		
//		String[] splitAppVer = null;
//		String[] splitServerVer = null;
//		
//		Log.d("inergy", "splitAppVer" + getAppVersionName() +  " ::: " + serverVersion);
//
//		splitServerVer = serverVersion.split("\\.");
//		splitAppVer = getAppVersionName().split("\\.");
//		
//		if( Integer.parseInt(splitAppVer[0]) < Integer.parseInt(splitServerVer[0])){
//			return false;
//		}else if(Integer.parseInt(splitAppVer[0]) > Integer.parseInt(splitServerVer[0])){
//			return true;
//		}else{
//			if( Integer.parseInt(splitAppVer[1]) < Integer.parseInt(splitServerVer[1])){
//				return false;
//			}else{
//				return true;
//			}
//		}
	}

	public String getAppVersionName(){
		PackageInfo pi = null;
		try {
			pi = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			finish();
		}
		return pi.versionName;

	}
	
	private void alert(String message) {
		new AlertDialog.Builder(this)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setMessage(message)
			.setPositiveButton(android.R.string.ok, alertListener)
			.create().show();
	}
	
	/**
	 * market://search?q=packageName
	 * market://search?q=searchName 
	 * market://details?id=packageName
	 */
	
	public void goGoogleStore(){
//		Intent marketLaunch = new Intent(Intent.ACTION_VIEW); 
//		String appGooglePath = this.getResources().getString(R.string.google_strore_link_url);
//		marketLaunch.setData(Uri.parse(appGooglePath)); 
//		startActivity(marketLaunch);
	}
	
	private OnClickListener alertListener = new OnClickListener(){

		@Override
		public void onClick(DialogInterface dialog, int which) {
			if(isGoolgleStore){
				goGoogleStore();
			}else{
				
			}
			finish();
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			finish();
			break;

		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

}
