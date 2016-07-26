package com.backarmapps.gugudan.util;

import com.backarmapps.gugudan.R;
import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;

public class ProgressDialog extends Dialog{
	
	private static Dialog mCurrentDialog;
	
	public static ProgressDialog create(Context context, CharSequence title,
	        CharSequence message) {
	    return create(context, title, message, false);
	}
	
	public static ProgressDialog create(Context context, CharSequence title,
	        CharSequence message, boolean indeterminate) {
	    return create(context, title, message, indeterminate, false, null);
	}
	
	public static ProgressDialog create(Context context, CharSequence title,
	        CharSequence message, boolean indeterminate, boolean cancelable) {
	    return create(context, title, message, indeterminate, cancelable, null);
	}
	
	public static ProgressDialog create(Context context, CharSequence title,
	        CharSequence message, boolean indeterminate,
	        boolean cancelable, OnCancelListener cancelListener) {
		ProgressDialog dialog = new ProgressDialog(context);
	    dialog.setTitle(title);
	    dialog.setCancelable(cancelable);
	    dialog.setOnCancelListener(cancelListener);
	    /* The next line will add the ProgressBar to the dialog. */
	    dialog.addContentView(new ProgressBar(context), new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	    //dialog.show();
	    mCurrentDialog = dialog;
	
	    return dialog;
	}
	
	public ProgressDialog(Context context) {
	    super(context, R.style.NewDialog);
	}

	@Override
	public void dismiss() {
		// TODO Auto-generated method stub
		super.dismiss();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		if(mCurrentDialog != null && mCurrentDialog.isShowing())
			mCurrentDialog.dismiss();
		super.show();
	}
	
	public static void dismissDialog(){
		if(mCurrentDialog != null && mCurrentDialog.isShowing())
			mCurrentDialog.dismiss();
	}
}
