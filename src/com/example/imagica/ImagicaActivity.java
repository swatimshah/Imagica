package com.example.imagica;

import java.util.Calendar;

import com.example.imagica.rest.ImagicaWSTask;
import com.example.imagica.rest.WebServiceTask;
import com.example.imagica.util.Utils;
import com.example.imagica.vo.PatientVo;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ImagicaActivity extends Activity {

	protected ImageButton nextImage = null;
	private PatientVo patientVo = null;
	private static final String SERVICE_URL = "https://aneuron-hmsone.rhcloud.com/rest/graph";
//	public int hours;
	public LineChart chart;
	public LineDataSet respDataSet = null;
	public LineDataSet bpDataSet = null;
	public LineDataSet tempDataSet = null;
	public LineDataSet pulseDataSet = null;
	public LineDataSet papDataSet = null;
	public LineDataSet cvpDataSet = null;	
	public TableLayout tableLayout1 = null;
	public ImageButton calendarButton = null; 	
	public TextView aDate = null;	
	
	public PatientVo getPatientVo() {
		return patientVo;
	}

	public void setPatientVo(PatientVo patientVo) {
		this.patientVo = patientVo;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_imagica);

		TextView patientNameView = (TextView) findViewById(R.id.printPatientName);
		TextView ageView = (TextView) findViewById(R.id.printAge);
		TextView bedView = (TextView) findViewById(R.id.printBed);
		TextView admissionDateView = (TextView) findViewById(R.id.printAdmissionDate);
		TextView icuView = (TextView) findViewById(R.id.printICU);
		calendarButton = (ImageButton) findViewById(R.id.calendarButton);
		calendarButton.setOnClickListener(calendarListener);

		Intent intent = getIntent();
		patientVo = (PatientVo) intent.getSerializableExtra("selectedPatient");

		patientNameView.setText("PatientName: " + patientVo.getPatientName());
		ageView.setText("Age: " + patientVo.getAge());
		bedView.setText("    Bed: " + patientVo.getBed());
		admissionDateView.setText("Admission Date: "
				+ patientVo.getAdmissionDate());
		icuView.setText("ICU: " + patientVo.getIcu());

		setTitle("View Chart");

		nextImage = (ImageButton) findViewById(R.id.next);
		nextImage.setOnClickListener(listener);

		chart = (LineChart) findViewById(R.id.chart);

		tableLayout1 = (TableLayout) findViewById(R.id.tableLayout1);
		
		// ------------------------------

		aDate = (TextView) findViewById(R.id.aDate);
		aDate.setText(Utils.getCurrentDateInSpecifiedFormat());
		loadPoints(Utils.getCurrentDateInSpecifiedFormat());

		CheckBox respCheckBox = (CheckBox) findViewById(R.id.RespCheckBox);
		respCheckBox.setOnClickListener(respListener);

		CheckBox bpCheckBox = (CheckBox) findViewById(R.id.BPCheckBox);
		bpCheckBox.setOnClickListener(bpListener);

		CheckBox tempCheckBox = (CheckBox) findViewById(R.id.TempCheckBox);
		tempCheckBox.setOnClickListener(tempListener);
		
		CheckBox pulseCheckBox = (CheckBox) findViewById(R.id.PulseCheckBox);
		pulseCheckBox.setOnClickListener(pulseListener);

		CheckBox papCheckBox = (CheckBox) findViewById(R.id.PAPCheckBox);
		papCheckBox.setOnClickListener(papListener);
		
		CheckBox cvpCheckBox = (CheckBox) findViewById(R.id.CVPCheckBox);
		cvpCheckBox.setOnClickListener(cvpListener);

	}

	OnClickListener respListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			if (!((CheckBox) v).isChecked()) {

				//respDataSet = chart.getData().getDataSetByLabel("Resp", true);

				chart.getData().removeDataSet(respDataSet);
				chart.invalidate();
			} else {
				if (!chart.getData().contains(respDataSet)) {
					chart.getData().addDataSet(respDataSet);
					chart.invalidate();
				}
			}
		}
	};

	OnClickListener bpListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			if (!((CheckBox) v).isChecked()) {

				//respDataSet = chart.getData().getDataSetByLabel("Resp", true);

				chart.getData().removeDataSet(bpDataSet);
				chart.invalidate();
			} else {
				if (!chart.getData().contains(bpDataSet)) {
					chart.getData().addDataSet(bpDataSet);
					chart.invalidate();
				}
			}
		}
	};


	OnClickListener tempListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			if (!((CheckBox) v).isChecked()) {

				//respDataSet = chart.getData().getDataSetByLabel("Resp", true);

				chart.getData().removeDataSet(tempDataSet);
				chart.invalidate();
			} else {
				if (!chart.getData().contains(tempDataSet)) {
					chart.getData().addDataSet(tempDataSet);
					chart.invalidate();
				}
			}
		}
	};
	
	OnClickListener pulseListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			if (!((CheckBox) v).isChecked()) {

				//respDataSet = chart.getData().getDataSetByLabel("Resp", true);

				chart.getData().removeDataSet(pulseDataSet);
				chart.invalidate();
			} else {
				if (!chart.getData().contains(pulseDataSet)) {
					chart.getData().addDataSet(pulseDataSet);
					chart.invalidate();
				}
			}
		}
	};

	OnClickListener papListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			if (!((CheckBox) v).isChecked()) {

				//respDataSet = chart.getData().getDataSetByLabel("Resp", true);

				chart.getData().removeDataSet(papDataSet);
				chart.invalidate();
			} else {
				if (!chart.getData().contains(papDataSet)) {
					chart.getData().addDataSet(papDataSet);
					chart.invalidate();
				}
			}
		}
	};
	
	OnClickListener cvpListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			if (!((CheckBox) v).isChecked()) {

				//respDataSet = chart.getData().getDataSetByLabel("Resp", true);

				chart.getData().removeDataSet(cvpDataSet);
				chart.invalidate();
			} else {
				if (!chart.getData().contains(cvpDataSet)) {
					chart.getData().addDataSet(cvpDataSet);
					chart.invalidate();
				}
			}
		}
	};
	
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

//			TextView currentTimeView = (TextView) findViewById(R.id.currentTime);
//			Calendar cal = Calendar.getInstance();
//			hours = cal.get(Calendar.HOUR_OF_DAY);
//			int minutes = cal.get(Calendar.MINUTE);
//			String curTime = hours + ":" + minutes;
//			currentTimeView.setText(curTime);

			loadPoints(selectedDate);

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
						History.renderHistory(ImagicaActivity.this);
					} else if ("Daily Notes".equals(item.getTitle())) {

						Intent intent = new Intent(ImagicaActivity.this,
								DailyNotesActivity.class);
						intent.putExtra("patientVo", patientVo);
						startActivity(intent);

					} else if ("Chart Entry".equals(item.getTitle())) {

						Intent intent = new Intent(ImagicaActivity.this,
								ChartEntryActivity.class);
						intent.putExtra("patientVo", patientVo);
						startActivity(intent);

					} else if ("Chart View".equals(item.getTitle())) {

						Intent intent = new Intent(ImagicaActivity.this,
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

	public void loadPoints(String selectedDate) {

		ImagicaWSTask wst = new ImagicaWSTask(WebServiceTask.GET_TASK, this,
				"Getting data...");

		String formattedSelectedDate = Utils.convertDateFormat(selectedDate);

		// the passed String is the URL we will POST to
		wst.execute(new String[] { SERVICE_URL + "?patientId="
				+ patientVo.getPatientId() + "&selectedDate="
				+ formattedSelectedDate });

	}

}
