package com.jhson.mousecontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final EditText ipAddress = (EditText) findViewById(R.id.activity_main_ip);
        Button connectionBtn = (Button) findViewById(R.id.activity_main_connection_btn);

        ipAddress.setText("192.168.0.2");
        
        connectionBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String ipStr = ipAddress.getText().toString();
				Intent intent = new Intent(MainActivity.this, MouseActivity.class);
				intent.putExtra("ip", ipStr);
				startActivity(intent);
				
			}
		});
        
    }

}
