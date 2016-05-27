package com.haoren.hijack;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
	Button btn1 = null;
	Button btn2 = null;
	Button btn3 = null;
	Button btn4 = null;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button)findViewById(R.id.getrunningtasks);
        btn1.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent mservice = new Intent(getApplicationContext(), GetRunningTasksService.class);
				startService(mservice);
				Toast.makeText(getApplicationContext(), "打开唯品会app进行测试", Toast.LENGTH_LONG).show();
				
			}
		});
        btn2 = (Button)findViewById(R.id.accessibility);
        btn2.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new Builder(MainActivity.this);
				builder.setIcon(R.drawable.ic_launcher);
				builder.setTitle("开启辅助功能");
				builder.setMessage("为了软件更好运行,需要开启辅助功能");
				builder.setPositiveButton("去开启", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
				        startActivity(intent);
					}
					
				});
				builder.show();
				
			}
		});
        btn3 = (Button)findViewById(R.id.webview);
        btn3.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClassName("com.haoren.hijack", "com.haoren.hijack.MyWebview");
		        startActivity(intent);
				
			}
		});
        btn4 = (Button)findViewById(R.id.alertwindow);
        btn4.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent mservice = new Intent(getApplicationContext(), AlertWindow.class);
				startService(mservice);
				Toast.makeText(getApplicationContext(), "打开唯品会app进行测试", Toast.LENGTH_LONG).show();
				
			}
		});

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
}
