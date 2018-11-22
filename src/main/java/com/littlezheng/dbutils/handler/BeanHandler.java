package com.littlezheng.dbutils.handler;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;

public class BeanHandler<T> implements ResultSetHandler<T> {

	private static final Integer PROPERTY_NOT_FOUND = -1;

	private final Class<T> clazz;

	public BeanHandler(Class<T> clazz) {
		super();
		if (clazz == null) {
			throw new RuntimeException("传入的class对象为空！");
		}
		this.clazz = clazz;
	}

	@Override
	public T handle(ResultSet rs) throws SQLException {
		if (rs == null || !rs.next()) {
			return null;
		}
		ResultSetMetaData metaData = rs.getMetaData();
		int cols = metaData.getColumnCount();
		if (cols <= 0) {
			return null;
		}
		T obj = createInstance(clazz);
		if (obj == null) {
			return null;
		}
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();
			int[] propInfos = new int[cols + 1];
			Arrays.fill(propInfos, PROPERTY_NOT_FOUND);

			for (int i = 1; i <= cols; i++) {
				String name = metaData.getColumnLabel(i);
				if (name == null || name.isEmpty()) {
					name = metaData.getColumnName(i);
				}
				for (int j = 0; j < props.length; j++) {
					if (props[j].getName().equalsIgnoreCase(name)) {
						propInfos[i] = j;
					}
				}
			}

			for (int i = 1; i <= cols; i++) {
				if (propInfos[i] != PROPERTY_NOT_FOUND) {
					Object value = rs.getObject(i);
					PropertyDescriptor pd = props[propInfos[i]];
					Method writeMethod = pd.getWriteMethod();
					if (writeMethod != null) {
						writeMethod.invoke(obj, value);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e);
		}

		return obj;
	}

	private T createInstance(Class<T> clazz) throws SQLException {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e);
		}
	}

}
