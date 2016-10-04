package com.example.user.p1181_customwidget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;



public class ConfigActivity extends Activity {

    int widgetID = AppWidgetManager.INVALID_APPWIDGET_ID;
    Intent resultvalue;

    final String LOG_TAG = "MyLog";

    public final static String WIDGET_PREF = "widget_pref";
    public final static String WIDGET_TEXT = "widget_text_";
    public final static String WIDGET_COLOR = "widget_color";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(LOG_TAG,"onCreate config");

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras!= null) {
            widgetID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        if(widgetID == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        resultvalue = new Intent();
        resultvalue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,widgetID);
        setResult(RESULT_CANCELED,resultvalue);

        setContentView(R.layout.activity_config);
    }

    public void onClick(View view) {
        int selRBColor = ((RadioGroup)findViewById(R.id.rgColor))
                .getCheckedRadioButtonId();

        int color = Color.RED;
        switch(selRBColor) {
            case R.id.radioRed:
                color = Color.parseColor("#66ff0000");
                break;
            case R.id.radioGreen:
                color = Color.parseColor("#6600ff00");
                break;
            case R.id.radioBlue:
                color = Color.parseColor("#660000ff");
                break;
        }

        EditText etText = (EditText)findViewById(R.id.etText);

        SharedPreferences sp = getSharedPreferences(WIDGET_PREF,MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(WIDGET_TEXT + widgetID, etText.getText()
        .toString());
        editor.putInt(WIDGET_COLOR + widgetID, color);
        editor.commit();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        MyWidget.updateWidget(this,appWidgetManager,sp,widgetID);
        setResult(RESULT_OK,resultvalue);

        Log.d(LOG_TAG,"finish config " + widgetID);
        finish();


    }
}
