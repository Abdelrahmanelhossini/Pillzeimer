package com.example.pillzeimer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.opengl.GLDebugHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignIn extends AppCompatActivity {

	static public String Username;
	Button signbtn;
	DBHelper DB;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		signbtn = (Button) findViewById(R.id.signbtn);
		EditText usernamefield = findViewById(R.id.username);
		EditText passwordfield = findViewById(R.id.pass);
		DB = new DBHelper(this);


		signbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String username = usernamefield.getText().toString();
				String password = passwordfield.getText().toString();

				if(username.equals("") || password.equals("")){
					Toast.makeText(SignIn.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
				}else{
					Boolean checkUser = DB.checkUsernamePassword(username, password);


					if(checkUser){
						Toast.makeText(SignIn.this, "Sign in successfully!", Toast.LENGTH_SHORT).show();
						SignUp.Username = username;
						Intent i = new Intent(SignIn.this, HomePage.class);
						startActivity(i);
					} else{
						Toast.makeText(SignIn.this, "Invalid credentials or user doesn't exist", Toast.LENGTH_LONG).show();
					}
				}
			}
		});
	}
}