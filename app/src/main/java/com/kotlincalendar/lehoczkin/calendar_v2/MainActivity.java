package com.kotlincalendar.lehoczkin.calendar_v2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlarmManager manager = (AlarmManager)MainActivity.this.getSystemService(MainActivity.this.ALARM_SERVICE);
        Intent myIntent;
        String today_name = getNamesFromFileToDisplay();

        myIntent = new Intent(MainActivity.this,NotificationReceiver.class);
        myIntent.putExtra("name",today_name);
        sendBroadcast(myIntent);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 19);

        pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,myIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);

        TextView actually_name = (TextView) findViewById(R.id.names);
        actually_name.setText(today_name);


    }



    private String getNamesFromFileToDisplay() {

        BufferedReader reader = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        String currentdate = dateFormat.format(new Date());
        String date = "";
        String names = "";
        String actual_names = "";

        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("name_days.txt")));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                String[] line = mLine.split(";");
                if(line.length > 1) {
                    date = line[0];
                    names = line[1];
                }
                if(date.equals(currentdate)){
                    actual_names = names;
                }
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return  actual_names;
    }
}
