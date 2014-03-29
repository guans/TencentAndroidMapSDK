package com.tencent.mapsdkdemo.raster;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;

public class SplashScreen extends Activity {
	 private long m_dwSplashTime=3000;
	    private boolean m_bPaused=false;
	    private boolean m_bSplashActive=true;

	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.splash);
	        
	    	Handler x = new Handler();
			
			
			/* 延时3秒 */
			x.postDelayed(new splashhandler(), 3000);
			
			

	}
	
	    

	    class splashhandler implements Runnable {

			public void run() {
				startActivity(new Intent(SplashScreen.this,MainActivity.class));
				SplashScreen.this.finish();
			}

		}
}
