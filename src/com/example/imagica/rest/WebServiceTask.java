package com.example.imagica.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

public abstract class WebServiceTask extends AsyncTask<String, Integer, String> {

	private String json = "";

	protected StringBuffer errorMessages = new StringBuffer();

	public static final int POST_TASK = 1;
	public static final int GET_TASK = 2;

	private static final String TAG = "WebServiceTask";

	// connection timeout, in milliseconds (waiting to connect)
	private static final int CONN_TIMEOUT = 3000;

	// socket timeout, in milliseconds (waiting for data)
	private static final int SOCKET_TIMEOUT = 5000;

	private int taskType = GET_TASK;
	protected Context mContext = null;
	private String processMessage = "Processing...";

	private ProgressDialog pDlg = null;

	public WebServiceTask(int taskType, Context mContext, String processMessage) {

		this.taskType = taskType;
		this.mContext = mContext;
		this.processMessage = processMessage;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	@Override
	protected void onPreExecute() {

		hideKeyboard();
		showProgressDialog();

	}

	private void showProgressDialog() {

		pDlg = new ProgressDialog(mContext);
		pDlg.setMessage(processMessage);
		pDlg.setProgressDrawable(mContext.getWallpaper());
		pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pDlg.setCancelable(false);
		pDlg.show();

	}

	private void hideKeyboard() {

		InputMethodManager inputManager = (InputMethodManager) ((Activity) mContext)
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		if (((Activity) mContext).getCurrentFocus() != null) {
			inputManager.hideSoftInputFromWindow(((Activity) mContext)
					.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	@Override
	protected String doInBackground(String... urls) {
		// TODO Auto-generated method stub
		String url = urls[0];
		String result = "";

		HttpResponse response = null;

		response = doResponse(url);

		if (response == null) {
			return result;
		} else {

			try {

				result = inputStreamToString(response.getEntity().getContent());

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
		pDlg.dismiss();

	}

	protected abstract void handleResponse(String response);

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

}
