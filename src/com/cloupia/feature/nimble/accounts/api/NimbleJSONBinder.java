package com.cloupia.feature.nimble.accounts.api;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cloupia.service.cIM.inframgr.collector.controller.ItemDataObjectBinderIf;
/**
 * This is sample abstratc class implementation how to implement the JSON Binder for the exact implementation.
 * This is basically bind the Context for Data
 * 
 * 
 *
 */
public abstract class NimbleJSONBinder implements ItemDataObjectBinderIf {
	private static Logger logger = Logger.getLogger(NimbleJSONBinder.class);

	public abstract List<Class> getPersistantClassList();

	protected void bindContext(Object obj, Map<String, Object> context) {
		for (Map.Entry<String, Object> entry : context.entrySet()) {
			String varName = entry.getKey();
			Object value = entry.getValue();
			try {
				Field field = obj.getClass().getDeclaredField(varName);
				field.setAccessible(true);
				if (value != null)
					field.set(obj, value);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block

			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				logger.debug("No field by name " + varName + " for Class "
						+ obj.getClass().getSimpleName());
				continue;
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				logger.info("Illegal argument value while setting value for "
						+ varName + obj.getClass());
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				logger.info("Illegal access while setting value for " + varName
						+ obj.getClass());
			}
		}
	}
}
