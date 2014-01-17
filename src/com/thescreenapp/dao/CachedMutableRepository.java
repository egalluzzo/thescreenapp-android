package com.thescreenapp.dao;

import java.util.HashMap;
import java.util.Map;

import com.thescreenapp.model.ScreenModelObject;

public class CachedMutableRepository<T extends ScreenModelObject> implements MutableRepository<T> {
	protected MutableRepository<T> mDelegate;
	private Map<Long, T> mIdToObjectMap = new HashMap<Long, T>();
	private Map<String, T> mUuidToObjectMap = new HashMap<String, T>();
	
	public CachedMutableRepository(MutableRepository<T> delegate) {
		mDelegate = delegate;
		Iterable<T> objects = delegate.findAll();
		for (T object : objects) {
			mIdToObjectMap.put(object.getId(), object);
			mUuidToObjectMap.put(object.getUuid(), object);
		}
	}

	@Override
	public T findById(long id) {
		T object = mIdToObjectMap.get(id);
		if (object == null) {
			object = mDelegate.findById(id);
			if (object != null) {
				mIdToObjectMap.put(id, object);
			}
		}
		return object;
	}

	@Override
	public T findByUuid(String uuid) {
		T object = mUuidToObjectMap.get(uuid);
		if (object == null) {
			object = mDelegate.findByUuid(uuid);
			if (object != null) {
				mUuidToObjectMap.put(uuid, object);
			}
		}
		return object;
	}

	@Override
	public Iterable<T> findAll() {
		return mIdToObjectMap.values();
	}
	
	@Override
	public void create(T object) {
		mDelegate.create(object);
		mIdToObjectMap.put(object.getId(), object);
		mUuidToObjectMap.put(object.getUuid(), object);
	}
	
	@Override
	public void update(T object) {
		mDelegate.update(object);
		mIdToObjectMap.put(object.getId(), object);
		mUuidToObjectMap.put(object.getUuid(), object);
	}

	@Override
	public void delete(T object) {
		mDelegate.delete(object);
		mIdToObjectMap.remove(object.getId());
		mUuidToObjectMap.remove(object.getUuid());
	}
}
