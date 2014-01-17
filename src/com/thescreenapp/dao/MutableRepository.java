package com.thescreenapp.dao;

import com.thescreenapp.model.ScreenModelObject;

public interface MutableRepository<T extends ScreenModelObject> extends Repository<T> {
	void create(T object);
	void update(T object);
	void delete(T object);
}
