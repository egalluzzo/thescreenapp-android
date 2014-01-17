package com.thescreenapp.dao;

import com.thescreenapp.model.ScreenModelObject;

public interface Repository<T extends ScreenModelObject> {
	T findById(long id);
	T findByUuid(String uuid);
	Iterable<T> findAll();
}
