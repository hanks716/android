package com.example.myapplication2.util;

import org.ksoap2.serialization.SoapObject;

import java.lang.reflect.Field;

/**
 * Created by 이영곤 on 2015-01-31.
 */
public class SOAPParser<T> {

	public SOAPParser(){}

	public T fromSoap(SoapObject rs, Class<T> dataContainerClass)
    {
	    try
	    {
		    T container = dataContainerClass.newInstance();
		    return fromSoap(rs, container);
	    } catch (InstantiationException e)
	    {
		    e.printStackTrace();
	    } catch (IllegalAccessException e)
	    {
		    e.printStackTrace();
	    }

	    return null;
    }

	public T fromSoap(SoapObject rs, T container)
	{
		try {
			Field[] fieldList = container.getClass().getDeclaredFields();
			for(Field field:fieldList)
			{
				if(!rs.hasProperty(field.getName()))
					continue;

				String value = AselTran.GetValue(rs, field.getName());
				if (field.getType() == int.class) {
					field.set(container, Integer.parseInt(value));
				} else if (field.getType() == boolean.class) {
					field.set(container, value.charAt(0) == 'Y' ? true : false);
				} else {
					field.set(container, value);
				}
			}

			return container;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return null;
	}
}
