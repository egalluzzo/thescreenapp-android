package com.thescreenapp.android.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ScreenDbHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "Screen.db";
	public static final int DATABASE_VERSION = 2;
	
	public ScreenDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(ScreenContract.Candidate.STATEMENT_CREATE_TABLE);
		db.execSQL(ScreenContract.Interview.STATEMENT_CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for (int version = oldVersion + 1; version <= newVersion; version++) {
			switch (version) {
				case 1:
					// Do nothing.  The first version was 1, so you can't upgrade to it.
					break;
				case 2:
					for (String statement : ScreenContract.Candidate.STATEMENTS_UPGRADE_TO_V2) {
						db.execSQL(statement);
					}
					for (String statement : ScreenContract.Interview.STATEMENTS_UPGRADE_TO_V2) {
						db.execSQL(statement);
					}
					break;
				default:
					throw new IllegalArgumentException("Cannot upgrade to unknown database version " + version);
			}
		}
	}
}
