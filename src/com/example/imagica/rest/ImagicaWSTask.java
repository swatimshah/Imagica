package com.example.imagica.rest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.imagica.ImagicaActivity;
import com.example.imagica.R;
import com.example.imagica.R.id;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ImagicaWSTask extends WebServiceTask {

	public ImagicaWSTask(int taskType, Context mContext, String processMessage) {
		super(taskType, mContext, processMessage);
		// TODO Auto-generated constructor stub
	}

	public void handleResponse(String response) {

		Log.e("Todo response", "Graph response: " + response);

		TextView error = (TextView) ((Activity) mContext)
				.findViewById(R.id.errorMessage);
		if (errorMessages != null && errorMessages.length() > 0) {
			error.setTextColor(Color.RED);
			error.setText(errorMessages.toString());
		} else if (errorMessages != null && errorMessages.length() == 0
				&& (response == null || response.trim().equals(""))) {
			Toast.makeText(mContext, "The graph was saved successfully.",
					Toast.LENGTH_SHORT).show();
		} else if (response.contains("Error report")
				&& response.contains("JBWEB000124")) {

			LineChart chart = (LineChart) ((ImagicaActivity) mContext)
					.findViewById(R.id.chart);
			chart.clear();
			// chart.invalidate();
			// if ((ViewGroup) ((ImagicaActivity) mContext).tableLayout1
			// .getParent() != null) {
			// ((ViewGroup) ((ImagicaActivity) mContext).tableLayout1
			// .getParent())
			// .removeView(((ImagicaActivity) mContext).tableLayout1);
			(((ImagicaActivity) mContext).tableLayout1)
					.setVisibility(View.INVISIBLE);
			// }
			error.setText("");
		} else {

			Log.e("error", "parse JSON object array .. ");

			JSONArray array = null;
			try {
				array = new JSONArray(response);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			ArrayList<String> labels = new ArrayList<String>();
			labels.add("00");
			labels.add("01");
			labels.add("02");
			labels.add("03");
			labels.add("04");
			labels.add("05");
			labels.add("06");
			labels.add("07");
			labels.add("08");
			labels.add("09");
			labels.add("10");
			labels.add("11");
			labels.add("12");
			labels.add("13");
			labels.add("14");
			labels.add("15");
			labels.add("16");
			labels.add("17");
			labels.add("18");
			labels.add("19");
			labels.add("20");
			labels.add("21");
			labels.add("22");
			labels.add("23");

			ArrayList<ArrayList<Entry>> entries = new ArrayList<ArrayList<Entry>>();
			ArrayList dataSets = new ArrayList();

			for (int i = 0; i < array.length(); ++i) {

				JSONObject obj = null;
				try {
					obj = array.getJSONObject(i);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (obj != null) {

					String graphPoints = null;

					try {
						graphPoints = (String) obj.get("graphPoints");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					StringTokenizer tokenizer = new StringTokenizer(
							graphPoints, "|");

					int pointIndex = 0;

					entries.add(i, new ArrayList<Entry>());

					while (tokenizer.hasMoreTokens()) {

						String token = tokenizer.nextToken();
						Integer x_coOrd = new Integer(token.substring(0,
								token.indexOf(",")));
						Float y_coOrd = new Float(token.substring(
								token.indexOf(",") + 1).trim());
						entries.get(i).add(
								new Entry(new Integer(y_coOrd.intValue()),
										x_coOrd));
					}

					LineDataSet dataSet = null;

					if (i == 0) {
						dataSet = new LineDataSet(entries.get(i), "Resp");
						dataSet.setColor(Color.MAGENTA);
						((ImagicaActivity) mContext).respDataSet = dataSet;
					} else if (i == 1) {
						dataSet = new LineDataSet(entries.get(i), "BP");
						dataSet.setColor(Color.GREEN);
						((ImagicaActivity) mContext).bpDataSet = dataSet;
					} else if (i == 2) {
						dataSet = new LineDataSet(entries.get(i), "Temp");
						dataSet.setColor(Color.YELLOW);
						((ImagicaActivity) mContext).tempDataSet = dataSet;
					} else if (i == 3) {
						dataSet = new LineDataSet(entries.get(i), "Pulse");
						dataSet.setColor(Color.BLUE);
						((ImagicaActivity) mContext).pulseDataSet = dataSet;
					} else if (i == 4) {
						dataSet = new LineDataSet(entries.get(i), "PAP");
						dataSet.setColor(Color.DKGRAY);
						((ImagicaActivity) mContext).papDataSet = dataSet;
					} else if (i == 5) {
						dataSet = new LineDataSet(entries.get(i), "CVP");
						dataSet.setColor(Color.RED);
						((ImagicaActivity) mContext).cvpDataSet = dataSet;
					}

					dataSets.add(dataSet);

					// TextView error = (TextView)
					// findViewById(R.id.errorMessage);
				}
				// LineChart chart = (LineChart) ((ImagicaActivity) mContext)
				// .findViewById(R.id.chart);
				LineChart chart = (LineChart) ((ImagicaActivity) mContext).chart;
				LineData data = new LineData(labels, dataSets);
				chart.setData(data);
				chart.setDescription("A graph of clinical data against time of day");
				chart.setScaleY(0.5f);
				chart.invalidate();

				(((ImagicaActivity) mContext).tableLayout1)
						.setVisibility(View.VISIBLE);
				error.setText("");
			}
		}
	}
}
