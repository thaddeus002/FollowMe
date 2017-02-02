package org.thaddeus.followme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class ConfigActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        
        final TrackCustomer spyingForYou = new TrackCustomer(getApplicationContext());
        
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
                   spyingForYou.setServer(server);
                   spyingForYou.requestServer();
                   message = "Sended to "+server;
               } else {
                   message = "No server defined";
               }
               
               Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
           } 
        });
        
        startButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
           }
       });
               
        stopButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {  
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
}
