package com.thescreenapp.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListQuery<T> implements Query<T> {
	private List<T> mList;
	private Iterator<T> mIterator;
	private T mCurrent;
	
	public ListQuery(List<T> list) {
		mList = list;
		mIterator = list.iterator();
	}
	
	public ListQuery(Iterator<T> iterator) {
		mList = new ArrayList<T>();
		while (iterator.hasNext()) {
			mList.add(iterator.next());
		}
		mIterator = mList.iterator();
	}
	
	@Override
	public boolean next() {
		boolean hasNext = mIterator.hasNext();
		mCurrent = hasNext ? mIterator.next() : null;
		return hasNext;
	}

	@Override
	public T current() throws IllegalStateException {
		if (mCurrent == null) {
			throw new IllegalStateException("No next item in the query");
		}
		return mCurrent;
	}

	@Override
	public List<T> all() {
		return mList;
	}

	@Override
	public void close() {
		// No need to do anything
	}
}
