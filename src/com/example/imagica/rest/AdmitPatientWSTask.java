package com.example.imagica.rest;

import java.io.IOException;

import org.apache.http.HttpResponse;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import com.example.imagica.R;
import com.example.imagica.R.id;


public class AdmitPatientWSTask extends WebServiceTask {

	public AdmitPatientWSTask(int taskType, Context mContext,
			String processMessage) {
		super(taskType, mContext, processMessage);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void handleResponse(String response) {
		// TODO Auto-generated method stub
		Log.e("Todo response", "Todo response: " + response);
		TextView result = (TextView) ((Activity) mContext)
				.findViewById(R.id.resultMessage);
		TextView error = (TextView) ((Activity) mContext)
				.findViewById(R.id.errorMessage);

		if (errorMessages != null && errorMessages.length() > 0) {
			error.setTextColor(Color.RED);
			error.setText(errorMessages.toString());
		}

		if (errorMessages != null && errorMessages.length() == 0
				&& (response == null || response.trim().equals(""))) {
			result.setTextColor(Color.GREEN);
			result.setText("The patient is admitted successfully");
			error.setText("");
		} else {
			result.setTextColor(Color.RED);
			result.setText("The patient could not be admitted. Reason: "
					+ response);
		}

	}

	public void initializeErrorBuffer() {
		// TODO Auto-generated method stub
		errorMessages = new StringBuffer();
	}

}
