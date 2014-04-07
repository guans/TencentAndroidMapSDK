package com.tencent.mapsdkdemo.raster;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class StreetView extends Activity{
	
	private WebView webView; 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.streedview);
//		 WebView 
		 webView = (WebView) findViewById(R.id.streetwebView);  
		 webView.getSettings().setJavaScriptEnabled(true);
		// webView.setScrollBarStyle(0);
		// WebSettings webSettings = webView.getSettings();
		// webSettings.setAllowFileAccess(true);
		// webSettings.setBuiltInZoomControls(true);
		 webView.getSettings().setPluginsEnabled(true);
		//启用地理定位 
		 webView.getSettings().setGeolocationEnabled(true); 

		// webView.addJavascriptInterface(new MyJavaScript(this, handler), "myjavascript");  
	     //加载test.html  
		 
		  
	     webView.loadUrl("file:///android_asset/test.html");  
	   //  setContentView(webView);  
		// webView.loadUrl("http://www.baidu.com");
		//设置Web视图  
		 webView.setWebViewClient(new HelloWebViewClient ()); 
		
	}
	
	 public boolean onKeyDown(int keyCode, KeyEvent event) {  
	        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {  
	        	webView.goBack(); //goBack()表示返回WebView的上一页面  
	            return true;  
	        }  
	        return false;  
	
	 }
	 
	//Web视图  
	    private class HelloWebViewClient extends WebViewClient {  
	        @Override 
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {  
	            view.loadUrl(url);  
	            return true;  
	        }  
	    }  
}
