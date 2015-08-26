package com.example.imagica.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.imagica.ImagicaActivity;
import com.example.imagica.R;
import com.example.imagica.R.id;
import com.example.imagica.R.layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.widget.EditText;
import android.os.AsyncTask;
import android.text.Spannable;
import android.text.SpannableString;
//import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class ExtendedClickableSpan extends ClickableSpan {

	private Context myContext;
	private static final String SERVICE_URL = "https://aneuron-hmsone.rhcloud.com/rest/history";
	private String json = "";
	StringBuffer errorMessages = new StringBuffer();
	Spanned myHistoryType = new SpannableString("");
	View textEntryView;
	String historyDataFromResponse;
	Spanned s = null;
	
	public ExtendedClickableSpan(Context context) {
		this.myContext = context;

	}

	@Override
	public void onClick(final View textView) {
		Log.e("error", "clickablespan onclick is called ..");
		final TextView tv = (TextView) textView;
		s = (SpannableString) tv.getText();
		final int start = s.getSpanStart(this);
		final int end = s.getSpanEnd(this);
		Log.e("error", "SpannableString: start: end" + s.toString() + ":"
				+ start + ":" + end);

		Log.e("error", "onClick [" + s.subSequence(start, end) + "]");

		ClickableSpan[] clickableSpans = s.getSpans(0, s.length() - 1,
				ClickableSpan.class);

		for (ClickableSpan cs : clickableSpans) {
			((SpannableString) s).setSpan(new BackgroundColorSpan(
					Color.TRANSPARENT), s.getSpanStart(cs), s.getSpanEnd(cs),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}

		((SpannableString) s).setSpan(new BackgroundColorSpan(Color.YELLOW),
				start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		myHistoryType = s;
		tv.setText(s);
		tv.setFocusable(true);
		tv.setFocusableInTouchMode(true);
		tv.setOnFocusChangeListener(focusChangeListener);

		// Get the layout inflater
		LayoutInflater inflater = ((Activity) myContext).getLayoutInflater();

		textEntryView = inflater.inflate(R.layout.history, null);

		Log.e("error", "loading history data ..");
		loadHistoryData();
		Log.e("error", "loaded history data ..");

//		final AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
//		Log.e("error", "launching alert ..");
//
//		builder.setTitle(s.toString());
//		// Get the layout inflater
//		LayoutInflater inflater = ((Activity) myContext).getLayoutInflater();
//
//		textEntryView = inflater.inflate(R.layout.history, null);
//		builder.setView(textEntryView);
//		EditText historyData = (EditText) textEntryView
//				.findViewById(R.id.historyData);
//
//		historyData.setText(historyDataFromResponse);
//
//		// Inflate and set the layout for the dialog
//		// Pass null as the parent view because its going in the dialog layout
//
//		// Add action buttons
//		builder.setPositiveButton("Save",
//				new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int id) {
//
//						final EditText history = (EditText) textEntryView
//								.findViewById(R.id.historyData);
//
//						Log.e("error", "history: " + history);
//
//						WebServiceTask wst = new WebServiceTask(
//								WebServiceTask.POST_TASK, myContext,
//								"Posting data...");
//
//						StringBuffer completeJsonString = new StringBuffer();
//
//						completeJsonString.append("{");
//
//						completeJsonString.append("\""
//								+ "patientId"
//								+ "\""
//								+ ":"
//								+ "\""
//								+ ((ImagicaActivity) myContext).getPatientVo()
//										.getPatientId() + "\",");
//
//						completeJsonString.append("\"" + "historyType" + "\""
//								+ ":" + "\"" + s + "\",");
//
//						completeJsonString.append("\"" + "historyData" + "\""
//								+ ":" + "\"" + history.getText().toString()
//								+ "\",");
//
//						completeJsonString.append("}");
//
//						completeJsonString.deleteCharAt(completeJsonString
//								.lastIndexOf(","));
//
//						json = completeJsonString.toString();
//
//						// the passed String is the URL we will POST to
//						wst.execute(new String[] { SERVICE_URL });
//
//					}
//				}).setNegativeButton("Cancel",
//				new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int id) {
//						dialog.cancel();
//					}
//				});
//		builder.create();
//		builder.show();

	}

	private void loadHistoryData() {

		((Activity) myContext).runOnUiThread(new Runnable() {
			@Override
			public void run() {

				WebServiceTask wst = new WebServiceTask(
						WebServiceTask.GET_TASK, myContext, "Getting data...");

				// myHistoryType.toString().replace(" ", "%20");

				// the passed String is the URL we will POST to
				try {
					wst.execute(new String[] { SERVICE_URL
							+ "?patientId="
							+ ((ImagicaActivity) myContext).getPatientVo()
									.getPatientId()
							+ "&historyType="
							+ URLEncoder.encode(myHistoryType.toString(),
									"UTF-8") });
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					Log.e("error", "Error while encoding .. ");
					e.printStackTrace();

				}

			}
		});
	}

	OnFocusChangeListener focusChangeListener = new OnFocusChangeListener() {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {

			Log.e("error", "Calling on focus change");

			SpannableString s = (SpannableString) ((TextView) v).getText();

			// TODO Auto-generated method stub
			s.setSpan(new BackgroundColorSpan(Color.TRANSPARENT), 0,
					s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		}
	};

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private class WebServiceTask extends AsyncTask<String, Integer, String> {

		public static final int POST_TASK = 1;
		public static final int GET_TASK = 2;

		private static final String TAG = "WebServiceTask";

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 3000;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 5000;

		private int taskType = GET_TASK;
		private Context mContext = null;
		private String processMessage = "Processing...";

		private ProgressDialog pDlg = null;

		public WebServiceTask(int taskType, Context mContext,
				String processMessage) {

			this.taskType = taskType;
			this.mContext = mContext;
			this.processMessage = processMessage;
		}

		private void showProgressDialog() {

			pDlg = new ProgressDialog(mContext);
			pDlg.setMessage(processMessage);
			pDlg.setProgressDrawable(mContext.getWallpaper());
			pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDlg.setCancelable(false);
			pDlg.show();

		}

		@Override
		protected void onPreExecute() {

			hideKeyboard();
			showProgressDialog();

		}

		protected String doInBackground(String... urls) {

			String url = urls[0];
			String result = "";

			HttpResponse response = null;

			response = doResponse(url);

			if (response == null) {
				return result;
			} else {

				try {

					result = inputStreamToString(response.getEntity()
							.getContent());

				} catch (IllegalStateException e) {
					Log.e(TAG, e.getLocalizedMessage(), e);
					errorMessages.append(e.getLocalizedMessage());

				} catch (IOException e) {
					Log.e(TAG, e.getLocalizedMessage(), e);
					errorMessages.append(e.getLocalizedMessage());
				}

			}

			return result;
		}

		@Override
		protected void onPostExecute(String response) {

			handleResponse(response);
			// pDlg.dismiss();

		}

		// Establish connection and socket (data retrieval) timeouts
		private HttpParams getHttpParams() {

			HttpParams htpp = new BasicHttpParams();

			HttpConnectionParams.setConnectionTimeout(htpp, CONN_TIMEOUT);
			HttpConnectionParams.setSoTimeout(htpp, SOCKET_TIMEOUT);

			return htpp;
		}

		private HttpResponse doResponse(String url) {

			// Use our connection and data timeouts as parameters for our
			// DefaultHttpClient
			HttpClient httpclient = new DefaultHttpClient(getHttpParams());

			HttpResponse response = null;

			try {
				switch (taskType) {

				case POST_TASK:
					HttpPost httppost = new HttpPost(url);

					Log.e("error", "json String: " + json);

					StringEntity se = new StringEntity(json);

					// Add parameters
					httppost.setEntity(se);
					// httppost.setHeader("Accept", "application/json");
					httppost.setHeader("Content-type", "application/json");
					response = httpclient.execute(httppost);
					break;
				case GET_TASK:
					Log.e("error", "webservice url: " + url);
					HttpGet httpget = new HttpGet(url);
					response = httpclient.execute(httpget);
					break;
				}
			} catch (Exception e) {

				errorMessages.append(e.getLocalizedMessage());
				// throw e;
				// Log.e(TAG, e.getLocalizedMessage(), e);
				// TextView result = (TextView)
				// findViewById(R.id.resultMessage);
				// result.setTextColor(Color.RED);
				// result.setText(e.getLocalizedMessage());
				// response = e.getLocalizedMessage();
			}

			return response;
		}

		private String inputStreamToString(InputStream is) {

			String line = "";
			StringBuilder total = new StringBuilder();

			// Wrap a BufferedReader around the InputStream
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));

			try {
				// Read response until the end
				while ((line = rd.readLine()) != null) {
					total.append(line);
				}
			} catch (IOException e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				errorMessages.append(e.getLocalizedMessage());
			}

			// Return full string
			return total.toString();
		}

		public void handleResponse(String response) {

			Log.e("Todo response", "Todo response: " + response);
			TextView result = (TextView) textEntryView
					.findViewById(R.id.resultMessage);
			TextView error = (TextView) textEntryView
					.findViewById(R.id.errorMessage);

			if (errorMessages != null && errorMessages.length() > 0) {
				error.setTextColor(Color.RED);
				error.setText(errorMessages.toString());
			}

			if (errorMessages != null && errorMessages.length() == 0
					&& (response == null || response.trim().equals(""))) {
				result.setTextColor(Color.GREEN);
				result.setText("The " + myHistoryType
						+ " is saved successfully");
				error.setText("");
			} else if (response.contains("Error report")
					&& response.contains("JBWEB000124")) {
				error.setText("");
				
				//Display alert for error
				
				final AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
				Log.e("error", "launching alert ..");

				builder.setTitle(s);
				builder.setView(textEntryView);
				error.setTextColor(Color.RED);
				error.setText("There was no data available for " + s);
				builder.setPositiveButton("Save",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {

								final EditText history = (EditText) textEntryView
										.findViewById(R.id.historyData);

								Log.e("error", "history: " + history);

								WebServiceTask wst = new WebServiceTask(
										WebServiceTask.POST_TASK, myContext,
										"Posting data...");

								StringBuffer completeJsonString = new StringBuffer();

								completeJsonString.append("{");

								completeJsonString.append("\""
										+ "patientId"
										+ "\""
										+ ":"
										+ "\""
										+ ((ImagicaActivity) myContext).getPatientVo()
												.getPatientId() + "\",");

								completeJsonString.append("\"" + "historyType" + "\""
										+ ":" + "\"" + s + "\",");

								completeJsonString.append("\"" + "historyData" + "\""
										+ ":" + "\"" + history.getText().toString()
										+ "\",");

								completeJsonString.append("}");

								completeJsonString.deleteCharAt(completeJsonString
										.lastIndexOf(","));

								json = completeJsonString.toString();

								// the passed String is the URL we will POST to
								wst.execute(new String[] { SERVICE_URL });

							}
						}).setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
				
				builder.create();
				builder.show();

				//Display alert
				
			} else {
				JSONObject obj = null;
				try {
					obj = new JSONObject(response);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (obj != null) {
					String history = null;
					try {
						historyDataFromResponse = (String) obj
								.get("historyData");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//Show alert dialog
					
					final AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
					Log.e("error", "launching alert ..");

					builder.setTitle(s.toString());
					builder.setView(textEntryView);
					EditText historyData = (EditText) textEntryView
							.findViewById(R.id.historyData);

					historyData.setText(historyDataFromResponse);

					// Inflate and set the layout for the dialog
					// Pass null as the parent view because its going in the dialog layout

					// Add action buttons
					builder.setPositiveButton("Save",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int id) {

									final EditText history = (EditText) textEntryView
											.findViewById(R.id.historyData);

									Log.e("error", "history: " + history);

									WebServiceTask wst = new WebServiceTask(
											WebServiceTask.POST_TASK, myContext,
											"Posting data...");

									StringBuffer completeJsonString = new StringBuffer();

									completeJsonString.append("{");

									completeJsonString.append("\""
											+ "patientId"
											+ "\""
											+ ":"
											+ "\""
											+ ((ImagicaActivity) myContext).getPatientVo()
													.getPatientId() + "\",");

									completeJsonString.append("\"" + "historyType" + "\""
											+ ":" + "\"" + s + "\",");

									completeJsonString.append("\"" + "historyData" + "\""
											+ ":" + "\"" + history.getText().toString()
											+ "\",");

									completeJsonString.append("}");

									completeJsonString.deleteCharAt(completeJsonString
											.lastIndexOf(","));

									json = completeJsonString.toString();

									// the passed String is the URL we will POST to
									wst.execute(new String[] { SERVICE_URL });

								}
							}).setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									dialog.cancel();
								}
							});
					builder.create();
					builder.show();
					errorMessages = new StringBuffer();
					
					//End - show alert dialog
					
				}
			}
			pDlg.dismiss();

			
			
		}

	}

	private void hideKeyboard() {

		InputMethodManager inputManager = (InputMethodManager) ((Activity) myContext)
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		inputManager.hideSoftInputFromWindow(((Activity) myContext)
				.getCurrentFocus().getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

}
