package com.thescreenapp.android.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.SparseArray;

import com.thescreenapp.dao.AbstractMutableRepository;
import com.thescreenapp.dao.AbstractQuery;
import com.thescreenapp.dao.Query;
import com.thescreenapp.model.ScreenModelObject;

/**
 * An abstract repository for records in a single table in Android's SQLite
 * data store.
 * 
 * @author eric
 */
public abstract class AbstractSqliteRepository<T extends ScreenModelObject> extends AbstractMutableRepository<T> {
	protected ScreenDbHelper mDbHelper;
	
	/**
	 * A {@link Query} that uses an Android SQLite {@link Cursor} to iterate
	 * over its results.
	 */
	public class CursorQuery extends AbstractQuery<T> {
		private Cursor mCursor;
		private SparseArray<T> mIndexToObjectMap;
		
		public CursorQuery(Cursor cursor) {
			mCursor = cursor;
			mIndexToObjectMap = new SparseArray<T>();
		}
		
		public Cursor getCursor() {
			return mCursor;
		}
		
		@Override
		public int count() {
		    
			if(mCursor != null && !mCursor.isClosed()){
				return mCursor.getCount();
		    } 
			return -1;
		}
		
		@Override
		public T get(int index) {
			T object = mIndexToObjectMap.get(index);
			if (object == null) {
				if (mCursor.moveToPosition(index)) {
					object = readObject(mCursor);
					mIndexToObjectMap.put(index, object);
				} else {
					throw new IndexOutOfBoundsException("Could not move to cursor position " + index);
				}
			}
			return object;
		}

		@Override
		public void close() {
			if (mCursor != null) {
				mCursor.close();
				mCursor = null;
				mIndexToObjectMap = null;
			}
		}
	}
	
	public AbstractSqliteRepository(ScreenDbHelper dbHelper) {
		mDbHelper = dbHelper;
	}

	@Override
	public void delete(ScreenModelObject object) {
		mDbHelper.getWritableDatabase().delete(getTableName(), ScreenBaseColumns._ID + " = ?", new String[] { String.valueOf(object.getId()) });
	}

	@Override
	public T findById(long id) {
		return findSingle(ScreenBaseColumns._ID + " = ?", new String[] {String.valueOf(id)});
	}

	@Override
	public T findByUuid(String uuid) {
		return findSingle(ScreenBaseColumns.COLUMN_NAME_UUID + " = ?", new String[] {uuid});
	}

	@Override
	public Query<T> findAll() {
		return find(null, null);
	}

	@Override
	protected long createFilledInObject(T object) {
		return mDbHelper.getWritableDatabase().insertOrThrow(getTableName(), null, getContentValues(object));
	}

	@Override
	protected void updateFilledInObject(T object) {
		int rowsUpdated = mDbHelper.getWritableDatabase().update(getTableName(), getContentValues(object), ScreenBaseColumns._ID + " = ?", new String[] {String.valueOf(object.getId())});
		if (rowsUpdated != 1) {
			throw new SQLException("Updated " + rowsUpdated + " rows instead of 1");
		}
	}
	
	protected Query<T> find(String selection, String[] selectionArgs) {
		return find(selection, selectionArgs, null);
	}
	
	protected Query<T> find(String selection, String[] selectionArgs, String sortBy) {
		return find(selection, selectionArgs, sortBy, null);
	}
	
	protected Query<T> find(String selection, String[] selectionArgs, String sortBy, String limit) {
		Cursor cursor = query(selection, selectionArgs, sortBy, limit);
		try {
			return new CursorQuery(cursor);
		} finally {
			//TODO: remove me
			//let QueryLoader or other invoking class call
			//close() on CursorQuery when they are ready
			//cursor.close();
		}
	}
	
	protected T findSingle(String selection, String[] selectionArgs) {
		Cursor cursor = query(selection, selectionArgs, null, "2");
		try {
			if (cursor.moveToFirst()) {
				T result = readObject(cursor);
				if (cursor.moveToNext()) {
					throw new SQLException("Expected one " + getTableName() + " record, got more than one");
				} else {
					return result;
				}
			} else {
				return null;
			}
		} finally {
			cursor.close();
		}
	}
	
	protected Cursor query(String selection, String[] selectionArgs, String sortBy, String limit) {
		return mDbHelper.getReadableDatabase().query(getTableName(), getColumnNames(), selection, selectionArgs, null, null, sortBy, limit);
	}

	/**
	 * The table in which records for class T are stored.
	 * 
	 * @return  The table name
	 */
	protected abstract String getTableName();
	
	/**
	 * All columns that should be populated in objects of class T, including
	 * the ID column.  Probably corresponds to all the fields inside class T
	 * and its superclasses.
	 * 
	 * @return  The list of column names
	 */
	protected abstract String[] getColumnNames();
	
	/**
	 * Creates an object from the columns currently under the cursor.  The
	 * implementation of this method should not move the cursor.
	 * 
	 * @param cursor  The cursor from which to read the object
	 * 
	 * @return  The newly created object
	 */
	protected abstract T readObject(Cursor cursor);
	
	/**
	 * Retrieves all the columns, excluding the ID column, that should be
	 * written to the database for the given object.
	 * 
	 * @param object  The object
	 * 
	 * @return  The columns and their values for the given object
	 */
	protected abstract ContentValues getContentValues(T object);
}
