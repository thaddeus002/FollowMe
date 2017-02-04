package org.thaddeus.followme;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import java.util.List;
import java.util.ArrayList;

public class ConfigActivity extends AppCompatActivity {

    private Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        context = getApplicationContext();
        
        setContentView(R.layout.activity_config);
        
        final EditText editServer = (EditText) findViewById(R.id.editServer);
        final EditText editPeriod = (EditText) findViewById(R.id.editPeriod);
        
        Button sendButton = (Button) findViewById(R.id.sendButton);
        Button startButton = (Button) findViewById(R.id.startButton);
        Button stopButton = (Button) findViewById(R.id.stopButton);
        
        sendButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String server = editServer.getText().toString();
               
               String message = null;
               if(server!= null && server.length()>0) {
                   TrackCustomer spyingForYou = new TrackCustomer(getApplicationContext(), server);
                   spyingForYou.requestServer(43.6, 1.25);
                   message = "Sended to "+server;
               } else {
                   message = "No server defined";
               }
               
               showMessage(message);
           } 
        });
        
        startButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent serviceIntent = new Intent(context, FollowService.class);
               Bundle serviceBundle = new Bundle(1);
               ArrayList<String> serviceArgs = new ArrayList<String>(2);
               String server=editServer.getText().toString();
               String period=editPeriod.getText().toString();
               serviceArgs.add(server);
               serviceArgs.add(period);
               serviceBundle.putStringArrayList("serviceArgs", serviceArgs);
               serviceIntent.putExtra("args", serviceBundle);
               context.startService(serviceIntent);
           }
       });

        stopButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {  
               context.stopService(new Intent(context, FollowService.class));
           }
       });  
    }   
       
    @Override
    public void onPause() {
        super.onPause();
    }
       
    @Override
    public void onResume() {
        super.onResume();
    }
    
    private void showMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
