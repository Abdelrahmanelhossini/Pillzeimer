package com.example.pillzeimer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
	Handler splash = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		createNotificationChannel();
		createNotificationChannel1();
		splash.postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent splash_screen = new Intent(MainActivity.this, SignUp.class);
				startActivity(splash_screen);
				finish();
			}
		}, 1700);
	}

	private void createNotificationChannel(){

		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
			CharSequence name = "MedicineReminderChannel";
			String description = "Channle for reminding dosages";
			int importance = NotificationManager.IMPORTANCE_HIGH;
			NotificationChannel channel = new NotificationChannel("Reminder", name, importance);
			channel.setDescription(description);

			NotificationManager notificationManager = getSystemService(NotificationManager.class);
			notificationManager.createNotificationChannel(channel);
		}

	}
	private void createNotificationChannel1(){

		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
			CharSequence name = "MedicineAlertChannel";
			String description = "Channle for reminding dosages";
			int importance = NotificationManager.IMPORTANCE_HIGH;
			NotificationChannel channel = new NotificationChannel("Alert", name, importance);
			channel.setDescription(description);

			NotificationManager notificationManager = getSystemService(NotificationManager.class);
			notificationManager.createNotificationChannel(channel);
		}

	}
}