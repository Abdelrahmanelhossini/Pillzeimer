package com.example.pillzeimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Profile extends AppCompatActivity {
	DBHelper DB;
	private RadioGroup radioGroup;
	static public Boolean first = null;
	Button saveBtn,clear;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		Button save = (Button) findViewById(R.id.save);
		Button skip = (Button) findViewById(R.id.skip);
		EditText agefield = (EditText) findViewById(R.id.age);
		EditText firstnamefield = (EditText) findViewById(R.id.fname);
		EditText lastnamefield = (EditText) findViewById(R.id.lname);
		EditText weightfield = (EditText) findViewById(R.id.weight);
		EditText heightfield = (EditText) findViewById(R.id.height);
		RadioButton m = (RadioButton) findViewById(R.id.male);
		RadioButton f = (RadioButton) findViewById(R.id.female);
		String firstname;
		String lastname;
		int age;
		String gender;
		Float weight;
		Integer height;
		DB = new DBHelper(this);


		radioGroup=(RadioGroup) findViewById(R.id.groupradio);
		// Uncheck or reset the radio buttons initially
		radioGroup.clearCheck();

		String[] arr = DB.getUserData(SignUp.Username);
		firstnamefield.setText(arr[0]);
		lastnamefield.setText(arr[1]);
		agefield.setText(arr[2]);
		if(arr[4] != null)
			weightfield.setText(arr[4]);
		if(arr[5] != null)
			heightfield.setText(arr[5]);


		try {
			Bundle bundle = getIntent().getExtras();
			first = null;
			if(bundle != null){
				first = bundle.getBoolean("first");
			}
			if(first != true){
				skip.setEnabled(false);
				skip.setVisibility(View.GONE);
				save.setGravity(Gravity.CENTER);
				radioGroup.setVisibility(View.GONE);



			}
		} catch (Exception e){
			skip.setEnabled(false);
			skip.setVisibility(View.GONE);
			save.setGravity(Gravity.CENTER);
			radioGroup.setVisibility(View.GONE);
		}

		// Add the Listener to the RadioGroup
		radioGroup.setOnCheckedChangeListener(
				new RadioGroup
						.OnCheckedChangeListener() {
					@Override

					// The flow will come here when
					// any of the radio buttons in the radioGroup
					// has been clicked

					// Check which radio button has been clicked
					public void onCheckedChanged(RadioGroup group,
												 int checkedId)
					{

						// Get the selected Radio Button
						RadioButton
								radioButton
								= (RadioButton)group
								.findViewById(checkedId);
					}
				});

		// Add the Listener to the Submit Button
		save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v)
			{
				RadioButton radioButton = null;
				// When submit button is clicked,
				// Ge the Radio Button which is set
				// If no Radio Button is set, -1 will be returned
				int selectedId = radioGroup.getCheckedRadioButtonId();
//                if (selectedId == -1) {
//                    Toast.makeText(MainActivity.this,
//                                    "No answer has been selected",
//                                    Toast.LENGTH_SHORT)
//                            .show();
//                }
				if(selectedId != -1) {

					radioButton
							= (RadioButton)radioGroup
							.findViewById(selectedId);

					// Now display the value of selected item
					// by the Toast message
//                    Toast.makeText(MainActivity.this,
//                                    radioButton.getText(),
//                                    Toast.LENGTH_SHORT)
//                            .show();
				}
				if(firstnamefield.getText().toString().equals("") || lastnamefield.getText().toString().equals("") || agefield.getText().toString().equals("")){

					Toast.makeText(Profile.this, "Fill all required fields", Toast.LENGTH_SHORT).show();
				} else{
					Integer age = Integer.parseInt(agefield.getText().toString());
					String gender = null;
					try{
						gender = radioButton.getText().toString();
					}catch (Exception e){
//						Toast.makeText(Profile.this, "gender problem", Toast.LENGTH_SHORT).show();
					}
					Integer height;
					Float weight;
					if(weightfield.getText().toString().equals(""))
						weight = null;
					else
						weight = Float.parseFloat(weightfield.getText().toString());
					if(heightfield.getText().toString().equals(""))
						height = null;
					else
						height = Integer.parseInt(heightfield.getText().toString());

					DB.updateUserData(SignUp.Username, firstnamefield.getText().toString(), lastnamefield.getText().toString(), age, gender, weight, height);
					Toast.makeText(Profile.this, "Saved successfully!", Toast.LENGTH_SHORT).show();
					Intent i = new Intent(Profile.this, HomePage.class);
					startActivity(i);
				}
			}
		});
		skip.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Profile.this, HomePage.class);
				startActivity(i);
			}
		});

//         Add the Listener to the Submit Button
	}
}

