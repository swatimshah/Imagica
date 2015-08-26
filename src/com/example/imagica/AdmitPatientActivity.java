package com.example.imagica;

import java.util.Calendar;

import com.example.imagica.rest.AdmitPatientWSTask;
import com.example.imagica.rest.WebServiceTask;
import com.example.imagica.util.Utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class AdmitPatientActivity extends Activity {

	private static final String SERVICE_URL = "https://aneuron-hmsone.rhcloud.com/rest/patient";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_admit_patient);

		EditText patientName = (EditText) findViewById(R.id.patientName);
		patientName
				.setBackgroundResource(android.R.drawable.editbox_background);
		EditText age = (EditText) findViewById(R.id.age);
		age.setBackgroundResource(android.R.drawable.editbox_background);
		EditText bed = (EditText) findViewById(R.id.bed);
		bed.setBackgroundResource(android.R.drawable.editbox_background);

		ImageButton calendarButton = (ImageButton) findViewById(R.id.calendarButton);
		calendarButton.setOnClickListener(calendarListener);

		String formattedInitialAdmissionDate = Utils
				.getCurrentDateInSpecifiedFormat();
		EditText admissionDate = (EditText) findViewById(R.id.admissionDate);
		admissionDate.setText(formattedInitialAdmissionDate);
		admissionDate
				.setBackgroundResource(android.R.drawable.editbox_background);

	}

	public void admitPatient(View vw) {

		EditText patientName = (EditText) findViewById(R.id.patientName);
		EditText age = (EditText) findViewById(R.id.age);
		EditText bed = (EditText) findViewById(R.id.bed);
		EditText admissionDate = (EditText) findViewById(R.id.admissionDate);
		Spinner icu = (Spinner) findViewById(R.id.spinner1);

		String patientNameString = patientName.getText().toString();
		String ageString = age.getText().toString();
		String bedString = bed.getText().toString();
		String admissionDateString = admissionDate.getText().toString();
		String icuString = ((String) icu.getSelectedItem()).toString();

		AdmitPatientWSTask wst = new AdmitPatientWSTask(
				WebServiceTask.POST_TASK, this, "Posting data...");
		wst.initializeErrorBuffer();

		StringBuffer completeJsonString = new StringBuffer();

		completeJsonString.append("{");

		completeJsonString.append("\"" + "patientName" + "\"" + ":" + "\""
				+ patientNameString + "\",");

		completeJsonString.append("\"" + "age" + "\"" + ":" + "\"" + ageString
				+ "\",");

		completeJsonString.append("\"" + "bed" + "\"" + ":" + "\"" + bedString
				+ "\",");

		String formattedAdmissionDate = Utils
				.convertDateFormat(admissionDateString);

		completeJsonString.append("\"" + "admissionDate" + "\"" + ":" + "\""
				+ formattedAdmissionDate + "\",");

		completeJsonString.append("\"" + "icu" + "\"" + ":" + "\"" + icuString
				+ "\",");

		completeJsonString.append("}");

		completeJsonString.deleteCharAt(completeJsonString.lastIndexOf(","));

		wst.setJson(completeJsonString.toString());
		// the passed String is the URL we will POST to
		wst.execute(new String[] { SERVICE_URL });

	}

	OnClickListener calendarListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			showDialog(0);
		}
	};

	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);

		return new DatePickerDialog(this, datePickerListener, year, month, day);
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			TextView admissionDate = (TextView) findViewById(R.id.admissionDate);

			String dayString = Integer.valueOf(selectedDay).toString();
			if (dayString.length() < 2)
				dayString = "0" + dayString;

			String monthString = Integer.valueOf(selectedMonth + 1).toString();
			if (monthString.length() < 2)
				monthString = "0" + monthString;

			String selectedDate = dayString + "/" + monthString + "/"
					+ selectedYear;

			admissionDate.setText(selectedDate);

		}
	};

}
