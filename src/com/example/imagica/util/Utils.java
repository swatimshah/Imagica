package com.example.imagica.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.util.Log;

public class Utils {
	
	public static String convertDateFormat(String admissionDateString) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String dateInString = admissionDateString;
		Date date = Calendar.getInstance().getTime();

		try {

			date = formatter.parse(dateInString);

		} catch (Exception e) {

			Log.e("error", "admission date exception: " + e.getMessage());
			//errorMessages.append(e.getMessage());
			e.printStackTrace();

		}

		java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date unformattedAdmissionDate = date;
		String formattedAdmissionDate = df.format(unformattedAdmissionDate);
		System.out.println("Admission Date: " + formattedAdmissionDate);
		return formattedAdmissionDate;
	}

	public static String getCurrentDateInSpecifiedFormat() {
		Date initialAdmissionDate = Calendar.getInstance().getTime();
		java.text.DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date unformattedInitialAdmissionDate = initialAdmissionDate;
		String formattedInitialAdmissionDate = dateFormat
				.format(unformattedInitialAdmissionDate);
		System.out.println("Selected Admission Date for the first time: "
				+ formattedInitialAdmissionDate);
		return formattedInitialAdmissionDate;
	}

	public static String convertMilliSecondsIntoDate(Long longDate) {
		// Date time formatting - start

		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd");
		
		Date date = Calendar.getInstance().getTime();

		try {

			date = new Date(longDate);

		} catch (Exception e) {

			Log.e("error",
					"admission date exception: " + e.getMessage());
			e.printStackTrace();
		}

		java.text.DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date unformattedAdmissionDate = date;
		TimeZone timeZone = TimeZone.getTimeZone("IST");
		df.setTimeZone(timeZone);
		String formattedAdmissionDate = df
				.format(unformattedAdmissionDate);
		System.out.println("Admission Date: "
				+ formattedAdmissionDate);

		// Date time formatting - end
		return formattedAdmissionDate;
	}
	
}
