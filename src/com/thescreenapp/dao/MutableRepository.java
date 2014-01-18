package com.thescreenapp.dao;

import com.thescreenapp.model.ScreenModelObject;

/**
 * A repository that allows all the basic CRUD operations on the objects in it.
 *
 * @param <T>  The class of objects in this repository.
 */
public interface MutableRepository<T extends ScreenModelObject> extends Repository<T> {
	/**
	 * Adds the given object to this repository.  The existing ID in the object
	 * will be ignored.  The ID will be populated with the ID of the object
	 * after it is saved.  The last update date will be set to the current date.
	 * 
	 * @param object  The object to create
	 */
	void create(T object);
	
	/**
	 * Updates the given object in the repository.  The last update date will be
	 * set to the current date.
	 * 
	 * @param object  The object to save
	 */
	void update(T object);
	
	/**
	 * Deletes the given object from the repository (or at least the object with
	 * the same ID).
	 * 
	 * @param object  The object to delete
	 */
	void delete(T object);
}
