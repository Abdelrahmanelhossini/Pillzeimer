package com.example.pillzeimer;


import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlertBroadcast extends BroadcastReceiver{
	@Override
	public void onReceive(Context context, Intent intent){
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Alert")
				.setSmallIcon(R.drawable.ic_launcher_foreground)
				.setContentTitle("Warning")
				.setContentText(AddMedicine.msg2)
				.setStyle(new NotificationCompat.BigTextStyle()
						.bigText("Go to see our contracted pharmacies in the app!"))
				.setPriority(NotificationCompat.PRIORITY_MAX);

		NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

		notificationManager.notify(AddMedicine.id2, builder.build());
	}
}
