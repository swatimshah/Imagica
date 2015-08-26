package com.example.imagica;

import java.util.ArrayList;
import java.util.Calendar;

import com.example.imagica.rest.ImagicaWSTask;
import com.example.imagica.rest.WebServiceTask;
import com.example.imagica.util.Utils;
import com.example.imagica.vo.PatientVo;
import com.github.mikephil.charting.data.Entry;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChartEntryActivity extends Activity {

	private ImageView nextImage = null;
	private PatientVo patientVo = null;
	private String parameterResp = "";
	private TextView respValue;
	private String parameterBp = "";
	private TextView bpValue;
	private String parameterTemp = "";
	private TextView tempValue;
	private String parameterPulse = "";
	private TextView pulseValue;
	private String parameterPap = "";
	private TextView papValue;
	private String parameterCvp = "";
	private TextView cvpValue;
	ArrayList<String> errorMessagesList = new ArrayList<String>();
	TextView errorMessages = null;
	TextView aDate = null;
	ImageButton calendarButton = null;
	TextView aTime = null;
	TableLayout tableLayout1 = null;
	Button save = null;
	private static final String SERVICE_URL = "https://aneuron-hmsone.rhcloud.com/rest/graph";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_chart_entry);

		WindowManager wm = (WindowManager) this
				.getSystemService(this.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		RelativeLayout test = (RelativeLayout) findViewById(R.id.testLayout);
		LayoutParams params = test.getLayoutParams();
		params.height = (int) (display.getHeight() * 0.85);
		// new LayoutParams(display.getWidth(), display.getHeight());
		test.setLayoutParams(params);

		TextView patientNameView = (TextView) findViewById(R.id.printPatientName);
		TextView ageView = (TextView) findViewById(R.id.printAge);
		TextView bedView = (TextView) findViewById(R.id.printBed);
		TextView admissionDateView = (TextView) findViewById(R.id.printAdmissionDate);
		TextView icuView = (TextView) findViewById(R.id.printICU);

		Intent intent = getIntent();
		patientVo = (PatientVo) intent.getSerializableExtra("patientVo");

		patientNameView.setText("PatientName: " + patientVo.getPatientName());
		ageView.setText("Age: " + patientVo.getAge());
		bedView.setText("    Bed: " + patientVo.getBed());
		admissionDateView.setText("Admission Date: "
				+ patientVo.getAdmissionDate());
		icuView.setText("ICU: " + patientVo.getIcu());

		// Resp

		TextView respLbl = (TextView) findViewById(R.id.respLbl);
		parameterResp = respLbl.getText().toString();

		respValue = (EditText) findViewById(R.id.respValue);
		respValue.setBackgroundResource(android.R.drawable.editbox_background);
		respValue.setInputType(InputType.TYPE_CLASS_NUMBER);

		// BP
		TextView bpLbl = (TextView) findViewById(R.id.bpLbl);
		parameterBp = bpLbl.getText().toString();

		bpValue = (EditText) findViewById(R.id.bpValue);
		bpValue.setBackgroundResource(android.R.drawable.editbox_background);
		bpValue.setInputType(InputType.TYPE_CLASS_NUMBER);

		// Temperature

		TextView tempLbl = (TextView) findViewById(R.id.tempLbl);
		parameterTemp = tempLbl.getText().toString();

		tempValue = (EditText) findViewById(R.id.tempValue);
		tempValue.setBackgroundResource(android.R.drawable.editbox_background);
		tempValue.setInputType(InputType.TYPE_CLASS_NUMBER);

		// Pulse

		TextView pulseLbl = (TextView) findViewById(R.id.pulseLbl);
		parameterPulse = pulseLbl.getText().toString();

		pulseValue = (EditText) findViewById(R.id.pulseValue);
		pulseValue.setBackgroundResource(android.R.drawable.editbox_background);
		pulseValue.setInputType(InputType.TYPE_CLASS_NUMBER);

		// PAP

		TextView papLbl = (TextView) findViewById(R.id.papLbl);
		parameterPap = papLbl.getText().toString();

		papValue = (EditText) findViewById(R.id.papValue);
		papValue.setBackgroundResource(android.R.drawable.editbox_background);
		papValue.setInputType(InputType.TYPE_CLASS_NUMBER);

		// PAP

		TextView cvpLbl = (TextView) findViewById(R.id.cvpLbl);
		parameterCvp = cvpLbl.getText().toString();

		cvpValue = (EditText) findViewById(R.id.cvpValue);
		cvpValue.setBackgroundResource(android.R.drawable.editbox_background);
		cvpValue.setInputType(InputType.TYPE_CLASS_NUMBER);

		aDate = (TextView) findViewById(R.id.aDate);
		aDate.setText(Utils.getCurrentDateInSpecifiedFormat());

		calendarButton = (ImageButton) findViewById(R.id.calendarButton);
		calendarButton.setOnClickListener(calendarListener);

		aTime = (EditText) findViewById(R.id.aTime);
		aTime.setBackgroundResource(android.R.drawable.editbox_background);
		aTime.setInputType(InputType.TYPE_DATETIME_VARIATION_TIME
				| InputType.TYPE_CLASS_DATETIME);

		nextImage = (ImageView) findViewById(R.id.next);
		nextImage.setOnClickListener(listener);

		Button saveButton = (Button) findViewById(R.id.saveChartData);
		saveButton.setOnClickListener(saveListener);

		errorMessages = (TextView) findViewById(R.id.errorMessage);
		tableLayout1 = (TableLayout) findViewById(R.id.tableLayout1);
		save = (Button) findViewById(R.id.saveChartData);
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
			TextView aDate = (TextView) findViewById(R.id.aDate);

			String dayString = Integer.valueOf(selectedDay).toString();
			if (dayString.length() < 2)
				dayString = "0" + dayString;

			String monthString = Integer.valueOf(selectedMonth + 1).toString();
			if (monthString.length() < 2)
				monthString = "0" + monthString;

			String selectedDate = dayString + "/" + monthString + "/"
					+ selectedYear;

			aDate.setText(selectedDate);

		}
	};

	OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// Creating the instance of PopupMenu
			PopupMenu popup = new PopupMenu(getApplicationContext(), nextImage);
			// Inflating the Popup using xml file
			popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

			// registering popup with OnMenuItemClickListener
			popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
				public boolean onMenuItemClick(MenuItem item) {
					if ("History".equals(item.getTitle())) {
						History.renderHistory(ChartEntryActivity.this);
					} else if ("Daily Notes".equals(item.getTitle())) {

						Intent intent = new Intent(ChartEntryActivity.this,
								DailyNotesActivity.class);
						intent.putExtra("patientVo", patientVo);
						startActivity(intent);

					} else if ("Chart Entry".equals(item.getTitle())) {

						Intent intent = new Intent(ChartEntryActivity.this,
								ChartEntryActivity.class);
						intent.putExtra("patientVo", patientVo);
						startActivity(intent);

					} else if ("Chart View".equals(item.getTitle())) {

						Intent intent = new Intent(ChartEntryActivity.this,
								ImagicaActivity.class);
						intent.putExtra("selectedPatient", patientVo);
						startActivity(intent);

					} else {

						Toast.makeText(getApplicationContext(),
								"You Clicked : " + item.getTitle(),
								Toast.LENGTH_SHORT).show();
					}

					return true;
				}
			});

			popup.show();// showing popup menu
		}
	};// closing the setOnClickListener method

	OnClickListener saveListener = new OnClickListener() {
		@Override
		public void onClick(View v) {

			ArrayList<ArrayList<Entry>> entries = new ArrayList<ArrayList<Entry>>();
			errorMessagesList.clear();
			
			EditText aTime = (EditText) findViewById(R.id.aTime);
			int aTimeInt = 0;
			if (aTime.getText() == null
					|| "".equals(aTime.getText().toString().trim())) {
				errorMessagesList.add("Please enter a valid time");
			} else {
				String aTimeString = aTime.getText().toString().trim();
				aTimeInt = Integer.valueOf(aTimeString.substring(0,
						aTimeString.indexOf(":")));
			}
			// Respiration

			int xAxisIndex = aTimeInt;
			int resp = 0;

			if (respValue.getText() == null
					|| "".equals(respValue.getText().toString().trim())) {
				errorMessagesList.add("Please enter a Resp value");
			} else {
				resp = Integer.valueOf(respValue.getText().toString().trim());
			}

			int yAxisValue = resp;

			ArrayList<Entry> entriesResp = new ArrayList<Entry>();
			entriesResp.add(new Entry(yAxisValue, xAxisIndex));

			// BP

			int xAxisIndexBP = aTimeInt;
			int bp = 0;

			if (bpValue.getText() == null
					|| "".equals(bpValue.getText().toString().trim())) {
				errorMessagesList.add("Please enter a BP value");
			} else {

				bp = Integer.valueOf(bpValue.getText().toString().trim());
			}

			int yAxisValueBP = bp;

			ArrayList<Entry> entriesBP = new ArrayList<Entry>();
			entriesBP.add(new Entry(yAxisValueBP, xAxisIndexBP));

			// Temp

			int xAxisIndexTemp = aTimeInt;
			int temp = 0;

			if (tempValue.getText() == null
					|| "".equals(tempValue.getText().toString().trim())) {
				errorMessagesList.add("Please enter a Temp value");
			} else {

				temp = Integer.valueOf(tempValue.getText().toString().trim());
			}

			int yAxisValueTemp = temp;

			ArrayList<Entry> entriesTemp = new ArrayList<Entry>();
			entriesTemp.add(new Entry(yAxisValueTemp, xAxisIndexTemp));

			// Pulse

			int xAxisIndexPulse = aTimeInt;
			int pulse = 0;

			if (pulseValue.getText() == null
					|| "".equals(pulseValue.getText().toString().trim())) {
				errorMessagesList.add("Please enter a Pulse value");
			} else {

				pulse = Integer.valueOf(pulseValue.getText().toString().trim());
			}

			int yAxisValuePulse = pulse;

			ArrayList<Entry> entriesPulse = new ArrayList<Entry>();
			entriesPulse.add(new Entry(yAxisValuePulse, xAxisIndexPulse));

			// PAP

			int xAxisIndexPap = aTimeInt;
			int pap = 0;

			if (papValue.getText() == null
					|| "".equals(papValue.getText().toString().trim())) {
				errorMessagesList.add("Please enter a PAP value");
			} else {

				pap = Integer.valueOf(papValue.getText().toString().trim());
			}

			int yAxisValuePap = pap;

			ArrayList<Entry> entriesPap = new ArrayList<Entry>();
			entriesPap.add(new Entry(yAxisValuePap, xAxisIndexPap));

			// CVP

			int xAxisIndexCvp = aTimeInt;
			int cvp = 0;

			if (cvpValue.getText() == null
					|| "".equals(cvpValue.getText().toString().trim())) {
				errorMessagesList.add("Please enter a CVP value");
			} else {

				cvp = Integer.valueOf(cvpValue.getText().toString().trim());
			}

			int yAxisValueCvp = cvp;

			ArrayList<Entry> entriesCvp = new ArrayList<Entry>();
			entriesCvp.add(new Entry(yAxisValueCvp, xAxisIndexCvp));

			entries.add(entriesResp);
			entries.add(entriesBP);
			entries.add(entriesTemp);
			entries.add(entriesPulse);
			entries.add(entriesPap);
			entries.add(entriesCvp);

			TextView selectedDate = (TextView) findViewById(R.id.aDate);

			if (errorMessagesList.size() > 0) {
				errorMessages.setTextColor(Color.RED);
				errorMessages.setText(errorMessagesList.toString());
			} else {
				errorMessages.setText("");
				savePoints(entries, selectedDate.getText().toString());
			}
		}
	};// closing the setOnClickListener method

	public void savePoints(ArrayList<ArrayList<Entry>> entries,
			String selectedDate) {

		ImagicaWSTask wst = new ImagicaWSTask(WebServiceTask.POST_TASK, this,
				"Posting data...");

		StringBuffer completeJsonString = new StringBuffer();

		completeJsonString.append("[");

		// Populate Respiration

		completeJsonString.append("{");

		completeJsonString.append("\"" + "patientId" + "\"" + ":" + "\""
				+ patientVo.getPatientId() + "\",");

		String formattedSelectedDate = Utils.convertDateFormat(selectedDate);

		completeJsonString.append("\"" + "selectedDate" + "\"" + ":" + "\""
				+ formattedSelectedDate + "\",");

		completeJsonString.append("\"" + "parameter" + "\"" + ":" + "\""
				+ parameterResp + "\",");

		StringBuffer pointsStringBuffer = new StringBuffer();
		String pointsString = "";

		for (Entry entry : entries.get(0)) {

			if (entry != null)
				pointsStringBuffer.append(entry.getXIndex() + ", "
						+ entry.getVal() + "|");

		}

		if (pointsStringBuffer.lastIndexOf("|") != -1) {
			pointsStringBuffer
					.deleteCharAt(pointsStringBuffer.lastIndexOf("|"));
		}
		pointsString = pointsStringBuffer.toString();

		completeJsonString.append("\"" + "graphPoints" + "\"" + ":" + "\""
				+ pointsString + "\",");

		completeJsonString.deleteCharAt(completeJsonString.lastIndexOf(","));

		completeJsonString.append("},");

		// Populate BP

		completeJsonString.append("{");

		completeJsonString.append("\"" + "patientId" + "\"" + ":" + "\""
				+ patientVo.getPatientId() + "\",");

		completeJsonString.append("\"" + "selectedDate" + "\"" + ":" + "\""
				+ formattedSelectedDate + "\",");

		completeJsonString.append("\"" + "parameter" + "\"" + ":" + "\""
				+ parameterBp + "\",");

		StringBuffer pointsStringBufferBp = new StringBuffer();
		String pointsStringBp = "";

		for (Entry entry : entries.get(1)) {

			if (entry != null)
				pointsStringBufferBp.append(entry.getXIndex() + ", "
						+ entry.getVal() + "|");

		}

		if (pointsStringBufferBp.lastIndexOf("|") != -1) {
			pointsStringBufferBp.deleteCharAt(pointsStringBufferBp
					.lastIndexOf("|"));
		}
		pointsStringBp = pointsStringBufferBp.toString();

		completeJsonString.append("\"" + "graphPoints" + "\"" + ":" + "\""
				+ pointsStringBp + "\",");

		completeJsonString.deleteCharAt(completeJsonString.lastIndexOf(","));

		completeJsonString.append("},");

		// Populate Temp

		completeJsonString.append("{");

		completeJsonString.append("\"" + "patientId" + "\"" + ":" + "\""
				+ patientVo.getPatientId() + "\",");

		completeJsonString.append("\"" + "selectedDate" + "\"" + ":" + "\""
				+ formattedSelectedDate + "\",");

		completeJsonString.append("\"" + "parameter" + "\"" + ":" + "\""
				+ parameterTemp + "\",");

		StringBuffer pointsStringBufferTemp = new StringBuffer();
		String pointsStringTemp = "";

		for (Entry entry : entries.get(2)) {

			if (entry != null)
				pointsStringBufferTemp.append(entry.getXIndex() + ", "
						+ entry.getVal() + "|");

		}

		if (pointsStringBufferTemp.lastIndexOf("|") != -1) {
			pointsStringBufferTemp.deleteCharAt(pointsStringBufferTemp
					.lastIndexOf("|"));
		}
		pointsStringTemp = pointsStringBufferTemp.toString();

		completeJsonString.append("\"" + "graphPoints" + "\"" + ":" + "\""
				+ pointsStringTemp + "\",");

		completeJsonString.deleteCharAt(completeJsonString.lastIndexOf(","));

		completeJsonString.append("},");

		// Populate Pulse

		completeJsonString.append("{");

		completeJsonString.append("\"" + "patientId" + "\"" + ":" + "\""
				+ patientVo.getPatientId() + "\",");

		completeJsonString.append("\"" + "selectedDate" + "\"" + ":" + "\""
				+ formattedSelectedDate + "\",");

		completeJsonString.append("\"" + "parameter" + "\"" + ":" + "\""
				+ parameterPulse + "\",");

		StringBuffer pointsStringBufferPulse = new StringBuffer();
		String pointsStringPulse = "";

		for (Entry entry : entries.get(3)) {

			if (entry != null)
				pointsStringBufferPulse.append(entry.getXIndex() + ", "
						+ entry.getVal() + "|");

		}

		if (pointsStringBufferPulse.lastIndexOf("|") != -1) {
			pointsStringBufferPulse.deleteCharAt(pointsStringBufferPulse
					.lastIndexOf("|"));
		}
		pointsStringPulse = pointsStringBufferPulse.toString();

		completeJsonString.append("\"" + "graphPoints" + "\"" + ":" + "\""
				+ pointsStringPulse + "\",");

		completeJsonString.deleteCharAt(completeJsonString.lastIndexOf(","));

		completeJsonString.append("},");

		// Populate PAP

		completeJsonString.append("{");

		completeJsonString.append("\"" + "patientId" + "\"" + ":" + "\""
				+ patientVo.getPatientId() + "\",");

		completeJsonString.append("\"" + "selectedDate" + "\"" + ":" + "\""
				+ formattedSelectedDate + "\",");

		completeJsonString.append("\"" + "parameter" + "\"" + ":" + "\""
				+ parameterPap + "\",");

		StringBuffer pointsStringBufferPap = new StringBuffer();
		String pointsStringPap = "";

		for (Entry entry : entries.get(4)) {

			if (entry != null)
				pointsStringBufferPap.append(entry.getXIndex() + ", "
						+ entry.getVal() + "|");

		}

		if (pointsStringBufferPap.lastIndexOf("|") != -1) {
			pointsStringBufferPap.deleteCharAt(pointsStringBufferPap
					.lastIndexOf("|"));
		}
		pointsStringPap = pointsStringBufferPap.toString();

		completeJsonString.append("\"" + "graphPoints" + "\"" + ":" + "\""
				+ pointsStringPap + "\",");

		completeJsonString.deleteCharAt(completeJsonString.lastIndexOf(","));

		completeJsonString.append("},");

		// Populate CVP

		completeJsonString.append("{");

		completeJsonString.append("\"" + "patientId" + "\"" + ":" + "\""
				+ patientVo.getPatientId() + "\",");

		completeJsonString.append("\"" + "selectedDate" + "\"" + ":" + "\""
				+ formattedSelectedDate + "\",");

		completeJsonString.append("\"" + "parameter" + "\"" + ":" + "\""
				+ parameterCvp + "\",");

		StringBuffer pointsStringBufferCvp = new StringBuffer();
		String pointsStringCvp = "";

		for (Entry entry : entries.get(5)) {

			if (entry != null)
				pointsStringBufferCvp.append(entry.getXIndex() + ", "
						+ entry.getVal() + "|");

		}

		if (pointsStringBufferCvp.lastIndexOf("|") != -1) {
			pointsStringBufferCvp.deleteCharAt(pointsStringBufferCvp
					.lastIndexOf("|"));
		}
		pointsStringCvp = pointsStringBufferCvp.toString();

		completeJsonString.append("\"" + "graphPoints" + "\"" + ":" + "\""
				+ pointsStringCvp + "\",");

		completeJsonString.deleteCharAt(completeJsonString.lastIndexOf(","));

		completeJsonString.append("}");

		// End of JSON array population

		completeJsonString.append("]");

		wst.setJson(completeJsonString.toString());
		// the passed String is the URL we will POST to
		wst.execute(new String[] { SERVICE_URL });

	}

}
