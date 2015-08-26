package com.example.imagica.adapter;

import java.util.List;

import com.example.imagica.R;
import com.example.imagica.R.id;
import com.example.imagica.R.layout;
import com.example.imagica.vo.PatientVo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PatientAdapter extends ArrayAdapter<PatientVo> {

	private Context context;
	private List<PatientVo> patients;

	public PatientAdapter(Context context, int textViewResourceId,
			List<PatientVo> patients) {
		super(context, textViewResourceId, patients);
		try {
			this.context = context;
			this.patients = patients;

		} catch (Exception e) {

		}
	}

	public int getCount() {
		return patients.size();
	}

	public PatientVo getItem(int position) {
		return patients.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public static class ViewHolder {
		public TextView patientName;
		public TextView age;
		public TextView bed;
		public TextView admissionDate;
		public TextView icu;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		//View view = convertView;
		final ViewHolder holder;

		if (convertView == null) {
			LayoutInflater vi = (LayoutInflater) ((Activity)context).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.patientlayout, null);
			holder = new ViewHolder();

			holder.patientName = (TextView) convertView
					.findViewById(R.id.printPatientName);
			holder.age = (TextView) convertView.findViewById(R.id.printAge);
			holder.bed = (TextView) convertView.findViewById(R.id.printBed);
			holder.admissionDate = (TextView) convertView
					.findViewById(R.id.printAdmissionDate);
			holder.icu = (TextView) convertView.findViewById(R.id.printICU);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.patientName.setText("Patient Name: " + patients.get(position).getPatientName());
//		Log.e("error", "setting patient name in holder: position"
//				+ holder.patientName.getText() + ":" + position);
		holder.age.setText("Age: " + patients.get(position).getAge() + "");
		holder.bed.setText("Bed: " + patients.get(position).getBed() + "");
		holder.admissionDate.setText("Admission Date: " + patients.get(position).getAdmissionDate());
		holder.icu.setText("ICU: " + patients.get(position).getIcu());

		return convertView;
	}

}
