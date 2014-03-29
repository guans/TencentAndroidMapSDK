package com.tencent.mapsdkdemo.raster;

import java.util.Date;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;




import com.tencent.tencentmap.mapsdk.map.GeoPoint;
import com.tencent.tencentmap.mapsdk.map.MapActivity;
import com.tencent.tencentmap.mapsdk.map.MapController;
import com.tencent.tencentmap.mapsdk.map.MapView;


import com.tencent.tencentmap.lbssdk.TencentMapLBSApi;
import com.tencent.tencentmap.lbssdk.TencentMapLBSApiListener;
import com.tencent.tencentmap.lbssdk.TencentMapLBSApiPOI;
import com.tencent.tencentmap.lbssdk.TencentMapLBSApiResult;


public class LocationMe extends MapActivity {

	MapView mMapView; 
	MapController mMapController;

	Button btnTraffic = null;
	Button btnAnimationTo=null;
	Button btnZoomSatellite=null;
	
	
	LocListener mListener;   //接受回调信息
	PowerManager.WakeLock mWakeLock;  //监视器
	double x=0;
	double y=0;
	int mReqType, mReqGeoType, mReqLevel;
	RadioGroup mEditReqGeoType;
	RadioGroup mEditReqLevel;
	
	

	@Override
	/**
	 *显示地图，启用内置缩放控件，并用MapController控制地图的中心点及Zoom级别
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapviewdemo);
		mMapView = (MapView) findViewById(R.id.mapviewtraffic);
		
		
		
		
		


		
		Button btn = (Button) findViewById(R.id.add);
		btn.setOnClickListener(new OnClickListener() {
			@SuppressLint("NewApi")
			public void onClick(View arg0) {
				mReqGeoType = TencentMapLBSApi.GEO_TYPE_WGS84;
		        mReqLevel = TencentMapLBSApi.LEVEL_NAME;
			
				mListener = new LocListener(mReqGeoType, mReqLevel, 1);
				
				// 注意, manifest 文件中已配置 key
				
				// 添加定位监听器
				int req = TencentMapLBSApi.getInstance().requestLocationUpdate(
						LocationMe.this
								.getApplicationContext(), mListener);
				Log.e("REQLOC", "res: " + req);
				TencentMapLBSApi.getInstance().setGPSUpdateInterval(1000);
				
				if (req == -2) {
				//	mTextRes.setText("Key不正确. 请在manifest文件中设置正确的Key");
				}
			}
		});
		btn = (Button) findViewById(R.id.remove);
		btn.setOnClickListener(new OnClickListener() {
			@SuppressLint("NewApi")
			public void onClick(View arg0) {
				TencentMapLBSApi.getInstance().removeLocationUpdate();
			
			}
		});
		
		
		
		
			
			
			
			
			
		btnTraffic = (Button) this.findViewById(R.id.btnTraffic);
		btnTraffic.setText("打开实时交通");
		btnTraffic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean boTraffic = mMapView.isTraffic();
				if (boTraffic == false) {
					int iCurrentLevel=mMapView.getZoomLevel();
					if(iCurrentLevel<10)  //实时交通在10级以上才显示     
					{
						mMapView.getController().setZoom(10);
					}
					mMapView.setTraffic(true);
					btnTraffic.setText("关闭实时交通");
				} else {
					mMapView.setTraffic(false);
					btnTraffic.setText("打开实时交通");
				}
			}
		});

		btnAnimationTo=(Button)this.findViewById(R.id.btnAnimationTo);
		btnAnimationTo.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GeoPoint ge=new GeoPoint((int) (x * 1E6), (int) (y * 1E6)); 
				Runnable runAnimate=new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(LocationMe.this, "animation finish", Toast.LENGTH_LONG).show();
					}};
				mMapView.getController().animateTo(ge,runAnimate);
			}});
		
		btnZoomSatellite=(Button)this.findViewById(R.id.btnSatellite);
		btnZoomSatellite.setText("打开卫星影像");
		btnZoomSatellite.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean boSatellite=mMapView.isSatellite();
				if(boSatellite==true)
				{
					mMapView.setSatellite(false);
					btnZoomSatellite.setText("打开卫星影像");
				}
				else
				{
					mMapView.setSatellite(true);
					btnZoomSatellite.setText("关闭卫星影像");
				}
				
			}});
		
		mMapView.setBuiltInZoomControls(true); // 设置启用内置的缩放控件
		mMapController = mMapView.getController(); // 得到mMapView的控制权,可以用它控制和驱动平移和缩放
		
		mMapController.setZoom(9);
	}
	
	
	
	
	
	
	
	
	public void onResume() {
		super.onResume();
		mWakeLock.acquire();
	}
	
	public void onDestory() {
		super.onDestroy();
		mWakeLock.release();
		// 删除定位监听器
		TencentMapLBSApi.getInstance().removeLocationUpdate();
		
	}

	@SuppressLint("NewApi")
	protected void onPause() {
		super.onPause();
		mWakeLock.release();
		// 删除定位监听器
		TencentMapLBSApi.getInstance().removeLocationUpdate();
	}
	
	public class LocListener extends TencentMapLBSApiListener {
		
		public LocListener(int reqGeoType, int reqLevel,
				int reqDelay) {
			super(reqGeoType, reqLevel, reqDelay);
		}

		
		@Override
		public void onLocationUpdate(TencentMapLBSApiResult locRes) {
			//String res = locResToString(locRes);
		
			//String date = (new Date()).toLocaleString();
			//mTextRes.setText(date + "\n" + res);
			x=locRes.Latitude;
			y=locRes.Longitude;
		}

		
		@Override
		public void onStatusUpdate(int state) {
			String strState = null;
			switch (state) {
			case TencentMapLBSApi.STATE_GPS_DISABLED:
				strState = "Gps Disabled";
				break;
			case TencentMapLBSApi.STATE_GPS_ENABLED:
				strState = "Gps Enabled";
				break;
			case TencentMapLBSApi.STATE_WIFI_DISABLED:
				strState = "Wifi Disabled";
				break;
			case TencentMapLBSApi.STATE_WIFI_ENABLED:
				strState = "Wifi Enabled";
				break;
			}
		//	mTextState.setText(strState);
		}
	}
	
	
	
	
	
	
	
	
	
}
