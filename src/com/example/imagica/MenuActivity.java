package com.example.imagica;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		Button admitPatientButton = (Button) findViewById(R.id.admitPatient);
		admitPatientButton.setOnClickListener(admitPatientListener);

		Button monitorPatientButton = (Button) findViewById(R.id.monitorPatient);
		monitorPatientButton.setOnClickListener(monitorPatientListener);
		
	}
	
	OnClickListener admitPatientListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getApplicationContext(), AdmitPatientActivity.class);
			startActivity(intent);			
			
		}
	};

	OnClickListener monitorPatientListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getApplicationContext(), MonitorPatientActivity.class);
			startActivity(intent);			
			
		}
	};
	
	
}
