package com.example.pillzeimer;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
	Button signin;
	Button register;
	EditText pass;
	EditText repass;
	DBHelper DB;
	static public String Username;
	static public String FirstName;
	static public String LastName;
	static public String Age;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		repass = (EditText) findViewById(R.id.repass);
		signin = (Button) findViewById(R.id.logintxt);
		register = (Button) findViewById(R.id.signbtn);
		DB = new DBHelper(this);


		EditText fname = findViewById(R.id.fname);
		EditText lname = findViewById(R.id.lname);
		EditText user = findViewById(R.id.username);
		EditText age1 = findViewById(R.id.age);
		pass = (EditText) findViewById(R.id.pass);



		register.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("ResourceAsColor")
			@Override
			public void onClick(View v) {
				String username = user.getText().toString();
				String firstname = fname.getText().toString();
				String lastname = lname.getText().toString();
				String age = age1.getText().toString();
				String password = pass.getText().toString();
				String repassword = repass.getText().toString();

				if(username.equals("") || firstname.equals("") || lastname.equals("") || age1.getText().toString().equals("") || password.equals("") || repassword.equals("")){
					Toast.makeText(SignUp.this, "Please enter all the fields!", Toast.LENGTH_SHORT).show();
				} else{
					if(password.equals(repassword)){
						Boolean checkuser = DB.checkUsername(username);
						if(checkuser == false){
							Boolean insert = DB.insertUser(username, firstname, lastname, age, password);
							if(insert){
								FirstName = firstname;
								LastName = lastname;
								Age = age;
								Username = username;
								Toast.makeText(SignUp.this, "Registered successfully", Toast.LENGTH_SHORT).show();
								Intent i = new Intent(SignUp.this, Profile.class);
								i.putExtra("first", true);
								startActivity(i);
							}
							else {
								Toast.makeText(SignUp.this, "WH", Toast.LENGTH_SHORT).show();
							}
						} else{
							Toast.makeText(SignUp.this, "User already exists!", Toast.LENGTH_SHORT).show();
						}
					} else{
						Toast.makeText(SignUp.this, "Password not matching!", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		signin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(SignUp.this, SignIn.class);
				startActivity(i);
			}
		});
	}
}