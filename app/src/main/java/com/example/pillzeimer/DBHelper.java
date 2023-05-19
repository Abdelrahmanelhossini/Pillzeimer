package com.example.pillzeimer;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

	public static final String Dbname="Pillzeimer6.DB";

	public DBHelper( Context context) {
		super(context, "Pillzeimer6.DB", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table Users(username TEXT primary key, firstname TEXT, lastname TEXT, age NUMBER, password TEXT, gender TEXT, weight NUMBER, height NUMBER)");
		db.execSQL("create table UserMedicines(id INTEGER primary key, name TEXT, starttime TEXT, dose NUMBER, pills NUMBER, username TEXT)");


	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists Users ");
		db.execSQL("drop table if exists UserMedicines");
	}


	public long insertMedicine(String name, String starttime, String dose, String pills, String username){
		SQLiteDatabase DB = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("name", name);
		contentValues.put("starttime", starttime);
		contentValues.put("dose", dose);
		contentValues.put("pills", pills);
		contentValues.put("username", username);

		long result = DB.insert("UserMedicines", null, contentValues);
		return result;
	}



	public boolean updateMedicineData(String ID, String name, String starttime, String dose, String pills){
		SQLiteDatabase DB = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("name", name);
		contentValues.put("starttime", starttime);
		contentValues.put("dose", dose);
		contentValues.put("pills", pills);
		Cursor cursor = DB.rawQuery("select * from UserMedicines where id = ?", new String[]{ID});
		if (cursor.getCount() > 0) {
			long result = DB.update("UserMedicines", contentValues, "id = ?", new String[]{ID});
			if(result == -1){
				return false;
			} else {
				return true;
			}
		} else{
			return false;
		}

	}

	public boolean deleteMedicine(String ID){
		SQLiteDatabase DB = this.getWritableDatabase();
		Cursor cursor = DB.rawQuery("select * from UserMedicines where id = ?", new String[]{ID});
		if (cursor.getCount() > 0) {
			long result = DB.delete("UserMedicines", "id = ?", new String[]{ID});
			if(result == -1){
				return false;
			} else {
				return true;
			}
		} else{
			return false;
		}


	}


	public Cursor getUserMedicines(String username){
		SQLiteDatabase DB = this.getWritableDatabase();
		Cursor cursor = DB.rawQuery("select * from UserMedicines where username = ?", new String[]{username});
//		String[][] arr = new String[cursor.getCount()][5];
//		int i = 0;
//		while (cursor.moveToNext()){
//			arr[i][0] = cursor.getString(0);
//			arr[i][1] = cursor.getString(1);
//			arr[i][2] = cursor.getString(2);
//			arr[i][3] = cursor.getString(3);
//			arr[i][4] = cursor.getString(4);
//			i++;
//		}
		return cursor;
	}





	public boolean insertUser(String username, String firstname,String lastname,String age,String password){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("username",username);
		contentValues.put("firstname",firstname);
		contentValues.put("lastname",lastname);
		contentValues.put("age",age);
		contentValues.put ("password",password);

		long result = db.insert("Users","gender, weight, height" , contentValues);
		if ( result ==-1 )
			return false;
		else
			return true;


	}

	public boolean updateUserData(String username, String firstname,String lastname,int age, @Nullable String gender, @Nullable Float weight, @Nullable Integer height){
		SQLiteDatabase DB = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("firstname", firstname);
		contentValues.put("lastname", lastname);
		contentValues.put("age", age);
		if(gender != null)
			contentValues.put("gender", gender);
		if(weight != null)
			contentValues.put("weight", weight);
		if(height != null)
			contentValues.put("height", height);
		Cursor cursor = DB.rawQuery("select * from Users where username = ?", new String[]{username});
		if (cursor.getCount() > 0) {
			long result = DB.update("Users", contentValues, "username = ?", new String[]{username});
			if(result == -1){
				return false;
			} else {
				return true;
			}
		} else{
			return false;
		}

	}


	public boolean checkUsername (String username ){
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor =db.rawQuery("Select * from Users where username = ?", new String[] { username });
		if (cursor.getCount () > 0)
			return true;
		else
			return false;
	}
	public boolean checkUsernamePassword (String username,String password ){
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("Select * from Users where username = ? and password = ?",new String[]{username, password});
		if (cursor.getCount () > 0)
			return true;
		else
			return false;
	}


//	public Boolean updateUserData(String firstname, String lastname, String age)


	public String[] getUserData(String username){
		String[] arr = new String[6];
		SQLiteDatabase DB = this.getWritableDatabase();
		Cursor cursor = DB.rawQuery("select * from Users where username = ?", new String[]{username});
		while (cursor.moveToNext()){
			arr[0] = cursor.getString(1);
			arr[1] = cursor.getString(2);
			arr[2] = cursor.getString(3);
			arr[3] = cursor.getString(5);
			arr[4] = cursor.getString(6);
			arr[5] = cursor.getString(7);
		}
		return arr;
	}

}