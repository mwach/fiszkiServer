package mawa.mobica.com.dao;

import java.sql.SQLException;

public interface IDao<T> {

	public T create(T object) throws SQLException;

	public void delete(long objectId) throws SQLException;

	public void update(T object) throws SQLException;

	public T get(long objectId) throws SQLException;
}
