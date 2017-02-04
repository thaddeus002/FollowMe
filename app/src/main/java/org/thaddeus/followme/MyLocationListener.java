package org.thaddeus.followme;

import android.app.Service;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;
import android.content.Context;

/**
 * Make actions when a location is available for send.
 */
public class MyLocationListener implements LocationListener {

	private Service main;
	private Context context;
	private String server = "192.168.0.15";
	private Location location = null;

	/** Which of the two(context or service) may we keep */
	public MyLocationListener(Context context, Service main) {
		this.main = main;
		this.context = context;
	}

	public void setServer(String server) {
		this.server = server;
	}
	
	@Override
	public void onLocationChanged(Location location) {
		//Make action
		TrackCustomer spyingForYou = new TrackCustomer(context, server);
		spyingForYou.requestServer(location.getLatitude(), location.getLongitude());
	}

	
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		Toast.makeText(context, provider + "'s Status changed to "+status+"!", Toast.LENGTH_SHORT).show();
	}
	
	
	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(context, provider + " enabled!", Toast.LENGTH_SHORT).show();
	}
	
	
	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(context, provider + " disabled!", Toast.LENGTH_SHORT).show();
	}

}
