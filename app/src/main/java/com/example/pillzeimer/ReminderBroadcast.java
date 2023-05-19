package com.example.pillzeimer;


import android.app.NotificationChannel;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcast extends BroadcastReceiver{
	@Override
	public void onReceive(Context context, Intent intent){
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Reminder")
				.setSmallIcon(R.drawable.ic_launcher_foreground)
				.setContentTitle("Reminder")
				.setContentText(AddMedicine.msg)
				.setPriority(NotificationCompat.PRIORITY_MAX);

		NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

		notificationManager.notify(AddMedicine.id, builder.build());
	}

}
