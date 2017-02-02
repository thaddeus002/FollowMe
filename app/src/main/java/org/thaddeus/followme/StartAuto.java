package org.thaddeus.followme;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;

/**
 * To start at boot
 */
public class StartAuto extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Intent i = new Intent(context, FollowService.class);
		context.startService(i);
	}
}
