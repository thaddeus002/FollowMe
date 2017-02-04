package org.thaddeus.followme;

import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.os.IBinder;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;


public class FollowService extends Service {
	
	private LocationManager locationManager = null;
	private MyLocationListener myListener;
	private String provider;
	
	@Override
	public void onCreate() {
		
		
		
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		//TODO use also button
		showMessage("Starting service");
		initGPS();
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	// don't exist in Service - TODO find if there is one method to override.
	public void onStopCommand(Intent intent) {
		showMessage("Stopping service");
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	/** Start the tracking */
	private void initGPS() {
		if(locationManager==null) {
			locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
			Criteria criteria = new Criteria();
			criteria.setAccuracy(Criteria.ACCURACY_FINE);
			provider = locationManager.getBestProvider(criteria, true);
		}
		if(provider != null) {
			Location location = locationManager.getLastKnownLocation(provider);
		    myListener=new MyLocationListener(this, this);
		    
		    if(location!=null) {
				myListener.onLocationChanged(location);
			}
			// condition for updating pos : at least 20m and 1 minuts
			//locationManager.requestLocationUpdates(provider, 60*1000, 20, myListener);
			//for testing
			locationManager.requestLocationUpdates(provider, 1000, 1, myListener);
		}
	}
	
	private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
