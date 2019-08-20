package com.example.myapplication2.util;

import org.ksoap2.serialization.SoapObject;

public class AselTran
{

    // XML 에서 컬럼값 꺼내기...
    public static String GetValue(SoapObject so, String field)
    {
	    if (!so.hasProperty(field))
	    {
		    return "";
	    }

        String tVal = so.getProperty(field).toString();
        if (tVal.equals("anyType{}"))
            tVal = "";
        return tVal;
    }

    public static String GetValue(SoapObject so, int field)
    {
        String tVal = so.getProperty(field).toString();
        if (tVal.equals("anyType{}"))
            tVal = "";
        return tVal;
    }
}
