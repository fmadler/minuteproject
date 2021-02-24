package net.sf.minuteProject.architecture.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

public class BeanUtils {
	
	public static void copyBeanObject (Object origin, Object target) throws IllegalAccessException, InvocationTargetException {
		org.apache.commons.beanutils.BeanUtils.copyProperties(origin, target);
	}
	
	public static Object cloneBeanObject (Object origin) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
		return org.apache.commons.beanutils.BeanUtils.cloneBean(origin);
	}
	
	public static void populateBeanObject (Object bean, String beanPath, Object value) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		PropertyUtils.setProperty(bean, beanPath, value);
	}
	

	public static Object getBeanObjectInstance (Object bean) throws SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Class cls = bean.getClass();
        Constructor ct = cls.getConstructor();
		return ct.newInstance();		
	}

	public static void populateObject (Object bean, Object value, String beanPath) throws IllegalAccessException, InvocationTargetException {
		try {
			BeanUtils.populateBeanObject(bean, beanPath, value);
		} catch (NoSuchMethodException e) {
			// TODO log
		}
	}
}
