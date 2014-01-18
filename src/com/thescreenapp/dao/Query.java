package com.thescreenapp.dao;

import java.io.Closeable;
import java.util.List;

/**
 * A query over a number of objects.  Typical usage is as follows:
 * 
 * <pre>
 *     Query&lt;MyClass&gt; query = ...;
 *     try {
 *         while (query.next()) {
 *             MyClass myObject = query.current();
 *             // Do something with myObject here
 *         }
 *     } finally {
 *         query.close();
 *     }
 * </pre>
 * 
 * Alternatively, you may wish to call {@link #all()} to retrieve all results.
 * 
 * <p>FIXME: When compiling against Java 7, this should extend AutoCloseable.
 */
public interface Query<T> extends Closeable {
	/**
	 * Advances the query to the next object, if there is one.
	 * 
	 * @return <code>true</code> if the query is now pointing to a valid row
	 *         (so that {@link #current()} can be called), or <code>false</code>
	 *         if we are past the last result
	 */
	public boolean next();
	
	/**
	 * Returns the current object in the query.  {@link #next()} must have been
	 * called at least once prior to calling this method.
	 * 
	 * @return  The current object in this query
	 * 
	 * @throws IllegalStateException
	 *     If the query is pointing to before the first object or after the
	 *     last object
	 */
	public T current() throws IllegalStateException;
	
	/**
	 * Retrieves all the object in the query.  Even if {@link #next()} has been
	 * called a number of times, this method should still return a list of all
	 * entries in the query, not just the ones starting at the current point in
	 * the query.
	 * 
	 * @return
	 */
	public List<T> all();
	
	/**
	 * Closes this query and releases any resources associated with it, after
	 * which point it will be unusable.  It should be possible to call this
	 * method multiple times without ill effect.
	 */
	public void close();
}
