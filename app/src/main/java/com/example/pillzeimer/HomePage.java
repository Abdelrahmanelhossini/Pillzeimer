package com.example.pillzeimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.security.PublicKey;
import java.util.ArrayList;

public class HomePage extends AppCompatActivity {
	LinearLayout add;
	DBHelper DB;
	static public Integer MedicineID;
	ArrayList<String> medicine_id, medicine_name, medicine_starttime, medicine_dose, medicine_pills;
	CustomAdapter customAdapter;
	RecyclerView recyclerView;
	@SuppressLint("MissingInflatedId")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);
		add = (LinearLayout) findViewById(R.id.add);
		ImageView prof = (ImageView) findViewById(R.id.prof);
//		ImageView edit1 = (ImageView) findViewById(R.id.edit1);
//		ImageView edit2 = (ImageView) findViewById(R.id.edit2);
//		ImageView edit3 = (ImageView) findViewById(R.id.edit3);
		DB = new DBHelper(this);
		try{
			recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
			medicine_id = new ArrayList<>();
			medicine_name = new ArrayList<>();
			medicine_starttime = new ArrayList<>();
			medicine_dose = new ArrayList<>();
			medicine_pills = new ArrayList<>();
			storeMedicinesInArray();
			customAdapter = new CustomAdapter(HomePage.this, medicine_id, medicine_name, medicine_starttime, medicine_dose, medicine_pills);
			recyclerView.setAdapter(customAdapter);
			recyclerView.setLayoutManager(new LinearLayoutManager(HomePage.this));
		} catch (Exception e){

		}








		add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(HomePage.this, AddMedicine.class);
				i.putExtra("first", true);
				startActivity(i);
			}
		});
//		edit1.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent i = new Intent(HomePage.this, AddMedicine.class);
//				startActivity(i);
//			}
//		});
//		edit2.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent i = new Intent(HomePage.this, AddMedicine.class);
//				startActivity(i);
//			}
//		});
//		edit3.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent i = new Intent(HomePage.this, AddMedicine.class);
//				startActivity(i);
//			}
//		});
		prof.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(HomePage.this, Profile.class);
				startActivity(i);
			}
		});
	}
	void storeMedicinesInArray(){
		Cursor cursor = DB.getUserMedicines(SignUp.Username);
		if(cursor.getCount() == 0){
			Toast.makeText(HomePage.this, "No Medicines", Toast.LENGTH_SHORT).show();
		} else{
			while (cursor.moveToNext()){
				medicine_id.add(cursor.getString(0));
				medicine_name.add(cursor.getString(1));
				medicine_starttime.add(cursor.getString(2));
				medicine_dose.add(cursor.getString(3));
				medicine_pills.add(cursor.getString(4));
			}
		}
	}
}