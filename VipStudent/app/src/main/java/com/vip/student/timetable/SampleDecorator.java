package com.vip.student.timetable;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarCellView;

import java.util.Date;

public class SampleDecorator implements CalendarCellDecorator {
  @Override
  public void decorate(CalendarCellView cellView, Date date) {
//    ViewGroup parent = (ViewGroup) cellView.getParent();
//    parent.removeAllViews();
//    Button button = new Button(parent.getContext());
//    button.setText("哈哈,"+cellView.getText());
//    parent.addView(button);
//    String dateString = Integer.toString(date.getDate());
//    SpannableString string = new SpannableString(dateString + "\ntitle");
//    string.setSpan(new RelativeSizeSpan(0.5f), 0, dateString.length(),
//            Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//    cellView.setText(string);
  }

  @Override
  public void decorate(View cellView, View content, View content2, Date date) {
    Toast.makeText(cellView.getContext(), ((TextView) content).getText() + "", 0).show();
  }
}
