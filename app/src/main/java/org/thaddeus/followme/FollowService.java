package org.thaddeus.followme;

import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;
import java.lang.NumberFormatException;


public class FollowService extends Service {
	
	// the default server is in a local network
	private final static String DEFAULT_SERVER = "192.168.0.15";
	// the default period is an hour
	private final static int DEFAULT_PERIOD = 60;
	

	private LocationManager locationManager = null;
	private MyLocationListener myListener;
	private String provider;
	
	// server name or IP
	private String server;
	// in minuts
	private int period;
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);

		String message = "Starting service";
		
		period = 0;
		Bundle bundle = intent.getBundleExtra("args");
		
		if(bundle != null) {
			server = bundle.getStringArrayList("serviceArgs").get(0);
			try {
		        period = Integer.parseInt(bundle.getStringArrayList("serviceArgs").get(1));
		    } catch(NumberFormatException e) {
				period = 0;
			}
		}
		
		if(period <= 0) {
		    period = DEFAULT_PERIOD;
		} else  if(period > 1440) {
		    // Period can be more than one day
		    period = 1440;
		}
		
		if ("".equals(server)) {
		    server = DEFAULT_SERVER;
			message += "\nwith default server";
		}
		
		showMessage(message);
		initGPS();
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		showMessage("Stopping service");
		super.onDestroy();
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
		    myListener=new MyLocationListener(this, server);
		    
		    if(location!=null) {
				myListener.onLocationChanged(location);
			}
			// condition for updating pos : at least 20m and 2 minuts
			//locationManager.requestLocationUpdates(provider, period*60*1000, 20, myListener);
			//for testing
			locationManager.requestLocationUpdates(provider, 2*1000, 1, myListener);
		}
	}
	
	private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
