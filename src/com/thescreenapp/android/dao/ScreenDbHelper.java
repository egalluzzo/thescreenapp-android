package com.thescreenapp.android.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ScreenDbHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "Screen.db";
	public static final int DATABASE_VERSION = 1;
	
	public ScreenDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
