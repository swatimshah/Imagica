package com.example.imagica.rest;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.imagica.ImagicaActivity;
import com.example.imagica.R;
import com.example.imagica.R.id;
import com.example.imagica.R.layout;
import com.example.imagica.adapter.PatientAdapter;
import com.example.imagica.util.Utils;
import com.example.imagica.vo.PatientVo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MonitorPatientWSTask extends WebServiceTask {

	public MonitorPatientWSTask(int taskType, Context mContext,
			String processMessage) {
		super(taskType, mContext, processMessage);
		// TODO Auto-generated constructor stub
	}

	public void handleResponse(String response) {

		Log.e("Todo response", "Todo response: " + response);

		TextView error = (TextView) ((Activity) mContext)
				.findViewById(R.id.errorMessage);

		if (errorMessages != null && errorMessages.length() > 0) {
			error.setTextColor(Color.RED);
			error.setText(errorMessages.toString());
		}

		JSONArray inputArray = null;
		try {
			inputArray = new JSONArray(response);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<PatientVo> patientList = new ArrayList<PatientVo>();

		if (inputArray != null) {

			for (int i = 0; i < inputArray.length(); i++) {
				JSONObject jsonobject;
				try {

					PatientVo patientVo = new PatientVo();
					jsonobject = inputArray.getJSONObject(i);
					patientVo.setPatientId(jsonobject.getLong("patientId"));
					patientVo.setPatientName(jsonobject
							.getString("patientName"));
					Log.e("error",
							"Patient Name in Json: "
									+ patientVo.getPatientName());
					patientVo.setAge(jsonobject.getInt("age"));
					patientVo.setBed(jsonobject.getInt("bed"));

					Long longDate = jsonobject.getLong("admissionDate");
					patientVo.setAdmissionDate(Utils.convertMilliSecondsIntoDate(longDate));
					patientVo.setIcu(jsonobject.getString("icu"));
					patientList.add(patientVo);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			error.setText("");

		}

		displayPatientList(patientList);
		
	}

	private void displayPatientList(List<PatientVo> patientList) {
		// TODO Auto-generated method stub

		PatientAdapter patientAdapter = new PatientAdapter(mContext,
				R.layout.patientlayout, patientList);
		ListView listView = (ListView) ((Activity) mContext)
				.findViewById(R.id.listView);
		listView.setAdapter(patientAdapter);
		listView.setOnItemClickListener(customListViewListener);
	}

	OnItemClickListener customListViewListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub

			Log.e("error", "Detected a click..");

			Intent intent = new Intent(mContext, ImagicaActivity.class);
			intent.putExtra("selectedPatient",
					(PatientVo) parent.getItemAtPosition(position));
			mContext.startActivity(intent);
		}
	};

}
