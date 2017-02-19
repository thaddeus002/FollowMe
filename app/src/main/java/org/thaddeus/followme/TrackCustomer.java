package org.thaddeus.followme;

import android.content.Context;
import android.widget.Toast;
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

// for self signed certificates in https
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import java.security.cert.CertificateException;
import java.security.SecureRandom;

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

        //essai
        trustEveryone();

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        queue = new RequestQueue(cache, network);

        // Start the queue
        queue.start();

        String baseurl ="https://"+server+"/admin/track";

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
                    showMessage("Sending failed : "+error.getMessage());
                }
            });
        
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    
    
    private void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }});
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager(){
                public void checkClientTrusted(X509Certificate[] chain,
                        String authType) throws CertificateException {}
                public void checkServerTrusted(X509Certificate[] chain,
                        String authType) throws CertificateException {}
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }}}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
        } catch (Exception e) { // should never happen
            e.printStackTrace();
        }
    }
    
    private void showMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
