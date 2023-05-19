package com.example.pillzeimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.IntentSanitizer;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalTime;
import java.util.Date;
import java.util.concurrent.atomic.LongAdder;

public class AddMedicine extends AppCompatActivity {

	static public String msg;
	static public String msg2;
	static public int id;
	static public int id2;
	String medicine_id;
	String medicine_name = null;
	String medicine_starttime;
	String medicine_dose;
	String medicine_pills;
	Long med_id;
	DBHelper DB;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_medicine);




		DB = new DBHelper(this);
		createNotificationChannel();
		createNotificationChannel1();
		Button submit = (Button) findViewById(R.id.submit);
		EditText name = (EditText) findViewById(R.id.name);
		EditText stock = (EditText) findViewById(R.id.stock);
		EditText dosage = (EditText) findViewById(R.id.dosage);
		EditText notes = (EditText) findViewById(R.id.notes);
		EditText time = (EditText) findViewById(R.id.starttime);
		ImageView prof = (ImageView) findViewById(R.id.prof);
		TextView head = (TextView) findViewById(R.id.newmed);
		Button delete = (Button) findViewById(R.id.delete);

		getIncomingIntent();

		if(medicine_name != null){
			name.setText(medicine_name);
			time.setText(medicine_starttime);
			dosage.setText(medicine_dose);
			stock.setText(medicine_pills);
			head.setText("Update Medication");
			submit.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(!name.getText().toString().equals("") && !stock.getText().toString().equals("") && !dosage.getText().toString().equals("") && !time.getText().toString().equals("")) {
						DB.updateMedicineData(medicine_id, name.getText().toString(), time.getText().toString(), dosage.getText().toString(), stock.getText().toString());
						Toast.makeText(AddMedicine.this, "Saved Successfully!", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(AddMedicine.this, HomePage.class);
						startActivity(intent);
					}else{
						Toast.makeText(AddMedicine.this, "Fill all the fields", Toast.LENGTH_SHORT).show();
					}
				}
			});
			delete.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					DB.deleteMedicine(medicine_id);
					Toast.makeText(AddMedicine.this, "Deleted Successfully!", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(AddMedicine.this, HomePage.class);
					startActivity(intent);
				}
			});
//			try {
//				Bundle bundle = getIntent().getExtras();
//				Boolean first = null;
//				if(bundle != null){
//					first = bundle.getBoolean("first");
//				}
//				if(first != true){
//					head.setText("Update Medication");
//				}
//			} catch (Exception e){
//				head.setText("Update Medication");
//			}
//
//			Cursor cursor = DB.getUserMedicines(SignUp.Username);

		} else{
			delete.setVisibility(View.GONE);

		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if(!name.getText().toString().equals("") && !stock.getText().toString().equals("") && !dosage.getText().toString().equals("") && !time.getText().toString().equals("")) {
					int dose = Integer.parseInt(dosage.getText().toString());

					med_id = DB.insertMedicine(name.getText().toString(), time.getText().toString(), dosage.getText().toString(), stock.getText().toString(), SignUp.Username);
					Toast.makeText(AddMedicine.this, med_id.toString(), Toast.LENGTH_SHORT).show();


					int repition = 24 / dose;
					long repinmilli = repition * 60 * 60 * 1000;
					msg = "Your " + name.getText().toString() + " time is now!";
					id = Math.toIntExact(med_id);
					Toast.makeText(AddMedicine.this, "Reminder set", Toast.LENGTH_SHORT).show();
					Intent i = new Intent(AddMedicine.this, ReminderBroadcast.class);
					PendingIntent pendingIntent = PendingIntent.getBroadcast(AddMedicine.this, 0, i, PendingIntent.FLAG_IMMUTABLE);
					AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


					long trigger = 0;
					if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
						LocalTime now = LocalTime.now();
						Integer timehour = (time.getText().toString().charAt(0) + time.getText().toString().charAt(1));
						trigger = (timehour - now.getHour()) * 60 * 60 * 1000;
					}

					alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 10000, repinmilli, pendingIntent);

					Intent inte = new Intent(AddMedicine.this, AlertBroadcast.class);
					PendingIntent pendingInte = PendingIntent.getBroadcast(AddMedicine.this, 0, inte, PendingIntent.FLAG_IMMUTABLE);
					AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
					id2 = (int) (med_id + 200);
					msg2 = name.getText().toString() + " almost ran out!...";
					long warninmilli = ((Integer.parseInt(stock.getText().toString()) / dose) * 24 * 60 * 60 * 1000) - (24 * 60 * 60 * 1000);
					am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 20000, pendingInte);

					Intent intent = new Intent(AddMedicine.this, HomePage.class);
					startActivity(intent);


				} else{
//
					Toast.makeText(AddMedicine.this, "Fill all required fields!", Toast.LENGTH_SHORT).show();
				}
			}
		});
		}

		prof.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(AddMedicine.this, Profile.class);
				startActivity(i);
			}
		});


	}

	private void getIncomingIntent(){
		if(getIntent().hasExtra("medicine_name")){
			medicine_id = getIntent().getStringExtra("medicine_id");
			medicine_name = getIntent().getStringExtra("medicine_name");
			medicine_starttime = getIntent().getStringExtra("medicine_starttime");
			medicine_dose = getIntent().getStringExtra("medicine_dose");
			medicine_pills = getIntent().getStringExtra("medicine_pills");
		}
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