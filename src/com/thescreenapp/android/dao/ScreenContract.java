package com.thescreenapp.android.dao;

/**
 * A contract for the Screen tables.
 * 
 * FIXME: This is so primitive.  All this should be determined based on the
 *        model classes and possibly annotations (ORM).
 */
public class ScreenContract {
	private ScreenContract() {}
	
	public static abstract class Candidate implements ScreenBaseColumns {
        public static final String TABLE_NAME = "candidate";
        
        public static final String COLUMN_NAME_FIRST_NAME = "first_name";
        public static final String COLUMN_NAME_LAST_NAME = "first_name";
        public static final String COLUMN_NAME_PHONE_NUMBER = "phone_number";
        public static final String COLUMN_NAME_RATING = "rating";
        
        public static final String STATEMENT_CREATE_TABLE =
        		"CREATE TABLE " + TABLE_NAME + " ("
        		+ CREATE_SCREEN_BASE_COLUMNS_SQL + ", "
        		+ COLUMN_NAME_FIRST_NAME + " TEXT, "
        		+ COLUMN_NAME_LAST_NAME + " TEXT, "
        		+ COLUMN_NAME_PHONE_NUMBER + " TEXT, "
        		+ COLUMN_NAME_RATING + " INTEGER"
        		+ ")";
        
        public static final String STATEMENT_DELETE_TABLE =
        		"DROP TABLE IF EXISTS " + TABLE_NAME;
	}
	
	public static abstract class Interview implements ScreenBaseColumns {
        public static final String TABLE_NAME = "interview";
        
        public static final String COLUMN_NAME_CANDIDATE_ID = "candidate_id";
        public static final String COLUMN_NAME_LOCATION = "location";
        public static final String COLUMN_NAME_INTERVIEW_DATE = "interview_date";
        
        public static final String[] ALL_COLUMN_NAMES = new String[] {
        	_ID,
        	COLUMN_NAME_UUID,
        	COLUMN_NAME_CREATED,
        	COLUMN_NAME_UPDATED,
        	COLUMN_NAME_CANDIDATE_ID,
        	COLUMN_NAME_LOCATION,
        	COLUMN_NAME_INTERVIEW_DATE
        };
        
        public static final String STATEMENT_CREATE_TABLE =
        		"CREATE TABLE " + TABLE_NAME + " ("
        		+ CREATE_SCREEN_BASE_COLUMNS_SQL + ", "
        		+ COLUMN_NAME_CANDIDATE_ID + " INTEGER NOT NULL, "
        		+ COLUMN_NAME_LOCATION + " TEXT, "
        		+ COLUMN_NAME_INTERVIEW_DATE + " TIMESTAMP"
        		+ ")";
        
        public static final String STATEMENT_DELETE_TABLE =
        		"DROP TABLE IF EXISTS " + TABLE_NAME;
	}
}
