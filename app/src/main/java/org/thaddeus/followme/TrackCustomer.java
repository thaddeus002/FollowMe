package org.thaddeus.followme;

import android.content.Context;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.Date;


/**
 * This will send data to a track server with the method {@link requestServer()}.
 */
public class TrackCustomer {

	private Context context;
	private String server;

	public TrackCustomer(Context context, String server) {
		this.context = context;
		this.server = server;
	}

	public void requestServer(double lat, double lon) {
		
		RequestQueue queue;

		// Instantiate the cache
		Cache cache = new DiskBasedCache(context.getCacheDir(), 1024); // 1Kb cap

		// Set up the network to use HttpURLConnection as the HTTP client.
		Network network = new BasicNetwork(new HurlStack());

		// Instantiate the RequestQueue with the cache and network.
		queue = new RequestQueue(cache, network);

		// Start the queue
		queue.start();

		String baseurl ="http://"+server+"/admin/track";

		Date now = new Date(); 

		String url=baseurl+ "?timestamp="+now.getTime()/1000 + "&lat="+lat+"&lon="+lon;

		// Request a string response from the provided URL.
		StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
	        new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					// Display the first 500 characters of the response string.
					//mTextView.setText("Response is: "+ response.substring(0,500));
				}
			}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					//mTextView.setText("That didn't work!");
				}
			});
		
		// Add the request to the RequestQueue.
		queue.add(stringRequest);
	}
}
