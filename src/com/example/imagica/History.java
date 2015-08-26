package com.example.imagica;

import com.example.imagica.view.ExtendedClickableSpan;

import android.content.Context;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TextView.BufferType;

public class History {

	public static void renderHistory(Context context) {

		TableLayout table = null;
		// ((ViewGroup) ((ImagicaActivity) context).nextImage.getParent())
		// .removeView(((ImagicaActivity) context).nextImage);
		if (context instanceof ImagicaActivity) {
			((ImagicaActivity) context).setTitle("History");
			((ViewGroup) ((ImagicaActivity) context).chart.getParent())
					.removeView(((ImagicaActivity) context).chart);

			((ViewGroup) ((ImagicaActivity) context).tableLayout1.getParent())
					.removeView(((ImagicaActivity) context).tableLayout1);

			((ViewGroup) ((ImagicaActivity) context).aDate.getParent())
					.removeView(((ImagicaActivity) context).aDate);

			((ViewGroup) ((ImagicaActivity) context).calendarButton.getParent())
					.removeView(((ImagicaActivity) context).calendarButton);

			// get a reference for the TableLayout
			table = (TableLayout) ((ImagicaActivity) context)
					.findViewById(R.id.TableLayout01);

		} else if (context instanceof ChartEntryActivity) {
			((ChartEntryActivity) context).setTitle("History");
			((ViewGroup) ((ChartEntryActivity) context).aDate.getParent())
					.removeView(((ChartEntryActivity) context).aDate);

			((ViewGroup) ((ChartEntryActivity) context).calendarButton
					.getParent())
					.removeView(((ChartEntryActivity) context).calendarButton);

			((ViewGroup) ((ChartEntryActivity) context).aTime.getParent())
					.removeView(((ChartEntryActivity) context).aTime);

			((ViewGroup) ((ChartEntryActivity) context).tableLayout1
					.getParent())
					.removeView(((ChartEntryActivity) context).tableLayout1);

			((ViewGroup) ((ChartEntryActivity) context).save.getParent())
					.removeView(((ChartEntryActivity) context).save);

			// get a reference for the TableLayout
			table = (TableLayout) ((ChartEntryActivity) context)
					.findViewById(R.id.TableLayout01);

		} else if (context instanceof DailyNotesActivity) {
			((DailyNotesActivity) context).setTitle("History");

			((ViewGroup) ((DailyNotesActivity) context).notesLayout.getParent())
					.removeView(((DailyNotesActivity) context).notesLayout);

			// get a reference for the TableLayout
			table = (TableLayout) ((DailyNotesActivity) context)
					.findViewById(R.id.TableLayout01);

		}

		// create a new TableRow
		TableRow row = new TableRow(context);
		row.setBackgroundResource(R.drawable.table_border);

		// ViewGroup imagicaLayout = (LinearLayout) ((ImagicaActivity) context)
		// .findViewById(R.id.imagicaLayout);
		ExtendedClickableSpan allergySpan = new ExtendedClickableSpan(context);
		TextView myTextView = new TextView(context);
		String myString = "- Known Allergies";
		int i1 = 0;
		int i2 = myString.length() - 1;
		myTextView.setMovementMethod(LinkMovementMethod.getInstance());
		myTextView.setText(myString, BufferType.SPANNABLE);
		myTextView.setTextSize(16);
		myTextView.setFocusable(true);
		myTextView.setFocusableInTouchMode(true);

		Spannable mySpannable = (Spannable) myTextView.getText();
		mySpannable.setSpan(allergySpan, i1, i2 + 1,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		row.addView(myTextView);

		// create a new TableRow
		TableRow row1 = new TableRow(context);
		row1.setBackgroundResource(R.drawable.table_border);

		ExtendedClickableSpan complaintsSpan = new ExtendedClickableSpan(
				context);
		TextView complaintsTextView = new TextView(context);
		String complaintsString = "- Chief Complaints";
		int i1_complaint = 0;
		int i2_complaint = complaintsString.length() - 1;
		complaintsTextView.setMovementMethod(LinkMovementMethod.getInstance());
		complaintsTextView.setText(complaintsString, BufferType.SPANNABLE);
		complaintsTextView.setTextSize(16);
		complaintsTextView.setFocusable(true);
		complaintsTextView.setFocusableInTouchMode(true);

		Spannable mySpannableComplaints = (Spannable) complaintsTextView
				.getText();
		mySpannableComplaints.setSpan(complaintsSpan, i1_complaint,
				i2_complaint + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		row1.addView(complaintsTextView);

		// create a new TableRow
		TableRow row2 = new TableRow(context);
		row2.setBackgroundResource(R.drawable.table_border);

		ExtendedClickableSpan detailsSpan = new ExtendedClickableSpan(context);
		TextView detailsTextView = new TextView(context);
		String detailsString = "- Details";
		int i1_details = 0;
		int i2_details = detailsString.length() - 1;
		detailsTextView.setMovementMethod(LinkMovementMethod.getInstance());
		detailsTextView.setText(detailsString, BufferType.SPANNABLE);
		detailsTextView.setTextSize(16);
		detailsTextView.setFocusable(true);
		detailsTextView.setFocusableInTouchMode(true);

		Spannable mySpannableDetails = (Spannable) detailsTextView.getText();
		mySpannableDetails.setSpan(detailsSpan, i1_details, i2_details + 1,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		row2.addView(detailsTextView);

		// create a new TableRow
		TableRow row3 = new TableRow(context);
		row3.setBackgroundResource(R.drawable.table_border);

		ExtendedClickableSpan pastHistorySpan = new ExtendedClickableSpan(
				context);
		TextView pastHistoryTextView = new TextView(context);
		String pastHistoryString = "- Past History";
		int i1_pastHistory = 0;
		int i2_pastHistory = pastHistoryString.length() - 1;
		pastHistoryTextView.setMovementMethod(LinkMovementMethod.getInstance());
		pastHistoryTextView.setText(pastHistoryString, BufferType.SPANNABLE);
		pastHistoryTextView.setTextSize(16);
		pastHistoryTextView.setFocusable(true);
		pastHistoryTextView.setFocusableInTouchMode(true);

		Spannable mySpannablePastHistory = (Spannable) pastHistoryTextView
				.getText();
		mySpannablePastHistory.setSpan(pastHistorySpan, i1_pastHistory,
				i2_pastHistory + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		row3.addView(pastHistoryTextView);

		// create a new TableRow
		TableRow row4 = new TableRow(context);
		row4.setBackgroundResource(R.drawable.table_border);

		ExtendedClickableSpan personalHistorySpan = new ExtendedClickableSpan(
				context);
		TextView personalHistoryTextView = new TextView(context);
		String personalHistoryString = "- Personal History";
		int i1_personalHistory = 0;
		int i2_personalHistory = personalHistoryString.length() - 1;
		personalHistoryTextView.setMovementMethod(LinkMovementMethod
				.getInstance());
		personalHistoryTextView.setText(personalHistoryString,
				BufferType.SPANNABLE);
		personalHistoryTextView.setTextSize(16);
		personalHistoryTextView.setFocusable(true);
		personalHistoryTextView.setFocusableInTouchMode(true);

		Spannable mySpannablePersonalHistory = (Spannable) personalHistoryTextView
				.getText();
		mySpannablePersonalHistory.setSpan(personalHistorySpan,
				i1_personalHistory, i2_personalHistory + 1,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		row4.addView(personalHistoryTextView);

		// create a new TableRow
		TableRow row5 = new TableRow(context);
		row5.setBackgroundResource(R.drawable.table_border);

		ExtendedClickableSpan familyHistorySpan = new ExtendedClickableSpan(
				context);
		TextView familyHistoryTextView = new TextView(context);
		String familyHistoryString = "- Family History";
		int i1_familyHistory = 0;
		int i2_familyHistory = familyHistoryString.length() - 1;
		familyHistoryTextView.setMovementMethod(LinkMovementMethod
				.getInstance());
		familyHistoryTextView
				.setText(familyHistoryString, BufferType.SPANNABLE);
		familyHistoryTextView.setTextSize(16);
		familyHistoryTextView.setFocusable(true);
		familyHistoryTextView.setFocusableInTouchMode(true);

		Spannable mySpannableFamilyHistory = (Spannable) familyHistoryTextView
				.getText();
		mySpannableFamilyHistory.setSpan(familyHistorySpan, i1_familyHistory,
				i2_familyHistory + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		row5.addView(familyHistoryTextView);

		// add the TableRow to the TableLayout
		table.addView(row, new TableLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		table.addView(row1, new TableLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		table.addView(row2, new TableLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		table.addView(row3, new TableLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		table.addView(row4, new TableLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		table.addView(row5, new TableLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

	}

}
