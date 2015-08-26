package com.example.imagica;

import com.example.imagica.vo.PatientVo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class DailyNotesActivity extends Activity {

	private PatientVo patientVo = null;
	private ImageView nextImage = null;
	public LinearLayout notesLayout = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_daily_notes);

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

		EditText temperature = (EditText) findViewById(R.id.temperature);
		temperature
				.setBackgroundResource(android.R.drawable.editbox_background);

		EditText pulse = (EditText) findViewById(R.id.pulse);
		pulse.setBackgroundResource(android.R.drawable.editbox_background);

		EditText respiration = (EditText) findViewById(R.id.respiration);
		respiration
				.setBackgroundResource(android.R.drawable.editbox_background);

		EditText bp = (EditText) findViewById(R.id.bp);
		bp.setBackgroundResource(android.R.drawable.editbox_background);

		nextImage = (ImageView) findViewById(R.id.next);
		nextImage.setOnClickListener(listener);		
		
		notesLayout = (LinearLayout) findViewById(R.id.notesLayout);
	}
	
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
						History.renderHistory(DailyNotesActivity.this);
					} else if ("Daily Notes".equals(item.getTitle())) {

						Intent intent = new Intent(DailyNotesActivity.this,
								DailyNotesActivity.class);
						intent.putExtra("patientVo", patientVo);
						startActivity(intent);

					} else if ("Chart Entry".equals(item.getTitle())) {

						Intent intent = new Intent(DailyNotesActivity.this,
								ChartEntryActivity.class);
						intent.putExtra("patientVo", patientVo);
						startActivity(intent);

					} else if ("Chart View".equals(item.getTitle())) {

						Intent intent = new Intent(DailyNotesActivity.this,
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
	
}
