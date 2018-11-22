package com.littlezheng.dbutils.test;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

public class Test2 {

	public static void main(String[] args) throws Exception {
		Person p = new Person();
		p.setId(12L);
		p.setName("Jack");
		p.setAge(23);
		
		BeanInfo beanInfo = Introspector.getBeanInfo(Person.class);
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		if(propertyDescriptors != null && propertyDescriptors.length > 0){
			for (PropertyDescriptor pd : propertyDescriptors) {
				System.out.println(pd.getName());
				/*Method readMethod = pd.getReadMethod();
				System.out.println(pd.getName() + " = " + readMethod.invoke(p));*/
			}
		}
	}
}
