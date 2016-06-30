package com.squareup.timessquare;

import android.view.View;

import java.util.Date;

public interface CalendarCellDecorator {
  void decorate(CalendarCellView cellView, Date date);
  void decorate(View cellView, View content,View content2,Date date);
}
