package com.example.imagica;


import com.example.imagica.rest.MonitorPatientWSTask;
import com.example.imagica.rest.WebServiceTask;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.TextView;

public class MonitorPatientActivity extends Activity {
	
	private static final String SERVICE_URL = "https://aneuron-hmsone.rhcloud.com/rest/patient";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_monitor_patient);

		SearchView aSearchView = (SearchView) findViewById(R.id.searchView);
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		aSearchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));
		aSearchView.setSubmitButtonEnabled(true);

				
		int id = aSearchView.getContext().getResources()
				.getIdentifier("android:id/search_src_text", null, null);
		TextView textView = (TextView) aSearchView.findViewById(id);
		textView.setHintTextColor(Color.DKGRAY);

		
		Log.e("error", "Executing searchable activity:");

	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		// super.onNewIntent(intent);

		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {

			Log.e("error", "Received SEARCH intent");
			String query = intent.getStringExtra(SearchManager.QUERY);
			doMySearch(query);
		}

	}

	public void doMySearch(String query) {
		Log.e("error", "query: " + query);

		MonitorPatientWSTask wst = new MonitorPatientWSTask(WebServiceTask.GET_TASK, this,
				"Getting data...");

		// the passed String is the URL we will POST to
		wst.execute(new String[] { SERVICE_URL + "/" + query });

	}

}
