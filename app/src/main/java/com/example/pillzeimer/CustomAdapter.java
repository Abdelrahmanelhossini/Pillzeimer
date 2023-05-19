package com.example.pillzeimer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

	Context context;
	ArrayList<String> medicine_id, medicine_name, medicine_starttime, medicine_dose, medicine_pills;

	CustomAdapter(Context context, ArrayList medicine_id, ArrayList medicine_name, ArrayList medicine_starttime, ArrayList medicine_dose, ArrayList medicine_pills){
		this.context = context;
		this.medicine_id = medicine_id;
		this.medicine_name = medicine_name;
		this.medicine_starttime = medicine_starttime;
		this.medicine_dose = medicine_dose;
		this.medicine_pills = medicine_pills;
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.medicines, parent, false);
		return new MyViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

		holder.medicine_name_txt.setText(String.valueOf(medicine_name.get(position)));
		holder.medicine_starttime_txt.setText(String.valueOf(medicine_starttime.get(position)));
		holder.medicine_dose_txt.setText(String.valueOf(medicine_dose.get(position)) + " pills/day");
		holder.medicine_pills_txt.setText("Remaining: " + String.valueOf(medicine_pills.get(position)));
		holder.medicine_id_relative.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(context, AddMedicine.class);
				i.putExtra("medicine_id", String.valueOf(medicine_id.get(position)));
				i.putExtra("medicine_name", String.valueOf(medicine_name.get(position)));
				i.putExtra("medicine_starttime", String.valueOf(medicine_starttime.get(position)));
				i.putExtra("medicine_dose", String.valueOf(medicine_dose.get(position)));
				i.putExtra("medicine_pills", String.valueOf(medicine_pills.get(position)));
				context.startActivity(i);
			}
		});

	}

	@Override
	public int getItemCount() {
		return medicine_id.size();
	}

	public class MyViewHolder extends RecyclerView.ViewHolder {

		TextView medicine_name_txt, medicine_starttime_txt, medicine_dose_txt, medicine_pills_txt;
		ImageView medicine_id_edit;
		RelativeLayout medicine_id_relative;

		public MyViewHolder(@NonNull View itemView) {
			super(itemView);

			medicine_name_txt = itemView.findViewById(R.id.medicine_name_txt);
			medicine_starttime_txt = itemView.findViewById(R.id.medicine_starttime_txt);
			medicine_dose_txt = itemView.findViewById(R.id.medicine_dose_txt);
			medicine_pills_txt = itemView.findViewById(R.id.medicine_pills_txt);
			medicine_id_edit = itemView.findViewById(R.id.medicine_id_edit);
			medicine_id_relative = itemView.findViewById(R.id.medicine_id_relative);

		}
	}
}
